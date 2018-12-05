package app.onbo.api.models.response.v2.FOrder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import app.onbo.api.models.response.v2.menu.MenuItem;

public class FOrderItem implements Parcelable {

    private String item_q_total;

    private String f_o_i_id;

    private String item_price;

    private String f_o_id;

    private String restaurant_id;

    private List<FOrderAddOn> f_order_addons;

    private List<FOrderVExtra> f_order_v_extras;

    private String tax_total;

    private String item_slug;

    private String item_name;

    private String item_id;

    private String tax_id;

    private String item_qty;

    private List<FOrderVariant> f_order_variants;

    private String item_tax;

    private String item_total;

    private String item_hash;

    private MenuItem menu_item;

    protected FOrderItem(Parcel in) {
        item_q_total = in.readString();
        f_o_i_id = in.readString();
        item_price = in.readString();
        f_o_id = in.readString();
        restaurant_id = in.readString();
        f_order_addons = in.createTypedArrayList(FOrderAddOn.CREATOR);
        f_order_v_extras = in.createTypedArrayList(FOrderVExtra.CREATOR);
        tax_total = in.readString();
        item_slug = in.readString();
        item_name = in.readString();
        item_id = in.readString();
        tax_id = in.readString();
        item_qty = in.readString();
        f_order_variants = in.createTypedArrayList(FOrderVariant.CREATOR);
        item_tax = in.readString();
        item_total = in.readString();
        item_hash = in.readString();
        menu_item = in.readParcelable(MenuItem.class.getClassLoader());
    }

    public static final Creator<FOrderItem> CREATOR = new Creator<FOrderItem>() {
        @Override
        public FOrderItem createFromParcel(Parcel in) {
            return new FOrderItem(in);
        }

        @Override
        public FOrderItem[] newArray(int size) {
            return new FOrderItem[size];
        }
    };

    public MenuItem getMenu_item() {
        return menu_item;
    }

    public void setMenu_item(MenuItem menu_item) {
        this.menu_item = menu_item;
    }




    public String getItem_q_total() {
        return item_q_total;
    }

    public void setItem_q_total(String item_q_total) {
        this.item_q_total = item_q_total;
    }

    public String getF_o_i_id() {
        return f_o_i_id;
    }

    public void setF_o_i_id(String f_o_i_id) {
        this.f_o_i_id = f_o_i_id;
    }

    public String getItem_price() {
        return item_price;
    }

    public void setItem_price(String item_price) {
        this.item_price = item_price;
    }

    public String getF_o_id() {
        return f_o_id;
    }

    public void setF_o_id(String f_o_id) {
        this.f_o_id = f_o_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }



    public String getTax_total() {
        return tax_total;
    }

    public void setTax_total(String tax_total) {
        this.tax_total = tax_total;
    }

    public String getItem_slug() {
        return item_slug;
    }

    public void setItem_slug(String item_slug) {
        this.item_slug = item_slug;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getTax_id() {
        return tax_id;
    }

    public void setTax_id(String tax_id) {
        this.tax_id = tax_id;
    }

    public String getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(String item_qty) {
        this.item_qty = item_qty;
    }


    public String getItem_tax() {
        return item_tax;
    }

    public void setItem_tax(String item_tax) {
        this.item_tax = item_tax;
    }

    public String getItem_total() {
        return item_total;
    }

    public void setItem_total(String item_total) {
        this.item_total = item_total;
    }

    public String getItem_hash() {
        return item_hash;
    }

    public void setItem_hash(String item_hash) {
        this.item_hash = item_hash;
    }

    @Override
    public String toString() {
        return "ClassPojo [item_q_total = " + item_q_total + ", f_o_i_id = " + f_o_i_id + ", item_price = " + item_price + ", f_o_id = " + f_o_id + ", restaurant_id = " + restaurant_id + ", f_order_addons = " + f_order_addons + ", f_order_v_extras = " + f_order_v_extras + ", tax_total = " + tax_total + ", item_slug = " + item_slug + ", item_name = " + item_name + ", item_id = " + item_id + ", tax_id = " + tax_id + ", item_qty = " + item_qty + ", f_order_variants = " + f_order_variants + ", item_tax = " + item_tax + ", item_total = " + item_total + ", item_hash = " + item_hash + "]";
    }

    public List<FOrderAddOn> getF_order_addons() {
        return f_order_addons;
    }

    public void setF_order_addons(List<FOrderAddOn> f_order_addons) {
        this.f_order_addons = f_order_addons;
    }

    public List<FOrderVExtra> getF_order_v_extras() {
        return f_order_v_extras;
    }

    public void setF_order_v_extras(List<FOrderVExtra> f_order_v_extras) {
        this.f_order_v_extras = f_order_v_extras;
    }

    public List<FOrderVariant> getF_order_variants() {
        return f_order_variants;
    }

    public void setF_order_variants(List<FOrderVariant> f_order_variants) {
        this.f_order_variants = f_order_variants;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(item_q_total);
        dest.writeString(f_o_i_id);
        dest.writeString(item_price);
        dest.writeString(f_o_id);
        dest.writeString(restaurant_id);
        dest.writeTypedList(f_order_addons);
        dest.writeTypedList(f_order_v_extras);
        dest.writeString(tax_total);
        dest.writeString(item_slug);
        dest.writeString(item_name);
        dest.writeString(item_id);
        dest.writeString(tax_id);
        dest.writeString(item_qty);
        dest.writeTypedList(f_order_variants);
        dest.writeString(item_tax);
        dest.writeString(item_total);
        dest.writeString(item_hash);
        dest.writeParcelable(menu_item, flags);
    }
}
