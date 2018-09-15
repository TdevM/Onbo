package tdevm.app_ui.modules.nondine.activities;

import android.util.Log;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.request.NonDineOrder;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.nondine.NonDinePresenterContract;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

public class InitNonDineOrderPresenter extends BasePresenter implements NonDinePresenterContract.InitNonDineOrderPresenter{

    public static final String TAG = InitNonDineOrderPresenter.class.getSimpleName();

    private APIService apiService;
    private AuthUtils authUtils;
    private CompositeDisposable compositeDisposable;
    private CartHelper cart;

    private NonDineViewContract.InitNonDineOrderView nonDineOrderView;


    @Inject
    public InitNonDineOrderPresenter(APIService apiService, AuthUtils authUtils, CartHelper cart) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.cart = cart;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(NonDineViewContract.InitNonDineOrderView view) {
        this.nonDineOrderView = view;
        Log.d(TAG,"Attach view called for presenter");
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
        NonDineOrder order = new NonDineOrder(authUtils.getScannedRestaurantId(),"This is a hardcoded text",cart.convertCartTOJSON().toString());
        Observable<Response<FOrder>> createNDCashOrder =  apiService.createUnpaidNonDineOrder(authUtils.getAuthLoginToken(),order);
        subscribe(createNDCashOrder, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                nonDineOrderView.showProgressUI();
                if(fOrderResponse.isSuccessful()){
                    if(fOrderResponse.body()!=null){
                        nonDineOrderView.onNDCashOrderCreated(fOrderResponse.body());
                    }
                }else {
                    nonDineOrderView.onOrderCreationFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                nonDineOrderView.onOrderCreationFailure();
            }

            @Override
            public void onComplete() {
                nonDineOrderView.hideProgressUI();
            }
        });
    }

}
