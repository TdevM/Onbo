package tdevm.app_ui.modules.dinein.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.modules.dinein.activities.TempOrderActivity;
import tdevm.app_ui.modules.dinein.bottomsheets.DishReviewsSheetFragment;

import static tdevm.app_ui.modules.dinein.activities.TempOrderActivity.ORDER_RUNNING_STATUS;

/**
 * A simple {@link Fragment} subclass.
 */
public class InitializeTempOrderFragment extends Fragment {

    private Boolean orderRunning;
    private TempOrderActivity tempOrderActivity;
    Unbinder unbinder;
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

    @BindView(R.id.et_order_guest_count)
    EditText getGuestCount;

    public InitializeTempOrderFragment() {
        // Required empty public constructor
    }

    public static InitializeTempOrderFragment newInstance(Boolean orderRunning) {
        final InitializeTempOrderFragment fragment = new InitializeTempOrderFragment();
        final Bundle args = new Bundle();
        args.putBoolean(ORDER_RUNNING_STATUS,orderRunning);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        tempOrderActivity = (TempOrderActivity)getActivity();
        View view = inflater.inflate(R.layout.fragment_initialize_temp_order, container, false);
        unbinder = ButterKnife.bind(this,view);
        if(getArguments()!=null){
            orderRunning = getArguments().getBoolean(ORDER_RUNNING_STATUS);
        }
        if(orderRunning){
            getGuestCount.setVisibility(View.GONE);
        }

        return view;
    }

    private void placeOrder(){
       if(getGuestCount.getText()!=null && userMessage.getText()!=null){
           tempOrderActivity.createOrder(Integer.parseInt(getGuestCount.getText().toString()),userMessage.getText().toString());
       }
    }

    private void addItemsToOrder(){
        if(userMessage.getText()!=null){
            tempOrderActivity.addItemsToOrder(userMessage.getText().toString());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
