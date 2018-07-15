package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.callbacks.DishItemClickListener;
import tdevm.app_ui.modules.dinein.fragments.CartFragmentPresenterImpl;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.widgets.IncDecButton;


/**
 * Created by Tridev on 28-10-2017.
 */

public class CartItemsRecyclerAdapter extends RecyclerView.Adapter<CartItemsRecyclerAdapter.CartItemsViewHolder> {

    public static final String TAG = CartItemsRecyclerAdapter.class.getSimpleName();
    private Context context;
    private List<CartItem> cartItemArrayList;
    private DishItemClickListener dishItemClickListener;
    private CartHelper cartHelper;
    private CartFragmentPresenterImpl cartFragmentPresenter;

    public CartItemsRecyclerAdapter(Context context, CartFragmentPresenterImpl cartFragmentPresenter, CartHelper cartHelper) {
        this.context = context;
        this.cartFragmentPresenter = cartFragmentPresenter;
        this.cartHelper = cartHelper;
    }

    public void setOnDishItemClickListener(DishItemClickListener d) {
        this.dishItemClickListener = d;
    }


    @Override
    public CartItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_recycler_item, parent, false);
        return new CartItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartItemsViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder");
        cartItemArrayList = new ArrayList<>();
        Log.d("Cart", String.valueOf(cartHelper.getCartTotalItems()));
        List<CartItem> cartItems = cartHelper.getCartItems();
        cartItemArrayList.addAll(cartItems);
        holder.dishName.setText(cartItemArrayList.get(position).getDishesOfCuisine().getDish_name());
        holder.dishPrice.setText(String.valueOf(context.getString(R.string.rupee_symbol, cartItemArrayList.get(position).getPrice().intValue())));
        holder.incDecButton.setNumber(cartItemArrayList.get(position).getQuantity(), true);
        if(cartItemArrayList.get(position).getDishesOfCuisine().getDish_vegetarian()){
            holder.vegNonVegIndicator.setImageDrawable(context.getResources().getDrawable(R.drawable.veg_symbol));
        }else if(!cartItemArrayList.get(position).getDishesOfCuisine().getDish_vegetarian()){
            holder.vegNonVegIndicator.setImageDrawable(context.getResources().getDrawable(R.drawable.non_veg_symbol));
        }
        holder.bind(cartItemArrayList.get(position).getDishesOfCuisine(), dishItemClickListener);
    }

    @Override
    public int getItemCount() {
        int size;
        List<CartItem> cartItems = cartHelper.getCartItems();
        if(cartItems!=null){
            size = cartItems.size();
            Log.d(TAG,"SizeE: "+String.valueOf(cartItems.size()));
        }else {
            size = 0;
            Log.d(TAG,"cartItems is null, size is : "+ size);
        }
        return size;
    }


    public class CartItemsViewHolder extends RecyclerView.ViewHolder {



        @BindView(R.id.cart_item_tv_dish_name)
        TextView dishName;
        @BindView(R.id.cart_item_tv_dish_price)
        TextView dishPrice;
        @BindView(R.id.cart_item_qty_widget)
        IncDecButton incDecButton;
        @BindView(R.id.iv_veg_non_veg_cart)
        AppCompatImageView vegNonVegIndicator;

        public CartItemsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(final DishesOfCuisine dish, final DishItemClickListener dishItemClickListener) {
            incDecButton.setOnButtonsClickedListener(new IncDecButton.OnButtonsClickedListener() {
                @Override
                public void onPlusClicked(int num) {
                    if (dish.getIs_customizable()) {
//                        if (dish.getIs_child()) {
//                            dishItemClickListener.onCustomizableItemClicked(dish, dish.getParent_id(), 1);
//                        } else if (dish.getIs_parent()) {
//                            dishItemClickListener.onCustomizableItemClicked(dish, 1);
//                        }
                    } else {
                       // dishItemClickListener.onPlusButtonClicked(dish, num);
                        Log.d(TAG,"Click+");
                    }
                }

                @Override
                public void onMinusClicked(int num) {
                    if (dish.getIs_customizable()) {
//                        if (dish.getIs_child()) {
//                            dishItemClickListener.onCustomizableItemClicked(dish, dish.getParent_id(), 0);
//                        } else if (dish.getIs_parent()) {
//                            dishItemClickListener.onCustomizableItemClicked(dish, 0);
//                        }
                    } else {
                      //  dishItemClickListener.onMinusButtonClicked(dish, num);
                        Log.d(TAG,"Click+");

                    }
                }
            });
        }
    }

}
