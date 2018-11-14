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
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.activities.InitializeDineOrderActivity;
import tdevm.app_ui.modules.orders.callback.CartBadgeListener;
import tdevm.app_ui.modules.payment.IOnBackPressed;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemsAddedSuccessFragment extends Fragment implements IOnBackPressed {


    Unbinder unbinder;


    @BindView(R.id.iv_animate_t1_success)
    ImageView imageView;

    @OnClick(R.id.btn_view_order_detail)
    void showRunning() {
        activity.showRunningOrderFragment();
    }

    CartBadgeListener cartBadgeListener;
    InitializeDineOrderActivity activity;

    public ItemsAddedSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (InitializeDineOrderActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_items_added_success, container, false);
        unbinder = ButterKnife.bind(this, view);
        //animate(imageView);
        return view;

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void animate(View view) {
        ImageView v = (ImageView) view;
        Drawable d = v.getDrawable();
        Animatable animatedVector = (Animatable) d;
        //final Handler mainHandler = new Handler(Looper.getMainLooper());
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
        animatedVector.start();
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
