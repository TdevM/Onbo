package onbo.app.modules.entry;

import onbo.app.api.models.QRObjectRestaurant;
import onbo.app.base.BasePresenterMVP;

/**
 * Created by Tridev on 14-03-2018.
 */

public interface MenuEntryPresenterContract {

    interface RestaurantMenuEntryPresenter extends BasePresenterMVP<MenuEntryViewContract.RestaurantMenuEntryView>{
        void attachView(MenuEntryViewContract.RestaurantMenuEntryView view);
        void detachView();
        void clearExistingCart();
        void handleQRContent(String qrContent);
        void verifyRestaurantTableVacant(QRObjectRestaurant qrObjectRestaurant);
        void setQrScannerShown(boolean v);
    }
}