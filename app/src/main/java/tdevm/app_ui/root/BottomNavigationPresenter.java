package tdevm.app_ui.root;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.RestaurantTable;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 18-10-2017.
 */

public class BottomNavigationPresenter extends BasePresenter implements NavigationHomePresenterContract.BottomNavigationHomePresenter{
    public static final String TAG  = BottomNavigationPresenter.class.getSimpleName();

    private static final String MODE_DINE_IN = "MODE_DINE_IN";
    private static final String MODE_NON_DINE = "MODE_NON_DINE";
    private APIService apiService;
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;

    private NavigationHomeViewContract.BottomNavigationView bottomNavigationView;

    @Inject
    public BottomNavigationPresenter(APIService apiService,AuthUtils authUtils){
        this.authUtils = authUtils;
        compositeDisposable = new CompositeDisposable();
        this.apiService = apiService;
    }


    public void handleUserAuthentication(){
        if (authUtils.getAuthLoginState()) {
            bottomNavigationView.showUserProfile();
        } else {
            bottomNavigationView.redirectAuthActivity();
        }
    }

    public void handleQRContent(String qrContent){
        try {
            JSONObject object = new JSONObject(qrContent);
            String restaurantUUID = object.getString("restaurant_id");
            String tableNo;
            try {
               tableNo = object.getString("table_no");
               if(tableNo!=null && restaurantUUID!=null){
                    //T1
                   String tableShortId = restaurantUUID+'_'+tableNo;
                   authUtils.saveDineQRTransaction(restaurantUUID,tableShortId,MODE_DINE_IN);
                   Log.d(TAG,"Saved Restaurant"+ authUtils.getScannedRestaurantUuid());
                   Log.d(TAG,"Saved Table"+ authUtils.getScannedRestaurantTableShortId());
                   Log.d(TAG,"Restaurant Mode"+ authUtils.getRestaurantMode());
                   verifyRestaurantTableVacant(tableShortId);
                }
            }catch (JSONException e){
                //T2
                if(restaurantUUID!=null) {
                    authUtils.saveNonDineQRTransaction(restaurantUUID,MODE_NON_DINE);
                    Log.d(TAG,"Saved Restaurant"+authUtils.getScannedRestaurantUuid());
                    Log.d(TAG,"Restaurant Mode"+ authUtils.getRestaurantMode());
                    Log.d(TAG,"Type 2 valid");
                    bottomNavigationView.redirectNonDineActivity();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG,"Malformed QR content");
        }
    }


    public void verifyRestaurantTableVacant(String tableShortId){
        Map<String,String> getRestData = new HashMap<>();
        getRestData.put("table_id",tableShortId);
        Observable<Response<RestaurantTable>> observable = apiService.verifyTableVacancy(authUtils.getAuthLoginToken(),getRestData);
        subscribe(observable, new Observer<Response<RestaurantTable>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
               compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Response<RestaurantTable> restaurantTableResponse) {
                if(restaurantTableResponse.code() ==200){
                    Log.d(TAG,restaurantTableResponse.body().getRestaurant_table_uuid_shortid());
                    bottomNavigationView.redirectDineInActivity(restaurantTableResponse.body().getRestaurant_uuid());
                }else if(restaurantTableResponse.code() ==206) {
                    Log.d(TAG,restaurantTableResponse.body().toString());
                    bottomNavigationView.showTableOccupiedError();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Override
    public void attachView(NavigationHomeViewContract.BottomNavigationView view) {
        bottomNavigationView = view;
    }

    @Override
    public void detachView() {
      compositeDisposable.clear();
      compositeDisposable.dispose();
    }

    public void verifyUserAuthentication() {
        if (authUtils.getAuthLoginState()) {
            bottomNavigationView.startQRScanner();
        } else {
            bottomNavigationView.redirectAuthActivity();
        }
    }
}
