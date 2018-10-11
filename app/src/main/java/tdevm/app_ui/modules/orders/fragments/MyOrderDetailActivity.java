package tdevm.app_ui.modules.orders.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.modules.orders.RestaurantOrdersViewContract;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderDetailActivity extends AppCompatActivity implements RestaurantOrdersViewContract.MyOrderDetailView {

    public static final String TAG = MyOrderDetailActivity.class.getSimpleName();
    Unbinder unbinder;

    @Inject
    MyOrderDetailPresenter presenter;

    @BindView(R.id.toolbar_order_detail_activity)
    Toolbar toolbar;


    public MyOrderDetailActivity() {
        // Required empty public constructor
    }

    public static MyOrderDetailActivity newInstance() {
        return new MyOrderDetailActivity();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        resolveDaggerDependencies();
        setContentView(R.layout.activity_my_order_details);
        ButterKnife.bind(this);
        toolbar.setTitle("Your Order");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.attachView(this);
    }

    @Override
    public void showProgressUI() {

    }

    @Override
    public void hideProgressUI() {

    }

    @Override
    public void resolveDaggerDependencies() {
        ((AppApplication) getApplication()).getApiComponent().inject(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }
}
