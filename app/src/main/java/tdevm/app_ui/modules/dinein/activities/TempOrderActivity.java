package tdevm.app_ui.modules.dinein.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.fragments.InitializeTempOrderFragment;


public class TempOrderActivity extends AppCompatActivity implements DineInViewContract.PlaceTempOrderView{
    public static final String TAG = TempOrderActivity.class.getSimpleName();
    public static final String ORDER_RUNNING_STATUS = "ORDER_RUNNING_STATUS";

    @Inject
    TempOrderPresenterImpl placeTempOrderPresenter;

    @Override
    protected void onResume() {
        super.onResume();
        placeTempOrderPresenter.attachView(this);
        Log.d(TAG,"Temp Order onResume");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        placeTempOrderPresenter.checkCurrentOrderDetails();
        Log.d(TAG,"Temp Order onCreate");
        setContentView(R.layout.activity_place_temp_order);
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication)getApplication()).getApiComponent().inject(this);

    }

    @Override
    public void showGetGuestMessage(){
        Log.d(TAG,"Not running");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ORDER_RUNNING_STATUS,false);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        InitializeTempOrderFragment fragment = new InitializeTempOrderFragment();
        fragment.setArguments(bundle);
        transaction.add(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
    }

    @Override
    public void showGetMessage(){
        Log.d(TAG,"Running");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ORDER_RUNNING_STATUS,true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        InitializeTempOrderFragment fragment = new InitializeTempOrderFragment();
        fragment.setArguments(bundle);
        transaction.add(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
    }

    @Override
    public void createOrder(int guest, String userMessage){

    }

    @Override
    public void addItemsToOrder(String userMessage){

    }

    @Override
    public void onOrderItemsAdded() {
        Toast.makeText(this, "Items added!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNewOrderCreated() {
        Toast.makeText(this, "Order placed!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        placeTempOrderPresenter.detachView();
    }
}
