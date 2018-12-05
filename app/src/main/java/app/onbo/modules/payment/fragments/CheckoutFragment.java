package app.onbo.modules.payment.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.shimmer.ShimmerFrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.merged.MergedOrder;
import app.onbo.modules.dinein.adapters.MergedOrderAdapter;
import app.onbo.modules.payment.PaymentActivity;
import app.onbo.modules.payment.PaymentViewContract;
import app.onbo.utils.GeneralUtils;


public class CheckoutFragment extends Fragment implements PaymentViewContract.CheckoutFragmentView {

    public static final String TAG = CheckoutFragment.class.getSimpleName();


    Unbinder unbinder;
    @BindView(R.id.rv_checkout_t1)
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


    @BindView(R.id.progress_bar_close_order_pb)
    ProgressBar closeProgressBar;


    @BindView(R.id.shimmer_fragment_checkout_t1)
    ShimmerFrameLayout shimmerFrameLayout;


    @BindView(R.id.btn_checkout_t1_final)
    Button btnCloseOrderT1;

    @OnClick(R.id.btn_checkout_t1_final)
    void checkoutBtn() {
        closeRunningOrder();
    }


    String orderId;

    @BindView(R.id.toolbar_fragment_checkout_t1)
    Toolbar toolbar;

    PaymentActivity paymentActivity;


    MergedOrderAdapter mergedOrderAdapter;


    MergedOrder fetchedOrder;


    @Inject
    CheckoutPresenter checkoutPresenter;

    public CheckoutFragment() {
        // Required empty public constructor
    }


    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkoutPresenter.attachView(this);
        if (getArguments() != null) {
            orderId = getArguments().getString("ORDER_ID");
            Log.d(TAG, "Got into checkout" + orderId);
            checkoutPresenter.fetchMergedOrder(orderId);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        paymentActivity = (PaymentActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setTitle("Order Summary");
            toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
        }

        if (getArguments() != null) {
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
            recyclerViewTempOrder.setLayoutManager(mLayoutManager);
            recyclerViewTempOrder.setNestedScrollingEnabled(false);


            mergedOrderAdapter = new MergedOrderAdapter(getContext());
            recyclerViewTempOrder.setAdapter(mergedOrderAdapter);
        }
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        checkoutPresenter.detachView();
    }

    private void closeRunningOrder() {
        new AlertDialog.Builder(paymentActivity)
                .setTitle("Close Order")
                .setMessage("Close order and proceed to pay?")
                .setPositiveButton("OK", (dialog, which) -> {
                    if (orderId != null) {
                        checkoutPresenter.closeRunningOrder(orderId);
                    }
                    Log.d(TAG, "Sending close order");
                })
                .setNegativeButton("Cancel", (dialog, which) -> Log.d(TAG, "Aborting close order..."))
                .show();
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
    public void onOrderClosed(FOrder fOrder) {
        Toast.makeText(paymentActivity, "Order closed!", Toast.LENGTH_SHORT).show();
        Log.d(TAG, fOrder.getOrder_id());
        Log.d(TAG, fOrder.getT_order_id());
        paymentActivity.showMakePayment(fOrder);
    }

    @Override
    public void showCloseProgressUI() {
        closeProgressBar.setVisibility(View.VISIBLE);
        btnCloseOrderT1.setVisibility(View.GONE);

    }

    @Override
    public void hideCloseProgressUI() {
        btnCloseOrderT1.setVisibility(View.VISIBLE);
        closeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onOrderClosedFailure() {

    }

    @Override
    public void onMergedOrderFetched(MergedOrder mergedOrder) {
        mergedOrderAdapter.onMergedOrderFetched(mergedOrder);
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
}
