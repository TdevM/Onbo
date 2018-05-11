package tdevm.app_ui.modules.nondinein.activities;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.nondinein.NonDinePresenterContract;
import tdevm.app_ui.modules.nondinein.NonDineViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 02-02-2018.
 */

public class NonDineRestDetailPresenter extends BasePresenter implements NonDinePresenterContract.NonDineRestaurantDetails{

    public static final String TAG = NonDineRestDetailPresenter.class.getSimpleName();
    private APIService apiService;
    private AuthUtils authUtils;
    private CartHelper cartHelper;
    private NonDineViewContract.NonDineRestaurantDetailsView view;
    private CompositeDisposable compositeDisposable;

    @Inject
    public NonDineRestDetailPresenter(APIService apiService, AuthUtils authUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.compositeDisposable = new CompositeDisposable();
        this.cartHelper = cartHelper;
    }

    public void fetchRestaurantDetails(){
        Map<String,String> map = new HashMap<>();
        map.put("restaurant_uuid",authUtils.getScannedRestaurantUuid());
        Observable<Response<Restaurant>> restaurant = apiService.fetchRestaurantDetails(map, authUtils.getAuthLoginToken());
        subscribe(restaurant, new Observer<Response<Restaurant>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<Restaurant> restaurantResponse) {
              view.onRestaurantDetailsFetched(restaurantResponse.body());
                Log.d(TAG,"Restaurant Details Fetched"+restaurantResponse.body().getRestaurants_name());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });


    }

    @Override
    public void attachView(NonDineViewContract.NonDineRestaurantDetailsView view) {
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
