package tdevm.app_ui.api.models.response.v2.FOrder;

public class FOrderItem {

    private String item_q_total;

    private String f_o_i_id;

    private String item_price;

    private String f_o_id;

    private String restaurant_id;

    private FOrderAddOn[] f_order_addons;

    private String[] f_order_v_extras;

    private String tax_total;

    private String item_slug;

    private String item_name;

    private String item_id;

    private String tax_id;

    private String item_qty;

    private FOrderVariant[] f_order_variants;

    private String item_tax;

    private String item_total;

    private String item_hash;

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

    public FOrderAddOn[] getF_order_addons() {
        return f_order_addons;
    }

    public void setF_order_addons(FOrderAddOn[] f_order_addons) {
        this.f_order_addons = f_order_addons;
    }

    public String[] getF_order_v_extras() {
        return f_order_v_extras;
    }

    public void setF_order_v_extras(String[] f_order_v_extras) {
        this.f_order_v_extras = f_order_v_extras;
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

    public FOrderVariant[] getF_order_variants() {
        return f_order_variants;
    }

    public void setF_order_variants(FOrderVariant[] f_order_variants) {
        this.f_order_variants = f_order_variants;
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
}
