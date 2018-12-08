package app.onbo.root;

import java.util.List;

import app.onbo.api.models.response.UserApp;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.menu.CuisineMenuItems;
import app.onbo.base.BaseView;

/**
 * Created by Tridev on 18-10-2017.
 */

public interface RootActivityViewContract {

    interface RootActivityView extends BaseView {
        void showRestaurantDetailsActivity(Restaurant restaurant);
        void showUserProfile();
        void redirectAuthActivity();
        void redirectEntryActivity();
        void onUnpaidOrdersFetched(List<FOrder> unpaidOrders);
        void noUnpaidOrders();
    }

    interface AccountsFragmentView extends BaseView {
        void onUserFetched(UserApp userApp);
        void showProgressUI();
        void hideProgressUI();
        void resolveDaggerDependencies();
        void onUserFetchFailure();
        void allowEdit(UserApp userApp);
        void onLoggedOut();
        void showNoInternetError();
        void hideNoInternetError();
        void hideBackendError();
        void showBackendError();
    }

    interface RestaurantsListView extends BaseView {
        void onRestaurantsFetched(List<Restaurant> restaurantList);
        void onRestaurantsFetchFailure();
        void showNoInternetError();
        void showBackendError();
        void hideNoInternetError();
        void hideBackendError();

    }

    interface RestaurantDetailView extends BaseView {
        void onMenuItemsFetchedV2(List<CuisineMenuItems> cuisineMenuItems);
        void onMenuItemsFetchFailure();
    }
}
