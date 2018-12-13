package app.onbo.modules.auth;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appsee.Appsee;

import app.onbo.R;

import app.onbo.api.models.response.UserApp;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.modules.auth.fragments.AuthInitFragment;
import app.onbo.modules.auth.fragments.AuthLoginFragment;
import app.onbo.modules.auth.fragments.AuthRegisterFragment;
import app.onbo.modules.auth.fragments.AuthRegisterUpdate;
import app.onbo.modules.auth.fragments.VerifyPhoneOTPFragment;
import app.onbo.modules.dinein.DineInActivity;
import app.onbo.modules.payment.PaymentActivity;
import app.onbo.root.RootActivity;

//TODO back stack management.
public class AuthenticationActivity extends AppCompatActivity implements AuthInitFragment.AuthInitInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);
        Appsee.start();

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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE", phone);
        verifyPhoneOTPFragment.setArguments(bundle);
        transaction
                .replace(R.id.frame_layout_auth_activity, verifyPhoneOTPFragment, "OTP_VERIFICATION")
                .addToBackStack("OTP_VERIFICATION")
                .commit();

    }

    public void showLoginFragment(Long phone) {
        AuthLoginFragment authLoginFragment = AuthLoginFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE", phone);
        authLoginFragment.setArguments(bundle);

        transaction.replace(R.id.frame_layout_auth_activity, authLoginFragment, "LOGIN")
                .addToBackStack("LOGIN")
                .commit();

    }

    public void showRegisterFragment(Long phone) {
        AuthRegisterFragment authRegisterFragment = AuthRegisterFragment.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE", phone);
        authRegisterFragment.setArguments(bundle);

        transaction.replace(R.id.frame_layout_auth_activity, authRegisterFragment, "REGISTER")
                .commit();
    }

    public void showRegisterUpdateFragment(UserApp userApp) {
        AuthRegisterUpdate authRegisterFragment = AuthRegisterUpdate.newInstance();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putParcelable("USER", userApp);
        authRegisterFragment.setArguments(bundle);

        transaction.replace(R.id.frame_layout_auth_activity, authRegisterFragment, "REGISTER")
                .commit();
    }

    @Override
    public void onAuthInitInteractionListener(Uri uri) {

    }

    //public

    public void showRootActivity() {
        Intent intent = new Intent(this, RootActivity.class);
        startActivity(intent);
    }

    public void showDinePaymentOptions(FOrder fOrder) {
        Intent i = new Intent(AuthenticationActivity.this, PaymentActivity.class);
        i.putExtra("F_ORDER_AVAILABLE", true);
        i.putExtra("F_ORDER", fOrder);
        i.putExtra("F_ORDER_ID", fOrder.getOrder_id());
        i.putExtra("T_ORDER_ID", fOrder.getT_order_id());
        startActivity(i);
        finish();
    }

    public void showDineActivity(Restaurant restaurant) {
        Intent i = new Intent(AuthenticationActivity.this, DineInActivity.class);
        i.putExtra("RESTAURANT", restaurant);
        startActivity(i);
    }

    public void showTNCContent(String title, int value) {
        Intent intent = new Intent(AuthenticationActivity.this, AuthTnCActivity.class);
        intent.putExtra("TITLE", title);
        intent.putExtra("VALUE", value);
        startActivity(intent);
    }

}
