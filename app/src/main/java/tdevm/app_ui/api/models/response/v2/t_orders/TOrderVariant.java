
package tdevm.app_ui.api.models.response.v2.t_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TOrderVariant {

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

}
