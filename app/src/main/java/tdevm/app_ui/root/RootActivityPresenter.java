package tdevm.app_ui.root;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.PreferenceUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 18-10-2017.
 */

public class RootActivityPresenter extends BasePresenter implements NavigationHomePresenterContract.BottomNavigationHomePresenter {
    public static final String TAG = RootActivityPresenter.class.getSimpleName();

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;

    private NavigationHomeViewContract.RootActivityView rootActivityView;

    @Inject
    public RootActivityPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.preferenceUtils = preferenceUtils;
        this.cartHelper = cartHelper;
        this.compositeDisposable = new CompositeDisposable();
        this.apiService = apiService;
    }


    @Override
    public void handleUserAuthentication() {
        if (preferenceUtils.getAuthLoginState()) {
            rootActivityView.showUserProfile();
        } else {
            rootActivityView.redirectAuthActivity();
        }
    }

    @Override
    public void attachView(NavigationHomeViewContract.RootActivityView view) {
        rootActivityView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    public void verifyUserAuthentication() {
        if (preferenceUtils.getAuthLoginState()) {
            rootActivityView.redirectEntryActivity();
        } else {
            rootActivityView.redirectAuthActivity();
        }
    }
}
