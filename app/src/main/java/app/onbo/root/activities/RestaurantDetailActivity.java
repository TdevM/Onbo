package app.onbo.root.activities;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;
import java.util.ListIterator;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.menu.Cuisine;
import app.onbo.api.models.response.v2.menu.CuisineMenuItems;
import app.onbo.root.RootActivityViewContract;
import app.onbo.root.adapters.MenuAdapterRestaurantDetail;

public class RestaurantDetailActivity extends AppCompatActivity implements RootActivityViewContract.RestaurantDetailView {


    public static final String TAG = RestaurantDetailActivity.class.getSimpleName();

    @BindView(R.id.toolbar_restaurant_name)
    Toolbar toolbar;
    @BindView(R.id.image_view_restaurant_detail)
    ImageView imageViewRestaurant;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;


    @BindView(R.id.recycler_view_restaurant_menu)
    RecyclerView recyclerView;

    @BindView(R.id.shimmer_fragment_fc_restaurant)
    ShimmerFrameLayout shimmerFrameLayout;

    @Inject
    RestaurantDetailPresenter presenter;


    @BindView(R.id.tv_restaurant_name)
    TextView restaurantName;

    @BindView(R.id.tv_restaurant_locality)
    TextView restaurantAddress;


    @BindView(R.id.tv_restaurant_cuisine)
    TextView restaurantCuisines;

    @BindView(R.id.tv_restaurant_rating_start)
    TextView starRating;

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    @BindView(R.id.tv_cost_for_two)
    TextView costForTwo;

    @BindView(R.id.tv_self_ordering)
    TextView selfOrdering;

    @BindView(R.id.tv_qr_enabled)
    TextView qrEnabled;


    private Restaurant restaurant;

    private MenuAdapterRestaurantDetail adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onResume() {
        presenter.attachView(this);

        if (restaurant != null) {
            presenter.fetchMenuItems(restaurant);
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            onBackPressed();
        });

        restaurant = getIntent().getParcelableExtra("RESTAURANT");
        adapter = new MenuAdapterRestaurantDetail(this);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(adapter);
        if (restaurant != null) {
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
            //collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleWhite);
            //collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.collapsingToolbarLayoutTitleWhite);
            restaurantName.setText(restaurant.getRestaurant_name());
            restaurantAddress.setText(restaurant.getAddress_complete());
            costForTwo.setText(String.format("%s for two", String.valueOf(restaurant.getAvg_cost_for_two())));
            if (restaurant.getRating() != null) {
                Double d = Double.parseDouble(restaurant.getRating().getRestaurant_avg_rating());
                double roundOff = Math.round(d * 100.0) / 100.0;
                starRating.setText(String.valueOf(roundOff));
            }
            if (restaurant.getQr_active()) {
                qrEnabled.setText("QR Active");
            } else {
                qrEnabled.setText("QR Inactive");
            }
            if (restaurant.getCuisines() != null) {
                restaurantCuisines.setText(generateCuisineSlug(restaurant.getCuisines()));
            }
            if (restaurant.getRestaurant_mode() != null) {
                if (restaurant.getRestaurant_mode().equals("DINE_IN")) {
                    selfOrdering.setText(this.getString(R.string.dine_id));
                } else {
                    selfOrdering.setText(this.getString(R.string.quick_serve));
                }
            }

            Glide.with(this).load(restaurant.getImage()).into(imageViewRestaurant);
        }

    }

    @Override
    public void onMenuItemsFetchedV2(List<CuisineMenuItems> cuisineMenuItems) {
        adapter.onCuisineListFetched(cuisineMenuItems);
    }

    @Override
    public void onMenuItemsFetchFailure() {
        Toast.makeText(this, "Failed to fetch menu", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressUI() {
        shimmerFrameLayout.startShimmer();

    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getApplication()).getApiComponent().inject(this);

    }

    public String generateCuisineSlug(List<Cuisine> cuisines) {
        ListIterator<Cuisine> cuisineListIterator = cuisines.listIterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (cuisineListIterator.hasNext()) {
            Cuisine cuisine = cuisineListIterator.next();
            stringBuilder.append(cuisine.getCuisine_name());
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }
}
