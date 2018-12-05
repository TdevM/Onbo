package app.onbo.modules.payment.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import javax.inject.Inject;

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
import app.onbo.utils.GeneralUtils;


public class CashPickupFragment extends Fragment implements IOnBackPressed, PaymentViewContract.CashPickupView {


    public static final String TAG = CashPickupFragment.class.getSimpleName();

    private static String orderId;
    private static String fOrderId;
    private static FOrder fOrder;

    Unbinder unbinder;

    @BindView(R.id.tv_cash_pickup_order_amount)
    TextView cashPickupAmt;

    @BindView(R.id.btn_check_payment_status_t1)
    Button btnCheckButtonPaymentStatus;

    @OnClick(R.id.btn_check_payment_status_t1)
    void fetchPaymentStatus() {
        pickupPresenter.fetchOrderPaymentStatus(fOrder);
    }

    @BindView(R.id.pb_t1_check_payment_status)
    ProgressBar progressBarPaymentStatusT1;

    @BindView(R.id.tv_payment_status_t1)
    TextView paymentPendingStatus;

    @BindView(R.id.iv_animate_final_cash_pickup)
    ImageView imageViewCashPickup;

    PaymentActivity paymentActivity;

    public CashPickupFragment() {
        // Required empty public constructor
    }


    @Inject
    CashPickupPresenter pickupPresenter;

    @Override
    public void onResume() {
        super.onResume();
        pickupPresenter.attachView(this);
    }

    public static CashPickupFragment newInstance() {
        CashPickupFragment fragment = new CashPickupFragment();
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
            Log.d(TAG, "Got into Cash PaymentFragment" + orderId + " " + fOrderId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cash_pickup, container, false);
        resolveDaggerDependencies();
        paymentActivity = (PaymentActivity) getActivity();
        ButterKnife.bind(this, view);
        //animate(imageViewCashPickup);
        if (fOrder != null) {
            cashPickupAmt.setText(getContext().getString(R.string.rupee_symbol_to_pay, GeneralUtils.parseStringDouble(fOrder.getGrand_total())));
        }


        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    public void animate(View view) {
        ImageView v = (ImageView) view;
        Drawable d = v.getDrawable();
        AnimatedVectorDrawableCompat animatedVector = (AnimatedVectorDrawableCompat) d;
        final Handler mainHandler = new Handler(Looper.getMainLooper());
        animatedVector.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
            @Override
            public void onAnimationEnd(final Drawable drawable) {
                mainHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        animatedVector.start();
                    }
                });
            }
        });
        animatedVector.start();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onBackPressed() {

        return false;
    }

    @Override
    public void onFOrderFetched(FOrder fOrder) {
        if (fOrder.getTxn_status()) {
            paymentPendingStatus.setVisibility(View.INVISIBLE);
            paymentActivity.showPaymentSuccess(fOrder);

        } else {
            paymentPendingStatus.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showProgressUI() {
        progressBarPaymentStatusT1.setVisibility(View.VISIBLE);
        btnCheckButtonPaymentStatus.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgressUI() {
        btnCheckButtonPaymentStatus.setVisibility(View.VISIBLE);
        progressBarPaymentStatusT1.setVisibility(View.INVISIBLE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        pickupPresenter.detachView();
    }
}
