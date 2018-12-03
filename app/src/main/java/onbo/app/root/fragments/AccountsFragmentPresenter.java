package onbo.app.root.fragments;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import onbo.app.api.APIService;
import onbo.app.api.models.response.UserApp;
import onbo.app.base.BasePresenter;
import onbo.app.root.RootActivityPresenterContract;
import onbo.app.root.RootActivityViewContract;
import onbo.app.utils.PreferenceUtils;
import onbo.app.utils.CartHelper;

/**
 * Created by Tridev on 12-02-2018.
 */

public class AccountsFragmentPresenter extends BasePresenter implements RootActivityPresenterContract.AccountsPresenter {

    private RootActivityViewContract.AccountsFragmentView fragmentView;

    private PreferenceUtils preferenceUtils;
    private APIService apiService;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;

    @Inject
    public AccountsFragmentPresenter(PreferenceUtils preferenceUtils, APIService apiService, CartHelper cartHelper) {
        this.preferenceUtils = preferenceUtils;
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void attachView(RootActivityViewContract.AccountsFragmentView view) {
        this.fragmentView = view;
    }

    @Override
    public void fetchUser() {
        Observable<Response<UserApp>> appObservable = apiService.fetchUser("Bearer " + preferenceUtils.getAuthLoginToken());
        subscribe(appObservable, new Observer<Response<UserApp>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                fragmentView.showProgressUI();
            }

            @Override
            public void onNext(Response<UserApp> userAppResponse) {
                if (userAppResponse.isSuccessful()) {
                    if (userAppResponse.body() != null) {
                        fragmentView.onUserFetched(userAppResponse.body());
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
                fragmentView.hideProgressUI();
            }
        });
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
