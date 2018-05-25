
package tdevm.app_ui.api.models.response.v2.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ItemDetail implements Serializable, Parcelable
{

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("availability")
    @Expose
    private List<Availability> availability = null;
    @SerializedName("bld")
    @Expose
    private Bld bld;
    @SerializedName("taxes")
    @Expose
    private Taxes taxes;
    @SerializedName("is_gst_incl")
    @Expose
    private Boolean isGstIncl;
    @SerializedName("dynamics")
    @Expose
    private Dynamics dynamics;


    public ItemDetail() {
    }

    protected ItemDetail(Parcel in) {
        id = in.readString();
        itemId = in.readString();
        bld = in.readParcelable(Bld.class.getClassLoader());
        taxes = in.readParcelable(Taxes.class.getClassLoader());
        byte tmpIsGstIncl = in.readByte();
        isGstIncl = tmpIsGstIncl == 0 ? null : tmpIsGstIncl == 1;
        dynamics = in.readParcelable(Dynamics.class.getClassLoader());
    }

    public static final Creator<ItemDetail> CREATOR = new Creator<ItemDetail>() {
        @Override
        public ItemDetail createFromParcel(Parcel in) {
            return new ItemDetail(in);
        }

        @Override
        public ItemDetail[] newArray(int size) {
            return new ItemDetail[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public List<Availability> getAvailability() {
        return availability;
    }

    public void setAvailability(List<Availability> availability) {
        this.availability = availability;
    }

    public Bld getBld() {
        return bld;
    }

    public void setBld(Bld bld) {
        this.bld = bld;
    }

    public Taxes getTaxes() {
        return taxes;
    }

    public void setTaxes(Taxes taxes) {
        this.taxes = taxes;
    }

    public Boolean getIsGstIncl() {
        return isGstIncl;
    }

    public void setIsGstIncl(Boolean isGstIncl) {
        this.isGstIncl = isGstIncl;
    }

    public Dynamics getDynamics() {
        return dynamics;
    }

    public void setDynamics(Dynamics dynamics) {
        this.dynamics = dynamics;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(itemId);
        dest.writeParcelable(bld, flags);
        dest.writeParcelable(taxes, flags);
        dest.writeByte((byte) (isGstIncl == null ? 0 : isGstIncl ? 1 : 2));
        dest.writeParcelable(dynamics, flags);
    }
}
