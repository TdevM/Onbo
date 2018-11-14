package tdevm.app_ui.modules.payment.fragments;

import android.content.Context;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.payment.IOnBackPressed;
import tdevm.app_ui.utils.GeneralUtils;


public class CashPickupFragment extends Fragment implements IOnBackPressed {


    public static final String TAG = CashPickupFragment.class.getSimpleName();

    private static String orderId;
    private static String fOrderId;
    private static FOrder fOrder;

    Unbinder unbinder;

    @BindView(R.id.tv_cash_pickup_order_amount)
    TextView cashPickupAmt;

    @BindView(R.id.iv_animate_final_cash_pickup)
    ImageView imageViewCashPickup;

    public CashPickupFragment() {
        // Required empty public constructor
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
            Log.d(TAG, "Got into PaymentFragment" + orderId + " " + fOrderId);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cash_pickup, container, false);
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
}
