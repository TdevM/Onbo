package tdevm.app_ui.modules.nondine;

import tdevm.app_ui.api.models.response.v2.FOrder.Checkout;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 02-02-2018.
 */

public interface NonDineViewContract {

    interface NonDineActivityView{

    }

    interface NonDineRestaurantDetailsView extends BaseView{
        void onRestaurantDetailsFetched(Restaurant restaurant);
    }

    interface InitNonDineOrderView extends BaseView{
        void onNDCashOrderCreated(FOrder fOrder);
        void onOrderCreationFailure();
    }

    interface NonDineCheckoutView extends BaseView{
        void onCheckoutDataFetched(Checkout checkout);
        void onCheckoutResponseFailure();
    }

    interface OrderPaymentTypeView extends BaseView{

    }

    interface PlaceNDOrderCashView extends BaseView{

    }
}
