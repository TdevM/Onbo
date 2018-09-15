package tdevm.app_ui.modules.nondine.fragments;


import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.request.NonDineOrder;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.nondine.NonDinePresenterContract;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

public class PlaceNDOrderCashPresenter extends BasePresenter implements NonDinePresenterContract.PlaceNDOrderCashPresenter{

    public static final String TAG = PlaceNDOrderCashPresenter.class.getSimpleName();

    private APIService apiService;
    private CartHelper cartHelper;
    private AuthUtils authUtils;
    private NonDineViewContract.PlaceNDOrderCashView view;
    private CompositeDisposable compositeDisposable;

    @Inject
    public PlaceNDOrderCashPresenter(APIService apiService, CartHelper cartHelper, AuthUtils authUtils) {
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.authUtils = authUtils;
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
