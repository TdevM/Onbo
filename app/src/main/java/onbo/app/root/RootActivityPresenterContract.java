package onbo.app.root;

import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.base.BasePresenterMVP;

/**
 * Created by Tridev on 30-12-2017.
 */

public interface RootActivityPresenterContract {

    interface BottomNavigationHomePresenter extends BasePresenterMVP<RootActivityViewContract.RootActivityView> {
        void attachView(RootActivityViewContract.RootActivityView view);

        void detachView();

        void handleUserAuthentication();

    }

    interface AccountsPresenter extends BasePresenterMVP<RootActivityViewContract.AccountsFragmentView> {
        void detachView();

        void attachView(RootActivityViewContract.AccountsFragmentView view);

        void fetchUser();

        void fetchUserEdit();

        void logOutUser();
    }

    interface RestaurantListFragmentPresenter extends BasePresenterMVP<RootActivityViewContract.RestaurantsListView> {
        void fetchRestaurants(String cityId);

        void detachView();

        void attachView(RootActivityViewContract.RestaurantsListView view);
    }

    interface RestaurantDetailPresenter extends BasePresenterMVP<RootActivityViewContract.RestaurantDetailView> {
        void fetchMenuItems(Restaurant restaurant);
    }
}
