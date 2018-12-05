package app.onbo.modules.dinein.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.onbo.R;


/**
 * Created by Tridev on 25-11-2017.
 */

public class RunningOrderItemsAdapter extends RecyclerView.Adapter<RunningOrderItemsAdapter.RunningItemsViewHolder> {

    private Context context;
    //private ArrayList<KOT_items> kot_items;

//    public RunningOrderItemsAdapter(Context context, ArrayList<KOT_items> kot_items) {
//        this.context = context;
//        this.kot_items = kot_items;
//    }

    @Override
    public RunningItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_temp_order_inner, parent, false);
        return new RunningOrderItemsAdapter.RunningItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RunningItemsViewHolder holder, int position) {
//         holder.dishName.setText(kot_items.get(position).getDish().getDish_name());
//         holder.dishPrice.setText(context.getString(R.string.rupee_symbol,kot_items.get(position).getDish().getDish_price().intValue()));
//         holder.dishQty.setText(kot_items.get(position).getDish_quantity());
//         int quantity = Integer.parseInt(kot_items.get(position).getDish_quantity());
//         Double price = kot_items.get(position).getDish().getDish_price();
//         holder.dishTotal.setText(context.getString(R.string.rupee_symbol,quantity*price.intValue()));
    }

    @Override
    public int getItemCount() {
       // return kot_items.size();
        return 0;
    }

    public class RunningItemsViewHolder extends RecyclerView.ViewHolder{

        TextView dishName, dishPrice, dishQty, dishTotal;
        public RunningItemsViewHolder(View itemView) {
            super(itemView);
            dishName = itemView.findViewById(R.id.tv_rv_item_inner_dish_name);
            dishPrice = itemView.findViewById(R.id.tv_rv_item_inner_dish_price);
            dishQty = itemView.findViewById(R.id.tv_rv_item_inner_dish_qty);
            dishTotal = itemView.findViewById(R.id.tv_rv_item_inner_dish_total_qty);
        }
    }
}
