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
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.payment.PaymentPresenterContract;
import tdevm.app_ui.modules.payment.PaymentViewContract;
import tdevm.app_ui.utils.PreferenceUtils;

public class PaymentFragmentPresenter extends BasePresenter implements PaymentPresenterContract.PaymentFragmentPresenterContract {

    public static final String TAG = PaymentFragmentPresenter.class.getSimpleName();
    private PreferenceUtils preferenceUtils;
    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private PaymentViewContract.PaymentFragmentView paymentFragmentView;


    @Inject
    public PaymentFragmentPresenter(PreferenceUtils preferenceUtils, APIService apiService) {
        this.preferenceUtils = preferenceUtils;
        this.apiService = apiService;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(PaymentViewContract.PaymentFragmentView view) {
        this.paymentFragmentView = view;
    }

    @Override
    public void fetchClosedOrder(String tOrderId, String fOrderId){
        Map<String,String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        map.put("t_order_id", tOrderId);
        map.put("order_id",fOrderId);
        Observable<retrofit2.Response<FOrder>> observable = apiService.fetchClosedOrder(preferenceUtils.getAuthLoginToken(),map);
        subscribe(observable, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                if(fOrderResponse.isSuccessful()){
                    if(fOrderResponse.body()!=null){
                        paymentFragmentView.onClosedOrderFetched(fOrderResponse.body());
                    }
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
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
