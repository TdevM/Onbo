package tdevm.app_ui.modules.entry;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationResult;

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
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 14-03-2018.
 */

public class MenuEntryPresenter extends BasePresenter implements MenuEntryPresenterContract.RestaurantMenuEntryPresenter{

    public static final String TAG = MenuEntryPresenter.class.getSimpleName();

    private static final String MODE_DINE_IN = "MODE_DINE_IN";
    private static final String MODE_NON_DINE = "MODE_NON_DINE";
    private APIService apiService;
    private AuthUtils authUtils;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;
    private MenuEntryViewContract.RestaurantMenuEntryView view;

    @Inject
    public MenuEntryPresenter(APIService apiService, AuthUtils authUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.cartHelper = cartHelper;
        compositeDisposable = new CompositeDisposable();
    }

    public void handleLocationUpdates(LocationResult result){
        if(result!=null){
            Location mCurrentLocation  = result.getLastLocation();
            Log.d(TAG,"Provider: "+mCurrentLocation.getProvider());
           if(mCurrentLocation.getAccuracy()<50.0){
               view.startQRScanner();
           }
        }
    }

    @Override
    public void clearExistingCart() {
        if (cartHelper.cartItemsExist()) {
            cartHelper.clearCart();
            Log.d(TAG, "Cart Items existing. Cart Cleared.");
        }
    }

    @Override
    public void handleQRContent(String qrContent) {
        try {
            JSONObject object = new JSONObject(qrContent);
            String restaurantUUID = object.getString("restaurant_id");
            String tableNo;
            try {
                tableNo = object.getString("table_no");
                if (tableNo != null && restaurantUUID != null) {
                    //T1
                    String tableShortId = restaurantUUID + '_' + tableNo;
                    authUtils.saveDineQRTransaction(restaurantUUID, tableShortId, MODE_DINE_IN);
                    Log.d(TAG, "Saved Restaurant" + authUtils.getScannedRestaurantUuid());
                    Log.d(TAG, "Saved Table" + authUtils.getScannedRestaurantTableShortId());
                    Log.d(TAG, "Restaurant Mode" + authUtils.getRestaurantMode());
                    verifyRestaurantTableVacant(tableShortId);
                    clearExistingCart();
                }
            } catch (JSONException e) {
                //T2
                if (restaurantUUID != null) {
                    authUtils.saveNonDineQRTransaction(restaurantUUID, MODE_NON_DINE);
                    Log.d(TAG, "Saved Restaurant" + authUtils.getScannedRestaurantUuid());
                    Log.d(TAG, "Restaurant Mode" + authUtils.getRestaurantMode());
                    Log.d(TAG, "Type 2 valid");
                    clearExistingCart();
                    //Calculate distance.
                    //THEN
                    view.redirectNonDineActivity();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Malformed QR content");
        }
    }


    @Override
    public void verifyRestaurantTableVacant(String tableShortId) {
        Map<String, String> getRestData = new HashMap<>();
        getRestData.put("table_id", tableShortId);
        Observable<Response<RestaurantTable>> observable = apiService.verifyTableVacancy(authUtils.getAuthLoginToken(), getRestData);
        subscribe(observable, new Observer<Response<RestaurantTable>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Response<RestaurantTable> restaurantTableResponse) {
                if (restaurantTableResponse.code() == 200) {
                    Log.d(TAG, restaurantTableResponse.body().getRestaurant_table_uuid_shortid());
                    //Calculate distance.
                    //THEN
                    view.redirectDineInActivity();
                } else if (restaurantTableResponse.code() == 206) {
                    Log.d(TAG, restaurantTableResponse.body().toString());
                    view.showTableOccupiedError();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Override
    public void attachView(MenuEntryViewContract.RestaurantMenuEntryView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
