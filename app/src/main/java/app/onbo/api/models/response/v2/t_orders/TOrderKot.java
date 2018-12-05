
package app.onbo.api.models.response.v2.t_orders;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TOrderKot implements Parcelable {

    @SerializedName("kot_id")
    @Expose
    private String kotId;
    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("kot_no")
    @Expose
    private Integer kotNo;
    @SerializedName("t_stamp")
    @Expose
    private String tStamp;
    @SerializedName("is_del")
    @Expose
    private Boolean isDel;
    @SerializedName("completed")
    @Expose
    private Boolean completed;
    @SerializedName("kot_message")
    @Expose
    private String kotMessage;
    @SerializedName("t_order_items")
    @Expose
    private List<TOrderItem> tOrderItems = null;

    protected TOrderKot(Parcel in) {
        kotId = in.readString();
        orderId = in.readString();
        if (in.readByte() == 0) {
            kotNo = null;
        } else {
            kotNo = in.readInt();
        }
        tStamp = in.readString();
        byte tmpIsDel = in.readByte();
        isDel = tmpIsDel == 0 ? null : tmpIsDel == 1;
        byte tmpCompleted = in.readByte();
        completed = tmpCompleted == 0 ? null : tmpCompleted == 1;
        kotMessage = in.readString();
        tOrderItems = in.createTypedArrayList(TOrderItem.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(kotId);
        dest.writeString(orderId);
        if (kotNo == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(kotNo);
        }
        dest.writeString(tStamp);
        dest.writeByte((byte) (isDel == null ? 0 : isDel ? 1 : 2));
        dest.writeByte((byte) (completed == null ? 0 : completed ? 1 : 2));
        dest.writeString(kotMessage);
        dest.writeTypedList(tOrderItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TOrderKot> CREATOR = new Creator<TOrderKot>() {
        @Override
        public TOrderKot createFromParcel(Parcel in) {
            return new TOrderKot(in);
        }

        @Override
        public TOrderKot[] newArray(int size) {
            return new TOrderKot[size];
        }
    };

    public String getKotId() {
        return kotId;
    }

    public void setKotId(String kotId) {
        this.kotId = kotId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getKotNo() {
        return kotNo;
    }

    public void setKotNo(Integer kotNo) {
        this.kotNo = kotNo;
    }

    public String getTStamp() {
        return tStamp;
    }

    public void setTStamp(String tStamp) {
        this.tStamp = tStamp;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getKotMessage() {
        return kotMessage;
    }

    public void setKotMessage(String kotMessage) {
        this.kotMessage = kotMessage;
    }

    public List<TOrderItem> getTOrderItems() {
        return tOrderItems;
    }

    public void setTOrderItems(List<TOrderItem> tOrderItems) {
        this.tOrderItems = tOrderItems;
    }


    @Override
    public String toString()
    {
        return "ClassPojo [kot_no = "+kotNo+", t_order_items = "+tOrderItems+", t_stamp = "+tStamp+", kot_id = "+kotId+", is_del = "+isDel+", kot_message = "+kotMessage+", order_id = "+orderId+", completed = "+completed+"]";
    }

}
