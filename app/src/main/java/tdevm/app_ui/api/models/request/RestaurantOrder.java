package tdevm.app_ui.api.models.request;

import org.json.JSONArray;

/**
 * Created by Tridev on 05-11-2017.
 */

public class RestaurantOrder {

    private String restaurant_uuid;
    private String restaurant_table_shortid;
    private String temp_table_order_id;
    private String user_message;
    private String dishes_quantities;
    private int guest_count;

    public RestaurantOrder(String restaurant_uuid, String restaurant_table_shortid, String user_message, String dishes_quantities,int guest_count) {
        this.guest_count = guest_count;
        this.restaurant_uuid = restaurant_uuid;
        this.restaurant_table_shortid = restaurant_table_shortid;
        this.user_message = user_message;
        this.dishes_quantities = dishes_quantities;
    }

    public RestaurantOrder(String temp_table_order_id, String dishes_quantities) {
        this.temp_table_order_id = temp_table_order_id;
        this.dishes_quantities = dishes_quantities;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public String getRestaurant_table_shortid() {
        return restaurant_table_shortid;
    }

    public void setRestaurant_table_shortid(String restaurant_table_shortid) {
        this.restaurant_table_shortid = restaurant_table_shortid;
    }

    public String getUser_message() {
        return user_message;
    }

    public void setUser_message(String user_message) {
        this.user_message = user_message;
    }

    public String getDishes_quantities() {
        return dishes_quantities;
    }

    public void setDishes_quantities(String dishes_quantities) {
        this.dishes_quantities = dishes_quantities;
    }
}
