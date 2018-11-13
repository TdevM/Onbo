package tdevm.app_ui.modules.auth.fragments;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.auth.AuthPresenterContract;
import tdevm.app_ui.modules.auth.AuthViewContract;
import tdevm.app_ui.utils.PreferenceUtils;

public class AuthRegisterUpdatePresenter extends BasePresenter implements AuthPresenterContract.AuthRegisterUpdatePresenterContract {


    public static final String TAG = AuthRegisterUpdatePresenter.class.getSimpleName();

    private AuthViewContract.AuthRegisterUpdateView authRegisterView;
    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;

    @Inject
    public AuthRegisterUpdatePresenter(APIService apiService, PreferenceUtils preferenceUtils) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(AuthViewContract.AuthRegisterUpdateView view) {
        this.authRegisterView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void registerUserUpdate(User user) {
        Observable<Response<Object>> registerUserUpdate = apiService.registerUserUpdate(user);
        subscribe(registerUserUpdate, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                authRegisterView.showProgressUI();
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                if (response.code() == 209) {
                    authRegisterView.showGenericError();
                    Log.d("RegisterPresenter", response.body().toString());
                } else if (response.code() == 200) {
                    Log.d("RegisterPresenter", response.body().toString());
                    preferenceUtils.saveAuthTransaction(response.headers().get("Authorization"), user.getMobile(), true);
                    authRegisterView.showRegistrationSuccess();
                } else if (response.code() == 211) {
                    authRegisterView.emailInUseError();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                authRegisterView.showGenericError();
            }

            @Override
            public void onComplete() {
                authRegisterView.hideProgressUI();
            }
        });
    }
}
