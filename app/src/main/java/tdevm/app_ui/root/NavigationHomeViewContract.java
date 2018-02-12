package tdevm.app_ui.root;

import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 18-10-2017.
 */

public interface NavigationHomeViewContract {

    interface BottomNavigationView extends BaseView{
        void showTableOccupiedError();
        void redirectDineInActivity(String restaurantUUID);
        void redirectNonDineActivity();
        void showUserProfile();
        void redirectAuthActivity();
        void startQRScanner();
    }

    interface AccountsFragmentView extends BaseView{
        void onUserFetched(UserApp userApp);
        void showProgressUI();
        void hideProgressUI();
        void resolveDaggerDependencies();
    }
}
