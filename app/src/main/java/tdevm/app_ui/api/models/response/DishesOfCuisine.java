package tdevm.app_ui.api.models.response;


import android.arch.persistence.room.Embedded;
import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;


/**
 * Created by Tridev on 26-08-2017.
 */

public class DishesOfCuisine  implements Parcelable {

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
    private Boolean is_customizable;
    private Boolean is_parent;
    private Boolean is_child;
    private Long parent_id;


    public static Creator<DishesOfCuisine> getCREATOR() {
        return CREATOR;
    }

    public DishesOfCuisine(){

    }

    protected DishesOfCuisine(Parcel in) {
        dish_image_url = in.readString();
        if (in.readByte() == 0) {
            dish_id = null;
        } else {
            dish_id = in.readLong();
        }
        byte tmpDish_visibility = in.readByte();
        dish_visibility = tmpDish_visibility == 0 ? null : tmpDish_visibility == 1;
        restaurant_uuid = in.readString();
        dish_name = in.readString();
        byte tmpDish_vegetarian = in.readByte();
        dish_vegetarian = tmpDish_vegetarian == 0 ? null : tmpDish_vegetarian == 1;
        dish_added_timestamp = in.readString();
        if (in.readByte() == 0) {
            dish_price = null;
        } else {
            dish_price = in.readDouble();
        }
        if (in.readByte() == 0) {
            cuisine_id = null;
        } else {
            cuisine_id = in.readLong();
        }
        dish_details = in.readString();
        byte tmpIs_customizable = in.readByte();
        is_customizable = tmpIs_customizable == 0 ? null : tmpIs_customizable == 1;
        byte tmpIs_parent = in.readByte();
        is_parent = tmpIs_parent == 0 ? null : tmpIs_parent == 1;
        byte tmpIs_child = in.readByte();
        is_child = tmpIs_child == 0 ? null : tmpIs_child == 1;
        if (in.readByte() == 0) {
            parent_id = null;
        } else {
            parent_id = in.readLong();
        }
    }

    public static final Creator<DishesOfCuisine> CREATOR = new Creator<DishesOfCuisine>() {
        @Override
        public DishesOfCuisine createFromParcel(Parcel in) {
            return new DishesOfCuisine(in);
        }

        @Override
        public DishesOfCuisine[] newArray(int size) {
            return new DishesOfCuisine[size];
        }
    };

    public String getDish_image_url() {
        return dish_image_url;
    }

    public Boolean getIs_customizable() {
        return is_customizable;
    }

    public void setIs_customizable(Boolean is_customizable) {
        this.is_customizable = is_customizable;
    }

    public Boolean getIs_parent() {
        return is_parent;
    }

    public void setIs_parent(Boolean is_parent) {
        this.is_parent = is_parent;
    }

    public Boolean getIs_child() {
        return is_child;
    }

    public void setIs_child(Boolean is_child) {
        this.is_child = is_child;
    }

    public Long getParent_id() {
        return parent_id;
    }

    public void setParent_id(Long parent_id) {
        this.parent_id = parent_id;
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


    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (!(o instanceof DishesOfCuisine)) return false;

        return (this.dish_id == ((DishesOfCuisine) o).getDish_id());
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 1;
        hash = hash * prime + dish_id.intValue();
        hash = hash * prime + (dish_name == null ? 0 : dish_name.hashCode());
        hash = hash * prime + (dish_price == null ? 0 : dish_price.hashCode());
        hash = hash * prime + (dish_details == null ? 0 : dish_details.hashCode());

        return hash;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dish_image_url);
        if (dish_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(dish_id);
        }
        parcel.writeByte((byte) (dish_visibility == null ? 0 : dish_visibility ? 1 : 2));
        parcel.writeString(restaurant_uuid);
        parcel.writeString(dish_name);
        parcel.writeByte((byte) (dish_vegetarian == null ? 0 : dish_vegetarian ? 1 : 2));
        parcel.writeString(dish_added_timestamp);
        if (dish_price == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(dish_price);
        }
        if (cuisine_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(cuisine_id);
        }
        parcel.writeString(dish_details);
        parcel.writeByte((byte) (is_customizable == null ? 0 : is_customizable ? 1 : 2));
        parcel.writeByte((byte) (is_parent == null ? 0 : is_parent ? 1 : 2));
        parcel.writeByte((byte) (is_child == null ? 0 : is_child ? 1 : 2));
        if (parent_id == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeLong(parent_id);
        }
    }
}
