package tdevm.app_ui.api.models.response.v2.FOrder;

import android.os.Parcel;
import android.os.Parcelable;

public class FOrderAddOn implements Parcelable {


    private String price;

    private String f_o_i_id;

    private String add_on_name;

    private String add_on_id;

    private String f_o_ad_id;

    protected FOrderAddOn(Parcel in) {
        price = in.readString();
        f_o_i_id = in.readString();
        add_on_name = in.readString();
        add_on_id = in.readString();
        f_o_ad_id = in.readString();
    }

    public static final Creator<FOrderAddOn> CREATOR = new Creator<FOrderAddOn>() {
        @Override
        public FOrderAddOn createFromParcel(Parcel in) {
            return new FOrderAddOn(in);
        }

        @Override
        public FOrderAddOn[] newArray(int size) {
            return new FOrderAddOn[size];
        }
    };

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getF_o_i_id() {
        return f_o_i_id;
    }

    public void setF_o_i_id(String f_o_i_id) {
        this.f_o_i_id = f_o_i_id;
    }

    public String getAdd_on_name() {
        return add_on_name;
    }

    public void setAdd_on_name(String add_on_name) {
        this.add_on_name = add_on_name;
    }

    public String getAdd_on_id() {
        return add_on_id;
    }

    public void setAdd_on_id(String add_on_id) {
        this.add_on_id = add_on_id;
    }

    public String getF_o_ad_id() {
        return f_o_ad_id;
    }

    public void setF_o_ad_id(String f_o_ad_id) {
        this.f_o_ad_id = f_o_ad_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [price = " + price + ", f_o_i_id = " + f_o_i_id + ", add_on_name = " + add_on_name + ", add_on_id = " + add_on_id + ", f_o_ad_id = " + f_o_ad_id + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(price);
        dest.writeString(f_o_i_id);
        dest.writeString(add_on_name);
        dest.writeString(add_on_id);
        dest.writeString(f_o_ad_id);
    }
}
