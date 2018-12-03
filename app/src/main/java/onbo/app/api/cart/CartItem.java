package onbo.app.api.cart;


import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


import onbo.app.api.models.cart.MenuItem;


/**
 * Created by Tridev on 22-10-2017.
 */

@Entity(tableName = "cart_items")
public class CartItem {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String item_hash;
    @Embedded
    private MenuItem menuItem;
    private int quantity;
    private int price;
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
    public CartItem(@NonNull String item_hash,MenuItem menuItem, int quantity, int price, Boolean isCustomizable) {
        this.menuItem = menuItem;
        this.item_hash = item_hash;
        this.quantity = quantity;
        this.price = price;
        this.isCustomizable = isCustomizable;
    }

    @Ignore
    public CartItem(@NonNull String item_hash, MenuItem menuItem, int quantity, int price) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }


    @Ignore
    public CartItem(@NonNull Long id, String item_hash, MenuItem menuItem, int quantity, int price, Boolean isCustomizable) {
        this.id = id;
        this.item_hash = item_hash;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
        this.isCustomizable = isCustomizable;
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
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
