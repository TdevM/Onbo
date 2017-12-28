package tdevm.app_ui.api.models.response;


import java.math.BigDecimal;

/**
 * Created by Tridev on 22-10-2017.
 */

public class CartItem {

    private DishesOfCuisine dish;
    private int quantity;
    private BigDecimal price;

    public CartItem(DishesOfCuisine dish, int quantity, BigDecimal price) {
        this.dish = dish;
        this.quantity = quantity;
        this.price = price;
    }

    public DishesOfCuisine getDish() {
        return dish;
    }

    public void setDish(DishesOfCuisine dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
