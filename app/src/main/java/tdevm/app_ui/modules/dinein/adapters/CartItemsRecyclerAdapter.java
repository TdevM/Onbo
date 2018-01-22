package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

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
        cartItemArrayList = new ArrayList<>();
        Log.d("Cart", String.valueOf(cartHelper.getCartTotalItems()));
        List<CartItem> cartItems = cartHelper.getCartItems();
        cartItemArrayList.addAll(cartItems);
        Glide.with(context).load(cartItemArrayList.get(position).getDishesOfCuisine().getDish_image_url()).into(holder.dishImage);
        holder.dishName.setText(cartItemArrayList.get(position).getDishesOfCuisine().getDish_name());
        holder.dishPrice.setText(String.valueOf(context.getString(R.string.rupee_symbol, cartItemArrayList.get(position).getPrice().intValue())));
        holder.incDecButton.setNumber(cartItemArrayList.get(position).getQuantity(), true);
        holder.bind(cartItemArrayList.get(position).getDishesOfCuisine(), dishItemClickListener);
    }

    @Override
    public int getItemCount() {
        List<CartItem> cartItems = cartHelper.getCartItems();
        return cartItems.size();
    }

    public void fetchCartItems() {

    }


    public class CartItemsViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.cart_item_image_view)
        ImageView dishImage;
        @BindView(R.id.cart_item_tv_dish_name)
        TextView dishName;
        @BindView(R.id.cart_item_tv_dish_price)
        TextView dishPrice;
        @BindView(R.id.cart_item_qty_widget)
        IncDecButton incDecButton;

        public CartItemsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void bind(final DishesOfCuisine dish, final DishItemClickListener dishItemClickListener) {
            incDecButton.setOnButtonsClickedListener(new IncDecButton.OnButtonsClickedListener() {
                @Override
                public void onPlusClicked(int num) {
                    if (dish.getIs_customizable()) {
                        if (dish.getIs_child()) {
                            dishItemClickListener.onCustomizableItemClicked(dish, dish.getParent_id(), 1);
                        } else if (dish.getIs_parent()) {
                            dishItemClickListener.onCustomizableItemClicked(dish, 1);
                        }
                    } else {
                        dishItemClickListener.onPlusButtonClicked(dish, num);
                        Log.d(TAG,"Click+");
                    }
                }

                @Override
                public void onMinusClicked(int num) {
                    if (dish.getIs_customizable()) {
                        if (dish.getIs_child()) {
                            dishItemClickListener.onCustomizableItemClicked(dish, dish.getParent_id(), 0);
                        } else if (dish.getIs_parent()) {
                            dishItemClickListener.onCustomizableItemClicked(dish, 0);
                        }
                    } else {
                        dishItemClickListener.onMinusButtonClicked(dish, num);
                        Log.d(TAG,"Click+");

                    }
                }
            });
        }
    }

}
