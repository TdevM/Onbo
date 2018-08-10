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

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.payment.PaymentActivity;
import tdevm.app_ui.modules.payment.PaymentViewContract;


public class PaymentFragment extends Fragment implements PaymentViewContract.PaymentFragmentView {

    public static final String TAG = PaymentFragment.class.getSimpleName();
    Unbinder unbinder;
    PaymentActivity activity;
    private static String orderId;
    private static String fOrderId;
    private static FOrder fOrder;

    @Inject
    PaymentFragmentPresenter presenter;

    @OnClick(R.id.btn_pay)
    void startRazorPay() {
        startPayment();
    }

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
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
            fOrderId = getArguments().getString("F_ORDER_ID");
            Log.d(TAG, "Got into PaymentFragment" + orderId + " " + fOrderId);
            presenter.fetchClosedOrder(orderId, fOrderId);
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        activity = (PaymentActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_payment, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }


    @Override
    public void onClosedOrderFetched(FOrder order) {
        fOrder = order;
    }

    public void startPayment() {
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.done);
        final Activity activity = getActivity();
        if (fOrder != null) {
            try {
                JSONObject options = new JSONObject();
                options.put("name", "tdevm's palace");
                options.put("description", "Order" + fOrder.getOrder_id());
                options.put("currency", "INR");
                options.put("amount", fOrder.getGrand_total());
                checkout.open(activity, options);
            } catch (Exception e) {
                Log.e(TAG, "Error in starting Razorpay Checkout", e);
            }
        }
    }


    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);

    }




}
