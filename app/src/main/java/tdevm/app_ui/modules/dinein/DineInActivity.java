package tdevm.app_ui.modules.dinein;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.fragments.CartFragment;
import tdevm.app_ui.modules.dinein.fragments.DishMenuFragment;
import tdevm.app_ui.modules.dinein.fragments.HighestRatedItemsFragment;
import tdevm.app_ui.modules.dinein.fragments.RunningOrderFragment;
import tdevm.app_ui.root.BottomNavigationViewHelper;
import tdevm.app_ui.utils.AuthUtils;

public class DineInActivity extends AppCompatActivity {


    FragmentTransaction fragmentTransaction;
    //Toolbar toolbarDineIn;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_dine_in_menu:
                    DishMenuFragment dishMenuFragment = new DishMenuFragment();
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
                case R.id.navigation_running_order:
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new RunningOrderFragment());
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
        resolveDaggerDependencies();
        //toolbarDineIn = findViewById(R.id.toolbar_dine_in_home);
        //setSupportActionBar(toolbarDineIn);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("Dine in");
        }
//        toolbarDineIn.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        DishMenuFragment dishMenuFragment = new DishMenuFragment();
        transaction.replace(R.id.frame_layout_dine_in, dishMenuFragment);
        transaction.commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }

}
