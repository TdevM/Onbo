
package tdevm.app_ui.api.models.response.v2.t_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TOrderAddOn {

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
}
