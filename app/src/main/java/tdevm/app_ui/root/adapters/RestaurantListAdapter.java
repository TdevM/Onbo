package tdevm.app_ui.root.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.api.models.response.v2.menu.Cuisine;
import tdevm.app_ui.root.callbacks.RestaurantItemClickListener;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.RestaurantListViewHolder> {

    private List<Restaurant> restaurants;
    private Context context;
    private RestaurantItemClickListener listener;


    public RestaurantListAdapter(Context context) {
        this.context = context;
        restaurants = new ArrayList<>();
    }


    public void setRestaurantItemClickedListener(RestaurantItemClickListener clickedListener) {
        this.listener = clickedListener;
    }

    @NonNull
    @Override
    public RestaurantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_restaurant_list, parent, false);
        return new RestaurantListViewHolder(view);
    }


    public void onRestaurantsFetched(List<Restaurant> restaurantList) {
        this.restaurants.clear();
        this.restaurants.addAll(restaurantList);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(context)
                .load(restaurants.get(position).getImage())
                .apply(requestOptions)
                .into(holder.restaurantImageView);
        holder.restaurantName.setText(restaurants.get(position).getRestaurant_name());
        holder.restaurantLocality.setText(restaurants.get(position).getLocation().getLocation_locality());
        holder.costForTwo.setText(String.format("%s for two", String.valueOf(restaurants.get(position).getAvg_cost_for_two())));
        if (restaurants.get(position).getRating() != null) {
            Double d = Double.parseDouble(restaurants.get(position).getRating().getRestaurant_avg_rating());
            double roundOff = Math.round(d * 100.0) / 100.0;
            holder.starRating.setText(String.valueOf(roundOff));
        }
        if (restaurants.get(position).getQr_active()) {
            holder.qrEnabled.setText("QR Active");
        } else {
            holder.qrEnabled.setText("QR Inactive");
        }
        if (restaurants.get(position).getCuisines() != null) {
            holder.restaurantCuisines.setText(generateCuisineSlug(restaurants.get(position).getCuisines()));
        }
        if(restaurants.get(position).getRestaurant_mode()!=null){
            if(restaurants.get(position).getRestaurant_mode().equals("DINE_IN")){
                holder.selfOrdering.setText(context.getString(R.string.dine_id));
            }else {
                holder.selfOrdering.setText(context.getString(R.string.quick_serve));
            }
        }
        holder.bind(restaurants.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return restaurants.size();
    }

    public class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view_restaurant_list_item)
        CardView cardView;
        @BindView(R.id.tv_restaurant_name)
        TextView restaurantName;
        @BindView(R.id.iv_restaurant_image)
        ImageView restaurantImageView;

        @BindView(R.id.tv_restaurant_locality)
        TextView restaurantLocality;

        @BindView(R.id.tv_restaurant_cuisine)
        TextView restaurantCuisines;

        @BindView(R.id.tv_restaurant_rating_start)
        TextView starRating;

        @BindView(R.id.tv_cost_for_two)
        TextView costForTwo;

        @BindView(R.id.tv_self_ordering)
        TextView selfOrdering;

        @BindView(R.id.tv_qr_enabled)
        TextView qrEnabled;

        public RestaurantListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final Restaurant restaurant, final RestaurantItemClickListener clickListener) {
            cardView.setOnClickListener(v -> {
                clickListener.onRestaurantItemClicked(restaurant);
            });

        }
    }

    public String generateCuisineSlug(List<Cuisine> cuisines) {
        ListIterator<Cuisine> cuisineListIterator = cuisines.listIterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (cuisineListIterator.hasNext()) {
            Cuisine cuisine = cuisineListIterator.next();
            stringBuilder.append(cuisine.getCuisine_name());
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}
