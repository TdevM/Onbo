package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitializeTempOrderFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.btn_temp_order_init)
    Button orderItitiate;

    @BindView(R.id.et_order_message)
    EditText editText;


    public InitializeTempOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initialize_temp_order, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

}
