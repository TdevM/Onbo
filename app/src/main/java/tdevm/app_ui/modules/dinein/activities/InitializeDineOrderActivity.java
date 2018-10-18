package tdevm.app_ui.modules.dinein.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import javax.inject.Inject;

import retrofit2.Response;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.fragments.InitializeOrderFragment;
import tdevm.app_ui.modules.dinein.fragments.OrderSuccessFragment;


public class InitializeDineOrderActivity extends AppCompatActivity implements DineInViewContract.PlaceTempOrderView {
    public static final String TAG = InitializeDineOrderActivity.class.getSimpleName();
    public static final String ORDER_RUNNING_STATUS = "ORDER_RUNNING_STATUS";
    public static TOrder tOrder;

    @Inject
    InitDineOrderPresenterImpl placeTempOrderPresenter;

    @Override
    protected void onResume() {
        super.onResume();
        placeTempOrderPresenter.attachView(this);
        Log.d(TAG, "Temp Order onResume");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        placeTempOrderPresenter.checkCurrentOrderDetails();
        Log.d(TAG, placeTempOrderPresenter.convertCartTOJSON().toString());
        Log.d(TAG, "Temp Order onCreate");
        //setStatusBarGradient(this);
        setContentView(R.layout.activity_place_temp_order);
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.drawable.gradient_6);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(android.R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(android.R.color.transparent));
            //window.setBackgroundDrawable(background);
        }
    }


    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);

    }

    @Override
    public void showGetGuestMessage() {
        Log.d(TAG, "Not running");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ORDER_RUNNING_STATUS, false);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        InitializeOrderFragment fragment = new InitializeOrderFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
    }

    @Override
    public void showGetMessage(Response<TOrder> arrayListResponse) {
        Log.d(TAG, "Running");
        tOrder = arrayListResponse.body();
        Bundle bundle = new Bundle();
        bundle.putBoolean(ORDER_RUNNING_STATUS, true);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        InitializeOrderFragment fragment = new InitializeOrderFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
    }

    @Override
    public void createOrder(int guest, String userMessage) {
        placeTempOrderPresenter.createNewOrder(guest, userMessage);

    }

    @Override
    public void addItemsToOrder(String userMessage) {
        placeTempOrderPresenter.addItemsToOrder(userMessage, tOrder);

    }

    @Override
    public void onOrderItemsAdded() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        OrderSuccessFragment fragment = new OrderSuccessFragment();
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
        placeTempOrderPresenter.clearCart();
    }

    @Override
    public void onNewOrderCreated() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        OrderSuccessFragment fragment = new OrderSuccessFragment();
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
