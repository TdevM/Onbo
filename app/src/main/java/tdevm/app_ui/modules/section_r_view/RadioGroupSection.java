package tdevm.app_ui.modules.section_r_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;
import tdevm.app_ui.api.models.response.v2.menu.MenuVariant;

public class RadioGroupSection extends StatelessSection {


    /**
     * Create a stateless Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */

    String title;
    Context context;
    List<MenuVariant> menuVariants;
    SectionedRecyclerViewAdapter adapter;
    List<MenuVOption> radioGroupState;

    public RadioGroupSection(SectionParameters sectionParameters, List<MenuVariant> menuVariants, Context context, String title, SectionedRecyclerViewAdapter adapter) {
        super(sectionParameters);
        this.title = title;
        this.adapter = adapter;
        this.context = context;
        this.menuVariants = menuVariants;
        radioGroupState = new ArrayList<>();
    }


    @Override
    public int getContentItemsTotal() {
        return menuVariants.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;
        itemHolder.tvItem.setText(menuVariants.get(position).getVariantName());
        for (int i = 0; i < menuVariants.get(position).getMenuVOptions().size(); i++) {
            RadioButton btn = new RadioButton(context);
            btn.setId(Integer.parseInt(menuVariants.get(position).getMenuVOptions().get(i).getOptionId()));
            btn.setText(menuVariants.get(position).getMenuVOptions().get(i).getOptionName());
            itemHolder.group.addView(btn);
        }
        if (menuVariants.get(position).getMenuVOptions().size() > 0) {
            itemHolder.group.check(Integer.parseInt(menuVariants.get(position).getMenuVOptions().get(0).getOptionId()));
            radioGroupState.add(position, menuVariants.get(position).getMenuVOptions().get(0));
        }

        itemHolder.group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup g, int checkedId) {

                Iterator<MenuVOption> arrayListIterator = menuVariants.get(position).getMenuVOptions().listIterator();
                while (arrayListIterator.hasNext()) {
                    MenuVOption option = arrayListIterator.next();
                    //Toast.makeText(context, option.getOptionName(), Toast.LENGTH_SHORT).show();
                    if (checkedId == Integer.parseInt(option.getOptionId())) {

                        radioGroupState.remove(position);
                        radioGroupState.add(position, option);

                    }
                }


            }
        });


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
