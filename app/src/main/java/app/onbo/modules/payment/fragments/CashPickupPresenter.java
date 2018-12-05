package app.onbo.modules.payment.fragments;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.base.BasePresenter;
import app.onbo.modules.payment.PaymentPresenterContract;
import app.onbo.modules.payment.PaymentViewContract;
import app.onbo.utils.CartHelper;
import app.onbo.utils.PreferenceUtils;

public class CashPickupPresenter extends BasePresenter implements PaymentPresenterContract.CashPickupPresenterContract {


    public static final String TAG = CashPickupPresenter.class.getSimpleName();

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;
    private CartHelper cartHelper;

    private PaymentViewContract.CashPickupView cashPickupView;


    @Inject
    public CashPickupPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.preferenceUtils = preferenceUtils;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(PaymentViewContract.CashPickupView view) {
        this.cashPickupView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void fetchOrderPaymentStatus(FOrder fOrder) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        map.put("t_order_id", fOrder.getT_order_id());
        map.put("order_id", fOrder.getOrder_id());
        Observable<Response<FOrder>> observable = apiService.fetchClosedOrder("Bearer " + preferenceUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                cashPickupView.showProgressUI();
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                if (fOrderResponse.code() == 200) {
                    if (fOrderResponse.body() != null) {
                        cashPickupView.onFOrderFetched(fOrderResponse.body());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                cashPickupView.hideProgressUI();
            }
        });
    }
}
