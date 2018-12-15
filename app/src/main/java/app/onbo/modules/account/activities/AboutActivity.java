package app.onbo.modules.account.activities;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.appsee.Appsee;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import app.onbo.R;

public class AboutActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_about_activity)
    Toolbar toolbar;


    @BindView(R.id.iv_show_anim)
    ImageView imageView;

    @BindView(R.id.tv_app_version)
    TextView appVersion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = packageInfo.versionName;
            int versionCode = packageInfo.versionCode;
            appVersion.setText(String.format("Version %s (%d)", versionName, versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        Appsee.start();
    }

}
