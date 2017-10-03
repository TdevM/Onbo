package tdevm.app_ui.api.models.response;

import tdevm.app_ui.api.models.response.Restaurant;

/**
 * Created by Tridev on 19-08-2017.
 */

public class RestaurantTable {

    private String restaurant_table_uuid_shortid;
    private String restaurant_uuid;
    private Boolean restaurant_table_occupancy_status;
    private String restaurant_table_id;
    private int restaurant_table_tableno;
    private Restaurant restaurant;

    public String getRestaurant_table_uuid_shortid() {
        return restaurant_table_uuid_shortid;
    }

    public void setRestaurant_table_uuid_shortid(String restaurant_table_uuid_shortid) {
        this.restaurant_table_uuid_shortid = restaurant_table_uuid_shortid;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public Boolean getRestaurant_table_occupancy_status() {
        return restaurant_table_occupancy_status;
    }

    public void setRestaurant_table_occupancy_status(Boolean restaurant_table_occupancy_status) {
        this.restaurant_table_occupancy_status = restaurant_table_occupancy_status;
    }

    public String getRestaurant_table_id() {
        return restaurant_table_id;
    }

    public void setRestaurant_table_id(String restaurant_table_id) {
        this.restaurant_table_id = restaurant_table_id;
    }

    public int getRestaurant_table_tableno() {
        return restaurant_table_tableno;
    }

    public void setRestaurant_table_tableno(int restaurant_table_tableno) {
        this.restaurant_table_tableno = restaurant_table_tableno;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
}
