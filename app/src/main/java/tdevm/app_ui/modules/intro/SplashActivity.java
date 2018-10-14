package tdevm.app_ui.modules.intro;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.api.models.response.v2.RestaurantTable;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.modules.auth.AuthenticationActivity;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.root.RootActivity;
import tdevm.app_ui.utils.PreferenceUtils;

public class SplashActivity extends AppCompatActivity implements IntroViewContract.SplashView {

    @Inject
    PreferenceUtils preferenceUtils;

    @Inject
    SplashPresenter presenter;

    private static int SPLASH_TIME_OUT = 3000;
    private static final String MODE_DINE_IN = "MODE_DINE_IN";
    private static final String MODE_NON_DINE = "MODE_NON_DINE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        resolveDaggerDependencies();
        if (preferenceUtils.getIntroScreenDisplayed()) {

            if (preferenceUtils.getAuthLoginState()) {
                presenter.checkCurrentOrderDetails();
            } else {
                Intent i = new Intent(SplashActivity.this, AuthenticationActivity.class);
                startActivity(i);
            }

        } else {
            Intent i = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(i);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }


    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onDineOrderRunning(TOrder tOrder) {
        Restaurant restaurant = tOrder.getRestaurant();
        RestaurantTable table = tOrder.getRestaurantTable();
        String tableShortId = restaurant.getRestaurant_uuid() + "_" + table.getTable_number();

        preferenceUtils.saveDineQRTransaction(restaurant.getRestaurant_id(), restaurant.getRestaurant_uuid(), table.getRestaurant_table_id(), tableShortId, MODE_DINE_IN);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, DineInActivity.class);
                i.putExtra("RESTAURANT",restaurant);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    @Override
    public void onNoDineOrderRunning() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, RootActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onOrderFetchFailure() {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }
}
