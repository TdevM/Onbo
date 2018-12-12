package app.onbo.modules.intro;

import app.onbo.base.BasePresenterMVP;

public class IntroPresenterContract {


    interface SplashPresenterInterface extends BasePresenterMVP<IntroViewContract.SplashView>{
        void attachView(IntroViewContract.SplashView view);
        void detachView();
        void checkCurrentOrderDetails();
        void checkLocationVerifiedAccess();
        void fetchClosedOrder(String tOrderId);
    }
}
