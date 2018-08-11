package tdevm.app_ui.modules.dinein.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import tdevm.app_ui.R;


public class RunningOrderEmptyFragment extends Fragment {

    public RunningOrderEmptyFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static RunningOrderEmptyFragment newInstance(String param1, String param2) {
        RunningOrderEmptyFragment fragment = new RunningOrderEmptyFragment();
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
        return inflater.inflate(R.layout.fragment_merged_order_empty, container, false);

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
