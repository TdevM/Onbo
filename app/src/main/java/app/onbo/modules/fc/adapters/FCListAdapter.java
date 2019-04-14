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

import app.onbo.R;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.root.callbacks.FoodCourtClickListener;
import app.onbo.root.callbacks.RestaurantItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FCListAdapter extends RecyclerView.Adapter<FCListAdapter.FCListViewHolder> {

    public static final String TAG =  FCListAdapter.class.getSimpleName();

    private List<FoodCourt> foodCourtList;
    private FoodCourtClickListener foodCourtClickListener;

    private Context context;

    public FCListAdapter(Context context) {
        this.context = context;
        this.foodCourtList = new ArrayList<>();
    }

    public void setFoodCourtClickListener(FoodCourtClickListener listener){
        this.foodCourtClickListener = listener;
    }


    public void onFCListFetched(List<FoodCourt> foodCourts){
        this.foodCourtList.clear();
        this.foodCourtList.addAll(foodCourts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FCListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_rv_fc_list_pickup, viewGroup, false);
        return new FCListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FCListViewHolder fcListViewHolder, int position) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(16));
        Glide.with(context)
                .load(foodCourtList.get(position).getImageUrl())
                .apply(requestOptions)
                .into(fcListViewHolder.restaurantImage);
        fcListViewHolder.restaurantName.setText(foodCourtList.get(position).getFcName());
        fcListViewHolder.restaurantLocality.setText(foodCourtList.get(position).getAddressComplete());
        fcListViewHolder.bind(foodCourtList.get(position),foodCourtClickListener);
    }

    @Override
    public int getItemCount() {
        return foodCourtList.size();
    }

    public class FCListViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.tv_restaurant_name)
        TextView restaurantName;

        @BindView(R.id.card_view_fc_list_item)
        CardView cardView;

        @BindView(R.id.tv_restaurant_locality_new)
        TextView restaurantLocality;


        @BindView(R.id.iv_restaurant_image)
        ImageView restaurantImage;

        public void bind(final FoodCourt foodCourt, final FoodCourtClickListener clickListener) {
            cardView.setOnClickListener(v -> {
                clickListener.onFoodCourtItemClicked(foodCourt);
            });

        }

        public FCListViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
