package tdevm.app_ui.modules.entry;

import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 14-03-2018.
 */

public interface MenuEntryViewContract {

    interface RestaurantMenuEntryView extends BaseView {
        void redirectDineInActivity();
        void redirectNonDineActivity();
        void showTableOccupiedError();
        void startQRScanner();

        void stopLocationUpdates();
    }
}
