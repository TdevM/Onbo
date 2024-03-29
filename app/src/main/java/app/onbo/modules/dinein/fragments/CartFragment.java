package app.onbo.modules.dinein.fragments;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.cart.CartItem;
import app.onbo.api.models.ItemHash;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.menu.MenuAddOn;
import app.onbo.api.models.response.v2.menu.MenuVOption;
import app.onbo.modules.dinein.DineInActivity;
import app.onbo.modules.dinein.DineInViewContract;
import app.onbo.modules.dinein.adapters.CartItemsRecyclerAdapter;
import app.onbo.modules.dinein.callbacks.CartItemClickListener;
import app.onbo.modules.nondine.NonDineActivity;
import app.onbo.modules.nondine.activities.InitNonDineOrderActivity;
import app.onbo.modules.orders.callback.CartBadgeListener;
import app.onbo.utils.CartHelper;
import app.onbo.utils.GeneralUtils;
import app.onbo.utils.PreferenceUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CartFragment extends Fragment implements DineInViewContract.CartFragmentView, CartItemClickListener {

    public static final String TAG = CartFragment.class.getSimpleName();
    private static final String MODE_DINE_IN = "MODE_DINE_IN";
    private static final String MODE_NON_DINE = "MODE_NON_DINE";
    @BindView(R.id.recycler_view_cart)
    RecyclerView recyclerViewCart;

    @OnClick(R.id.btn_order_initiate)
    void initiateOrder() {
        handleInitiateOrder();
    }

    @BindView(R.id.tv_total_quantities)
    TextView tvTotalQuantities;
    @BindView(R.id.tv_total_bill_amount)
    TextView tvTotalBillAmt;
    Unbinder unbinder;
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    CartHelper cartHelper;
    @Inject
    PreferenceUtils preferenceUtils;

    CartBadgeListener cartBadgeListener;

    @BindView(R.id.frame_layout_cart_empty)
    FrameLayout cartEmptyFrameLayout;


    @Inject
    CartFragmentPresenterImpl cartFragmentPresenter;

    private CartItemsRecyclerAdapter adapter;
    DineInActivity activity;
    NonDineActivity nonDineActivity;

    Restaurant restaurant;

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            restaurant = getArguments().getParcelable("RESTAURANT");
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        if (preferenceUtils.getRestaurantMode().equals(MODE_DINE_IN)) {
            activity = (DineInActivity) getActivity();
        } else if (preferenceUtils.getRestaurantMode().equals(MODE_NON_DINE)) {
            nonDineActivity = (NonDineActivity) getActivity();
        }

        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this, view);
        mLayoutManager = new LinearLayoutManager(getContext());
        adapter = new CartItemsRecyclerAdapter(getActivity(), cartFragmentPresenter, cartHelper);
        adapter.setOnCartItemClickListener(this);
        recyclerViewCart.setLayoutManager(mLayoutManager);
        recyclerViewCart.setAdapter(adapter);

        return view;

    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    public void clearCart() {
        cartFragmentPresenter.clearCart();
    }

    public void handleInitiateOrder() {
        cartFragmentPresenter.handleOrderInit();
    }

    @Override
    public void onDestroy() {
        cartFragmentPresenter.detachView();
        super.onDestroy();
    }


    @Override
    public void updateBottomSheet(int totalItems, int cartTotal) {
        tvTotalBillAmt.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(String.valueOf(cartTotal))));
        tvTotalQuantities.setText(String.valueOf(totalItems));
    }


    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();

    }


    @Override
    public void onResume() {
        super.onResume();
        cartFragmentPresenter.attachView(this);
        cartFragmentPresenter.fetchCartItems();
    }

    @Override
    public void onCartItemsFetched(List<CartItem> cartItems) {
        adapter.onCartItemFetched(cartItems);
        updateBottomSheet(cartHelper.getCartTotalItems(), cartHelper.getCartTotal());
        cartBadgeListener.onCartItemUpdated(0);
    }

    @Override
    public void showCartEmpty() {
        cartEmptyFrameLayout.setVisibility(View.VISIBLE);
        cartBadgeListener.onCartItemUpdated(0);
    }

    @Override
    public void startNonDineOrderActivity() {
        Intent intent = new Intent(nonDineActivity, InitNonDineOrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void startDineOrderActivity() {
        if (getArguments() != null) {
            restaurant = getArguments().getParcelable("RESTAURANT");
            activity.startDineOrderActivity(restaurant);
        }
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

    @Override
    public void onPlusButtonClicked(app.onbo.api.models.cart.MenuItem menuItem, int num) {
        ItemHash object = generateItemHash(menuItem);
        cartFragmentPresenter.addItemToCart(menuItem, object.getItemPrice(), object.getItemHash());
        Log.d(TAG, "ADDED TO CART: " + menuItem.getItemName());
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
    }

    @Override
    public void onMinusButtonClicked(app.onbo.api.models.cart.MenuItem menuItem, int num) {
        ItemHash object = generateItemHash(menuItem);
        cartFragmentPresenter.updateCartItem(menuItem, object.getItemPrice(), object.getItemHash());
        Log.d(TAG, "REMOVED FROM CART: " + menuItem.getItemName());
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
        Log.d(TAG, menuItem.getItemName());
    }

    @Override
    public void onCustomizableItemClicked(app.onbo.api.models.cart.MenuItem menuItem, int flag) {
        if (flag == 1) {
            ItemHash object = generateItemHash(menuItem);
            cartFragmentPresenter.addItemToCart(menuItem, object.getItemPrice(), object.getItemHash());
            Log.d(TAG, "ADDED TO CART: " + menuItem.getItemName());
            Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
        } else if (flag == 0) {
            ItemHash object = generateItemHash(menuItem);
            cartFragmentPresenter.updateCartItem(menuItem, object.getItemPrice(), object.getItemHash());
            Log.d(TAG, "REMOVED FROM CART: " + menuItem.getItemName());
            Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
            Log.d(TAG, menuItem.getItemName());
        }

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
