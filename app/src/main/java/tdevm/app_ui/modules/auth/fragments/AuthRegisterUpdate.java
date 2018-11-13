package tdevm.app_ui.modules.auth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthRegisterUpdate extends Fragment {


    public static final String TAG = AuthRegisterUpdate.class.getSimpleName();

    Unbinder unbinder;


    public AuthRegisterUpdate() {
        // Required empty public constructor
    }


    public static AuthRegisterFragment newInstance() {
        return new AuthRegisterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auth_register_update, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

}
