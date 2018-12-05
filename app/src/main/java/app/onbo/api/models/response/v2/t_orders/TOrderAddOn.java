
package app.onbo.api.models.response.v2.t_orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TOrderAddOn implements Parcelable {

    @SerializedName("order_add_on_id")
    @Expose
    private String orderAddOnId;
    @SerializedName("add_on_name")
    @Expose
    private String addOnName;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("order_detail_id")
    @Expose
    private String orderDetailId;
    @SerializedName("add_on_id")
    @Expose
    private String addOnId;

    protected TOrderAddOn(Parcel in) {
        orderAddOnId = in.readString();
        addOnName = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readInt();
        }
        orderDetailId = in.readString();
        addOnId = in.readString();
    }

    public static final Creator<TOrderAddOn> CREATOR = new Creator<TOrderAddOn>() {
        @Override
        public TOrderAddOn createFromParcel(Parcel in) {
            return new TOrderAddOn(in);
        }

        @Override
        public TOrderAddOn[] newArray(int size) {
            return new TOrderAddOn[size];
        }
    };

    public String getOrderAddOnId() {
        return orderAddOnId;
    }

    public void setOrderAddOnId(String orderAddOnId) {
        this.orderAddOnId = orderAddOnId;
    }

    public String getAddOnName() {
        return addOnName;
    }

    public void setAddOnName(String addOnName) {
        this.addOnName = addOnName;
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

    public String getAddOnId() {
        return addOnId;
    }

    public void setAddOnId(String addOnId) {
        this.addOnId = addOnId;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [price = "+price+", order_add_on_id = "+orderAddOnId+", order_detail_id = "+orderDetailId+", add_on_name = "+addOnName+", add_on_id = "+addOnId+"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderAddOnId);
        dest.writeString(addOnName);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(price);
        }
        dest.writeString(orderDetailId);
        dest.writeString(addOnId);
    }
}
