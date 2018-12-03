package onbo.app.modules.nondine.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import onbo.app.R;
import onbo.app.api.models.response.v2.FOrder.Checkout;
import onbo.app.api.models.response.v2.FOrder.OrderItem;
import onbo.app.utils.GeneralUtils;

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
        holder.itemPrice.setText(context.getString(R.string.rupee_symbol,GeneralUtils.parseStringDouble(orderItemList.get(position).getItemPrice())));
        holder.itemQty.setText(orderItemList.get(position).getItemQty());
        holder.itemTotal.setText(context.getString(R.string.rupee_symbol,GeneralUtils.parseStringDouble(orderItemList.get(position).getItemQTotal())));
        if (orderItemList.get(position).getIsVeg()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_veg_indicator));
        } else if (!orderItemList.get(position).getIsVeg()) {
            holder.imageView.setImageDrawable(context.getResources().getDrawable(R.drawable.non_veg_symbol));
            //holder.vegNonVegIndicator.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_non_veg_indicator));
        }
        if(orderItemList.get(position).getItemSlug()!=null){
            holder.slug.setText(orderItemList.get(position).getItemSlug());
        }else {
            holder.slug.setVisibility(View.GONE);
        }



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

        @BindView(R.id.tv_rv_inner_item_slug)
        TextView slug;

        @BindView(R.id.iv_veg_non_veg_merged_order)
        AppCompatImageView imageView;

        public NonDineCheckoutAdapterVH(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
