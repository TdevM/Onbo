package tdevm.app_ui.modules.section_r_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuVExtra;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;
import tdevm.app_ui.api.models.response.v2.menu.MenuVariant;

public class VariantExtraSection extends StatelessSection {

    String title;
    Context context;
    MenuVOption menuVariantOption;
    SectionedRecyclerViewAdapter adapter;
    List<MenuVExtra> menuVExtraState;

    /**
     * Create a stateless Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */
    public VariantExtraSection(SectionParameters sectionParameters, MenuVOption menuVariantOption, Context context, String title, SectionedRecyclerViewAdapter adapter) {
        super(sectionParameters);
        this.menuVariantOption = menuVariantOption;
        this.context = context;
        this.title = title;
        this.adapter = adapter;
        this.menuVExtraState = new ArrayList<>();
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemViewHolder = (MyItemViewHolder) holder;
        itemViewHolder.tvItem.setText("Menu v Extra");
        if (menuVariantOption.getMenuVExtras().size()>0) {
            for (int i = 0; i <menuVariantOption.getMenuVExtras().size(); i++) {
                RadioButton btn = new RadioButton(context);
                btn.setId(Integer.parseInt(menuVariantOption.getMenuVExtras().get(i).getExtraId()));
                btn.setText(menuVariantOption.getMenuVExtras().get(i).getOptionExtra());
                itemViewHolder.group.addView(btn);

            }

        }

    }


    class MyItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;
        RadioGroup group;
        private final View rootView;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            rootView = itemView;
            tvItem = itemView.findViewById(R.id.text_single_variant_name);
            group = itemView.findViewById(R.id.rg_variant_instance);

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

            tvTitle = view.findViewById(R.id.tvTitle);
        }
    }
}
