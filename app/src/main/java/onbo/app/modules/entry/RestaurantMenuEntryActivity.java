package onbo.app.modules.entry;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;
import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.modules.dinein.DineInActivity;
import onbo.app.modules.nondine.NonDineActivity;
import onbo.app.utils.PreferenceUtils;
import onbo.app.utils.CustomQRView;

@RuntimePermissions
public class RestaurantMenuEntryActivity extends AppCompatActivity implements MenuEntryViewContract.RestaurantMenuEntryView {
    public static final String TAG = RestaurantMenuEntryActivity.class.getSimpleName();

    private static final int REQUEST_CHECK_SETTINGS = 0x1;
    private static final int QR_RESULT = 100;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    private Location mCurrentLocation;


    private boolean locationRequestMade = false;
    private boolean QR_SCANNER_SHOWN = false;


    @BindView(R.id.fl_request_location)
    FrameLayout requestLocation;

    @BindView(R.id.fl_show_verifying)
    FrameLayout showVerifying;

    @BindView(R.id.fl_show_getting_menu)
    FrameLayout showGettingMenu;

    @BindView(R.id.fl_show_wrong_location)
    FrameLayout wrongLocation;

    @BindView(R.id.fl_show_qr_wrong)
    FrameLayout wrongQR;

    @BindView(R.id.fl_show_table_occupied)
    FrameLayout tableOccupied;


    @BindView(R.id.btn_fetch_location)
    Button startLocation;

    @BindView(R.id.tv_show_fetching)
    TextView textView;

    @OnClick(R.id.btn_fetch_location)
    void fetchLocation() {
        initializeScanning();
    }

    @OnClick(R.id.btn_fetch_location_retry_start_anyway)
    void run(){
        initializeScanning();
    }

    @OnClick(R.id.btn_fetch_location_retry_retry)
    void run1(){
        initializeScanning();
    }

    @OnClick(R.id.btn_fetch_location_retry_retry_table_retry)
    void run2(){
        initializeScanning();
    }

    void initializeScanning(){
        locationRequestMade = true;
        QR_SCANNER_SHOWN = false;
        if (checkPermissions()) {
            startLocationUpdates();
            showLocationFetching();
        } else if (!checkPermissions()) {
            RestaurantMenuEntryActivityPermissionsDispatcher.requestLocationUpdatesWithPermissionCheck(this);
        }
    }

    @Inject
    FusedLocationProviderClient fusedLocationProviderClient;
    @Inject
    SettingsClient settingsClient;
    @Inject
    PreferenceUtils preferenceUtils;
    @Inject
    MenuEntryPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "ON_CREATE");
        setContentView(R.layout.activity_restaurant_menu_entry);
        ButterKnife.bind(this);
        resolveDaggerDependencies();
        createLocationCallback();
        createLocationRequest();
        buildLocationSettingsRequest();

    }

    public void createLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(500);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void createLocationCallback() {
        mLocationCallback = new LocationCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                mCurrentLocation = locationResult.getLastLocation();
                if (mCurrentLocation != null) {
                    presenter.handleLocationUpdates(locationResult);
                    Log.d(TAG, "Provider: " + mCurrentLocation.getProvider());
                    Log.d(TAG, "Accuracy: " + String.valueOf(mCurrentLocation.getAccuracy()));
                    Log.d(TAG, "Altitude :" + String.valueOf(mCurrentLocation.getAltitude()));
                    Log.d(TAG, "Speed: " + String.valueOf(mCurrentLocation.getSpeed()));
                    Log.d(TAG, "Bearing :" + String.valueOf(mCurrentLocation.getBearing()));
                    Log.d(TAG, "Time: " + String.valueOf(mCurrentLocation.getTime()));
                    Log.d(TAG, "CompleteObject: " + mCurrentLocation.toString());
                }

            }
        };
    }

    private void buildLocationSettingsRequest() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        mLocationSettingsRequest = builder.build();
    }


    @Override
    public void showProgressUI() {
        //progressBar.setVisibility(View.VISIBLE);
        startLocation.setVisibility(View.GONE);
        // textView.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressUI() {
        //progressBar.setVisibility(View.GONE);
        textView.setVisibility(View.GONE);
        startLocation.setVisibility(View.VISIBLE);
    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }


    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    public void requestLocationUpdates() {

    }

    @SuppressWarnings("MissingPermission")
    public void startLocationUpdates() {
        // Begin by checking if the device has the necessary location settings.
        settingsClient.checkLocationSettings(mLocationSettingsRequest)
                .addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i(TAG, "All location settings are satisfied.");

                        //noinspection MissingPermission
                        fusedLocationProviderClient.requestLocationUpdates(mLocationRequest,
                                mLocationCallback, Looper.myLooper());
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i(TAG, "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings ");
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult(RestaurantMenuEntryActivity.this, REQUEST_CHECK_SETTINGS);
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i(TAG, "PendingIntent unable to execute request.");
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e(TAG, errorMessage);
                                Toast.makeText(RestaurantMenuEntryActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "ON_RESUME");
        presenter.attachView(this);
//        if (QR_SCANNER_SHOWN) {
//            stopLocationUpdates();
//            showFetchLocation();
//        } else {
        if (locationRequestMade) {
            if (checkPermissions()) {
                startLocationUpdates();
                //showProgressUI();
                showLocationFetching();
            } else if (!checkPermissions()) {
                RestaurantMenuEntryActivityPermissionsDispatcher.requestLocationUpdatesWithPermissionCheck(this);
            }
        }
        //  }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "ON_ACTIVITY_RESULT");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                stopLocationUpdates();
                locationRequestMade = false;
                presenter.setQrScannerShown(false);
                showFetchLocation();
            } else {
                presenter.handleQRContent(result.getContents());
                // hideProgressUI();
            }
        } else {
            //super.onActivityResult(requestCode, resultCode, data);
            stopLocationUpdates();
            showFetchLocation();
        }
        switch (requestCode) {
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.i(TAG, "User agreed to make required location settings changes.");
                        //Presenter start updates.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.i(TAG, "User chose not to make required location settings changes.");
                        stopLocationUpdates();
                        showFetchLocation();
                        locationRequestMade = false;
                        break;
                }
                break;
