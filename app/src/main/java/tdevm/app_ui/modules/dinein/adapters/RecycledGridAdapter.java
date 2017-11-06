package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;

import java.util.ArrayList;

import tdevm.app_ui.R;
import tdevm.app_ui.api.cart.model.Cart;
import tdevm.app_ui.api.models.response.Dish;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.listeners.DishItemClickListener;


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


    public void updateRecylerAdapterCart( Cart cartItems){
        cart = cartItems;
    }

    public void setOnDishItemClickListener(DishItemClickListener d){
        this.dishItemClickListener = d;
    }

    @Override
    public RecycledGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.gridview_layout, parent, false);
        return new RecycledGridViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecycledGridViewHolder holder, int position) {
            Glide.with(mContext).load(dishArrayList.get(position).getDish_image_url()).into(holder.imageViewCoverArt);
            holder.textView.setText(dishArrayList.get(position).getDish_name());
            holder.textViewPrice.setText(mContext.getString(R.string.rupee_symbol,dishArrayList.get(position).getDish_price().intValue()));
            //holder.bind(dishArrayList.get(position), dishItemClickListener);
    }

    @Override
    public int getItemCount() {
        return dishArrayList.size();
    }

    public class RecycledGridViewHolder extends RecyclerView.ViewHolder{
         TextView textView;
         ImageView imageViewCoverArt;
         TextView textViewPrice;
         ElegantNumberButton elegantNumberButton;
        public RecycledGridViewHolder(View itemView) {
            super(itemView);

            textView =  itemView.findViewById(R.id.android_gridview_text);
            imageViewCoverArt = itemView.findViewById(R.id.android_gridview_image);
            elegantNumberButton = itemView.findViewById(R.id.elegant_number_button);
            textViewPrice = itemView.findViewById(R.id.dish_price);

        }

//        public void bind(final DishesOfCuisine dishesOfCuisine, final DishItemClickListener dishItemClickListener) {
//            elegantNumberButton.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
//                @Override
//                public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
//                    dishItemClickListener.getDishItemQuant(dishesOfCuisine,oldValue,newValue);
//                }
//            });
//        }
    }
}
