package tdevm.app_ui.api.models.response.v2;


/**
 * Created by Tridev on 19-08-2017.
 */

public class Restaurant {
    private String restaurant_id;
    private Boolean does_bookings;
    private Location location;
    private int avg_cost_for_two;
    private String image;
    private String restaurants_name;
    private String restaurant_uuid;
    private int type_id;
    private int table_count;
    private String currency;
    private int location_id;
    private String restaurant_mode;
    private String opens_at;
    private String closes_at;
    private RestaurantType restaurant_type;

    public class RestaurantType{
        private String type_id;
        private String type_name;


        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }


    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Boolean getDoes_bookings() {
        return does_bookings;
    }

    public void setDoes_bookings(Boolean does_bookings) {
        this.does_bookings = does_bookings;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getAvg_cost_for_two() {
        return avg_cost_for_two;
    }

    public void setAvg_cost_for_two(int avg_cost_for_two) {
        this.avg_cost_for_two = avg_cost_for_two;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRestaurants_name() {
        return restaurants_name;
    }

    public void setRestaurants_name(String restaurants_name) {
        this.restaurants_name = restaurants_name;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getTable_count() {
        return table_count;
    }

    public void setTable_count(int table_count) {
        this.table_count = table_count;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getRestaurant_mode() {
        return restaurant_mode;
    }

    public void setRestaurant_mode(String restaurant_mode) {
        this.restaurant_mode = restaurant_mode;
    }

    public String getOpens_at() {
        return opens_at;
    }

    public void setOpens_at(String opens_at) {
        this.opens_at = opens_at;
    }

    public String getCloses_at() {
        return closes_at;
    }

    public void setCloses_at(String closes_at) {
        this.closes_at = closes_at;
    }
}
