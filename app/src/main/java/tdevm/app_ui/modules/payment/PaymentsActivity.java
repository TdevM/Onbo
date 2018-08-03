package tdevm.app_ui.modules.payment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import tdevm.app_ui.R;
import tdevm.app_ui.modules.payment.fragments.CheckoutFragment;
import tdevm.app_ui.modules.payment.fragments.PaymentFragment;

public class PaymentsActivity extends AppCompatActivity implements PaymentResultListener {

    public static final String TAG = PaymentsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        Checkout.preload(getApplicationContext());
        showCheckout();
    }

    @Override
    public void onPaymentSuccess(String s) {
        Log.d(TAG,"Payment Authorized Successfully");
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d(TAG,"Payment Failed");
    }


    public void showCheckout() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        CheckoutFragment fragment = new CheckoutFragment();
        transaction.replace(R.id.frame_layout_payments, fragment);
        transaction.commit();
    }

    public void showMakePayment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentFragment fragment = new PaymentFragment();
        transaction.replace(R.id.frame_layout_payments, fragment);
        transaction.commit();
    }

}
