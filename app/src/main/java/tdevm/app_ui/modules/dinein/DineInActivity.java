package tdevm.app_ui.modules.dinein;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.modules.dinein.fragments.RunningOrderEmptyFragment;
import tdevm.app_ui.modules.dinein.fragments.MenuItemsFragment;
import tdevm.app_ui.modules.payment.PaymentActivity;
import tdevm.app_ui.modules.dinein.fragments.CartFragment;
import tdevm.app_ui.modules.dinein.fragments.BellFragment;
import tdevm.app_ui.modules.dinein.fragments.MergedOrderFragment;

public class DineInActivity extends AppCompatActivity implements DineInViewContract.DineInActivity {

    public static final String TAG = DineInActivity.class.getSimpleName();

    @Inject
    DineInActivityPresenter presenter;

    FragmentTransaction fragmentTransaction;

    @BindView(R.id.toolbar_dine_in_activity)
    Toolbar toolbar;

    Restaurant restaurant;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_dine_in_menu:
                    MenuItemsFragment cuisineGridFragment = new MenuItemsFragment();
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, cuisineGridFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_bell_request:
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new BellFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_cart:
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new CartFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_running_order:
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new MergedOrderFragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_in_home);
        resolveDaggerDependencies();
        ButterKnife.bind(this);
        showMenuFragment();

        restaurant = getIntent().getParcelableExtra("RESTAURANT");

        if (restaurant != null) {
            toolbar.setTitle(restaurant.getRestaurant_name());
            toolbar.inflateMenu(R.menu.menu_dine_in_main);
            setSupportActionBar(toolbar);
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    private void showMenuFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuItemsFragment dishMenuFragment = new MenuItemsFragment();
        transaction.replace(R.id.frame_layout_dine_in, dishMenuFragment);
        transaction.commit();
    }

    @Override
    public void showProgressUI() {

    }


    private void closeRunningOrder() {
        new AlertDialog.Builder(this)
                .setTitle("Exit " + restaurant.getRestaurant_name())
                .setMessage("Are you sure you want to leave?")
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Log.d(TAG,"Exit cancelled");
                dialog.cancel();
            }
        }).show();
    }

    @Override
    public void onBackPressed() {
        closeRunningOrder();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dine_in_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void showCartEmptyFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CartFragment cartFragment = new CartFragment();
        transaction.replace(R.id.frame_layout_dine_in, cartFragment);
        transaction.commit();
    }

    public void showEmptyRunningOrderFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        RunningOrderEmptyFragment emptyFragment = new RunningOrderEmptyFragment();
        transaction.replace(R.id.frame_layout_dine_in, emptyFragment);
        transaction.commit();
    }

    @Override
    public void hideProgressUI() {

    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }

    public void startPaymentActivity(String orderId) {
        Intent intent = new Intent(DineInActivity.this, PaymentActivity.class);
        intent.putExtra("ORDER_ID", orderId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
