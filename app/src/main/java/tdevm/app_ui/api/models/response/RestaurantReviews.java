package tdevm.app_ui.api.models.response;

import tdevm.app_ui.api.models.request.User;

/**
 * Created by Tridev on 14-02-2018.
 */

public class RestaurantReviews {
    private String reviews_restaurants_rating;

    private String reviews_restaurants_review_id;

    private String reviews_restaurants_time;

    private String reviews_restaurants_restaurant_uuid;

    private String reviews_restaurants_user_mobile;

    private User user;

    private String reviews_restaurants_text;

    public String getReviews_restaurants_rating() {
        return reviews_restaurants_rating;
    }

    public void setReviews_restaurants_rating(String reviews_restaurants_rating) {
        this.reviews_restaurants_rating = reviews_restaurants_rating;
    }

    public String getReviews_restaurants_review_id() {
        return reviews_restaurants_review_id;
    }

    public void setReviews_restaurants_review_id(String reviews_restaurants_review_id) {
        this.reviews_restaurants_review_id = reviews_restaurants_review_id;
    }

    public String getReviews_restaurants_time() {
        return reviews_restaurants_time;
    }

    public void setReviews_restaurants_time(String reviews_restaurants_time) {
        this.reviews_restaurants_time = reviews_restaurants_time;
    }

    public String getReviews_restaurants_restaurant_uuid() {
        return reviews_restaurants_restaurant_uuid;
    }

    public void setReviews_restaurants_restaurant_uuid(String reviews_restaurants_restaurant_uuid) {
        this.reviews_restaurants_restaurant_uuid = reviews_restaurants_restaurant_uuid;
    }

    public String getReviews_restaurants_user_mobile() {
        return reviews_restaurants_user_mobile;
    }

    public void setReviews_restaurants_user_mobile(String reviews_restaurants_user_mobile) {
        this.reviews_restaurants_user_mobile = reviews_restaurants_user_mobile;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReviews_restaurants_text() {
        return reviews_restaurants_text;
    }

    public void setReviews_restaurants_text(String reviews_restaurants_text) {
        this.reviews_restaurants_text = reviews_restaurants_text;
    }

    @Override
    public String toString() {
        return "ClassPojo [reviews_restaurants_rating = " + reviews_restaurants_rating + ", reviews_restaurants_review_id = " + reviews_restaurants_review_id + ", reviews_restaurants_time = " + reviews_restaurants_time + ", reviews_restaurants_restaurant_uuid = " + reviews_restaurants_restaurant_uuid + ", reviews_restaurants_user_mobile = " + reviews_restaurants_user_mobile + ", user = " + user + ", reviews_restaurants_text = " + reviews_restaurants_text + "]";
    }
}
