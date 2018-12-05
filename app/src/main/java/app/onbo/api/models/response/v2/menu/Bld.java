
package app.onbo.api.models.response.v2.menu;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bld implements Serializable, Parcelable
{

    @SerializedName("breakfast")
    @Expose
    private Boolean breakfast;
    @SerializedName("lunch")
    @Expose
    private Boolean lunch;
    @SerializedName("dinner")
    @Expose
    private Boolean dinner;
    public final static Creator<Bld> CREATOR = new Creator<Bld>() {


        @SuppressWarnings({
            "unchecked"
        })
        public Bld createFromParcel(Parcel in) {
            return new Bld(in);
        }

        public Bld[] newArray(int size) {
            return (new Bld[size]);
        }

    }
    ;
    private final static long serialVersionUID = -212163320074153699L;

    protected Bld(Parcel in) {
        this.breakfast = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.lunch = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.dinner = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public Bld() {
    }

    public Boolean getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Boolean breakfast) {
        this.breakfast = breakfast;
    }

    public Boolean getLunch() {
        return lunch;
    }

    public void setLunch(Boolean lunch) {
        this.lunch = lunch;
    }

    public Boolean getDinner() {
        return dinner;
    }

    public void setDinner(Boolean dinner) {
        this.dinner = dinner;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(breakfast);
        dest.writeValue(lunch);
        dest.writeValue(dinner);
    }

    public int describeContents() {
        return  0;
    }

}
