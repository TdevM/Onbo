package tdevm.app_ui.navigation_fragments_dine_in;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import tdevm.app_ui.R;
import tdevm.app_ui.navigation_fragments_home.HomeFragment;

public class DineInHome extends AppCompatActivity {

    FragmentTransaction fragmentTransaction;
    Toolbar toolbarDineIn;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_dine_in_menu:
                    fragmentTransaction.replace(R.id.frame_layout_dine_in, new DishMenuFragment());
                    fragmentTransaction.commit();

                 //   mTextMessage.setText(R.string.title_menu);
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
        toolbarDineIn = (Toolbar)findViewById(R.id.toolbar_dine_in_home);


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
        transaction.replace(R.id.frame_layout_dine_in, HighestRatedItemsFragment.newInstance());
        transaction.commit();


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_favourites);

    }

}
