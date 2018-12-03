package onbo.app.modules.auth.fragments;


import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.api.models.request.User;
import onbo.app.api.models.response.UserApp;
import onbo.app.modules.auth.AuthViewContract;
import onbo.app.modules.auth.AuthenticationActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class AuthRegisterUpdate extends Fragment implements AuthViewContract.AuthRegisterUpdateView {

    public static final String TAG = AuthRegisterUpdate.class.getSimpleName();

    private static final String USER = "USER";
    private UserApp userApp;

    Unbinder unbinder;
    AuthenticationActivity authenticationActivity;

    @Inject
    AuthRegisterUpdatePresenter authRegisterPresenter;

    @BindView(R.id.tv_choose_gender_error)
    TextView chooseGenderError;

    @BindView(R.id.et_sign_up_name)
    EditText editTextName;
    @BindView(R.id.et_sign_up_email)
    EditText editTextEmail;
    @BindView(R.id.et_sign_up_password)
    EditText editTextPassword;

    @BindView(R.id.textInputLayout_et_sign_up_name)
    TextInputLayout nameWrapper;

    @BindView(R.id.textInputLayout_password)
    TextInputLayout passwordWrapper;

    @BindView(R.id.textInputLayout_sign_up_email)
    TextInputLayout emailWrapper;


    @BindView(R.id.tv_register_phone_number)
    TextView phoneNumberTextView;

    String gender;

    @BindView(R.id.btn_register)
    Button button;
    @BindView(R.id.progressBar_sign_up)
    ProgressBar progressBar;


    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean validatePassword(String password) {
        return password.length() > 7;
    }

    @BindView(R.id.tv_register_t_n_c)
    TextView termsAndConditionsText;

    @BindView(R.id.rg_register_fragment_gender)
    RadioGroup radioGroup;


    @Override
    public void onResume() {
        super.onResume();
        authRegisterPresenter.attachView(this);
    }

    @OnClick(R.id.btn_register)
    public void onButtonClick() {

        getRadioButtonInput();
        if (TextUtils.isEmpty(editTextName.getText())) {
            nameWrapper.setError("Enter your name");
        } else if (TextUtils.isEmpty(editTextEmail.getText())) {
            emailWrapper.setError("Enter your email");
        } else if (TextUtils.isEmpty(editTextPassword.getText())) {
            passwordWrapper.setError("Enter a password");
        } else if (gender == null) {
            chooseGenderError.setVisibility(View.VISIBLE);
        } else if (validatePassword(editTextPassword.getText().toString()) && validateEmail(editTextEmail.getText().toString())) {
            chooseGenderError.setVisibility(View.GONE);
            User user = new User(editTextName.getText().toString(), gender, editTextEmail.getText().toString(), Long.parseLong(userApp.getUserMobile()), editTextPassword.getText().toString());
            authRegisterPresenter.registerUserUpdate(user);
        }
    }


    public AuthRegisterUpdate() {
        // Required empty public constructor
    }


    public static AuthRegisterUpdate newInstance() {
        return new AuthRegisterUpdate();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_auth_register_update, container, false);
        resolveDaggerDependencies();
        unbinder = ButterKnife.bind(this, view);
        authenticationActivity = (AuthenticationActivity) getActivity();

        phoneNumberTextView.setText(getString(R.string.otp_sent_mobile_number, String.valueOf(userApp.getUserMobile())));
        editTextName.setText(userApp.getUserName());
        //editTextEmail.setText(userApp.getUserEmail());
        termsAndConditionsText.setLinkTextColor(getContext().getResources().getColor(R.color.primary_dark_app));
        makeLinks(termsAndConditionsText, new String[]{
                "Terms", "Privacy Policy", "Refund Policy"
        }, new ClickableSpan[]{
                termsPolicy, privacyPolicy, refundPolicy
        });


        editTextName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editTextName.getText())) {
                    nameWrapper.setError("Enter your name");
                } else {
                    nameWrapper.setErrorEnabled(false);
                }
            }
        });

        editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editTextEmail.getText())) {
                    emailWrapper.setError("Enter your email");
                } else if (!validateEmail(editTextEmail.getText().toString())) {
                    editTextEmail.setError("Enter a valid email");
                } else {
                    emailWrapper.setErrorEnabled(false);
                }
            }
        });


        editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(editTextPassword.getText())) {
                    passwordWrapper.setError("Enter a password");
                } else if (!validatePassword(editTextPassword.getText().toString())) {
                    editTextPassword.setError("Passwords must be 8 characters long");
                } else {
                    passwordWrapper.setErrorEnabled(false);
                }

            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getRadioButtonInput();
                if (gender != null) {
                    chooseGenderError.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        authRegisterPresenter.detachView();
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
        button.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }


    public void getRadioButtonInput() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        switch (selectedId) {
            case R.id.radio_button_gender_female:
                gender = "F";
                chooseGenderError.setVisibility(View.GONE);
                break;
            case R.id.radio_button_gender_male:
                gender = "M";
                chooseGenderError.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void showGenericError() {
        Toast.makeText(authenticationActivity, "Something went wrong! please try later", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void emailInUseError() {
        emailWrapper.setError("This email is already in use");
    }

    @Override
    public void showRegistrationSuccess() {
        authenticationActivity.showRootActivity();
        authenticationActivity.finish();
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            userApp = getArguments().getParcelable(USER);
        }
    }
}
