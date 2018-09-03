package tdevm.app_ui.modules.dinein;

import java.util.ArrayList;
import java.util.Map;

import tdevm.app_ui.api.models.cart.MenuItem;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 06-11-2017.
 */

public interface DineInPresenterContract {

    interface DineInActivityPresenter extends BasePresenterMVP<DineInViewContract.DineInActivity> {
        void attachView(DineInViewContract.DineInActivity view);

        void detachView();
    }

    interface DishMenuPresenter extends BasePresenterMVP<DineInViewContract.DishMenuView> {
        void attachView(DineInViewContract.DishMenuView view);

        void detachView();

        void FetchAllCuisines(Map<String, String> map);
    }

    interface SingleCuisineGridPresenter extends BasePresenterMVP<DineInViewContract.SingleCuisineGridView> {
        void attachView(DineInViewContract.SingleCuisineGridView view);

        void detachView();

        void fetchMenuItemsByCuisine(Map<String, String> map);

        void addItemToCart(MenuItem menuItem, int itemTotal, String  itemHash);

        void addItemToSelection(MenuItem menuItem);

        void updateCartItem(MenuItem menuItem, int itemTotal, String itemHash);
    }

    interface PlaceTempOrderPresenter extends BasePresenterMVP<DineInViewContract.PlaceTempOrderView> {
        void attachView(DineInViewContract.PlaceTempOrderView view);

        void detachView();

        void checkCurrentOrderDetails();

        void addItemsToOrder(String userMessage, TOrder tOrder);

        void createNewOrder(int guest, String message);
    }

    interface CartFragmentPresenter extends BasePresenterMVP<DineInViewContract.CartFragmentView> {
        void attachView(DineInViewContract.CartFragmentView view);

        void detachView();

        boolean cartItemsExists();

        void showCartEmpty();

        void addItemToCart(MenuItem menuItem, String itemHash);

        void updateCartItem(MenuItem menuItem, String itemHash);

        void fetchCartItems();

        // void addCustomizableItemToCart(DishesOfCuisine dishesOfCuisine, Long parentDishId, int operationFlag);
    }

    interface MergedOrderFragmentPresenter extends BasePresenterMVP<DineInViewContract.MergedOrderView> {
        void fetchMergedOrder(TOrder tOrder);
        void fetchTempRunningOrder();
        void attachView(DineInViewContract.MergedOrderView view);

        void detachView();
    }

    interface DishReviewSheet extends BasePresenterMVP<DineInViewContract.DishReviewsSheetView> {
        void attachView(DineInViewContract.DishReviewsSheetView view);

        void detachView();

        void fetchMenuItemReview(Long dishId);
    }
}
