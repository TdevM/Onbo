package tdevm.app_ui.modules.auth;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tdevm.app_ui.R;
import tdevm.app_ui.modules.account.fragments.HNSWebViewFragment;

public class AuthTnCActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_tn_c);

        int i = getIntent().getIntExtra("VALUE", 0);
        showHNSContent(getIntent().getStringExtra("TITLE"), i);

    }


    public void showHNSContent(String title, int value) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_auth_t_n_c, HNSWebViewFragment.newInstance(title, value));
        transaction.commit();

    }
}
