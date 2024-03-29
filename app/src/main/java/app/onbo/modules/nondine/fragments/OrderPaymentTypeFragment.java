package app.onbo.modules.nondine.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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


public class OrderPaymentTypeFragment extends Fragment implements NonDineViewContract.OrderPaymentTypeView {

    InitNonDineOrderActivity activity;

    @Inject
    OrderPaymentTypePresenter paymentTypePresenter;


    private static final String MODE_CASH = "MODE_CASH";
    private static final String MODE_DIGITAL = "MODE_DIGITAL";
    private String selectedPaymentMode;

    @BindView(R.id.btn_place_nd_order)
    Button buttonContinue;

    @BindView(R.id.progress_bar_order_payment_type)
    ProgressBar progressBar;

    @BindView(R.id.check_btn_cash)
    ImageView checkBtnCash;

    @BindView(R.id.check_btn_digital)
    ImageView checkBtnDigital;


    String userMessage;

    @OnClick(R.id.iv_payment_option_cash)
    void updateTypeCash() {
        selectedPaymentMode = MODE_CASH;
        showCashSelected();
        buttonContinue.setEnabled(true);
    }

    @OnClick(R.id.iv_payment_option_digital)
    void updateTypeDigital() {
        selectedPaymentMode = MODE_DIGITAL;
        showDigitalSelected();
        buttonContinue.setEnabled(true);
    }

    @OnClick(R.id.btn_place_nd_order)
    void selectPaymentAndPlaceOrder() {
        handleOrderPaymentType();
    }

    Unbinder unbinder;

    public OrderPaymentTypeFragment() {
        // Required empty public constructor
    }

    public static OrderPaymentTypeFragment newInstance(String userMessage) {
        OrderPaymentTypeFragment fragment = new OrderPaymentTypeFragment();
        Bundle args = new Bundle();
        args.putString("USER_MESSAGE", userMessage);
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
    public void onResume() {
        super.onResume();
        paymentTypePresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order_payment_type, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity = (InitNonDineOrderActivity) getActivity();
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
        unbinder.unbind();
        paymentTypePresenter.detachView();
    }

    @Override
    public void showProgressUI() {
        progressBar.setVisibility(View.VISIBLE);
        buttonContinue.setVisibility(View.GONE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
        buttonContinue.setVisibility(View.VISIBLE);
    }

    public void handleOrderPaymentType() {
        if (selectedPaymentMode != null) {
            if (getArguments() != null) {
                userMessage = getArguments().getString("USER_MESSAGE");
            } else {
                userMessage = "";
            }
            if (selectedPaymentMode.equals(MODE_CASH)) {
                paymentTypePresenter.createCashNDOrder(userMessage);
            } else if (selectedPaymentMode.equals(MODE_DIGITAL)) {
                //activity.showDigitalPaymentOptions();
                paymentTypePresenter.createPaidNDOrder(userMessage);
            }
        }
    }


    public void showCashSelected() {
        checkBtnDigital.setVisibility(View.INVISIBLE);
        checkBtnCash.setVisibility(View.VISIBLE);
    }

    public void showDigitalSelected() {
        checkBtnCash.setVisibility(View.INVISIBLE);
        checkBtnDigital.setVisibility(View.VISIBLE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((OnboApplication) getActivity().getApplication()).getApiComponent().inject(this);

    }

    @Override
    public void onNDCashOrderCreated(FOrder fOrder) {
        activity.onNDCashOrderCreated(fOrder);
    }

    @Override
    public void onNDPaidOrderCreated(FOrder fOrder) {
        activity.onPaidOrderCreated(fOrder);
    }

    @Override
    public void onOrderCreationFailure() {

    }


}
