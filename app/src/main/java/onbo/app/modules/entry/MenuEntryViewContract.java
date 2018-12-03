package onbo.app.modules.entry;

import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.base.BaseView;

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

        void showTableOccupiedError();
        void showMalformedQRCode();
        void showWrongLocationError();
    }
}
