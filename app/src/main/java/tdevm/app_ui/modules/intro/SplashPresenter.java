package tdevm.app_ui.modules.intro;

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
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.utils.PreferenceUtils;

public class SplashPresenter extends BasePresenter implements IntroPresenterContract.SplashPresenterInterface {

    public static final String TAG = SplashPresenter.class.getSimpleName();

    private APIService apiService;
    private CartHelper cartHelper;
    private PreferenceUtils utils;
    private CompositeDisposable compositeDisposable;
    private IntroViewContract.SplashView splashView;


    @Inject
    public SplashPresenter(APIService apiService, CartHelper cartHelper, PreferenceUtils utils) {
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.utils = utils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(IntroViewContract.SplashView view) {
        this.splashView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }


    @Override
    public void checkCurrentOrderDetails() {
        Log.d(TAG, "Checking order...");
        Map<String, String> map = new HashMap<>();
        Observable<Response<TOrder>> observable = apiService.fetchMyRunningOrder("Bearer " + utils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<TOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<TOrder> arrayListResponse) {
                Log.d(TAG, "onNext RAN");
                if (arrayListResponse.isSuccessful()) {
                    if (arrayListResponse.code() == 200) {
                        splashView.onDineOrderRunning(arrayListResponse.body());
                        Log.d(TAG, "Add items to Order RAN");
                    }
                } else if (arrayListResponse.code() == 404) {
                    // No order running
                    Log.d(TAG, "No Order running");
                    splashView.onNoDineOrderRunning();
                }
            }

            @Override
            public void onError(Throwable e) {
                splashView.onOrderFetchFailure();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @Override
    public void fetchClosedOrder(String tOrderId) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", utils.getScannedRestaurantId());
        map.put("t_order_id", tOrderId);
        Observable<retrofit2.Response<FOrder>> observable = apiService.fetchClosedOrder("Bearer " + utils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                if (fOrderResponse.isSuccessful()) {
                    if (fOrderResponse.body() != null) {
                        splashView.onFOrderFetched(fOrderResponse.body());
                    }
                } else {
                    splashView.onFOrderFetchFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                splashView.onFOrderFetchFailure();
            }

            @Override
            public void onComplete() {

            }
        });
    }


}
