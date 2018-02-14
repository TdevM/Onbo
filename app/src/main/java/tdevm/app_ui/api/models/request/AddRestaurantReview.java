package tdevm.app_ui.api.models.request;

/**
 * Created by Tridev on 14-02-2018.
 */

public class AddRestaurantReview {

    private int rating;
    private String text;
    private String uuid;

    public AddRestaurantReview(int rating, String text, String uuid) {
        this.rating = rating;
        this.text = text;
        this.uuid = uuid;
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

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
