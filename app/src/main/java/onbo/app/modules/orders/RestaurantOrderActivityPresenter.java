package onbo.app.modules.orders;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import onbo.app.api.APIService;
import onbo.app.base.BasePresenter;
import onbo.app.utils.PreferenceUtils;

public class RestaurantOrderActivityPresenter extends BasePresenter implements RestaurantOrdersPresenterContract.RestaurantOrderPresenter{

    public static final String TAG = RestaurantOrderActivityPresenter.class.getSimpleName();

    private RestaurantOrdersViewContract.RestaurantOrdersActivityView restaurantOrdersActivityView;
    private PreferenceUtils preferenceUtils;
    private APIService apiService;
    private CompositeDisposable compositeDisposable;


    @Inject
    public RestaurantOrderActivityPresenter(PreferenceUtils preferenceUtils, APIService apiService) {
        this.preferenceUtils = preferenceUtils;
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
