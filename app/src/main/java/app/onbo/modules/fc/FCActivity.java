package app.onbo.modules.fc;

import android.content.Intent;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import javax.inject.Inject;

import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FcRestaurant;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.modules.fc.activities.FCPremiseDetailActivity;
import app.onbo.modules.fc.adapters.FCRestaurantListAdapter;
import app.onbo.root.activities.RestaurantDetailActivity;
import app.onbo.root.adapters.EqualSpacingItemDecoration;
import app.onbo.root.callbacks.RestaurantItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FCActivity extends AppCompatActivity implements FCViewContract.FCActivityView, RestaurantItemClickListener {


    @BindView(R.id.rv_fc_list)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar_restaurant_name)
    Toolbar toolbar;
    @BindView(R.id.image_view_restaurant_detail)
    ImageView imageViewRestaurant;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    String fcUUID;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.tv_restaurant_name)
    TextView restaurantName;

    @BindView(R.id.tv_restaurant_locality)
    TextView restaurantAddress;

    private RecyclerView.LayoutManager manager;


    @BindView(R.id.shimmer_fragment_fc_restaurant)
    ShimmerFrameLayout shimmerFrameLayout;

    @Inject
    FCPresenter fcPresenter;

    FoodCourt foodCourt;

    FCRestaurantListAdapter adapter;

    @Override
    protected void onResume() {
        fcPresenter.attachView(this);
        if (foodCourt != null) {
            fcPresenter.fetchFCRestaurants(foodCourt.getFcUuid());
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fc);
        ButterKnife.bind(this);
        resolveDaggerDependencies();
        foodCourt = getIntent().getParcelableExtra("FOOD_COURT");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });
        manager = new LinearLayoutManager(this);
        adapter = new FCRestaurantListAdapter(this);
        adapter.setRestaurantItemClickedListener(this);

        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(dpToPx(12)));
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //collapsingToolbarLayout.setTitle(restaurant.getRestaurant_name());
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" ");//carefull there should a space between double quote otherwise it wont work
                    isShow = false;

                }
            }
        });
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        if (foodCourt != null) {
            Glide.with(this).load(foodCourt.getImageUrl()).into(imageViewRestaurant);
            restaurantName.setText(foodCourt.getFcName());
            restaurantAddress.setText(foodCourt.getAddressComplete());
        }

    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getApplication()).getApiComponent().inject(this);

    }

    @Override
    public void onFCFetched(List<FcRestaurant> fcRestaurantList) {
        adapter.onRestaurantsFetched(fcRestaurantList);
    }

    @Override
    public void onFCFetchFailure() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fcPresenter.detachView();
    }

    @Override
    public void onRestaurantItemClicked(Restaurant restaurant) {
        Intent intent = new Intent(this, FCPremiseDetailActivity.class);
        intent.putExtra("RESTAURANT", restaurant);
        startActivity(intent);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
