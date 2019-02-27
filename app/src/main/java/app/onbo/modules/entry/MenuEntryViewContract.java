package app.onbo.modules.entry;

import app.onbo.api.models.RemoteConfig;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.base.BaseView;

/**
 * Created by Tridev on 14-03-2018.
 */

public interface MenuEntryViewContract {

    interface RestaurantMenuEntryView extends BaseView {
        void redirectDineInActivity(Restaurant restaurant);

        void redirectNonDineActivity(Restaurant restaurant);

        void stopLocationUpdates();

        void showGettingMenu();

        void startQRScanner();

        void onLocationVerifiedAccessFetched(RemoteConfig config);

        void onLocationVerifiedAccessFailure();

        void showTableOccupiedError();

        void showMalformedQRCode();

        void showFCActivity(FoodCourt foodCourt);


        void showMalformedQRCode2();

        void showTableOccupiedError2();

        void showWrongLocationError();
    }
}
