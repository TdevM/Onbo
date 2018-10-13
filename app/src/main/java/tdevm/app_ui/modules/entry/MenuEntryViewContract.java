package tdevm.app_ui.modules.entry;

import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.base.BaseView;

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
