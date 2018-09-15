package tdevm.app_ui.modules.nondinein.activities;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.nondinein.NonDinePresenterContract;
import tdevm.app_ui.modules.nondinein.NonDineViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

public class InitNonDineOrderPresenter extends BasePresenter implements NonDinePresenterContract.InitNonDineOrderPresenter{

    public static final String TAG = InitNonDineOrderPresenter.class.getSimpleName();

    private APIService apiService;
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;
    private CartHelper cart;

    private NonDineViewContract.InitNonDineOrderView nonDineOrderView;


    @Inject
    public InitNonDineOrderPresenter(APIService apiService, AuthUtils authUtils, CartHelper cart) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.cart = cart;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(NonDineViewContract.InitNonDineOrderView view) {
        this.nonDineOrderView = view;
        Log.d(TAG,"Attach view called for presenter");
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

}
