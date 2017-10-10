package tdevm.app_ui.modules.auth;

import tdevm.app_ui.api.models.OneTimePassword;
import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 10-10-2017.
 */

// this interface consists of two inner interfaces,View and presenter for
public interface AuthContract {

    interface AuthView extends BaseView{
        void showPasswordInputFragment(Long phone);
        void showLoginError();
        void showRegisterFragment(Long phone);
    }

    interface AuthPresenter extends BasePresenter{
        void submitPhoneNumberToServer(Long phone);
        void submitLoginCredentials(User user);
        void submitSignUpData(User user);
        void sendOTP(Long phone);
    }

}
