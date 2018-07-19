package tdevm.app_ui.modules.dinein.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.RecycledGridMenuAdapter;
import tdevm.app_ui.modules.dinein.bottomsheets.DishReviewsSheetFragment;
import tdevm.app_ui.modules.dinein.bottomsheets.section_r_view.MenuItemCustomizationSheet;
import tdevm.app_ui.modules.dinein.callbacks.MenuItemClickListener;
import tdevm.app_ui.modules.dinein.callbacks.MenuItemOptionsSelected;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 30-07-2017.
 */

public class SingleCuisineGridFragment extends Fragment
        implements DineInViewContract.SingleCuisineGridView, MenuItemClickListener, MenuItemOptionsSelected {

    public static final String TAG = SingleCuisineGridFragment.class.getSimpleName();
    public static final String CUISINE_ID = "CUISINE_ID";
    public static final String RESTAURANT_ID = "RESTAURANT_ID";

    private RecyclerView.LayoutManager mLayoutManager;
    @BindView(R.id.recycler_view_dishes_grid_single)
    RecyclerView recyclerViewGridSingle;
    Unbinder unbinder;
    private Map<String, String> fetchDishesMap;
    RecycledGridMenuAdapter recycledGridMenuAdapter;

    @Inject
    SingleCuisineGridPresenter singleCuisineGridPresenter;
    @Inject
    CartHelper cartHelper;

    public static SingleCuisineGridFragment newInstance(String restaurantUUID, Long mCuisineId) {
        Bundle args = new Bundle();
        args.putLong(CUISINE_ID, mCuisineId);
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
        View view = inflater.inflate(R.layout.fragment_single_cuisine_grid, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLayoutManager = new GridLayoutManager(getContext(), 2);
        fetchDishesMap = new HashMap<>();
        fetchDishesMap.put("restaurant_id", String.valueOf(getArguments().getString(RESTAURANT_ID)));
        fetchDishesMap.put("cuisine_id", String.valueOf(getArguments().getLong(CUISINE_ID)));
        recyclerViewGridSingle.setLayoutManager(mLayoutManager);
        recycledGridMenuAdapter = new RecycledGridMenuAdapter(getActivity(), singleCuisineGridPresenter, cartHelper);
        recyclerViewGridSingle.setAdapter(recycledGridMenuAdapter);
        singleCuisineGridPresenter.fetchMenuItemsByCuisine(fetchDishesMap);
        recycledGridMenuAdapter.setDishItemClickListenerCallback(this);
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
        recycledGridMenuAdapter.onItemsFetched(arrayList);
    }

    @Override
    public void updateAdapter() {
        recycledGridMenuAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        singleCuisineGridPresenter.detachView();
    }

    @Override
    public void onPlusButtonClicked(MenuItem menuItem, int num) {

        List<MenuAddOn> menuAddOns = new ArrayList<>();
        List<MenuVOption> menuVOptions = new ArrayList<>();
        tdevm.app_ui.api.models.cart.MenuItem item = createCartMenuItemFromMenuItem(menuItem, menuVOptions, menuAddOns);
        String itemHash = generateItemHash(item);
        singleCuisineGridPresenter.addItemToCart(item, itemHash);
        //Toast.makeText(getActivity(), "Plus", Toast.LENGTH_SHORT).show();
        Log.d(TAG,"Created cart menu item"+ item.getItemName());
        Log.d(TAG,generateItemHash(item));
        Log.d(TAG, menuItem.getItemName());
    }

    @Override
    public void onMinusButtonClicked(MenuItem menuItem, int num) {
        //singleCuisineGridPresenter.updateCartItem(dishesOfCuisine);
        Toast.makeText(getActivity(), "Minus", Toast.LENGTH_SHORT).show();
        Log.d(TAG, menuItem.getItemName());
    }

    @Override
    public void onItemImageClicked(MenuItem menuItem) {
        DishReviewsSheetFragment.newInstance(30, menuItem).show(getChildFragmentManager(), "dialog");

    }

    @Override
    public void onCustomizableItemClicked(MenuItem menuItem, int flag) {
        MenuItemCustomizationSheet.newInstance(menuItem).show(getChildFragmentManager(), "dialog");
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

    public String generateItemHash(tdevm.app_ui.api.models.cart.MenuItem cartMenuItem) {
        String itemHash = cartMenuItem.getItemId();
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
                sb.append("_");
                sb.append(option.getOptionId());
            }
            if (menuAddOns != null) {
                menuAddOnIterator = menuAddOns.listIterator();
                while (menuAddOnIterator.hasNext()) {
                    MenuAddOn addOn = menuAddOnIterator.next();
                    sb.append("_");
                    sb.append(addOn.getAddOnId());
                }
            }

        }

        return sb.toString();
    }

    public String generateItemHashSimple(tdevm.app_ui.api.models.cart.MenuItem cartMenuItem) {
        String itemHash = cartMenuItem.getItemId();
        List<MenuAddOn> menuAddOns = cartMenuItem.getMenuAddOns();
        List<MenuVOption> menuVOptions = cartMenuItem.getMenuVariantOptions();
        Iterator<MenuVOption> menuVOptionIterator;
        Iterator<MenuAddOn> menuAddOnIterator;
        if (menuVOptions != null) {
            menuVOptionIterator = menuVOptions.listIterator();
            while (menuVOptionIterator.hasNext()) {
                MenuVOption option = menuVOptionIterator.next();
                itemHash = itemHash+"_";
                itemHash = itemHash+option.getOptionId();
               // sb.append();
            }
            if (menuAddOns != null) {
                menuAddOnIterator = menuAddOns.listIterator();
                while (menuAddOnIterator.hasNext()) {
                    MenuAddOn addOn = menuAddOnIterator.next();
                    itemHash = itemHash+"_";
                    itemHash = itemHash+addOn.getAddOnId();
                   // sb.append("_");
                    //sb.append(addOn.getAddOnId());
                }
            }

        }

        return itemHash;
    }

    @Override
    public void onOptionsSelected(MenuItem menuItem, List<MenuVOption> variantOptions, List<MenuAddOn> addOns) {
        Log.d(TAG, menuItem.getItemName() + "onOptionsSelected");
        tdevm.app_ui.api.models.cart.MenuItem item = createCartMenuItemFromMenuItem(menuItem, variantOptions, addOns);
        Log.d(TAG,"Created cart menu item with options"+ item.getItemName());
        Log.d(TAG,generateItemHash(item));
        Log.d(TAG, menuItem.getItemName());
        String itemHash = generateItemHash(item);
        singleCuisineGridPresenter.addItemToCart(item, itemHash);
    }
}
