package app.onbo.modules.entry;

import app.onbo.api.models.QRDataRestaurant;
import app.onbo.api.models.QRObject;
import app.onbo.base.BasePresenterMVP;

/**
 * Created by Tridev on 14-03-2018.
 */

public interface MenuEntryPresenterContract {

    interface RestaurantMenuEntryPresenter extends BasePresenterMVP<MenuEntryViewContract.RestaurantMenuEntryView> {
        void attachView(MenuEntryViewContract.RestaurantMenuEntryView view);

        void detachView();

        void clearExistingCart();

        void handleQRContent(String qrContent);

        void verifyRestaurantTableVacant(QRObject qrObject, QRDataRestaurant qrDataRestaurant);

        void setQrScannerShown(boolean v);

        void fetchFoodCourt(String foodCourtUUID);
    }
}
