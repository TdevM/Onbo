package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
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
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.modules.dinein.callbacks.MenuItemClickListener;
import tdevm.app_ui.utils.CartHelper;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuAdapterViewHolder> {


    public static final String TAG = MenuAdapter.class.getSimpleName();


    private Context mContext;
    private List<CuisineMenuItems> cuisineList;
    private MenuItemClickListener menuItemClickListener;
    private CartHelper cartHelper;
    RecyclerView.RecycledViewPool pool;

    public MenuAdapter(Context mContext, CartHelper helper) {
        this.mContext = mContext;
        this.cartHelper = helper;
        this.cuisineList = new ArrayList<>();
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
        Log.d(TAG, "Cuisine list menu items fetched");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MenuAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_cuisine_rv, parent, false);
        return new MenuAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuAdapterViewHolder holder, int position) {
        if (cuisineList != null) {
            holder.cuisineName.setText(cuisineList.get(position).getCuisine_name());
            RecyclerView.LayoutManager manager = new LinearLayoutManager(mContext);
            holder.recyclerView.setLayoutManager(manager);
            holder.recyclerView.setRecycledViewPool(pool);
            MenuItemsAdapter adapter = new MenuItemsAdapter(mContext, cuisineList.get(position).getMenu_items(), cartHelper);
            adapter.setDishItemClickListenerCallback(menuItemClickListener);
            holder.recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public int getItemCount() {

        return cuisineList.size();
    }

    class MenuAdapterViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_cuisine_name_menu_v2)
        TextView cuisineName;

        @BindView(R.id.rv_cuisine_list_menu_items)
        RecyclerView recyclerView;

        public MenuAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
