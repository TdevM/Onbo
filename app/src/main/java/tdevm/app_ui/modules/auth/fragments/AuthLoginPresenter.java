package tdevm.app_ui.modules.auth.fragments;

import android.content.SharedPreferences;
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
import tdevm.app_ui.modules.auth.AuthViewContract;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 12-10-2017.
 */
//TODO Save encrypted token to shared prefs.
public class AuthLoginPresenter extends BasePresenter {

    public static final String TAG = AuthInitPresenter.class.getSimpleName();
    private APIService apiService;
    private AuthViewContract.AuthLoginView authLoginView;
    private MySharedPreferences sharedPreferences;
    private AuthUtils authUtils;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject
    public AuthLoginPresenter(APIService apiService, MySharedPreferences sharedPreferences,AuthUtils authUtils) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.sharedPreferences = sharedPreferences;
    }

    public void setView(AuthViewContract.AuthLoginView view){
        authLoginView = view;
    }
    public void loginUser(final Long phone, final String password){
        Observable<Response<Object>> observable = apiService.loginUser(new User(password,phone));
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                authLoginView.showProgressUI();
                compositeDisposable.add(d);
                Log.d(TAG,"Subscribed");
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                authLoginView.hideProgressUI();
                if(response.code() ==401){
                    Log.d(TAG,String.valueOf(response.code()));
                    authLoginView.showLoginError();
                }else if(response.code() ==200){
                    Log.d(TAG,response.body().toString());
                    authUtils.saveAuthTransaction(response.headers().get("X-auth"),phone,true);
                    authLoginView.loginSuccess();
                    authLoginView.hideProgressUI();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                authLoginView.hideProgressUI();
                authLoginView.showLoginError();
                Log.d(TAG,"Error");
            }

            @Override
            public void onComplete() {

            }
        });

    }
}
