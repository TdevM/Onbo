package tdevm.app_ui.api.cart;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.api.models.cart.MenuItem;


/**
 * Created by Tridev on 01-01-2018.
 */
@Entity(tableName = "cart_selections")
public class CartSelection  {

    @PrimaryKey
    private Long selectionItemId;
    @ColumnInfo(name = "qty")
    private int qty;


    public CartSelection(){

    }

    @Ignore
    public CartSelection(Long selectionItemId, int qty) {
        this.selectionItemId = selectionItemId;
        this.qty = qty;
    }

    public Long getSelectionItemId() {
        return selectionItemId;
    }

    public void setSelectionItemId(Long selectionItemId) {
        this.selectionItemId = selectionItemId;
    }


    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
