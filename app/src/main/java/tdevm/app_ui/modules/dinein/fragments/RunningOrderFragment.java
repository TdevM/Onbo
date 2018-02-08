package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.DineInViewContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class RunningOrderFragment extends Fragment implements DineInViewContract.RunningOrderView{


    @BindView(R.id.frame_layout_order_empty)
    FrameLayout frameLayout;
    Unbinder unbinder;

    public RunningOrderFragment() {
        // Required empty public constructor
    }

    public static RunningOrderFragment newInstance(){
        return new RunningOrderFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view;
        view =  inflater.inflate(R.layout.fragment_running_order, container, false);
        unbinder = ButterKnife.bind(this,view);
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

    }

    @Override
    public void onTempOrderFetched() {

    }

    @Override
    public void showNoRunningOrder() {

    }
}
