package tdevm.app_ui.modules.intro;

import tdevm.app_ui.base.BasePresenterMVP;

public class IntroPresenterContract {


    interface SplashPresenterInterface extends BasePresenterMVP<IntroViewContract.SplashView>{
        void attachView(IntroViewContract.SplashView view);
        void detachView();
        void checkCurrentOrderDetails();
    }
}
