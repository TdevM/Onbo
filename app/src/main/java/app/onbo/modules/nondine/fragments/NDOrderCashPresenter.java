package app.onbo.modules.nondine.fragments;


import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import app.onbo.api.APIService;
import app.onbo.base.BasePresenter;
import app.onbo.modules.nondine.NonDinePresenterContract;
import app.onbo.modules.nondine.NonDineViewContract;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

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
