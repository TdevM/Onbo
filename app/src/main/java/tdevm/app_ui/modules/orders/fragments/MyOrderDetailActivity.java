package tdevm.app_ui.modules.orders.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import tdevm.app_ui.AppApplication;
import tdevm.app_ui.R;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.modules.orders.RestaurantOrdersViewContract;
import tdevm.app_ui.modules.orders.adapters.MyOrderItemsAdapter;
import tdevm.app_ui.utils.GeneralUtils;

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

    @BindView(R.id.iv_my_order_details_order_status)
    ImageView orderStatusImage;

    @BindView(R.id.tv_my_order_details_order_status)
    TextView orderStatus;

    @BindView(R.id.show_table_text)
    TextView showTable;

    @BindView(R.id.tv_my_order_details_restaurant_address)
    TextView restaurantAddress;

    @BindView(R.id.tv_my_order_details_restaurant_name)
    TextView restaurantName;

    @BindView(R.id.tv_my_order_details_restaurant_type)
    TextView restaurantType;

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
        toolbar.setTitle("Order Details");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Intent intent = getIntent();
        fOrder = intent.getParcelableExtra("ORDER");

        if (fOrder != null) {
            if (fOrder.getF_order_items() != null) {
                adapter = new MyOrderItemsAdapter(this, fOrder.getF_order_items());
                recyclerView.setAdapter(adapter);
                updateCardDetails(fOrder);
            }

        }


    }

    private void updateCardDetails(FOrder fOrder) {
        //tableNo.setText(String.valueOf(fOrder.getRestaurant_table().getTable_number()));
        if (fOrder.getRestaurant_table() != null) {
            showTable.setVisibility(View.VISIBLE);
            tableNo.setText(this.getString(R.string.show_number_pound_symbol, (String.valueOf(fOrder.getRestaurant_table().getTable_number()))));
        } else {
            showTable.setVisibility(View.GONE);
            tableNo.setVisibility(View.GONE);
        }
        restaurantName.setText(fOrder.getRestaurant().getRestaurant_name());
        if (fOrder.getRestaurant().getRestaurant_mode() != null) {
            if (fOrder.getRestaurant().getRestaurant_mode().equals("DINE_IN")) {
                restaurantType.setText(getString(R.string.dine_in_2));
            } else {
                restaurantType.setText(getString(R.string.quick_serve_2));
            }
        }
        orderId.setText(this.getString(R.string.show_number_pound_symbol, fOrder.getOrder_id()));

        tvDate.setText(GeneralUtils.parseTime(fOrder.getTimestamp()));
        restaurantAddress.setText(fOrder.getRestaurant().getAddress_complete());
        subTotal.setText(this.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(fOrder.getSubtotal())));
        taxes.setText(this.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(fOrder.getTaxes())));
        total.setText(this.getString(R.string.rupee_symbol, GeneralUtils.parseStringDouble(fOrder.getGrand_total())));

        if (fOrder.getStatus() != null)
            switch (fOrder.getStatus()) {
                case "COMPLETED":
                    orderStatusImage.setImageResource(R.drawable.ic_filled_circle_completed);
                    orderStatus.setText(this.getString(R.string.order_status_completed));
                    break;
                case "PAYMENT_PENDING":
                    orderStatusImage.setImageResource(R.drawable.ic_filled_circle_payment_pending);
                    orderStatus.setText(this.getString(R.string.order_status_pending_payment));
                    //GeneralUtils.animate(orderStatusImage);
                    break;
                case "PREPARING":
                    orderStatusImage.setImageResource(R.drawable.ic_filled_circle_preparing);
                    orderStatus.setText(this.getString(R.string.order_status_preparing));
                   // GeneralUtils.animate(orderStatusImage);
                    break;
                case "CANCELLED":
                    orderStatusImage.setImageResource(R.drawable.ic_filled_circle_payment_failed_cancelled);
                    orderStatus.setText(this.getString(R.string.order_status_cancelled));
                    break;
                case "PAYMENT_FAILED":
                    orderStatusImage.setImageResource(R.drawable.ic_filled_circle_payment_failed_cancelled);
                    orderStatus.setText(this.getString(R.string.order_status_payment_failed));
                    break;
                case "PREPARED":
                    orderStatusImage.setImageResource(R.drawable.ic_filled_circle_completed);
                    orderStatus.setText(this.getString(R.string.order_status_prepared));
                    break;

            }
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
