package onbo.app.modules.dinein;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import onbo.app.api.cart.CartItem;
import onbo.app.api.models.response.v2.menu.Cuisine;
import onbo.app.api.models.response.v2.menu.CuisineMenuItems;
import onbo.app.api.models.response.v2.merged.MergedOrder;
import onbo.app.api.models.response.v2.reviews.DishReviews;
import onbo.app.api.models.response.v2.t_orders.TOrder;
import onbo.app.base.BaseView;

/**
 * Created by Tridev on 28-12-2017.
 */

public interface DineInViewContract {

    interface DishMenuView extends BaseView {
        void onCuisinesFetched(ArrayList<Cuisine> arrayList);
    }

    interface TopRatedView extends BaseView {

    }

    interface CartFragmentView extends BaseView {
        void updateBottomSheet(int totalItems, int cartTotal);

        void updateAdapter();



        void onCartItemsFetched(List<CartItem> cartItems);

        void showCartEmpty();

        void startNonDineOrderActivity();

        void startDineOrderActivity();
    }

    interface SingleCuisineGridView extends BaseView {
        void onMenuItemsFetchedV2(List<CuisineMenuItems> cuisineMenuItems);

        void onMenuItemFetchFailure();

        void updateAdapter(int cartItemsCount);
    }

    interface DineInActivity extends BaseView {

    }

    interface MergedOrderView extends BaseView {
        void onRunningOrderFetched(TOrder tOrder);

        void onMergedOrderFetched(MergedOrder mergedOrder);

        void showNoRunningOrder();
    }

    interface PlaceTempOrderView extends BaseView {
        void showGetMessage(Response<TOrder> arrayListResponse);
        void onOrderItemsAdded(TOrder tOrder);
        void onNewOrderCreated(TOrder tOrder);
        void showGetGuestMessage();

    }

    interface DishReviewsSheetView extends BaseView {
        void onDishReviewsFetched(ArrayList<DishReviews> body);
    }

    interface InitializeT1OrderView extends BaseView {
        void onOrderItemsAdded(TOrder tOrder);
        void onNewOrderCreated(TOrder tOrder);
    }

}
