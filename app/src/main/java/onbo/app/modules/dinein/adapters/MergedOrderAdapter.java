package onbo.app.modules.dinein.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
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
import onbo.app.R;
import onbo.app.api.models.response.v2.merged.MergedItems;
import onbo.app.api.models.response.v2.merged.MergedOrder;
import onbo.app.utils.GeneralUtils;

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
        this.mergedItemsList.clear();
        this.mergedItemsList.addAll(mergedOrder.getMergedItems());
        Log.d(TAG, "Merged order adapter updated");
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MergedOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_merged_order_inner, parent, false);
        return new MergedOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MergedOrderViewHolder holder, int position) {
        holder.itemName.setText(mergedItemsList.get(position).getItem_data().getItem_name());
        holder.itemPrice.setText(context.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedItemsList.get(position).getItem_total())));
        holder.qty.setText(mergedItemsList.get(position).getItem_qty());
        holder.itemTotal.setText(context.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedItemsList.get(position).getItem_q_total())));
        if (mergedItemsList.get(position).getItem_data().getIs_veg()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_veg_indicator));
        } else if (!mergedItemsList.get(position).getItem_data().getIs_veg()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.non_veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_non_veg_indicator));
        }
        if (mergedItemsList.get(position).getItem_slug() != null) {
            holder.slug.setText(mergedItemsList.get(position).getItem_slug());
        } else {
            holder.slug.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return mergedItemsList.size();
    }

    public class MergedOrderViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, qty, itemTotal, slug;
        @BindView(R.id.iv_veg_non_veg_merged_order)
        AppCompatImageView imageView;

        public MergedOrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemName = itemView.findViewById(R.id.tv_rv_item_inner_item_name);
            itemPrice = itemView.findViewById(R.id.tv_rv_item_inner_item_price);
            qty = itemView.findViewById(R.id.tv_rv_item_inner_item_qty);
            itemTotal = itemView.findViewById(R.id.tv_rv_item_inner_item_total);
            slug = itemView.findViewById(R.id.tv_rv_inner_item_slug);

        }

    }
}
