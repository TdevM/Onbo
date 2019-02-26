
package app.onbo.api.models.response.v2;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodCourt {

    @SerializedName("fc_id")
    @Expose
    private String fcId;
    @SerializedName("fc_name")
    @Expose
    private String fcName;
    @SerializedName("location_id")
    @Expose
    private String locationId;
    @SerializedName("address_complete")
    @Expose
    private String addressComplete;
    @SerializedName("fc_uuid")
    @Expose
    private String fcUuid;
    @SerializedName("fc_restaurants")
    @Expose
    private List<FcRestaurant> fcRestaurants = null;
    @SerializedName("location")
    @Expose
    private Location location;

    public String getFcId() {
        return fcId;
    }

    public void setFcId(String fcId) {
        this.fcId = fcId;
    }

    public String getFcName() {
        return fcName;
    }

    public void setFcName(String fcName) {
        this.fcName = fcName;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getAddressComplete() {
        return addressComplete;
    }

    public void setAddressComplete(String addressComplete) {
        this.addressComplete = addressComplete;
    }

    public String getFcUuid() {
        return fcUuid;
    }

    public void setFcUuid(String fcUuid) {
        this.fcUuid = fcUuid;
    }

    public List<FcRestaurant> getFcRestaurants() {
        return fcRestaurants;
    }

    public void setFcRestaurants(List<FcRestaurant> fcRestaurants) {
        this.fcRestaurants = fcRestaurants;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

}
