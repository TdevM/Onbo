package tdevm.app_ui.modules.payment;

import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;
import tdevm.app_ui.base.BaseView;

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
        void onPaymentCaptured();

        void onPaymentCaptureFailure();
    }
}
