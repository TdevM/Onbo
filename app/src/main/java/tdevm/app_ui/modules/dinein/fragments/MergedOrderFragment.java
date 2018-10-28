package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.modules.dinein.DineInActivity;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.MergedOrderAdapter;
import tdevm.app_ui.utils.GeneralUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class MergedOrderFragment extends Fragment implements DineInViewContract.MergedOrderView,
        SwipeRefreshLayout.OnRefreshListener {

    public static final String TAG = MergedOrderFragment.class.getCanonicalName();


    Unbinder unbinder;
    @BindView(R.id.rv_merged_order)
    RecyclerView recyclerViewTempOrder;
    @BindView(R.id.tv_merged_order_id)
    TextView tempOrderId;
    @BindView(R.id.tv_merged_order_table_no)
    TextView tableNo;
    @BindView(R.id.tv_merged_subtotal)
    TextView subTotal;
    @BindView(R.id.tv_merged_taxes)
    TextView taxes;
    @BindView(R.id.tv_merged_total)
    TextView total;
    @BindView(R.id.tv_merged_order_date)
    TextView tvDate;

//    @BindView(R.id.shimmer_fragment_merged_order)
//    ShimmerFrameLayout shimmerFrameLayout;

//    @BindView(R.id.scroll_view_fragment_running_order)
//    ScrollView scrollView;


    @BindView(R.id.swipe_refresh_my_running_orders)
    SwipeRefreshLayout swipeRefreshLayout;


    MergedOrderAdapter mergedOrderAdapter;
    @Inject
    MergedOrderPresenter presenter;
    DineInActivity dineInActivity;

    MergedOrder fetchedOrder;

    @OnClick(R.id.btn_close_order)
    public void closeOrder() {
        startPaymentActivity();
    }


    public MergedOrderFragment() {
        // Required empty public constructor
    }

    public static MergedOrderFragment newInstance() {
        return new MergedOrderFragment();
    }

    @Override
    public void onResume() {
        presenter.attachView(this);
        presenter.fetchTempRunningOrder();
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        resolveDaggerDependencies();
        dineInActivity = (DineInActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_merged_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTempOrder.setLayoutManager(mLayoutManager);
        recyclerViewTempOrder.setNestedScrollingEnabled(false);


        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.primary_default_app);

        mergedOrderAdapter = new MergedOrderAdapter(getContext());
        recyclerViewTempOrder.setAdapter(mergedOrderAdapter);

        return view;
    }

    @Override
    public void showProgressUI() {
//        shimmerFrameLayout.setVisibility(View.VISIBLE);
//        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
//        shimmerFrameLayout.stopShimmer();
//        shimmerFrameLayout.setVisibility(View.GONE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);

    }


    @Override
    public void onRunningOrderFetched(TOrder tOrder) {
        presenter.fetchMergedOrder(tOrder);
    }

    @Override
    public void onMergedOrderFetched(MergedOrder mergedOrder) {
        mergedOrderAdapter.onMergedOrderFetched(mergedOrder);
        swipeRefreshLayout.setRefreshing(false);
        fetchedOrder = mergedOrder;
        Log.d(TAG, "Order ID" + String.valueOf(mergedOrder.getOrderId()));
        Log.d(TAG, "Table no" + String.valueOf(mergedOrder.getTableId()));
        tableNo.setText(getContext().getString(R.string.show_number_pound_symbol, String.valueOf(mergedOrder.getRestaurantTable().getTable_number())));
        tempOrderId.setText(getContext().getString(R.string.show_number_pound_symbol, mergedOrder.getOrderId()));

        tvDate.setText(GeneralUtils.parseTime(mergedOrder.getTimestamp()));
        subTotal.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getSubtotal())));
        taxes.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getTaxes())));
        total.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getGrand_total())));
    }

    @Override
    public void showNoRunningOrder() {
        dineInActivity.showEmptyRunningOrderFragment();
    }

    public void startPaymentActivity() {
        if (fetchedOrder != null) {
            dineInActivity.startPaymentActivity(fetchedOrder.getOrderId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        presenter.fetchTempRunningOrder();
    }
}
