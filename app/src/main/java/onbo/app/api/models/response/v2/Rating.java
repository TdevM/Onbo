package onbo.app.api.models.response.v2;

import android.os.Parcel;
import android.os.Parcelable;

public class Rating implements Parcelable {
    private String restaurant_avg_rating;


    protected Rating(Parcel in) {
        restaurant_avg_rating = in.readString();
    }

    public static final Creator<Rating> CREATOR = new Creator<Rating>() {
        @Override
        public Rating createFromParcel(Parcel in) {
            return new Rating(in);
        }

        @Override
        public Rating[] newArray(int size) {
            return new Rating[size];
        }
    };

    public String getRestaurant_avg_rating() {
        return restaurant_avg_rating;
    }

    public void setRestaurant_avg_rating(String restaurant_avg_rating) {
        this.restaurant_avg_rating = restaurant_avg_rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(restaurant_avg_rating);
    }
}