package tdevm.app_ui.root;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.modules.auth.AuthenticationActivity;
import tdevm.app_ui.modules.entry.RestaurantMenuEntryActivity;
import tdevm.app_ui.root.activities.RestaurantDetailActivity;
import tdevm.app_ui.root.fragments.AccountsFragment;
import tdevm.app_ui.root.fragments.RestaurantListFragment;
import tdevm.app_ui.utils.BadgeDrawable;

public class RootActivity extends AppCompatActivity implements NavigationHomeViewContract.RootActivityView {

    public static final String TAG = RootActivity.class.getSimpleName();
    @Inject
    RootActivityPresenter rootActivityPresenter;
    Toolbar toolbarMain;
    BottomNavigationView navigation;
    FragmentTransaction fragmentTransaction;
    LayerDrawable icon;
    int unpaidOrdersCount = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragmentTransaction.replace(R.id.frame_layout, new RestaurantListFragment());
                    fragmentTransaction.commit();
                    break;
                case R.id.navigation_scanner:
                    rootActivityPresenter.verifyUserAuthentication();
                    break;
                case R.id.navigation_account:
                    rootActivityPresenter.handleUserAuthentication();
                    break;
            }
            return true;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_bottom_navigation);
        toolbarMain = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbarMain);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        }
        toolbarMain.setNavigationOnClickListener(view -> onBackPressed());
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, RestaurantListFragment.newInstance());
        transaction.commit();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dine_in_main, menu);
        MenuItem itemCart = menu.findItem(R.id.navigation_menu_root_notifications);
        icon = (LayerDrawable) itemCart.getIcon();
        return true;
    }

    @Override
    public void showRestaurantDetailsActivity(Restaurant restaurant) {
        Intent intent = new Intent(this, RestaurantDetailActivity.class);
        intent.putExtra("RESTAURANT", restaurant);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        rootActivityPresenter.attachView(this);
        rootActivityPresenter.fetchUnpaidOrders();
        super.onResume();
    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public void showUserProfile() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new AccountsFragment());
        transaction.commit();
    }

    @Override
    public void redirectEntryActivity() {
        Intent intent = new Intent(RootActivity.this, RestaurantMenuEntryActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUnpaidOrdersFetched(List<FOrder> unpaidOrders) {
        setBadgeCount(this, icon, String.valueOf(unpaidOrders.size()));
    }

    @Override
    public void noUnpaidOrders() {

    }

    public static void setBadgeCount(Context context, LayerDrawable icon, String count) {

        BadgeDrawable badge;

        // Reuse drawable if possible
        Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
        if (reuse != null && reuse instanceof BadgeDrawable) {
            badge = (BadgeDrawable) reuse;
        } else {
            badge = new BadgeDrawable(context);
        }

        badge.setCount(count);
        icon.mutate();
        icon.setDrawableByLayerId(R.id.ic_badge, badge);
    }


    @Override
    public void redirectAuthActivity() {
        Intent intent = new Intent(RootActivity.this, AuthenticationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        rootActivityPresenter.detachView();
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }


    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }
}
