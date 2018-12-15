package app.onbo.modules.dinein;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;
import app.onbo.api.cart.CartItem;
import app.onbo.api.models.response.v2.menu.Cuisine;
import app.onbo.api.models.response.v2.menu.CuisineMenuItems;
import app.onbo.api.models.response.v2.merged.MergedOrder;
import app.onbo.api.models.response.v2.reviews.DishReviews;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.base.BaseView;

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
        void showNoInternetError();
        void hideNoInternetError();
        void hideBackendError();
        void showBackendError();
        void onMenuItemFetchFailure();

        void updateAdapter(int cartItemsCount);
    }

    interface DineInActivity extends BaseView {

    }

    interface MergedOrderView extends BaseView {
        void onRunningOrderFetched(TOrder tOrder);

        void onMergedOrderFetched(MergedOrder mergedOrder);
        void showNoInternetError();
        void hideNoInternetError();
        void hideBackendError();
        void showBackendError();
        void showNoRunningOrder();
    }

    interface PlaceTempOrderView extends BaseView {
        void showGetMessage(Response<TOrder> arrayListResponse);
        void onOrderItemsAdded(TOrder tOrder);
        void onNewOrderCreated(TOrder tOrder);
        void showGetGuestMessage();

        void showOrderAlreadyClosed();
    }

    interface DishReviewsSheetView extends BaseView {
        void onDishReviewsFetched(ArrayList<DishReviews> body);
    }

    interface InitializeT1OrderView extends BaseView {
        void onOrderItemsAdded(TOrder tOrder);
        void onNewOrderCreated(TOrder tOrder);
    }

}
