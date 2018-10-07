package tdevm.app_ui.modules.nondine;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.MenuItem;

import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.fragments.CartFragment;
import tdevm.app_ui.modules.dinein.fragments.MenuItemsFragment;

public class NonDineActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    FragmentTransaction fragmentTransaction;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_dine_in_menu_t2:
                    MenuItemsFragment menuItemsFragment = new MenuItemsFragment();
                    fragmentTransaction.replace(R.id.frame_layout_non_dine_in, menuItemsFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_cart_t2:
                    fragmentTransaction.replace(R.id.frame_layout_non_dine_in, new CartFragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_dine);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        MenuItemsFragment menuItemsFragment = new MenuItemsFragment();
        transaction.replace(R.id.frame_layout_non_dine_in, menuItemsFragment);
        transaction.commit();

        BottomNavigationView navigation = findViewById(R.id.navigation_non_dine_in);
        //BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    public void showCartEmptyFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CartFragment cartFragment = new CartFragment();
        transaction.replace(R.id.frame_layout_non_dine_in, cartFragment);
        transaction.commit();
    }


}
