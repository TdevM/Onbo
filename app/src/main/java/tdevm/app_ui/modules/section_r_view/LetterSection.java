package tdevm.app_ui.modules.section_r_view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOnGroup;

public class LetterSection extends StatelessSection {


    /**
     * Create a stateless Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     *
     *
     */


    private ArrayList<MenuAddOn> arrayList;
    private String title;
    private MenuAddOnGroup group;


    public LetterSection(SectionParameters sectionParameters, MenuAddOnGroup menuAddOnGroup, String mTitle) {
        super(sectionParameters);
        //this.arrayList = strings;
        arrayList = new ArrayList<>();
        this.group = menuAddOnGroup;
        this.arrayList.addAll(menuAddOnGroup.getMenuAddOns());
        title = mTitle;
    }

    @Override
    public int getContentItemsTotal() {
        return group.getMenuAddOns().size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
       return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        itemHolder.tvItem.setText(arrayList.get(position).getAddOnName());
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

        headerHolder.tvTitle.setText(group.getGroupName());
    }


    class HeaderViewHolder extends RecyclerView.ViewHolder {

        final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle =  view.findViewById(R.id.tvTitle);
        }
    }
}
