package tdevm.app_ui.modules.nondine.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

public class InitNonDineOrderActivity extends AppCompatActivity implements NonDineViewContract.InitNonDineOrderView,
        PaymentResultListener {

    public static final String TAG = InitNonDineOrderActivity.class.getSimpleName();
    private FOrder fOrderDigital;
    @Inject
    InitNonDineOrderPresenter presenter;


    @BindView(R.id.pb_non_dine_init)
    ProgressBar progressBar;

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
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
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
    }


    public void startPayment(FOrder fOrder) {
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.done);
        fOrderDigital = fOrder;
        final Activity activity = this;
        if (fOrder != null) {
            try {
                JSONObject options = new JSONObject();
                options.put("name", "tdevm's palace");
                options.put("description", "Order ID" + fOrder.getOrder_id());
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
        if(fOrderDigital!=null){
            presenter.capturePaymentForOrder(s, fOrderDigital.getOrder_id());
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        // Authorization failed
    }

    @Override
    public void onPaymentCaptured() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        OrderSuccessFragment fragment = new OrderSuccessFragment();
        transaction.replace(R.id.frame_layout_place_non_dine_order, fragment);
        transaction.commit();
        presenter.clearCart();
    }

    @Override
    public void onPaymentCaptureFailure() {

    }
}
