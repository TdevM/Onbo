package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.modules.dinein.callbacks.MenuItemClickListener;
import tdevm.app_ui.modules.dinein.fragments.SingleCuisineGridPresenter;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.widgets.IncDecButton;


/**
 * Created by Tridev on 29-10-2017.
 */

public class MenuItemsAdapter extends RecyclerView.Adapter<MenuItemsAdapter.RecycledGridViewHolder> {

    private Context mContext;
    private List<MenuItem> menuItemList;
    private List<CartSelection> cartSelectionList;
    private MenuItemClickListener menuItemClickListener;
    private SingleCuisineGridPresenter singleCuisineGridPresenter;
    private CartHelper cartHelper;
    private int itemQty;

    public MenuItemsAdapter(Context context,List<MenuItem> menuItems, CartHelper cartHelper) {
        this.mContext = context;
        this.cartHelper = cartHelper;
        this.menuItemList = new ArrayList<>();
        this.menuItemList.clear();
        this.menuItemList.addAll(menuItems);
    }

    public MenuItemsAdapter() {
    }

    public void setDishItemClickListenerCallback(MenuItemClickListener menuItemClickListenerCallback) {
        this.menuItemClickListener = menuItemClickListenerCallback;
    }

    public void setItemQty(int qty){
        this.itemQty = qty;
    }



    public void onCartSelectionListFetched(){
        this.cartSelectionList.addAll(cartSelectionList);
    }

    @Override
    public RecycledGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_cuisine_menu_items, parent, false);
        return new RecycledGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycledGridViewHolder holder, int position) {

        //Glide.with(mContext).load(dishArrayList.get(position).getItemImage()).into(holder.dishImage);
        holder.dishName.setText(menuItemList.get(position).getItemName());
        holder.dishPrice.setText(mContext.getString(R.string.rupee_symbol, menuItemList.get(position).getItemPrice() * 0.01));
        if (menuItemList.get(position).getIsVeg()) {
            holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_veg_indicator));
        } else if (!menuItemList.get(position).getIsVeg()) {
            holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.non_veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_non_veg_indicator));
        }
        Single<CartSelection> selection = cartHelper.getCartSelectionById(Long.parseLong(menuItemList.get(position).getItemId()));
        selection.subscribe(new DisposableSingleObserver<CartSelection>() {
            @Override
            public void onSuccess(CartSelection cartSelection) {
                if (cartSelection != null) {
                    holder.incDecButton.setNumber(cartSelection.getQty(), true);
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });



        holder.bind(menuItemList.get(position), menuItemClickListener);
    }

    @Override
    public int getItemCount() {
        return menuItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class RecycledGridViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_si_dish_name)
        TextView dishName;
        @BindView(R.id.tv_si_dish_price)
        TextView dishPrice;

        @BindView(R.id.btn_id_item_dish_grid)
        IncDecButton incDecButton;
        @BindView(R.id.iv_veg_non_veg_grid)
        AppCompatImageView vegNonVegIndicator;

        public RecycledGridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final MenuItem menuItem, final MenuItemClickListener menuItemClickListener) {
            incDecButton.setOnButtonsClickedListener(new IncDecButton.OnButtonsClickedListener() {
                @Override
                public void onPlusClicked(int num) {

                    if (menuItem.getCustomizable()) {
                        menuItemClickListener.onCustomizableItemClicked(menuItem, 1);
                        if (incDecButton.getNumber() == 0) {
                            incDecButton.setNumber(0, true);
                        }
                    } else {
                        menuItemClickListener.onPlusButtonClicked(menuItem, num);
                    }
                }

                @Override
                public void onMinusClicked(int num) {
                    if (menuItem.getCustomizable()) {
                        menuItemClickListener.onCustomizableItemClicked(menuItem, 0);
                    } else {
                        menuItemClickListener.onMinusButtonClicked(menuItem, num);
                    }
                }
            });
            // Open dish reviews
            //dishImage.setOnClickListener(v -> menuItemClickListener.onItemImageClicked(menuItem));
        }

    }
}
