package tdevm.app_ui.modules.nondine.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.dinein.fragments.OrderSuccessFragment;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.modules.nondine.fragments.DigitalPaymentOptionsFragment;
import tdevm.app_ui.modules.nondine.fragments.NDOrderCashFragment;
import tdevm.app_ui.modules.nondine.fragments.NonDineCheckoutFragment;
import tdevm.app_ui.modules.nondine.fragments.OrderPaymentTypeFragment;
import tdevm.app_ui.modules.nondine.fragments.PaymentFailedFragmentNonDine;
import tdevm.app_ui.modules.nondine.fragments.PaymentSuccessFragmentNonDine;
import tdevm.app_ui.modules.payment.PaymentActivity;
import tdevm.app_ui.modules.payment.fragments.PaymentFailedFragment;
import tdevm.app_ui.modules.payment.fragments.PaymentSuccessFragment;
import tdevm.app_ui.root.RootActivity;

public class InitNonDineOrderActivity extends AppCompatActivity implements NonDineViewContract.InitNonDineOrderView,
        PaymentResultListener {

    public static final String TAG = InitNonDineOrderActivity.class.getSimpleName();
    private FOrder fOrderDigital;
    @Inject
    InitNonDineOrderPresenter presenter;


    @BindView(R.id.frame_layout_payment_capture_progress)
    FrameLayout paymentCaptureProgress;


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

    public void showOrderPaymentType() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, OrderPaymentTypeFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
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
        PaymentFailedFragmentNonDine fragment = new PaymentFailedFragmentNonDine();
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
