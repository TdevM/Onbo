package tdevm.app_ui.navigation_fragments_dine_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HighestRatedItemsFragment extends Fragment {


    public HighestRatedItemsFragment() {
        // Required empty public constructor
    }

    public static HighestRatedItemsFragment newInstance() {
        return new HighestRatedItemsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_highest_rated_items, container, false);
    }

}
