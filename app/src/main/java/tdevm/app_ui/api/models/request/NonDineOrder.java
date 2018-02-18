package tdevm.app_ui.api.models.request;

/**
 * Created by Tridev on 16-02-2018.
 */

public class NonDineOrder {

    private String restaurant_uuid;
    private String u_message;
    private String dishes_qty;
    private String txn_mode;
    private String txn_id;
    private Double bill_amt;

    //Paid order
    public NonDineOrder(String restaurant_uuid, String u_message, String dishes_qty, String txn_mode, String txn_id, Double bill_amt) {
        this.restaurant_uuid = restaurant_uuid;
        this.u_message = u_message;
        this.dishes_qty = dishes_qty;
        this.txn_mode = txn_mode;
        this.txn_id = txn_id;
        this.bill_amt = bill_amt;
    }

    //Non Paid Order
    public NonDineOrder(String restaurant_uuid, String u_message, String dishes_qty, Double bill_amt) {
        this.restaurant_uuid = restaurant_uuid;
        this.u_message = u_message;
        this.dishes_qty = dishes_qty;
        this.bill_amt = bill_amt;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public String getU_message() {
        return u_message;
    }

    public void setU_message(String u_message) {
        this.u_message = u_message;
    }

    public String getDishes_qty() {
        return dishes_qty;
    }

    public void setDishes_qty(String dishes_qty) {
        this.dishes_qty = dishes_qty;
    }

    public String getTxn_mode() {
        return txn_mode;
    }

    public void setTxn_mode(String txn_mode) {
        this.txn_mode = txn_mode;
    }

    public String getTxn_id() {
        return txn_id;
    }

    public void setTxn_id(String txn_id) {
        this.txn_id = txn_id;
    }

    public Double getBill_amt() {
        return bill_amt;
    }

    public void setBill_amt(Double bill_amt) {
        this.bill_amt = bill_amt;
    }
}
