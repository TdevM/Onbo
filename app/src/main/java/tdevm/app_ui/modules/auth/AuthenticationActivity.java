package tdevm.app_ui.modules.auth;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import tdevm.app_ui.R;

import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.modules.auth.fragments.AuthInitFragment;
import tdevm.app_ui.modules.auth.fragments.AuthLoginFragment;
import tdevm.app_ui.modules.auth.fragments.AuthRegisterFragment;
import tdevm.app_ui.modules.auth.fragments.VerifyPhoneOTPFragment;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.modules.intro.SplashActivity;
import tdevm.app_ui.modules.payment.PaymentActivity;
import tdevm.app_ui.root.RootActivity;

//TODO back stack management.
public class AuthenticationActivity extends AppCompatActivity implements AuthInitFragment.AuthInitInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_layout_auth_activity);

        if (fragment == null) {
            fragment = new AuthInitFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_auth_activity, fragment).commit();
        }
    }

    public void showOTPVerificationFragment(Long phone) {
        VerifyPhoneOTPFragment verifyPhoneOTPFragment = VerifyPhoneOTPFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE", phone);
        verifyPhoneOTPFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_layout_auth_activity, verifyPhoneOTPFragment).commit();

    }

    public void showLoginFragment(Long phone) {
        AuthLoginFragment authLoginFragment = AuthLoginFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE", phone);
        authLoginFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_layout_auth_activity, authLoginFragment).commit();

    }

    public void showRegisterFragment(Long phone) {
        AuthRegisterFragment authRegisterFragment = AuthRegisterFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE", phone);
        authRegisterFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .replace(R.id.frame_layout_auth_activity, authRegisterFragment).commit();
    }

    @Override
    public void onAuthInitInteractionListener(Uri uri) {

    }

    public void showRootActivity(){
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
    }

    public void showDinePaymentOptions(FOrder fOrder){
        Intent i = new Intent(AuthenticationActivity.this, PaymentActivity.class);
        i.putExtra("PAYMENT_PENDING", true);
        i.putExtra("F_ORDER", fOrder);
        i.putExtra("ORDER_ID", fOrder.getOrder_id());
        startActivity(i);
        finish();
    }

    public void showDineActivity(Restaurant restaurant){
        Intent i = new Intent(AuthenticationActivity.this, DineInActivity.class);
        i.putExtra("RESTAURANT",restaurant);
        startActivity(i);
    }

}
