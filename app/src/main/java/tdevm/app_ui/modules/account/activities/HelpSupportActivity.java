package tdevm.app_ui.modules.account.activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.account.fragments.HNSActionList;
import tdevm.app_ui.modules.account.fragments.HNSWebViewFragment;

public class HelpSupportActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_support);
        ButterKnife.bind(this);
        showHNSActionList();
    }


    void showHNSActionList() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        HNSActionList actionList = new HNSActionList();
        transaction.replace(R.id.frame_layout_help_n_support, actionList);
        transaction.commit();
    }

    public void showHNSContent(String title, int value) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_help_n_support, HNSWebViewFragment.newInstance(title, value));
        transaction.addToBackStack("HNS_List");
        transaction.commit();

    }
}

