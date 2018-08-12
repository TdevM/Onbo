package tdevm.app_ui.modules.orders.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.orders.RestaurantOrdersViewContract;
import tdevm.app_ui.modules.orders.adapters.MyOrdersAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrdersFragment extends Fragment implements RestaurantOrdersViewContract.MyOrdersFragmentView {

    public static final String TAG = MyOrdersFragment.class.getSimpleName();
    Unbinder unbinder;

    @BindView(R.id.rv_my_orders)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    MyOrdersAdapter adapter;

    @Inject
    MyOrdersFragmentPresenter presenter;

    public MyOrdersFragment() {
        // Required empty public constructor
    }

    public static MyOrdersFragment newInstance() {
        return new MyOrdersFragment();
    }


    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        resolveDaggerDependencies();
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        unbinder = ButterKnife.bind(this, view);
        adapter = new MyOrdersAdapter(getContext());
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        presenter.fetchMyOrders();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();

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
    public void onMyOrderFetched(List<FOrder> fOrders) {
        adapter.onMyOrdersFetched(fOrders);
    }

    @Override
    public void onMyOrdersEmpty() {

    }

    @Override
    public void onFetchingOrdersFailure() {
        Toast.makeText(getContext(), "Failed to fetch orders", Toast.LENGTH_SHORT).show();
    }
}
