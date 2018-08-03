package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.MergedOrderAdapter;
import tdevm.app_ui.modules.dinein.adapters.RunningOrderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MergedOrderFragment extends Fragment implements DineInViewContract.MergedOrderView {

    public static final String TAG = MergedOrderFragment.class.getCanonicalName();


    @BindView(R.id.frame_layout_order_empty)
    FrameLayout frameLayout;
    Unbinder unbinder;
    @BindView(R.id.rv_merged_order)
    RecyclerView recyclerViewTempOrder;
    @BindView(R.id.tv_merged_order_id)
    TextView tempOrderId;
    @BindView(R.id.tv_merged_order_table_no)
    TextView tableNo;
    MergedOrderAdapter mergedOrderAdapter;
    @Inject
    MergedOrderPresenter presenter;

    public MergedOrderFragment() {
        // Required empty public constructor
    }

    public static MergedOrderFragment newInstance() {
        return new MergedOrderFragment();
    }

    @Override
    public void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        resolveDaggerDependencies();
        View view = inflater.inflate(R.layout.fragment_merged_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTempOrder.setLayoutManager(mLayoutManager);
        mergedOrderAdapter = new MergedOrderAdapter(getContext());
        recyclerViewTempOrder.setAdapter(mergedOrderAdapter);
        presenter.fetchTempRunningOrder();
        return view;
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


    @Override
    public void onRunningOrderFetched(TOrder tOrder) {
        presenter.fetchMergedOrder(tOrder);
    }

    @Override
    public void onMergedOrderFetched(MergedOrder mergedOrder) {
        mergedOrderAdapter.onMergedOrderFetched(mergedOrder);
        Log.d(TAG, "Order ID" + String.valueOf(mergedOrder.getOrderId()));
        Log.d(TAG, "Table no" + String.valueOf(mergedOrder.getTableId()));
    }

    @Override
    public void showNoRunningOrder() {
        frameLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
    }
}
