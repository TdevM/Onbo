package tdevm.app_ui.modules.orders.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.orders.RestaurantOrdersViewContract;
import tdevm.app_ui.modules.orders.adapters.MyOrderItemsAdapter;
import tdevm.app_ui.root.adapters.EqualSpacingItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyOrderDetailActivity extends AppCompatActivity implements RestaurantOrdersViewContract.MyOrderDetailView {

    public static final String TAG = MyOrderDetailActivity.class.getSimpleName();

    @BindView(R.id.tv_merged_order_id)
    TextView orderId;
    @BindView(R.id.tv_merged_order_table_no)
    TextView tableNo;
    @BindView(R.id.tv_merged_subtotal)
    TextView subTotal;
    @BindView(R.id.tv_merged_taxes)
    TextView taxes;
    @BindView(R.id.tv_merged_total)
    TextView total;
    @BindView(R.id.tv_merged_order_date)
    TextView tvDate;

    @Inject
    MyOrderDetailPresenter presenter;

    @BindView(R.id.toolbar_order_detail_activity)
    Toolbar toolbar;

    @BindView(R.id.rv_my_order_details)
    RecyclerView recyclerView;

    RecyclerView.LayoutManager layoutManager;
    MyOrderItemsAdapter adapter;
    FOrder fOrder;


    public MyOrderDetailActivity() {
        // Required empty public constructor
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
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        fOrder = intent.getParcelableExtra("ORDER");

        if (fOrder != null) {
            if(fOrder.getF_order_items()!=null){
                adapter = new MyOrderItemsAdapter(this, fOrder.getF_order_items());
                recyclerView.setAdapter(adapter);
                updateCardDetails(fOrder);
            }

        }



    }

    private void updateCardDetails(FOrder fOrder) {
        //tableNo.setText(String.valueOf(fOrder.getRestaurant_table().getTable_number()));
        orderId.setText(fOrder.getOrder_id());
        tvDate.setText(fOrder.getTimestamp());
        subTotal.setText(String.valueOf(Integer.parseInt(fOrder.getSubtotal()) * 0.01));
        taxes.setText(String.valueOf(Integer.parseInt(fOrder.getTaxes()) * 0.01));
        total.setText(String.valueOf(Integer.parseInt(fOrder.getGrand_total()) * 0.01));
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
