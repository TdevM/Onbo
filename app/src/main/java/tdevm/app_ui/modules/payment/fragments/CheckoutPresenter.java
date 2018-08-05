package tdevm.app_ui.modules.payment.fragments;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.payment.PaymentPresenterContract;
import tdevm.app_ui.modules.payment.PaymentViewContract;
import tdevm.app_ui.utils.AuthUtils;

public class CheckoutPresenter extends BasePresenter implements PaymentPresenterContract.CheckoutPresenterContract {

    public static final String TAG = CheckoutPresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;

    private AuthUtils authUtils;
    private PaymentViewContract.CheckoutFragmentView view;

    @Inject
    public CheckoutPresenter(AuthUtils authUtils) {
        this.authUtils = authUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void closeRunningOrder() {

    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void attachView(PaymentViewContract.CheckoutFragmentView view) {
        this.view = view;
    }



}
