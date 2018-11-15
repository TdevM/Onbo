package tdevm.app_ui.modules.payment.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.payment.PaymentActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentSuccessFragment extends Fragment {


    @OnClick(R.id.btn_order_payment_success_t1)
    void paymentSuccess() {
        activity.goToHome();
    }

    PaymentActivity activity;

    Unbinder unbinder;

    public PaymentSuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (PaymentActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_payment_success, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
