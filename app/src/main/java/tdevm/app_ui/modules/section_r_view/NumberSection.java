package tdevm.app_ui.modules.section_r_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import tdevm.app_ui.R;

public class NumberSection extends StatelessSection {

    /**
     * Create a stateless Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */

    private final int mItemCount;
    private String title;

    public NumberSection(SectionParameters sectionParameters, int mItemCount, String title) {
        super(sectionParameters);
        this.mItemCount = mItemCount;
        this.title = title;
    }

    @Override
    public int getContentItemsTotal() {
        return mItemCount;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        itemHolder.tvItem.setText(String.valueOf(position));
    }

    class MyItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;

        public MyItemViewHolder(View itemView) {
            super(itemView);

            tvItem = itemView.findViewById(R.id.text_single_dish_review);
        }
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.tvTitle.setText(title);
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }
}
