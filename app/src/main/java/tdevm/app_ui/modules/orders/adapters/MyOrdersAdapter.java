package tdevm.app_ui.modules.orders.adapters;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrderItem;
import tdevm.app_ui.modules.orders.callback.MyOrdersClickListener;

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
        holder.orderTotal.setText(context.getString(R.string.rupee_symbol, String.valueOf(Integer.parseInt(orderList.get(position).getGrand_total()) * 0.01)));
        if (orderList.get(position).getRestaurant().getLocation() != null) {
            holder.localityName.setText(orderList.get(position).getRestaurant().getLocation().getLocation_locality());
        }


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date date = null;
        try {
            date = dateFormat.parse(orderList.get(position).getTimestamp());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(date);
        holder.orderTime.setText(date.toString());
        holder.orderSlug.setText(generateSlug(orderList.get(position)));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions = requestOptions.transforms(new CenterCrop(), new RoundedCorners(10));
        Glide.with(context)
                .load(orderList.get(position).getRestaurant().getImage())
                .apply(requestOptions)
                .into(holder.restaurantImage);
        holder.orderStatus.setText(orderList.get(position).getStatus());
        holder.bind(orderList.get(position), myOrdersClickListener);


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
