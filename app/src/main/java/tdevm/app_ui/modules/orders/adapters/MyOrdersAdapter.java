package tdevm.app_ui.modules.orders.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.MyOrdersViewHolder> {

    public static final String TAG = MyOrdersAdapter.class.getSimpleName();

    private Context context;
    private List<FOrder> orderList;


    public MyOrdersAdapter(Context context) {
        this.context = context;
        this.orderList = new ArrayList<>();
    }

    public void onMyOrdersFetched(List<FOrder> fOrders) {
        this.orderList.addAll(fOrders);
        Log.d(TAG,fOrders.size() + "Order fetched");
        notifyDataSetChanged();
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
        holder.orderTotal.setText(orderList.get(position).getGrand_total());
        if (orderList.get(position).getRestaurant().getLocation()!=null) {
            holder.localityName.setText(orderList.get(position).getRestaurant().getLocation().getLocation_locality());
        }
        holder.orderTime.setText(orderList.get(position).getTimestamp());
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

        public MyOrdersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
