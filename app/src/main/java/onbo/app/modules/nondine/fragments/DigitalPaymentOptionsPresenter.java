package onbo.app.modules.nondine.fragments;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import onbo.app.api.APIService;
import onbo.app.api.models.request.NonDineOrder;
import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.base.BasePresenter;
import onbo.app.modules.nondine.NonDinePresenterContract;
import onbo.app.modules.nondine.NonDineViewContract;
import onbo.app.utils.CartHelper;
import onbo.app.utils.PreferenceUtils;

public class DigitalPaymentOptionsPresenter extends BasePresenter implements NonDinePresenterContract.DigitalPaymentOptionPresenter {

    public static final String TAG = DigitalPaymentOptionsPresenter.class.getSimpleName();


    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;


    private NonDineViewContract.DigitalPaymentOptionView view;

    @Inject
    public DigitalPaymentOptionsPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.cartHelper = cartHelper;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(NonDineViewContract.DigitalPaymentOptionView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }


    @Override
    public void createPaidNDOrder() {
        NonDineOrder order = new NonDineOrder(preferenceUtils.getScannedRestaurantId(),"This is a hardcoded text",cartHelper.convertCartTOJSON().toString());
        Observable<Response<FOrder>> createNDCashOrder =  apiService.createPaidNonDineOrder("Bearer "+ preferenceUtils.getAuthLoginToken(),order);
        subscribe(createNDCashOrder, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                view.showProgressUI();
                if(fOrderResponse.isSuccessful()){
                    if(fOrderResponse.body()!=null){
                        view.onNDPaidOrderCreated(fOrderResponse.body());
                    }
                }else {
                    view.onOrderCreationFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onOrderCreationFailure();
            }

            @Override
            public void onComplete() {
                view.hideProgressUI();
            }
        });
    }
}
