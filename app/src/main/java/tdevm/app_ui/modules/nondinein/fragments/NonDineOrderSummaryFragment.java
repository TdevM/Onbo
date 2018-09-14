package tdevm.app_ui.modules.nondinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.nondinein.NonDineViewContract;


public class NonDineOrderSummaryFragment extends Fragment implements NonDineViewContract.NonDineOrderSummaryView{

    public static final String TAG = NonDineOrderSummaryFragment.class.getSimpleName();

    @Inject
    NonDineOrderSummaryPresenter presenter;


    public NonDineOrderSummaryFragment() {
        // Required empty public constructor
    }


    public static NonDineOrderSummaryFragment newInstance() {
        NonDineOrderSummaryFragment fragment = new NonDineOrderSummaryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
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
        resolveDaggerDependencies();
        View view = inflater.inflate(R.layout.fragment_non_dine_order_summary, container, false);
        presenter.test();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.detachView();
    }

    @Override
    public void showProgressUI() {
        Toast.makeText(getActivity(), "Test Success", Toast.LENGTH_SHORT).show();


    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getActivity().getApplication()).getApiComponent().inject(this);
    }
}
