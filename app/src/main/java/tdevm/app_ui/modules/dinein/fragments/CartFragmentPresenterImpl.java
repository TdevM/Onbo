package tdevm.app_ui.modules.dinein.fragments;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.models.cart.MenuItem;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInPresenterContract;
import tdevm.app_ui.modules.dinein.DineInViewContract;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

/**
 * Created by Tridev on 06-01-2018.
 */

public class CartFragmentPresenterImpl extends BasePresenter implements DineInPresenterContract.CartFragmentPresenter {

    public static final String TAG = CartFragmentPresenterImpl.class.getSimpleName();
    private CartHelper cartHelper;
    private DineInViewContract.CartFragmentView cartFragmentView;
    private CompositeDisposable compositeDisposable;
    private AuthUtils authUtils;
    private static final String MODE_DINE_IN = "MODE_DINE_IN";
    private static final String MODE_NON_DINE = "MODE_NON_DINE";

    @Inject
    public CartFragmentPresenterImpl(CartHelper cartHelper, AuthUtils authUtils) {
        this.cartHelper = cartHelper;
        this.authUtils = authUtils;
        this.compositeDisposable = new CompositeDisposable();
    }


    @Override
    public boolean cartItemsExists() {
        return cartHelper.cartItemsExist();
    }


    @Override
    public void addItemToCart(MenuItem menuItem, String itemHash) {
//        cartHelper.addItemToCart(dishesOfCuisine);
//        cartHelper.addItemToSelection(dishesOfCuisine);
//        cartFragmentView.updateAdapter();
//        cartFragmentView.updateBottomSheet(cartHelper.getCartTotalItems(),cartHelper.getCartTotal());
        logSelections();
    }

    @Override
    public void updateCartItem(MenuItem menuItem, String itemHash) {
//        cartHelper.updateCartItem(dishesOfCuisine);
//        cartHelper.updateSelectionItem(dishesOfCuisine);
        cartFragmentView.updateAdapter();
        cartFragmentView.updateBottomSheet(cartHelper.getCartTotalItems(), cartHelper.getCartTotal());
        if (cartHelper.getCartTotalItems() == 0) {
            showCartEmpty();
        }
        logSelections();
    }

    @Override
    public void fetchCartItems() {
        Single<List<CartItem>> single = cartHelper.getCartItems();
        single.subscribe(new SingleObserver<List<CartItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<CartItem> cartItems) {
                cartFragmentView.onCartItemsFetched(cartItems);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    @Override
    public void showCartEmpty() {
        if (authUtils.getRestaurantMode().equals(MODE_DINE_IN)) {
            cartFragmentView.showDineCartEmpty();
        } else if (authUtils.getRestaurantMode().equals(MODE_NON_DINE)) {
            cartFragmentView.showNonDineEmptyCart();
        }
    }

    public void clearCart() {
        cartHelper.clearCart();
    }

//    @Override
//    public void addCustomizableItemToCart(DishesOfCuisine dishesOfCuisine, Long dishId, int operationFlag) {
//        if (operationFlag == 1) {
////          cartHelper.addItemToCart(dishesOfCuisine);
////          cartHelper.incrementCartSelectionById(dishId);
////          cartFragmentView.updateAdapter();
////          cartFragmentView.updateBottomSheet(cartHelper.getCartTotalItems(),cartHelper.getCartTotal());
//        } else if (operationFlag == 0) {
////          cartHelper.updateCartItem(dishesOfCuisine);
////          cartHelper.decrementCartSelectionById(dishId);
////          cartFragmentView.updateAdapter();
//            cartFragmentView.updateBottomSheet(cartHelper.getCartTotalItems(), cartHelper.getCartTotal());
//        }
//    }

    @Override
    public void attachView(DineInViewContract.CartFragmentView view) {
        cartFragmentView = view;
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
}
