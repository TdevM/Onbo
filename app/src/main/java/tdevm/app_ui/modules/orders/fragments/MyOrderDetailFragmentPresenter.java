package tdevm.app_ui.modules.orders.fragments;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.orders.RestaurantOrdersPresenterContract;
import tdevm.app_ui.modules.orders.RestaurantOrdersViewContract;
import tdevm.app_ui.utils.PreferenceUtils;

public class MyOrderDetailFragmentPresenter extends BasePresenter implements RestaurantOrdersPresenterContract.MyOrderDetailFragment {

    public static final String TAG = MyOrderDetailFragmentPresenter.class.getSimpleName();
    private PreferenceUtils preferenceUtils;
    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private RestaurantOrdersViewContract.MyOrderDetailFragmentView myOrderDetailFragmentView;

    @Inject
    public MyOrderDetailFragmentPresenter(PreferenceUtils preferenceUtils, APIService apiService) {
        this.preferenceUtils = preferenceUtils;
        this.apiService = apiService;
    }

    @Override
    public void attachView(RestaurantOrdersViewContract.MyOrderDetailFragmentView view) {
        this.myOrderDetailFragmentView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
