package tdevm.app_ui.api.cart;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

/**
 * Created by Tridev on 02-01-2018.
 */

@Dao
public interface CartItemDao {

    @Query("SELECT * FROM cart_items")
    List<CartItem> getCartItems();

    @Query("SELECT * FROM cart_items WHERE item_hash = :itemHash")
    CartItem getCartItemByHash(String itemHash);

    @Insert
    void addItemToCart(CartItem cartItem);

    @Update
    void updateCartItem(CartItem cartItem);

    @Delete
    void deleteItemFromCart(CartItem item);

    @Query("DELETE FROM cart_items")
    void deleteCartItems();

    @Query("SELECT SUM(price) FROM cart_items")
    Single<Integer> getCartTotal();

    @Query("SELECT SUM(quantity) FROM cart_items")
    Single<Integer> getTotalItems();

}
