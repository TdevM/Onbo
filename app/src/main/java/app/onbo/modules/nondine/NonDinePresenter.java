package app.onbo.modules.nondine;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import app.onbo.api.APIService;
import app.onbo.base.BasePresenter;
import app.onbo.utils.CartHelper;
import app.onbo.utils.PreferenceUtils;

public class NonDinePresenter extends BasePresenter implements NonDinePresenterContract.NonDineActivityPresenter {


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
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
