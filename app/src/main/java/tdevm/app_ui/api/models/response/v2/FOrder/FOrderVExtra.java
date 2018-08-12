package tdevm.app_ui.api.models.response.v2.FOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FOrderVExtra {

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
}
