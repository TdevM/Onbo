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
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.models.cart.MenuItem;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;
import tdevm.app_ui.modules.dinein.callbacks.CartItemClickListener;
import tdevm.app_ui.modules.dinein.fragments.CartFragmentPresenterImpl;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.utils.GeneralUtils;
import tdevm.app_ui.widgets.IncDecButton;


/**
 * Created by Tridev on 28-10-2017.
 */

public class CartItemsRecyclerAdapter extends RecyclerView.Adapter<CartItemsRecyclerAdapter.CartItemsViewHolder> {

    public static final String TAG = CartItemsRecyclerAdapter.class.getSimpleName();
    private Context context;
    private List<CartItem> cartItemArrayList;
    private CartItemClickListener cartItemClickListener;
    private CartHelper cartHelper;
    private CartFragmentPresenterImpl cartFragmentPresenter;

    public CartItemsRecyclerAdapter(Context context, CartFragmentPresenterImpl cartFragmentPresenter, CartHelper cartHelper) {
        this.context = context;
        this.cartFragmentPresenter = cartFragmentPresenter;
        this.cartHelper = cartHelper;
        this.cartItemArrayList = new ArrayList<>();
    }

    public void setOnCartItemClickListener(CartItemClickListener d) {
        this.cartItemClickListener = d;
    }

    public void onCartItemFetched(List<CartItem> items) {
        this.cartItemArrayList.clear();
        this.cartItemArrayList.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public CartItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart_recycler, parent, false);
        return new CartItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CartItemsViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder");
        Log.d("Cart", String.valueOf(cartHelper.getCartTotalItems()));
        MenuItem item = cartItemArrayList.get(position).getMenuItem();
        holder.dishName.setText(item.getItemName());
        holder.dishPrice.setText(context.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(String.valueOf(cartItemArrayList.get(position).getPrice()))));
        holder.incDecButton.setNumber(cartItemArrayList.get(position).getQuantity(), true);
        if (item.getVeg()) {
            holder.vegNonVegIndicator.setImageDrawable(context.getResources().getDrawable(R.drawable.veg_symbol));
        } else if (!item.getVeg()) {
            holder.vegNonVegIndicator.setImageDrawable(context.getResources().getDrawable(R.drawable.non_veg_symbol));
        }
        if(item.getCustomizable()){
            holder.cartSlug.setText(generateSlug(item));
        }else {
            holder.cartSlug.setVisibility(View.GONE);
        }
        holder.bind(cartItemArrayList.get(position).getMenuItem(), cartItemArrayList.get(position).getItem_hash(), cartItemClickListener);
    }

    @Override
    public int getItemCount() {
        return cartItemArrayList.size();
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
        @BindView(R.id.tv_cart_slug)
        TextView cartSlug;

        public CartItemsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final MenuItem menuItem, String itemHash, final CartItemClickListener menuItemClickListener) {
            incDecButton.setOnButtonsClickedListener(new IncDecButton.OnButtonsClickedListener() {
                @Override
                public void onPlusClicked(int num) {
                    if (menuItem.getCustomizable()) {
                        cartItemClickListener.onCustomizableItemClicked(menuItem, 1);
                        if (incDecButton.getNumber() == 0) {
                            incDecButton.setNumber(0, true);
                        }
                    } else {
                        cartItemClickListener.onPlusButtonClicked(menuItem, num);
                    }
                }

                @Override
                public void onMinusClicked(int num) {
                    if (menuItem.getCustomizable()) {
                        cartItemClickListener.onCustomizableItemClicked(menuItem, 0);
                    } else {
                        cartItemClickListener.onMinusButtonClicked(menuItem, num);
                    }
                }
            });

        }
    }


    private String generateSlug(MenuItem item) {
        StringBuilder sb = new StringBuilder();
        List<MenuAddOn> menuAddOns = item.getMenuAddOns();
        List<MenuVOption> menuVOptions = item.getMenuVariantOptions();
        Iterator<MenuVOption> menuVOptionIterator;
        Iterator<MenuAddOn> menuAddOnIterator;
        if (menuVOptions != null) {
            menuVOptionIterator = menuVOptions.listIterator();
            while (menuVOptionIterator.hasNext()) {
                MenuVOption option = menuVOptionIterator.next();
                sb.append(" ");
                sb.append(option.getOptionName());
            }
            if (menuAddOns != null) {
                menuAddOnIterator = menuAddOns.listIterator();
                while (menuAddOnIterator.hasNext()) {
                    MenuAddOn addOn = menuAddOnIterator.next();
                    sb.append(" ");
                    sb.append(addOn.getAddOnName());
                }
            }

        }
        return sb.toString();
    }

}
