package tdevm.app_ui.modules.nondine;

import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 02-02-2018.
 */

public interface NonDinePresenterContract {

    interface NonDineRestaurantDetails extends BasePresenterMVP<NonDineViewContract.NonDineRestaurantDetailsView>{
        void attachView(NonDineViewContract.NonDineRestaurantDetailsView view);
        void detachView();
    }

    interface InitNonDineOrderPresenter extends BasePresenterMVP<NonDineViewContract.InitNonDineOrderView>{
        void attachView(NonDineViewContract.InitNonDineOrderView view);
        void detachView();
        void createCashNDOrder();
    }

    interface NonDineSummaryPresenter extends BasePresenterMVP<NonDineViewContract.NonDineCheckoutView>{
        void attachView(NonDineViewContract.NonDineCheckoutView view);
        void detachView();
        void checkoutOrderSummary();
    }

    interface OrderPaymentTypePresenter extends BasePresenterMVP<NonDineViewContract.OrderPaymentTypeView>{
        void attachView(NonDineViewContract.OrderPaymentTypeView view);
        void detachView();

    }

    interface PlaceNDOrderCashPresenter extends BasePresenterMVP<NonDineViewContract.PlaceNDOrderCashView>{
        void attachView(NonDineViewContract.PlaceNDOrderCashView view);
        void detachView();

    }
}


