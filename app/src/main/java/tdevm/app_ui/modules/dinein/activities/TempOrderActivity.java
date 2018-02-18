package tdevm.app_ui.modules.dinein.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Response;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.TempOrder;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.fragments.InitializeTempOrderFragment;
import tdevm.app_ui.modules.dinein.fragments.TempOrderFragment;


public class TempOrderActivity extends AppCompatActivity implements DineInViewContract.PlaceTempOrderView{
    public static final String TAG = TempOrderActivity.class.getSimpleName();
    public static final String ORDER_RUNNING_STATUS = "ORDER_RUNNING_STATUS";
    public static ArrayList<TempOrder> tempOrderArrayList;

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
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
    }

    @Override
    public void showGetMessage(Response<ArrayList<TempOrder>> arrayListResponse){
        Log.d(TAG,"Running");
        tempOrderArrayList = new ArrayList<>();
        tempOrderArrayList.addAll(arrayListResponse.body());
        Bundle bundle = new Bundle();
        bundle.putBoolean(ORDER_RUNNING_STATUS,true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        InitializeTempOrderFragment fragment = new InitializeTempOrderFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
    }

    @Override
    public void createOrder(int guest, String userMessage){
        placeTempOrderPresenter.createNewOrder(guest,userMessage);
        placeTempOrderPresenter.clearCart();
    }

    @Override
    public void addItemsToOrder(String userMessage){
        placeTempOrderPresenter.addItemsToOrder(userMessage,tempOrderArrayList);

    }

    @Override
    public void onOrderItemsAdded() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TempOrderFragment fragment = new TempOrderFragment();
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
        tempOrderArrayList.clear();
        placeTempOrderPresenter.clearCart();
    }

    @Override
    public void onNewOrderCreated() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        TempOrderFragment fragment = new TempOrderFragment();
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
        placeTempOrderPresenter.clearCart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        placeTempOrderPresenter.detachView();
    }
}
