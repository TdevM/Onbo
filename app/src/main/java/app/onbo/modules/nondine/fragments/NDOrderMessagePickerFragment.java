package app.onbo.modules.nondine.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import app.onbo.R;
import app.onbo.modules.nondine.activities.InitNonDineOrderActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class NDOrderMessagePickerFragment extends Fragment {


    Unbinder unbinder;

    @BindView(R.id.btn_nd_order_init)
    Button btnInitNDOrder;

    @BindView(R.id.et_nd_order_user_message)
    EditText editTextUserMessage;


    @OnClick(R.id.btn_nd_order_init)
    void showNDPayment() {
        activity.showOrderPaymentType(String.valueOf(editTextUserMessage.getText()));
    }

    InitNonDineOrderActivity activity;

    public NDOrderMessagePickerFragment() {
        // Required empty public constructor
    }


    public static NDOrderMessagePickerFragment newInstance() {
        NDOrderMessagePickerFragment fragment = new NDOrderMessagePickerFragment();
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
        activity = (InitNonDineOrderActivity) getActivity();
        View view = inflater.inflate(R.layout.fragment_ndorder_message_picker, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
