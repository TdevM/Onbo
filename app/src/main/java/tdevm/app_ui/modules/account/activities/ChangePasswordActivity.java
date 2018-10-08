package tdevm.app_ui.modules.account.activities;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.request.Password;
import tdevm.app_ui.modules.account.AccountViewContract;
import tdevm.app_ui.modules.account.ChangePasswordPresenter;

public class ChangePasswordActivity extends AppCompatActivity implements AccountViewContract.ChangePasswordView {

    @BindView(R.id.et_old_password)
    TextInputEditText oldPassword;

    @BindView(R.id.et_new_password)
    TextInputEditText newPassword;

    @BindView(R.id.btn_confirm_password_change)
    Button confirmPasswordChange;

    @OnClick(R.id.btn_confirm_password_change)
    void callChangePassword(){
        changeUserPassword();
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
    }

    @Override
    public void onPasswordChangeFailure(Object o) {
        Toast.makeText(this, "Failed to change password", Toast.LENGTH_SHORT).show();
    }


    private void changeUserPassword(){
        Password password = new Password(oldPassword.getText().toString(),newPassword.getText().toString());
        passwordPresenter.changeUserPassword(password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        passwordPresenter.detachView();
    }
}
