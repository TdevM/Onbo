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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.api.models.request.Password;
import onbo.app.modules.account.AccountViewContract;
import onbo.app.modules.account.ChangePasswordPresenter;

public class ChangePasswordActivity extends AppCompatActivity implements AccountViewContract.ChangePasswordView {

    @BindView(R.id.textInputLayout_old_pass)
    TextInputLayout oldPassword;

    @BindView(R.id.textInputLayout_new_pass)
    TextInputLayout newPassword;

    @BindView(R.id.btn_confirm_password_change)
    Button confirmPasswordChange;

    @BindView(R.id.et_old_password)
    TextInputEditText oldPasswordEt;

    @BindView(R.id.et_new_password)
    TextInputEditText newPasswordEt;

    @OnClick(R.id.btn_confirm_password_change)
    void callChangePassword() {
        if (TextUtils.isEmpty(oldPasswordEt.getText())) {
            oldPassword.setError("Enter old password");
        } else if (TextUtils.isEmpty(newPasswordEt.getText())) {
            newPassword.setError("Enter new password");
        } else if (validatePassword(newPasswordEt.getText().toString())) {
            changeUserPassword();
        }

    }


    @BindView(R.id.toolbar_change_password)
    android.support.v7.widget.Toolbar toolbar;

    @BindView(R.id.progress_bar_change_password)
    ProgressBar progressBar;

    @Inject
    ChangePasswordPresenter passwordPresenter;


    @Override
    protected void onResume() {
        super.onResume();
        passwordPresenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_change_password);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());


        oldPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(oldPasswordEt.getText())) {
                    oldPassword.setError("Enter old password");
                } else {
                    oldPassword.setErrorEnabled(false);
                }

            }
        });


        newPasswordEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(newPasswordEt.getText())) {
                    newPassword.setError("Enter new password");
                } else if (!validatePassword(newPasswordEt.getText().toString())) {
                    newPasswordEt.setError("Passwords must be 8 characters long");
                } else {
                    newPassword.setErrorEnabled(false);
                }

            }
        });

    }


    public boolean validatePassword(String password) {
        return password.length() > 7;
    }

    @Override
    public void showProgressUI() {
        progressBar.setVisibility(View.VISIBLE);
        confirmPasswordChange.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
        confirmPasswordChange.setVisibility(View.VISIBLE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);

    }

    @Override
    public void onPasswordChangeSuccess(Object o) {
        Toast.makeText(this, "Password Changed", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPasswordChangeFailure(Object o) {
        oldPassword.setError("Incorrect password");
    }

    @Override
    public void onGenericError() {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }


    private void changeUserPassword() {
        Password password = new Password(oldPasswordEt.getText().toString(), newPasswordEt.getText().toString());
        passwordPresenter.changeUserPassword(password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        passwordPresenter.detachView();
    }
}
