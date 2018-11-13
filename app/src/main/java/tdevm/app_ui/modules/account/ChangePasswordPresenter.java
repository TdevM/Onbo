package tdevm.app_ui.modules.account;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.request.Password;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.utils.PreferenceUtils;

public class ChangePasswordPresenter extends BasePresenter implements AccountPresenterContract.ChangePasswordPresenter {

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;
    private AccountViewContract.ChangePasswordView changePasswordView;


    @Inject
    public ChangePasswordPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.cartHelper = cartHelper;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(AccountViewContract.ChangePasswordView view) {
        this.changePasswordView = view;

    }


    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void changeUserPassword(Password password) {
        Observable<Response<Object>> changePass = apiService.changeUserPassword("Bearer " + preferenceUtils.getAuthLoginToken(), password);
        subscribe(changePass, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                changePasswordView.showProgressUI();
            }

            @Override
            public void onNext(Response<Object> objectResponse) {
                if (objectResponse.code() == 200) {
                    changePasswordView.onPasswordChangeSuccess(objectResponse.body());
                } else if (objectResponse.code() == 210) {
                    changePasswordView.onPasswordChangeFailure(objectResponse.body());
                }
            }

            @Override
            public void onError(Throwable e) {
                changePasswordView.onPasswordChangeFailure(e);
            }

            @Override
            public void onComplete() {
                changePasswordView.hideProgressUI();
            }
        });
    }
}
