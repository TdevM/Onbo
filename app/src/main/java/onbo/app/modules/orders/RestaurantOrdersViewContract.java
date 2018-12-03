package onbo.app.modules.orders;

import java.util.List;

import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.base.BaseView;

public interface RestaurantOrdersViewContract {

    interface RestaurantOrdersActivityView extends BaseView{

    }

    interface MyOrdersFragmentView extends BaseView{
        void onMyOrderFetched(List<FOrder> fOrders);
        void onMyOrdersEmpty();
        void onFetchingOrdersFailure();
    }

    interface MyOrderDetailView extends BaseView{

    }
}
