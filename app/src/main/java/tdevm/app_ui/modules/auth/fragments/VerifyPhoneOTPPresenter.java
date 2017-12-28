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
import tdevm.app_ui.api.models.OneTimePassword;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.auth.AuthPresenterContract;
import tdevm.app_ui.modules.auth.AuthViewContract;

/**
 * Created by Tridev on 12-10-2017.
 */

public class VerifyPhoneOTPPresenter extends BasePresenter implements AuthPresenterContract.VerifyPhoneOTPPresenter{

    public static final String TAG = VerifyPhoneOTPPresenter.class.getSimpleName();

    private APIService apiService;
    private AuthViewContract.AuthOTPView authOTPView;
    private CompositeDisposable compositeDisposable;

    @Inject
    public VerifyPhoneOTPPresenter(APIService apiService) {
        compositeDisposable = new CompositeDisposable();
        this.apiService =apiService;
    }

    @Override
    public void attachView(AuthViewContract.AuthOTPView view) {
      authOTPView = view;
    }

    @Override
    public void resendOTP(final Long phone){
        Observable<Response<Object>> observable = apiService.getMobileOTP(phone);
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                authOTPView.showProgressUI();
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                if(response.code() ==200){
                    Log.d(TAG,response.body().toString());
                    authOTPView.showOTPSentSuccess();
                }else {
                    Log.d(TAG,response.errorBody().toString());
                    authOTPView.showOTPSentFailure();
                }
            }
            @Override
            public void onError(@NonNull Throwable e) {
                authOTPView.showOTPSentFailure();
            }

            @Override
            public void onComplete() {

            }
        });

    }

    // This method will be called in two ways, one by user input, another by a BroadcastReceiver;
    @Override
    public void verifyOTP(final Long phone, Long OTP){
        Log.d(TAG,phone+OTP.toString());
        Observable<Response<Object>> observable = apiService.verifyMobileOTP(new OneTimePassword(OTP,phone));
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
              compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                if(response.code() ==200){
                    //OTP Verified, show Register Fragment.
                    Log.d(TAG,response.body().toString());
                    authOTPView.showVerificationSuccess(phone);
                }else if (response.code() ==401){
                    Log.d(TAG,"Failed to verify OTP :"+response.errorBody().toString());
                    authOTPView.showVerificationFailure();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    @Override
    public void parseSMS(String sender, String body, Long phone){
        Log.d(TAG,sender+body+phone);
        String otp = body.replaceAll("\\D", "");
        Log.d(TAG,"OTP is"+ otp);
        verifyOTP(phone, Long.parseLong(otp));
    }



    @Override
    public void detachView() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
