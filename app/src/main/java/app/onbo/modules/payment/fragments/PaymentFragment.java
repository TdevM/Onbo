package app.onbo.modules.payment.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.razorpay.Checkout;

import org.json.JSONObject;

import javax.inject.Inject;

import app.onbo.utils.PreferenceUtils;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.modules.payment.IOnBackPressed;
import app.onbo.modules.payment.PaymentActivity;
import app.onbo.modules.payment.PaymentViewContract;


public class PaymentFragment extends Fragment implements PaymentViewContract.PaymentFragmentView,
        IOnBackPressed {

    public static final String TAG = PaymentFragment.class.getSimpleName();
    Unbinder unbinder;
    PaymentActivity activity;
    private static String orderId;
    private static String fOrderId;
    private static FOrder fOrder;

    @Inject
    PreferenceUtils preferenceUtils;

    private static final String MODE_CASH = "MODE_CASH";
    private static final String MODE_DIGITAL = "MODE_DIGITAL";
    private String selectedPaymentMode;

    @BindView(R.id.btn_pay_dine_order)
    Button buttonContinue;

    @BindView(R.id.progress_bar_order_payment_type_dine)
    ProgressBar progressBar;

    @BindView(R.id.check_btn_cash)
    ImageView checkBtnCash;

    @BindView(R.id.check_btn_digital)
    ImageView checkBtnDigital;


    @OnClick(R.id.iv_payment_option_cash)
    void updateTypeCash() {
        selectedPaymentMode = MODE_CASH;
        showCashSelected();
        buttonContinue.setEnabled(true);
    }

    @OnClick(R.id.iv_payment_option_digital)
    void updateTypeDigital() {
        selectedPaymentMode = MODE_DIGITAL;
        showDigitalSelected();
        buttonContinue.setEnabled(true);
    }

    @Inject
    PaymentFragmentPresenter presenter;

    @OnClick(R.id.btn_pay_dine_order)
    void start() {
        handleOrderPaymentType();
    }

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    public static PaymentFragment newInstance(String orderId) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderId = getArguments().getString("ORDER_ID");
            fOrderId = getArguments().getString("F_ORDER_ID");
            fOrder = getArguments().getParcelable("ORDER");
            Log.d(TAG, "Got into PaymentFragment" + orderId + " " + fOrderId);

        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        presenter.fetchClosedOrder(orderId, fOrderId);
        activity = (PaymentActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }


    public void handleOrderPaymentType() {
        if (selectedPaymentMode != null) {
            if (selectedPaymentMode.equals(MODE_CASH)) {

                if (getArguments() != null) {

                    fOrder = getArguments().getParcelable("ORDER");
                    if (fOrder != null) {
                        activity.showCashPickupScreen(fOrder);
                    }
                    Log.d(TAG, "Got into PaymentFragment" + "f_order" + " " + fOrder);

                }

            } else if (selectedPaymentMode.equals(MODE_DIGITAL)) {
                presenter.checkPaymentStatus(orderId, fOrderId);
            }
        }
    }

    public void showCashSelected() {
        checkBtnDigital.setVisibility(View.INVISIBLE);
        checkBtnCash.setVisibility(View.VISIBLE);
    }

    public void showDigitalSelected() {
        checkBtnCash.setVisibility(View.INVISIBLE);
        checkBtnDigital.setVisibility(View.VISIBLE);
    }


    @Override
    public void onClosedOrderFetched(FOrder order) {
        fOrder = order;
    }

    @Override
    public void onPaymentStatusFetched(FOrder order) {
        fOrder = order;
        if (order.getTxn_status() && order.getCompleted()) {
            activity.showPaymentSuccess(order);
        } else {
            startPayment();
        }
    }

    @Override
    public void onPaymentStatusFetchedFailure() {

    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        //checkout.setImage(R.drawable.done);
        final Activity activity = getActivity();
        if (fOrder != null) {
            try {
                JSONObject options = new JSONObject();
                options.put("name", fOrder.getRestaurant().getRestaurant_name());
                options.put("description", "ORDER NO # " + fOrder.getOrder_id());
                options.put("currency", "INR");
                options.put("prefill.email", preferenceUtils.getAuthUserEmail());
                options.put("prefill.contact", preferenceUtils.getAuthLoginPhone());
                options.put("amount", fOrder.getGrand_total());
                checkout.open(activity, options);
            } catch (Exception e) {
                Log.e(TAG, "Error in starting Razorpay Checkout", e);
            }
        }
    }

    private void showPaymentWarning() {
        new AlertDialog.Builder(activity)
                .setTitle("Payment pending")
                .setMessage("Please complete your payment.")
                .setPositiveButton("OK", (dialog, which) -> {
                    if (orderId != null) {
                        // checkoutPresenter.closeRunningOrder(orderId);
                    }
                    Log.d(TAG, "Sending close order");
                })
                .setNegativeButton("Cancel", (dialog, which) -> Log.d(TAG, "Aborting close order..."))
                .show();
    }


    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);

    }


    @Override
    public boolean onBackPressed() {
        showPaymentWarning();
        return true;
    }
}
