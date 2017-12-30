package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.model.Cart;
import tdevm.app_ui.api.models.response.Dish;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.listeners.DishItemClickListener;
import tdevm.app_ui.widgets.IncDecButton;


/**
 * Created by Tridev on 29-10-2017.
 */

public class RecycledGridAdapter extends RecyclerView.Adapter<RecycledGridAdapter.RecycledGridViewHolder> {

    private Context mContext;
    private ArrayList<DishesOfCuisine> dishArrayList;
    private static Cart cart;
    private DishItemClickListener dishItemClickListener;


    public RecycledGridAdapter(Context context,ArrayList<DishesOfCuisine> dishArrayList) {
        this.dishArrayList = dishArrayList;
        mContext = context;
    }


    public void setOnDishItemClickListener(DishItemClickListener d){
        this.dishItemClickListener = d;
    }

    @Override
    public RecycledGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_single_dish_grid_layout, parent, false);
        return new RecycledGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycledGridViewHolder holder, int position) {
            Glide.with(mContext).load(dishArrayList.get(position).getDish_image_url()).into(holder.dishImage);
            holder.dishName.setText(dishArrayList.get(position).getDish_name());
            holder.dishPrice.setText(mContext.getString(R.string.rupee_symbol,dishArrayList.get(position).getDish_price().intValue()));
    }

    @Override
    public int getItemCount() {
        return dishArrayList.size();
    }

    public class RecycledGridViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_si_dish_name) TextView dishName;
        @BindView(R.id.tv_si_dish_price) TextView dishPrice;
        @BindView(R.id.iv_si_dish_grid) ImageView dishImage;
        @BindView(R.id.btn_id_item_dish_grid) IncDecButton incDecButton;
        public RecycledGridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);

        }

    }
}
