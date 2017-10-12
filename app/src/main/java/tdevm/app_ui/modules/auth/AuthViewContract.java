package tdevm.app_ui.modules.auth;

import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 12-10-2017.
 */

public interface AuthViewContract {

    public interface AuthInitView  extends BaseView {
        //User registered already
        void showLoginFragment(Long phone);
        //Send OTP to new User.
        void showOTPVerificationScreen(Long phone);
        //Wrong an error message.
        void showError();

    }

    public interface AuthLoginView extends BaseView{
        void showLoginError();
        void loginSuccess();

    }

    public interface AuthRegisterView extends BaseView{

    }

    public interface AuthOTPView extends BaseView{

    }

}
