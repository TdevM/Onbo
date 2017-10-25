package tdevm.app_ui.modules.auth;

import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 12-10-2017.
 */

public interface AuthViewContract {

    interface AuthInitView  extends BaseView {
        //User registered already
        void showLoginFragment(Long phone);
        //Send OTP to new User.
        void showOTPVerificationScreen(Long phone);
        //Wrong an error message.
        void showError();

    }

    interface AuthLoginView extends BaseView{
        void showLoginError();
        void loginSuccess();

    }

    interface AuthRegisterView extends BaseView{
        void showRegistrationError();
        void showRegistrationSuccess();
        void showDuplicationError(String message);
    }

    interface AuthOTPView extends BaseView{
        void showOTPSentSuccess();
        void showOTPSentFailure();
        void showVerificationFailure();
        void showVerificationSuccess(Long phone);
    }

}