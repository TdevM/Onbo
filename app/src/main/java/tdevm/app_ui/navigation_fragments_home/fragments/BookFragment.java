package tdevm.app_ui.navigation_fragments_home.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookFragment extends Fragment {


    public BookFragment() {
        // Required empty public constructor
    }

    public static BookFragment newInstance() {
        return new BookFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book, container, false);
    }

}
