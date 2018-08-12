package tdevm.app_ui.modules.orders;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.orders.fragments.MyOrdersFragment;

public class RestaurantOrdersActivity extends AppCompatActivity implements RestaurantOrdersViewContract.RestaurantOrdersActivityView {


    @BindView(R.id.toolbar_orders_activity)
    Toolbar toolbar;


    @Inject
    RestaurantOrderActivityPresenter presenter;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_restaurant_orders);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.my_orders);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        showMyOrdersFragment();
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_restaurant_orders, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showMyOrdersFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        MyOrdersFragment fragment = new MyOrdersFragment();
        fragmentTransaction.replace(R.id.frame_layout_my_orders, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
