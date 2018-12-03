package onbo.app.api.models.request;

/**
 * Created by Tridev on 14-02-2018.
 */

public class AddDishReview {

    private int rating;
    private String text;
    private String restaurant_uuid;
    private Long dish_id;


    public AddDishReview(int rating, String text, String restaurant_uuid, Long dish_id) {
        this.rating = rating;
        this.text = text;
        this.restaurant_uuid = restaurant_uuid;
        this.dish_id = dish_id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public Long getDish_id() {
        return dish_id;
    }

    public void setDish_id(Long dish_id) {
        this.dish_id = dish_id;
    }
}
