package tdevm.app_ui.modules.orders;

import java.util.List;

import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.base.BaseView;

public interface RestaurantOrdersViewContract {

    interface RestaurantOrdersActivityView extends BaseView{

    }

    interface MyOrdersFragmentView extends BaseView{
        void onMyOrderFetched(List<FOrder> fOrders);
        void onMyOrdersEmpty();
        void onFetchingOrdersFailure();
    }

    interface MyOrderDetailFragmentView extends BaseView{

    }
}
