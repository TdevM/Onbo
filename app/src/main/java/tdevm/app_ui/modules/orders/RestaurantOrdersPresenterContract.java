package tdevm.app_ui.modules.orders;

import tdevm.app_ui.base.BasePresenterMVP;

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

    interface MyOrderDetailFragment extends BasePresenterMVP<RestaurantOrdersViewContract.MyOrderDetailFragmentView>{
        void attachView(RestaurantOrdersViewContract.MyOrderDetailFragmentView view);
        void detachView();
    }

}
