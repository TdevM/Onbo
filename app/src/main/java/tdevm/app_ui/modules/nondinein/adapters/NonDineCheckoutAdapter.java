package tdevm.app_ui.modules.nondinein.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.Checkout;
import tdevm.app_ui.api.models.response.v2.FOrder.OrderItem;

public class NonDineCheckoutAdapter extends RecyclerView.Adapter<NonDineCheckoutAdapter.NonDineCheckoutAdapterVH> {

    public static final String TAG = NonDineCheckoutAdapter.class.getSimpleName();
    private Checkout checkout;
    private List<OrderItem> orderItemList;
    private Context context;

    public NonDineCheckoutAdapter(Context context) {
        this.context = context;
        orderItemList = new ArrayList<>();
    }

    public void onCheckoutDataFetched(Checkout c) {
        this.checkout = c;
        orderItemList.clear();
        orderItemList.addAll(c.getOrderItems());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NonDineCheckoutAdapterVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_merged_order_inner, parent, false);
        return new NonDineCheckoutAdapterVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NonDineCheckoutAdapterVH holder, int position) {
        holder.itemName.setText(orderItemList.get(position).getItemName());
        holder.itemPrice.setText(String.valueOf(Integer.parseInt(orderItemList.get(position).getItemPrice()) * 0.01));
        holder.itemQty.setText(orderItemList.get(position).getItemQty());
        holder.itemTotal.setText(String.valueOf(Integer.parseInt(orderItemList.get(position).getItemQTotal()) * 0.01));
    }

    @Override
    public int getItemCount() {
        return orderItemList.size();
    }

    class NonDineCheckoutAdapterVH extends RecyclerView.ViewHolder {


        @BindView(R.id.tv_rv_item_inner_item_name)
        TextView itemName;
        @BindView(R.id.tv_rv_item_inner_item_price)
        TextView itemPrice;
        @BindView(R.id.tv_rv_item_inner_item_qty)
        TextView itemQty;
        @BindView(R.id.tv_rv_item_inner_item_total)
        TextView itemTotal;

        public NonDineCheckoutAdapterVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
