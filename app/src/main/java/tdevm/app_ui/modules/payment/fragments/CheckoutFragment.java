package tdevm.app_ui.modules.payment.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.payment.PaymentsActivity;


public class CheckoutFragment extends Fragment {

    @OnClick(R.id.btn_checkout)
    void checkout() {
        showPaymentFragment();
    }

    PaymentsActivity paymentsActivity;
    Unbinder unbinder;

    public CheckoutFragment() {
        // Required empty public constructor
    }


    public static CheckoutFragment newInstance(String param1, String param2) {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        paymentsActivity = (PaymentsActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void showPaymentFragment(){
        paymentsActivity.showMakePayment();
    }
}
