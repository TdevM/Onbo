package onbo.app.api.cart;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;


/**
 * Created by Tridev on 01-01-2018.
 */
@Entity(tableName = "cart_selections")
public class CartSelection {

    @PrimaryKey
    private Long selectionItemId;
    @ColumnInfo(name = "qty")
    private int qty;


    public CartSelection() {

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
