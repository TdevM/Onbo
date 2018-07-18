package tdevm.app_ui.modules.dinein.bottomsheets.section_r_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOnGroup;

public class CheckboxGroupSection extends StatelessSection {


    /**
     * Create a stateless Section object based on {@link SectionParameters}.
     *
     * @param sectionParameters section parameters
     */

    private List<MenuAddOnGroup> menuAddOnGroups;
    Context context;
    String title;
    List<MenuAddOn> checkboxGroupState;


    public CheckboxGroupSection(SectionParameters sectionParameters, List<MenuAddOnGroup> menuAddOnGroups, Context context, String title) {
        super(sectionParameters);
        this.context = context;
        this.title = title;
        this.menuAddOnGroups = menuAddOnGroups;
        this.checkboxGroupState = new ArrayList<>();
    }

    @Override
    public int getContentItemsTotal() {
        return menuAddOnGroups.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new MyItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyItemViewHolder itemHolder = (MyItemViewHolder) holder;

        // bind your view here
        itemHolder.tvItem.setText(menuAddOnGroups.get(position).getGroupName());
        if (menuAddOnGroups.get(position).getMenuAddOns().size() > 0) {
            for (int i = 0; i < menuAddOnGroups.get(position).getMenuAddOns().size(); i++) {
                CheckBox cb = new CheckBox(context);
                cb.setText(menuAddOnGroups.get(position).getMenuAddOns().get(i).getAddOnName() + "   " + String.valueOf( menuAddOnGroups.get(position).getMenuAddOns().get(i).getPrice()));
                cb.setId(Integer.parseInt(menuAddOnGroups.get(position).getMenuAddOns().get(i).getAddOnId()));
                itemHolder.ll.addView(cb);
                cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Iterator<MenuAddOn> arrayListIterator = menuAddOnGroups.get(position).getMenuAddOns().listIterator();
                        while (arrayListIterator.hasNext()) {
                            MenuAddOn addOn = arrayListIterator.next();
                            //Toast.makeText(context, option.getOptionName(), Toast.LENGTH_SHORT).show();
                            if (buttonView.getId() == Integer.parseInt(addOn.getAddOnId())) {
                                if (isChecked) {
                                    checkboxGroupState.add(addOn);
                                } else if (!isChecked) {
                                    if (checkboxGroupState.contains(addOn)) {
                                        checkboxGroupState.remove(addOn);
                                    }
                                }
                                //    Log.d("List iterator", addOn.getAddOnName());
                            }
                        }
                        // checkboxGroupState.add()
                        //    Log.d("Checkbox group", String.valueOf(buttonView.getId()));
                    }
                });

            }

        }
//


    }


    class MyItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvItem;
        LinearLayout ll;

        public MyItemViewHolder(View itemView) {
            super(itemView);
            tvItem = itemView.findViewById(R.id.text_single_add_on_name);
            ll = itemView.findViewById(R.id.l_layout_checkboxes);

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
