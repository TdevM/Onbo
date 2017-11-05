package tdevm.app_ui.api.models.request;

/**
 * Created by Tridev on 05-11-2017.
 */

public class RestaurantOrder {

    private String restaurant_uuid;
    private String restaurant_table_shortid;
    private String user_message;
    private String dishes_quantities;

    public RestaurantOrder(String restaurant_uuid, String restaurant_table_shortid, String user_message, String dishes_quantities) {
        this.restaurant_uuid = restaurant_uuid;
        this.restaurant_table_shortid = restaurant_table_shortid;
        this.user_message = user_message;
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
