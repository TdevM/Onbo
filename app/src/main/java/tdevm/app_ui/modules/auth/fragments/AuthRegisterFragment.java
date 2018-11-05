package tdevm.app_ui.modules.auth.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.modules.auth.AuthViewContract;
import tdevm.app_ui.modules.auth.AuthenticationActivity;

public class AuthRegisterFragment extends Fragment implements AuthViewContract.AuthRegisterView {

    public static final String TAG = AuthRegisterFragment.class.getSimpleName();

    private static final String PHONE = "PHONE";
    private Long phoneNumber;

    Unbinder unbinder;
    AuthenticationActivity authenticationActivity;

    @Inject
    AuthRegisterPresenter authRegisterPresenter;

    @BindView(R.id.et_sign_up_name)
    EditText editTextName;
    @BindView(R.id.et_sign_up_email)
    EditText editTextEmail;
    @BindView(R.id.et_sign_up_password)
    EditText editTextPassword;


//    @BindView(R.id.et_sign_up_phone_number)
//    EditText editTextPhone;

    @BindView(R.id.tv_register_phone_number)
    TextView phoneNumberTextView;

    @BindView(R.id.btn_register)
    Button button;
    @BindView(R.id.progressBar_sign_up)
    ProgressBar progressBar;


    @BindView(R.id.tv_register_t_n_c)
    TextView termsAndConditionsText;

    @BindView(R.id.rg_register_fragment_gender)
    RadioGroup radioGroup;


    @OnClick(R.id.btn_register)
    public void onButtonClick() {
        if (TextUtils.isEmpty(editTextName.getText())) {
            Toast.makeText(getActivity(), "Fill all details", Toast.LENGTH_SHORT).show();
        } else {
            User user = new User(
                    editTextName.getText().toString(),
                    editTextEmail.getText().toString(),
                    phoneNumber, editTextPassword.getText().toString());
            authRegisterPresenter.registerUser(user);
        }
    }

    public AuthRegisterFragment() {
        // Required empty public constructor
    }

    public static AuthRegisterFragment newInstance() {
        return new AuthRegisterFragment();
    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void onResume() {
        authRegisterPresenter.attachView(this);
        super.onResume();
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
        View view = inflater.inflate(R.layout.fragment_auth_register, container, false);
        resolveDaggerDependencies();
        unbinder = ButterKnife.bind(this, view);
        authenticationActivity = (AuthenticationActivity) getActivity();

        phoneNumberTextView.setText(getString(R.string.otp_sent_mobile_number, String.valueOf(phoneNumber)));

        termsAndConditionsText.setLinkTextColor(getContext().getResources().getColor(R.color.primary_dark_app));
        makeLinks(termsAndConditionsText, new String[]{
                "Terms", "Privacy Policy", "Refund Policy"
        }, new ClickableSpan[]{
                termsPolicy, privacyPolicy, refundPolicy
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void makeLinks(TextView textView, String[] links, ClickableSpan[] clickableSpans) {
        SpannableString spannableString = new SpannableString(textView.getText());
        for (int i = 0; i < links.length; i++) {
            ClickableSpan clickableSpan = clickableSpans[i];
            String link = links[i];

            int startIndexOfLink = textView.getText().toString().indexOf(link);
            spannableString.setSpan(clickableSpan, startIndexOfLink,
                    startIndexOfLink + link.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        textView.setHighlightColor(
                Color.TRANSPARENT); // prevent TextView change background when highlight
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setText(spannableString, TextView.BufferType.SPANNABLE);
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
    public void showRegistrationError(String error) {
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
        authRegisterPresenter.detachView();
        super.onDestroy();
    }


    ClickableSpan termsPolicy = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            authenticationActivity.showTNCContent("Terms of Service", 0);
        }

    };

    ClickableSpan privacyPolicy = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            authenticationActivity.showTNCContent("Privacy Policy", 1);
        }

    };

    ClickableSpan refundPolicy = new ClickableSpan() {
        @Override
        public void onClick(View view) {
            authenticationActivity.showTNCContent("Refund Policy", 2);
        }


    };
}
