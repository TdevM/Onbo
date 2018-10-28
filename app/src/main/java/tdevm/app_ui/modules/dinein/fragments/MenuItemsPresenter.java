package tdevm.app_ui.modules.dinein.fragments;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.PreferenceUtils;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.utils.CartListener;

/**
 * Created by Tridev on 06-11-2017.
 */
public class MenuItemsPresenter extends BasePresenter
        implements DineInPresenterContract.SingleCuisineGridPresenter, CartListener {

    public static final String TAG = MenuItemsPresenter.class.getSimpleName();
    private DineInViewContract.SingleCuisineGridView singleCuisineGridView;
    private APIService apiService;
    private CompositeDisposable compositeDisposable;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;

    @Inject
    public MenuItemsPresenter(PreferenceUtils preferenceUtils, APIService apiService, CartHelper cartHelper) {
        this.compositeDisposable = new CompositeDisposable();
        this.preferenceUtils = preferenceUtils;
        this.cartHelper = cartHelper;
        this.apiService = apiService;
        this.cartHelper.setCartListener(this);
    }

    @Override
    public void fetchMenuItemsByCuisine(Map<String, String> map) {
        Observable<ArrayList<MenuItem>> dishesOfCuisineObservable = apiService.fetchMenuItemsByCuisine(preferenceUtils.getAuthLoginToken(), map);
        subscribe(dishesOfCuisineObservable, new Observer<ArrayList<MenuItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(ArrayList<MenuItem> arrayList) {

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
    public void fetchMenuItems(Map<String, String> map) {
        Observable<Response<List<CuisineMenuItems>>> cuisineItem = apiService.fetchMenuItems(preferenceUtils.getAuthLoginToken(), map);
        subscribe(cuisineItem, new Observer<Response<List<CuisineMenuItems>>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                singleCuisineGridView.showProgressUI();
            }

            @Override
            public void onNext(Response<List<CuisineMenuItems>> listResponse) {
                Log.d(TAG,"SUccess response");
                if (listResponse.isSuccessful()){
                    if(listResponse.code() == 200){
                        singleCuisineGridView.onMenuItemsFetchedV2(listResponse.body());
                        Log.d(TAG,"Presenter fetch menu V2");
                    }
                }else {
                    singleCuisineGridView.onMenuItemFetchFailure();
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                singleCuisineGridView.hideProgressUI();
            }
        });
    }

    @Override
    public void addItemToCart(tdevm.app_ui.api.models.cart.MenuItem menuItem, int itemTotal, String itemHash) {
        cartHelper.addItemToCart(menuItem, itemTotal, itemHash);
        cartHelper.addItemToSelection(menuItem.getItemId());
        singleCuisineGridView.updateAdapter();
    }

    @Override
    public void addItemToSelection(tdevm.app_ui.api.models.cart.MenuItem menuItem) {
        cartHelper.addItemToSelection(menuItem.getItemId());
    }

    @Override
    public void updateCartItem(tdevm.app_ui.api.models.cart.MenuItem menuItem, int itemTotal, String itemHash) {
        cartHelper.updateCartItem(menuItem, itemTotal, itemHash);
        cartHelper.updateSelectionItem(menuItem);
        singleCuisineGridView.updateAdapter();
    }


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
        cartHelper.onDestroy();
    }

    public void logSelections() {
        Log.d(TAG, "Log Started");
        Single<List<CartSelection>> selection = cartHelper.getCartSelections();
        Single<List<CartItem>> cartItems = cartHelper.getCartItems();
        selection.subscribe(new SingleObserver<List<CartSelection>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<CartSelection> cartSelections) {
                for (int i = 0; i < cartSelections.size(); i++) {
                    Log.d(TAG, "Item selection id:" + String.valueOf(cartSelections.get(i).getSelectionItemId()));
                    Log.d(TAG, "Item selection qty added:" + String.valueOf(cartSelections.get(i).getQty()));
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });


        cartItems.subscribe(new SingleObserver<List<CartItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<CartItem> cartItems) {
                for (int i = 0; i < cartItems.size(); i++) {
                    Log.d(TAG, "Cart Item:" + cartItems.get(i).getMenuItem().getItemName());
                    Log.d(TAG, "Cart Item QTY:" + cartItems.get(i).getQuantity());
                    Log.d(TAG, "Cart Item Total:" + cartItems.get(i).getPrice());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

    }


    @Override
    public void onCartItemAdded() {
        logSelections();
    }

    @Override
    public void onCartSelectionAdded() {
        singleCuisineGridView.updateAdapter();
        logSelections();
    }

    @Override
    public void onCartSelectionUpdated() {
        singleCuisineGridView.updateAdapter();
        logSelections();
    }

    @Override
    public void onCartItemUpdated() {
        logSelections();
    }
}
