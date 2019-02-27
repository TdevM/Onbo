package app.onbo.modules.fc;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import javax.inject.Inject;

import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FoodCourt;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FCActivity extends AppCompatActivity implements FCViewContract.FCActivityView {


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

    @Override
    protected void onResume() {
        fcPresenter.attachView(this);
        if(foodCourt!=null){
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

    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getApplication()).getApiComponent().inject(this);

    }

    @Override
    public void onFCFetched(FoodCourt foodCourt) {

    }

    @Override
    public void onFCFetchFailure() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fcPresenter.detachView();
    }
}
