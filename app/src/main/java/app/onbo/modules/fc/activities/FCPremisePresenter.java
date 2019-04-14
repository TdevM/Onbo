package app.onbo.modules.fc.activities;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import app.onbo.api.APIService;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.menu.CuisineMenuItems;
import app.onbo.base.BasePresenter;
import app.onbo.modules.fc.FCPresenterContract;
import app.onbo.modules.fc.FCViewContract;
import app.onbo.root.RootActivityViewContract;
import app.onbo.utils.CartHelper;
import app.onbo.utils.PreferenceUtils;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class FCPremisePresenter extends BasePresenter implements FCPresenterContract.FCPremisePresenter {

    public static final String TAG = FCPremisePresenter.class.getSimpleName();

    private static final String MODE_NON_DINE = "MODE_NON_DINE";

    private APIService apiService;
    private CartHelper cartHelper;

    private CompositeDisposable compositeDisposable;
    private PreferenceUtils preferenceUtils;

    private FCViewContract.FCPremiseDetailView detailActivity;

    @Inject
    public FCPremisePresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
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
    public void saveFCQRTransaction(Restaurant restaurant) {
        preferenceUtils.saveFCQRTransaction(restaurant.getRestaurant_id(), restaurant.getRestaurant_uuid(), MODE_NON_DINE);
        detailActivity.redirectNonDineActivity(restaurant);
    }

    @Override
    public void clearCart(){
        cartHelper.clearCart();
    }

    @Override
    public void attachView(FCViewContract.FCPremiseDetailView view) {
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
