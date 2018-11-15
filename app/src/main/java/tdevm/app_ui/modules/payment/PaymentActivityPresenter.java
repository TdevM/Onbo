package tdevm.app_ui.modules.payment;


import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.request.PaymentCapture;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.PreferenceUtils;

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
        Observable<Response<FOrder>> observable = apiService.payOrder("Bearer " + preferenceUtils.getAuthLoginToken(), capture);
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
                ///paymentActivityView.onPaymentCaptureFailure();
            }

            @Override
            public void onComplete() {
                paymentActivityView.hidePaymentCaptureProgressUI();
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
