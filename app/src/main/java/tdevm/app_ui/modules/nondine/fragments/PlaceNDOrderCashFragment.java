package tdevm.app_ui.modules.nondine.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.nondine.NonDineViewContract;


public class PlaceNDOrderCashFragment extends Fragment implements NonDineViewContract.PlaceNDOrderCashView {

    public static final String TAG = PlaceNDOrderCashFragment.class.getSimpleName();

    private OnFragmentInteractionListener mListener;

    public static String CREATED_ORDER_ND_CASH = "CREATED_ORDER_ND_CASH";
    Unbinder unbinder;

    public PlaceNDOrderCashFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.tv_nd_cash_order_id_value)
    TextView orderId;

    @BindView(R.id.tv_nd_cash_order_amount_value)
    TextView orderAmount;

    @BindView(R.id.shimmer_fragment_create_nd_cash_order)
    ShimmerFrameLayout shimmerFrameLayout;

    @Inject
    PlaceNDOrderCashPresenter placeNDOrderCashPresenter;


    public static PlaceNDOrderCashFragment newInstance(FOrder fOrder) {
        PlaceNDOrderCashFragment fragment = new PlaceNDOrderCashFragment();
        Bundle args = new Bundle();
        args.putParcelable(CREATED_ORDER_ND_CASH, fOrder);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onResume() {
        super.onResume();
        placeNDOrderCashPresenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        if (getArguments() != null) {

        }
        View view = inflater.inflate(R.layout.fragment_pay_cash_terminal, container, false);
        unbinder = ButterKnife.bind(this, view);
        showOrderDetails();
        return view;
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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
        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void hideProgressUI() {
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }


    public void showOrderDetails() {

        if (getArguments() != null) {
            FOrder order = getArguments().getParcelable(CREATED_ORDER_ND_CASH);
            if (order != null) {
                orderId.setText(order.getOrder_id());
                orderAmount.setText(String.valueOf(Integer.parseInt(order.getGrand_total()) * 0.01));
            }


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        placeNDOrderCashPresenter.detachView();
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
