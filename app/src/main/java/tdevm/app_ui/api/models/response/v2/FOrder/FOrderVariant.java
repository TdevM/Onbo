package tdevm.app_ui.api.models.response.v2.FOrder;

import android.os.Parcel;
import android.os.Parcelable;

public class FOrderVariant implements Parcelable {

    private String f_o_v_id;

    private String price;

    private String f_o_i_id;

    private String option_name;

    private String option_id;

    protected FOrderVariant(Parcel in) {
        f_o_v_id = in.readString();
        price = in.readString();
        f_o_i_id = in.readString();
        option_name = in.readString();
        option_id = in.readString();
    }

    public static final Creator<FOrderVariant> CREATOR = new Creator<FOrderVariant>() {
        @Override
        public FOrderVariant createFromParcel(Parcel in) {
            return new FOrderVariant(in);
        }

        @Override
        public FOrderVariant[] newArray(int size) {
            return new FOrderVariant[size];
        }
    };

    public String getF_o_v_id() {
        return f_o_v_id;
    }

    public void setF_o_v_id(String f_o_v_id) {
        this.f_o_v_id = f_o_v_id;
    }

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

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [f_o_v_id = " + f_o_v_id + ", price = " + price + ", f_o_i_id = " + f_o_i_id + ", option_name = " + option_name + ", option_id = " + option_id + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(f_o_v_id);
        dest.writeString(price);
        dest.writeString(f_o_i_id);
        dest.writeString(option_name);
        dest.writeString(option_id);
    }
}
