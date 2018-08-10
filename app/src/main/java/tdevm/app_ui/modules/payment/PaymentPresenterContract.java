package tdevm.app_ui.modules.payment;

import tdevm.app_ui.base.BasePresenterMVP;

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
}