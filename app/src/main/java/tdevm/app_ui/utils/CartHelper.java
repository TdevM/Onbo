package tdevm.app_ui.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.cart.CartItemDao;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.cart.CartSelectionDao;
import tdevm.app_ui.api.models.cart.MenuItem;
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
    private Integer cartTotal;
    private Integer cartItemsCount;

    @Inject
    public CartHelper(CartItemDao cartItemDao, CartSelectionDao cartSelectionDao) {
        this.cartItemDao = cartItemDao;
        this.cartSelectionDao = cartSelectionDao;
        cartItemsList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
    }

    //Queries
    public List<CartItem> getCartItems() {
//        Flowable<List<CartItem>> cartItems = cartItemDao.getCartItems();
//        Disposable disposable = cartItems.subscribeWith(new DisposableSubscriber<List<CartItem>>() {
//            @Override
//            public void onNext(List<CartItem> cartItems) {
//                cartItemsList = new ArrayList<>(cartItems);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                cartItemsList = new ArrayList<>();
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//        compositeDisposable.add(disposable);
        return cartItemDao.getCartItems();
    }

    public CartItem getCartItemById(String itemHash) {
//        Single<CartItem> cartItemSingle = cartItemDao.getCartItemById(dishId);
//        cartItemSingle.subscribeWith(new SingleObserver<CartItem>() {
//            @Override
//            public void onSubscribe(Disposable d) {
//                compositeDisposable.add(d);
//            }
//
//            @Override
//            public void onSuccess(CartItem cartItem) {
//                item = cartItem;
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.d(TAG,"getCartItemById" +e.getMessage());
//            }
//        });
        return cartItemDao.getCartItemByHash(itemHash);
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
                Log.d(TAG, "getCartTotalItems" + e.getMessage());
            }
        });
        return cartItemsCount;
    }

    public Integer getCartTotal() {
        Single<Integer> single = cartItemDao.getCartTotal();
        single.subscribeWith(new DisposableSingleObserver<Integer>() {
            @Override
            public void onSuccess(Integer integer) {
                cartTotal = integer;
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "getCartTotal" + e.getMessage());
            }
        });
        return cartTotal;
    }

    public CartSelection getCartSelectionById(Long dishId) {
        return cartSelectionDao.getCartSelectionById(dishId);
    }

    //Mutations
    public void addItemToCart(MenuItem menuItem, int itemPrice, String itemHash) {
        CartItem item = getCartItemById(itemHash);
        if (item == null) {
            Log.d(TAG, "ITEM WAS NULL");
            CartItem cartItem = new CartItem(itemHash, menuItem, 1, itemPrice, menuItem.getCustomizable());
            cartItemDao.addItemToCart(cartItem);
        } else if (item.getQuantity() >= 1) {
            Log.d(TAG, item.getItem_hash());
            Log.d(TAG, "ITEM WAS NOTTTT NULL");
            CartItem cartItem = new CartItem(item.getId(), itemHash, menuItem, item.getQuantity() + 1, (item.getQuantity() + 1) * itemPrice, menuItem.getCustomizable());
            cartItemDao.updateCartItem(cartItem);
        }
    }

    public void updateCartItem(MenuItem menuItem, int itemPrice, String itemHash) {
        CartItem item = getCartItemById(itemHash);
        if (item != null) {
            CartItem cartItem = new CartItem(itemHash, menuItem, item.getQuantity() - 1, (item.getQuantity() - 1) * itemPrice);
            cartItemDao.updateCartItem(cartItem);
            if (item.getQuantity() == 1) {
                cartItemDao.deleteItemFromCart(item);
            }
        }
    }

    public void addItemToSelection(String itemId) {
        CartSelection i = cartSelectionDao.getCartSelectionById(Long.parseLong(itemId));
        if (i == null) {
            CartSelection newSelection = new CartSelection(Long.parseLong(itemId), 1);
            cartSelectionDao.addItemToSelection(newSelection);
        } else {
            cartSelectionDao.incrementCartSelectionById(Long.parseLong(itemId));
        }
    }

    public void updateSelectionItem(MenuItem menuItem) {
        CartSelection i = cartSelectionDao.getCartSelectionById(Long.parseLong(menuItem.getItemId()));
        if (i != null) {
            cartSelectionDao.decrementCartSelectionById(Long.parseLong(menuItem.getItemId()));
        }
    }

    public void incrementCartSelectionById(Long itemId) {
        cartSelectionDao.incrementCartSelectionById(itemId);
    }

    public void decrementCartSelectionById(Long itemId) {
        cartSelectionDao.decrementCartSelectionById(itemId);
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

    public void clearCart() {
        cartItemDao.deleteCartItems();
        cartSelectionDao.deleteCartSelections();
    }

    public void logCartItems() {
        for (int i = 0; i < getCartItems().size(); i++) {
            Log.d(TAG, "Cart Item:" + getCartItems().get(i).getMenuItem().getItemName()
                    + "QTY:" + getCartItems().get(i).getQuantity() +
                    "Total:" + getCartItems().get(i).getPrice());
        }
    }

    public void onDestroy() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }
}
