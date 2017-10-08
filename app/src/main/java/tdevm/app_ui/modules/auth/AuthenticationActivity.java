package tdevm.app_ui.modules.auth;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import tdevm.app_ui.R;
import tdevm.app_ui.modules.auth.fragments.AuthInitFragment;
import tdevm.app_ui.modules.auth.fragments.AuthLoginFragment;
import tdevm.app_ui.modules.auth.fragments.AuthRegisterFragment;
import tdevm.app_ui.modules.auth.fragments.VerifyPhoneOTPFragment;
import tdevm.app_ui.navigation_fragments_home.HomeFragment;

public class AuthenticationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_auth_activity, AuthRegisterFragment.newInstance());
        transaction.commit();
    }
}
