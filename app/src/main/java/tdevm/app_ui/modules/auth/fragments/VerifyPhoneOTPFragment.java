package tdevm.app_ui.modules.auth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyPhoneOTPFragment extends Fragment {


    public VerifyPhoneOTPFragment() {
        // Required empty public constructor
    }

    public static VerifyPhoneOTPFragment newInstance(){
        return new VerifyPhoneOTPFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_phone_otp, container, false);
    }

}
