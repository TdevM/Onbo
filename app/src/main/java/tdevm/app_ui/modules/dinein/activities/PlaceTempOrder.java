package tdevm.app_ui.modules.dinein.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.modules.dinein.fragments.DishMenuFragment;
import tdevm.app_ui.modules.dinein.fragments.InitializeTempOrderFragment;


public class PlaceTempOrder extends AppCompatActivity implements DineInViewContract.PlaceTempOrderView{
    public static final String TAG = PlaceTempOrder.class.getSimpleName();

    @Inject
    PlaceTempOrderPresenterImpl placeTempOrderPresenter;

    @Override
    protected void onResume() {
        resolveDaggerDependencies();
        placeTempOrderPresenter.attachView(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_temp_order);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        InitializeTempOrderFragment fragment = new InitializeTempOrderFragment();
        transaction.replace(R.id.frame_layout_place_temp_order, fragment);
        transaction.commit();

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
    public void onOrderDetailsFetched(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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
