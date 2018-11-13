package tdevm.app_ui.modules.auth;

import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 12-10-2017.
 */

public interface AuthViewContract {

    interface AuthInitView extends BaseView {
        //User registered already
        void showLoginFragment(Long phone);

        //Send OTP to new User.
        void showOTPVerificationScreen(Long phone);

        void showError();

    }

    interface AuthLoginView extends BaseView {
        void showLoginError();

        void loginSuccess();

        void showGenericError();

        void onDineOrderRunning(TOrder tOrder);

        void onNoDineOrderRunning();

        void onOrderFetchFailure();

        void onFOrderFetched(FOrder fOrder);

        void onFOrderFetchFailure();

    }

    interface AuthRegisterView extends BaseView {
        void showGenericError();

        void emailInUseError();

        void showRegistrationSuccess();

    }

    interface AuthOTPView extends BaseView {
        void showOTPSentSuccess();

        void showOTPSentFailure();

        void showVerificationFailure();

        void showVerificationSuccessRegister(Long phone);

        void onRegisteredUserFetched(UserApp userApp);

        void showGenericError();
    }

}
