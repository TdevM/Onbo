package tdevm.app_ui.root.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
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
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.HeterogeneousObject;
import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.utils.GeneralUtils;

public class MenuAdapterRestaurantDetail extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String TAG = MenuAdapterRestaurantDetail.class.getSimpleName();
    private Context mContext;
    private List<CuisineMenuItems> cuisineList;

    private List<HeterogeneousObject> heterogeneousObjects;

    public MenuAdapterRestaurantDetail(Context mContext) {
        this.mContext = mContext;
        this.cuisineList = new ArrayList<>();
        heterogeneousObjects = new ArrayList<>();
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
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_menu_res_detail, parent, false);
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
                holder.itemName.setText(object.getMenuItem().getItemName());
                holder.itemPrice.setText(mContext.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(String.valueOf(object.getMenuItem().getItemPrice()))));

                if (object.getMenuItem().getIsVeg()) {
                    holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.veg_symbol));
                } else if (!object.getMenuItem().getIsVeg()) {
                    holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.non_veg_symbol));
                }
                holder.description.setText(object.getMenuItem().getDescription());
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
        TextView itemName;
        @BindView(R.id.tv_si_dish_price)
        TextView itemPrice;

        @BindView(R.id.tv_menu_item_item_detail)
        TextView description;

        @BindView(R.id.iv_veg_non_veg_grid)
        AppCompatImageView vegNonVegIndicator;

        public RecycledGridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}