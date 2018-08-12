package tdevm.app_ui.root.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.R;

public class RestaurantDetailActivity extends AppCompatActivity {


    @BindView(R.id.toolbar_restaurant_details_activity)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}
