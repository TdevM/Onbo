package app.onbo.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.merged.MergedOrder;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.modules.dinein.DineInActivity;
import app.onbo.modules.dinein.DineInViewContract;
import app.onbo.modules.dinein.adapters.MergedOrderAdapter;
import app.onbo.utils.GeneralUtils;

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

    @BindView(R.id.tv_merged_order_status)
    TextView tvOrderStatus;

    @BindView(R.id.shimmer_fragment_merged_order)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.frame_layout_merged_order_empty)
    FrameLayout mergedOrderEmpty;

    @BindView(R.id.btn_close_order)
    Button closeOrder;

    @BindView(R.id.btn_pay_order)
    Button payDineOrder;

    @OnClick(R.id.btn_pay_order)
    public void payOrder() {
        startPaymentActivityMakePayment();
    }


    @BindView(R.id.swipe_refresh_my_running_orders)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.frame_layout_backend_error)
    FrameLayout backendError;


    @BindView(R.id.frame_layout_connection_broken)
    FrameLayout noInternet;


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
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }


    @Override
    public void showNoInternetError() {
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
        backendError.setVisibility(View.VISIBLE);

    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);

    }


    @Override
    public void onRunningOrderFetched(TOrder tOrder) {
        presenter.fetchMergedOrder(tOrder);

    }

    @Override
    public void onMergedOrderFetched(MergedOrder mergedOrder) {
        if (!mergedOrder.getClosed()) {

            mergedOrderAdapter.onMergedOrderFetched(mergedOrder);
            mergedOrderEmpty.setVisibility(View.GONE);
            swipeRefreshLayout.setRefreshing(false);
            fetchedOrder = mergedOrder;
            Log.d(TAG, "Order ID" + String.valueOf(mergedOrder.getOrderId()));
            Log.d(TAG, "Table no" + String.valueOf(mergedOrder.getTableId()));
            tableNo.setText(getContext().getString(R.string.show_number_pound_symbol, String.valueOf(mergedOrder.getRestaurantTable().getTable_number())));
            tempOrderId.setText(getContext().getString(R.string.show_number_pound_symbol, mergedOrder.getOrderId()));
            closeOrder.setVisibility(View.VISIBLE);
            payDineOrder.setVisibility(View.GONE);
            tvOrderStatus.setText(getString(R.string.order_active));
            tvDate.setText(mergedOrder.getTimestamp());
            subTotal.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getSubtotal())));
            taxes.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getTaxes())));
            total.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getGrand_total())));
        } else {
            if (mergedOrder.getClosed()) {
                mergedOrderAdapter.onMergedOrderFetched(mergedOrder);
                mergedOrderEmpty.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
                fetchedOrder = mergedOrder;
                Log.d(TAG, "Order ID" + String.valueOf(mergedOrder.getOrderId()));
                Log.d(TAG, "Table no" + String.valueOf(mergedOrder.getTableId()));
                tableNo.setText(getContext().getString(R.string.show_number_pound_symbol, String.valueOf(mergedOrder.getRestaurantTable().getTable_number())));
                tempOrderId.setText(getContext().getString(R.string.show_number_pound_symbol, mergedOrder.getOrderId()));
                tvOrderStatus.setText(getString(R.string.payment_pending));
                tvDate.setText(mergedOrder.getTimestamp());
                closeOrder.setVisibility(View.GONE);
                payDineOrder.setVisibility(View.VISIBLE);
                subTotal.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getSubtotal())));
                taxes.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getTaxes())));
                total.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(mergedOrder.getOrderTotal().getGrand_total())));
            }

        }

    }


    @Override
    public void showNoRunningOrder() {
        mergedOrderEmpty.setVisibility(View.VISIBLE);
    }

    public void startPaymentActivity() {
        if (fetchedOrder != null) {
            dineInActivity.startPaymentActivity(fetchedOrder.getOrderId());
        }
    }

    public void startPaymentActivityMakePayment() {
        if (fetchedOrder != null) {
            dineInActivity.startPaymentActivityShowMakePayment(fetchedOrder.getOrderId());
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
