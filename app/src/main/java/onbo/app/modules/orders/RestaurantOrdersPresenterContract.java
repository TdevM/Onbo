package onbo.app.modules.orders;

import onbo.app.base.BasePresenterMVP;

public interface RestaurantOrdersPresenterContract {

    interface RestaurantOrderPresenter extends BasePresenterMVP<RestaurantOrdersViewContract.RestaurantOrdersActivityView>{
        void attachView(RestaurantOrdersViewContract.RestaurantOrdersActivityView view);
        void detachView();
    }

    interface MyOrdersFragmentPresenter extends BasePresenterMVP<RestaurantOrdersViewContract.MyOrdersFragmentView>{
        void fetchMyOrders();
        void attachView(RestaurantOrdersViewContract.MyOrdersFragmentView view);
        void detachView();
    }

    interface MyOrderDetail extends BasePresenterMVP<RestaurantOrdersViewContract.MyOrderDetailView>{
        void attachView(RestaurantOrdersViewContract.MyOrderDetailView view);
        void detachView();
    }

}
