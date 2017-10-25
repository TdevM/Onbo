package tdevm.app_ui.api.models.response;

/**
 * Created by Tridev on 26-08-2017.
 */

public class DishesOfCuisine {


    private String dish_image_url;
    private Long dish_id;
    private Boolean dish_visibility;
    private String restaurant_uuid;
    private String dish_name;
    private Boolean dish_vegetarian;
    private String dish_added_timestamp;
    private Double dish_price;
    private Long cuisine_id;
    private String dish_details;


    public String getDish_image_url() {
        return dish_image_url;
    }

    public void setDish_image_url(String dish_image_url) {
        this.dish_image_url = dish_image_url;
    }

    public Long getDish_id() {
        return dish_id;
    }

    public void setDish_id(Long dish_id) {
        this.dish_id = dish_id;
    }

    public Boolean getDish_visibility() {
        return dish_visibility;
    }

    public void setDish_visibility(Boolean dish_visibility) {
        this.dish_visibility = dish_visibility;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public String getDish_name() {
        return dish_name;
    }

    public void setDish_name(String dish_name) {
        this.dish_name = dish_name;
    }

    public Boolean getDish_vegetarian() {
        return dish_vegetarian;
    }

    public void setDish_vegetarian(Boolean dish_vegetarian) {
        this.dish_vegetarian = dish_vegetarian;
    }

    public String getDish_added_timestamp() {
        return dish_added_timestamp;
    }

    public void setDish_added_timestamp(String dish_added_timestamp) {
        this.dish_added_timestamp = dish_added_timestamp;
    }

    public Double getDish_price() {
        return dish_price;
    }

    public void setDish_price(Double dish_price) {
        this.dish_price = dish_price;
    }

    public Long getCuisine_id() {
        return cuisine_id;
    }

    public void setCuisine_id(Long cuisine_id) {
        this.cuisine_id = cuisine_id;
    }

    public String getDish_details() {
        return dish_details;
    }

    public void setDish_details(String dish_details) {
        this.dish_details = dish_details;
    }

    @Override
    public String toString()
    {
        return "DishesOfCuisine [dish_image_url = "+dish_image_url+", dish_id = "+dish_id+", dish_visibility = "+dish_visibility+", restaurant_uuid = "+restaurant_uuid+", dish_name = "+dish_name+", dish_vegetarian = "+dish_vegetarian+", dish_added_timestamp = "+dish_added_timestamp+", dish_price = "+dish_price+", cuisine_id = "+cuisine_id+", dish_details = "+dish_details+"]";
    }
}
