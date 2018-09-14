package tdevm.app_ui.modules.nondinein.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.nondinein.NonDineViewContract;


public class OrderPaymentTypeFragment extends Fragment implements NonDineViewContract.OrderPaymentTypeView {

    private OrderPaymentTypeListener mListener;

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
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);

    }


    public interface OrderPaymentTypeListener {
        void onPaymentMethodSelected(Uri uri);
    }
}
