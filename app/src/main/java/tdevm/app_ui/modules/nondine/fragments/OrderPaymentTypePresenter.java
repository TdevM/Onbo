package tdevm.app_ui.modules.nondine.fragments;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.nondine.NonDinePresenterContract;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

public class OrderPaymentTypePresenter extends BasePresenter implements NonDinePresenterContract.OrderPaymentTypePresenter{


    public static final String TAG = OrderPaymentTypePresenter.class.getSimpleName();

    private APIService apiService;
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;
    private CartHelper cart;

    private NonDineViewContract.OrderPaymentTypeView paymentTypeView;

    @Inject
    public OrderPaymentTypePresenter(APIService apiService, AuthUtils authUtils, CartHelper cart) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.cart = cart;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(NonDineViewContract.OrderPaymentTypeView view) {
        this.paymentTypeView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
