package tdevm.app_ui.modules.orders;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NonDineOrdersFragment extends Fragment {


    public NonDineOrdersFragment() {
        // Required empty public constructor
    }
    public static NonDineOrdersFragment newInstance(){
        return new NonDineOrdersFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_non_dine_orders, container, false);
    }

}
