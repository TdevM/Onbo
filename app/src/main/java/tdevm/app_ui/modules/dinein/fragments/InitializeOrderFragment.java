package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.activities.InitializeDineOrderActivity;

import static tdevm.app_ui.modules.dinein.activities.InitializeDineOrderActivity.ORDER_RUNNING_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitializeOrderFragment extends Fragment {

    private Boolean orderRunning;
    private InitializeDineOrderActivity initializeDineOrderActivity;
    Unbinder unbinder;

    @BindView(R.id.toolbar_guest_fragment)
    Toolbar toolbar;

    @BindView(R.id.progress_bar_place_t_order)
    ProgressBar progressBar;

    @OnClick(R.id.btn_temp_order_init)
    void clicked(){
        if(orderRunning){
            addItemsToOrder();
        }else  {
            placeOrder();
        }
    }

    @BindView(R.id.et_temp_order_user_message)
    EditText userMessage;

    @BindView(R.id.tv_guest_count)
    TextView guestCount;

    @OnClick(R.id.btn_plus_guest_count)
    void increaseGuestCount(){
       int count =  Integer.parseInt(guestCount.getText().toString());
       count = count+1;
       guestCount.setText(String.valueOf(count));
    }

    @OnClick(R.id.btn_minus_guest_count)
    void decreaseGuestCount(){
        int count =  Integer.parseInt(guestCount.getText().toString());
        count = count-1;
        guestCount.setText(String.valueOf(count));
    }

    @BindView(R.id.layout_guest_count_picker)
    LinearLayout guestPicker;

    public InitializeOrderFragment() {
        // Required empty public constructor
    }

    public static InitializeOrderFragment newInstance(Boolean orderRunning) {
        final InitializeOrderFragment fragment = new InitializeOrderFragment();
        final Bundle args = new Bundle();
        args.putBoolean(ORDER_RUNNING_STATUS,orderRunning);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeDineOrderActivity = (InitializeDineOrderActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_initialize_temp_order, container, false);
        unbinder = ButterKnife.bind(this,view);
        if(getArguments()!=null){
            orderRunning = getArguments().getBoolean(ORDER_RUNNING_STATUS);
        }
        if(orderRunning){
            guestPicker.setVisibility(View.GONE);
        }

        return view;
    }

    private void placeOrder(){
       if(guestCount.getText()!=null && userMessage.getText()!=null){
           initializeDineOrderActivity.createOrder(Integer.parseInt(guestCount.getText().toString()),userMessage.getText().toString());
       }
    }

    private void addItemsToOrder(){
        if(userMessage.getText()!=null){
            initializeDineOrderActivity.addItemsToOrder(userMessage.getText().toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
