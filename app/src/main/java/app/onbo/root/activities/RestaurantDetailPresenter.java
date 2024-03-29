package app.onbo.root.activities;

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
import app.onbo.api.APIService;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.menu.CuisineMenuItems;
import app.onbo.base.BasePresenter;
import app.onbo.root.RootActivityPresenterContract;
import app.onbo.root.RootActivityViewContract;
import app.onbo.utils.CartHelper;
import app.onbo.utils.PreferenceUtils;

public class RestaurantDetailPresenter extends BasePresenter implements RootActivityPresenterContract.RestaurantDetailPresenter {

    public static final String TAG = RestaurantDetailPresenter.class.getSimpleName();

    private APIService apiService;
    private CartHelper cartHelper;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;

    private RootActivityViewContract.RestaurantDetailView detailActivity;

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
        Observable<Response<List<CuisineMenuItems>>> cuisineItem = apiService.fetchMenuItems(map1);
        subscribe(cuisineItem, new Observer<Response<List<CuisineMenuItems>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                detailActivity.showProgressUI();
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
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
                detailActivity.hideProgressUI();
            }
        });
    }

    @Override
    public void attachView(RootActivityViewContract.RestaurantDetailView view) {
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
