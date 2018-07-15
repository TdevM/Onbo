package tdevm.app_ui.modules.section_r_view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import tdevm.app_ui.R;

public class HeaderRecycler extends BottomSheetDialogFragment {

    private SectionedRecyclerViewAdapter sectionAdapter;

    public HeaderRecycler() {
        // Required empty public constructor
    }


    public static HeaderRecycler newInstance() {
        HeaderRecycler fragment = new HeaderRecycler();
        Bundle args = new Bundle();
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
        // Inflate the layout for this fragment
        sectionAdapter = new SectionedRecyclerViewAdapter();


        NumberSection section = new NumberSection(SectionParameters
                .builder()
                .headerResourceId(R.layout.section_ex1_header)
                .itemResourceId(R.layout.item_single_dish_review).build(),30,"Addons");

        sectionAdapter.addSection(section);
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
