package tdevm.app_ui.modules.dinein.adapters;

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

import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.merged.MergedItems;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;

public class MergedOrderAdapter extends RecyclerView.Adapter<MergedOrderAdapter.MergedOrderViewHolder> {

    public static final String TAG = MergedOrderAdapter.class.getSimpleName();
    private Context context;
    private MergedOrder mergedOrder;
    private List<MergedItems> mergedItemsList;

    public MergedOrderAdapter(Context context) {
        this.context = context;
        mergedItemsList = new ArrayList<>();
    }

    public void onMergedOrderFetched(MergedOrder mergedOrder) {
        this.mergedOrder = mergedOrder;
        this.mergedItemsList.addAll(mergedOrder.getMergedItems());
        Log.d(TAG, "Merged order adapter updated");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MergedOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_merged_order_inner, parent, false);
        return new MergedOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MergedOrderViewHolder holder, int position) {
        holder.itemName.setText(mergedItemsList.get(position).getItem_data().getItem_name());
        holder.itemPrice.setText(String.valueOf(Integer.parseInt(mergedItemsList.get(position).getItem_total())*0.01));
        holder.qty.setText(mergedItemsList.get(position).getItem_qty());
        holder.itemTotal.setText(String.valueOf(Integer.parseInt(mergedItemsList.get(position).getItem_q_total())*0.01));
    }

    @Override
    public int getItemCount() {
        return mergedItemsList.size();
    }

    public class MergedOrderViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, qty, itemTotal;
        public MergedOrderViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.tv_rv_item_inner_item_name);
            itemPrice = itemView.findViewById(R.id.tv_rv_item_inner_item_price);
            qty = itemView.findViewById(R.id.tv_rv_item_inner_item_qty);
            itemTotal = itemView.findViewById(R.id.tv_rv_item_inner_item_total);
        }

    }
}
