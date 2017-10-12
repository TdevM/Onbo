package tdevm.app_ui.modules.auth;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.dagger.components.ApplicationComponent;

import tdevm.app_ui.modules.auth.fragments.AuthInitFragment;
import tdevm.app_ui.modules.auth.fragments.AuthLoginFragment;
import tdevm.app_ui.modules.auth.fragments.AuthRegisterFragment;
import tdevm.app_ui.modules.auth.fragments.VerifyPhoneOTPFragment;

public class AuthenticationActivity extends AppCompatActivity implements AuthInitFragment.AuthInitInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.frame_layout_auth_activity);

        if(fragment == null){
            fragment = new AuthInitFragment();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout_auth_activity,fragment).commit();
        }
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AppApplication) getApplication()).getApplicationComponent();
    }

    public void showOTPVerificationFragment(Long phone){
        VerifyPhoneOTPFragment verifyPhoneOTPFragment = VerifyPhoneOTPFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE",phone);
        verifyPhoneOTPFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_auth_activity,verifyPhoneOTPFragment).addToBackStack(null).commit();

    }

    public void showLoginFragment(Long phone){
        AuthLoginFragment authLoginFragment = AuthLoginFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE",phone);
        authLoginFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_auth_activity,authLoginFragment).addToBackStack(null).commit();

    }

    public void showRegisterFragment(Long phone){
        AuthRegisterFragment authRegisterFragment = AuthRegisterFragment.newInstance();
        Bundle bundle = new Bundle();
        bundle.putLong("PHONE",phone);
        authRegisterFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_auth_activity,authRegisterFragment).commit();
    }

    @Override
    public void onAuthInitInteractionListener(Uri uri) {

    }


}
