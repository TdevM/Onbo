package tdevm.app_ui.modules.section_r_view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOnGroup;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;

public class HeaderRecycler extends BottomSheetDialogFragment {

    private SectionedRecyclerViewAdapter sectionAdapter;
    private MenuItem menuItem;

    public HeaderRecycler() {
        // Required empty public constructor
    }


    public static HeaderRecycler newInstance(MenuItem menuItem) {
        HeaderRecycler fragment = new HeaderRecycler();
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
        Log.d("TAG___________________", menuItem.getItemName());
        sectionAdapter = new SectionedRecyclerViewAdapter();


//        NumberSection section = new NumberSection(SectionParameters
//                .builder()
//                .headerResourceId(R.layout.section_ex1_header)
//                .itemResourceId(R.layout.item_single_dish_review).build(), 5, "Addons");
//        LetterSection section1 = new LetterSection(SectionParameters
//                .builder()
//                .headerResourceId(R.layout.section_ex1_header)
//                .itemResourceId(R.layout.item_single_dish_review).build(), menuItem.getMenuAddOnGroups().get(0), "Variants");



        CheckboxGroupSection section2 = new CheckboxGroupSection(SectionParameters
                .builder()
                .itemResourceId(R.layout.item_single_addon_group_instance)
                .headerResourceId(R.layout.section_ex1_header)
                .build(), menuItem.getMenuAddOnGroups(),getContext(),"Customize your dish");

        RadioGroupSection section = new RadioGroupSection(SectionParameters
        .builder()
        .itemResourceId(R.layout.item_single_variant_group_instance)
        .headerResourceId(R.layout.section_ex1_header)
        .build(),menuItem.getMenuVariants(),getContext(),"Choose");


        sectionAdapter.addSection(section);
        sectionAdapter.addSection(section2);
        View view = inflater.inflate(R.layout.fragment_header_recycler, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_header_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(sectionAdapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
