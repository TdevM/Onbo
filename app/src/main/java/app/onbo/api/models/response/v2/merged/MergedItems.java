package app.onbo.api.models.response.v2.merged;

public class MergedItems {


    private String item_q_total;

    private String item_slug;

    private ItemData item_data;

    private String item_id;

    private String restaurant_id;

    private String is_del;

    private String item_qty;

    private String r_od_id;

    private String order_id;

    private String item_tax;

    private String tax_total;

    private String item_total;

    private String item_hash;

    public String getItem_q_total() {
        return item_q_total;
    }

    public void setItem_q_total(String item_q_total) {
        this.item_q_total = item_q_total;
    }

    public String getItem_slug() {
        return item_slug;
    }

    public void setItem_slug(String item_slug) {
        this.item_slug = item_slug;
    }

    public ItemData getItem_data() {
        return item_data;
    }

    public void setItem_data(ItemData item_data) {
        this.item_data = item_data;
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getIs_del() {
        return is_del;
    }

    public void setIs_del(String is_del) {
        this.is_del = is_del;
    }

    public String getItem_qty() {
        return item_qty;
    }

    public void setItem_qty(String item_qty) {
        this.item_qty = item_qty;
    }

    public String getR_od_id() {
        return r_od_id;
    }

    public void setR_od_id(String r_od_id) {
        this.r_od_id = r_od_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getItem_tax() {
        return item_tax;
    }

    public void setItem_tax(String item_tax) {
        this.item_tax = item_tax;
    }

    public String getTax_total() {
        return tax_total;
    }

    public void setTax_total(String tax_total) {
        this.tax_total = tax_total;
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
        return "ClassPojo [item_q_total = " + item_q_total + ", item_slug = " + item_slug + ", item_data = " + item_data + ", item_id = " + item_id + ", restaurant_id = " + restaurant_id + ", is_del = " + is_del + ", item_qty = " + item_qty + ", r_od_id = " + r_od_id + ", order_id = " + order_id + ", item_tax = " + item_tax + ", tax_total = " + tax_total + ", item_total = " + item_total + ", item_hash = " + item_hash + "]";
    }
}
