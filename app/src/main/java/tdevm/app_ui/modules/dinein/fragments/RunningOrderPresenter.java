package tdevm.app_ui.modules.dinein.fragments;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 08-02-2018.
 */

public class RunningOrderPresenter extends BasePresenter implements DineInPresenterContract.RunningOrderFragmentPresenter {

    private DineInViewContract.RunningOrderView view;
    private CompositeDisposable compositeDisposable;
    private AuthUtils authUtils;
    private CartHelper cartHelper;
    private APIService apiService;

    public RunningOrderPresenter(AuthUtils authUtils, CartHelper cartHelper, APIService apiService) {
        this.compositeDisposable = new CompositeDisposable();
        this.authUtils = authUtils;
        this.cartHelper = cartHelper;
        this.apiService = apiService;
    }


    public void fetchTempRunningOrder(){

    }


    @Override
    public void attachView(DineInViewContract.RunningOrderView runningOrderView) {
        this.view = runningOrderView;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
