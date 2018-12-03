package onbo.app.modules.auth;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import onbo.app.R;

import onbo.app.api.models.response.UserApp;
import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.modules.auth.fragments.AuthInitFragment;
import onbo.app.modules.auth.fragments.AuthLoginFragment;
import onbo.app.modules.auth.fragments.AuthRegisterFragment;
import onbo.app.modules.auth.fragments.AuthRegisterUpdate;
import onbo.app.modules.auth.fragments.VerifyPhoneOTPFragment;
import onbo.app.modules.dinein.DineInActivity;
import onbo.app.modules.payment.PaymentActivity;
import onbo.app.root.RootActivity;

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
        i.putExtra("PAYMENT_PENDING", true);
        i.putExtra("F_ORDER", fOrder);
        i.putExtra("ORDER_ID", fOrder.getOrder_id());
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
