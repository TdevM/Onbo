package app.onbo.modules.fc;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import app.onbo.api.APIService;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.base.BasePresenter;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class FCPresenter extends BasePresenter implements FCPresenterContract.FCPresenter {


    public static final String TAG = FCPresenter.class.getSimpleName();
    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    FCViewContract.FCActivityView view;

    @Inject
    public FCPresenter(APIService apiService) {
        this.apiService = apiService;
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(FCViewContract.FCActivityView view) {
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
    public void fetchFCRestaurants(String fcUUID) {
        Map<String, String> map = new HashMap<>();
        map.put("fc_uuid", fcUUID);
        Observable<Response<FoodCourt>> observable = apiService.fetchFCRestaurants(map);
        subscribe(observable, new Observer<Response<FoodCourt>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                view.showProgressUI();
            }

            @Override
            public void onNext(Response<FoodCourt> foodCourtResponse) {
                if (foodCourtResponse.code() == 200) {
                    view.onFCFetched(foodCourtResponse.body());
                } else {
                    view.onFCFetchFailure();
                }
            }

            @Override
            public void onError(Throwable e) {
                view.onFCFetchFailure();
            }

            @Override
            public void onComplete() {
                view.hideProgressUI();
            }
        });
    }
}
