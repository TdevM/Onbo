package tdevm.app_ui.utils;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import tdevm.app_ui.R;

public class CustomQRView extends AppCompatActivity implements
        DecoratedBarcodeView.TorchListener {

    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private Button switchFlashlightButton;
    FloatingActionButton flashLightButtonOn;
    FloatingActionButton flashLightButtonOff;
    FloatingActionButton floatingActionButtonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_qrview);

        barcodeScannerView = findViewById(R.id.zxing_barcode_scanner);
        barcodeScannerView.setTorchListener(this);

        //  switchFlashlightButton = (Button)findViewById(R.id.switch_flashlight);
        floatingActionButtonBack = (FloatingActionButton) findViewById(R.id.floatingActionButton_back);
        flashLightButtonOn = (FloatingActionButton) findViewById(R.id.floatingActionButton_flash);
        flashLightButtonOff = (FloatingActionButton) findViewById(R.id.floatingActionButton_flash_off);

        // if the device does not have flashlight in its camera,
        // then remove the switch flashlight button...
        if (!hasFlash()) {
            switchFlashlightButton.setVisibility(View.GONE);
            flashLightButtonOn.setVisibility(View.GONE);
        }

        capture = new CaptureManager(this, barcodeScannerView);
        capture.initializeFromIntent(getIntent(), savedInstanceState);
        capture.decode();

        flashLightButtonOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeScannerView.setTorchOn();
                flashLightButtonOn.setVisibility(View.GONE);
                flashLightButtonOff.setVisibility(View.VISIBLE);
            }
        });

        flashLightButtonOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                barcodeScannerView.setTorchOff();
                flashLightButtonOff.setVisibility(View.GONE);
                flashLightButtonOn.setVisibility(View.VISIBLE);
            }
        });

        floatingActionButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return barcodeScannerView.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event);
    }

    /**
     * Check if the device's camera has a Flashlight.
     *
     * @return true if there is Flashlight, otherwise false.
     */
    private boolean hasFlash() {
        return getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

//    public void switchFlashlight(View view) {
//        if (getString(R.string.turn_on_flashlight).equals(switchFlashlightButton.getText())) {
//            barcodeScannerView.setTorchOn();
//        } else {
//            barcodeScannerView.setTorchOff();
//        }
//    }

    @Override
    public void onTorchOn() {
        // switchFlashlightButton.setText(R.string.turn_off_flashlight);
        flashLightButtonOn.setVisibility(View.GONE);
        flashLightButtonOff.setVisibility(View.VISIBLE);

    }

    @Override
    public void onTorchOff() {
        //  switchFlashlightButton.setText(R.string.turn_on_flashlight);
        flashLightButtonOn.setVisibility(View.VISIBLE);
        flashLightButtonOff.setVisibility(View.GONE);
    }
}