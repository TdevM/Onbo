
package tdevm.app_ui.api.models.response.v2.t_orders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TOrderVExtra {

    @SerializedName("t_extra_id")
    @Expose
    private String tExtraId;
    @SerializedName("option_extra")
    @Expose
    private String optionExtra;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("order_detail_id")
    @Expose
    private String orderDetailId;
    @SerializedName("extra_id")
    @Expose
    private String extraId;

    public String getTExtraId() {
        return tExtraId;
    }

    public void setTExtraId(String tExtraId) {
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
    public String toString()
    {
        return "ClassPojo [option_extra = "+optionExtra+", price = "+price+", extra_id = "+extraId+", order_detail_id = "+orderDetailId+", t_extra_id = "+tExtraId+"]";
    }

}
