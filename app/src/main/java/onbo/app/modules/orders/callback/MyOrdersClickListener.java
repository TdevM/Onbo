package onbo.app.modules.orders.callback;

import onbo.app.api.models.response.v2.FOrder.FOrder;

public interface MyOrdersClickListener {

    void onOrderClicked(FOrder fOrder);
}
