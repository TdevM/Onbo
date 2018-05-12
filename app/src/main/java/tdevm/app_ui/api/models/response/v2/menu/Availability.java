
package tdevm.app_ui.api.models.response.v2.menu;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Availability implements Serializable, Parcelable
{

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    public final static Creator<Availability> CREATOR = new Creator<Availability>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Availability createFromParcel(Parcel in) {
            return new Availability(in);
        }

        public Availability[] newArray(int size) {
            return (new Availability[size]);
        }

    }
    ;
    private final static long serialVersionUID = -6822462789532368548L;

    protected Availability(Parcel in) {
        this.day = ((String) in.readValue((String.class.getClassLoader())));
        this.available = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.from = ((String) in.readValue((String.class.getClassLoader())));
        this.to = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Availability() {
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(day);
        dest.writeValue(available);
        dest.writeValue(from);
        dest.writeValue(to);
    }

    public int describeContents() {
        return  0;
    }

}
