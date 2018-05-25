
package tdevm.app_ui.api.models.response.v2.menu;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Taxes implements Serializable, Parcelable
{

    @SerializedName("service_tax")
    @Expose
    private Integer serviceTax;
    @SerializedName("vat")
    @Expose
    private Integer vat;
    @SerializedName("service_charges")
    @Expose
    private Integer serviceCharges;
    @SerializedName("GST")
    @Expose
    private Integer gST;
    @SerializedName("GST_details")
    @Expose
    private GSTDetails gSTDetails;
    public final static Creator<Taxes> CREATOR = new Creator<Taxes>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Taxes createFromParcel(Parcel in) {
            return new Taxes(in);
        }

        public Taxes[] newArray(int size) {
            return (new Taxes[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3913791821517593410L;

    protected Taxes(Parcel in) {
        this.serviceTax = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.vat = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.serviceCharges = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.gST = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.gSTDetails = ((GSTDetails) in.readValue((GSTDetails.class.getClassLoader())));
    }

    public Taxes() {
    }

    public Integer getServiceTax() {
        return serviceTax;
    }

    public void setServiceTax(Integer serviceTax) {
        this.serviceTax = serviceTax;
    }

    public Integer getVat() {
        return vat;
    }

    public void setVat(Integer vat) {
        this.vat = vat;
    }

    public Integer getServiceCharges() {
        return serviceCharges;
    }

    public void setServiceCharges(Integer serviceCharges) {
        this.serviceCharges = serviceCharges;
    }

    public Integer getGST() {
        return gST;
    }

    public void setGST(Integer gST) {
        this.gST = gST;
    }

    public GSTDetails getGSTDetails() {
        return gSTDetails;
    }

    public void setGSTDetails(GSTDetails gSTDetails) {
        this.gSTDetails = gSTDetails;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(serviceTax);
        dest.writeValue(vat);
        dest.writeValue(serviceCharges);
        dest.writeValue(gST);
        dest.writeValue(gSTDetails);
    }

    public int describeContents() {
        return  0;
    }

}
