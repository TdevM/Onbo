package tdevm.app_ui.modules.nondinein.fragments;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.base.BasePresenterMVP;
import tdevm.app_ui.modules.nondinein.NonDinePresenterContract;
import tdevm.app_ui.modules.nondinein.NonDineViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

public class NonDineOrderSummaryPresenter extends BasePresenter implements NonDinePresenterContract.NonDineSummaryPresenter{

    public static final String TAG = NonDineOrderSummaryPresenter.class.getSimpleName();

    private APIService apiService;
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;
    private CartHelper cart;
    private NonDineViewContract.NonDineOrderSummaryView summaryView;


    @Inject
    public NonDineOrderSummaryPresenter(APIService apiService, AuthUtils authUtils, CartHelper cart) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.compositeDisposable = new CompositeDisposable();
        this.cart = cart;
    }

    @Override
    public void attachView(NonDineViewContract.NonDineOrderSummaryView view) {
        this.summaryView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void test() {
        summaryView.showProgressUI();
    }
}
