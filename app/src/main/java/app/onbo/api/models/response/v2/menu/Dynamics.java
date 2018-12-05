
package app.onbo.api.models.response.v2.menu;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dynamics implements Serializable, Parcelable
{

    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("value")
    @Expose
    private String value;
    public final static Creator<Dynamics> CREATOR = new Creator<Dynamics>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Dynamics createFromParcel(Parcel in) {
            return new Dynamics(in);
        }

        public Dynamics[] newArray(int size) {
            return (new Dynamics[size]);
        }

    }
    ;
    private final static long serialVersionUID = -9032478561750546237L;

    protected Dynamics(Parcel in) {
        this.color = ((String) in.readValue((String.class.getClassLoader())));
        this.value = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Dynamics() {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(color);
        dest.writeValue(value);
    }

    public int describeContents() {
        return  0;
    }

}
