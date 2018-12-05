package app.onbo.modules.nondine.fragments;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.request.NonDineOrder;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.base.BasePresenter;
import app.onbo.modules.nondine.NonDinePresenterContract;
import app.onbo.modules.nondine.NonDineViewContract;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

public class OrderPaymentTypePresenter extends BasePresenter implements NonDinePresenterContract.OrderPaymentTypePresenter {


    public static final String TAG = OrderPaymentTypePresenter.class.getSimpleName();

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;
    private CartHelper cart;

    private NonDineViewContract.OrderPaymentTypeView paymentTypeView;

    @Inject
    public OrderPaymentTypePresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cart) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.cart = cart;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(NonDineViewContract.OrderPaymentTypeView view) {
        this.paymentTypeView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void createCashNDOrder() {
        NonDineOrder order = new NonDineOrder(preferenceUtils.getScannedRestaurantId(), "This is a hardcoded text", cart.convertCartTOJSON().toString());
        Observable<Response<FOrder>> createNDCashOrder = apiService.createUnpaidNonDineOrder("Bearer " + preferenceUtils.getAuthLoginToken(), order);
        subscribe(createNDCashOrder, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                paymentTypeView.showProgressUI();
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                paymentTypeView.showProgressUI();
                if (fOrderResponse.isSuccessful()) {
                    if (fOrderResponse.body() != null) {
                        paymentTypeView.onNDCashOrderCreated(fOrderResponse.body());
                    }
                } else {
                    paymentTypeView.onOrderCreationFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                paymentTypeView.onOrderCreationFailure();
            }

            @Override
            public void onComplete() {
                paymentTypeView.hideProgressUI();
            }
        });
    }

    @Override
    public void createPaidNDOrder() {
        NonDineOrder order = new NonDineOrder(preferenceUtils.getScannedRestaurantId(), "This is a hardcoded text", cart.convertCartTOJSON().toString());
        Observable<Response<FOrder>> createNDCashOrder = apiService.createPaidNonDineOrder("Bearer " + preferenceUtils.getAuthLoginToken(), order);
        subscribe(createNDCashOrder, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                paymentTypeView.showProgressUI();
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {

                if (fOrderResponse.code() == 200) {
                    if (fOrderResponse.body() != null) {
                        paymentTypeView.onNDPaidOrderCreated(fOrderResponse.body());
                    }
                } else {
                    paymentTypeView.onOrderCreationFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                paymentTypeView.onOrderCreationFailure();
            }

            @Override
            public void onComplete() {
                //paymentTypeView.hideProgressUI();
            }
        });
    }
}

