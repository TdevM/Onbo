package app.onbo.api.models.response.v2.menu;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tridev on 23-08-2017.
 */

public class Cuisine implements Parcelable {

    private String added_at;
    private String restaurant_id;
    private String cuisine_name;
    private Long cuisine_id;

    protected Cuisine(Parcel in) {
        added_at = in.readString();
        restaurant_id = in.readString();
        cuisine_name = in.readString();
        if (in.readByte() == 0) {
            cuisine_id = null;
        } else {
            cuisine_id = in.readLong();
        }
    }

    public static final Creator<Cuisine> CREATOR = new Creator<Cuisine>() {
        @Override
        public Cuisine createFromParcel(Parcel in) {
            return new Cuisine(in);
        }

        @Override
        public Cuisine[] newArray(int size) {
            return new Cuisine[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(added_at);
        dest.writeString(restaurant_id);
        dest.writeString(cuisine_name);
        if (cuisine_id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(cuisine_id);
        }
    }
}
