package tdevm.app_ui.root;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.RestaurantTable;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 18-10-2017.
 */

public class BottomNavigationPresenter extends BasePresenter implements NavigationHomePresenterContract.BottomNavigationHomePresenter {
    public static final String TAG = BottomNavigationPresenter.class.getSimpleName();

    private APIService apiService;
    private AuthUtils authUtils;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;

    private NavigationHomeViewContract.BottomNavigationView bottomNavigationView;

    @Inject
    public BottomNavigationPresenter(APIService apiService, AuthUtils authUtils, CartHelper cartHelper) {
        this.authUtils = authUtils;
        this.cartHelper = cartHelper;
        this.compositeDisposable = new CompositeDisposable();
        this.apiService = apiService;
    }


    @Override
    public void handleUserAuthentication() {
        if (authUtils.getAuthLoginState()) {
            bottomNavigationView.showUserProfile();
        } else {
            bottomNavigationView.redirectAuthActivity();
        }
    }

    @Override
    public void attachView(NavigationHomeViewContract.BottomNavigationView view) {
        bottomNavigationView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    public void verifyUserAuthentication() {
        if (authUtils.getAuthLoginState()) {
            bottomNavigationView.redirectEntryActivity();
        } else {
            bottomNavigationView.redirectAuthActivity();
        }
    }
}
