package tdevm.app_ui.modules.dinein.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.ItemHash;
import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.MenuAdapter;
import tdevm.app_ui.modules.dinein.adapters.RecycledGridMenuAdapter;
import tdevm.app_ui.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import tdevm.app_ui.modules.dinein.bottomsheets.section_r_view.MenuItemCustomizationSheet;
import tdevm.app_ui.modules.dinein.callbacks.MenuItemClickListener;
import tdevm.app_ui.modules.dinein.callbacks.MenuItemOptionsSelected;
import tdevm.app_ui.utils.PreferenceUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 30-07-2017.
 */

public class SingleCuisineGridFragment extends Fragment
        implements DineInViewContract.SingleCuisineGridView,
        MenuItemClickListener, MenuItemOptionsSelected, DishReviewsSheetFragment.DishReviewsSheetListener {

    public static final String TAG = SingleCuisineGridFragment.class.getSimpleName();
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
    SingleCuisineGridPresenter singleCuisineGridPresenter;
    @Inject
    CartHelper cartHelper;

    @Inject
    PreferenceUtils preferenceUtils;

    public static SingleCuisineGridFragment newInstance(String restaurantUUID) {
        Bundle args = new Bundle();
        args.putLong(CUISINE_ID, 4);
        args.putString(RESTAURANT_ID, restaurantUUID);
        SingleCuisineGridFragment fragment = new SingleCuisineGridFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        singleCuisineGridPresenter.attachView(this);
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
        menuAdapter = new MenuAdapter(getContext(), cartHelper);
        menuAdapter.setDishItemClickListenerCallback(this);
        recyclerViewGridSingle.setAdapter(menuAdapter);
        singleCuisineGridPresenter.fetchMenuItems(fetchDishesMap);
        logSelections();
        return view;
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }


    @Override
    public void onMenuItemsFetched(ArrayList<MenuItem> arrayList) {
        //recycledGridMenuAdapter.onItemsFetched(arrayList);
    }

    @Override
    public void onMenuItemsFetchedV2(List<CuisineMenuItems> cuisineMenuItems) {
        menuAdapter.onCuisineListFetched(cuisineMenuItems);
        Log.d(TAG, "Cuisine menu item fetched into fragment");
    }

    @Override
    public void onMenuItemFetchFailure() {
        Toast.makeText(getContext(), "Failed to load items", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateAdapter() {
        menuAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        singleCuisineGridPresenter.detachView();
        cartHelper.onDestroy();
    }

    @Override
    public void onPlusButtonClicked(MenuItem menuItem, int num) {

        List<MenuAddOn> menuAddOns = new ArrayList<>();
        List<MenuVOption> menuVOptions = new ArrayList<>();
        tdevm.app_ui.api.models.cart.MenuItem item = createCartMenuItemFromMenuItem(menuItem, menuVOptions, menuAddOns);

        ItemHash object = generateItemHash(item);

        singleCuisineGridPresenter.addItemToCart(item, object.getItemPrice(), object.getItemHash());
        Log.d(TAG, "ADDED TO CART: " + menuItem.getItemName());
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
    }

    @Override
    public void onMinusButtonClicked(MenuItem menuItem, int num) {
        List<MenuAddOn> menuAddOns = new ArrayList<>();
        List<MenuVOption> menuVOptions = new ArrayList<>();
        tdevm.app_ui.api.models.cart.MenuItem item = createCartMenuItemFromMenuItem(menuItem, menuVOptions, menuAddOns);

        ItemHash object = generateItemHash(item);

        singleCuisineGridPresenter.updateCartItem(item, object.getItemPrice(), object.getItemHash());
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


    public tdevm.app_ui.api.models.cart.MenuItem createCartMenuItemFromMenuItem(MenuItem item, List<MenuVOption> menuVOptions, List<MenuAddOn> menuAddOns) {
        return new tdevm.app_ui.api.models.cart.MenuItem(
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

    public ItemHash generateItemHash(tdevm.app_ui.api.models.cart.MenuItem cartMenuItem) {
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

    public int generateItemHashArray(tdevm.app_ui.api.models.cart.MenuItem cartMenuItem) {

        return cartMenuItem.hashCode();

    }

    @Override
    public void onOptionsSelected(MenuItem menuItem, List<MenuVOption> variantOptions, List<MenuAddOn> addOns) {
        Log.d(TAG, menuItem.getItemName() + "onOptionsSelected");
        tdevm.app_ui.api.models.cart.MenuItem item = createCartMenuItemFromMenuItem(menuItem, variantOptions, addOns);
        Log.d(TAG, "ADDED TO CART: " + menuItem.getItemName());
        ItemHash object = generateItemHash(item);
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
        singleCuisineGridPresenter.addItemToCart(item, object.getItemPrice(), object.getItemHash());
    }

    @Override
    public void onItemClicked(int position) {

    }
}
