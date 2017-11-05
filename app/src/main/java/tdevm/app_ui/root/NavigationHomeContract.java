package tdevm.app_ui.root;

/**
 * Created by Tridev on 18-10-2017.
 */

public interface NavigationHomeContract {

    interface BottomNavigationView{
        void showTableOccupiedError();
        void redirectDineInActivity(String restaurantUUID);
    }
}
