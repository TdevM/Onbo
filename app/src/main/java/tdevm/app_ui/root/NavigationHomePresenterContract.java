package tdevm.app_ui.root;

import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 30-12-2017.
 */

public interface NavigationHomePresenterContract {

    interface BottomNavigationHomePresenter extends BasePresenterMVP<NavigationHomeViewContract.RootActivityView>{
        void attachView(NavigationHomeViewContract.RootActivityView view);
        void detachView();
        void handleUserAuthentication();

    }

    interface AccountsPresenter extends BasePresenterMVP<NavigationHomeViewContract.AccountsFragmentView>{
        void detachView();
        void attachView(NavigationHomeViewContract.AccountsFragmentView view);
        void fetchUser();
        void fetchUserEdit();
        void logOutUser();
    }

    interface RestaurantListFragmentPresenter extends BasePresenterMVP<NavigationHomeViewContract.RestaurantsListView>{
        void fetchRestaurants(String cityId);
        void detachView();
        void attachView(NavigationHomeViewContract.RestaurantsListView view);
    }
}
