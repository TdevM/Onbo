package app.onbo.modules.fc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import javax.inject.Inject;

import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.modules.entry.RestaurantMenuEntryActivity;
import app.onbo.modules.fc.adapters.FCListAdapter;
import app.onbo.root.adapters.EqualSpacingItemDecoration;
import app.onbo.root.adapters.MenuAdapterRestaurantDetail;
import app.onbo.root.callbacks.FoodCourtClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;

public class FCListActivity extends AppCompatActivity implements FCViewContract.FCListActivity, FoodCourtClickListener {

    public static final String TAG = FCListActivity.class.getSimpleName();

    @Inject
    FCListPresenter presenter;

    @BindView(R.id.toolbar_fc_list)
    Toolbar toolbarMain;

    @BindView(R.id.rv_fc_list_pickup)
    RecyclerView recyclerViewFCList;

    @BindView(R.id.shimmer_fragment_fc_restaurant_list)
    ShimmerFrameLayout shimmerFrameLayout;

    private FCListAdapter adapter;
    private RecyclerView.LayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_fclist);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarMain);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Pickup Restaurants");
        }

        adapter = new FCListAdapter(this);
        adapter.setFoodCourtClickListener(this);

        manager = new LinearLayoutManager(this);
        recyclerViewFCList.setLayoutManager(manager);
        recyclerViewFCList.addItemDecoration(new EqualSpacingItemDecoration(dpToPx(12)));
        recyclerViewFCList.setAdapter(adapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.fetchFCList();
    }



    @Override
    public void showProgressUI() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.setVisibility(View.GONE);
        shimmerFrameLayout.stopShimmer();
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

    @Override
    public void onFCListFetched(List<FoodCourt> foodCourts) {
        adapter.onFCListFetched(foodCourts);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onFoodCourtItemClicked(FoodCourt foodCourt) {
        Intent intent = new Intent(FCListActivity.this, FCActivity.class);
        intent.putExtra("FOOD_COURT", foodCourt);
        startActivity(intent);
    }
}
