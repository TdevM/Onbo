package tdevm.app_ui.modules.payment.fragments;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.payment.PaymentPresenterContract;
import tdevm.app_ui.modules.payment.PaymentViewContract;
import tdevm.app_ui.utils.AuthUtils;

public class PaymentFragmentPresenter extends BasePresenter implements PaymentPresenterContract.PaymentFragmentPresenterContract {

    public static final String TAG = PaymentFragmentPresenter.class.getSimpleName();
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;
    private PaymentViewContract.PaymentFragmentView paymentFragmentView;


    @Inject
    public PaymentFragmentPresenter(AuthUtils authUtils) {
        this.authUtils = authUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(PaymentViewContract.PaymentFragmentView view) {
        this.paymentFragmentView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
