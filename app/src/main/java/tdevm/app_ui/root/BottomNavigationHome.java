package tdevm.app_ui.root;
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
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.MySharedPreferences;
import tdevm.app_ui.modules.auth.AuthenticationActivity;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.root.fragments.AccountsFragment;
import tdevm.app_ui.root.fragments.BookFragment;
import tdevm.app_ui.root.fragments.HomeFragment;
import tdevm.app_ui.root.fragments.NotificationsFragment;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CustomQRView;
import tdevm.app_ui.R;
public class BottomNavigationHome extends AppCompatActivity implements NavigationHomeContract.BottomNavigationView {

    @Inject
    MySharedPreferences mySharedPreferences;
    @Inject
    APIService apiService;
    private BottomNavigationPresenter bottomNavigationPresenter;
    private AuthUtils authUtils;
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
        resolveDaggerDependencies();
        setContentView(R.layout.activity_bottom_navigation);
        rView = findViewById(R.id.btn_r_view);
        netReq = findViewById(R.id.btn_net_req);
        authUtils = new AuthUtils(mySharedPreferences);
        bottomNavigationPresenter = new BottomNavigationPresenter(apiService,authUtils,this);
        toolbarMain = findViewById(R.id.toolbar_main);
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
                if(authUtils.getAuthLoginState()){
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
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        bottomNavigationPresenter.verifyRestaurantTableVacant(result.getContents());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void resolveDaggerDependencies(){
        ((AppApplication)getApplication()).getApiComponent().inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void showTableOccupiedError() {
      Toast.makeText(BottomNavigationHome.this,"This table is occupied",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectDineInActivity(String restaurantUUID) {
        Intent intent = new Intent(BottomNavigationHome.this, DineInActivity.class);
        intent.putExtra("RESTAURANT_UUID",restaurantUUID);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        bottomNavigationPresenter.compositeDisposable.clear();
        bottomNavigationPresenter.compositeDisposable.dispose();
        super.onDestroy();
    }
}