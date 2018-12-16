package app.onbo.modules.auth.fragments;

import android.util.Log;

import com.onesignal.OneSignal;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.request.User;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.base.BasePresenter;
import app.onbo.modules.auth.AuthPresenterContract;
import app.onbo.modules.auth.AuthViewContract;
import app.onbo.utils.PreferenceUtils;

/**
 * Created by Tridev on 12-10-2017.
 */
//TODO Save encrypted token to shared prefs.
public class AuthLoginPresenter extends BasePresenter implements AuthPresenterContract.AuthLoginPresenter {

    public static final String TAG = AuthLoginPresenter.class.getSimpleName();
    private APIService apiService;
    private AuthViewContract.AuthLoginView authLoginView;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;

    @Inject
    public AuthLoginPresenter(APIService apiService, PreferenceUtils preferenceUtils) {
        this.compositeDisposable = new CompositeDisposable();
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
    }

    public void loginUser(final Long phone, final String password) {
        Observable<Response<Object>> observable = apiService.loginUser(new User(password, phone));
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                authLoginView.showProgressUI();
                compositeDisposable.add(d);
                Log.d(TAG, "Subscribed");
            }

            @Override
            public void onNext(@NonNull Response<Object> response) {
                authLoginView.hideProgressUI();
                if (response.code() == 401) {
                    Log.d(TAG, String.valueOf(response.code()));
                    authLoginView.showLoginError();
                } else if (response.code() == 200) {
                    Log.d(TAG, response.body().toString());
                    preferenceUtils.saveAuthTransaction(response.headers().get("Authorization"), phone, true);
                    OneSignal.sendTag("USER_MOBILE", String.valueOf(phone));
                    authLoginView.loginSuccess();

                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                authLoginView.hideProgressUI();
                authLoginView.showLoginError();
                Log.d(TAG, "Error");
            }

            @Override
            public void onComplete() {

            }
        });

    }


    @Override
    public void checkCurrentOrderDetails() {
        Log.d(TAG, "Checking order...");
        Map<String, String> map = new HashMap<>();
        Observable<Response<TOrder>> observable = apiService.fetchMyRunningOrder("Bearer " + preferenceUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<TOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                authLoginView.showProgressUI();
            }

            @Override
            public void onNext(Response<TOrder> arrayListResponse) {
                Log.d(TAG, "onNext RAN");
                if (arrayListResponse.isSuccessful()) {
                    if (arrayListResponse.code() == 200) {
                        authLoginView.onDineOrderRunning(arrayListResponse.body());
                        Log.d(TAG, "Add items to Order RAN");
                    }
                } else if (arrayListResponse.code() == 404) {
                    // No order running
                    Log.d(TAG, "No Order running");
                    authLoginView.onNoDineOrderRunning();
                }
            }

            @Override
            public void onError(Throwable e) {
                authLoginView.onOrderFetchFailure();
            }

            @Override
            public void onComplete() {
                authLoginView.hideProgressUI();
            }
        });
    }


    @Override
    public void fetchClosedOrder(String tOrderId) {
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_id", preferenceUtils.getScannedRestaurantId());
        map.put("t_order_id", tOrderId);
        Observable<retrofit2.Response<FOrder>> observable = apiService.fetchClosedOrder("Bearer " + preferenceUtils.getAuthLoginToken(), map);
        subscribe(observable, new Observer<Response<FOrder>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                authLoginView.showProgressUI();
            }

            @Override
            public void onNext(Response<FOrder> fOrderResponse) {
                if (fOrderResponse.isSuccessful()) {
                    if (fOrderResponse.body() != null) {
                        authLoginView.onFOrderFetched(fOrderResponse.body());
                    }
                } else {
                    authLoginView.onFOrderFetchFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                authLoginView.onFOrderFetchFailure();
            }

            @Override
            public void onComplete() {
                authLoginView.hideProgressUI();
            }
        });
    }

    @Override
    public void attachView(AuthViewContract.AuthLoginView view) {
        authLoginView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
