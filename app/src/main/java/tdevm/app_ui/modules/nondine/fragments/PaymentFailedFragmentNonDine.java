package tdevm.app_ui.modules.nondine.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.nondine.activities.InitNonDineOrderActivity;
import tdevm.app_ui.modules.payment.PaymentActivity;


public class PaymentFailedFragmentNonDine extends Fragment {


    @OnClick(R.id.btn_order_payment_failure_t1)
    void paymentFailed() {
        activity.showOrderPaymentType();
    }

    InitNonDineOrderActivity activity;

    Unbinder unbinder;

    private FOrder fOrder;

    public PaymentFailedFragmentNonDine() {
        // Required empty public constructor
    }


    public static PaymentFailedFragmentNonDine newInstance() {
        PaymentFailedFragmentNonDine fragment = new PaymentFailedFragmentNonDine();
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
        activity = (InitNonDineOrderActivity) getActivity();
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
}
