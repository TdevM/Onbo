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

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.adapters.RunningOrderAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunningOrderFragment extends Fragment implements DineInViewContract.RunningOrderView {

    public static final String TAG = RunningOrderFragment.class.getCanonicalName();


    @BindView(R.id.frame_layout_order_empty)
    FrameLayout frameLayout;
    Unbinder unbinder;
    @BindView(R.id.rv_temp_order)
    RecyclerView recyclerViewTempOrder;
    @BindView(R.id.tv_temp_order_id)
    TextView tempOrderId;
    @BindView(R.id.tv_temp_order_table_no)
    TextView tableNo;
    RunningOrderAdapter runningOrderAdapter;
    @Inject
    RunningOrderPresenter presenter;

    public RunningOrderFragment() {
        // Required empty public constructor
    }

    public static RunningOrderFragment newInstance() {
        return new RunningOrderFragment();
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
        View view = inflater.inflate(R.layout.fragment_running_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewTempOrder.setLayoutManager(mLayoutManager);
        runningOrderAdapter = new RunningOrderAdapter(getContext());
        recyclerViewTempOrder.setAdapter(runningOrderAdapter);
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


//    @Override
//    public void onTempOrderFetched(ArrayList<TempOrder> tempOrder) {
//        runningOrderAdapter.onTempOrderFetched(tempOrder);
//        Log.d(TAG, "Temp order size:" + String.valueOf(tempOrder.size()));
//        Log.d(TAG, "Table no" + String.valueOf(tempOrder.get(0).getTable_no()));
//        tempOrderId.setText(getActivity().getString(R.string.running_order_id,tempOrder.get(0).getOrder_id()));
//        tableNo.setText(getActivity().getString(R.string.running_order_table_no,tempOrder.get(0).getTable_no()));
//    }
//
//    @Override
//    public void showNoRunningOrder() {
//        frameLayout.setVisibility(View.VISIBLE);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
        unbinder.unbind();
    }
}
