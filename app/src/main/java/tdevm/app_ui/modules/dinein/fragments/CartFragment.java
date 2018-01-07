package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.CartItemsRecyclerAdapter;
import tdevm.app_ui.modules.dinein.callbacks.DishItemClickListener;
import tdevm.app_ui.utils.CartHelper;

public class CartFragment extends Fragment implements DineInViewContract.CartFragmentView, DishItemClickListener{

    public static final String TAG = CartFragment.class.getSimpleName();

    @BindView(R.id.recycler_view_cart)
    RecyclerView recyclerViewCart;
    @BindView(R.id.btn_order_initiate)
    Button btnInitiateOrder;
    @BindView(R.id.tv_total_quantities)
    TextView tvTotalQuantities;
    @BindView(R.id.tv_total_bill_amount)
    TextView tvTotalBillAmt;
    Unbinder unbinder;
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    CartHelper cartHelper;

    @Inject
    CartFragmentPresenterImpl cartFragmentPresenter;

    private CartItemsRecyclerAdapter adapter;

    public CartFragment() {
    }

    public static CartFragment newInstance(){
        return new CartFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_cart, container, false);
        unbinder = ButterKnife.bind(this,view);
        resolveDaggerDependencies();
        mLayoutManager = new LinearLayoutManager(getContext());
        adapter = new CartItemsRecyclerAdapter(getActivity(),cartFragmentPresenter,cartHelper);
        adapter.setOnDishItemClickListener(this);
        cartFragmentPresenter.attachView(this);
        cartFragmentPresenter.fetchCartItems();
        recyclerViewCart.setLayoutManager(mLayoutManager);
        recyclerViewCart.setAdapter(adapter);
        return view;

    }

    @Override
    public void resolveDaggerDependencies(){
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void onDestroy() {
        cartFragmentPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void onPlusButtonClicked(DishesOfCuisine dishesOfCuisine, int num) {
       cartFragmentPresenter.addItemToCart(dishesOfCuisine);
        Log.d(TAG,dishesOfCuisine.getDish_name());
    }

    @Override
    public void onMinusButtonClicked(DishesOfCuisine dishesOfCuisine, int num) {
      cartFragmentPresenter.updateCartItem(dishesOfCuisine);
        Log.d(TAG,dishesOfCuisine.getDish_name());
    }

    @Override
    public void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine, int flag) {
        if(flag==1){
            cartFragmentPresenter.addItemToCart(dishesOfCuisine);
            Log.d(TAG,dishesOfCuisine.getDish_name());
        }else {
            cartFragmentPresenter.updateCartItem(dishesOfCuisine);
            Log.d(TAG,dishesOfCuisine.getDish_name());
        }
    }

    @Override
    public void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine, Long parentDishId, int flag) {
        cartFragmentPresenter.addCustomizableItemToCart(dishesOfCuisine,parentDishId,flag);
    }


    @Override
    public void injectAdapterWithData() {
        adapter.fetchCartItems();
    }

    @Override
    public void renderEmptyCart() {

    }

    @Override
    public void updateBottomSheet(int totalItems, Double cartTotal) {
        if(cartTotal!=null) {
            tvTotalBillAmt.setText(String.valueOf(getActivity().getApplication().getString(R.string.rupee_symbol, cartTotal.intValue())));
            tvTotalQuantities.setText(String.valueOf(totalItems));
        }
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }
}
