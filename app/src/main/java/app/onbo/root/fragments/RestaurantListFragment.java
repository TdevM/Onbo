package app.onbo.root.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import javax.inject.Inject;

import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.root.RootActivity;
import app.onbo.root.RootActivityViewContract;
import app.onbo.root.adapters.EqualSpacingItemDecoration;
import app.onbo.root.adapters.RestaurantListAdapter;
import app.onbo.root.callbacks.RestaurantItemClickListener;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment
        implements RootActivityViewContract.RestaurantsListView, RestaurantItemClickListener,
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



    @BindView(R.id.frame_layout_backend_error)
    FrameLayout backendError;


    @BindView(R.id.frame_layout_connection_broken)
    FrameLayout noInternet;

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

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        resolveDaggerDependencies();
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restuarant_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        presenter.fetchRestaurants("118");
        activity = (RootActivity) getActivity();

        layoutManager = new LinearLayoutManager(getContext());
        adapter = new RestaurantListAdapter(getContext());

        recyclerView.setLayoutManager(layoutManager);
        //DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);

        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(dpToPx(12)));
        //recyclerView.addItemDecoration(decoration);


        recyclerView.setAdapter(adapter);
        adapter.setRestaurantItemClickedListener(this);

        swipeRefreshLayout.setColorSchemeResources(R.color.primary_default_app);

        swipeRefreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onRestaurantsFetched(List<Restaurant> restaurantList) {
        adapter.onRestaurantsFetched(restaurantList);
        hideBackendError();
        hideNoInternetError();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRestaurantsFetchFailure() {
        Toast.makeText(getContext(), "Failed to fetch restaurants", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNoInternetError() {
        swipeRefreshLayout.setRefreshing(false);
        noInternet.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideNoInternetError() {
        noInternet.setVisibility(View.GONE);
    }

    @Override
    public void hideBackendError() {
        backendError.setVisibility(View.GONE);

    }

    @Override
    public void showBackendError() {
        swipeRefreshLayout.setRefreshing(false);
        backendError.setVisibility(View.VISIBLE);

    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void showProgressUI() {
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);
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
