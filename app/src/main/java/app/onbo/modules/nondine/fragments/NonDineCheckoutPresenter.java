package app.onbo.modules.nondine.fragments;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.request.NonDineOrder;
import app.onbo.api.models.response.v2.FOrder.Checkout;
import app.onbo.base.BasePresenter;
import app.onbo.modules.nondine.NonDinePresenterContract;
import app.onbo.modules.nondine.NonDineViewContract;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

public class NonDineCheckoutPresenter extends BasePresenter implements NonDinePresenterContract.NonDineSummaryPresenter{

    public static final String TAG = NonDineCheckoutPresenter.class.getSimpleName();

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;
    private CartHelper cart;
    private NonDineViewContract.NonDineCheckoutView summaryView;


    @Inject
    public NonDineCheckoutPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cart) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
        this.cart = cart;
    }

    @Override
    public void attachView(NonDineViewContract.NonDineCheckoutView view) {
        this.summaryView = view;
    }


    @Override
    public void checkoutOrderSummary() {
        NonDineOrder order = new NonDineOrder(preferenceUtils.getScannedRestaurantId(),cart.convertCartTOJSON().toString());
        Observable<Response<Checkout>> checkout = apiService.checkoutOrder(order);
        subscribe(checkout, new Observer<Response<Checkout>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                summaryView.showProgressUI();
            }

            @Override
            public void onNext(Response<Checkout> checkoutResponse) {
                if(checkoutResponse.isSuccessful()){
                    if(checkoutResponse.body()!=null){
                        summaryView.onCheckoutDataFetched(checkoutResponse.body());
                    }
                }else {
                    summaryView.onCheckoutResponseFailure();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                summaryView.hideProgressUI();
            }
        });
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }


}
