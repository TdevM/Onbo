package tdevm.app_ui.modules.nondine;

import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 02-02-2018.
 */

public interface NonDinePresenterContract {

    interface NonDineRestaurantDetails extends BasePresenterMVP<NonDineViewContract.NonDineRestaurantDetailsView> {
        void attachView(NonDineViewContract.NonDineRestaurantDetailsView view);

        void detachView();
    }

    interface InitNonDineOrderPresenter extends BasePresenterMVP<NonDineViewContract.InitNonDineOrderView> {
        void capturePaymentForOrder(String payment_id, String orderId);

        void attachView(NonDineViewContract.InitNonDineOrderView view);

        void detachView();

    }

    interface NonDineSummaryPresenter extends BasePresenterMVP<NonDineViewContract.NonDineCheckoutView> {
        void attachView(NonDineViewContract.NonDineCheckoutView view);

        void detachView();

        void checkoutOrderSummary();
    }

    interface OrderPaymentTypePresenter extends BasePresenterMVP<NonDineViewContract.OrderPaymentTypeView> {
        void attachView(NonDineViewContract.OrderPaymentTypeView view);

        void detachView();

        void createCashNDOrder();

    }

    interface PlaceNDOrderCashPresenter extends BasePresenterMVP<NonDineViewContract.PlaceNDOrderCashView> {
        void attachView(NonDineViewContract.PlaceNDOrderCashView view);

        void detachView();

    }

    interface DigitalPaymentOptionPresenter extends BasePresenterMVP<NonDineViewContract.DigitalPaymentOptionView> {
        void attachView(NonDineViewContract.DigitalPaymentOptionView view);

        void detachView();

        void createPaidNDOrder();
    }

    interface NonDineActivityPresenter  extends BasePresenterMVP<NonDineViewContract.NonDineActivityView>{

    }

}


