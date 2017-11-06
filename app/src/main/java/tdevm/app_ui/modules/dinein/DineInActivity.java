package tdevm.app_ui.modules.dinein;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.fragments.CartFragment;
import tdevm.app_ui.modules.dinein.fragments.DishMenuFragment;
import tdevm.app_ui.modules.dinein.fragments.HighestRatedItemsFragment;

public class DineInActivity extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    Toolbar toolbarDineIn;
    public String RESTAURANT_UUID;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_dine_in_menu:
                    DishMenuFragment dishMenuFragment = new DishMenuFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("RESTAURANT_UUID", RESTAURANT_UUID);
                    dishMenuFragment.setArguments(bundle);
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, dishMenuFragment);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_favourites:
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new HighestRatedItemsFragment());
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_cart:
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new CartFragment());
                    fragmentTransaction.commit();
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dine_in_home);
        toolbarDineIn = findViewById(R.id.toolbar_dine_in_home);
        RESTAURANT_UUID = getIntent().getStringExtra("RESTAURANT_UUID");
        setSupportActionBar(toolbarDineIn);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Dine in");
        }
        toolbarDineIn.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DishMenuFragment dishMenuFragment = new DishMenuFragment();
        Bundle bundle = new Bundle();
        bundle.putString("RESTAURANT_UUID", RESTAURANT_UUID);
        dishMenuFragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_dine_in, dishMenuFragment);
        transaction.commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
}
