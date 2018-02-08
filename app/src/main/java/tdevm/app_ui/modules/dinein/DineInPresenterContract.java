package tdevm.app_ui.modules.dinein;

import java.util.ArrayList;
import java.util.Map;

import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.api.models.response.TempOrder;
import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 06-11-2017.
 */

public interface DineInPresenterContract {

    interface DineInActivityPresenter extends BasePresenterMVP<DineInViewContract.DineInActivity>{
        void attachView(DineInViewContract.DineInActivity view);
        void detachView();
    }

    interface DishMenuPresenter extends BasePresenterMVP<DineInViewContract.DishMenuView>{
        void attachView(DineInViewContract.DishMenuView view);
        void detachView();
        void FetchAllCuisines(Map<String,String> map);
    }

    interface SingleCuisineGridPresenter extends BasePresenterMVP<DineInViewContract.SingleCuisineGridView>{
        void attachView(DineInViewContract.SingleCuisineGridView view);
        void detachView();
        void fetchDishesByCuisines(Map<String,String> map);
        void fetchVariantsOfADish(Map<String,String> map, DishesOfCuisine variantsToFetch);
        void addItemToCart(DishesOfCuisine dishesOfCuisine);
        void addItemToSelection(DishesOfCuisine dishesOfCuisine);
        void updateCartItem(DishesOfCuisine dishesOfCuisine);
        void addCustomizableItemToCart(DishesOfCuisine dishesOfCuisine, int operationFlag);
        void addDishVariantItemToCart(DishesOfCuisine selectedDish, DishesOfCuisine parentDish);

    }

    interface PlaceTempOrderPresenter extends BasePresenterMVP<DineInViewContract.PlaceTempOrderView>{
        void attachView(DineInViewContract.PlaceTempOrderView view);
        void detachView();
        void checkCurrentOrderDetails(Map<String,String> map);
        void addItemsToOrder(ArrayList<TempOrder> arrayList);
        void createNewOrder();
    }

    interface CartFragmentPresenter extends BasePresenterMVP<DineInViewContract.CartFragmentView>{
        void attachView(DineInViewContract.CartFragmentView view);
        void detachView();
        boolean cartItemsExists();
        void showCartEmpty();
        void addItemToCart(DishesOfCuisine dishesOfCuisine);
        void updateCartItem(DishesOfCuisine dishesOfCuisine);
        void addCustomizableItemToCart(DishesOfCuisine dishesOfCuisine, Long parentDishId, int operationFlag);
    }

    interface RunningOrderFragmentPresenter extends BasePresenterMVP<DineInViewContract.RunningOrderView>{
        void fetchTempRunningOrder();
        void attachView(DineInViewContract.RunningOrderView view);
        void detachView();
    }
}
