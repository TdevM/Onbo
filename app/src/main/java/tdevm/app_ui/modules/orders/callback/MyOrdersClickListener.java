package tdevm.app_ui.modules.orders.callback;

import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;

public interface MyOrdersClickListener {

    void onOrderClicked(FOrder fOrder);
}
