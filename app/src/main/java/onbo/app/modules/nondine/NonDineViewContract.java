package onbo.app.modules.nondine;

import onbo.app.api.models.response.v2.FOrder.Checkout;
import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.base.BaseView;

/**
 * Created by Tridev on 02-02-2018.
 */

public interface NonDineViewContract {

    interface NonDineActivityView extends BaseView{

    }

    interface NonDineRestaurantDetailsView extends BaseView{
        void onRestaurantDetailsFetched(Restaurant restaurant);
    }

    interface InitNonDineOrderView extends BaseView{
        void onPaymentCaptured();
        void onPaymentCaptureFailure();
        void showPaymentCaptureProgressUI();

        void hidePaymentCaptureProgressUI();

    }

    interface NonDineCheckoutView extends BaseView{
        void onCheckoutDataFetched(Checkout checkout);
        void onCheckoutResponseFailure();
    }

    interface OrderPaymentTypeView extends BaseView{
        void onNDCashOrderCreated(FOrder fOrder);
        void onNDPaidOrderCreated(FOrder fOrder);
        void onOrderCreationFailure();
    }

    interface PlaceNDOrderCashView extends BaseView{

    }

    interface DigitalPaymentOptionView extends BaseView{
        void showProgressUI();
        void hideProgressUI();
        void onNDPaidOrderCreated(FOrder fOrder);
        void onOrderCreationFailure();
    }


}
