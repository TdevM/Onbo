package tdevm.app_ui.modules.dinein;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.fragments.RunningOrderEmptyFragment;
import tdevm.app_ui.modules.dinein.fragments.SingleCuisineGridFragment;
import tdevm.app_ui.modules.payment.PaymentActivity;
import tdevm.app_ui.modules.dinein.fragments.CartFragment;
import tdevm.app_ui.modules.dinein.fragments.DishMenuFragment;
import tdevm.app_ui.modules.dinein.fragments.BellFragment;
import tdevm.app_ui.modules.dinein.fragments.MergedOrderFragment;
import tdevm.app_ui.root.BottomNavigationViewBehavior;
import tdevm.app_ui.root.BottomNavigationViewHelper;

public class DineInActivity extends AppCompatActivity implements DineInViewContract.DineInActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Inject
    DineInActivityPresenter presenter;

    FragmentTransaction fragmentTransaction;
    Toolbar toolbarDineIn;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_dine_in_menu:
                    SingleCuisineGridFragment cuisineGridFragment = new SingleCuisineGridFragment();
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        SingleCuisineGridFragment dishMenuFragment = new SingleCuisineGridFragment();
        transaction.replace(R.id.frame_layout_dine_in, dishMenuFragment);
        transaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar_dine_in_activity);
        toolbar.setTitle(R.string.rest_name);
        toolbar.inflateMenu(R.menu.menu_dine_in_main);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Dine in");
        }

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dine_in_main,menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void showCartEmptyFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CartFragment cartFragment = new CartFragment();
        transaction.replace(R.id.frame_layout_dine_in, cartFragment);
        transaction.commit();
    }

    public void showEmptyRunningOrderFragment(){
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

    public void startPaymentActivity(String orderId){
        Intent intent = new Intent(DineInActivity.this, PaymentActivity.class);
        intent.putExtra("ORDER_ID",orderId);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
