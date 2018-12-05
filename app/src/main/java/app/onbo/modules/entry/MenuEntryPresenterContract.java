package app.onbo.modules.entry;

import app.onbo.api.models.QRObjectRestaurant;
import app.onbo.base.BasePresenterMVP;

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
