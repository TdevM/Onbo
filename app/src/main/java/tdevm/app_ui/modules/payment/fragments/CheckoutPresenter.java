package tdevm.app_ui.modules.payment.fragments;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.payment.PaymentPresenterContract;
import tdevm.app_ui.modules.payment.PaymentViewContract;
import tdevm.app_ui.utils.PreferenceUtils;

public class CheckoutPresenter extends BasePresenter implements PaymentPresenterContract.CheckoutPresenterContract {

    public static final String TAG = CheckoutPresenter.class.getSimpleName();
    private CompositeDisposable compositeDisposable;

    private PreferenceUtils preferenceUtils;
    private APIService service;
    private PaymentViewContract.CheckoutFragmentView view;

    @Inject
    public CheckoutPresenter(PreferenceUtils preferenceUtils, APIService apiService) {
        this.preferenceUtils = preferenceUtils;
        this.service = apiService;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void closeRunningOrder(String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        map.put("order_id", orderId);
        Observable<Response<FOrder>> closeRunningOrder = service.closeRunningOrder("Bearer " + preferenceUtils.getAuthLoginToken(), map);
        subscribe(closeRunningOrder, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                view.showCloseProgressUI();

            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                if (fOrderResponse.code() == 200) {
                    if (fOrderResponse.body() != null) {
                        view.onOrderClosed(fOrderResponse.body());
                    }
                } else {
                    view.onOrderClosedFailure();
                }

            }

            @Override
            public void onError(Throwable e) {
                view.onOrderClosedFailure();
            }

            @Override
            public void onComplete() {
                view.hideCloseProgressUI();
            }
        });
    }

    public void fetchMergedOrder(String orderId) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        map.put("order_id", orderId);
        Observable<Response<MergedOrder>> observable = service.fetchMergedOrder("Bearer " + preferenceUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<MergedOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                view.showProgressUI();

            }

            @Override
            public void onNext(Response<MergedOrder> arrayListResponse) {
                view.showProgressUI();
                if (arrayListResponse.isSuccessful()) {
                    view.onMergedOrderFetched(arrayListResponse.body());
                } else if (arrayListResponse.code() == 404) {
                    //view.showNoRunningOrder();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                view.hideProgressUI();
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

    @Override
    public void attachView(PaymentViewContract.CheckoutFragmentView view) {
        this.view = view;
    }


}
