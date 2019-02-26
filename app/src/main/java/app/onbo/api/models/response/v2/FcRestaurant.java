
package app.onbo.api.models.response.v2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FcRestaurant {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("fc_id")
    @Expose
    private String fcId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("restaurant")
    @Expose
    private Restaurant restaurant;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFcId() {
        return fcId;
    }

    public void setFcId(String fcId) {
        this.fcId = fcId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
