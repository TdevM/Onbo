package tdevm.app_ui.modules.dinein.fragments;


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
import android.widget.TextView;

import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.models.ItemHash;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.activities.InitializeDineOrderActivity;
import tdevm.app_ui.modules.dinein.adapters.CartItemsRecyclerAdapter;
import tdevm.app_ui.modules.dinein.callbacks.CartItemClickListener;
import tdevm.app_ui.modules.nondine.NonDineActivity;
import tdevm.app_ui.modules.nondine.activities.InitNonDineOrderActivity;
import tdevm.app_ui.utils.PreferenceUtils;
import tdevm.app_ui.utils.CartHelper;

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

    @Inject
    CartFragmentPresenterImpl cartFragmentPresenter;

    private CartItemsRecyclerAdapter adapter;
    DineInActivity activity;
    NonDineActivity nonDineActivity;

    public CartFragment() {
    }

    public static CartFragment newInstance() {
        return new CartFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        cartFragmentPresenter.attachView(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (preferenceUtils.getRestaurantMode().equals(MODE_DINE_IN)) {
            activity = (DineInActivity) getActivity();
        } else if (preferenceUtils.getRestaurantMode().equals(MODE_NON_DINE)) {
            nonDineActivity = (NonDineActivity) getActivity();
        }
        View view;
        if (cartFragmentPresenter.cartItemsExists()) {
            view = inflater.inflate(R.layout.fragment_cart, container, false);
            unbinder = ButterKnife.bind(this, view);
            mLayoutManager = new LinearLayoutManager(getContext());
            adapter = new CartItemsRecyclerAdapter(getActivity(), cartFragmentPresenter, cartHelper);
            adapter.setOnCartItemClickListener(this);
            recyclerViewCart.setLayoutManager(mLayoutManager);
            recyclerViewCart.setAdapter(adapter);
            cartFragmentPresenter.fetchCartItems();
            //TODO Avoid Direct Model Access
            updateBottomSheet(cartHelper.getCartTotalItems(), cartHelper.getCartTotal());
            Log.d(TAG, String.valueOf(cartHelper.getCartTotalItems()));
        } else {
            view = inflater.inflate(R.layout.fragment_cart_empty, container, false);
        }
        return view;

    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
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

    public void handleInitiateOrder(){
        cartFragmentPresenter.handleOrderInit();
    }

    @Override
    public void onDestroy() {
        cartFragmentPresenter.detachView();
        super.onDestroy();
    }


    @Override
    public void updateBottomSheet(int totalItems, int cartTotal) {
        tvTotalBillAmt.setText(String.valueOf(getActivity().getApplication().getString(R.string.rupee_symbol, String.valueOf(cartTotal*0.01) )));
        tvTotalQuantities.setText(String.valueOf(totalItems));
    }


    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showNonDineEmptyCart() {
        nonDineActivity.showCartEmptyFragment();
    }

    @Override
    public void onCartItemsFetched(List<CartItem> cartItems) {
        adapter.onCartItemFetched(cartItems);
    }

    @Override
    public void showDineCartEmpty() {
        activity.showCartEmptyFragment();
    }

    @Override
    public void startNonDineOrderActivity() {
        Intent intent = new Intent(getContext(), InitNonDineOrderActivity.class);
        startActivity(intent);
    }

    @Override
    public void startDineOrderActivity() {
        Intent intent = new Intent(getContext(), InitializeDineOrderActivity.class);
        startActivity(intent);
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

    @Override
    public void onPlusButtonClicked(tdevm.app_ui.api.models.cart.MenuItem menuItem, int num) {
        ItemHash object = generateItemHash(menuItem);
        cartFragmentPresenter.addItemToCart(menuItem, object.getItemPrice(), object.getItemHash());
        Log.d(TAG, "ADDED TO CART: " + menuItem.getItemName());
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
    }

    @Override
    public void onMinusButtonClicked(tdevm.app_ui.api.models.cart.MenuItem menuItem, int num) {
        ItemHash object = generateItemHash(menuItem);
        cartFragmentPresenter.updateCartItem(menuItem, object.getItemPrice(), object.getItemHash());
        Log.d(TAG, "REMOVED FROM CART: " + menuItem.getItemName());
        Log.d(TAG, "ITEM_HASH: " + object.getItemHash());
        Log.d(TAG, menuItem.getItemName());
    }

    @Override
    public void onCustomizableItemClicked(tdevm.app_ui.api.models.cart.MenuItem menuItem, int flag) {

    }
}
