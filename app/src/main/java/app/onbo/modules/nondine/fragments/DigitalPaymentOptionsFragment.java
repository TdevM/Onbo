package app.onbo.modules.nondine.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.modules.nondine.NonDineViewContract;
import app.onbo.modules.nondine.activities.InitNonDineOrderActivity;

public class DigitalPaymentOptionsFragment extends Fragment implements NonDineViewContract.DigitalPaymentOptionView {


    public static final String TAG = DigitalPaymentOptionsFragment.class.getSimpleName();

    InitNonDineOrderActivity activity;

    @Inject
    DigitalPaymentOptionsPresenter paymentOptionsPresenter;

    Unbinder unbinder;

    @BindView(R.id.btn_pay_amount)
    Button button;

    @BindView(R.id.progress_bar_pay_fragment)
    ProgressBar progressBar;

    @OnClick(R.id.btn_pay_amount)
    void createPaidOrder() {
        paymentOptionsPresenter.createPaidNDOrder();
    }


    public DigitalPaymentOptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        paymentOptionsPresenter.attachView(this);
    }

    public static DigitalPaymentOptionsFragment newInstance() {
        DigitalPaymentOptionsFragment fragment = new DigitalPaymentOptionsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_digital_payment_options, container, false);
        activity = (InitNonDineOrderActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);
        resolveDaggerDependencies();
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
        paymentOptionsPresenter.detachView();
    }

    @Override
    public void onNDPaidOrderCreated(FOrder fOrder) {
        // start razorpay
        activity.startPayment(fOrder);
    }


    @Override
    public void onOrderCreationFailure() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showProgressUI() {
        Log.d(TAG,"SHOW PROGRESS UI CALL");
        progressBar.setVisibility(View.VISIBLE);
        button.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
        button.setVisibility(View.VISIBLE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }

}
