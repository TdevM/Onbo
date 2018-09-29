package tdevm.app_ui.modules.nondine.fragments;

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
import tdevm.app_ui.base.BasePresenterMVP;
import tdevm.app_ui.modules.nondine.NonDinePresenterContract;
import tdevm.app_ui.modules.nondine.NonDineViewContract;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.utils.PreferenceUtils;

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
        Observable<Response<FOrder>> createNDCashOrder =  apiService.createPaidNonDineOrder(preferenceUtils.getAuthLoginToken(),order);
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
