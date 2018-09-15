package tdevm.app_ui.modules.nondine.activities;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.modules.nondine.fragments.NonDineCheckoutFragment;
import tdevm.app_ui.modules.nondine.fragments.OrderPaymentTypeFragment;
import tdevm.app_ui.modules.nondine.fragments.PlaceNDOrderCashFragment;

public class InitNonDineOrderActivity extends AppCompatActivity implements NonDineViewContract.InitNonDineOrderView,
        OrderPaymentTypeFragment.OrderPaymentTypeListener, PlaceNDOrderCashFragment.OnFragmentInteractionListener{

    public static final String TAG = InitNonDineOrderActivity.class.getSimpleName();

    @Inject
    InitNonDineOrderPresenter presenter;


    @BindView(R.id.pb_non_dine_init)
    ProgressBar progressBar;

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
        ButterKnife.bind(this);
        showOrderSummary();
    }

    @Override
    public void showProgressUI() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressUI() {
        progressBar.setVisibility(View.GONE);
    }

    public void showOrderSummary(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, NonDineCheckoutFragment.newInstance());
        transaction.commit();
    }

    public void showOrderPaymentType(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, OrderPaymentTypeFragment.newInstance());
        transaction.commit();
    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }


    public void createNonDineOrderCash(){
        presenter.createCashNDOrder();
    }

    public void showDigitalPaymentOptions(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    @Override
    public void onPaymentMethodSelected(Uri uri) {

    }

    @Override
    public void onNDCashOrderCreated(FOrder fOrder) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout_place_non_dine_order, PlaceNDOrderCashFragment.newInstance(fOrder));
        transaction.commit();
    }

    @Override
    public void onOrderCreationFailure() {
        Toast.makeText(this, "Failed to create order.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
