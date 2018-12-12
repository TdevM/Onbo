package app.onbo.modules.orders.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.FOrder.FOrderItem;
import app.onbo.modules.orders.callback.MyOrdersClickListener;
import app.onbo.utils.GeneralUtils;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder> {

    public static final String TAG = MyOrdersAdapter.class.getSimpleName();

    private Context context;
    private List<FOrder> orderList;
    private MyOrdersClickListener myOrdersClickListener;


    public MyOrdersAdapter(Context context) {
        this.context = context;
        this.orderList = new ArrayList<>();
    }

    public void onMyOrdersFetched(List<FOrder> fOrders) {
        this.orderList.clear();
        this.orderList.addAll(fOrders);
        Log.d(TAG, fOrders.size() + "Order fetched");
        notifyDataSetChanged();
    }

    public void setOrderClickListener(MyOrdersClickListener listener) {
        this.myOrdersClickListener = listener;
    }

    @NonNull
    @Override
    public MyOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_my_orders, parent, false);
        return new MyOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrdersViewHolder holder, int position) {
        holder.restaurantName.setText(orderList.get(position).getRestaurant().getRestaurant_name());
        //holder.orderTotal.setText(orderList.get(position).getGrand_total());
        holder.orderTotal.setText(context.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(orderList.get(position).getGrand_total())));
        if (orderList.get(position).getRestaurant().getLocation() != null) {
            holder.localityName.setText(orderList.get(position).getRestaurant().getLocation().getLocation_locality());
        }


        holder.orderTime.setText(orderList.get(position).getTimestamp());
        holder.orderSlug.setText(generateSlug(orderList.get(position)));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(10));
        Glide.with(context)
                .load(orderList.get(position).getRestaurant().getImage())
                .apply(requestOptions)
                .into(holder.restaurantImage);


        if (orderList.get(holder.getAdapterPosition()).getStatus() != null) {

            switch (orderList.get(holder.getAdapterPosition()).getStatus()) {
                case "COMPLETED":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_filled_circle_completed);
                    holder.orderStatus.setText(context.getString(R.string.order_status_completed));
                    break;
                case "PAYMENT_PENDING":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_filled_circle_payment_pending);
                    holder.orderStatus.setText(context.getString(R.string.order_status_pending_payment));
                    //GeneralUtils.animate(holder.orderStatusImage);
                    break;
                case "PREPARING":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_filled_circle_preparing);
                    holder.orderStatus.setText(context.getString(R.string.order_status_preparing));
                    //GeneralUtils.animate(holder.orderStatusImage);
                    break;
                case "CANCELLED":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_filled_circle_payment_failed_cancelled);
                    holder.orderStatus.setText(context.getString(R.string.order_status_cancelled));
                    break;
                case "PAYMENT_FAILED":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_filled_circle_payment_failed_cancelled);
                    holder.orderStatus.setText(context.getString(R.string.order_status_payment_failed));
                    break;
                case "PREPARED":
                    holder.orderStatusImage.setImageResource(R.drawable.ic_filled_circle_completed);
                    holder.orderStatus.setText(context.getString(R.string.order_status_prepared));
                    break;

            }
        }


        holder.bind(orderList.get(holder.getAdapterPosition()), myOrdersClickListener);


    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class MyOrdersViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_my_orders_restaurant_name)
        TextView restaurantName;
        @BindView(R.id.tv_my_orders_order_total)
        TextView orderTotal;
        @BindView(R.id.tv_my_orders_restaurant_locality_name)
        TextView localityName;
        @BindView(R.id.tv_my_orders_order_time)
        TextView orderTime;

        @BindView(R.id.iv_my_orders_order_status_image)
        ImageView orderStatusImage;

        @BindView(R.id.tv_orders_slug)
        TextView orderSlug;

        @BindView(R.id.iv_my_orders_restaurant_image)
        ImageView restaurantImage;

        @BindView(R.id.card_view_my_order_single)
        CardView cardView;

        @BindView(R.id.tv_my_orders_order_status)
        TextView orderStatus;

        public MyOrdersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final FOrder fOrder, MyOrdersClickListener myOrdersClickListener) {

            cardView.setOnClickListener(v -> {
                myOrdersClickListener.onOrderClicked(fOrder);
            });

        }
    }


    private String generateSlug(FOrder fOrder) {
        StringBuilder sb = new StringBuilder();
        List<FOrderItem> fOrderItems = fOrder.getF_order_items();
        Iterator<FOrderItem> fOrderItemIterator = fOrderItems.listIterator();
        if (fOrderItems != null) {
            while (fOrderItemIterator.hasNext()) {
                FOrderItem item = fOrderItemIterator.next();
                sb.append(item.getItem_name());
                sb.append(", ");
            }

        }
        return sb.toString();
    }
}
