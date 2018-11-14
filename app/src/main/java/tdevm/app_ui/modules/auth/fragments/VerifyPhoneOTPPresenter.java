package tdevm.app_ui.modules.auth.fragments;

import android.util.Log;


import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.OneTimePassword;
import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.auth.AuthPresenterContract;
import tdevm.app_ui.modules.auth.AuthViewContract;

/**
 * Created by Tridev on 12-10-2017.
 */

public class VerifyPhoneOTPPresenter extends BasePresenter implements AuthPresenterContract.VerifyPhoneOTPPresenter {

    public static final String TAG = VerifyPhoneOTPPresenter.class.getSimpleName();

    private APIService apiService;
    private AuthViewContract.AuthOTPView authOTPView;
    private CompositeDisposable compositeDisposable;

    @Inject
    public VerifyPhoneOTPPresenter(APIService apiService) {
        this.compositeDisposable = new CompositeDisposable();
        this.apiService = apiService;
    }

    @Override
    public void attachView(AuthViewContract.AuthOTPView view) {
        authOTPView = view;
    }

    @Override
    public void resendOTP(final Long phone) {
        Observable<Response<Object>> observable = apiService.getMobileOTP(phone);
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                authOTPView.showProgressUI();
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                if (response.code() == 200 || response.code() == 206) {
                    Log.d(TAG, response.body().toString());
                    authOTPView.showOTPSentSuccess();
                } else {
                    Log.d(TAG, response.errorBody().toString());
                    authOTPView.showOTPSentFailure();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                authOTPView.showOTPSentFailure();
            }

            @Override
            public void onComplete() {
                authOTPView.hideProgressUI();
            }
        });

    }

    // This method will be called in two ways, one by user input, another by a BroadcastReceiver;
    @Override
    public void verifyOTP(final Long phone, Long OTP) {
        Log.d(TAG, phone + OTP.toString());
        Observable<Response<Object>> observable = apiService.verifyMobileOTP(new OneTimePassword(OTP, phone));
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
                authOTPView.showProgressUI();
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                authOTPView.showProgressUI();
                if (response.code() == 200) {
                    //OTP Verified, show Register Fragment.
                    Log.d(TAG, response.body().toString());
                    authOTPView.showVerificationSuccessRegister(phone);
                    authOTPView.hideProgressUI();
                } else if (response.code() == 201) {
                    //OTP Verified, show Register Update Fragment.
                    Log.d(TAG, response.body().toString());
                    fetchUserPOS(phone);

                } else if (response.code() == 209) {
                    // Log.d(TAG, "Failed to verify OTP :" + response.errorBody().toString());
                    authOTPView.showVerificationFailure();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                authOTPView.showGenericError();
            }

            @Override
            public void onComplete() {
                authOTPView.hideProgressUI();
            }
        });

    }

    private void fetchUserPOS(Long phone) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", String.valueOf(phone));
        Observable<Response<UserApp>> fetchPOSUser = apiService.fetchPOSUser(map);
        subscribe(fetchPOSUser, new Observer<Response<UserApp>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                authOTPView.showProgressUI();
            }

            @Override
            public void onNext(Response<UserApp> userAppResponse) {
                if (userAppResponse.code() == 200) {
                    authOTPView.onRegisteredUserFetched(userAppResponse.body());
                } else {
                    authOTPView.showGenericError();
                }

            }

            @Override
            public void onError(Throwable e) {
                authOTPView.showGenericError();
            }

            @Override
            public void onComplete() {
                authOTPView.hideProgressUI();
            }
        });
    }

    @Override
    public void parseSMS(String sender, String body, Long phone) {
        Log.d(TAG, sender + body + phone);
        String otp = body.replaceAll("\\D", "");
        Log.d(TAG, "OTP is" + otp);
        Long otpLong = Long.parseLong(otp);
        if (otpLong != null) {
            verifyOTP(phone, otpLong);
        }

    }


    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
