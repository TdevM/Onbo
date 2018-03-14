package tdevm.app_ui.root;

import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 30-12-2017.
 */

public interface NavigationHomePresenterContract {

    interface BottomNavigationHomePresenter extends BasePresenterMVP<NavigationHomeViewContract.BottomNavigationView>{
        void attachView(NavigationHomeViewContract.BottomNavigationView view);
        void detachView();
        void handleUserAuthentication();

    }

    interface AccountsPresenter extends BasePresenterMVP<NavigationHomeViewContract.AccountsFragmentView>{
        void detachView();
        void attachView(NavigationHomeViewContract.AccountsFragmentView view);
        void fetchUser();

        void logOutUser();
    }
}
