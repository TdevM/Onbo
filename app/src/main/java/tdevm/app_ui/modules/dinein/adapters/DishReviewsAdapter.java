package tdevm.app_ui.modules.dinein.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.DishReviews;
import tdevm.app_ui.modules.dinein.bottomsheets.DishReviewsSheetFragment;

/**
 * Created by Tridev on 14-02-2018.
 */

public class DishReviewsAdapter extends RecyclerView.Adapter<DishReviewsAdapter.ViewHolder>{

    public static final String TAG = DishReviewsAdapter.class.getSimpleName();

    private final int mItemCount;
    private ArrayList<DishReviews> reviewsArrayList;

    public DishReviewsAdapter(int i) {
        this.mItemCount = i;
        reviewsArrayList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DishReviewsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()), parent);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.text.setText(String.valueOf(position));

    }

    @Override
    public int getItemCount() {
        return mItemCount;
    }

    public void onDishReviewsFetched(ArrayList<DishReviews> list) {
        reviewsArrayList.addAll(list);
        notifyDataSetChanged();
        Log.d(TAG,"Adapter dish reviews fetched");
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView text;

        ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            // TODO: Customize the item layout
            super(inflater.inflate(R.layout.item_single_dish_review, parent, false));
            text = (TextView) itemView.findViewById(R.id.text);

        }

    }
}
