package onbo.app.modules.nondine;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import onbo.app.AppApplication;
import onbo.app.R;
import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.modules.dinein.fragments.CartFragment;
import onbo.app.modules.dinein.fragments.MenuItemsFragment;
import onbo.app.modules.orders.callback.CartBadgeListener;

public class NonDineActivity extends AppCompatActivity implements
        BottomNavigationBar.OnTabSelectedListener, CartBadgeListener,
        NonDineViewContract.NonDineActivityView {


    @BindView(R.id.toolbar_non_dine_activity)
    Toolbar toolbar;
    Restaurant restaurant;

    FragmentTransaction fragmentTransaction;

    @BindView(R.id.navigation_non_dine_in)
    BottomNavigationBar bottomNavigationBar;
    TextBadgeItem numberBadgeItem;

    @Inject
    NonDinePresenter presenter;


    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_dine);
        showMenuFragment();
        resolveDaggerDependencies();
        ButterKnife.bind(this);
        restaurant = getIntent().getParcelableExtra("RESTAURANT");

        if (restaurant != null) {
            toolbar.setTitle(restaurant.getRestaurant_name());
            toolbar.inflateMenu(R.menu.menu_dine_in_main);
            setSupportActionBar(toolbar);
        }

        numberBadgeItem = new TextBadgeItem();
        numberBadgeItem.setBackgroundColorResource(R.color.primary_dark_app);
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.ic_local_dining_black_24dp, "Menu"));
        bottomNavigationBar.addItem(
                new BottomNavigationItem(R.drawable.ic_add_shopping_cart_black_24dp, "Cart")
                        .setBadgeItem(numberBadgeItem).setActiveColorResource(R.color.primary_default_app))

                .setFirstSelectedPosition(0)
                .initialise();

        onCartItemUpdated(0);
        bottomNavigationBar.setTabSelectedListener(this);
    }

    private void showMenuFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuItemsFragment menuItemsFragment = new MenuItemsFragment();
        transaction.replace(R.id.frame_layout_non_dine_in, menuItemsFragment);
        transaction.commit();
    }


    public void showCartEmptyFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CartFragment cartFragment = new CartFragment();
        transaction.replace(R.id.frame_layout_non_dine_in, cartFragment);
        transaction.commit();
    }


    @Override
    public void onTabSelected(int position) {
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (position) {
            case 0:
                MenuItemsFragment menuItemsFragment = new MenuItemsFragment();
                fragmentTransaction.replace(R.id.frame_layout_non_dine_in, menuItemsFragment);
                fragmentTransaction.commit();
                break;
            case 1:
                fragmentTransaction.replace(R.id.frame_layout_non_dine_in, new CartFragment());
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

    @Override
    public void onCartItemUpdated(int count) {
        if(presenter.getCartItemsCount()==0){
            numberBadgeItem.hide(false);
        }else {
            numberBadgeItem.show();
            numberBadgeItem.setText(String.valueOf(presenter.getCartItemsCount()));
        }

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
