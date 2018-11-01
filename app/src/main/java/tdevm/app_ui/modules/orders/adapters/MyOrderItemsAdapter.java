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
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;

import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrderAddOn;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrderItem;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrderVariant;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;
import tdevm.app_ui.api.models.response.v2.merged.MergedItems;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;
import tdevm.app_ui.utils.GeneralUtils;

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
        holder.itemPrice.setText(context.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedItemsList.get(position).getItem_total())));
        holder.qty.setText(mergedItemsList.get(position).getItem_qty());
        holder.itemTotal.setText(context.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedItemsList.get(position).getItem_q_total())));
        if (mergedItemsList.get(position).getMenu_item().getIsVeg()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_veg_indicator));
        } else if (!mergedItemsList.get(position).getMenu_item().getIsVeg()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.non_veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_non_veg_indicator));
        }
        if (mergedItemsList.get(position).getMenu_item().getCustomizable()) {
            holder.slug.setText(generateSlug(mergedItemsList.get(position)));
        }else {
            holder.slug.setVisibility(View.GONE);
        }


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


    private String generateSlug(FOrderItem item) {
        StringBuilder sb = new StringBuilder();
        if (item.getF_order_addons() != null && item.getF_order_variants() != null) {
            List<FOrderAddOn> menuAddOns = item.getF_order_addons();
            List<FOrderVariant> menuVOptions = item.getF_order_variants();
            Iterator<FOrderVariant> menuVOptionIterator;
            Iterator<FOrderAddOn> menuAddOnIterator;
            if (menuVOptions != null) {
                menuVOptionIterator = menuVOptions.listIterator();
                while (menuVOptionIterator.hasNext()) {
                    FOrderVariant option = menuVOptionIterator.next();
                    sb.append(" ");
                    sb.append(option.getOption_name());
                }
                if (menuAddOns != null) {
                    menuAddOnIterator = menuAddOns.listIterator();
                    while (menuAddOnIterator.hasNext()) {
                        FOrderAddOn addOn = menuAddOnIterator.next();
                        sb.append(" ");
                        sb.append(addOn.getAdd_on_name());
                    }
                }

            }
        }
        return sb.toString();
    }
}
