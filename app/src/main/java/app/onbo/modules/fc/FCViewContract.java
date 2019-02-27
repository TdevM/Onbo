package app.onbo.modules.fc;

import java.util.List;

import app.onbo.api.models.response.v2.FcRestaurant;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.menu.CuisineMenuItems;
import app.onbo.base.BaseView;

public interface FCViewContract {

    interface FCActivityView extends BaseView{
        void onFCFetched(List<FcRestaurant> fcRestaurants);
        void onFCFetchFailure();
    }

    interface FCPremiseDetailView extends BaseView{
        void onMenuItemsFetchedV2(List<CuisineMenuItems> cuisineMenuItems);
        void onMenuItemsFetchFailure();
        void redirectNonDineActivity(Restaurant restaurant);
    }
}
