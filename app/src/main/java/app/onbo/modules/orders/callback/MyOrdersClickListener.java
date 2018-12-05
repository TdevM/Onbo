package app.onbo.modules.orders.callback;

import app.onbo.api.models.response.v2.FOrder.FOrder;

public interface MyOrdersClickListener {

    void onOrderClicked(FOrder fOrder);
}
