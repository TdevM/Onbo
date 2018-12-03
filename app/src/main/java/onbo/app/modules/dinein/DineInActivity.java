package onbo.app.modules.dinein;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.modules.dinein.fragments.MenuItemsFragment;
import onbo.app.modules.orders.callback.CartBadgeListener;
import onbo.app.modules.payment.PaymentActivity;
import onbo.app.modules.dinein.fragments.CartFragment;
import onbo.app.modules.dinein.fragments.BellFragment;
import onbo.app.modules.dinein.fragments.MergedOrderFragment;

public class DineInActivity extends AppCompatActivity implements
        DineInViewContract.DineInActivity, CartBadgeListener, BottomNavigationBar.OnTabSelectedListener {

    public static final String TAG = DineInActivity.class.getSimpleName();

    @Inject
    DineInActivityPresenter presenter;

    FragmentTransaction fragmentTransaction;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @BindView(R.id.toolbar_dine_in_activity)
    Toolbar toolbar;

    @BindView(R.id.navigation)
    BottomNavigationBar bottomNavigationBar;

    TextBadgeItem textBadgeItem;

    Restaurant restaurant;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            switch (item.getItemId()) {
//                case R.id.navigation_dine_in_menu:
//                    MenuItemsFragment cuisineGridFragment = new MenuItemsFragment();
//                    fragmentTransaction.replace(R.id.frame_layout_dine_in, cuisineGridFragment);
//                    fragmentTransaction.commit();
//                    return true;
//                case R.id.navigation_bell_request:
//                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new BellFragment());
//                    fragmentTransaction.commit();
//                    return true;
//                case R.id.navigation_cart:
//                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new CartFragment());
//                    fragmentTransaction.commit();
//                    return true;
//                case R.id.navigation_running_order:
//                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new MergedOrderFragment());
//                    fragmentTransaction.commit();
//                    return true;
//            }
//            return false;
//        }
//
//    };

    @Override
    protected void onResume() {
        presenter.attachView(this);
        onCartItemUpdated(0);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_in_home);
        resolveDaggerDependencies();
        ButterKnife.bind(this);
        String running_order = "RUNNING_ORDER_FRAGMENT";
        String toOpen = getIntent().getStringExtra("FRAGMENT_TO_OPEN");


        restaurant = getIntent().getParcelableExtra("RESTAURANT");

        if (restaurant != null) {
            toolbar.setTitle(restaurant.getRestaurant_name());
            toolbar.inflateMenu(R.menu.menu_dine_in_main);
            setSupportActionBar(toolbar);
        }

        textBadgeItem = new TextBadgeItem();
        textBadgeItem.setBackgroundColorResource(R.color.primary_dark_app);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_local_dining_black_24dp, "Menu"));
        bottomNavigationBar.addItem(
                new BottomNavigationItem(R.drawable.ic_add_shopping_cart_black_24dp, "Cart")
                        .setBadgeItem(textBadgeItem).setActiveColorResource(R.color.primary_default_app));
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_receipt_black_24dp, "Running"))
                //bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_add_alert_black_24dp, "Bell"))
                .setFirstSelectedPosition(0)
                .initialise();

        bottomNavigationBar.setTabSelectedListener(this);
        if (toOpen != null) {
            if (toOpen.equals(running_order)) {
                showRunningOrder();

                bottomNavigationBar.selectTab(2);
            }
        } else {
            showMenuFragment();
        }

    }

    private void showMenuFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuItemsFragment dishMenuFragment = new MenuItemsFragment();
        transaction.replace(R.id.frame_layout_dine_in, dishMenuFragment);
        transaction.commit();
    }

    private void showRunningOrder() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MergedOrderFragment mergedOrderFragment = new MergedOrderFragment();
        transaction.replace(R.id.frame_layout_dine_in, mergedOrderFragment);
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
                Log.d(TAG, "Exit cancelled");
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
        // getMenuInflater().inflate(R.menu.menu_dine_in_main, menu);
        return false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
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
        intent.putExtra("PAYMENT_PENDING", false);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onCartItemUpdated(int count) {
        if (presenter.getCartItemsCount() == 0) {
            textBadgeItem.hide(false);
        } else {
            textBadgeItem.show();
            textBadgeItem.setText(String.valueOf(presenter.getCartItemsCount()));
        }
    }

    @Override
    public void onTabSelected(int position) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                MenuItemsFragment cuisineGridFragment = new MenuItemsFragment();
                fragmentTransaction.replace(R.id.frame_layout_dine_in, cuisineGridFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                CartFragment fragment = new CartFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("RESTAURANT", restaurant);
                fragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.frame_layout_dine_in, fragment);
                fragmentTransaction.commit();
                break;
            case 2:
                fragmentTransaction.replace(R.id.frame_layout_dine_in, new MergedOrderFragment());
                fragmentTransaction.commit();
                break;
            case 3:
                fragmentTransaction.replace(R.id.frame_layout_dine_in, new BellFragment());
                fragmentTransaction.commit();
                break;
        }
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }
}
