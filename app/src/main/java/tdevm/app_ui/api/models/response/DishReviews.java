package tdevm.app_ui.api.models.response;

import tdevm.app_ui.api.models.request.User;

/**
 * Created by Tridev on 14-02-2018.
 */

public class DishReviews {
    private Dish dish;

    private String reviews_dish_rating;

    private String dish_id;

    private String restaurant_uuid;

    private String user_mobile;

    private String reviews_dish_text;

    private String reviews_dish_id;

    private String reviews_dish_time;

    private User user;

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getReviews_dish_rating() {
        return reviews_dish_rating;
    }

    public void setReviews_dish_rating(String reviews_dish_rating) {
        this.reviews_dish_rating = reviews_dish_rating;
    }

    public String getDish_id() {
        return dish_id;
    }

    public void setDish_id(String dish_id) {
        this.dish_id = dish_id;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public String getUser_mobile() {
        return user_mobile;
    }

    public void setUser_mobile(String user_mobile) {
        this.user_mobile = user_mobile;
    }

    public String getReviews_dish_text() {
        return reviews_dish_text;
    }

    public void setReviews_dish_text(String reviews_dish_text) {
        this.reviews_dish_text = reviews_dish_text;
    }

    public String getReviews_dish_id() {
        return reviews_dish_id;
    }

    public void setReviews_dish_id(String reviews_dish_id) {
        this.reviews_dish_id = reviews_dish_id;
    }

    public String getReviews_dish_time() {
        return reviews_dish_time;
    }

    public void setReviews_dish_time(String reviews_dish_time) {
        this.reviews_dish_time = reviews_dish_time;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ClassPojo [dish = " + dish + ", reviews_dish_rating = " + reviews_dish_rating + ", dish_id = " + dish_id + ", restaurant_uuid = " + restaurant_uuid + ", user_mobile = " + user_mobile + ", reviews_dish_text = " + reviews_dish_text + ", reviews_dish_id = " + reviews_dish_id + ", reviews_dish_time = " + reviews_dish_time + ", user = " + user + "]";
    }
}
