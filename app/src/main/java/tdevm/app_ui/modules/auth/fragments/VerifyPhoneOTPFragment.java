package tdevm.app_ui.modules.auth.fragments;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.APIService;

import tdevm.app_ui.modules.auth.AuthViewContract;
import tdevm.app_ui.modules.auth.AuthenticationActivity;

/**
 * A simple {@link Fragment} subclass.
 */

//TODO 30 Seconds Resend
public class VerifyPhoneOTPFragment extends Fragment implements AuthViewContract.AuthOTPView{

    private static final String PHONE = "PHONE";
    private Long phoneNumber;
    Unbinder unbinder;
    @Inject
    APIService apiService;

    AuthenticationActivity authenticationActivity;
    VerifyPhoneOTPPresenter verifyPhoneOTPPresenter;

    @BindView(R.id.et_otp_view)
    EditText editTextOTP;
    @BindView(R.id.tv_otp_mobile_number)
    TextView textViewOTPNumber;
    @BindView(R.id.btn_otp_verify_mobile)
    Button buttonVerifyOTP;
    @BindView(R.id.btn_otp_resend_otp)
    Button resendOTP;
    @BindView(R.id.progressBar_otp_view)
    ProgressBar progressBarOTP;

    @OnClick(R.id.btn_otp_resend_otp)
    public void onButtonClick(){
            verifyPhoneOTPPresenter.resendOTP(phoneNumber);
    }

    @OnClick(R.id.btn_otp_verify_mobile) //Manual verification
    public void onButtonClickedVerify(){
       verifyOTP();
    }

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
    public void resolveDaggerDependencies(){
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Log.d("FRAGMENT OTP..", "onCreate Called");
        View view  = inflater.inflate(R.layout.fragment_verify_phone_otp, container, false);
        unbinder = ButterKnife.bind(this,view);
        resolveDaggerDependencies();
        textViewOTPNumber.setText(getString(R.string.otp_sent_mobile_number,String.valueOf(phoneNumber)));
        authenticationActivity = (AuthenticationActivity) getActivity();
        verifyPhoneOTPPresenter = new VerifyPhoneOTPPresenter(apiService,this);
        editTextOTP.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});
        return view;
    }

    public void verifyOTP(){
        if(TextUtils.isEmpty(editTextOTP.getText())){
            Toast.makeText(getActivity(), "Enter OTP", Toast.LENGTH_SHORT).show();
        }else {
            verifyPhoneOTPPresenter.verifyOTP(phoneNumber,Long.parseLong(editTextOTP.getText().toString()));
        }
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void showProgressUI() {
        progressBarOTP.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressUI() {
      progressBarOTP.setVisibility(View.GONE);
    }

    @Override
    public void showOTPSentSuccess() {
        Toast.makeText(authenticationActivity, "OTP Sent!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOTPSentFailure() {
        Toast.makeText(authenticationActivity, "Failed to send an OTP!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVerificationFailure() {
        Toast.makeText(authenticationActivity, "Incorrect OTP!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVerificationSuccess(Long phone) {
        authenticationActivity.showRegisterFragment(phone);
        Toast.makeText(authenticationActivity, "Verified!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        verifyPhoneOTPPresenter.compositeDisposable.dispose();
        verifyPhoneOTPPresenter.compositeDisposable.clear();
        super.onDestroy();
    }
}
