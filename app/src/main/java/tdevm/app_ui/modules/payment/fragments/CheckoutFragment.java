package tdevm.app_ui.modules.payment.fragments;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.payment.PaymentViewContract;
import tdevm.app_ui.modules.payment.PaymentActivity;


public class CheckoutFragment extends Fragment implements PaymentViewContract.CheckoutFragmentView {

    public static final String TAG = CheckoutFragment.class.getSimpleName();

    @OnClick(R.id.btn_checkout)
    void checkout() {
        closeRunningOrder();
    }

    @BindView(R.id.pb_fragment_checkout)
    ProgressBar progressBar;
    @BindView(R.id.btn_checkout)
    Button checkoutBtn;
    String orderId;

    PaymentActivity paymentActivity;
    Unbinder unbinder;

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
        if (getArguments() != null) {
            orderId = getArguments().getString("ORDER_ID");
            Log.d(TAG, "Got into checkout" + orderId);
            checkoutPresenter.fetchMergedOrder(orderId);
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
                .setTitle("Close running_order_empty")
                .setMessage("Are you sure you want to close your running order?")
                .setPositiveButton("Close", (dialog, which) -> {
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
        progressBar.setVisibility(View.VISIBLE);
        checkoutBtn.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.INVISIBLE);
        checkoutBtn.setVisibility(View.VISIBLE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void onOrderClosed(FOrder fOrder) {
        Toast.makeText(paymentActivity, "Order closed!", Toast.LENGTH_SHORT).show();
        paymentActivity.showMakePayment(fOrder);
    }

    @Override
    public void onOrderClosedFailure() {

    }

    @Override
    public void onMergedOrderFetched(FOrder fOrder) {
        Log.d(TAG, fOrder.toString());
    }
}
