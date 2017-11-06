package tdevm.app_ui.modules.dinein.fragments;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInContract;

/**
 * Created by Tridev on 06-11-2017.
 */

public class SingleCuisineGridPresenter extends BasePresenter {

    private DineInContract.SingleCuisineGridView singleCuisineGridView;
    private APIService apiService;
    private Map<String,String> map;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public SingleCuisineGridPresenter(DineInContract.SingleCuisineGridView singleCuisineGridView, APIService apiService, Map<String, String> map) {
        this.singleCuisineGridView = singleCuisineGridView;
        this.apiService = apiService;
        this.map = map;
    }

    public void fetchDishesByCuisines(){
        Observable<ArrayList<DishesOfCuisine>> dishesOfCuisineObservable = apiService.fetchDishesByCuisine(map);
        subscribe(dishesOfCuisineObservable, new Observer<ArrayList<DishesOfCuisine>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(ArrayList<DishesOfCuisine> arrayList) {
              singleCuisineGridView.onDishesOfCuisinesFetched(arrayList);
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
