package onbo.app.api.models.request;

/**
 * Created by Tridev on 16-02-2018.
 */

public class NonDineOrder {

    private String restaurant_id;
    private String user_msg;
    private String guest_count;
    private String order_items;

    public NonDineOrder(String restaurant_id, String user_msg, String guest_count, String order_items) {
        this.restaurant_id = restaurant_id;
        this.user_msg = user_msg;
        this.guest_count = guest_count;
        this.order_items = order_items;
    }

    public NonDineOrder(String restaurant_id, String order_items) {
        this.restaurant_id = restaurant_id;
        this.order_items = order_items;
    }

    public NonDineOrder(String restaurant_id, String user_msg, String order_items) {
        this.restaurant_id = restaurant_id;
        this.user_msg = user_msg;
        this.order_items = order_items;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getUser_msg() {
        return user_msg;
    }

    public void setUser_msg(String user_msg) {
        this.user_msg = user_msg;
    }

    public String getGuest_count() {
        return guest_count;
    }

    public void setGuest_count(String guest_count) {
        this.guest_count = guest_count;
    }

    public String getOrder_items() {
        return order_items;
    }

    public void setOrder_items(String order_items) {
        this.order_items = order_items;
    }
}
