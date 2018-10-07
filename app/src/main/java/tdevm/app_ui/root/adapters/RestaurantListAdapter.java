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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.root.callbacks.RestaurantItemClickListener;

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
        Glide.with(context).load(restaurants.get(position).getImage()).into(holder.restaurantImageView);
        holder.restaurantName.setText(restaurants.get(position).getRestaurant_name());
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
}
