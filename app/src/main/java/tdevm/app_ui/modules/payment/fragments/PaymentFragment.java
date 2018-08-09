package tdevm.app_ui.modules.payment.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.razorpay.Checkout;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.payment.PaymentActivity;


public class PaymentFragment extends Fragment {

    public static final String TAG = PaymentFragment.class.getSimpleName();
    Unbinder unbinder;
    PaymentActivity activity;
    String orderId;

    @OnClick(R.id.btn_pay)
    void startRazorPay() {
        startPayment();
    }

    public PaymentFragment() {
        // Required empty public constructor
    }


    public static PaymentFragment newInstance(String orderId) {
        PaymentFragment fragment = new PaymentFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            orderId = getArguments().getString("ORDER_ID");
            Log.d(TAG,"Got into PaymentFragment"+ orderId);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment ge
        activity = (PaymentActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.done);
        final Activity activity = getActivity();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "TdevM's palace");
            options.put("description", "Order" + orderId);
            options.put("currency", "INR");
            options.put("amount", "100");
            checkout.open(activity, options);
        } catch (Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }


}
