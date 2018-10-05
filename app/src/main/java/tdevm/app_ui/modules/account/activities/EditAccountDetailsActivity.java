package tdevm.app_ui.modules.account.activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.modules.account.AccountViewContract;
import tdevm.app_ui.modules.account.EditAccountDetailPresenter;

public class EditAccountDetailsActivity extends AppCompatActivity implements AccountViewContract.EditAccountView {


    @BindView(R.id.et_user_email_edit)
    TextInputEditText email;
    @BindView(R.id.et_user_name_edit)
    TextInputEditText name;
    @BindView(R.id.btn_update_user_details)
    Button updateButton;
    @BindView(R.id.progress_bar_update_account_details)
    ProgressBar progressBar;

    @Inject
    EditAccountDetailPresenter presenter;


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
            UserApp user = new UserApp(name.getText().toString(),email.getText().toString());
            presenter.updateUser(user);
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
