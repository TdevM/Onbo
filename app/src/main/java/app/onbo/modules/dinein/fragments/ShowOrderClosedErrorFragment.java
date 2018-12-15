package app.onbo.modules.dinein.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.onbo.R;
import app.onbo.modules.dinein.activities.InitializeDineOrderActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowOrderClosedErrorFragment extends android.support.v4.app.Fragment {


    @OnClick(R.id.btn_order_already_closed)
    void click() {
        activity.showRunningOrderFragment();
    }

    InitializeDineOrderActivity activity;


    Unbinder unbinder;

    public ShowOrderClosedErrorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_order_closed_error, container, false);
        activity = (InitializeDineOrderActivity) getActivity();
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

}
