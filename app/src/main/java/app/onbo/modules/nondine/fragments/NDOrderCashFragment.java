package app.onbo.modules.nondine.fragments;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.graphics.drawable.Animatable2Compat;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.modules.nondine.NonDineViewContract;
import app.onbo.utils.GeneralUtils;


public class NDOrderCashFragment extends Fragment implements NonDineViewContract.PlaceNDOrderCashView {

    public static final String TAG = NDOrderCashFragment.class.getSimpleName();

    public static String CREATED_ORDER_ND_CASH = "CREATED_ORDER_ND_CASH";
    Unbinder unbinder;

    public NDOrderCashFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tv_nd_cash_order_id_value)
    TextView orderId;

    @BindView(R.id.tv_nd_cash_order_amount_value)
    TextView orderAmount;


    @BindView(R.id.iv_animate_final_cash_pickup)
    ImageView imageView;


    @Inject
    NDOrderCashPresenter NDOrderCashPresenter;


    public static NDOrderCashFragment newInstance(FOrder fOrder) {
        NDOrderCashFragment fragment = new NDOrderCashFragment();
        Bundle args = new Bundle();
        args.putParcelable(CREATED_ORDER_ND_CASH, fOrder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        NDOrderCashPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        if (getArguments() != null) {

        }
        View view = inflater.inflate(R.layout.fragment_pay_cash_terminal, container, false);
        unbinder = ButterKnife.bind(this, view);
        showOrderDetails();
        //animate(imageView);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    public void showOrderDetails() {

        if (getArguments() != null) {
            FOrder order = getArguments().getParcelable(CREATED_ORDER_ND_CASH);
            if (order != null) {
                orderId.setText(getContext().getString(R.string.show_number_pound_symbol,order.getOrder_id()));
                orderAmount.setText(getContext().getString(R.string.rupee_symbol,GeneralUtils.parseStringDouble(order.getGrand_total())));
            }


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        NDOrderCashPresenter.detachView();
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

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
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

}
