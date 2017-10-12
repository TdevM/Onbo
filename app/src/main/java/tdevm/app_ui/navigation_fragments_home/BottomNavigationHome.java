package tdevm.app_ui.navigation_fragments_home;
import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.api.models.MySharedPreferences;
import tdevm.app_ui.dagger.components.ApplicationComponent;
import tdevm.app_ui.dagger.components.DaggerAPIComponent;
import tdevm.app_ui.dagger.modules.APIModule;
import tdevm.app_ui.modules.auth.AuthenticationActivity;
import tdevm.app_ui.utils.CustomQRView;
import tdevm.app_ui.R;
public class BottomNavigationHome extends AppCompatActivity {

    @Inject
    MySharedPreferences mySharedPreferences;

    Button rView, netReq, userReg;
    Toolbar toolbarMain;
    BottomNavigationView navigation;
    FragmentTransaction fragmentTransaction;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.frame_layout, new HomeFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.navigation_booking:
                    fragmentTransaction.replace(R.id.frame_layout, new BookFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.navigation_scanner:

                    new IntentIntegrator(BottomNavigationHome.this).setOrientationLocked(false).setCaptureActivity(CustomQRView.class).initiateScan();
                    break;
                case R.id.navigation_account:
                    fragmentTransaction.replace(R.id.frame_layout, new AccountsFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.navigation_notifications:
                    fragmentTransaction.replace(R.id.frame_layout, new NotificationsFragment());
                    fragmentTransaction.commit();
                    break;
            }
            return true;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);
        rView = findViewById(R.id.btn_r_view);
        netReq = findViewById(R.id.btn_net_req);
        toolbarMain = findViewById(R.id.toolbar_main);

        DaggerAPIComponent.builder()
                .aPIModule(new APIModule())
                .applicationComponent(getApplicationComponent())
                .build().inject(this);
        setSupportActionBar(toolbarMain);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Home");
        }
        toolbarMain.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        rView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean state = mySharedPreferences.getDataBool("LOGIN_STATE");
                if(state){
                    Toast.makeText(BottomNavigationHome.this, "Already Logged in", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(BottomNavigationHome.this, AuthenticationActivity.class);
                    startActivity(intent);
                }
            }
        });
        userReg = findViewById(R.id.btn_reg);
        navigation= findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    protected ApplicationComponent getApplicationComponent() {
        return ((AppApplication)getApplication()).getApplicationComponent();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}