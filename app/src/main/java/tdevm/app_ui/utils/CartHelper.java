package tdevm.app_ui.utils;

import android.util.Log;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.cart.CartItemDao;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.cart.CartSelectionDao;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.base.BasePresenter;

/**
 * Created by Tridev on 05-01-2018.
 */

public class CartHelper extends BasePresenter {
    public static final String TAG = CartHelper.class.getSimpleName();

    private CompositeDisposable compositeDisposable;
    private CartItemDao cartItemDao;
    private CartSelectionDao cartSelectionDao;

    //
    private CartItem item;
    private List<CartItem> cartItemsList;
    private Double cartTotal;
    private Integer cartItemsCount;

    @Inject
    public CartHelper(CartItemDao cartItemDao, CartSelectionDao cartSelectionDao) {
        this.cartItemDao = cartItemDao;
        this.cartSelectionDao = cartSelectionDao;
        compositeDisposable = new CompositeDisposable();
    }

    //Queries
    public List<CartItem> getCartItems() {
        Flowable<List<CartItem>> cartItems = cartItemDao.getCartItems();
        Disposable disposable = cartItems.subscribeWith(new DisposableSubscriber<List<CartItem>>() {
            @Override
            public void onNext(List<CartItem> cartItems) {
                cartItemsList = new ArrayList<>(cartItems);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
        compositeDisposable.add(disposable);
        return cartItemsList;
    }

    public CartItem getCartItemById(Long dishId) {
        Single<CartItem> cartItemSingle = cartItemDao.getCartItemById(dishId);
        cartItemSingle.subscribeWith(new SingleObserver<CartItem>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(CartItem cartItem) {
                item = cartItem;
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, e.getMessage());
            }
        });
        return item;
    }

    public List<CartSelection> getCartSelections() {
        return cartSelectionDao.getCartSelections();
    }

    public int getCartTotalItems() {
        Single<Integer> single = cartItemDao.getTotalItems();
        single.subscribeWith(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(Integer integer) {
                cartItemsCount = integer;
            }

            @Override
            public void onError(Throwable e) {
                cartItemsCount = 0;
            }
        });
        return cartItemsCount;
    }

    public Double getCartTotal() {
        Single<Double> single = cartItemDao.getCartTotal();
        single.subscribeWith(new DisposableSingleObserver<Double>() {
            @Override
            public void onSuccess(Double aDouble) {
                cartTotal = aDouble;
            }

            @Override
            public void onError(Throwable e) {

            }
        });
        return cartTotal;
    }

    public CartSelection getCartSelectionById(Long dishId) {
        return cartSelectionDao.getCartSelectionById(dishId);
    }

    //Mutations
    public void addItemToCart(DishesOfCuisine dishesOfCuisine) {
        CartItem item = getCartItemById(dishesOfCuisine.getDish_id());
        if (item == null) {
            CartItem cartItem = new CartItem(dishesOfCuisine.getDish_id(), dishesOfCuisine, 1, dishesOfCuisine.getDish_price());
            cartItemDao.addItemToCart(cartItem);
        } else if (item.getQuantity() >= 1) {
            CartItem cartItem = new CartItem(dishesOfCuisine.getDish_id(), dishesOfCuisine, item.getQuantity() + 1, (item.getQuantity() + 1) * dishesOfCuisine.getDish_price());
            cartItemDao.updateCartItem(cartItem);
        }
    }

    public void updateCartItem(DishesOfCuisine dishesOfCuisine) {
        CartItem item = getCartItemById(dishesOfCuisine.getDish_id());
        if (item != null) {
            CartItem cartItem = new CartItem(dishesOfCuisine.getDish_id(), dishesOfCuisine, item.getQuantity() - 1, (item.getQuantity() - 1) * dishesOfCuisine.getDish_price());
            cartItemDao.updateCartItem(cartItem);
            if (item.getQuantity() == 1) {
                cartItemDao.deleteItemFromCart(item);
            }
        }
    }

    public void addItemToSelection(DishesOfCuisine dishesOfCuisine) {
        CartSelection i = cartSelectionDao.getCartSelectionById(dishesOfCuisine.getDish_id());
        if (i == null) {
            CartSelection newSelection = new CartSelection(dishesOfCuisine.getDish_id(), dishesOfCuisine, 1);
            cartSelectionDao.addItemToSelection(newSelection);
        } else {
            cartSelectionDao.incrementCartSelectionById(dishesOfCuisine.getDish_id());
        }
    }

    public void incrementCartSelectionById(Long dishId) {
        cartSelectionDao.incrementCartSelectionById(dishId);
    }

    public void decrementCartSelectionById(Long dishId) {
        cartSelectionDao.decrementCartSelectionById(dishId);
    }

    public void updateSelectionItem(DishesOfCuisine dishesOfCuisine) {
        CartSelection i = cartSelectionDao.getCartSelectionById(dishesOfCuisine.getDish_id());
        if (i != null) {
            cartSelectionDao.decrementCartSelectionById(dishesOfCuisine.getDish_id());
        }
    }

    public Boolean cartSelectionExist() {
        if (cartSelectionDao != null) {
            if (cartSelectionDao.getCartSelections() != null) {
                return true;
            }

        }
        return false;
    }


    public Boolean cartItemsExist() {
        if (cartItemDao != null) {
            int i = getCartTotalItems();
            if (i > 0) {
                return true;
            }
        }
        return false;
    }

    public void onDestroy() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
