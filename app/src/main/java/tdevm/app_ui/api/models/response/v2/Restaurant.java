package tdevm.app_ui.api.models.response.v2;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import tdevm.app_ui.api.models.response.v2.menu.Cuisine;

/**
 * Created by Tridev on 19-08-2017.
 */

public class Restaurant implements Parcelable {
    private String restaurant_id;
    private Boolean does_bookings;
    private Location location;
    private int avg_cost_for_two;
    private String image;
    private String restaurant_name;
    private String restaurant_uuid;
    private int type_id;
    private int table_count;
    private String currency;
    private int location_id;
    private String restaurant_mode;
    private String opens_at;
    private String closes_at;
    private RestaurantType restaurant_type;
    private Rating rating;
    private Boolean qr_active;
    private String address_complete;
    private List<Cuisine> cuisines;


    protected Restaurant(Parcel in) {
        restaurant_id = in.readString();
        byte tmpDoes_bookings = in.readByte();
        does_bookings = tmpDoes_bookings == 0 ? null : tmpDoes_bookings == 1;
        location = in.readParcelable(Location.class.getClassLoader());
        avg_cost_for_two = in.readInt();
        image = in.readString();
        restaurant_name = in.readString();
        restaurant_uuid = in.readString();
        type_id = in.readInt();
        table_count = in.readInt();
        currency = in.readString();
        location_id = in.readInt();
        restaurant_mode = in.readString();
        opens_at = in.readString();
        closes_at = in.readString();
        rating = in.readParcelable(Rating.class.getClassLoader());
        byte tmpQr_active = in.readByte();
        qr_active = tmpQr_active == 0 ? null : tmpQr_active == 1;
        address_complete = in.readString();
        cuisines = in.createTypedArrayList(Cuisine.CREATOR);
    }

    public static final Creator<Restaurant> CREATOR = new Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel in) {
            return new Restaurant(in);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };

    public String getAddress_complete() {
        return address_complete;
    }

    public void setAddress_complete(String address_complete) {
        this.address_complete = address_complete;
    }

    public List<Cuisine> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine> cuisines) {
        this.cuisines = cuisines;
    }

    public Boolean getQr_active() {
        return qr_active;
    }

    public void setQr_active(Boolean qr_active) {
        this.qr_active = qr_active;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurant_id);
        dest.writeByte((byte) (does_bookings == null ? 0 : does_bookings ? 1 : 2));
        dest.writeParcelable(location, flags);
        dest.writeInt(avg_cost_for_two);
        dest.writeString(image);
        dest.writeString(restaurant_name);
        dest.writeString(restaurant_uuid);
        dest.writeInt(type_id);
        dest.writeInt(table_count);
        dest.writeString(currency);
        dest.writeInt(location_id);
        dest.writeString(restaurant_mode);
        dest.writeString(opens_at);
        dest.writeString(closes_at);
        dest.writeParcelable(rating, flags);
        dest.writeByte((byte) (qr_active == null ? 0 : qr_active ? 1 : 2));
        dest.writeString(address_complete);
        dest.writeTypedList(cuisines);
    }


    public class RestaurantType {
        private String type_id;
        private String type_name;


        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }
    }





    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }

    public Boolean getDoes_bookings() {
        return does_bookings;
    }

    public void setDoes_bookings(Boolean does_bookings) {
        this.does_bookings = does_bookings;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getAvg_cost_for_two() {
        return avg_cost_for_two;
    }

    public void setAvg_cost_for_two(int avg_cost_for_two) {
        this.avg_cost_for_two = avg_cost_for_two;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public RestaurantType getRestaurant_type() {
        return restaurant_type;
    }

    public void setRestaurant_type(RestaurantType restaurant_type) {
        this.restaurant_type = restaurant_type;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public int getType_id() {
        return type_id;
    }

    public void setType_id(int type_id) {
        this.type_id = type_id;
    }

    public int getTable_count() {
        return table_count;
    }

    public void setTable_count(int table_count) {
        this.table_count = table_count;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public String getRestaurant_mode() {
        return restaurant_mode;
    }

    public void setRestaurant_mode(String restaurant_mode) {
        this.restaurant_mode = restaurant_mode;
    }

    public String getOpens_at() {
        return opens_at;
    }

    public void setOpens_at(String opens_at) {
        this.opens_at = opens_at;
    }

    public String getCloses_at() {
        return closes_at;
    }

    public void setCloses_at(String closes_at) {
        this.closes_at = closes_at;
    }
}
