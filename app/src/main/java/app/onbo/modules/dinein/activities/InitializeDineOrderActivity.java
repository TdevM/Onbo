package app.onbo.modules.dinein.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.appsee.Appsee;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import app.onbo.OnboApplication;
import app.onbo.R;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.modules.dinein.DineInActivity;
import app.onbo.modules.dinein.DineInViewContract;
import app.onbo.modules.dinein.fragments.InitializeOrderFragment;
import app.onbo.modules.dinein.fragments.ItemsAddedSuccessFragment;
import app.onbo.modules.dinein.fragments.OrderSuccessFragment;
import app.onbo.modules.orders.callback.CartBadgeListener;


public class InitializeDineOrderActivity extends AppCompatActivity
        implements DineInViewContract.PlaceTempOrderView, CartBadgeListener {
    public static final String TAG = InitializeDineOrderActivity.class.getSimpleName();
    public static final String ORDER_RUNNING_STATUS = "ORDER_RUNNING_STATUS";
    public static TOrder tOrder;

    @Inject
    InitDineOrderPresenterImpl placeTempOrderPresenter;

    @BindView(R.id.pb_t1_init_order)
    ProgressBar progressBar;

    Restaurant restaurant;

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "Temp Order onResume");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_place_temp_order);
        Appsee.start();
        ButterKnife.bind(this);
        placeTempOrderPresenter.attachView(this);
        placeTempOrderPresenter.checkCurrentOrderDetails();

        restaurant = getIntent().getParcelableExtra("RESTAURANT");
        Log.d(TAG, placeTempOrderPresenter.convertCartTOJSON().toString());
        Log.d(TAG, "Temp Order onCreate");
        //setStatusBarGradient(this);

    }

    @Override
    public void showProgressUI() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
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
        ((OnboApplication) getApplication()).getApiComponent().inject(this);

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
        bundle.putParcelable("T_ORDER", arrayListResponse.body());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        InitializeOrderFragment fragment = new InitializeOrderFragment();
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
    }

    @Override
    public void onOrderItemsAdded(TOrder tOrder) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        ItemsAddedSuccessFragment fragment = new ItemsAddedSuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("T_ORDER", tOrder);
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
        placeTempOrderPresenter.clearCart();
    }

    @Override
    public void onNewOrderCreated(TOrder tOrder) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        OrderSuccessFragment fragment = new OrderSuccessFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("T_ORDER", tOrder);
        fragment.setArguments(bundle);
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();
        placeTempOrderPresenter.clearCart();
    }


    public void showRunningOrderFragment() {
        Intent intent = new Intent(InitializeDineOrderActivity.this, DineInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        intent.putExtra("FRAGMENT_TO_OPEN", "RUNNING_ORDER_FRAGMENT");
        intent.putExtra("RESTAURANT", restaurant);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        placeTempOrderPresenter.detachView();
    }

//    @Override
//    public void onBackPressed() {
////        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout_place_temp_order);
////        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
////            super.onBackPressed();
////        }
//    }

    @Override
    public void onCartItemUpdated(int count) {

    }
}
