package tdevm.app_ui.root;

import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 30-12-2017.
 */

public interface RootActivityPresenterContract {

    interface BottomNavigationHomePresenter extends BasePresenterMVP<RooActivityViewContract.RootActivityView> {
        void attachView(RooActivityViewContract.RootActivityView view);

        void detachView();

        void handleUserAuthentication();

    }

    interface AccountsPresenter extends BasePresenterMVP<RooActivityViewContract.AccountsFragmentView> {
        void detachView();

        void attachView(RooActivityViewContract.AccountsFragmentView view);

        void fetchUser();

        void fetchUserEdit();

        void logOutUser();
    }

    interface RestaurantListFragmentPresenter extends BasePresenterMVP<RooActivityViewContract.RestaurantsListView> {
        void fetchRestaurants(String cityId);

        void detachView();

        void attachView(RooActivityViewContract.RestaurantsListView view);
    }

    interface RestaurantDetailPresenter extends BasePresenterMVP<RooActivityViewContract.RestaurantDetailView> {
        void fetchMenuItems(Restaurant restaurant);
    }
}
