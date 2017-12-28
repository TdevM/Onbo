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
import tdevm.app_ui.api.models.MySharedPreferences;
import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.auth.AuthPresenterContract;
import tdevm.app_ui.modules.auth.AuthViewContract;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 12-10-2017.
 */

public class AuthRegisterPresenter extends BasePresenter implements AuthPresenterContract.AuthRegisterPresenter {

    public static final String TAG = AuthRegisterPresenter.class.getSimpleName();
    private AuthViewContract.AuthRegisterView authRegisterView;
    private APIService apiService;
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;

    @Inject
    public AuthRegisterPresenter(APIService apiService,AuthUtils authUtils) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void registerUser(User user){
        Observable<Response<Object>> observable = apiService.registerUser(user);
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
                authRegisterView.showProgressUI();
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                if(response.code() == 206){
                    authRegisterView.showRegistrationError();
                    Log.d("RegisterPresenter",response.body().toString());
                }else if(response.code() ==200){
                    Log.d("RegisterPresenter",response.body().toString());
                    authUtils.saveAuthTransaction(response.headers().get("X-auth"),user.getMobile(),true);
                    authRegisterView.showRegistrationSuccess();
                }

            }

            @Override
            public void onError(@NonNull Throwable e) {
                authRegisterView.showDuplicationError(e.getMessage());
                authRegisterView.hideProgressUI();
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
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }
}
