package app.onbo.modules.payment.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.modules.payment.IOnBackPressed;
import app.onbo.modules.payment.PaymentActivity;


public class PaymentFailedFragment extends Fragment implements IOnBackPressed {


    public static final String TAG = PaymentFailedFragment.class.getSimpleName();

    @OnClick(R.id.btn_order_payment_failure_t1)
    void paymentFailed() {
        if (fOrder != null) {
            activity.showMakePayment(fOrder);
        }
    }

    PaymentActivity activity;

    Unbinder unbinder;

    private FOrder fOrder;

    public PaymentFailedFragment() {
        // Required empty public constructor
    }


    public static PaymentFailedFragment newInstance() {
        PaymentFailedFragment fragment = new PaymentFailedFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fOrder = getArguments().getParcelable("ORDER");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (PaymentActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_payment_failed, container, false);
        unbinder = ButterKnife.bind(this, view);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onBackPressed() {
        Log.d(TAG, "Payment failed on back pressed");

        return true;
    }
}
