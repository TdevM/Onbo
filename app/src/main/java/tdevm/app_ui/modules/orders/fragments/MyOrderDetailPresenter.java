package tdevm.app_ui.modules.orders.fragments;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.orders.RestaurantOrdersPresenterContract;
import tdevm.app_ui.modules.orders.RestaurantOrdersViewContract;
import tdevm.app_ui.utils.PreferenceUtils;

public class MyOrderDetailPresenter extends BasePresenter implements RestaurantOrdersPresenterContract.MyOrderDetail {

    public static final String TAG = MyOrderDetailPresenter.class.getSimpleName();
    private PreferenceUtils preferenceUtils;
    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private RestaurantOrdersViewContract.MyOrderDetailView myOrderDetailView;

    @Inject
    public MyOrderDetailPresenter(PreferenceUtils preferenceUtils, APIService apiService) {
        this.preferenceUtils = preferenceUtils;
        this.apiService = apiService;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(RestaurantOrdersViewContract.MyOrderDetailView view) {
        this.myOrderDetailView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
