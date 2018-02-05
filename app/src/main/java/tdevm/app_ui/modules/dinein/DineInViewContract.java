package tdevm.app_ui.modules.dinein;

import java.util.ArrayList;
import java.util.List;

import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.models.response.Cuisine;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 28-12-2017.
 */

public interface DineInViewContract {

    interface DishMenuView extends BaseView {
        void onCuisinesFetched(ArrayList<Cuisine> arrayList);
    }

    interface TopRatedView extends BaseView{

    }

    interface CartFragmentView extends BaseView{
        void updateBottomSheet(int totalItems, Double cartTotal);
        void updateAdapter();
        void showNonDineEmptyCart();
        void showDineCartEmpty();
    }

    interface SingleCuisineGridView extends BaseView{
        void onDishesOfCuisinesFetched(ArrayList<DishesOfCuisine> arrayList);
        void onDishVariantsFetched(ArrayList<DishesOfCuisine> arrayList, DishesOfCuisine variantsOfDish);
        void updateAdapter();
    }

    interface DineInActivity extends BaseView{

    }

    interface PlaceTempOrderView extends BaseView{
        void onOrderDetailsFetched(String message);
        void onOrderItemsAdded();
        void onNewOrderCreated();
    }

}
