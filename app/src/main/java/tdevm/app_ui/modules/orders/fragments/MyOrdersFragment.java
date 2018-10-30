package tdevm.app_ui.modules.orders.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.orders.RestaurantOrdersActivity;
import tdevm.app_ui.modules.orders.RestaurantOrdersViewContract;
import tdevm.app_ui.modules.orders.adapters.MyOrdersAdapter;
import tdevm.app_ui.modules.orders.callback.MyOrdersClickListener;
import tdevm.app_ui.root.adapters.EqualSpacingItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment implements
        RestaurantOrdersViewContract.MyOrdersFragmentView,
        SwipeRefreshLayout.OnRefreshListener, MyOrdersClickListener {

    public static final String TAG = MyOrdersFragment.class.getSimpleName();
    Unbinder unbinder;

    @BindView(R.id.rv_my_orders)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    MyOrdersAdapter adapter;

    @BindView(R.id.swipe_refresh_layout_my_orders)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.shimmer_fragment_my_orders)
    ShimmerFrameLayout shimmerFrameLayout;


    @BindView(R.id.frame_layout_my_orders_empty)
    FrameLayout emptyOrder;

    @Inject
    MyOrdersFragmentPresenter presenter;

    RestaurantOrdersActivity activity;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    public static MyOrdersFragment newInstance() {
        return new MyOrdersFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.fetchMyOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        resolveDaggerDependencies();
        activity = (RestaurantOrdersActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new MyOrdersAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(32));
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_default_app);
        adapter.setOrderClickListener(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();

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
    public void onMyOrderFetched(List<FOrder> fOrders) {
        swipeRefreshLayout.setRefreshing(false);
        adapter.onMyOrdersFetched(fOrders);
    }

    @Override
    public void onMyOrdersEmpty() {
        emptyOrder.setVisibility(View.VISIBLE);

    }

    @Override
    public void onFetchingOrdersFailure() {
        Toast.makeText(getContext(), "Failed to fetch orders", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        presenter.fetchMyOrders();
    }

    @Override
    public void onOrderClicked(FOrder fOrder) {
        activity.showOrderDetails(fOrder);
    }
}
