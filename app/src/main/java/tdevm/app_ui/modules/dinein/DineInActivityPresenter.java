package tdevm.app_ui.modules.dinein;

import android.util.Log;

import java.util.List;

import javax.inject.Inject;

import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 31-01-2018.
 */

public class DineInActivityPresenter extends BasePresenter implements DineInPresenterContract.DineInActivityPresenter {


    public static final String TAG = DineInActivityPresenter.class.getSimpleName();
    private DineInViewContract.DineInActivity dineInActivityView;
    private APIService apiService;
    private AuthUtils authUtils;
    private CartHelper cartHelper;

    @Inject
    public DineInActivityPresenter( APIService apiService, AuthUtils authUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.cartHelper = cartHelper;
    }


    @Override
    public void attachView(DineInViewContract.DineInActivity view) {
        this.dineInActivityView = view;
    }

    @Override
    public void detachView() {

    }
}
