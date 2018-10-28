package tdevm.app_ui.modules.payment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.payment.fragments.CashPickupFragment;
import tdevm.app_ui.modules.payment.fragments.CheckoutFragment;
import tdevm.app_ui.modules.payment.fragments.PaymentFragment;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener, PaymentViewContract.PaymentActivityView {

    public static final String TAG = PaymentActivity.class.getSimpleName();

    @Inject
    PaymentActivityPresenter paymentActivityPresenter;

    private String orderId;
    private Boolean paymentPending;
    private FOrder fOrder;

    @Override
    protected void onResume() {
        super.onResume();
        paymentActivityPresenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_payments);
        Checkout.preload(getApplicationContext());
        handlePaymentView();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d(TAG, "Payment Authorized Successfully" + s);
        if (orderId != null) {
            paymentActivityPresenter.captureOrderPayment(s, orderId);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d(TAG, "Payment Failed");
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
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentFragment fragment = new PaymentFragment();
        Bundle bundle = new Bundle();
        bundle.putString("F_ORDER_ID", fOrder.getOrder_id());
        bundle.putString("ORDER_ID", fOrder.getT_order_id());
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_payments, fragment);
        transaction.commit();
    }

    public void showCashPickupScreen() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CashPickupFragment fragment = new CashPickupFragment();
        transaction.replace(R.id.frame_layout_payments, fragment);
        transaction.commit();
    }

    @Override
    public void showProgressUI() {

    }

    public void handlePaymentView() {
        try {
            Intent intent = getIntent();
            paymentPending = intent.getBooleanExtra("PAYMENT_PENDING", false);
            if (paymentPending) {
                orderId = intent.getStringExtra("ORDER_ID");
                fOrder = intent.getParcelableExtra("F_ORDER");
                if (fOrder != null) {
                    showMakePayment(fOrder);
                }
                Log.d(TAG, "PAYMENT Pending" + String.valueOf(paymentPending));
                Log.d(TAG, "F_ORDER_ID: " + fOrder.getOrder_id());

            } else {
                orderId = intent.getStringExtra("ORDER_ID");
                Log.d(TAG, "PAYMENT Pending" + String.valueOf(paymentPending));
                Log.d(TAG, "ORDER_ID_RECEIVED FROM Activity: " + orderId);
                showCheckout(orderId);
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
        ((AppApplication) getApplication()).getApiComponent().inject(this);

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

    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout_payments);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    public void onPaymentCaptured() {
        Toast.makeText(this, "Voila! Your payment was successful", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPaymentCaptureFailure() {
        Toast.makeText(this, ":( Payment failed.", Toast.LENGTH_SHORT).show();
    }
}
