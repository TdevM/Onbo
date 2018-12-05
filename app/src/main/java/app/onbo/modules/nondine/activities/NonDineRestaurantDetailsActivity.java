package app.onbo.modules.nondine.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.widget.TextView;

import com.appsee.Appsee;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.modules.nondine.NonDineActivity;
import app.onbo.modules.nondine.NonDineViewContract;

public class NonDineRestaurantDetailsActivity extends AppCompatActivity implements NonDineViewContract.NonDineRestaurantDetailsView {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Inject
    NonDineRestDetailPresenter presenter;

    private Restaurant restaurant;

    @OnClick(R.id.btn_start_order)
    void startActivity() {
        if (restaurant != null) {
            Intent intent = new Intent(NonDineRestaurantDetailsActivity.this, NonDineActivity.class);
            intent.putExtra("RESTAURANT", restaurant);
            startActivity(intent);
        }

    }

    @BindView(R.id.tv_rest_name)
    TextView textView;


    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_dine_restaurant_details);
        Appsee.start();
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        resolveDaggerDependencies();
        ButterKnife.bind(this);
        presenter.fetchRestaurantDetails();

    }


    @Override
    public void onRestaurantDetailsFetched(Restaurant res) {
        this.restaurant = res;
        textView.setText(restaurant.getRestaurant_name());
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getApplication()).getApiComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
