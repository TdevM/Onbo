package tdevm.app_ui.modules.auth.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
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
import tdevm.app_ui.api.models.request.User;

import tdevm.app_ui.modules.auth.AuthViewContract;
import tdevm.app_ui.modules.auth.AuthenticationActivity;

public class AuthRegisterFragment extends Fragment implements AuthViewContract.AuthRegisterView{

    public static final String TAG = AuthRegisterFragment.class.getSimpleName();

    private static final String PHONE = "PHONE";
    private Long phoneNumber;

    @Inject
    MySharedPreferences mySharedPreferences;
    @Inject
    APIService apiService;

    Unbinder unbinder;
    AuthenticationActivity authenticationActivity;
    AuthRegisterPresenter authRegisterPresenter;

    @BindView(R.id.et_sign_up_name)
     EditText editTextName;
    @BindView(R.id.et_sign_up_email)
     EditText editTextEmail;
    @BindView(R.id.et_sign_up_password)
     EditText editTextPassword;
    @BindView(R.id.et_sign_up_phone_number)
     EditText editTextPhone;
    @BindView(R.id.btn_register)
    Button button;
    @BindView(R.id.progressBar_sign_up)
    ProgressBar progressBar;

    @OnClick(R.id.btn_register)
    public void onButtonClick(){
        if(TextUtils.isEmpty(editTextName.getText())){
            Toast.makeText(getActivity(), "Fill all details", Toast.LENGTH_SHORT).show();
        }else {
            User user = new User(editTextName.getText().toString(),editTextEmail.getText().toString(),phoneNumber,editTextPassword.getText().toString());
            authRegisterPresenter.registerUser(user);
        }
    }

    public AuthRegisterFragment() {
        // Required empty public constructor
    }

    public static AuthRegisterFragment newInstance(){
        return new AuthRegisterFragment();
    }

    public void resolveDaggerDependencies(){
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
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
        Log.d(TAG,"mySharedPrefs: "+mySharedPreferences);
        Log.d(TAG,"API Service: "+apiService);
        View view = inflater.inflate(R.layout.fragment_auth_register, container, false);
        resolveDaggerDependencies();
        unbinder = ButterKnife.bind(this,view);
        authenticationActivity = (AuthenticationActivity) getActivity();
        authRegisterPresenter = new AuthRegisterPresenter(this,apiService,mySharedPreferences);
        editTextPhone.setText(getString(R.string.otp_sent_mobile_number,String.valueOf(phoneNumber)));
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
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
    public void showRegistrationError() {
        Toast.makeText(authenticationActivity, "Registration Failed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegistrationSuccess() {
        Toast.makeText(authenticationActivity, "Registered", Toast.LENGTH_SHORT).show();
        authenticationActivity.finish();
    }

    @Override
    public void showDuplicationError(String message) {
        Toast.makeText(authenticationActivity, "Email already registered", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        authRegisterPresenter.compositeDisposable.dispose();
        authRegisterPresenter.compositeDisposable.clear();
        super.onDestroy();
    }
}
