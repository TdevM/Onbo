package onbo.app.api.models.request;

/**
 * Created by Tridev on 05-11-2017.
 */

public class RestaurantOrder {

    private String restaurant_id;
    private String table_id;
    private String t_order_id;
    private String user_message;
    private String order_items;
    private int guest_count;

    public RestaurantOrder(String restaurant_id, String table_id, String user_message, String order_items, int guest_count) {
        this.restaurant_id = restaurant_id;
        this.table_id = table_id;
        this.user_message = user_message;
        this.order_items = order_items;
        this.guest_count = guest_count;
    }

    public RestaurantOrder(String restaurant_id, String t_order_id, String user_message, String order_items) {
        this.restaurant_id = restaurant_id;
        this.t_order_id = t_order_id;
        this.user_message = user_message;
        this.order_items = order_items;
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

    public String getT_order_id() {
        return t_order_id;
    }

    public void setT_order_id(String t_order_id) {
        this.t_order_id = t_order_id;
    }

    public String getUser_message() {
        return user_message;
    }

    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }

    public String getOrder_items() {
        return order_items;
    }

    public void setOrder_items(String order_items) {
        this.order_items = order_items;
    }

    public int getGuest_count() {
        return guest_count;
    }

    public void setGuest_count(int guest_count) {
        this.guest_count = guest_count;
    }
}
