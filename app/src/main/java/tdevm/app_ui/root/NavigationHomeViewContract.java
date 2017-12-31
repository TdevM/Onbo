package tdevm.app_ui.root;

import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 18-10-2017.
 */

public interface NavigationHomeViewContract {

    interface BottomNavigationView extends BaseView{
        void showTableOccupiedError();
        void redirectDineInActivity(String restaurantUUID);
        void showUserProfile();
        void redirectAuthActivity();
        void startQRScanner();
    }
}
