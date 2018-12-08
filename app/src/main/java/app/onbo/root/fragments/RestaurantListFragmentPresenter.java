package app.onbo.root.fragments;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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
import app.onbo.base.BasePresenter;
import app.onbo.root.RootActivityPresenterContract;
import app.onbo.root.RootActivityViewContract;
import app.onbo.utils.PreferenceUtils;

public class RestaurantListFragmentPresenter extends BasePresenter implements RootActivityPresenterContract.RestaurantListFragmentPresenter {

    public static final String TAG = RestaurantListFragmentPresenter.class.getSimpleName();

    private PreferenceUtils preferenceUtils;
    private APIService service;
    private RootActivityViewContract.RestaurantsListView restaurantsListView;
    private CompositeDisposable compositeDisposable;
    private Application context;


    @Inject
    public RestaurantListFragmentPresenter(PreferenceUtils preferenceUtils, APIService service, Application c) {
        this.preferenceUtils = preferenceUtils;
        this.service = service;
        this.context = c;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void fetchRestaurants(String cityId) {
        if (isConnectedToInternet()) {
            Map<String, String> map = new HashMap<>();
            map.put("city_id", cityId);
            Observable<Response<List<Restaurant>>> observable = service.fetchAllRestaurants(map, "Bearer " + preferenceUtils.getAuthLoginToken());
            subscribe(observable, new Observer<Response<List<Restaurant>>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                    restaurantsListView.showProgressUI();
                }

                @Override
                public void onNext(Response<List<Restaurant>> restaurantResponse) {
                    restaurantsListView.showProgressUI();
                    if (restaurantResponse.code() == 200) {
                        if (restaurantResponse.body() != null) {
                            restaurantsListView.onRestaurantsFetched(restaurantResponse.body());
                        }
                    } else {
                        restaurantsListView.showBackendError();

                    }

                }

                @Override
                public void onError(Throwable e) {
                    restaurantsListView.showBackendError();
                }

                @Override
                public void onComplete() {
                    restaurantsListView.hideProgressUI();
                }
            });
        } else {
            restaurantsListView.showNoInternetError();
        }
    }


    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void attachView(RootActivityViewContract.RestaurantsListView view) {
        this.restaurantsListView = view;
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
