package tdevm.app_ui.api.cart;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import tdevm.app_ui.api.models.response.DishesOfCuisine;


/**
 * Created by Tridev on 01-01-2018.
 */
@Entity(tableName = "cart_selections")
public class CartSelection  {

    @PrimaryKey
    private Long dishId;
    @Embedded
    private DishesOfCuisine dishesOfCuisine;
    @ColumnInfo(name = "qty")
    private int qty;


    public CartSelection(){

    }

    @Ignore
    public CartSelection(Long dishId, DishesOfCuisine dishesOfCuisine, int qty) {
        this.dishId = dishId;
        this.dishesOfCuisine = dishesOfCuisine;
        this.qty = qty;
    }

    @Ignore
    public CartSelection(Long dishId, DishesOfCuisine dishesOfCuisine) {
        this.dishId = dishId;
        this.dishesOfCuisine = dishesOfCuisine;
    }

    @Ignore
    public CartSelection(DishesOfCuisine dishesOfCuisine) {
        this.dishesOfCuisine = dishesOfCuisine;
    }

    public DishesOfCuisine getDishesOfCuisine() {
        return dishesOfCuisine;
    }

    public void setDishesOfCuisine(DishesOfCuisine dishesOfCuisine) {
        this.dishesOfCuisine = dishesOfCuisine;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
