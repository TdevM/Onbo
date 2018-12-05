package app.onbo.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import app.onbo.api.cart.CartItem;
import app.onbo.api.cart.CartItemDao;
import app.onbo.api.cart.CartSelection;
import app.onbo.api.cart.CartSelectionDao;
import app.onbo.api.models.cart.MenuItem;
import app.onbo.base.BasePresenter;

/**
 * Created by Tridev on 05-01-2018.
 */

public class CartHelper extends BasePresenter {
    public static final String TAG = CartHelper.class.getSimpleName();

    private CompositeDisposable compositeDisposable;
    public CartItemDao cartItemDao;
    private CartSelectionDao cartSelectionDao;
    private CartListener listener;

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


    interface cartModifications {

        void onItemAdded();

        void onItemUpdated();

    }


    public void setCartListener(CartListener cartListener) {
        this.listener = cartListener;
    }

    public Single<List<CartItem>> getCartItems() {
        return cartItemDao.getCartItems();
    }

    public Single<CartItem> getCartItemByHash(String itemHash) {
        return cartItemDao.getCartItemByHash(itemHash);
    }

    public Single<List<CartSelection>> getCartSelections() {
        return cartSelectionDao.getCartSelections();
    }

    public Single<CartSelection> getCartSelectionById(Long dishId) {
        return cartSelectionDao.getCartSelectionById(dishId);
    }

