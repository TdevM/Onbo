package app.onbo.root;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.base.BasePresenter;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

/**
 * Created by Tridev on 18-10-2017.
 */

public class RootActivityPresenter extends BasePresenter implements RootActivityPresenterContract.BottomNavigationHomePresenter {
    public static final String TAG = RootActivityPresenter.class.getSimpleName();

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;

    private RootActivityViewContract.RootActivityView rootActivityView;

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

    public void fetchUnpaidOrders() {
        Observable<Response<List<FOrder>>> fetchUnpaidOrders = apiService.fetchUnpaidOrders();
        subscribe(fetchUnpaidOrders, new Observer<Response<List<FOrder>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<List<FOrder>> listResponse) {
                if (listResponse.code() == 200) {
                    rootActivityView.onUnpaidOrdersFetched(listResponse.body());
                } else if (listResponse.code() == 404) {
                    rootActivityView.noUnpaidOrders();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    @Override
    public void attachView(RootActivityViewContract.RootActivityView view) {
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

    public void logoutUser() {
        preferenceUtils.clearAuth();
    }
}
