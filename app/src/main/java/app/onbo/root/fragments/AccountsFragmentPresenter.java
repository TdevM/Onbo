package app.onbo.root.fragments;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.response.UserApp;
import app.onbo.base.BasePresenter;
import app.onbo.root.RootActivityPresenterContract;
import app.onbo.root.RootActivityViewContract;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

/**
 * Created by Tridev on 12-02-2018.
 */

public class AccountsFragmentPresenter extends BasePresenter implements RootActivityPresenterContract.AccountsPresenter {

    private RootActivityViewContract.AccountsFragmentView fragmentView;

    private PreferenceUtils preferenceUtils;
    private APIService apiService;
    private CartHelper cartHelper;
    private Application context;
    private CompositeDisposable compositeDisposable;

    @Inject
    public AccountsFragmentPresenter(PreferenceUtils preferenceUtils, APIService apiService, Application c, CartHelper cartHelper) {
        this.preferenceUtils = preferenceUtils;
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.context = c;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void attachView(RootActivityViewContract.AccountsFragmentView view) {
        this.fragmentView = view;
    }

    @Override
    public void fetchUser() {

        if (isConnectedToInternet()) {
            Observable<Response<UserApp>> appObservable = apiService.fetchUser("Bearer " + preferenceUtils.getAuthLoginToken());
            subscribe(appObservable, new Observer<Response<UserApp>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                    fragmentView.showProgressUI();
                }

                @Override
                public void onNext(Response<UserApp> userAppResponse) {
                    if (userAppResponse.code() == 200) {
                        if (userAppResponse.body() != null) {
                            fragmentView.onUserFetched(userAppResponse.body());
                        }
                    } else {
                        fragmentView.showBackendError();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    fragmentView.showBackendError();
                }

                @Override
                public void onComplete() {
                    fragmentView.hideProgressUI();
                }
            });
        } else {
            fragmentView.showNoInternetError();
        }
    }


    @Override
    public void fetchUserEdit() {
        Observable<Response<UserApp>> appObservable = apiService.fetchUser("Bearer " + preferenceUtils.getAuthLoginToken());
        subscribe(appObservable, new Observer<Response<UserApp>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<UserApp> userAppResponse) {
                if (userAppResponse.isSuccessful()) {
                    if (userAppResponse.body() != null) {
                        fragmentView.allowEdit(userAppResponse.body());
                    }
                } else {
                    fragmentView.onUserFetchFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                fragmentView.onUserFetchFailure();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }


    @Override
    public void logOutUser() {
        fragmentView.onLoggedOut();
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
