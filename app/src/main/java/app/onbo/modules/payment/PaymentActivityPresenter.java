package app.onbo.modules.payment;


import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import app.onbo.api.models.response.v2.merged.MergedOrder;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.request.PaymentCapture;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.base.BasePresenter;
import app.onbo.utils.PreferenceUtils;

public class PaymentActivityPresenter extends BasePresenter implements PaymentPresenterContract.PaymentActivityPresenterContract {

    public static final String TAG = PaymentActivityPresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;
    private PreferenceUtils preferenceUtils;
    private APIService apiService;
    private PaymentViewContract.PaymentActivityView paymentActivityView;


    @Inject
    public PaymentActivityPresenter(PreferenceUtils preferenceUtils, APIService apiService) {
        this.preferenceUtils = preferenceUtils;
        this.apiService = apiService;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(PaymentViewContract.PaymentActivityView view) {
        this.paymentActivityView = view;
    }

    @Override
    public void captureOrderPayment(String paymentId, String orderId) {
        PaymentCapture capture = new PaymentCapture(orderId, paymentId, preferenceUtils.getScannedRestaurantId());
        Observable<Response<FOrder>> observable = apiService.payOrder(capture);
        subscribe(observable, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                paymentActivityView.showPaymentCaptureProgressUI();
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                if (fOrderResponse.code() == 200) {
                    if (fOrderResponse.body() != null) {
                        Log.d(TAG, fOrderResponse.body().toString());
                        paymentActivityView.onPaymentCaptured(fOrderResponse.body());
                    }
                } else {
                    paymentActivityView.onPaymentCaptureFailure(fOrderResponse.body());
                }
            }

            @Override
            public void onError(Throwable e) {
                //paymentActivityView.onPaymentCaptureFailure();
            }

            @Override
            public void onComplete() {
                paymentActivityView.hidePaymentCaptureProgressUI();
            }
        });


    }


    public void fetchMergedOrder(String tOrder) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        map.put("order_id", tOrder);
        Observable<Response<MergedOrder>> observable = apiService.fetchMergedOrder(map);
        subscribe(observable, new Observer<Response<MergedOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                paymentActivityView.showFetchingPayment();
            }

            @Override
            public void onNext(Response<MergedOrder> arrayListResponse) {
                if (arrayListResponse.code() == 200) {
                    paymentActivityView.onMergedOrderFetched(arrayListResponse.body());
                } else if (arrayListResponse.code() == 404) {
                    paymentActivityView.onMergedOrderFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                paymentActivityView.onMergedOrderFailure();
            }

            @Override
            public void onComplete() {
                paymentActivityView.hideFetchingPayment();
            }
        });
    }

    @Override
    public void fetchClosedOrder(String tOrderId) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        map.put("t_order_id", tOrderId);
        Observable<retrofit2.Response<FOrder>> observable = apiService.fetchClosedOrder(map);
        subscribe(observable, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                paymentActivityView.showFetchingPayment();
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                if (fOrderResponse.code() == 200) {
                    if (fOrderResponse.body() != null) {
                        paymentActivityView.onFOrderFetched(fOrderResponse.body());
                    }
                } else {
                    paymentActivityView.onFOrderFetchFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                paymentActivityView.onFOrderFetchFailure();
            }

            @Override
            public void onComplete() {
                paymentActivityView.hideFetchingPayment();
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
}
