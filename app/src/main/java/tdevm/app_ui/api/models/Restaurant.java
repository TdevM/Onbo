package tdevm.app_ui.api.models;

import tdevm.app_ui.api.models.Location;

/**
 * Created by Tridev on 19-08-2017.
 */

public class Restaurant {
    private String restaurant_uuid;
    private Boolean restaurants_booking_allowed;
    private Location location;
    private int restaurants_avg_cost_for_two;
    private String restaurant_image_url;
    private String restaurants_name;
    private int restaurant_id;
    private int restaurant_type_id;
    private int restaurants_no_of_tables;
    private String restaurants_currency;
    private int location_id;

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public Boolean getRestaurants_booking_allowed() {
        return restaurants_booking_allowed;
    }

    public void setRestaurants_booking_allowed(Boolean restaurants_booking_allowed) {
        this.restaurants_booking_allowed = restaurants_booking_allowed;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getRestaurants_avg_cost_for_two() {
        return restaurants_avg_cost_for_two;
    }

    public void setRestaurants_avg_cost_for_two(int restaurants_avg_cost_for_two) {
        this.restaurants_avg_cost_for_two = restaurants_avg_cost_for_two;
    }

    public String getRestaurant_image_url() {
        return restaurant_image_url;
    }

    public void setRestaurant_image_url(String restaurant_image_url) {
        this.restaurant_image_url = restaurant_image_url;
    }

    public String getRestaurants_name() {
        return restaurants_name;
    }

    public void setRestaurants_name(String restaurants_name) {
        this.restaurants_name = restaurants_name;
    }

    public int getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(int restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public int getRestaurant_type_id() {
        return restaurant_type_id;
    }

    public void setRestaurant_type_id(int restaurant_type_id) {
        this.restaurant_type_id = restaurant_type_id;
    }

    public int getRestaurants_no_of_tables() {
        return restaurants_no_of_tables;
    }

    public void setRestaurants_no_of_tables(int restaurants_no_of_tables) {
        this.restaurants_no_of_tables = restaurants_no_of_tables;
    }

    public String getRestaurants_currency() {
        return restaurants_currency;
    }

    public void setRestaurants_currency(String restaurants_currency) {
        this.restaurants_currency = restaurants_currency;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }
}
