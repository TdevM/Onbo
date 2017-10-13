package tdevm.app_ui.modules.auth.fragments;

import android.content.SharedPreferences;
import android.util.Log;

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

/**
 * Created by Tridev on 12-10-2017.
 */
//TODO Save encrypted token to shared prefs.
public class AuthLoginPresenter extends BasePresenter {

    public static final String TAG = AuthInitPresenter.class.getSimpleName();


    //TODO use Dagger here.
    private APIService apiService;
    private AuthViewContract.AuthLoginView authLoginView;
    private MySharedPreferences sharedPreferences;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public AuthLoginPresenter(AuthViewContract.AuthLoginView authLoginView, APIService apiService, MySharedPreferences sharedPreferences) {
        this.apiService = apiService;
        this.authLoginView = authLoginView;
        this.sharedPreferences = sharedPreferences;
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
                    sharedPreferences.putDataString("TOKEN",response.headers().get("X-auth"));
                    sharedPreferences.putDataBool("LOGIN_STATE",true);
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
