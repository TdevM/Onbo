package tdevm.app_ui.modules.dinein.fragments;

import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.cart.model.Cart;
import tdevm.app_ui.api.cart.util.CartHelper;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInContract;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 06-11-2017.
 */

public class SingleCuisineGridPresenter extends BasePresenter {

    public static final String TAG = SingleCuisineGridPresenter.class.getSimpleName();
    private DineInContract.SingleCuisineGridView singleCuisineGridView;
    private APIService apiService;
    private Map<String,String> map;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Cart cart = CartHelper.getCart();
    private AuthUtils authUtils;
    public SingleCuisineGridPresenter(DineInContract.SingleCuisineGridView singleCuisineGridView, AuthUtils authUtils,APIService apiService, Map<String, String> map) {
        this.singleCuisineGridView = singleCuisineGridView;
        this.authUtils = authUtils;
        this.apiService = apiService;
        this.map = map;
    }

    public void fetchDishesByCuisines(){
        Observable<ArrayList<DishesOfCuisine>> dishesOfCuisineObservable = apiService.fetchDishesByCuisine(authUtils.getAuthLoginToken(),map);
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

    public void fetchVariantsOfADish(Long dishId){
        map.put("dish_id",String.valueOf(dishId));
        Observable<ArrayList<DishesOfCuisine>> dishesOfCuisineObservable = apiService.fetchDishVariantsByCuisines(authUtils.getAuthLoginToken(),map);
        subscribe(dishesOfCuisineObservable, new Observer<ArrayList<DishesOfCuisine>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(ArrayList<DishesOfCuisine> dishesOfCuisines) {
               singleCuisineGridView.onDishVariantsFetched(dishesOfCuisines);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public void addToCart(DishesOfCuisine dishesOfCuisine){
        Log.d(TAG,dishesOfCuisine.getDish_name());
    }

    public void updateCart(){

    }
}
