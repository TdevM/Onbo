package app.onbo.modules.fc.adapters;

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

import app.onbo.R;
import app.onbo.api.models.response.v2.FcRestaurant;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.menu.Cuisine;
import app.onbo.root.callbacks.RestaurantItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FCRestaurantListAdapter extends RecyclerView.Adapter<FCRestaurantListAdapter.RestaurantListViewHolder> {

    private List<FcRestaurant> fcRestaurants;
    private Context context;
    private RestaurantItemClickListener listener;


    public FCRestaurantListAdapter(Context context) {
        this.context = context;
        fcRestaurants = new ArrayList<>();
    }


    public void setRestaurantItemClickedListener(RestaurantItemClickListener clickedListener) {
        this.listener = clickedListener;
    }

    @NonNull
    @Override
    public RestaurantListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_restaurant_list_fc, parent, false);
        return new RestaurantListViewHolder(view);
    }


    public void onRestaurantsFetched(List<FcRestaurant> fcRestaurants) {
        this.fcRestaurants.clear();
        this.fcRestaurants.addAll(fcRestaurants);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(context)
                .load(fcRestaurants.get(position).getRestaurant().getImage())
                .apply(requestOptions)
                .into(holder.restaurantImageView);
        holder.restaurantName.setText(fcRestaurants.get(position).getRestaurant().getRestaurant_name());
        holder.restaurantLocality.setText(fcRestaurants.get(position).getRestaurant().getAddress_complete());
        holder.costForTwo.setText(String.format("%s for two", String.valueOf(fcRestaurants.get(position).getRestaurant().getAvg_cost_for_two())));

        if (fcRestaurants.get(position).getRestaurant().getCuisines() != null) {
            holder.restaurantCuisines.setText(generateCuisineSlug(fcRestaurants.get(position).getRestaurant().getCuisines()));
        }
        if (fcRestaurants.get(position).getRestaurant().getRestaurant_mode() != null) {
            if (fcRestaurants.get(position).getRestaurant().getRestaurant_mode().equals("FOOD_COURT")) {
                holder.selfOrdering.setText(context.getString(R.string.food_court));
            }
        }
        holder.bind(fcRestaurants.get(position).getRestaurant(), listener);
    }

    @Override
    public int getItemCount() {
        return fcRestaurants.size();
    }

    public class RestaurantListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.card_view_restaurant_list_item)
        CardView cardView;
        @BindView(R.id.tv_restaurant_name)
        TextView restaurantName;
        @BindView(R.id.iv_restaurant_image)
        ImageView restaurantImageView;

        @BindView(R.id.tv_restaurant_locality_new)
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

