package tdevm.app_ui.modules.nondine.fragments;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.nondine.NonDinePresenterContract;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.utils.PreferenceUtils;
import tdevm.app_ui.utils.CartHelper;

public class NDOrderCashPresenter extends BasePresenter implements NonDinePresenterContract.PlaceNDOrderCashPresenter{

    public static final String TAG = NDOrderCashPresenter.class.getSimpleName();

    private APIService apiService;
    private CartHelper cartHelper;
    private PreferenceUtils preferenceUtils;
    private NonDineViewContract.PlaceNDOrderCashView view;
    private CompositeDisposable compositeDisposable;

    @Inject
    public NDOrderCashPresenter(APIService apiService, CartHelper cartHelper, PreferenceUtils preferenceUtils) {
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void attachView(NonDineViewContract.PlaceNDOrderCashView placeNDOrderCashView) {
        this.view = placeNDOrderCashView;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }




}
