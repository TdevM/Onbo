package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.models.response.HeterogeneousObject;
import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.modules.dinein.callbacks.MenuItemClickListener;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.widgets.IncDecButton;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = MenuAdapter.class.getSimpleName();
    private Context mContext;
    private List<CuisineMenuItems> cuisineList;
    private MenuItemClickListener menuItemClickListener;
    private CartHelper cartHelper;
    RecyclerView.RecycledViewPool pool;

    private List<HeterogeneousObject> heterogeneousObjects;

    public MenuAdapter(Context mContext, CartHelper helper) {
        this.mContext = mContext;
        this.cartHelper = helper;
        this.cuisineList = new ArrayList<>();
        heterogeneousObjects = new ArrayList<>();
        pool = new RecyclerView.RecycledViewPool();
    }

    public void setDishItemClickListenerCallback(MenuItemClickListener menuItemClickListenerCallback) {
        this.menuItemClickListener = menuItemClickListenerCallback;
    }

    public void onCuisineListFetched(List<CuisineMenuItems> cuisines) {
        this.cuisineList.clear();
        ListIterator<CuisineMenuItems> iterator = cuisines.listIterator();
        while (iterator.hasNext()) {
            if (iterator.next().getMenu_items().size() <= 0) {
                iterator.remove();
            }
        }
        this.cuisineList.addAll(cuisines);
        this.heterogeneousObjects.clear();
        ListIterator<CuisineMenuItems> sortedMenu = cuisineList.listIterator();
        while (sortedMenu.hasNext()) {
            CuisineMenuItems item = sortedMenu.next();
            HeterogeneousObject cuisine = new HeterogeneousObject(0, "CUISINE", item);
            heterogeneousObjects.add(cuisine);
            for (int i = 0; i < item.getMenu_items().size(); i++) {
                HeterogeneousObject menuItem = new HeterogeneousObject(1, "MENU_ITEM", item.getMenu_items().get(i));
                heterogeneousObjects.add(menuItem);

            }
        }
//        ListIterator<HeterogeneousObject> objectListIterator = heterogeneousObjects.listIterator();
//        while (objectListIterator.hasNext()) {
//            Log.d(TAG, "RV Item :" + objectListIterator.next().toString());
//        }

        Log.d(TAG, "Cuisine list menu items fetched");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == 0) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_cuisine_rv, parent, false);
            return new MenuAdapterViewHolder(view);

        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_cuisine_menu_items, parent, false);
            return new RecycledGridViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        HeterogeneousObject object = heterogeneousObjects.get(position);
        if (object.getItemType() == 0) {
            if (object.getCuisine() != null) {
                MenuAdapterViewHolder holder = (MenuAdapterViewHolder) viewHolder;
                holder.cuisineName.setText(object.getCuisine().getCuisine_name());

            }
        } else {
            if (object.getMenuItem() != null) {
                RecycledGridViewHolder holder = (RecycledGridViewHolder) viewHolder;
                holder.dishName.setText(object.getMenuItem().getItemName());
                holder.dishPrice.setText(mContext.getString(R.string.rupee_symbol, object.getMenuItem().getItemPrice() * 0.01));
                holder.itemDescription.setText(object.getMenuItem().getDescription());

                if (object.getMenuItem().getIsVeg()) {
                    holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.veg_symbol));
                    //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_veg_indicator));
                } else if (!object.getMenuItem().getIsVeg()) {
                    holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.non_veg_symbol));
                    //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_non_veg_indicator));
                }
                Single<CartSelection> selection = cartHelper.getCartSelectionById(Long.parseLong(object.getMenuItem().getItemId()));
                selection.subscribe(new DisposableSingleObserver<CartSelection>() {
                    @Override
                    public void onSuccess(CartSelection cartSelection) {
                        if (cartSelection != null) {
                            holder.incDecButton.setNumber(cartSelection.getQty(), true);
                        } else {
                            holder.incDecButton.setNumber(0, false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        holder.incDecButton.setNumber(0, false);
                    }
                });
                holder.bind(object.getMenuItem(), menuItemClickListener);
            }
        }

    }

    @Override
    public int getItemCount() {
        return heterogeneousObjects.size();
    }

    class MenuAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_cuisine_name_menu_v2)
        TextView cuisineName;


        public MenuAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        super.getItemViewType(position);
        if (heterogeneousObjects.get(position).getItemType() == 0) {
            return 0;
        } else {
            return 1;
        }
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

        @BindView(R.id.tv_si_item_description)
        TextView itemDescription;

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