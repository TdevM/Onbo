package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import tdevm.app_ui.modules.dinein.adapters.RunningOrderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MergedOrderFragment extends Fragment implements DineInViewContract.MergedOrderView {

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

    @BindView(R.id.shimmer_fragment_running_order)
    ShimmerFrameLayout shimmerFrameLayout;

    @BindView(R.id.scroll_view_fragment_running_order)
    ScrollView scrollView;

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
        mergedOrderAdapter = new MergedOrderAdapter(getContext());
        recyclerViewTempOrder.setAdapter(mergedOrderAdapter);
        presenter.fetchTempRunningOrder();
        return view;
    }

    @Override
    public void showProgressUI() {
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
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
        fetchedOrder = mergedOrder;
        Log.d(TAG, "Order ID" + String.valueOf(mergedOrder.getOrderId()));
        Log.d(TAG, "Table no" + String.valueOf(mergedOrder.getTableId()));
        tableNo.setText("Table no: " + String.valueOf(mergedOrder.getRestaurantTable().getTable_number()));
        tempOrderId.setText("Order ID: " + mergedOrder.getOrderId());
        tvDate.setText(mergedOrder.getTimestamp());
        subTotal.setText(String.valueOf(Integer.parseInt(mergedOrder.getOrderTotal().getSubtotal()) * 0.01));
        taxes.setText(String.valueOf(Integer.parseInt(mergedOrder.getOrderTotal().getTaxes()) * 0.01));
        total.setText(String.valueOf(Integer.parseInt(mergedOrder.getOrderTotal().getGrand_total()) * 0.01));
    }

    @Override
    public void showNoRunningOrder() {
        dineInActivity.showEmptyRunningOrderFragment();
    }

    public void startPaymentActivity() {
        if(fetchedOrder!=null){
            dineInActivity.startPaymentActivity(fetchedOrder.getOrderId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
    }
}
