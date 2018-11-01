
package tdevm.app_ui.api.models.response.v2.t_orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TOrderItem implements Parcelable {

    @SerializedName("detail_id")
    @Expose
    private String detailId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("item_price")
    @Expose
    private Integer itemPrice;
    @SerializedName("item_qty")
    @Expose
    private Integer itemQty;
    @SerializedName("item_total")
    @Expose
    private Integer itemTotal;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("kot_id")
    @Expose
    private String kotId;
    @SerializedName("item_tax")
    @Expose
    private Integer itemTax;
    @SerializedName("tax_total")
    @Expose
    private Integer taxTotal;
    @SerializedName("item_q_total")
    @Expose
    private Integer itemQTotal;
    @SerializedName("is_del")
    @Expose
    private Boolean isDel;
    @SerializedName("item_hash")
    @Expose
    private String itemHash;
    @SerializedName("item_slug")
    @Expose
    private String itemSlug;
    @SerializedName("t_order_variants")
    @Expose
    private List<TOrderVariant> tOrderVariants = null;
    @SerializedName("t_order_v_extras")
    @Expose
    private List<TOrderVExtra> tOrderVExtras = null;
    @SerializedName("t_order_add_ons")
    @Expose
    private List<TOrderAddOn> tOrderAddOns = null;

    protected TOrderItem(Parcel in) {
        detailId = in.readString();
        orderId = in.readString();
        itemName = in.readString();
        if (in.readByte() == 0) {
            itemPrice = null;
        } else {
            itemPrice = in.readInt();
        }
        if (in.readByte() == 0) {
            itemQty = null;
        } else {
            itemQty = in.readInt();
        }
        if (in.readByte() == 0) {
            itemTotal = null;
        } else {
            itemTotal = in.readInt();
        }
        itemId = in.readString();
        restaurantId = in.readString();
        kotId = in.readString();
        if (in.readByte() == 0) {
            itemTax = null;
        } else {
            itemTax = in.readInt();
        }
        if (in.readByte() == 0) {
            taxTotal = null;
        } else {
            taxTotal = in.readInt();
        }
        if (in.readByte() == 0) {
            itemQTotal = null;
        } else {
            itemQTotal = in.readInt();
        }
        byte tmpIsDel = in.readByte();
        isDel = tmpIsDel == 0 ? null : tmpIsDel == 1;
        itemHash = in.readString();
        itemSlug = in.readString();
        tOrderVariants = in.createTypedArrayList(TOrderVariant.CREATOR);
        tOrderVExtras = in.createTypedArrayList(TOrderVExtra.CREATOR);
        tOrderAddOns = in.createTypedArrayList(TOrderAddOn.CREATOR);
    }

    public static final Creator<TOrderItem> CREATOR = new Creator<TOrderItem>() {
        @Override
        public TOrderItem createFromParcel(Parcel in) {
            return new TOrderItem(in);
        }

        @Override
        public TOrderItem[] newArray(int size) {
            return new TOrderItem[size];
        }
    };

    public String getDetailId() {
        return detailId;
    }

    public void setDetailId(String detailId) {
        this.detailId = detailId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Integer getItemQty() {
        return itemQty;
    }

    public void setItemQty(Integer itemQty) {
        this.itemQty = itemQty;
    }

    public Integer getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(Integer itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getKotId() {
        return kotId;
    }

    public void setKotId(String kotId) {
        this.kotId = kotId;
    }

    public Integer getItemTax() {
        return itemTax;
    }

    public void setItemTax(Integer itemTax) {
        this.itemTax = itemTax;
    }

    public Integer getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(Integer taxTotal) {
        this.taxTotal = taxTotal;
    }

    public Integer getItemQTotal() {
        return itemQTotal;
    }

    public void setItemQTotal(Integer itemQTotal) {
        this.itemQTotal = itemQTotal;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String itemHash) {
        this.itemHash = itemHash;
    }

    public String getItemSlug() {
        return itemSlug;
    }

    public void setItemSlug(String itemSlug) {
        this.itemSlug = itemSlug;
    }

    public List<TOrderVariant> getTOrderVariants() {
        return tOrderVariants;
    }

    public void setTOrderVariants(List<TOrderVariant> tOrderVariants) {
        this.tOrderVariants = tOrderVariants;
    }

    public List<TOrderVExtra> getTOrderVExtras() {
        return tOrderVExtras;
    }

    public void setTOrderVExtras(List<TOrderVExtra> tOrderVExtras) {
        this.tOrderVExtras = tOrderVExtras;
    }

    public List<TOrderAddOn> getTOrderAddOns() {
        return tOrderAddOns;
    }

    public void setTOrderAddOns(List<TOrderAddOn> tOrderAddOns) {
        this.tOrderAddOns = tOrderAddOns;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [item_q_total = "+itemQTotal+", t_order_v_extras = "+tOrderVExtras+", detail_id = "+detailId+", item_price = "+itemPrice+", kot_id = "+kotId+", restaurant_id = "+restaurantId+", order_id = "+orderId+", t_order_add_ons = "+tOrderAddOns+", t_order_variants = "+tOrderVariants+", tax_total = "+taxTotal+", item_slug = "+itemSlug+", item_name = "+itemName+", item_id = "+itemId+", is_del = "+isDel+", item_qty = "+itemQty+", item_tax = "+itemTax+", item_hash = "+itemHash+", item_total = "+itemTotal+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(detailId);
        dest.writeString(orderId);
        dest.writeString(itemName);
        if (itemPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemPrice);
        }
        if (itemQty == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemQty);
        }
        if (itemTotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemTotal);
        }
        dest.writeString(itemId);
        dest.writeString(restaurantId);
        dest.writeString(kotId);
        if (itemTax == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemTax);
        }
        if (taxTotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(taxTotal);
        }
        if (itemQTotal == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemQTotal);
        }
        dest.writeByte((byte) (isDel == null ? 0 : isDel ? 1 : 2));
        dest.writeString(itemHash);
        dest.writeString(itemSlug);
        dest.writeTypedList(tOrderVariants);
        dest.writeTypedList(tOrderVExtras);
        dest.writeTypedList(tOrderAddOns);
    }
}
