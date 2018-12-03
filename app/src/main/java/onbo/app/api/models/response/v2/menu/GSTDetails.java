
package onbo.app.api.models.response.v2.menu;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GSTDetails implements Serializable, Parcelable
{

    @SerializedName("SGST")
    @Expose
    private Integer sGST;
    @SerializedName("CGST")
    @Expose
    private Integer cGST;
    @SerializedName("IGST")
    @Expose
    private Integer iGST;
    public final static Creator<GSTDetails> CREATOR = new Creator<GSTDetails>() {


        @SuppressWarnings({
            "unchecked"
        })
        public GSTDetails createFromParcel(Parcel in) {
            return new GSTDetails(in);
        }

        public GSTDetails[] newArray(int size) {
            return (new GSTDetails[size]);
        }

    }
    ;
    private final static long serialVersionUID = 470249487663591877L;

    protected GSTDetails(Parcel in) {
        this.sGST = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.cGST = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.iGST = ((Integer) in.readValue((Integer.class.getClassLoader())));
    }

    public GSTDetails() {
    }

    public Integer getSGST() {
        return sGST;
    }

    public void setSGST(Integer sGST) {
        this.sGST = sGST;
    }

    public Integer getCGST() {
        return cGST;
    }

    public void setCGST(Integer cGST) {
        this.cGST = cGST;
    }

    public Integer getIGST() {
        return iGST;
    }

    public void setIGST(Integer iGST) {
        this.iGST = iGST;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(sGST);
        dest.writeValue(cGST);
        dest.writeValue(iGST);
    }

    public int describeContents() {
        return  0;
    }

}
