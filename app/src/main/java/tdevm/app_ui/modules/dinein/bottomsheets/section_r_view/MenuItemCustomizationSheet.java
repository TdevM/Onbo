package tdevm.app_ui.modules.dinein.bottomsheets.section_r_view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;
import tdevm.app_ui.modules.dinein.callbacks.MenuItemOptionsSelected;

public class MenuItemCustomizationSheet extends BottomSheetDialogFragment {

    private SectionedRecyclerViewAdapter sectionAdapter;
    private MenuItem menuItem;
    private Button button;
    private MenuItemOptionsSelected menuItemOptionsSelected;

    public MenuItemCustomizationSheet() {
        // Required empty public constructor
    }


    public static MenuItemCustomizationSheet newInstance(MenuItem menuItem) {
        MenuItemCustomizationSheet fragment = new MenuItemCustomizationSheet();
        Bundle args = new Bundle();
        args.putParcelable("options", menuItem);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        View view = inflater.inflate(R.layout.fragment_menu_customization_sheet, container, false);
        button = view.findViewById(R.id.btn_variant_add_to_cart);
        menuItem = bundle.getParcelable("options");
        Log.d("TAG___________________", menuItem.getItemName());
        sectionAdapter = new SectionedRecyclerViewAdapter();


        CheckboxGroupSection section2 = new CheckboxGroupSection(SectionParameters
                .builder()
                .itemResourceId(R.layout.item_single_addon_group_instance)
                .headerResourceId(R.layout.section_ex1_header)
                .build(), menuItem.getMenuAddOnGroups(), getContext(), "Customize your dish");


        RadioGroupSection section = new RadioGroupSection(SectionParameters
                .builder()
                .itemResourceId(R.layout.item_single_variant_group_instance)
                .headerResourceId(R.layout.section_ex1_header)
                .build(), menuItem.getMenuVariants(), getContext(), "Choose", sectionAdapter);


        sectionAdapter.addSection(section);
        sectionAdapter.addSection(section2);


        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_header_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (MenuVOption option : section.radioGroupState) {
                    Log.d("RadioGroup State", option.getOptionName());
                }
                for (MenuAddOn addOn : section2.checkboxGroupState) {
                    Log.d("Checkbox State", addOn.getAddOnName());
                }
                menuItemOptionsSelected.onOptionsSelected(menuItem, section.radioGroupState, section2.checkboxGroupState);
            }
        });
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            menuItemOptionsSelected = (MenuItemOptionsSelected) parent;
        } else {
            menuItemOptionsSelected = (MenuItemOptionsSelected) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        menuItemOptionsSelected = null;
    }


}
