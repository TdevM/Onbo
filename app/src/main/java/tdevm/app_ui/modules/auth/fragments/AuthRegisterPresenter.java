package tdevm.app_ui.modules.auth.fragments;

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

public class AuthRegisterPresenter extends BasePresenter {

    public static final String TAG = AuthRegisterPresenter.class.getSimpleName();
    private AuthViewContract.AuthRegisterView authRegisterView;
    private APIService apiService;
    private MySharedPreferences mySharedPreferences;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    public AuthRegisterPresenter(AuthViewContract.AuthRegisterView authRegisterView, APIService apiService, MySharedPreferences mySharedPreferences) {
        this.authRegisterView = authRegisterView;
        this.apiService = apiService;
        this.mySharedPreferences = mySharedPreferences;
    }

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
}
