package tdevm.app_ui.root.activities;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.root.RootActivityPresenterContract;
import tdevm.app_ui.root.RooActivityViewContract;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.utils.PreferenceUtils;

public class RestaurantDetailPresenter extends BasePresenter implements RootActivityPresenterContract.RestaurantDetailPresenter {

    public static final String TAG = RestaurantDetailPresenter.class.getSimpleName();

    private APIService apiService;
    private CartHelper cartHelper;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;

    private RooActivityViewContract.RestaurantDetailView detailActivity;

    @Inject
    public RestaurantDetailPresenter(APIService apiService, CartHelper cartHelper, PreferenceUtils preferenceUtils) {
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void fetchMenuItems(Restaurant restaurant) {
        Map<String, String> map1 = new HashMap<>();
        map1.put("restaurant_id", restaurant.getRestaurant_id());
        Observable<Response<List<CuisineMenuItems>>> cuisineItem = apiService.fetchMenuItems("Bearer " + preferenceUtils.getAuthLoginToken(), map1);
        subscribe(cuisineItem, new Observer<Response<List<CuisineMenuItems>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<List<CuisineMenuItems>> listResponse) {
                Log.d(TAG, "SUccess response");
                if (listResponse.isSuccessful()) {
                    if (listResponse.code() == 200) {
                        detailActivity.onMenuItemsFetchedV2(listResponse.body());
                        Log.d(TAG, "Presenter fetch menu V2");
                    }
                } else {
                    detailActivity.onMenuItemsFetchFailure();
                }
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
    public void attachView(RooActivityViewContract.RestaurantDetailView view) {
        this.detailActivity = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
