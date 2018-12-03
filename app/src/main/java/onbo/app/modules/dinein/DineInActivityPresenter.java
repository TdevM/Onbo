package onbo.app.modules.dinein;

import javax.inject.Inject;

import onbo.app.api.APIService;
import onbo.app.base.BasePresenter;
import onbo.app.utils.PreferenceUtils;
import onbo.app.utils.CartHelper;

/**
 * Created by Tridev on 31-01-2018.
 */

public class DineInActivityPresenter extends BasePresenter implements DineInPresenterContract.DineInActivityPresenter {


    public static final String TAG = DineInActivityPresenter.class.getSimpleName();
    private DineInViewContract.DineInActivity dineInActivityView;
    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;

    @Inject
    public DineInActivityPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.cartHelper = cartHelper;
    }



    public Integer getCartItemsCount() {
        return cartHelper.getCartTotalItems();
    }


    @Override
    public void attachView(DineInViewContract.DineInActivity view) {
        this.dineInActivityView = view;
    }

    @Override
    public void detachView() {

    }
}
