package onbo.app.modules.nondine.activities;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import onbo.app.api.APIService;
import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.base.BasePresenter;
import onbo.app.modules.nondine.NonDinePresenterContract;
import onbo.app.modules.nondine.NonDineViewContract;
import onbo.app.utils.PreferenceUtils;
import onbo.app.utils.CartHelper;

/**
 * Created by Tridev on 02-02-2018.
 */

public class NonDineRestDetailPresenter extends BasePresenter implements NonDinePresenterContract.NonDineRestaurantDetails{

    public static final String TAG = NonDineRestDetailPresenter.class.getSimpleName();
    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;
    private NonDineViewContract.NonDineRestaurantDetailsView view;
    private CompositeDisposable compositeDisposable;

    @Inject
    public NonDineRestDetailPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
        this.cartHelper = cartHelper;
    }

    public void fetchRestaurantDetails(){
        Map<String,String> map = new HashMap<>();
        map.put("restaurant_uuid", preferenceUtils.getScannedRestaurantUuid());
        Observable<Response<Restaurant>> restaurant = apiService.fetchRestaurantDetails(map, "Bearer "+ preferenceUtils.getAuthLoginToken());
        subscribe(restaurant, new Observer<Response<Restaurant>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<Restaurant> restaurantResponse) {
              view.onRestaurantDetailsFetched(restaurantResponse.body());
                Log.d(TAG,"Restaurant Details Fetched"+restaurantResponse.body().getRestaurant_name());
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
