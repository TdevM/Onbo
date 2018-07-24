
package tdevm.app_ui.api.models.response.v2.t_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TOrderKot {

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
