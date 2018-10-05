package tdevm.app_ui.root;

import java.util.List;

import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 18-10-2017.
 */

public interface NavigationHomeViewContract {

    interface RootActivityView extends BaseView {
        void showRestaurantDetailsActivity(Restaurant restaurant);
        void showUserProfile();
        void redirectAuthActivity();
        void redirectEntryActivity();
    }

    interface AccountsFragmentView extends BaseView {
        void onUserFetched(UserApp userApp);
        void showProgressUI();
        void hideProgressUI();
        void resolveDaggerDependencies();
        void onUserFetchFailure();
        void allowEdit(UserApp userApp);
        void onLoggedOut();
    }

    interface RestaurantsListView extends BaseView {
        void onRestaurantsFetched(List<Restaurant> restaurantList);
        void onRestaurantsFetchFailure();

    }
}