    public int getCartTotalItems() {
        Single<Integer> single = cartItemDao.getTotalItems();
        single.subscribe(new SingleObserver<Integer>() {
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
        single.subscribe(new DisposableSingleObserver<Integer>() {
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

    public CartItem getCartItemByHashNew(String itemHash) {
        Single<CartItem> cartItemSingle = cartItemDao.getCartItemByHash(itemHash);
        cartItemSingle.subscribe(new DisposableSingleObserver<CartItem>() {
            @Override
            public void onSuccess(CartItem c) {
                if (c != null) {
                    item = c;
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "ITEM WAS NULL");
            }
        });
        return item;
    }

    //Mutations
    public void addItemToCart(MenuItem menuItem, int itemPrice, String itemHash) {
        Single<CartItem> cartItemSingle = cartItemDao.getCartItemByHash(itemHash);
        cartItemSingle.subscribe(new DisposableSingleObserver<CartItem>() {
            @Override
            public void onSuccess(CartItem cartItem) {
                if (cartItem != null) {
                    if (cartItem.getQuantity() >= 1) {
                        Log.d(TAG, cartItem.getItem_hash());
                        Log.d(TAG, "ITEM WAS NOTTTT NULL");
                        CartItem item = new CartItem(cartItem.getId(), itemHash, menuItem, cartItem.getQuantity() + 1, (cartItem.getQuantity() + 1) * itemPrice, menuItem.getCustomizable());
                        cartItemDao.updateCartItem(item);
                        listener.onCartItemUpdated();

                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "ITEM WAS NULL");
                insertCartItem(menuItem, itemPrice, itemHash);
                listener.onCartItemAdded();
            }
        });
    }

    public void updateCartItem(MenuItem menuItem, int itemPrice, String itemHash) {
        Single<CartItem> cartItemSingle = cartItemDao.getCartItemByHash(itemHash);
        cartItemSingle.subscribe(new DisposableSingleObserver<CartItem>() {
            @Override
            public void onSuccess(CartItem cartItem) {
                if (cartItem != null) {
                    mutateCartItem(menuItem, itemPrice, itemHash, cartItem);
                }
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "ITEM NOT FOUND");
            }
        });
    }

    public void addItemToSelection(String itemId) {
        Single<CartSelection> i = cartSelectionDao.getCartSelectionById(Long.parseLong(itemId));
        i.subscribe(new DisposableSingleObserver<CartSelection>() {
            @Override
            public void onSuccess(CartSelection cartSelection) {
                cartSelectionDao.incrementCartSelectionById(Long.parseLong(itemId));
            }

            @Override
            public void onError(Throwable e) {
                CartSelection newSelection = new CartSelection(Long.parseLong(itemId), 1);
                cartSelectionDao.addItemToSelection(newSelection);
            }
        });
    }

    public void updateSelectionItem(MenuItem menuItem) {
        Single<CartSelection> i = cartSelectionDao.getCartSelectionById(Long.parseLong(menuItem.getItemId()));
        i.subscribe(new DisposableSingleObserver<CartSelection>() {
            @Override
            public void onSuccess(CartSelection cartSelection) {
                cartSelectionDao.decrementCartSelectionById(Long.parseLong(menuItem.getItemId()));
            }

            @Override
            public void onError(Throwable e) {

            }
        });
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
        Single<List<CartItem>> cartItemsFetch = getCartItems();
        cartItemsFetch.subscribe(new SingleObserver<List<CartItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<CartItem> cartItems) {
                for (int i = 0; i < cartItems.size(); i++) {
                    Log.d(TAG, "Cart Item:" + cartItems.get(i).getMenuItem().getItemName()
                            + "QTY:" + cartItems.get(i).getQuantity() +
                            "Total:" + cartItems.get(i).getPrice());
                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });


    }

    public void onDestroy() {
        compositeDisposable.clear();
        compositeDisposable.dispose();
    }


    //Mutations


    private void insertCartItem(MenuItem menuItem, int itemPrice, String itemHash) {

        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {
                CartItem cartItem = new CartItem(itemHash, menuItem, 1, itemPrice, menuItem.getCustomizable());
                cartItemDao.addItemToCart(cartItem);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Inserted to cart");
                listener.onCartItemAdded();
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Failed to insert to cartItem");
                e.printStackTrace();
            }
        });

    }

    private void mutateCartItem(MenuItem menuItem, int itemPrice, String itemHash, CartItem cartItem) {

        if (cartItem.getQuantity() == 1) {
            cartItemDao.deleteItemFromCart(cartItem);
            listener.onCartItemUpdated();
        } else {
            Completable.fromAction(new Action() {
                @Override
                public void run() throws Exception {
                    CartItem item = new CartItem(cartItem.getId(), itemHash, menuItem, cartItem.getQuantity() - 1, (cartItem.getQuantity() - 1) * itemPrice, menuItem.getCustomizable());
                    cartItemDao.updateCartItem(item);
                }
            }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
                @Override
                public void onSubscribe(Disposable d) {
                    compositeDisposable.add(d);
                }

                @Override
                public void onComplete() {
                    listener.onCartItemUpdated();

                }

                @Override
                public void onError(Throwable e) {

                }
            });
        }


    }


    private void insertCartSelection(String itemId) {
        Completable.fromAction(new Action() {
            @Override
            public void run() throws Exception {

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new CompletableObserver() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "Inserted to selection");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Failed to insert to selection");
                e.printStackTrace();
            }
        });
    }

    public JSONArray convertCartTOJSON() {
        JSONArray root = new JSONArray();
        Single<List<CartItem>> cartItems = getCartItems();
        cartItems.subscribe(new SingleObserver<List<CartItem>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onSuccess(List<CartItem> cartItems) {
                for (int i = 0; i < cartItems.size(); i++) {
                    JSONObject rootObject = new JSONObject();
                    JSONArray variants = new JSONArray();
                    JSONArray addOns = new JSONArray();
                    JSONArray vExtra = new JSONArray();
                    for (int j = 0; j < 2; j++) {
                        try {
                            rootObject.put("item_id", cartItems.get(i).getMenuItem().getItemId());
                            rootObject.put("item_qty", cartItems.get(i).getQuantity());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    root.put(rootObject);
                    for (int x = 0; x < cartItems.get(i).getMenuItem().getMenuVariantOptions().size(); x++) {

                        JSONObject variantObjects = new JSONObject();
                        for (int j = 0; j < 2; j++) {
                            try {
                                variantObjects.put("variant_option_id", cartItems.get(i).getMenuItem().getMenuVariantOptions().get(x).getOptionId());
                                variantObjects.put("variant_id", cartItems.get(i).getMenuItem().getMenuVariantOptions().get(x).getVariantId());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        variants.put(variantObjects);
                    }
                    try {
                        rootObject.put("order_variants", variants);
                        rootObject.put("variants_extras", vExtra);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    for (int x = 0; x < cartItems.get(i).getMenuItem().getMenuAddOns().size(); x++) {
                        JSONObject addOnObjects = new JSONObject();
                        for (int j = 0; j < 2; j++) {
                            try {
                                addOnObjects.put("menu_add_on_id", cartItems.get(i).getMenuItem().getMenuAddOns().get(x).getAddOnId());
                                addOnObjects.put("group_id", cartItems.get(i).getMenuItem().getMenuAddOns().get(x).getGroupId());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        addOns.put(addOnObjects);
                    }
                    try {
                        rootObject.put("order_add_ons", addOns);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
            }

            @Override
            public void onError(Throwable e) {

            }
        });

        return root;
    }

}