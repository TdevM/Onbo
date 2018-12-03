package onbo.app.modules.payment;

import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.api.models.response.v2.merged.MergedOrder;
import onbo.app.base.BaseView;

public interface PaymentViewContract {


    interface CheckoutFragmentView extends BaseView {
        void onOrderClosed(FOrder fOrder);

        void showCloseProgressUI();

        void hideCloseProgressUI();

        void onMergedOrderFetched(MergedOrder fOrder);

        void onOrderClosedFailure();
    }

    interface PaymentFragmentView extends BaseView {
        void onClosedOrderFetched(FOrder fOrder);
    }

    interface PaymentActivityView extends BaseView {
        void onPaymentCaptured(FOrder fOrder);

        void showPaymentCaptureProgressUI();

        void hidePaymentCaptureProgressUI();

        void onPaymentCaptureFailure(FOrder fOrder);
    }

    interface CashPickupView extends BaseView {

        void onFOrderFetched(FOrder fOrder);
    }
}
