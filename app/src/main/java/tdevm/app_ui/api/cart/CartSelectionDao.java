package tdevm.app_ui.api.cart;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Tridev on 02-01-2018.
 */

@Dao
public interface CartSelectionDao {

    @Query("SELECT * FROM cart_selections")
    List<CartSelection> getCartSelections();


    @Query("SELECT * FROM cart_selections WHERE itemId = :itemId")
    CartSelection getCartSelectionById(Long itemId);

    @Update
    void updateCartSelectionItem(CartSelection selection);

    @Insert
    void addItemToSelection(CartSelection selection);

    @Delete
    void deleteItemFromSelection(CartSelection selection);

    @Query("DELETE FROM cart_selections")
    void deleteCartSelections();

    @Query("UPDATE cart_selections SET qty = qty + 1 WHERE itemId = :itemId ")
    void incrementCartSelectionById(Long itemId);

    @Query("UPDATE cart_selections SET qty = qty - 1 WHERE itemId = :itemId")
    void decrementCartSelectionById(Long itemId);
}
