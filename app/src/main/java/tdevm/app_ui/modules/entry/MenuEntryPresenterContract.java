package tdevm.app_ui.modules.entry;

import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 14-03-2018.
 */

public interface MenuEntryPresenterContract {

    interface RestaurantMenuEntryPresenter extends BasePresenterMVP<MenuEntryViewContract.RestaurantMenuEntryView>{
        void attachView(MenuEntryViewContract.RestaurantMenuEntryView view);
        void detachView();
        void clearExistingCart();
        void handleQRContent(String qrContent);
        void verifyRestaurantTableVacant(String tableShortId, String restaurantUUID);
    }
}
