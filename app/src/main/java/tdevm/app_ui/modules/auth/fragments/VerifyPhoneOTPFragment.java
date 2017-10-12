package tdevm.app_ui.modules.auth.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Unbinder;
import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerifyPhoneOTPFragment extends Fragment {

    private static final String PHONE = "PHONE";
    private Long phoneNumber;
    Unbinder unbinder;

    public VerifyPhoneOTPFragment() {
        // Required empty public constructor
    }

    public static VerifyPhoneOTPFragment newInstance(){
        return new VerifyPhoneOTPFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getLong(PHONE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_phone_otp, container, false);
    }

}
