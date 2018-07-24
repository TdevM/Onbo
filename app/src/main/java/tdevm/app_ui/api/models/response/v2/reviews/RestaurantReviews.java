package tdevm.app_ui.api.models.response.v2.reviews;

import tdevm.app_ui.api.models.request.User;

/**
 * Created by Tridev on 14-02-2018.
 */

public class RestaurantReviews {
    private String rating;

    private String id;

    private String timestamp;

    private String restaurant_id;

    private String user_id;

    private User user;

    private String text;

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "ClassPojo [rating = " + rating + ", id = " + id + ", timestamp = " + timestamp + ", restaurant_id = " + restaurant_id + ", user_id = " + user_id + ", user = " + user + ", text = " + text + "]";
    }
}
