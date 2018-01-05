package tdevm.app_ui.api.cart;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import tdevm.app_ui.api.models.response.DishesOfCuisine;


/**
 * Created by Tridev on 22-10-2017.
 */

@Entity(tableName = "cart_items")
public class CartItem {

    @PrimaryKey
    private Long dishId;
    @Embedded
    private DishesOfCuisine dishesOfCuisine;
    private int quantity;
    private Double price;
    private Boolean isCustomizable;



    public CartItem() {
    }

    public Boolean getCustomizable() {
        return isCustomizable;
    }

    public void setCustomizable(Boolean customizable) {
        isCustomizable = customizable;
    }


    @Ignore
    public CartItem(Long dishId, DishesOfCuisine dishesOfCuisine, int quantity, Double price) {
        this.dishId = dishId;
        this.dishesOfCuisine = dishesOfCuisine;
        this.quantity = quantity;
        this.price = price;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public DishesOfCuisine getDishesOfCuisine() {
        return dishesOfCuisine;
    }

    public void setDishesOfCuisine(DishesOfCuisine dishesOfCuisine) {
        this.dishesOfCuisine = dishesOfCuisine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

}
