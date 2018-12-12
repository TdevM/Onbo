package app.onbo.modules.dinein.fragments;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.response.v2.merged.MergedOrder;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.base.BasePresenter;
import app.onbo.modules.dinein.DineInPresenterContract;
import app.onbo.modules.dinein.DineInViewContract;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

/**
 * Created by Tridev on 08-02-2018.
 */

public class MergedOrderPresenter extends BasePresenter implements DineInPresenterContract.MergedOrderFragmentPresenter {

    private DineInViewContract.MergedOrderView view;
    private CompositeDisposable compositeDisposable;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;
    private APIService apiService;
    private Application context;


    @Inject
    public MergedOrderPresenter(PreferenceUtils preferenceUtils, CartHelper cartHelper, Application c, APIService apiService) {
        this.compositeDisposable = new CompositeDisposable();
        this.preferenceUtils = preferenceUtils;
        this.cartHelper = cartHelper;
        this.context = c;
        this.apiService = apiService;
    }


    public void fetchTempRunningOrder() {
        if (isConnectedToInternet()) {
            Map<String, String> map = new HashMap<>();
            map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
            Observable<Response<TOrder>> observable = apiService.fetchMyRunningOrder("Bearer " + preferenceUtils.getAuthLoginToken(), map);
            subscribe(observable, new Observer<Response<TOrder>>() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                    view.showProgressUI();

                }

                @Override
                public void onNext(Response<TOrder> arrayListResponse) {
                    view.showProgressUI();
                    if (arrayListResponse.code() == 200) {
                        view.onRunningOrderFetched(arrayListResponse.body());
                    } else if (arrayListResponse.code() == 404) {
                        view.showNoRunningOrder();
                    }
                }

                @Override
                public void onError(Throwable e) {
                    view.showBackendError();
                }

                @Override
                public void onComplete() {

                }
            });
        } else {
            view.showNoInternetError();

        }

    }


    public void fetchMergedOrder(TOrder tOrder) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        map.put("order_id", tOrder.getOrderId());
        Observable<Response<MergedOrder>> observable = apiService.fetchMergedOrder("Bearer " + preferenceUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<MergedOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<MergedOrder> arrayListResponse) {
                view.showProgressUI();
                if (arrayListResponse.code() == 200) {
                    view.onMergedOrderFetched(arrayListResponse.body());

                } else if (arrayListResponse.code() == 404) {
                    view.showNoRunningOrder();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.showBackendError();
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


    public boolean isConnectedToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
}
