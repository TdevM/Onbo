package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.CartItemsRecyclerAdapter;
import tdevm.app_ui.modules.dinein.callbacks.DishItemClickListener;
import tdevm.app_ui.modules.nondinein.activities.NonDineActivity;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.widgets.ItemOffsetDecoration;

public class CartFragment extends Fragment implements DineInViewContract.CartFragmentView, DishItemClickListener {

    public static final String TAG = CartFragment.class.getSimpleName();
    private static final String MODE_DINE_IN = "MODE_DINE_IN";
    private static final String MODE_NON_DINE = "MODE_NON_DINE";
    @BindView(R.id.recycler_view_cart)
    RecyclerView recyclerViewCart;
    @OnClick(R.id.btn_order_initiate)
    void clear(){
        clearCart();
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
    AuthUtils authUtils;

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
        if(authUtils.getRestaurantMode().equals(MODE_DINE_IN)){
            activity = (DineInActivity)getActivity();
        }else if(authUtils.getRestaurantMode().equals(MODE_NON_DINE)){
            nonDineActivity = (NonDineActivity)getActivity();
        }
        View view;
        if (cartFragmentPresenter.cartItemsExists()) {
            view = inflater.inflate(R.layout.fragment_cart, container, false);
            unbinder = ButterKnife.bind(this, view);
            mLayoutManager = new LinearLayoutManager(getContext());
            adapter = new CartItemsRecyclerAdapter(getActivity(), cartFragmentPresenter, cartHelper);
            adapter.setOnDishItemClickListener(this);
            ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(getActivity(), R.dimen.recycler_view_item_width);
//            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
            //recyclerViewCart.addItemDecoration(itemDecoration);
            recyclerViewCart.setLayoutManager(mLayoutManager);
            recyclerViewCart.setAdapter(adapter);
            //TODO Avoid Direct Model Access
            updateBottomSheet(cartHelper.getCartTotalItems(),cartHelper.getCartTotal());
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

    public void clearCart(){
        cartFragmentPresenter.clearCart();
    }

    @Override
    public void onDestroy() {
        cartFragmentPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onPlusButtonClicked(DishesOfCuisine dishesOfCuisine, int num) {
        cartFragmentPresenter.addItemToCart(dishesOfCuisine);
        Log.d(TAG, dishesOfCuisine.getDish_name());
    }

    @Override
    public void onMinusButtonClicked(DishesOfCuisine dishesOfCuisine, int num) {
        cartFragmentPresenter.updateCartItem(dishesOfCuisine);
        Log.d(TAG, dishesOfCuisine.getDish_name());
    }

    @Override
    public void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine, int flag) {
        if (flag == 1) {
            cartFragmentPresenter.addItemToCart(dishesOfCuisine);
            Log.d(TAG, dishesOfCuisine.getDish_name());
        } else {
            cartFragmentPresenter.updateCartItem(dishesOfCuisine);
            Log.d(TAG, dishesOfCuisine.getDish_name());
        }
    }

    @Override
    public void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine, Long parentDishId, int flag) {
        cartFragmentPresenter.addCustomizableItemToCart(dishesOfCuisine, parentDishId, flag);
    }


    @Override
    public void updateBottomSheet(int totalItems, Double cartTotal) {
        if (cartTotal != null) {
            tvTotalBillAmt.setText(String.valueOf(getActivity().getApplication().getString(R.string.rupee_symbol, cartTotal.intValue())));
            tvTotalQuantities.setText(String.valueOf(totalItems));
        }
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
    public void showDineCartEmpty() {
       activity.showCartEmptyFragment();
    }
}
