
package app.onbo.api.models.response.v2.t_orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TOrderVariant implements Parcelable {

    @SerializedName("order_variant_id")
    @Expose
    private String orderVariantId;
    @SerializedName("option_name")
    @Expose
    private String optionName;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("order_detail_id")
    @Expose
    private String orderDetailId;
    @SerializedName("option_id")
    @Expose
    private String optionId;

    protected TOrderVariant(Parcel in) {
        orderVariantId = in.readString();
        optionName = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        orderDetailId = in.readString();
        optionId = in.readString();
    }

    public static final Creator<TOrderVariant> CREATOR = new Creator<TOrderVariant>() {
        @Override
        public TOrderVariant createFromParcel(Parcel in) {
            return new TOrderVariant(in);
        }

        @Override
        public TOrderVariant[] newArray(int size) {
            return new TOrderVariant[size];
        }
    };

    public String getOrderVariantId() {
        return orderVariantId;
    }

    public void setOrderVariantId(String orderVariantId) {
        this.orderVariantId = orderVariantId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
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

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [price = "+price+", option_name = "+optionName+", order_detail_id = "+orderDetailId+", option_id = "+optionId+", order_variant_id = "+orderVariantId+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderVariantId);
        dest.writeString(optionName);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
        dest.writeString(orderDetailId);
        dest.writeString(optionId);
    }
}
