package tdevm.app_ui.root;
import android.Manifest;
import android.content.Intent;

import android.content.IntentFilter;
import android.content.IntentSender;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tbruyelle.rxpermissions2.RxPermissions;

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
import tdevm.app_ui.utils.SMSListener;

public class BottomNavigationHome extends AppCompatActivity implements NavigationHomeContract.BottomNavigationView {

    public static final String TAG = BottomNavigationHome.class.getSimpleName();
    @Inject
    BottomNavigationPresenter bottomNavigationPresenter;
    Button rView, netReq, userReg;
    Toolbar toolbarMain;
    BottomNavigationView navigation;
    FragmentTransaction fragmentTransaction;
    RxPermissions rxPermissions;

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
        toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);
        rxPermissions = new RxPermissions(this);

        rxPermissions.request(Manifest.permission.READ_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ).subscribe(granted -> {
            if (granted) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permission Required", Toast.LENGTH_SHORT).show();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }

        toolbarMain.setNavigationOnClickListener(view -> onBackPressed());
        rView.setOnClickListener(view -> bottomNavigationPresenter.handleUserAuthentication());
        userReg = findViewById(R.id.btn_reg);
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
        bottomNavigationPresenter.verifyRestaurantTableVacant(result.getContents());
    }

    @Override
    protected void onResume() {
        bottomNavigationPresenter.setView(this);
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
        intent.putExtra("RESTAURANT_UUID", restaurantUUID);
        startActivity(intent);
    }

    @Override
    public void showUserProfile() {
        Toast.makeText(BottomNavigationHome.this, "Already Logged in", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void redirectAuthActivity() {
        Intent intent = new Intent(BottomNavigationHome.this, AuthenticationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        bottomNavigationPresenter.compositeDisposable.clear();
        bottomNavigationPresenter.compositeDisposable.dispose();
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

//    private void showLocationDialog() {
//        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
//        builder.setAlwaysShow(true);
//
//        Task<LocationSettingsResponse> locationSettingsResponseTask = LocationServices.getSettingsClient(this)
//                .checkLocationSettings(builder.build());
//        locationSettingsResponseTask.addOnCompleteListener((Task<LocationSettingsResponse> task) -> {
//            try {
//                LocationSettingsResponse response = task.getResult(ApiException.class);
//                // All location settings are satisfied. The client can initialize location
//                // requests here.
//                Log.d(TAG, "Opened");
//            } catch (ApiException exception) {
//                switch (exception.getStatusCode()) {
//                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
//                        // Location settings are not satisfied. But could be fixed by showing the
//                        // user a dialog.
//                        Log.d(TAG, "Rejected");
//                        try {
//                            // Cast to a resolvable exception.
//                            ResolvableApiException resolvable = (ResolvableApiException) exception;
//                            // Show the dialog by calling startResolutionForResult(),
//                            // and check the locationSettingsResponseTask in onActivityResult().
//                            resolvable.startResolutionForResult(
//                                    BottomNavigationHome.this,
//                                    REQUEST_LOCATION);
//                        } catch (IntentSender.SendIntentException e) {
//                            // Ignore the error.
//                        } catch (ClassCastException e) {
//                            // Ignore, should be an impossible error.
//                        }
//                        break;
//                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
//                        // Location settings are not satisfied. However, we have no way to fix the
//                        // settings so we won't show the dialog.
//                        break;
//                }
//            }
//        });
//    }


    @Override
    protected void onStart() {
        super.onStart();
//        if(isPlayServicesAvailable()){
//            googleApiClient.connect();
//        }
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }
}
