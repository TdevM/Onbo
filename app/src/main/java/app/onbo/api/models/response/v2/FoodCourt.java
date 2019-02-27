
package app.onbo.api.models.response.v2;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodCourt implements Parcelable {

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

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @SerializedName("fc_uuid")
    @Expose
    private String fcUuid;


    @SerializedName("location")
    @Expose
    private Location location;

    protected FoodCourt(Parcel in) {
        fcId = in.readString();
        fcName = in.readString();
        locationId = in.readString();
        addressComplete = in.readString();
        imageUrl = in.readString();
        fcUuid = in.readString();
        location = in.readParcelable(Location.class.getClassLoader());
    }

    public static final Creator<FoodCourt> CREATOR = new Creator<FoodCourt>() {
        @Override
        public FoodCourt createFromParcel(Parcel in) {
            return new FoodCourt(in);
        }

        @Override
        public FoodCourt[] newArray(int size) {
            return new FoodCourt[size];
        }
    };

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


    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fcId);
        dest.writeString(fcName);
        dest.writeString(locationId);
        dest.writeString(addressComplete);
        dest.writeString(imageUrl);
        dest.writeString(fcUuid);
        dest.writeParcelable(location, flags);
    }
}
