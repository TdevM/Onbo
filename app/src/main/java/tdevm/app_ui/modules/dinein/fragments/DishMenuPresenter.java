package tdevm.app_ui.modules.dinein.fragments;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.v2.Cuisine;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 06-11-2017.
 */

public class DishMenuPresenter extends BasePresenter implements DineInPresenterContract.DishMenuPresenter{

    public static final String TAG = DishMenuPresenter.class.getSimpleName();
    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private DineInViewContract.DishMenuView dishMenuView;

    private AuthUtils authUtils;

    @Inject
    public DishMenuPresenter(APIService apiService, AuthUtils authUtils) {
        this.apiService = apiService;
        this.authUtils = authUtils;
        this.compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void FetchAllCuisines(Map<String,String> map) {
        Observable<ArrayList<Cuisine>> fetchCuisines = apiService.fetchCuisines(authUtils.getAuthLoginToken(),map);
        subscribe(fetchCuisines, new Observer<ArrayList<Cuisine>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(ArrayList<Cuisine> cuisines) {
                dishMenuView.onCuisinesFetched(cuisines);
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
    public void attachView(DineInViewContract.DishMenuView view) {
      dishMenuView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
