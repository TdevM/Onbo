package app.onbo.modules.dinein.bottomsheets;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.response.v2.reviews.DishReviews;
import app.onbo.base.BasePresenter;
import app.onbo.modules.dinein.DineInPresenterContract;
import app.onbo.modules.dinein.DineInViewContract;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

/**
 * Created by Tridev on 14-02-2018.
 */

public class DishReviewsSheetPresenter extends BasePresenter implements DineInPresenterContract.DishReviewSheet {

    public static final String TAG = DishReviewsSheetPresenter.class.getSimpleName();

    private DineInViewContract.DishReviewsSheetView sheetView;
    private APIService apiService;
    private CartHelper cartHelper;
    private PreferenceUtils preferenceUtils;
    private CompositeDisposable compositeDisposable;

    @Inject
    public DishReviewsSheetPresenter(APIService apiService, CartHelper cartHelper, PreferenceUtils preferenceUtils) {
        this.apiService = apiService;
        this.cartHelper = cartHelper;
        this.preferenceUtils = preferenceUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void fetchMenuItemReview(Long dishId) {
        Map<String,String> map = new HashMap<>();
        map.put("dish_id",String.valueOf(dishId.intValue()));
        Observable<Response<ArrayList<DishReviews>>> responseObservable = apiService.fetchDishReviewsById(map);
        subscribe(responseObservable, new Observer<Response<ArrayList<DishReviews>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<ArrayList<DishReviews>> arrayListResponse) {
                if(arrayListResponse.isSuccessful()){
                    Log.d(TAG,arrayListResponse.body().size()+"Reviews fetched");
                    sheetView.onDishReviewsFetched(arrayListResponse.body());
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
    public void attachView(DineInViewContract.DishReviewsSheetView view) {
        this.sheetView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
