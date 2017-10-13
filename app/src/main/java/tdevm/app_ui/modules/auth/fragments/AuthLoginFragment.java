package tdevm.app_ui.modules.auth.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.MySharedPreferences;
import tdevm.app_ui.dagger.components.ApplicationComponent;
import tdevm.app_ui.dagger.components.DaggerAPIComponent;
import tdevm.app_ui.dagger.modules.APIModule;
import tdevm.app_ui.modules.auth.AuthViewContract;
import tdevm.app_ui.modules.auth.AuthenticationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
//TODO Create a Base Fragment class
public class AuthLoginFragment extends Fragment implements AuthViewContract.AuthLoginView {

    private static final String PHONE = "PHONE";
    private Long phoneNumber;
    Unbinder unbinder;

    @Inject
    MySharedPreferences mySharedPreferences;
    @Inject
    APIService apiService;


    private AuthLoginPresenter authLoginPresenter;
    AuthenticationActivity authenticationActivity;

    @BindView(R.id.et_login_phone_number)
    EditText loginPhone;
    @BindView(R.id.et_login_password)
    EditText loginPassword;
    @BindView(R.id.btn_login)
    Button buttonLogin;
    @BindView(R.id.progressBar_login)
    ProgressBar progressBar;

    @OnClick(R.id.btn_login)
    public void onButtonClick(){
        if(TextUtils.isEmpty(loginPassword.getText())){
            Toast.makeText(getActivity(), "Enter password", Toast.LENGTH_SHORT).show();
        }else {
            authLoginPresenter.loginUser(phoneNumber,loginPassword.getText().toString());
        }
    }


    public AuthLoginFragment() {
        // Required empty public constructor
    }
    public static AuthLoginFragment newInstance() {
        Log.d("AuthInitFragment", "new Instance() Called");
        return new AuthLoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getLong(PHONE);
        }
    }

    public void resolveDaggerDependencies(){
        DaggerAPIComponent.builder()
                .aPIModule(new APIModule())
                .applicationComponent(getApplicationComponent())
                .build().inject(this);
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AppApplication) getActivity().getApplication()).getApplicationComponent();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        authenticationActivity = (AuthenticationActivity) getActivity();
        authLoginPresenter  = new AuthLoginPresenter(this,apiService,mySharedPreferences);
        View view = inflater.inflate(R.layout.fragment_auth_login, container, false);
        unbinder = ButterKnife.bind(this,view);
        loginPhone.setText(getString(R.string.otp_sent_mobile_number,String.valueOf(phoneNumber)));
        loginPhone.setFocusable(false);
        loginPhone.setClickable(false);
        return view;
    }

    @Override
    public void showProgressUI() {
      progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void showLoginError() {
        loginPassword.setText("");
        Toast.makeText(getActivity(), "Log in failed.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        //Destroy Auth Activity
        authenticationActivity.finish();
        Toast.makeText(getActivity(), "Logged in!", Toast.LENGTH_SHORT).show();
        loginPassword.setText("");
    }
}
