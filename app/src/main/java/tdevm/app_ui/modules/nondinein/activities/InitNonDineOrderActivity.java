package tdevm.app_ui.modules.nondinein.activities;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.nondinein.NonDineViewContract;
import tdevm.app_ui.modules.nondinein.fragments.NonDineCheckoutFragment;
import tdevm.app_ui.modules.nondinein.fragments.OrderPaymentTypeFragment;

public class InitNonDineOrderActivity extends AppCompatActivity implements NonDineViewContract.InitNonDineOrderView, OrderPaymentTypeFragment.OrderPaymentTypeListener{

    public static final String TAG = InitNonDineOrderActivity.class.getSimpleName();

    @Inject
    InitNonDineOrderPresenter presenter;


    @Override
    protected void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_init_non_dine_order);
        showOrderSummary();
    }

    @Override
    public void showProgressUI() {
    }

    @Override
    public void hideProgressUI() {

    }

    public void showOrderSummary(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, NonDineCheckoutFragment.newInstance());
        transaction.commit();
    }

    public void showOrderPaymentType(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order,OrderPaymentTypeFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onPaymentMethodSelected(Uri uri) {

    }
}
