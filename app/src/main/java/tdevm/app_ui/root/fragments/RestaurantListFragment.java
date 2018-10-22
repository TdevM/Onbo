package tdevm.app_ui.root.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.root.RootActivity;
import tdevm.app_ui.root.NavigationHomeViewContract;
import tdevm.app_ui.root.adapters.EqualSpacingItemDecoration;
import tdevm.app_ui.root.adapters.ItemOffsetDecoration;
import tdevm.app_ui.root.adapters.RestaurantListAdapter;
import tdevm.app_ui.root.callbacks.RestaurantItemClickListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment
        implements NavigationHomeViewContract.RestaurantsListView, RestaurantItemClickListener,
        SwipeRefreshLayout.OnRefreshListener {


    public static final String TAG = RestaurantListFragment.class.getSimpleName();
    Unbinder unbinder;


    @BindView(R.id.rv_restaurant_list)
    RecyclerView recyclerView;

    @BindView(R.id.swipeToRefresh_restaurant_list)
    SwipeRefreshLayout swipeRefreshLayout;


    @BindView(R.id.shimmer_fragment_restaurant_list)
    ShimmerFrameLayout shimmerFrameLayout;

    RecyclerView.LayoutManager layoutManager;
    RootActivity activity;

    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Inject
    RestaurantListFragmentPresenter presenter;

    RestaurantListAdapter adapter;

    public static RestaurantListFragment newInstance() {
        return new RestaurantListFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        resolveDaggerDependencies();
        activity = (RootActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_restuarant_list, container, false);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RestaurantListAdapter(getContext());
        unbinder = ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(32));


        recyclerView.setAdapter(adapter);
        adapter.setRestaurantItemClickedListener(this);
        presenter.fetchRestaurants("118");
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_default_app);

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRestaurantsFetched(List<Restaurant> restaurantList) {
        adapter.onRestaurantsFetched(restaurantList);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRestaurantsFetchFailure() {
        Toast.makeText(getContext(), "Failed to fetch restaurants", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressUI() {
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }

    @Override
    public void onRestaurantItemClicked(Restaurant restaurant) {
        Log.d(TAG, "Clicked restaurant: " + restaurant.getRestaurant_name());
        activity.showRestaurantDetailsActivity(restaurant);
    }

    @Override
    public void onRefresh() {
        presenter.fetchRestaurants("118");
    }
}
