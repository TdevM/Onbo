package tdevm.app_ui.api.models.response;


import android.arch.persistence.room.Embedded;
import android.os.Parcel;
import android.os.Parcelable;

import java.math.BigDecimal;
import java.util.ArrayList;


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
    private Boolean is_deleted;
    private ArrayList<DishVariant> dish_variants;

    public ArrayList<DishVariant> getDish_variants() {
        return dish_variants;
    }

    public void setDish_variants(ArrayList<DishVariant> dish_variants) {
        this.dish_variants = dish_variants;
    }

    public static Creator<DishesOfCuisine> getCREATOR() {
        return CREATOR;
    }

    public DishesOfCuisine() {

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
        byte tmpIs_deleted = in.readByte();
        is_deleted = tmpIs_deleted == 0 ? null : tmpIs_deleted == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dish_image_url);
        if (dish_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(dish_id);
        }
        dest.writeByte((byte) (dish_visibility == null ? 0 : dish_visibility ? 1 : 2));
        dest.writeString(restaurant_uuid);
        dest.writeString(dish_name);
        dest.writeByte((byte) (dish_vegetarian == null ? 0 : dish_vegetarian ? 1 : 2));
        dest.writeString(dish_added_timestamp);
        if (dish_price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(dish_price);
        }
        if (cuisine_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(cuisine_id);
        }
        dest.writeString(dish_details);
        dest.writeByte((byte) (is_customizable == null ? 0 : is_customizable ? 1 : 2));
        dest.writeByte((byte) (is_deleted == null ? 0 : is_deleted ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
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



    @Override
    public int hashCode() {
        int result = dish_image_url != null ? dish_image_url.hashCode() : 0;
        result = 31 * result + (dish_id != null ? dish_id.hashCode() : 0);
        result = 31 * result + (dish_visibility != null ? dish_visibility.hashCode() : 0);
        result = 31 * result + (restaurant_uuid != null ? restaurant_uuid.hashCode() : 0);
        result = 31 * result + (dish_name != null ? dish_name.hashCode() : 0);
        result = 31 * result + (dish_vegetarian != null ? dish_vegetarian.hashCode() : 0);
        result = 31 * result + (dish_added_timestamp != null ? dish_added_timestamp.hashCode() : 0);
        result = 31 * result + (dish_price != null ? dish_price.hashCode() : 0);
        result = 31 * result + (cuisine_id != null ? cuisine_id.hashCode() : 0);
        result = 31 * result + (dish_details != null ? dish_details.hashCode() : 0);
        result = 31 * result + (is_customizable != null ? is_customizable.hashCode() : 0);
        result = 31 * result + (is_deleted != null ? is_deleted.hashCode() : 0);
        return result;
    }


    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }



    public String getDish_image_url() {
        return dish_image_url;
    }

    public Boolean getIs_customizable() {
        return is_customizable;
    }

    public void setIs_customizable(Boolean is_customizable) {
        this.is_customizable = is_customizable;
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
    public String toString() {
        return "DishesOfCuisineChild [dish_image_url = " + dish_image_url + ", dish_id = " + dish_id + ", dish_visibility = " + dish_visibility + ", restaurant_uuid = " + restaurant_uuid + ", dish_name = " + dish_name + ", dish_vegetarian = " + dish_vegetarian + ", dish_added_timestamp = " + dish_added_timestamp + ", dish_price = " + dish_price + ", cuisine_id = " + cuisine_id + ", dish_details = " + dish_details + "]";
    }

}
