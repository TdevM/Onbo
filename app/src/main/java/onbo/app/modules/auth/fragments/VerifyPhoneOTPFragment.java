package onbo.app.modules.auth.fragments;

import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.TextUtils;
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
import onbo.app.AppApplication;
import onbo.app.R;

import onbo.app.api.models.response.UserApp;
import onbo.app.modules.auth.AuthViewContract;
import onbo.app.modules.auth.AuthenticationActivity;
import onbo.app.utils.SMSListener;

//TODO 30 Seconds Resend
public class VerifyPhoneOTPFragment extends Fragment implements AuthViewContract.AuthOTPView, SMSListener.SMSReceived {

    public static final String TAG = VerifyPhoneOTPFragment.class.getSimpleName();
    private static final String PHONE = "PHONE";
    private Long phoneNumber;
    Unbinder unbinder;

    AuthenticationActivity authenticationActivity;

    @Inject
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

    SMSListener smsListener;
    IntentFilter intentFilter;

    @OnClick(R.id.btn_otp_resend_otp)
    public void onButtonClick() {
        verifyPhoneOTPPresenter.resendOTP(phoneNumber);
    }

    @OnClick(R.id.btn_otp_verify_mobile) //Manual verification
    public void onButtonClickedVerify() {
        verifyOTP();
    }

    public VerifyPhoneOTPFragment() {
        // Required empty public constructor
    }

    public static VerifyPhoneOTPFragment newInstance() {

        return new VerifyPhoneOTPFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getLong(PHONE);
        }
    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        SMSListener.setOnSMSReceivedListener(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        smsListener = new SMSListener();
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        View view = inflater.inflate(R.layout.fragment_verify_phone_otp, container, false);
        unbinder = ButterKnife.bind(this, view);
        resolveDaggerDependencies();
        textViewOTPNumber.setText(getString(R.string.otp_sent_mobile_number, String.valueOf(phoneNumber)));
        authenticationActivity = (AuthenticationActivity) getActivity();
        editTextOTP.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        return view;
    }


    public void verifyOTP() {
        if (TextUtils.isEmpty(editTextOTP.getText())) {
            editTextOTP.setError("Enter OTP");
        } else {
            verifyPhoneOTPPresenter.verifyOTP(phoneNumber, Long.parseLong(editTextOTP.getText().toString()));
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
        buttonVerifyOTP.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();
        verifyPhoneOTPPresenter.attachView(this);
        getActivity().registerReceiver(smsListener, intentFilter);

    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(smsListener);
        verifyPhoneOTPPresenter.detachView();
    }

    @Override
    public void hideProgressUI() {
        progressBarOTP.setVisibility(View.GONE);
        buttonVerifyOTP.setVisibility(View.VISIBLE);
    }

    @Override
    public void showOTPSentSuccess() {
        Toast.makeText(getActivity(), "OTP Sent!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showOTPSentFailure() {
        Toast.makeText(getActivity(), "Failed to send an OTP!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showVerificationFailure() {
        //Toast.makeText(getActivity(), "Incorrect OTP!", Toast.LENGTH_SHORT).show();
        editTextOTP.setError("Incorrect OTP");
    }

    @Override
    public void showVerificationSuccessRegister(Long phone) {
        authenticationActivity.showRegisterFragment(phone);
        Toast.makeText(getActivity(), "Verified!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRegisteredUserFetched(UserApp userApp) {
        authenticationActivity.showRegisterUpdateFragment(userApp);
        Toast.makeText(authenticationActivity, "Verified!", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void showGenericError() {
        Toast.makeText(authenticationActivity, "Something went wrong! please try again later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        verifyPhoneOTPPresenter.detachView();
//        getActivity().unregisterReceiver(smsListener);

        super.onDestroy();
    }

    @Override
    public void onSMSReceived(String sender, String body) {
        //Toast.makeText(authenticationActivity, sender+" "+body, Toast.LENGTH_SHORT).show();
        verifyPhoneOTPPresenter.parseSMS(sender, body, phoneNumber);
    }
}
