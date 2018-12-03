package onbo.app.modules.intro;

import onbo.app.base.BasePresenterMVP;

public class IntroPresenterContract {


    interface SplashPresenterInterface extends BasePresenterMVP<IntroViewContract.SplashView>{
        void attachView(IntroViewContract.SplashView view);
        void detachView();
        void checkCurrentOrderDetails();
        void fetchClosedOrder(String tOrderId);
    }
}
