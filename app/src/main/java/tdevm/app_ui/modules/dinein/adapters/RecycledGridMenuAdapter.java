package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.cart.CartSelectionDao;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.callbacks.DishItemClickListener;
import tdevm.app_ui.modules.dinein.fragments.SingleCuisineGridPresenter;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.widgets.IncDecButton;


/**
 * Created by Tridev on 29-10-2017.
 */

public class RecycledGridMenuAdapter extends RecyclerView.Adapter<RecycledGridMenuAdapter.RecycledGridViewHolder> {

    private Context mContext;
    private ArrayList<DishesOfCuisine> dishArrayList;
    private DishItemClickListener dishItemClickListener;
    private SingleCuisineGridPresenter singleCuisineGridPresenter;
    private CartHelper cartHelper;

    public RecycledGridMenuAdapter(Context context, SingleCuisineGridPresenter presenter, CartHelper helper) {
        this.mContext = context;
        this.singleCuisineGridPresenter = presenter;
        this.cartHelper = helper;
        dishArrayList = new ArrayList<>();
    }

    public void setDishItemClickListenerCallback(DishItemClickListener dishItemClickListenerCallback) {
        this.dishItemClickListener = dishItemClickListenerCallback;
    }

    public void onDishesFetched(ArrayList<DishesOfCuisine> dishesOfCuisines) {
        dishArrayList.addAll(dishesOfCuisines);
        notifyDataSetChanged();
    }

    @Override
    public RecycledGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_dish_grid_layout, parent, false);
        return new RecycledGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycledGridViewHolder holder, int position) {

        Glide.with(mContext).load(dishArrayList.get(position).getDish_image_url()).into(holder.dishImage);
        holder.dishName.setText(dishArrayList.get(position).getDish_name());
        holder.dishPrice.setText(mContext.getString(R.string.rupee_symbol, dishArrayList.get(position).getDish_price().intValue()));
        if(dishArrayList.get(position).getDish_vegetarian()){
           holder.vegNonVegIndicator.setImageResource(R.drawable.ic_veg);
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_veg));
        }else if(!dishArrayList.get(position).getDish_vegetarian()){
           holder.vegNonVegIndicator.setImageResource(R.drawable.ic_non_veg);
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_non_veg));
        }
            CartSelection selection = cartHelper.getCartSelectionById(dishArrayList.get(position).getDish_id());
            if(selection!=null){
                holder.incDecButton.setNumber(selection.getQty(),true);
            }

        holder.bind(dishArrayList.get(position), dishItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dishArrayList.size();
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
        @BindView(R.id.iv_si_dish_grid)
        ImageView dishImage;
        @BindView(R.id.btn_id_item_dish_grid)
        IncDecButton incDecButton;
        @BindView(R.id.iv_veg_non_veg_grid)
        AppCompatImageView vegNonVegIndicator;

        public RecycledGridViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final DishesOfCuisine dishesOfCuisine, final DishItemClickListener dishItemClickListener) {
            incDecButton.setOnButtonsClickedListener(new IncDecButton.OnButtonsClickedListener() {
                @Override
                public void onPlusClicked(int num) {
                    if (dishesOfCuisine.getIs_customizable()) {
                        dishItemClickListener.onCustomizableItemClicked(dishesOfCuisine, 1);
                        if (incDecButton.getNumber() == 0) {
                            incDecButton.setNumber(0, true);
                        }
                    } else {
                        dishItemClickListener.onPlusButtonClicked(dishesOfCuisine, num);
                    }
                }

                @Override
                public void onMinusClicked(int num) {
                    if (dishesOfCuisine.getIs_customizable()) {
                        dishItemClickListener.onCustomizableItemClicked(dishesOfCuisine, 0);
                    } else {
                        dishItemClickListener.onMinusButtonClicked(dishesOfCuisine, num);
                    }
                }
            });
            // Open dish reviews
            dishImage.setOnClickListener(v -> dishItemClickListener.onDishImageClicked(dishesOfCuisine));
        }

    }
}
