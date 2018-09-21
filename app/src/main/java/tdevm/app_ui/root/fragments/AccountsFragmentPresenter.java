package tdevm.app_ui.root.fragments;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.root.NavigationHomePresenterContract;
import tdevm.app_ui.root.NavigationHomeViewContract;
import tdevm.app_ui.utils.PreferenceUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 12-02-2018.
 */

public class AccountsFragmentPresenter extends BasePresenter implements NavigationHomePresenterContract.AccountsPresenter {

    private NavigationHomeViewContract.AccountsFragmentView fragmentView;

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
    public void attachView(NavigationHomeViewContract.AccountsFragmentView view) {
       this.fragmentView = view;
    }

    @Override
    public void fetchUser() {
      Observable<Response<UserApp>> appObservable = apiService.fetchUser(preferenceUtils.getAuthLoginToken());
      subscribe(appObservable, new Observer<Response<UserApp>>() {
          @Override
          public void onSubscribe(Disposable d) {
              compositeDisposable.add(d);
          }

          @Override
          public void onNext(Response<UserApp> userAppResponse) {
              if(userAppResponse.isSuccessful()){
                  if(userAppResponse.body()!=null){
                      fragmentView.onUserFetched(userAppResponse.body());
                  }
              }else {
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
