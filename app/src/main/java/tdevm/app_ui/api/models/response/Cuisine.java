package tdevm.app_ui.api.models.response;

/**
 * Created by Tridev on 23-08-2017.
 */

public class Cuisine {

    private String cuisine_added_timestamp;
    private String restaurant_uuid;
    private String cuisine_type;
    private Long cuisine_id;
    private Boolean cuisine_visibility;
    private Boolean is_deleted;

    public Boolean getCuisine_visibility() {
        return cuisine_visibility;
    }

    public void setCuisine_visibility(Boolean cuisine_visibility) {
        this.cuisine_visibility = cuisine_visibility;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getCuisine_added_timestamp() {
        return cuisine_added_timestamp;
    }

    public void setCuisine_added_timestamp(String cuisine_added_timestamp) {
        this.cuisine_added_timestamp = cuisine_added_timestamp;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public String getCuisine_type() {
        return cuisine_type;
    }

    public void setCuisine_type(String cuisine_type) {
        this.cuisine_type = cuisine_type;
    }

    public Long getCuisine_id() {
        return cuisine_id;
    }

    public void setCuisine_id(Long cuisine_id) {
        this.cuisine_id = cuisine_id;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [cuisine_added_timestamp = "+cuisine_added_timestamp+", restaurant_uuid = "+restaurant_uuid+", cuisine_type = "+cuisine_type+", cuisine_id = "+cuisine_id+"]";
    }
}
