package tdevm.app_ui.modules.dinein.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.Cuisine;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInContract;

/**
 * Created by Tridev on 06-11-2017.
 */

public class DishMenuPresenter extends BasePresenter {

    public static final String TAG = DishMenuPresenter.class.getSimpleName();
    private APIService apiService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    private DineInContract.DishMenuView dishMenuView;

    public DishMenuPresenter(APIService apiService, DineInContract.DishMenuView dishMenuView) {
        this.apiService = apiService;
        this.dishMenuView = dishMenuView;
    }

    public void FetchAllCuisines(Map<String,String> map) {
        Observable<ArrayList<Cuisine>> fetchCuisines = apiService.fetchCuisines(map);
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
}
