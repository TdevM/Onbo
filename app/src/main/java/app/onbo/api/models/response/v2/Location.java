package app.onbo.api.models.response.v2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tridev on 19-08-2017.
 */

public class Location implements Parcelable {
    private String city_id;
    private String location_lat;
    private String location_locality;
    private String country_id;
    private String location_address;
    private String location_zipcode;
    private String location_long;
    private String location_id;
    private String state_id;

    protected Location(Parcel in) {
        city_id = in.readString();
        location_lat = in.readString();
        location_locality = in.readString();
        country_id = in.readString();
        location_address = in.readString();
        location_zipcode = in.readString();
        location_long = in.readString();
        location_id = in.readString();
        state_id = in.readString();
    }

    public static final Creator<Location> CREATOR = new Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel in) {
            return new Location(in);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }
    public String getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(String location_lat) {
        this.location_lat = location_lat;
    }

    public String getLocation_locality() {
        return location_locality;
    }

    public void setLocation_locality(String location_locality) {
        this.location_locality = location_locality;
    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getLocation_address() {
        return location_address;
    }

    public void setLocation_address(String location_address) {
        this.location_address = location_address;
    }

    public String getLocation_zipcode() {
        return location_zipcode;
    }

    public void setLocation_zipcode(String location_zipcode) {
        this.location_zipcode = location_zipcode;
    }

    public String getLocation_long() {
        return location_long;
    }

    public void setLocation_long(String location_long) {
        this.location_long = location_long;
    }

    public String getLocation_id() {
        return location_id;
    }

    public void setLocation_id(String location_id) {
        this.location_id = location_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [city_id = " + city_id + ", location_lat = " + location_lat + ", location_locality = " + location_locality + ", country_id = " + country_id +  ", location_address = " + location_address + ", location_zipcode = " + location_zipcode + ", location_long = " + location_long + ", location_id = " + location_id + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(city_id);
        dest.writeString(location_lat);
        dest.writeString(location_locality);
        dest.writeString(country_id);
        dest.writeString(location_address);
        dest.writeString(location_zipcode);
        dest.writeString(location_long);
        dest.writeString(location_id);
        dest.writeString(state_id);
    }
}