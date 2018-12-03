package onbo.app.modules.auth;

import onbo.app.api.models.request.User;
import onbo.app.base.BasePresenterMVP;

/**
 * Created by Tridev on 20-12-2017.
 */

public interface AuthPresenterContract {

    interface AuthInitPresenter extends BasePresenterMVP<AuthViewContract.AuthInitView> {
        void attachView(AuthViewContract.AuthInitView view);

        void detachView();

        void sendOTP(final Long phone);
    }

    interface AuthLoginPresenter extends BasePresenterMVP<AuthViewContract.AuthLoginView> {
        void attachView(AuthViewContract.AuthLoginView view);

        void detachView();

        void loginUser(final Long phone, final String password);

        void checkCurrentOrderDetails();

        void fetchClosedOrder(String tOrderId);
    }

    interface AuthRegisterPresenter extends BasePresenterMVP<AuthViewContract.AuthRegisterView> {
        void attachView(AuthViewContract.AuthRegisterView view);

        void detachView();

        void registerUser(User user);

    }

    interface VerifyPhoneOTPPresenter extends BasePresenterMVP<AuthViewContract.AuthOTPView> {
        void attachView(AuthViewContract.AuthOTPView view);

        void detachView();

        void resendOTP(final Long phone);

        void verifyOTP(final Long phone, Long OTP);

        void parseSMS(String sender, String body, Long phone);
    }

    interface AuthRegisterUpdatePresenterContract extends BasePresenterMVP<AuthViewContract.AuthRegisterUpdateView> {
        @Override
        void attachView(AuthViewContract.AuthRegisterUpdateView view);

        @Override
        void detachView();

        void registerUserUpdate(User user);
    }

}
