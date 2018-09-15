
package tdevm.app_ui.api.models.response.v2.FOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tax {

    @SerializedName("tax_id")
    @Expose
    private Integer taxId;
    @SerializedName("tax_name")
    @Expose
    private String taxName;
    @SerializedName("tax_value")
    @Expose
    private Integer taxValue;
    @SerializedName("tax_breakup")
    @Expose
    private TaxBreakup taxBreakup;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("is_del")
    @Expose
    private Boolean isDel;

    public Integer getTaxId() {
        return taxId;
    }

    public void setTaxId(Integer taxId) {
        this.taxId = taxId;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public Integer getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(Integer taxValue) {
        this.taxValue = taxValue;
    }

    public TaxBreakup getTaxBreakup() {
        return taxBreakup;
    }

    public void setTaxBreakup(TaxBreakup taxBreakup) {
        this.taxBreakup = taxBreakup;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    @Override
    public String toString() {
        return "Tax{" +
                "taxId=" + taxId +
                ", taxName='" + taxName + '\'' +
                ", taxValue=" + taxValue +
                ", taxBreakup=" + taxBreakup +
                ", restaurantId='" + restaurantId + '\'' +
                ", isDel=" + isDel +
                '}';
    }
}
