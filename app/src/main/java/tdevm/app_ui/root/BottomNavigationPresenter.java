package tdevm.app_ui.root;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

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
import tdevm.app_ui.base.BasePresenterMVP;
import tdevm.app_ui.base.BaseView;
import tdevm.app_ui.modules.auth.AuthenticationActivity;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 18-10-2017.
 */

public class BottomNavigationPresenter extends BasePresenter{
    public static final String TAG  = BottomNavigationPresenter.class.getSimpleName();

    private APIService apiService;
    private AuthUtils authUtils;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    private NavigationHomeContract.BottomNavigationView bottomNavigationView;

    @Inject
    public BottomNavigationPresenter(APIService apiService,AuthUtils authUtils){
        this.authUtils = authUtils;
        this.apiService = apiService;
    }

    public void setView(BottomNavigationHome view) {
      bottomNavigationView = view;
    }

    public void handleUserAuthentication(){
        if (authUtils.getAuthLoginState()) {
            bottomNavigationView.showUserProfile();
        } else {
            bottomNavigationView.redirectAuthActivity();
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


}
