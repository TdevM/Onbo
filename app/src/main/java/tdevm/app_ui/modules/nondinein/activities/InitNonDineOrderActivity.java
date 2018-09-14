package tdevm.app_ui.modules.nondinein.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import javax.inject.Inject;

import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.nondinein.NonDineViewContract;
import tdevm.app_ui.modules.nondinein.fragments.OrderPaymentTypeFragment;

public class InitNonDineOrderActivity extends AppCompatActivity implements NonDineViewContract.InitNonDineOrderView, OrderPaymentTypeFragment.OrderPaymentTypeListener{

    public static final String TAG = InitNonDineOrderActivity.class.getSimpleName();

    @Inject
    InitNonDineOrderPresenter presenter;


    @Override
    protected void onResume() {
        presenter.attachView(this);
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_init_non_dine_order);
        presenter.test();
    }

    @Override
    public void showProgressUI() {
        Toast.makeText(this, "Test Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void hideProgressUI() {

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
