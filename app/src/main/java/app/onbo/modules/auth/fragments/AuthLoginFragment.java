package app.onbo.modules.auth.fragments;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.RestaurantTable;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.modules.auth.AuthViewContract;
import app.onbo.modules.auth.AuthenticationActivity;
import app.onbo.utils.PreferenceUtils;

//TODO Create a Base Fragment class
public class AuthLoginFragment extends Fragment implements AuthViewContract.AuthLoginView {

    public static final String TAG = AuthLoginFragment.class.getSimpleName();
    private static final String PHONE = "PHONE";
    private Long phoneNumber;
    Unbinder unbinder;

    private static final String MODE_DINE_IN = "MODE_DINE_IN";
    private static final String MODE_NON_DINE = "MODE_NON_DINE";

    @Inject
    AuthLoginPresenter authLoginPresenter;
    AuthenticationActivity authenticationActivity;

    @Inject
    PreferenceUtils preferenceUtils;

    @BindView(R.id.tv_login_phone_number)
    TextView loginPhoneNumber;


    @BindView(R.id.textInputLayout_password)
    TextInputLayout passwordWrapper;

    @BindView(R.id.et_login_password)
    EditText loginPassword;
    @BindView(R.id.btn_login)
    Button buttonLogin;
    @BindView(R.id.progressBar_login)
    ProgressBar progressBar;

    @OnClick(R.id.btn_login)
    public void onButtonClick() {
        if (TextUtils.isEmpty(loginPassword.getText())) {
            passwordWrapper.setError("Enter your password");
        } else {
            authLoginPresenter.loginUser(phoneNumber, loginPassword.getText().toString());
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
    public void onResume() {
        authLoginPresenter.attachView(this);
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            phoneNumber = getArguments().getLong(PHONE);
        }
    }

    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        authenticationActivity = (AuthenticationActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_auth_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        loginPhoneNumber.setText(getString(R.string.otp_sent_mobile_number, String.valueOf(phoneNumber)));
        loginPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(loginPassword.getText())) {
                    passwordWrapper.setError("Enter your password");
                } else {
                    passwordWrapper.setErrorEnabled(false);
                }
            }
        });
        return view;
    }

    @Override
    public void showProgressUI() {
        progressBar.setVisibility(View.VISIBLE);
        buttonLogin.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
        buttonLogin.setVisibility(View.VISIBLE);

    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void showLoginError() {
        loginPassword.setText("");
        passwordWrapper.setError("Password incorrect");
        //Toast.makeText(getActivity(), "Password incorrect", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginSuccess() {
        authLoginPresenter.checkCurrentOrderDetails();
    }

    @Override
    public void showGenericError() {
        loginPassword.setText("");
        Toast.makeText(getActivity(), "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDineOrderRunning(TOrder tOrder) {
        Restaurant restaurant = tOrder.getRestaurant();
        RestaurantTable table = tOrder.getRestaurantTable();
        String tableShortId = restaurant.getRestaurant_uuid() + "_" + table.getTable_number();
        preferenceUtils.saveDineQRTransaction(restaurant.getRestaurant_id(), restaurant.getRestaurant_uuid(), table.getRestaurant_table_id(), tableShortId, MODE_DINE_IN);
        // Check payment
        if (tOrder.getClosed()) {
            authLoginPresenter.fetchClosedOrder(tOrder.getOrderId());
        } else {
            authenticationActivity.showDineActivity(restaurant);
            authenticationActivity.finish();
        }

    }

    @Override
    public void onNoDineOrderRunning() {
        //Destroy Auth Activity
        authenticationActivity.showRootActivity();
        authenticationActivity.finish();
    }

    @Override
    public void onOrderFetchFailure() {
        Toast.makeText(authenticationActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFOrderFetched(FOrder fOrder) {
        authenticationActivity.showDinePaymentOptions(fOrder);
    }

    @Override
    public void onFOrderFetchFailure() {
        Toast.makeText(authenticationActivity, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        authLoginPresenter.detachView();
        super.onDestroy();
    }
}
