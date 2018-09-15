package tdevm.app_ui.modules.nondinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.Checkout;
import tdevm.app_ui.modules.nondinein.NonDineViewContract;
import tdevm.app_ui.modules.nondinein.activities.InitNonDineOrderActivity;
import tdevm.app_ui.modules.nondinein.adapters.NonDineCheckoutAdapter;


public class NonDineCheckoutFragment extends Fragment implements NonDineViewContract.NonDineCheckoutView {

    public static final String TAG = NonDineCheckoutFragment.class.getSimpleName();

    @Inject
    NonDineCheckoutPresenter presenter;
    Unbinder unbinder;

    @OnClick(R.id.btn_proceed_payment_type)
    void showPaymentTypeFragment(){
        activity.showOrderPaymentType();
    }

    InitNonDineOrderActivity activity;

    @BindView(R.id.rv_nd_checkout)
    RecyclerView recyclerViewCheckout;
    @BindView(R.id.tv_checkout_total)
    TextView orderTotal;
    @BindView(R.id.tv_checkout_taxes)
    TextView taxes;
    @BindView(R.id.tv_checkout_subtotal)
    TextView subtotal;


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
        unbinder = ButterKnife.bind(this,view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewCheckout.setLayoutManager(mLayoutManager);
        adapter = new NonDineCheckoutAdapter(getContext());
        recyclerViewCheckout.setAdapter(adapter);
        presenter.checkoutOrderSummary();
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

    }

    @Override
    public void hideProgressUI() {

    }

    public void updateOrderCharges(Checkout checkout){
        orderTotal.setText(String.valueOf(Integer.parseInt(checkout.getOrderTotal().getGrandTotal()) * 0.01));
        subtotal.setText(String.valueOf(Integer.parseInt(checkout.getOrderTotal().getSubtotal()) * 0.01));
        taxes.setText(String.valueOf(Integer.parseInt(checkout.getOrderTotal().getTaxes()) * 0.01));
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void onCheckoutDataFetched(Checkout checkout) {
        Log.d(TAG,"Checkout data fetched :  " + checkout.toString());
        adapter.onCheckoutDataFetched(checkout);
        updateOrderCharges(checkout);
    }

    @Override
    public void onCheckoutResponseFailure() {
        Log.d(TAG,"Failed to checkout");
    }
}
