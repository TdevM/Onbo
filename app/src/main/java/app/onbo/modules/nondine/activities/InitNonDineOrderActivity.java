package app.onbo.modules.nondine.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.appsee.Appsee;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import javax.inject.Inject;

import app.onbo.modules.nondine.fragments.NDOrderMessagePickerFragment;
import butterknife.BindView;
import butterknife.ButterKnife;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.modules.nondine.NonDineViewContract;
import app.onbo.modules.nondine.fragments.DigitalPaymentOptionsFragment;
import app.onbo.modules.nondine.fragments.NDOrderCashFragment;
import app.onbo.modules.nondine.fragments.NonDineCheckoutFragment;
import app.onbo.modules.nondine.fragments.OrderPaymentTypeFragment;
import app.onbo.modules.nondine.fragments.PaymentFailedFragmentNonDine;
import app.onbo.modules.nondine.fragments.PaymentSuccessFragmentNonDine;
import app.onbo.modules.payment.fragments.PaymentFailedFragment;
import app.onbo.root.RootActivity;

public class InitNonDineOrderActivity extends AppCompatActivity implements NonDineViewContract.InitNonDineOrderView,
        PaymentResultListener {

    public static final String TAG = InitNonDineOrderActivity.class.getSimpleName();
    private FOrder fOrderDigital;
    @Inject
    InitNonDineOrderPresenter presenter;


    @BindView(R.id.frame_layout_payment_capture_progress)
    FrameLayout paymentCaptureProgress;


    String userMessage;

    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_init_non_dine_order);
        Appsee.start();
        ButterKnife.bind(this);
        Checkout.preload(getApplicationContext());
        showOrderSummary();
    }

    @Override
    public void showProgressUI() {
        //progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressUI() {
        //progressBar.setVisibility(View.GONE);
    }

    public void showOrderSummary() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, NonDineCheckoutFragment.newInstance());
        transaction.commit();
    }

    public void showOrderPaymentType(String userMessage) {
        this.userMessage = userMessage;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, OrderPaymentTypeFragment.newInstance(userMessage));
        transaction.commit();
    }

    public void showGetNDOrderMessage() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, NDOrderMessagePickerFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getApplication()).getApiComponent().inject(this);
    }


    public void showDigitalPaymentOptions() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, DigitalPaymentOptionsFragment.newInstance());
        transaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }


    public void onNDCashOrderCreated(FOrder fOrder) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, NDOrderCashFragment.newInstance(fOrder));
        transaction.commit();
        presenter.clearCart();
    }

    public void onPaidOrderCreated(FOrder fOrder) {
        startPayment(fOrder);

    }

    public void goToHome() {
        Intent intent = new Intent(InitNonDineOrderActivity.this, RootActivity.class);
        startActivity(intent);
        finish();
    }


    public void startPayment(FOrder fOrder) {
        Checkout checkout = new Checkout();
        //checkout.setImage(R.drawable.done);
        fOrderDigital = fOrder;
        final Activity activity = this;
        if (fOrder != null) {
            try {
                JSONObject options = new JSONObject();
                options.put("name", fOrder.getRestaurant().getRestaurant_name());
                options.put("description", "Order ID # " + fOrder.getOrder_id());
                options.put("currency", "INR");
                options.put("amount", fOrder.getGrand_total());
                checkout.open(activity, options);
            } catch (Exception e) {
                Log.e(TAG, "Error in starting Razorpay Checkout", e);
            }
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        if (fOrderDigital != null) {
            presenter.capturePaymentForOrder(s, fOrderDigital.getOrder_id());
            presenter.clearCart();
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        // Authorization failed
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentFailedFragmentNonDine fragment = PaymentFailedFragmentNonDine.newInstance(userMessage);
        transaction.replace(R.id.frame_layout_place_non_dine_order, fragment);
        transaction.commitAllowingStateLoss();

    }

    @Override
    public void onPaymentCaptured() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentSuccessFragmentNonDine fragment = new PaymentSuccessFragmentNonDine();
        transaction.replace(R.id.frame_layout_place_non_dine_order, fragment);
        transaction.commit();

    }

    @Override
    public void onPaymentCaptureFailure() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        PaymentFailedFragment fragment = new PaymentFailedFragment();
        transaction.replace(R.id.frame_layout_place_non_dine_order, fragment);
        transaction.commit();
    }

    @Override
    public void showPaymentCaptureProgressUI() {
        paymentCaptureProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidePaymentCaptureProgressUI() {
        paymentCaptureProgress.setVisibility(View.GONE);
    }
}