//            case QR_RESULT:
//                if(resultCode == Activity.RESULT_OK){
//                    String result = data.getStringExtra("result");
//                    presenter.handleQRContent(result);
//                }
//                break;
        }
    }

    @Override
    public void redirectDineInActivity(Restaurant restaurant) {
        Intent intent = new Intent(RestaurantMenuEntryActivity.this, DineInActivity.class);
        intent.putExtra("RESTAURANT", restaurant);
        startActivity(intent);
        finish();
    }

    @Override
    public void showMalformedQRCode() {
        //Toast.makeText(this, "You seem to have scanned a wrong qr code.", Toast.LENGTH_SHORT).show();
        stopLocationUpdates();
        showWrongQr();
        //showFetchLocation();
        locationRequestMade = false;
    }

    @Override
    public void showWrongLocationError() {
        showWrongLocation();
        locationRequestMade = false;
        stopLocationUpdates();
    }

    @Override
    public void redirectNonDineActivity(Restaurant restaurant) {
        Intent intent = new Intent(RestaurantMenuEntryActivity.this, NonDineActivity.class);
        intent.putExtra("RESTAURANT", restaurant);
        startActivity(intent);
        finish();
    }


    public void showLocationFetching() {
        showVerifying.setVisibility(View.VISIBLE);

        requestLocation.setVisibility(View.GONE);
        showGettingMenu.setVisibility(View.GONE);
        wrongLocation.setVisibility(View.GONE);
        wrongQR.setVisibility(View.GONE);
        tableOccupied.setVisibility(View.GONE);
    }

    public void showFetchLocation() {
        requestLocation.setVisibility(View.VISIBLE);

        showVerifying.setVisibility(View.GONE);
        showGettingMenu.setVisibility(View.GONE);
        wrongLocation.setVisibility(View.GONE);
        wrongQR.setVisibility(View.GONE);
        tableOccupied.setVisibility(View.GONE);
    }

    public void showGettingMenu() {
        showGettingMenu.setVisibility(View.VISIBLE);

        requestLocation.setVisibility(View.GONE);
        showVerifying.setVisibility(View.GONE);
        wrongLocation.setVisibility(View.GONE);
        wrongQR.setVisibility(View.GONE);
        tableOccupied.setVisibility(View.GONE);
    }


    public void showWrongQr() {
        wrongQR.setVisibility(View.VISIBLE);

        showGettingMenu.setVisibility(View.GONE);
        requestLocation.setVisibility(View.GONE);
        showVerifying.setVisibility(View.GONE);
        wrongLocation.setVisibility(View.GONE);
        tableOccupied.setVisibility(View.GONE);
    }

    public void showWrongLocation() {
        wrongLocation.setVisibility(View.VISIBLE);

        showGettingMenu.setVisibility(View.GONE);
        wrongQR.setVisibility(View.GONE);
        requestLocation.setVisibility(View.GONE);
        showVerifying.setVisibility(View.GONE);
        tableOccupied.setVisibility(View.GONE);
    }

    public void showTableOccupied() {
        tableOccupied.setVisibility(View.VISIBLE);

        showGettingMenu.setVisibility(View.GONE);
        wrongLocation.setVisibility(View.GONE);
        requestLocation.setVisibility(View.GONE);
        showVerifying.setVisibility(View.GONE);
        wrongQR.setVisibility(View.GONE);
    }

    @Override
    public void showTableOccupiedError() {
        //hideProgressUI();
        //Toast.makeText(RestaurantMenuEntryActivity.this, "This table is occupied", Toast.LENGTH_SHORT).show();
        //showFetchLocation();
        showTableOccupied();
        stopLocationUpdates();
        locationRequestMade = false;
    }


    @Override
    public void startQRScanner() {
        new IntentIntegrator(RestaurantMenuEntryActivity.this).
                setOrientationLocked(false).
                setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES).
                setCaptureActivity(CustomQRView.class).
                initiateScan();
        QR_SCANNER_SHOWN = true;
        //locationRequestMade = false;
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    void showDenied() {
        Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    void showRationale(PermissionRequest request) {
        new android.app.AlertDialog.Builder(this)
                .setMessage("Location Permission Needed")
                .setPositiveButton("OK", (dialog, button) -> request.proceed())
                .setNegativeButton("CANCEL", (dialog, button) -> request.cancel())
                .show();
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    void showNeverAsk() {
        Toast.makeText(this, "Never ask again", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "Location updates stopped");

                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "ON_PAUSE");
        stopLocationUpdates();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "ON_DESTROY");
        presenter.detachView();
        stopLocationUpdates();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        RestaurantMenuEntryActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

}
