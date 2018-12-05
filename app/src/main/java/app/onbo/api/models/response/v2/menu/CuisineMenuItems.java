package app.onbo.api.models.response.v2.menu;

import java.util.List;

public class CuisineMenuItems {

    private String added_at;
    private String restaurant_id;
    private String cuisine_name;
    private Long cuisine_id;
    private List<MenuItem> menu_items;


    public String getAdded_at() {
        return added_at;
    }

    public void setAdded_at(String added_at) {
        this.added_at = added_at;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public String getCuisine_name() {
        return cuisine_name;
    }

    public void setCuisine_name(String cuisine_name) {
        this.cuisine_name = cuisine_name;
    }

    public Long getCuisine_id() {
        return cuisine_id;
    }

    public void setCuisine_id(Long cuisine_id) {
        this.cuisine_id = cuisine_id;
    }

    public List<MenuItem> getMenu_items() {
        return menu_items;
    }

    public void setMenu_items(List<MenuItem> menu_items) {
        this.menu_items = menu_items;
    }

    @Override
    public String toString() {
        return "CuisineMenuItems{" +
                "added_at='" + added_at + '\'' +
                ", restaurant_id='" + restaurant_id + '\'' +
                ", cuisine_name='" + cuisine_name + '\'' +
                ", cuisine_id=" + cuisine_id +
                ", menu_items=" + menu_items +
                '}';
    }
}
