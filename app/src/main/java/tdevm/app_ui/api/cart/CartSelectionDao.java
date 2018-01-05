package tdevm.app_ui.api.cart;

import android.arch.persistence.room.Dao;
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


    @Query("SELECT * FROM cart_selections WHERE dishId = :dishId")
    CartSelection getCartSelectionById(Long dishId);

    @Update
    void updateCartSelectionItem(CartSelection selection);

    @Insert
    void addItemToSelection(CartSelection selection);

    @Insert
    void deleteItemFromSelection(CartSelection selection);

    @Query("UPDATE cart_selections SET qty = qty + 1 WHERE dishId = :dishId ")
    void incrementCartSelectionById(Long dishId);

    @Query("UPDATE cart_selections SET qty = qty - 1 WHERE dishId = :dishId")
    void decrementCartSelectionById(Long dishId);
}
