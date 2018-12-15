package app.onbo.modules.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.appsee.Appsee;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import javax.inject.Inject;

import app.onbo.api.models.response.v2.merged.MergedOrder;
import butterknife.BindView;
import butterknife.ButterKnife;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.modules.payment.fragments.CashPickupFragment;
import app.onbo.modules.payment.fragments.CheckoutFragment;
import app.onbo.modules.payment.fragments.PaymentFailedFragment;
import app.onbo.modules.payment.fragments.PaymentFragment;
import app.onbo.modules.payment.fragments.PaymentSuccessFragment;
import app.onbo.root.RootActivity;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener, PaymentViewContract.PaymentActivityView {

    public static final String TAG = PaymentActivity.class.getSimpleName();

    @Inject
    PaymentActivityPresenter paymentActivityPresenter;

    private String fOrderId;
    private String tOrderId;
    private Boolean fOrderAvailable;
    private FOrder fOrder;

    @BindView(R.id.frame_layout_payment_capture_progress)
    FrameLayout frameLayoutPaymentCaptureProgress;


    @BindView(R.id.frame_layout_check_payment_status)
    FrameLayout frameLayoutCheckPaymentStatus;


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        paymentActivityPresenter.attachView(this);
        setContentView(R.layout.activity_payments);
        ButterKnife.bind(this);
        handlePaymentView();
        Appsee.start();

        Checkout.preload(getApplicationContext());

    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d(TAG, "Payment Authorized Successfully" + s);
        try {
            if (fOrder != null) {
                paymentActivityPresenter.captureOrderPayment(s, fOrder.getOrder_id());
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d(TAG, "Payment Failed");
        Log.d(TAG, "VALUE OF F_ORDER" + fOrder);
        try {
            showPaymentError(fOrder);
        } catch (Exception c) {
            c.printStackTrace();
        }

    }


    public void showCheckout(String orderId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle bundle = new Bundle();
        if (orderId != null) {
            bundle.putString("ORDER_ID", orderId);
            fragment.setArguments(bundle);
            transaction.replace(R.id.frame_layout_payments, fragment);
            transaction.commit();
        }
    }

    public void showMakePayment(FOrder fOrder) {
        this.fOrder = fOrder;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentFragment fragment = new PaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ORDER", fOrder);
        bundle.putString("F_ORDER_ID", fOrder.getOrder_id());
        bundle.putString("ORDER_ID", fOrder.getT_order_id());
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_payments, fragment);
        transaction.commit();
    }

    public void showCashPickupScreen(FOrder fOrder) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CashPickupFragment fragment = new CashPickupFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ORDER", fOrder);
        bundle.putString("F_ORDER_ID", fOrder.getOrder_id());
        bundle.putString("ORDER_ID", fOrder.getT_order_id());
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_payments, fragment).addToBackStack("MAKE_PAYMENT");
        transaction.commit();
    }

    @Override
    public void showProgressUI() {

    }

    public void handlePaymentView() {
        try {
            Intent intent = getIntent();
            fOrderAvailable = intent.getBooleanExtra("F_ORDER_AVAILABLE", false);

            if (fOrderAvailable) {
                fOrder = intent.getParcelableExtra("F_ORDER");
                if (fOrder.getCompleted() && fOrder.getTxn_status()) {
                    showPaymentSuccess(fOrder);
                } else {
                    Log.d(TAG, "PAYMENT Pending" + String.valueOf(true));
                    Log.d(TAG, "F_ORDER_ID: " + fOrder.getOrder_id());
                    showMakePayment(fOrder);
                }

            } else {
                tOrderId = intent.getStringExtra("T_ORDER_ID");
                Log.d(TAG, "PAYMENT Pending" + String.valueOf(true));
                Log.d(TAG, "T_ORDER_ID_RECEIVED FROM Activity: " + tOrderId);
                paymentActivityPresenter.fetchMergedOrder(tOrderId);

            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("getStringExtra_EX", e + "");
        }
    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getApplication()).getApiComponent().inject(this);

    }

    public void goToHome() {
        Intent intent = new Intent(PaymentActivity.this, RootActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        paymentActivityPresenter.detachView();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout_payments);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    public void showPaymentSuccess(FOrder fOrder) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentSuccessFragment fragment = new PaymentSuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ORDER", fOrder);
        bundle.putString("F_ORDER_ID", fOrder.getOrder_id());
        bundle.putString("ORDER_ID", fOrder.getT_order_id());
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_payments, fragment);
        transaction.commit();
    }

    public void showPaymentError(FOrder fOrder) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentFailedFragment fragment = new PaymentFailedFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("ORDER", fOrder);
        bundle.putString("F_ORDER_ID", fOrder.getOrder_id());
        bundle.putString("ORDER_ID", fOrder.getT_order_id());
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_payments, fragment);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onPaymentCaptured(FOrder fOrder) {
        showPaymentSuccess(fOrder);
        //Toast.makeText(this, "Voila! Your payment was successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPaymentCaptureProgressUI() {
        frameLayoutPaymentCaptureProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showFetchingPayment() {
        frameLayoutCheckPaymentStatus.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideFetchingPayment() {
        frameLayoutCheckPaymentStatus.setVisibility(View.GONE);
    }

    @Override
    public void hidePaymentCaptureProgressUI() {
        frameLayoutPaymentCaptureProgress.setVisibility(View.GONE);
    }

    @Override
    public void onFOrderFetched(FOrder fOrder) {
        if (fOrder.getCompleted() && fOrder.getTxn_status()) {
            showPaymentSuccess(fOrder);
        } else {
            showMakePayment(fOrder);
        }
    }

    @Override
    public void onFOrderFetchFailure() {

    }

    @Override
    public void onMergedOrderFetched(MergedOrder mergedOrder) {
        if (!mergedOrder.getClosed()) {
            showCheckout(mergedOrder.getOrderId());
        } else {
            paymentActivityPresenter.fetchClosedOrder(mergedOrder.getOrderId());
        }
    }

    @Override
    public void onMergedOrderFailure() {

    }

    @Override
    public void onPaymentCaptureFailure(FOrder fOrder) {
        showPaymentError(fOrder);
        // Toast.makeText(this, ":( Payment failed.", Toast.LENGTH_SHORT).show();
    }
}
