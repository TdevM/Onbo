package tdevm.app_ui.modules.auth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthRegisterFragment extends Fragment {

    EditText editText;

    public AuthRegisterFragment() {
        // Required empty public constructor
    }

    public static AuthRegisterFragment newInstance(){
        return new AuthRegisterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_register, container, false);
        editText = view.findViewById(R.id.et_sign_up_phone_number);
        editText.setText("+919560447160");
        // Inflate the layout for this fragment
        return view;
    }

}
