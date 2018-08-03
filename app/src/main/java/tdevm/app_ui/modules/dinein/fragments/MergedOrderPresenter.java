package tdevm.app_ui.modules.dinein.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 08-02-2018.
 */

public class MergedOrderPresenter extends BasePresenter implements DineInPresenterContract.MergedOrderFragmentPresenter {

    private DineInViewContract.MergedOrderView view;
    private CompositeDisposable compositeDisposable;
    private AuthUtils authUtils;
    private CartHelper cartHelper;
    private APIService apiService;

    @Inject
    public MergedOrderPresenter(AuthUtils authUtils, CartHelper cartHelper, APIService apiService) {
        this.compositeDisposable = new CompositeDisposable();
        this.authUtils = authUtils;
        this.cartHelper = cartHelper;
        this.apiService = apiService;
    }


    public void fetchTempRunningOrder() {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", authUtils.getScannedRestaurantId());
        Observable<Response<TOrder>> observable = apiService.fetchMyRunningOrder(authUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<TOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);

            }

            @Override
            public void onNext(Response<TOrder> arrayListResponse) {
                view.showProgressUI();
                if (arrayListResponse.isSuccessful()) {
                    view.onRunningOrderFetched(arrayListResponse.body());
                } else if (arrayListResponse.code() == 404) {
                     view.showNoRunningOrder();
                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void fetchMergedOrder(TOrder tOrder) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", authUtils.getScannedRestaurantId());
        map.put("order_id", tOrder.getOrderId());
        Observable<Response<MergedOrder>> observable = apiService.fetchMergedOrder(authUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<MergedOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<MergedOrder> arrayListResponse) {
                if (arrayListResponse.isSuccessful()) {
                    view.onMergedOrderFetched(arrayListResponse.body());

                } else if (arrayListResponse.code() == 404) {
                    view.showNoRunningOrder();
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
    public void attachView(DineInViewContract.MergedOrderView mergedOrderView) {
        this.view = mergedOrderView;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}