package tdevm.app_ui.modules.dinein.fragments;


import android.content.Context;
import android.graphics.drawable.Animatable;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.modules.dinein.activities.InitializeDineOrderActivity;
import tdevm.app_ui.modules.orders.callback.CartBadgeListener;
import tdevm.app_ui.modules.payment.IOnBackPressed;
import tdevm.app_ui.utils.CartListener;

import static tdevm.app_ui.modules.dinein.activities.InitializeDineOrderActivity.ORDER_RUNNING_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderSuccessFragment extends Fragment implements IOnBackPressed {


    Unbinder unbinder;


    private CartBadgeListener cartBadgeListener;

    @BindView(R.id.iv_animate_t1_success)
    ImageView imageView;

    @BindView(R.id.tv_t1_order_success_order_id)
    TextView orderIdSuccess;

    @BindView(R.id.tv_t1_order_success_table_no)
    TextView tableNoSuccess;

    @OnClick(R.id.btn_order_success_t1_details)
    void showRunningOrder() {
        activity.showRunningOrderFragment();
    }

    TOrder tOrder;

    InitializeDineOrderActivity activity;

    public OrderSuccessFragment() {
        // Required empty public constructor
    }


    public static OrderSuccessFragment getInstance() {
        return new OrderSuccessFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (InitializeDineOrderActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_order_success, container, false);
        unbinder = ButterKnife.bind(this, view);
        //animate(imageView);
        if (getArguments() != null) {
            tOrder = getArguments().getParcelable("T_ORDER");
            showOrderDetails(getArguments().getParcelable("T_ORDER"));

        }

        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    public void animate(View view) {
        ImageView v = (ImageView) view;
        Drawable d = v.getDrawable();
        Animatable animatedVector = (Animatable) d;
//        final Handler mainHandler = new Handler(Looper.getMainLooper());
        animatedVector.start();

//        animatedVector.registerAnimationCallback(new Animatable2Compat.AnimationCallback() {
//            @Override
//            public void onAnimationEnd(final Drawable drawable) {
//                mainHandler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        animatedVector.start();
//                    }
//                });
//            }
//        });
//        animatedVector.start();
    }

    void showOrderDetails(TOrder tOrder) {
        if (tOrder != null) {
            orderIdSuccess.setText(getContext().getString(R.string.show_number_pound_symbol, tOrder.getOrderId()));
            tableNoSuccess.setText(getContext().getString(R.string.show_number_pound_symbol, String.valueOf(tOrder.getRestaurantTable().getTable_number())));
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CartBadgeListener) {
            cartBadgeListener = (CartBadgeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CartBadgeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        cartBadgeListener = null;
    }

    @Override
    public boolean onBackPressed() {
        activity.showRunningOrderFragment();
        return true;
    }
}
