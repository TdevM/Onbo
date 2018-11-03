package tdevm.app_ui.root.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.modules.entry.RestaurantMenuEntryActivity;
import tdevm.app_ui.root.NavigationHomeViewContract;
import tdevm.app_ui.root.adapters.MenuAdapterRestaurantDetail;

public class RestaurantDetailActivity extends AppCompatActivity implements NavigationHomeViewContract.RestaurantDetailView {


    public static final String TAG = RestaurantDetailActivity.class.getSimpleName();

    @BindView(R.id.toolbar_restaurant_name)
    Toolbar toolbar;
    @BindView(R.id.image_view_restaurant_detail)
    ImageView imageViewRestaurant;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;


    @BindView(R.id.recycler_view_restaurant_menu)
    RecyclerView recyclerView;

    @Inject
    RestaurantDetailPresenter presenter;


    @OnClick(R.id.btn_res_detail_start_order)
    void startScan(){
        Intent intent = new Intent(this, RestaurantMenuEntryActivity.class);
        startActivity(intent);
        finish();
    }

    private Restaurant restaurant;

    private MenuAdapterRestaurantDetail adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_restaurant_detail);
        ButterKnife.bind(this);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        restaurant = getIntent().getParcelableExtra("RESTAURANT");
        adapter = new MenuAdapterRestaurantDetail(this);
        manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        if (restaurant != null) {
            presenter.fetchMenuItems(restaurant);
            collapsingToolbarLayout.setTitle(restaurant.getRestaurant_name());

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

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);

    }
}
