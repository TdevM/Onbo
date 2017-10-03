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

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.HashMap;
import java.util.Map;

import tdevm.app_ui.ui.view_pagers.VPagerMain;
import tdevm.app_ui.utils.CustomQRView;
import tdevm.app_ui.R;
public class BottomNavigationHome extends AppCompatActivity {

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
        rView = (Button) findViewById(R.id.btn_r_view);
        netReq = (Button) findViewById(R.id.btn_net_req);
        toolbarMain = (Toolbar)findViewById(R.id.toolbar_main);


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

        userReg = (Button)findViewById(R.id.btn_reg);
        navigation= (BottomNavigationView) findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();



        userReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(BottomNavigationHome.this, VPagerMain.class);
                startActivity(i);

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    }
    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}