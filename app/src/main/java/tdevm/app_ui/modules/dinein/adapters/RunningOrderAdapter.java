package tdevm.app_ui.modules.dinein.adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import tdevm.app_ui.R;


/**
 * Created by Tridev on 25-11-2017.
 */

public class RunningOrderAdapter extends RecyclerView.Adapter<RunningOrderAdapter.RunningAdapterViewHolder> {
    public static final String TAG = RunningOrderAdapter.class.getCanonicalName();
    private Context context;
    private ArrayList<TempOrder> tempOrderArrayList;

    public RunningOrderAdapter(Context context) {
        this.context = context;
        tempOrderArrayList = new ArrayList<>();
    }

    public void onTempOrderFetched(ArrayList<TempOrder> tempOrders){
        tempOrderArrayList.addAll(tempOrders);
        Log.d(TAG,"Temp Order adapter updated");
        notifyDataSetChanged();
    }

    @Override
    public RunningAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_rv_temp_order, parent, false);
        return new RunningAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RunningAdapterViewHolder holder, int position) {
        holder.kotNo.setText(tempOrderArrayList.get(position).getKot_id());
        Timestamp timestamp = new Timestamp(Long.parseLong(tempOrderArrayList.get(position).getKot_timestamp()));
        Date date = new Date(timestamp.getTime());
        Log.d("Date",date.toString());
        SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss a", Locale.UK);
        holder.kotTime.setText(ft.format(date));
        if(tempOrderArrayList.get(position).getKot_completed()){
            holder.kotStatus.setText("Items Prepared");
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        RunningOrderItemsAdapter adapter = new RunningOrderItemsAdapter(context,tempOrderArrayList.get(position).getKot_items());
        holder.recyclerViewInnerDishItems.setLayoutManager(mLayoutManager);
        holder.recyclerViewInnerDishItems.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return tempOrderArrayList.size();
    }

    public class RunningAdapterViewHolder extends RecyclerView.ViewHolder{

         TextView kotNo,kotTime,kotStatus;
         RecyclerView recyclerViewInnerDishItems;
        public RunningAdapterViewHolder(View itemView) {
            super(itemView);
            kotNo = itemView.findViewById(R.id.tv_item_kot_no);
            kotTime = itemView.findViewById(R.id.tv_item_kot_time);
            kotStatus = itemView.findViewById(R.id.tv_item_kot_status);
            recyclerViewInnerDishItems = itemView.findViewById(R.id.rv_temp_order_inner);
        }
    }
}
