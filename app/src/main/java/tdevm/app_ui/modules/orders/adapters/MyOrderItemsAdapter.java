package tdevm.app_ui.modules.orders.adapters;

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
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrderItem;
import tdevm.app_ui.api.models.response.v2.merged.MergedItems;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;

public class MyOrderItemsAdapter extends RecyclerView.Adapter<MyOrderItemsAdapter.MyOrderItemsViewHolder> {

    public static final String TAG = MyOrderItemsAdapter.class.getSimpleName();
    private Context context;
    private List<FOrderItem> mergedItemsList;

    public MyOrderItemsAdapter(Context context, List<FOrderItem> orderItems) {
        this.context = context;
        this.mergedItemsList = orderItems;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public MyOrderItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_merged_order_inner, parent, false);
        return new MyOrderItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderItemsViewHolder holder, int position) {
        holder.itemName.setText(mergedItemsList.get(position).getItem_name());
        holder.itemPrice.setText(String.valueOf(Integer.parseInt(mergedItemsList.get(position).getItem_total()) * 0.01));
        holder.qty.setText(mergedItemsList.get(position).getItem_qty());
        holder.itemTotal.setText(String.valueOf(Integer.parseInt(mergedItemsList.get(position).getItem_q_total()) * 0.01));
        if (mergedItemsList.get(position).getMenu_item().getIsVeg()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_veg_indicator));
        } else if (!mergedItemsList.get(position).getMenu_item().getIsVeg()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.non_veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_non_veg_indicator));
        }
        holder.slug.setText(mergedItemsList.get(position).getItem_slug());

    }

    @Override
    public int getItemCount() {
        return mergedItemsList.size();
    }

    public class MyOrderItemsViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemPrice, qty, itemTotal, slug;
        @BindView(R.id.iv_veg_non_veg_merged_order)
        AppCompatImageView imageView;

        public MyOrderItemsViewHolder(View itemView) {
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
