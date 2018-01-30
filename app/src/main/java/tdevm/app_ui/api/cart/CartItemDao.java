package tdevm.app_ui.api.cart;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by Tridev on 02-01-2018.
 */

@Dao
public interface CartItemDao {

    @Query("SELECT * FROM cart_items")
    Flowable<List<CartItem>> getCartItems();

    @Query("SELECT * FROM cart_items WHERE dishId = :dishId")
    Single<CartItem> getCartItemById(Long dishId);

    @Insert
    void addItemToCart(CartItem cartItem);

    @Update
    void updateCartItem(CartItem cartItem);

    @Delete
    void deleteItemFromCart(CartItem item);

    @Query("SELECT SUM(price) FROM cart_items")
    Single<Double> getCartTotal();

    @Query("SELECT SUM(quantity) FROM cart_items")
    Single<Integer> getTotalItems();

}
