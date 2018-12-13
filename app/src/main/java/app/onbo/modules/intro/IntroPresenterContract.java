package app.onbo.modules.intro;

import app.onbo.base.BasePresenterMVP;

public interface IntroPresenterContract {


    interface SplashPresenterInterface extends BasePresenterMVP<IntroViewContract.SplashView>{
        void attachView(IntroViewContract.SplashView view);
        void detachView();
        void checkCurrentOrderDetails();
        void checkLocationVerifiedAccess();
        void fetchClosedOrder(String tOrderId);

    }
}
