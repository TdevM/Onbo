package tdevm.app_ui.modules.auth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tdevm.app_ui.R;
import tdevm.app_ui.navigation_fragments_home.AccountsFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthInitFragment extends Fragment {


    public AuthInitFragment() {
        // Required empty public constructor
    }
    public static AuthInitFragment newInstance() {
        Log.d("AuthInitFragment", "new Instance() Called");
        return new AuthInitFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_auth_init, container, false);
    }

}
