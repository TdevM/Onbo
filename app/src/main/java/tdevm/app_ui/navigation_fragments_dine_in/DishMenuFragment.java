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
public class DishMenuFragment extends Fragment {


    public DishMenuFragment() {
        // Required empty public constructor
    }

    public static DishMenuFragment newInstance(){
        return new DishMenuFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dish_menu, container, false);
    }

}
