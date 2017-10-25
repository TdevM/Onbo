package tdevm.app_ui.modules.auth.fragments;

import android.util.Log;

import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.auth.AuthViewContract;

/**
 * Created by Tridev on 12-10-2017.
 */

//TODO use disposableObserver
public class AuthInitPresenter extends BasePresenter{

    public static final String TAG = AuthInitPresenter.class.getSimpleName();

    private APIService apiService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private AuthViewContract.AuthInitView authInitView;

    public AuthInitPresenter(AuthViewContract.AuthInitView authInitView, APIService apiService) {
        this.authInitView = authInitView;
        this.apiService = apiService;
    }

    void sendOTP(final Long phone){
        Observable<Response<Object>> objectObservable = apiService.getMobileOTP(phone);

        subscribe(objectObservable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
                authInitView.showProgressUI();
                Log.d(TAG, "Subscribed");
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                authInitView.hideProgressUI();
                if(response!=null){
                    if(response.code() == 206){
                        Log.d(TAG,"data: "+response.body());
                        authInitView.showLoginFragment(phone);
                    }else if(response.code() ==200){
                        Log.d(TAG,response.body().toString());
                        authInitView.showOTPVerificationScreen(phone);
                    }
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG,"Error");
                authInitView.hideProgressUI();
                authInitView.showError();
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"complete");
            }
        });
    }

}
