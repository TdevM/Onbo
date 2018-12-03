package onbo.app.modules.auth.fragments;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import onbo.app.api.APIService;
import onbo.app.api.models.request.User;
import onbo.app.base.BasePresenter;
import onbo.app.modules.auth.AuthPresenterContract;
import onbo.app.modules.auth.AuthViewContract;
import onbo.app.utils.PreferenceUtils;

/**
 * Created by Tridev on 12-10-2017.
 */

public class AuthRegisterPresenter extends BasePresenter implements AuthPresenterContract.AuthRegisterPresenter {

    public static final String TAG = AuthRegisterPresenter.class.getSimpleName();
    private AuthViewContract.AuthRegisterView authRegisterView;
    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;

    @Inject
    public AuthRegisterPresenter(APIService apiService, PreferenceUtils preferenceUtils) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void registerUser(User user) {
        Observable<Response<Object>> observable = apiService.registerUser(user);
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
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

    @Override
    public void attachView(AuthViewContract.AuthRegisterView view) {
        authRegisterView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
