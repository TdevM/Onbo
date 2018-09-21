package tdevm.app_ui.modules.intro;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.auth.AuthenticationActivity;
import tdevm.app_ui.utils.PreferenceUtils;

public class SplashActivity extends AppCompatActivity {

    @Inject
    PreferenceUtils preferenceUtils;

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        resolveDaggerDependencies();


        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                if(preferenceUtils.getIntroScreenDisplayed()){
                    Intent i = new Intent(SplashActivity.this, AuthenticationActivity.class);
                    startActivity(i);
                }else {
                    Intent i = new Intent(SplashActivity.this, IntroActivity.class);
                    startActivity(i);
                }


                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }

}
