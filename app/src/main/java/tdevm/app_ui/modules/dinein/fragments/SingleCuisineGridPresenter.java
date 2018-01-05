package tdevm.app_ui.modules.dinein.fragments;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 06-11-2017.
 */
public class SingleCuisineGridPresenter extends BasePresenter
        implements DineInPresenterContract.SingleCuisineGridPresenter{

    public static final String TAG = SingleCuisineGridPresenter.class.getSimpleName();
    private DineInViewContract.SingleCuisineGridView singleCuisineGridView;
    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private AuthUtils authUtils;
    private CartHelper cartHelper;

    @Inject
    public SingleCuisineGridPresenter(AuthUtils authUtils, APIService apiService, CartHelper cartHelper) {
        compositeDisposable = new CompositeDisposable();
        this.authUtils = authUtils;
        this.cartHelper = cartHelper;
        this.apiService = apiService;
    }

    @Override
    public void fetchDishesByCuisines(Map<String,String> map){
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

    @Override
    public void fetchVariantsOfADish(Map<String,String> map, DishesOfCuisine variantsToFetch){
        Observable<ArrayList<DishesOfCuisine>> dishesOfCuisineObservable = apiService.fetchDishVariantsByCuisines(authUtils.getAuthLoginToken(),map);
        subscribe(dishesOfCuisineObservable, new Observer<ArrayList<DishesOfCuisine>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(ArrayList<DishesOfCuisine> dishesOfCuisines) {
               singleCuisineGridView.onDishVariantsFetched(dishesOfCuisines,variantsToFetch);
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
    public void addItemToCart(DishesOfCuisine dishesOfCuisine) {
        cartHelper.addItemToCart(dishesOfCuisine);
        cartHelper.addItemToSelection(dishesOfCuisine);
        logSelections();
    }

    @Override
    public void updateCartItem(DishesOfCuisine dishesOfCuisine) {
      cartHelper.updateCartItem(dishesOfCuisine);
      cartHelper.updateSelectionItem(dishesOfCuisine);
      logSelections();
    }

    @Override
    public void addCustomizableItemToCart(DishesOfCuisine dishesOfCuisine, int operationFlag) {

    }

    @Override
    public void attachView(DineInViewContract.SingleCuisineGridView view) {
        singleCuisineGridView = view;
    }

    @Override
    public void detachView() {
      compositeDisposable.clear();
      compositeDisposable.dispose();
    }
    public void logSelections() {
        Log.d(TAG,"Log Started");
        List<CartSelection> selection = cartHelper.getCartSelections();
        List<CartItem> cartItems = cartHelper.getCartItems();

        for (int i = 0; i < selection.size(); i++) {
            Log.d(TAG, "Dish selection made:" + selection.get(i).getDishesOfCuisine().getDish_name());
            Log.d(TAG, "Dish qty added:" + String.valueOf(selection.get(i).getQty()));
        }

        for (int i = 0; i < cartItems.size(); i++) {
            Log.d(TAG, "Cart Item:" + cartItems.get(i).getDishesOfCuisine().getDish_name());
            Log.d(TAG, "Item QTY:" + cartItems.get(i).getQuantity());
            Log.d(TAG,"Item Total:"+ cartItems.get(i).getPrice());
        }
    }
}
