package tdevm.app_ui.modules.nondine.activities;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.request.NonDineOrder;
import tdevm.app_ui.api.models.request.PaymentCapture;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.nondine.NonDinePresenterContract;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.utils.PreferenceUtils;
import tdevm.app_ui.utils.CartHelper;

public class InitNonDineOrderPresenter extends BasePresenter implements NonDinePresenterContract.InitNonDineOrderPresenter {

    public static final String TAG = InitNonDineOrderPresenter.class.getSimpleName();

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;
    private CartHelper cart;

    private NonDineViewContract.InitNonDineOrderView nonDineOrderView;


    @Inject
    public InitNonDineOrderPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cart) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.cart = cart;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(NonDineViewContract.InitNonDineOrderView view) {
        this.nonDineOrderView = view;
        Log.d(TAG, "Attach view called for presenter");
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }


    @Override
    public void capturePaymentForOrder(String payment_id, String orderId) {

        PaymentCapture capture = new PaymentCapture(orderId, payment_id, preferenceUtils.getScannedRestaurantId());
        Observable<Response<FOrder>> observable = apiService.captureNonDineOrderPayment("Bearer "+ preferenceUtils.getAuthLoginToken(), capture);
        subscribe(observable, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                if (fOrderResponse.isSuccessful()) {
                    if (fOrderResponse.body() != null) {
                        Log.d(TAG, fOrderResponse.body().toString());
                        nonDineOrderView.onPaymentCaptured();
                    }
                } else {
                    nonDineOrderView.onPaymentCaptureFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                nonDineOrderView.onPaymentCaptureFailure();
            }

            @Override
            public void onComplete() {

            }
        });

    }





    public void clearCart() {
        cart.clearCart();
    }


}
