package onbo.app.modules.payment;

import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.base.BasePresenterMVP;

public interface PaymentPresenterContract {


    interface CheckoutPresenterContract extends BasePresenterMVP<PaymentViewContract.CheckoutFragmentView> {
        void closeRunningOrder(String orderId);

        void detachView();

        void attachView(PaymentViewContract.CheckoutFragmentView view);
    }

    interface PaymentFragmentPresenterContract extends BasePresenterMVP<PaymentViewContract.PaymentFragmentView> {
        void fetchClosedOrder(String tOrderId, String fOrderId);

        void attachView(PaymentViewContract.PaymentFragmentView view);

        void detachView();
    }

    interface PaymentActivityPresenterContract extends BasePresenterMVP<PaymentViewContract.PaymentActivityView> {
        void captureOrderPayment(String paymentId, String orderId);

        void attachView(PaymentViewContract.PaymentActivityView view);

        void detachView();
    }


    interface CashPickupPresenterContract extends BasePresenterMVP<PaymentViewContract.CashPickupView> {
        void attachView(PaymentViewContract.CashPickupView view);

        void detachView();

        void fetchOrderPaymentStatus(FOrder fOrder);
    }
}