package app.onbo.modules.account.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.appsee.Appsee;

import butterknife.BindView;
import butterknife.ButterKnife;
import app.onbo.R;

public class FavouritesActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_favourites)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        Appsee.start();
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
}
