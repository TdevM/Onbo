package tdevm.app_ui.api.models.response.v2.FOrder;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.api.models.response.v2.RestaurantTable;

public class FOrder implements Parcelable{

    private String created_from;

    private String txn_mode;

    private String offer_id;

    private String packing_chrg;

    private JSONObject txn_data;

    private String order_id;

    private String restaurant_id;

    private Restaurant restaurant;

    private String timestamp;

    private RestaurantTable restaurant_table;

    private String txn_status;

    private String txn_id;

    private String order_source;

    private String user_id;

    private String grand_total;

    private String completed;

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    private String status;

    private String table_id;

    private String subtotal;

    private String discount;

    private FOrderItem[] f_order_items;

    private String guest_count;

    private FOrderDetail f_order_detail;

    private String user_msg;

    private String conv_chrg;

    private String dlv_chrg;

    private User user;

    private String taxes;

    private String order_type;

    private String t_order_id;

    protected FOrder(Parcel in) {
        created_from = in.readString();
        txn_mode = in.readString();
        offer_id = in.readString();
        packing_chrg = in.readString();
        order_id = in.readString();
        timestamp = in.readString();
        txn_status = in.readString();
        txn_id = in.readString();
        order_source = in.readString();
        user_id = in.readString();
        grand_total = in.readString();
        completed = in.readString();
        status = in.readString();
        restaurant_id = in.readString();
        table_id = in.readString();
        subtotal = in.readString();
        discount = in.readString();
        guest_count = in.readString();
        user_msg = in.readString();
        conv_chrg = in.readString();
        dlv_chrg = in.readString();
        taxes = in.readString();
        order_type = in.readString();
    }

    public static final Creator<FOrder> CREATOR = new Creator<FOrder>() {
        @Override
        public FOrder createFromParcel(Parcel in) {
            return new FOrder(in);
        }

        @Override
        public FOrder[] newArray(int size) {
            return new FOrder[size];
        }
    };

    public String getCreated_from() {
        return created_from;
    }

    public void setCreated_from(String created_from) {
        this.created_from = created_from;
    }

    public String getTxn_mode() {
        return txn_mode;
    }

    public void setTxn_mode(String txn_mode) {
        this.txn_mode = txn_mode;
    }

    public String getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(String offer_id) {
        this.offer_id = offer_id;
    }

    public String getPacking_chrg() {
        return packing_chrg;
    }

    public void setPacking_chrg(String packing_chrg) {
        this.packing_chrg = packing_chrg;
    }

    public JSONObject getTxn_data() {
        return txn_data;
    }

    public void setTxn_data(JSONObject txn_data) {
        this.txn_data = txn_data;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public RestaurantTable getRestaurant_table() {
        return restaurant_table;
    }

    public void setRestaurant_table(RestaurantTable restaurant_table) {
        this.restaurant_table = restaurant_table;
    }

    public String getTxn_status() {
        return txn_status;
    }

    public void setTxn_status(String txn_status) {
        this.txn_status = txn_status;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public String getOrder_source() {
        return order_source;
    }

    public void setOrder_source(String order_source) {
        this.order_source = order_source;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getCompleted() {
        return completed;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getTable_id() {
        return table_id;
    }

    public void setTable_id(String table_id) {
        this.table_id = table_id;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public FOrderItem[] getF_order_items() {
        return f_order_items;
    }

    public void setF_order_items(FOrderItem[] f_order_items) {
        this.f_order_items = f_order_items;
    }

    public String getGuest_count() {
        return guest_count;
    }

    public void setGuest_count(String guest_count) {
        this.guest_count = guest_count;
    }

    public FOrderDetail getF_order_detail() {
        return f_order_detail;
    }

    public void setF_order_detail(FOrderDetail f_order_detail) {
        this.f_order_detail = f_order_detail;
    }

    public String getUser_msg() {
        return user_msg;
    }

    public void setUser_msg(String user_msg) {
        this.user_msg = user_msg;
    }

    public String getConv_chrg() {
        return conv_chrg;
    }

    public void setConv_chrg(String conv_chrg) {
        this.conv_chrg = conv_chrg;
    }

    public String getDlv_chrg() {
        return dlv_chrg;
    }

    public void setDlv_chrg(String dlv_chrg) {
        this.dlv_chrg = dlv_chrg;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    @Override
    public String toString() {
        return "ClassPojo [created_from = " + created_from + ", txn_mode = " + txn_mode + ", offer_id = " + offer_id + ", packing_chrg = " + packing_chrg + ", txn_data = " + txn_data + ", order_id = " + order_id + ", timestamp = " + timestamp + ", restaurant_table = " + restaurant_table + ", txn_status = " + txn_status + ", txn_id = " + txn_id + ", order_source = " + order_source + ", user_id = " + user_id + ", grand_total = " + grand_total + ", completed = " + completed + ", status = " + status + ", restaurant_id = " + restaurant_id + ", table_id = " + table_id + ", subtotal = " + subtotal + ", discount = " + discount + ", f_order_items = " + f_order_items + ", guest_count = " + guest_count + ", f_order_detail = " + f_order_detail + ", user_msg = " + user_msg + ", conv_chrg = " + conv_chrg + ", dlv_chrg = " + dlv_chrg + ", user = " + user + ", taxes = " + taxes + ", order_type = " + order_type + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getT_order_id() {
        return t_order_id;
    }

    public void setT_order_id(String t_order_id) {
        this.t_order_id = t_order_id;
    }

    public static Creator<FOrder> getCREATOR() {
        return CREATOR;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(created_from);
        dest.writeString(txn_mode);
        dest.writeString(offer_id);
        dest.writeString(packing_chrg);
        dest.writeString(order_id);
        dest.writeString(timestamp);
        dest.writeString(txn_status);
        dest.writeString(txn_id);
        dest.writeString(order_source);
        dest.writeString(user_id);
        dest.writeString(grand_total);
        dest.writeString(completed);
        dest.writeString(status);
        dest.writeString(restaurant_id);
        dest.writeString(table_id);
        dest.writeString(subtotal);
        dest.writeString(discount);
        dest.writeString(guest_count);
        dest.writeString(user_msg);
        dest.writeString(conv_chrg);
        dest.writeString(dlv_chrg);
        dest.writeString(taxes);
        dest.writeString(order_type);
    }
}
