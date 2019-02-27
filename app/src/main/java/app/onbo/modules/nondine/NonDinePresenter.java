package app.onbo.modules.nondine;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import app.onbo.api.APIService;
import app.onbo.base.BasePresenter;
import app.onbo.utils.CartHelper;
import app.onbo.utils.PreferenceUtils;

public class NonDinePresenter extends BasePresenter implements NonDinePresenterContract.NonDineActivityPresenter {


    public static final String TAG = NonDinePresenter.class.getSimpleName();

    private APIService apiService;
    private CartHelper cartHelper;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;
    private  NonDineViewContract.NonDineActivityView nonDineActivityView;


    @Inject
    public NonDinePresenter(APIService apiService, CartHelper cartHelper, PreferenceUtils preferenceUtils) {
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }




    public Integer getCartItemsCount() {
        return cartHelper.getCartTotalItems();
    }


    @Override
    public void attachView(NonDineViewContract.NonDineActivityView view) {
        this.nonDineActivityView = view;
        Log.d(TAG,"T2 RES ID:" + preferenceUtils.getScannedRestaurantId());
        Log.d(TAG,"T2 RES UUID:" +preferenceUtils.getScannedRestaurantUuid());
        Log.d(TAG,"T2 RES MODE:" +preferenceUtils.getRestaurantMode());
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
