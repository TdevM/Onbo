package app.onbo.modules.payment;

import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.merged.MergedOrder;
import app.onbo.base.BaseView;

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
