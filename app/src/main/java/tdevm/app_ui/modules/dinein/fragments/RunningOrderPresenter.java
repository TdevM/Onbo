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
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 08-02-2018.
 */

public class RunningOrderPresenter extends BasePresenter implements DineInPresenterContract.RunningOrderFragmentPresenter {

    private DineInViewContract.RunningOrderView view;
    private CompositeDisposable compositeDisposable;
    private AuthUtils authUtils;
    private CartHelper cartHelper;
    private APIService apiService;

    @Inject
    public RunningOrderPresenter(AuthUtils authUtils, CartHelper cartHelper, APIService apiService) {
        this.compositeDisposable = new CompositeDisposable();
        this.authUtils = authUtils;
        this.cartHelper = cartHelper;
        this.apiService = apiService;
    }


    public void fetchTempRunningOrder(){
        Map<String,String> map = new HashMap<>();
        map.put("restaurant_id",authUtils.getScannedRestaurantId());
        Observable<Response<TOrder>> observable = apiService.fetchMyRunningOrder(authUtils.getAuthLoginToken(),map);
        subscribe(observable, new Observer<Response<TOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<TOrder> arrayListResponse) {
                if(arrayListResponse.isSuccessful()){
                   // view.onTempOrderFetched(arrayListResponse.body());
                }else if(arrayListResponse.code() ==404){
                  //  view.showNoRunningOrder();
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


    @Override
    public void attachView(DineInViewContract.RunningOrderView runningOrderView) {
        this.view = runningOrderView;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
