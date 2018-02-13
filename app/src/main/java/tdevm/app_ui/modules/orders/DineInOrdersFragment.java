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
public class DineInOrdersFragment extends Fragment {


    public DineInOrdersFragment() {
        // Required empty public constructor
    }

    public static DineInOrdersFragment newInstance(){
        return new DineInOrdersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dine_in_orders, container, false);
    }

}
