package tdevm.app_ui.modules.auth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthLoginFragment extends Fragment {

    EditText editText,editText1;
    Button button;

    public AuthLoginFragment() {
        // Required empty public constructor
    }
    public static AuthLoginFragment newInstance() {
        Log.d("AuthInitFragment", "new Instance() Called");
        return new AuthLoginFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_login, container, false);
        editText = view.findViewById(R.id.et_login_phone_number);
        editText1 = view.findViewById(R.id.et_login_password);
        editText.setText("+919560447160");

        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        editText.setFocusable(false);
        editText1.setClickable(false);
        button = view.findViewById(R.id.btn_login);
        return view;
    }

}
