package tdevm.app_ui.modules.dinein;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.models.response.v2.menu.Cuisine;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;
import tdevm.app_ui.api.models.response.v2.reviews.DishReviews;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
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
        void updateBottomSheet(int totalItems, int cartTotal);
        void updateAdapter();
        void showNonDineEmptyCart();
        void onCartItemsFetched(List<CartItem> cartItems);
        void showDineCartEmpty();
        void startNonDineOrderActivity();
        void startDineOrderActivity();
    }

    interface SingleCuisineGridView extends BaseView{
        void onMenuItemsFetched(ArrayList<MenuItem> arrayList);
        void updateAdapter();
    }

    interface DineInActivity extends BaseView{

    }

    interface MergedOrderView extends BaseView{
        void onRunningOrderFetched(TOrder tOrder);
        void onMergedOrderFetched(MergedOrder mergedOrder);
        void showNoRunningOrder();
    }

    interface PlaceTempOrderView extends BaseView{
        void onOrderItemsAdded();
        void onNewOrderCreated();
        void showGetMessage(Response<TOrder> arrayListResponse);
        void createOrder(int guest, String userMessage);
        void showGetGuestMessage();
        void addItemsToOrder(String userMessage);
    }

    interface DishReviewsSheetView extends BaseView{
        void onDishReviewsFetched(ArrayList<DishReviews> body);
    }

}
