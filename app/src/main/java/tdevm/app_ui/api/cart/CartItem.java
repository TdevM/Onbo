package tdevm.app_ui.api.cart;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


import tdevm.app_ui.api.models.cart.MenuItem;
import tdevm.app_ui.api.models.response.DishesOfCuisine;


/**
 * Created by Tridev on 22-10-2017.
 */

@Entity(tableName = "cart_items")
public class CartItem {

    @PrimaryKey
    @NonNull
    private String item_hash;
    private Long cartItemId;
    @Embedded
    private MenuItem menuItem;
    private int quantity;
    private int price;
    private Boolean isCustomizable;


    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public CartItem() {
    }

    public Boolean getCustomizable() {
        return isCustomizable;
    }

    public void setCustomizable(Boolean customizable) {
        isCustomizable = customizable;
    }


    @Ignore
    public CartItem(String item_hash, MenuItem menuItem, int quantity, int price, Boolean isCustomizable) {
        this.item_hash = item_hash;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
        this.isCustomizable = isCustomizable;
    }

    @Ignore
    public CartItem(@NonNull String item_hash, Long cartItemId, MenuItem menuItem, int quantity, int price) {
        this.item_hash = item_hash;
        this.cartItemId = cartItemId;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }

    public String getItem_hash() {
        return item_hash;
    }

    public void setItem_hash(String item_hash) {
        this.item_hash = item_hash;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
