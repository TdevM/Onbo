package onbo.app.root.fragments;

import java.util.HashMap;
import java.util.List;
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
import onbo.app.root.RootActivityPresenterContract;
import onbo.app.root.RootActivityViewContract;
import onbo.app.utils.PreferenceUtils;

public class RestaurantListFragmentPresenter extends BasePresenter implements RootActivityPresenterContract.RestaurantListFragmentPresenter {

    public static final String TAG = RestaurantListFragmentPresenter.class.getSimpleName();

    private PreferenceUtils preferenceUtils;
    private APIService service;
    private RootActivityViewContract.RestaurantsListView restaurantsListView;
    private CompositeDisposable compositeDisposable;


    @Inject
    public RestaurantListFragmentPresenter(PreferenceUtils preferenceUtils, APIService service) {
        this.preferenceUtils = preferenceUtils;
        this.service = service;
        this.compositeDisposable = new CompositeDisposable();
    }



    @Override
    public void fetchRestaurants(String cityId){
        Map<String, String> map = new HashMap<>();
        map.put("city_id",cityId);
        Observable<Response<List<Restaurant>>> observable = service.fetchAllRestaurants(map, "Bearer "+preferenceUtils.getAuthLoginToken());
        subscribe(observable, new Observer<Response<List<Restaurant>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<List<Restaurant>> restaurantResponse) {
                restaurantsListView.showProgressUI();
                if(restaurantResponse.isSuccessful()){
                    if(restaurantResponse.body()!=null){
                        restaurantsListView.onRestaurantsFetched(restaurantResponse.body());
                    }
                }else {
                    restaurantsListView.onRestaurantsFetchFailure();

                }

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                restaurantsListView.hideProgressUI();
            }
        });
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
}
