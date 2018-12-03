package onbo.app.modules.account.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.appsee.Appsee;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.api.models.response.UserApp;
import onbo.app.modules.account.AccountViewContract;
import onbo.app.modules.account.EditAccountDetailPresenter;

public class EditAccountDetailsActivity extends AppCompatActivity implements AccountViewContract.EditAccountView {


    @BindView(R.id.et_user_email_edit)
    TextInputEditText email;
    @BindView(R.id.et_user_name_edit)
    TextInputEditText name;

    @BindView(R.id.textInputLayout_user_name)
    TextInputLayout userNameWrapper;

    @BindView(R.id.textInputLayout_user_email)
    TextInputLayout userEmailWrapper;

    @BindView(R.id.btn_update_user_details)
    Button updateButton;
    @BindView(R.id.progress_bar_update_account_details)
    ProgressBar progressBar;

    @Inject
    EditAccountDetailPresenter presenter;


    @BindView(R.id.toolbar_edit_account)
    android.support.v7.widget.Toolbar toolbar;


    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9#_~!$&'()*+,;=:.\"(),:;<>@\\[\\]\\\\]+@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*$";
    private Pattern pattern = Pattern.compile(EMAIL_PATTERN);
    private Matcher matcher;

    public boolean validateEmail(String email) {
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    UserApp userApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        Appsee.start();
        userApp = getIntent().getParcelableExtra("user_details");
        if (userApp != null) {
            setContentView(R.layout.activity_edit_account_details);
            ButterKnife.bind(this);
            if (userApp.getUserName() != null && userApp.getUserEmail() != null) {
                name.setText(userApp.getUserName());
                email.setText(userApp.getUserEmail());
            }

        }
        updateButton.setOnClickListener(v -> {


            if (TextUtils.isEmpty(name.getText())) {
                userNameWrapper.setError("Enter your name");
            } else if (TextUtils.isEmpty(email.getText())) {
                userEmailWrapper.setError("Enter your email");
            } else if (validateEmail(email.getText().toString())) {
                UserApp user = new UserApp(name.getText().toString(), email.getText().toString());
                presenter.updateUser(user);
            }

        });
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());


        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(email.getText())) {
                    userEmailWrapper.setError("Enter your email");
                } else if (!validateEmail(email.getText().toString())) {
                    email.setError("Enter a valid email");
                } else {
                    userEmailWrapper.setErrorEnabled(false);
                }
            }
        });

        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(name.getText())) {
                    userNameWrapper.setError("Enter your name");
                } else {
                    userNameWrapper.setErrorEnabled(false);
                }
            }
        });


    }

    @Override
    public void showProgressUI() {
        progressBar.setVisibility(View.VISIBLE);
        updateButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
        updateButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onAccountDetailsUpdated(Object app) {
        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onAccountDetailsUpdateFailure() {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }
}
