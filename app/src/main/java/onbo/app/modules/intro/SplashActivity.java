package onbo.app.modules.intro;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.api.models.response.v2.RestaurantTable;
import onbo.app.api.models.response.v2.t_orders.TOrder;
import onbo.app.modules.auth.AuthenticationActivity;
import onbo.app.modules.dinein.DineInActivity;
import onbo.app.modules.payment.PaymentActivity;
import onbo.app.root.RootActivity;
import onbo.app.utils.PreferenceUtils;

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
              showAuthenticationSplash();
            }

        } else {
            Intent i = new Intent(SplashActivity.this, IntroActivity.class);
            startActivity(i);
            finish();
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

        if (tOrder.getClosed()) {
            presenter.fetchClosedOrder(tOrder.getOrderId());
        } else if (!tOrder.getClosed()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, DineInActivity.class);
                    i.putExtra("RESTAURANT", restaurant);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }


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

    public void showAuthenticationSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this, AuthenticationActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onOrderFetchFailure() {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFOrderFetched(FOrder fOrder) {
        Intent i = new Intent(SplashActivity.this, PaymentActivity.class);
        i.putExtra("PAYMENT_PENDING", true);
        i.putExtra("F_ORDER", fOrder);
        i.putExtra("ORDER_ID", fOrder.getOrder_id());
        startActivity(i);
        finish();

    }

    @Override
    public void onFOrderFetchFailure() {
        Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show();
    }
}
