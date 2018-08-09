package tdevm.app_ui.modules.payment;

import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.base.BaseView;

public interface PaymentViewContract {


    interface CheckoutFragmentView extends BaseView {
        void onOrderClosed(FOrder fOrder);
        void onMergedOrderFetched(FOrder fOrder);
        void onOrderClosedFailure();
    }

    interface PaymentFragmentView extends BaseView {

    }

    interface PaymentActivityView extends BaseView {
        void onPaymentDone();
        void onPaymentFailure();
    }
}
