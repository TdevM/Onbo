package tdevm.app_ui.modules.nondine.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.modules.nondine.activities.InitNonDineOrderActivity;


public class OrderPaymentTypeFragment extends Fragment implements NonDineViewContract.OrderPaymentTypeView {

    private OrderPaymentTypeListener mListener;

    InitNonDineOrderActivity activity;

    private static final String MODE_CASH = "MODE_CASH";
    private static final String MODE_DIGITAL = "MODE_DIGITAL";
    private String selectedPaymentMode;

    @BindView(R.id.btn_place_nd_order)
    Button buttonContinue;

    @BindView(R.id.check_btn_cash)
    ImageView checkBtnCash;

    @BindView(R.id.check_btn_digital)
    ImageView checkBtnDigital;

    @OnClick(R.id.iv_payment_option_cash)
    void updateTypeCash(){
        selectedPaymentMode = MODE_CASH;
        showCashSelected();
        buttonContinue.setEnabled(true);
    }

    @OnClick(R.id.iv_payment_option_digital)
    void updateTypeDigital(){
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

    public static OrderPaymentTypeFragment newInstance() {
        OrderPaymentTypeFragment fragment = new OrderPaymentTypeFragment();
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
        View view = inflater.inflate(R.layout.fragment_order_payment_type, container, false);
        unbinder = ButterKnife.bind(this, view);
        activity = (InitNonDineOrderActivity) getActivity();
        resolveDaggerDependencies();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onPaymentMethodSelected(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OrderPaymentTypeListener) {
            mListener = (OrderPaymentTypeListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    public void handleOrderPaymentType(){
        if(selectedPaymentMode!=null){
            if (selectedPaymentMode.equals(MODE_CASH)){
                activity.createNonDineOrderCash();
            }else if(selectedPaymentMode.equals(MODE_DIGITAL)){
                activity.showDigitalPaymentOptions();
            }
        }
    }


    public void showCashSelected(){
        checkBtnDigital.setVisibility(View.GONE);
        checkBtnCash.setVisibility(View.VISIBLE);
    }

    public void showDigitalSelected(){
        checkBtnCash.setVisibility(View.GONE);
        checkBtnDigital.setVisibility(View.VISIBLE);
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);

    }


    public interface OrderPaymentTypeListener {
        void onPaymentMethodSelected(Uri uri);
    }
}
