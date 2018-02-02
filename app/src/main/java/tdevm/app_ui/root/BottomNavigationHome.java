package tdevm.app_ui.root;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.auth.AuthenticationActivity;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.modules.nondinein.activities.NonDineRestaurantDetailsActivity;
import tdevm.app_ui.root.fragments.AccountsFragment;
import tdevm.app_ui.root.fragments.BookFragment;
import tdevm.app_ui.root.fragments.HomeFragment;
import tdevm.app_ui.root.fragments.NotificationsFragment;
import tdevm.app_ui.utils.CustomQRView;

public class BottomNavigationHome extends AppCompatActivity implements NavigationHomeViewContract.BottomNavigationView {

    public static final String TAG = BottomNavigationHome.class.getSimpleName();
    @Inject
    BottomNavigationPresenter bottomNavigationPresenter;
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
                    bottomNavigationPresenter.verifyUserAuthentication();
                    break;
                case R.id.navigation_account:
                    bottomNavigationPresenter.handleUserAuthentication();
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
        toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
        toolbarMain.setNavigationOnClickListener(view -> onBackPressed());
        navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, HomeFragment.newInstance());
        transaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result.getContents() != null) {
            bottomNavigationPresenter.handleQRContent(result.getContents());
        }
    }


    @Override
    protected void onResume() {
        bottomNavigationPresenter.attachView(this);
        super.onResume();
    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void showTableOccupiedError() {
        Toast.makeText(BottomNavigationHome.this, "This table is occupied", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void redirectDineInActivity(String restaurantUUID) {
        Intent intent = new Intent(BottomNavigationHome.this, DineInActivity.class);
        startActivity(intent);
    }

    @Override
    public void redirectNonDineActivity() {
        Intent intent = new Intent(BottomNavigationHome.this,NonDineRestaurantDetailsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showUserProfile() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new AccountsFragment());
        transaction.commit();
    }

    @Override
    public void startQRScanner() {
        new IntentIntegrator(BottomNavigationHome.this).
                setOrientationLocked(false).
                setCaptureActivity(CustomQRView.class).
                initiateScan();
    }

    @Override
    public void redirectAuthActivity() {
        Intent intent = new Intent(BottomNavigationHome.this, AuthenticationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        bottomNavigationPresenter.detachView();
        super.onDestroy();
    }


//    private boolean isPlayServicesAvailable() {
//        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
//        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
//        if (resultCode != ConnectionResult.SUCCESS) {
//            if(apiAvailability.isUserResolvableError(resultCode)){
//                apiAvailability.getErrorDialog(this,resultCode,PLAY_SERVICES_RESOLUTION_REQUEST).show();
//            }else {
//                Toast.makeText(getApplicationContext(),
//                        "This device is not supported.", Toast.LENGTH_LONG)
//                        .show();
//                finish();
//            }
//            return false;
//        }
//        return true;
//    }


//    private void createLocationRequest() {
//        locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(30 * 1000);
//        locationRequest.setFastestInterval(5 * 1000);
//    }


    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }
}
