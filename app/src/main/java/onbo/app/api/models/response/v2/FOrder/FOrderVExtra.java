package onbo.app.api.models.response.v2.FOrder;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FOrderVExtra implements Parcelable {

    @SerializedName("f_o_v_e_id")
    @Expose
    private String tExtraId;
    @SerializedName("option_extra")
    @Expose
    private String optionExtra;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("f_o_i_id")
    @Expose
    private String orderDetailId;
    @SerializedName("extra_id")
    @Expose
    private String extraId;

    protected FOrderVExtra(Parcel in) {
        tExtraId = in.readString();
        optionExtra = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        orderDetailId = in.readString();
        extraId = in.readString();
    }

    public static final Creator<FOrderVExtra> CREATOR = new Creator<FOrderVExtra>() {
        @Override
        public FOrderVExtra createFromParcel(Parcel in) {
            return new FOrderVExtra(in);
        }

        @Override
        public FOrderVExtra[] newArray(int size) {
            return new FOrderVExtra[size];
        }
    };

    public String gettExtraId() {
        return tExtraId;
    }

    public void settExtraId(String tExtraId) {
        this.tExtraId = tExtraId;
    }

    public String getOptionExtra() {
        return optionExtra;
    }

    public void setOptionExtra(String optionExtra) {
        this.optionExtra = optionExtra;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(String orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getExtraId() {
        return extraId;
    }

    public void setExtraId(String extraId) {
        this.extraId = extraId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tExtraId);
        dest.writeString(optionExtra);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
        dest.writeString(orderDetailId);
        dest.writeString(extraId);
    }
}
