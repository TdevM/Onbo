package app.onbo.modules.dinein.bottomsheets.section_r_view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import app.onbo.R;
import app.onbo.api.models.response.v2.menu.MenuAddOn;
import app.onbo.api.models.response.v2.menu.MenuItem;
import app.onbo.api.models.response.v2.menu.MenuVOption;
import app.onbo.modules.dinein.callbacks.MenuItemOptionsSelected;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class MenuItemCustomizationSheet extends BottomSheetDialogFragment {

    Unbinder unbinder;

    @BindView(R.id.toolbar_fragment_h_n_s_list)
    Toolbar toolbar;


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
        menuItem = bundle.getParcelable("options");
        View view = inflater.inflate(R.layout.fragment_menu_customization_sheet, container, false);
        unbinder = ButterKnife.bind(this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setNavigationOnClickListener(v -> dismissSheet());
            toolbar.setTitle(menuItem.getItemName());
        }


        button = view.findViewById(R.id.btn_variant_add_to_cart);

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
                dismissSheet();
            }
        });
        return view;
    }

    public void dismissSheet() {
        this.dismiss();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        final Fragment parent = getParentFragment();
        if (parent != null) {
            menuItemOptionsSelected = (MenuItemOptionsSelected) parent;
        } else {
            menuItemOptionsSelected = (MenuItemOptionsSelected) context;
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        BottomSheetDialog bottomSheetDialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = d.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
                BottomSheetBehavior.from(bottomSheet).setHideable(true);
            }
        });
        return bottomSheetDialog;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        menuItemOptionsSelected = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
