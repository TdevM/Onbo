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
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 06-11-2017.
 */
public class SingleCuisineGridPresenter extends BasePresenter
        implements DineInPresenterContract.SingleCuisineGridPresenter {

    public static final String TAG = SingleCuisineGridPresenter.class.getSimpleName();
    private DineInViewContract.SingleCuisineGridView singleCuisineGridView;
    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private AuthUtils authUtils;
    private CartHelper cartHelper;

    @Inject
    public SingleCuisineGridPresenter(AuthUtils authUtils, APIService apiService, CartHelper cartHelper) {
        this.compositeDisposable = new CompositeDisposable();
        this.authUtils = authUtils;
        this.cartHelper = cartHelper;
        this.apiService = apiService;
    }

    @Override
    public void fetchMenuItemsByCuisine(Map<String, String> map) {
        Observable<ArrayList<MenuItem>> dishesOfCuisineObservable = apiService.fetchMenuItemsByCuisine(authUtils.getAuthLoginToken(), map);
        subscribe(dishesOfCuisineObservable, new Observer<ArrayList<MenuItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(ArrayList<MenuItem> arrayList) {
                singleCuisineGridView.onMenuItemsFetched(arrayList);
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
    public void addItemToCart(tdevm.app_ui.api.models.cart.MenuItem menuItem, int itemTotal, String itemHash) {
        cartHelper.addItemToCart(menuItem, itemTotal, itemHash);
        cartHelper.addItemToSelection(menuItem.getItemId());
        singleCuisineGridView.updateAdapter();
        logSelections();
    }

    @Override
    public void addItemToSelection(tdevm.app_ui.api.models.cart.MenuItem menuItem) {
        cartHelper.addItemToSelection(menuItem.getItemId());
        singleCuisineGridView.updateAdapter();
        // logSelections();
    }

    @Override
    public void updateCartItem(tdevm.app_ui.api.models.cart.MenuItem menuItem, int itemTotal, String itemHash) {

    }


//    @Override
//    public void addItemToCart(DishesOfCuisine dishesOfCuisine) {
////        cartHelper.addItemToCart(dishesOfCuisine);
////        cartHelper.addItemToSelection(dishesOfCuisine);
////        singleCuisineGridView.updateAdapter();
////        logSelections();
//    }
//
//    @Override
//    public void addItemToSelection(DishesOfCuisine dishesOfCuisine) {
////        cartHelper.addItemToSelection(dishesOfCuisine);
////        singleCuisineGridView.updateAdapter();
////        logSelections();
//    }
//
//
//    @Override
//    public void updateCartItem(DishesOfCuisine dishesOfCuisine) {
////      cartHelper.updateCartItem(dishesOfCuisine);
////      cartHelper.updateSelectionItem(dishesOfCuisine);
////      singleCuisineGridView.updateAdapter();
////      logSelections();
//    }
//
//    @Override
//    public void addCustomizableItemToCart(DishesOfCuisine dishesOfCuisine, int operationFlag) {
//
//    }
//
//    @Override
//    public void addDishVariantItemToCart(DishesOfCuisine selectedDish, DishesOfCuisine parentDish) {
////        cartHelper.addItemToCart(selectedDish);
////        cartHelper.addItemToSelection(parentDish);
////        singleCuisineGridView.updateAdapter();
//    }

    @Override
    public void attachView(DineInViewContract.SingleCuisineGridView view) {
        singleCuisineGridView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    public void logSelections() {
        Log.d(TAG, "Log Started");
        List<CartSelection> selection = cartHelper.getCartSelections();
        List<CartItem> cartItems = cartHelper.getCartItems();

        if (selection != null && cartItems != null) {
            for (int i = 0; i < selection.size(); i++) {
                Log.d(TAG, "Item selection id:" + String.valueOf(selection.get(i).getSelectionItemId()));
                Log.d(TAG, "Item selection qty added:" + String.valueOf(selection.get(i).getQty()));
            }

            for (int i = 0; i < cartItems.size(); i++) {
                Log.d(TAG, "Cart Item:" + cartItems.get(i).getMenuItem().getItemName());
                Log.d(TAG, "Cart Item QTY:" + cartItems.get(i).getQuantity());
                Log.d(TAG, "Cart Item Total:" + cartItems.get(i).getPrice());
            }
        }
    }
}
