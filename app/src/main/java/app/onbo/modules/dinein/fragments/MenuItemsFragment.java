package app.onbo.modules.dinein.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.ItemHash;
import app.onbo.api.models.response.v2.menu.CuisineMenuItems;
import app.onbo.api.models.response.v2.menu.MenuAddOn;
import app.onbo.api.models.response.v2.menu.MenuItem;
import app.onbo.api.models.response.v2.menu.MenuVOption;
import app.onbo.modules.dinein.DineInViewContract;
import app.onbo.modules.dinein.adapters.MenuAdapter;
import app.onbo.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import app.onbo.modules.dinein.bottomsheets.section_r_view.MenuItemCustomizationSheet;
import app.onbo.modules.dinein.callbacks.MenuItemClickListener;
import app.onbo.modules.dinein.callbacks.MenuItemOptionsSelected;
import app.onbo.modules.orders.callback.CartBadgeListener;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

/**
 * Created by Tridev on 30-07-2017.
 */

public class MenuItemsFragment extends Fragment
        implements DineInViewContract.SingleCuisineGridView,
        MenuItemClickListener, MenuItemOptionsSelected, DishReviewsSheetFragment.DishReviewsSheetListener,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = MenuItemsFragment.class.getSimpleName();
    public static final String CUISINE_ID = "CUISINE_ID";
    public static final String RESTAURANT_ID = "RESTAURANT_ID";

    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recycler_view_dishes_grid_single)
    RecyclerView recyclerViewGridSingle;
    Unbinder unbinder;
    private Map<String, String> fetchDishesMap;
    //    RecycledGridMenuAdapter recycledGridMenuAdapter;
    MenuAdapter menuAdapter;

    @Inject
    MenuItemsPresenter menuItemsPresenter;
    @Inject
    CartHelper cartHelper;

    @Inject
    PreferenceUtils preferenceUtils;

    CartBadgeListener cartBadgeListener;



    @BindView(R.id.frame_layout_backend_error)
    FrameLayout backendError;


    @BindView(R.id.frame_layout_connection_broken)
    FrameLayout noInternet;

    @BindView(R.id.swipe_refresh_menu_items_fragment)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.shimmer_fragment_menu_items)
    ShimmerFrameLayout shimmerFrameLayout;

    public static MenuItemsFragment newInstance(String restaurantUUID) {
        Bundle args = new Bundle();
        args.putLong(CUISINE_ID, 4);
        args.putString(RESTAURANT_ID, restaurantUUID);
        MenuItemsFragment fragment = new MenuItemsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        menuItemsPresenter.attachView(this);
        menuItemsPresenter.fetchMenuItems(fetchDishesMap);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();

        Log.d(TAG, " single cuisine onCreateee");
        View view = inflater.inflate(R.layout.fragment_single_cuisine_grid, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getContext());
        fetchDishesMap = new HashMap<>();
        fetchDishesMap.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        recyclerViewGridSingle.setLayoutManager(mLayoutManager);
        //recyclerViewGridSingle.hasFixedSize();
        menuAdapter = new MenuAdapter(getContext(), cartHelper);
        menuAdapter.setDishItemClickListenerCallback(this);
        recyclerViewGridSingle.setAdapter(menuAdapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_default_app);
        logSelections();
        return view;
    }

    @Override
    public void showProgressUI() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }


    @Override
    public void onMenuItemsFetchedV2(List<CuisineMenuItems> cuisineMenuItems) {
        menuAdapter.onCuisineListFetched(cuisineMenuItems);
        swipeRefreshLayout.setRefreshing(false);
        Log.d(TAG, "Cuisine menu item fetched into fragment");
    }

    @Override
    public void onMenuItemFetchFailure() {
        Toast.makeText(getContext(), "Failed to load items", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAdapter(int cartItemsCount) {
        menuAdapter.notifyDataSetChanged();
        cartBadgeListener.onCartItemUpdated(cartItemsCount);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        menuItemsPresenter.detachView();
        cartHelper.onDestroy();
    }


    @Override
    public void showNoInternetError() {

        noInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoInternetError() {
        noInternet.setVisibility(View.GONE);
    }

    @Override
    public void hideBackendError() {
        backendError.setVisibility(View.GONE);

    }

    @Override
    public void showBackendError() {

        backendError.setVisibility(View.VISIBLE);

    }

    @Override
    public void onPlusButtonClicked(MenuItem menuItem, int num) {

        List<MenuAddOn> menuAddOns = new ArrayList<>();
        List<MenuVOption> menuVOptions = new ArrayList<>();
        app.onbo.api.models.cart.MenuItem item = createCartMenuItemFromMenuItem(menuItem, menuVOptions, menuAddOns);

        ItemHash object = generateItemHash(item);

        menuItemsPresenter.addItemToCart(item, object.getItemPrice(), object.getItemHash());
        Log.d(TAG, "ADDED TO CART: " + menuItem.getItemName());
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
    }

    @Override
    public void onMinusButtonClicked(MenuItem menuItem, int num) {
        List<MenuAddOn> menuAddOns = new ArrayList<>();
        List<MenuVOption> menuVOptions = new ArrayList<>();
        app.onbo.api.models.cart.MenuItem item = createCartMenuItemFromMenuItem(menuItem, menuVOptions, menuAddOns);

        ItemHash object = generateItemHash(item);

        menuItemsPresenter.updateCartItem(item, object.getItemPrice(), object.getItemHash());
        Log.d(TAG, "REMOVED FROM CART: " + menuItem.getItemName());
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
    }

    @Override
    public void onItemImageClicked(MenuItem menuItem) {
        DishReviewsSheetFragment.newInstance(30, menuItem).show(getChildFragmentManager(), "reviews_dialog");

    }

    @Override
    public void onCustomizableItemClicked(MenuItem menuItem, int flag) {
        if (flag == 1) {
            MenuItemCustomizationSheet.newInstance(menuItem).show(getChildFragmentManager(), "dialog");
        } else if (flag == 0) {
            Toast.makeText(getContext(), "Remove this item from cart!", Toast.LENGTH_SHORT).show();
        }
    }


    public void logSelections() {
        cartHelper.logCartItems();
    }


    public app.onbo.api.models.cart.MenuItem createCartMenuItemFromMenuItem(MenuItem item, List<MenuVOption> menuVOptions, List<MenuAddOn> menuAddOns) {
        return new app.onbo.api.models.cart.MenuItem(
                item.getItemId(),
                item.getItemName(),
                item.getDescription(),
                item.getItemPrice(),
                item.getItemImage(),
                item.getIsVeg(),
                item.getRestaurantId(),
                item.getCuisineId(),
                item.getCustomizable(),
                menuVOptions,
                menuAddOns
        );

    }

    public ItemHash generateItemHash(app.onbo.api.models.cart.MenuItem cartMenuItem) {
        String itemHash = cartMenuItem.getItemId();
        int itemPrice = cartMenuItem.getItemPrice();
        StringBuilder sb = new StringBuilder();
        sb.append(itemHash);
        List<MenuAddOn> menuAddOns = cartMenuItem.getMenuAddOns();
        List<MenuVOption> menuVOptions = cartMenuItem.getMenuVariantOptions();
        Iterator<MenuVOption> menuVOptionIterator;
        Iterator<MenuAddOn> menuAddOnIterator;
        if (menuVOptions != null) {
            menuVOptionIterator = menuVOptions.listIterator();
            while (menuVOptionIterator.hasNext()) {
                MenuVOption option = menuVOptionIterator.next();
                sb.append(option.getOptionId());
                itemPrice += option.getPrice();
            }
            if (menuAddOns != null) {
                menuAddOnIterator = menuAddOns.listIterator();
                while (menuAddOnIterator.hasNext()) {
                    MenuAddOn addOn = menuAddOnIterator.next();
                    itemPrice += addOn.getPrice();
                    sb.append(addOn.getAddOnId());
                }
            }

        }

        return new ItemHash(sb.toString(), itemPrice);
    }

    public int generateItemHashArray(app.onbo.api.models.cart.MenuItem cartMenuItem) {

        return cartMenuItem.hashCode();

    }

    @Override
    public void onOptionsSelected(MenuItem menuItem, List<MenuVOption> variantOptions, List<MenuAddOn> addOns) {
        Log.d(TAG, menuItem.getItemName() + "onOptionsSelected");
        app.onbo.api.models.cart.MenuItem item = createCartMenuItemFromMenuItem(menuItem, variantOptions, addOns);
        Log.d(TAG, "ADDED TO CART: " + menuItem.getItemName());
        ItemHash object = generateItemHash(item);
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
        menuItemsPresenter.addItemToCart(item, object.getItemPrice(), object.getItemHash());
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void onRefresh() {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        menuItemsPresenter.fetchMenuItems(map);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartBadgeListener) {
            cartBadgeListener = (CartBadgeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartBadgeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartBadgeListener = null;
    }
}
