package onbo.app.modules.auth.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.modules.auth.AuthViewContract;
import onbo.app.modules.auth.AuthenticationActivity;

@RuntimePermissions
public class AuthInitFragment extends Fragment implements AuthViewContract.AuthInitView {
    public static final String TAG = AuthInitFragment.class.getSimpleName();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    Unbinder unbinder;
    private AuthInitInteractionListener mListener;

    private static final String PHONE_PATTERN = "^[6-9]\\d{9}$";
    private Pattern pattern = Pattern.compile(PHONE_PATTERN);
    private Matcher matcher;

    public boolean validatePhone(String phone) {
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }


    @Inject
    AuthInitPresenter authInitPresenter;

    @BindView(R.id.progress_bar_auth_init)
    ProgressBar progressBar;
    @BindView(R.id.et_init_phone_number)
    EditText phoneNumberInit;
    @BindView(R.id.btn_login_next)
    Button btnLoginInit;
    AuthenticationActivity authenticationActivity;

    @OnClick(R.id.btn_login_next)
    public void onButtonClick() {
        if (TextUtils.isEmpty(phoneNumberInit.getText())) {
            phoneNumberInit.setError("Enter a valid 10 digit number");
        } else if (!validatePhone(phoneNumberInit.getText().toString())) {
            phoneNumberInit.setError("Enter a valid 10 digit number");
        } else {
            AuthInitFragmentPermissionsDispatcher.sendOTPDetectSMSWithPermissionCheck(this);
        }
    }

    @NeedsPermission({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    public void sendOTPDetectSMS() {
        authInitPresenter.sendOTP(Long.parseLong(phoneNumberInit.getText().toString()));
    }

    public AuthInitFragment() {
        // Required empty public constructor
    }

    public static AuthInitFragment newInstance() {
        Log.d("AuthInitFragment", "new Instance() Called");
        return new AuthInitFragment();
    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        authInitPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        authenticationActivity = (AuthenticationActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_auth_init, container, false);
        unbinder = ButterKnife.bind(this, view);
        phoneNumberInit.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        // final Animation myAnim = AnimationUtils.loadAnimation(getContext(), R.anim.cycle7);
        // btnLoginInit.startAnimation(myAnim);
        return view;
    }


    @Override
    public void showProgressUI() {
        progressBar.setVisibility(View.VISIBLE);
        btnLoginInit.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
        btnLoginInit.setVisibility(View.VISIBLE);

    }

    @Override
    public void showLoginFragment(Long phone) {
        authenticationActivity.showLoginFragment(phone);
    }

    @Override
    public void showOTPVerificationScreen(Long phone) {
        authenticationActivity.showOTPVerificationFragment(phone);
    }

    @Override
    public void showError() {
        Toast.makeText(authenticationActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onAuthInitInteractionListener(uri);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AuthInitFragmentPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AuthInitInteractionListener) {
            mListener = (AuthInitInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement PickPowerFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface AuthInitInteractionListener {
        // TODO: Update argument type and name
        void onAuthInitInteractionListener(Uri uri);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy Called");
        authInitPresenter.detachView();
        super.onDestroy();
    }


    @OnShowRationale({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void showRationaleForSMS(PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setMessage("SEND SMS Permission Needed")
                .setPositiveButton("OK", (dialog, button) -> request.proceed())
                .setNegativeButton("CANCEL", (dialog, button) -> request.cancel())
                .show();
    }

    // Annotate a method which is invoked if the user doesn't grant the permissions
    @OnPermissionDenied({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void showDeniedForPhoneCall() {
        Toast.makeText(getActivity(), "Denied", Toast.LENGTH_SHORT).show();
    }

    // Annotates a method which is invoked if the user
    // chose to have the device "never ask again" about a permission
    @OnNeverAskAgain({Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS})
    void showNeverAskForPhoneCall() {
        Toast.makeText(getActivity(), "Never ask", Toast.LENGTH_SHORT).show();
    }
}