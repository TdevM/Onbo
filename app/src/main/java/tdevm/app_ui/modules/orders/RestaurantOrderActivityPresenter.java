package tdevm.app_ui.modules.orders;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.AuthUtils;

public class RestaurantOrderActivityPresenter extends BasePresenter implements RestaurantOrdersPresenterContract.RestaurantOrderPresenter{

    public static final String TAG = RestaurantOrderActivityPresenter.class.getSimpleName();

    private RestaurantOrdersViewContract.RestaurantOrdersActivityView restaurantOrdersActivityView;
    private AuthUtils authUtils;
    private APIService apiService;
    private CompositeDisposable compositeDisposable;


    @Inject
    public RestaurantOrderActivityPresenter(AuthUtils authUtils, APIService apiService) {
        this.authUtils = authUtils;
        this.apiService = apiService;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(RestaurantOrdersViewContract.RestaurantOrdersActivityView view) {
        this.restaurantOrdersActivityView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
