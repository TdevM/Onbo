package onbo.app.modules.orders.fragments;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import onbo.app.api.APIService;
import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.base.BasePresenter;
import onbo.app.modules.orders.RestaurantOrdersPresenterContract;
import onbo.app.modules.orders.RestaurantOrdersViewContract;
import onbo.app.utils.PreferenceUtils;

public class MyOrdersFragmentPresenter extends BasePresenter implements RestaurantOrdersPresenterContract.MyOrdersFragmentPresenter {

    public static final String TAG = MyOrdersFragmentPresenter.class.getSimpleName();

    private APIService service;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;
    private RestaurantOrdersViewContract.MyOrdersFragmentView fragmentView;

    @Inject
    public MyOrdersFragmentPresenter(APIService service, PreferenceUtils preferenceUtils) {
        this.service = service;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(RestaurantOrdersViewContract.MyOrdersFragmentView view) {
        this.fragmentView = view;
    }

    @Override
    public void fetchMyOrders(){
        Observable<Response<List<FOrder>>> observable = service.fetchMyOrders("Bearer "+ preferenceUtils.getAuthLoginToken());
        subscribe(observable, new Observer<Response<List<FOrder>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                fragmentView.showProgressUI();
            }

            @Override
            public void onNext(Response<List<FOrder>> fOrderResponse) {
                if (fOrderResponse.isSuccessful()){
                    if(fOrderResponse.body()!=null){
                        fragmentView.onMyOrderFetched(fOrderResponse.body());
                    }
                }else if(fOrderResponse.code() ==404){
                    fragmentView.onMyOrdersEmpty();
                }
            }

            @Override
            public void onError(Throwable e) {
                fragmentView.onFetchingOrdersFailure();
                e.printStackTrace();

            }

            @Override
            public void onComplete() {
                fragmentView.hideProgressUI();
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