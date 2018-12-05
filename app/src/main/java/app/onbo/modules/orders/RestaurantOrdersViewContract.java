package app.onbo.modules.orders;

import java.util.List;

import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.base.BaseView;

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
