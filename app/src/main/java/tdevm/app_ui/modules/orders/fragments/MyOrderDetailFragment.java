package tdevm.app_ui.modules.orders.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.orders.RestaurantOrdersViewContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderDetailFragment extends Fragment implements RestaurantOrdersViewContract.MyOrderDetailFragmentView{

    public static final String TAG = MyOrderDetailFragment.class.getSimpleName();
    Unbinder unbinder;

    @Inject
    MyOrderDetailFragmentPresenter presenter;


    public MyOrderDetailFragment() {
        // Required empty public constructor
    }
    public static MyOrderDetailFragment newInstance(){
        return new MyOrderDetailFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resolveDaggerDependencies();
        View view = inflater.inflate(R.layout.fragment_my_order_details, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.detachView();
    }
}
