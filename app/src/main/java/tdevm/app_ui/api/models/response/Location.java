package tdevm.app_ui.api.models.response;

/**
 * Created by Tridev on 19-08-2017.
 */

public class Location {
    private String city_id;
    private String location_lat;
    private String location_locality;
    private String country_id;
    private String[] location_phonenumbers;
    private String location_address;
    private String location_zipcode;
    private String location_long;
    private String location_id;

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

    public String[] getLocation_phonenumbers() {
        return location_phonenumbers;
    }

    public void setLocation_phonenumbers(String[] location_phonenumbers) {
        this.location_phonenumbers = location_phonenumbers;
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
        return "ClassPojo [city_id = " + city_id + ", location_lat = " + location_lat + ", location_locality = " + location_locality + ", country_id = " + country_id + ", location_phonenumbers = " + location_phonenumbers + ", location_address = " + location_address + ", location_zipcode = " + location_zipcode + ", location_long = " + location_long + ", location_id = " + location_id + "]";
    }
}