package app.onbo.modules.fc;

import java.util.List;

import app.onbo.api.APIService;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.base.BasePresenter;
import app.onbo.modules.fc.activities.FCPremisePresenter;
import app.onbo.utils.PreferenceUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;

public class FCListPresenter extends BasePresenter implements FCPresenterContract.FCListPresenterContract {


    public static final String TAG = FCListPresenter.class.getSimpleName();


    private APIService apiService;

    private CompositeDisposable compositeDisposable;

    FCViewContract.FCListActivity fcListActivity;

    public FCListPresenter(APIService apiService) {
        this.apiService = apiService;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void attachView(FCViewContract.FCListActivity view) {
        this.fcListActivity = view;

    }

    @Override
    public void fetchFCList(){
        Observable<Response<List<FoodCourt>>> responseObservable =  apiService.fetchAllFoodCourts();
        subscribe(responseObservable, new Observer<Response<List<FoodCourt>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<List<FoodCourt>> listResponse) {

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

    }
}
