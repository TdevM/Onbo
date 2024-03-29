package app.onbo.modules.nondine.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.Checkout;
import app.onbo.modules.nondine.NonDineViewContract;
import app.onbo.modules.nondine.activities.InitNonDineOrderActivity;
import app.onbo.modules.nondine.adapters.NonDineCheckoutAdapter;
import app.onbo.utils.GeneralUtils;


public class NonDineCheckoutFragment extends Fragment implements NonDineViewContract.NonDineCheckoutView {

    public static final String TAG = NonDineCheckoutFragment.class.getSimpleName();

    @Inject
    NonDineCheckoutPresenter presenter;
    Unbinder unbinder;

    @OnClick(R.id.btn_proceed_payment_type)
    void showPaymentTypeFragment() {
        activity.showGetNDOrderMessage();
    }

    InitNonDineOrderActivity activity;

    @BindView(R.id.rv_nd_checkout)
    RecyclerView recyclerViewCheckout;


    @BindView(R.id.tv_merged_total)
    TextView orderTotal;
    @BindView(R.id.tv_merged_taxes)
    TextView taxes;
    @BindView(R.id.tv_merged_subtotal)
    TextView subtotal;

    @BindView(R.id.toolbar_fragment_checkout_t2)
    Toolbar toolbar;

    @BindView(R.id.shimmer_fragment_checkout_t2)
    ShimmerFrameLayout shimmerFrameLayout;

    NonDineCheckoutAdapter adapter;

    public NonDineCheckoutFragment() {
        // Required empty public constructor
    }


    public static NonDineCheckoutFragment newInstance() {
        NonDineCheckoutFragment fragment = new NonDineCheckoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
        presenter.checkoutOrderSummary();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        activity = (InitNonDineOrderActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_non_dine_order_summary, container, false);
        unbinder = ButterKnife.bind(this, view);
        if (activity != null) {
            activity.setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
            toolbar.setTitle("Checkout");
            toolbar.setNavigationOnClickListener(v -> activity.onBackPressed());
        }
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCheckout.setLayoutManager(mLayoutManager);
        recyclerViewCheckout.setNestedScrollingEnabled(false);
        adapter = new NonDineCheckoutAdapter(getContext());
        recyclerViewCheckout.setAdapter(adapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
        unbinder.unbind();
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

    public void updateOrderCharges(Checkout checkout) {
        orderTotal.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(checkout.getOrderTotal().getGrandTotal())));
        subtotal.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(checkout.getOrderTotal().getSubtotal())));
        taxes.setText(getContext().getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(checkout.getOrderTotal().getTaxes())));
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void onCheckoutDataFetched(Checkout checkout) {
        Log.d(TAG, "Checkout data fetched :  " + checkout.toString());
        adapter.onCheckoutDataFetched(checkout);
        updateOrderCharges(checkout);
    }

    @Override
    public void onCheckoutResponseFailure() {
        Log.d(TAG, "Failed to checkout");
    }
}
