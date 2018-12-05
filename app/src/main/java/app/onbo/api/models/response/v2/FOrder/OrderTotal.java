
package app.onbo.api.models.response.v2.FOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class OrderTotal {

    @SerializedName("subtotal")
    @Expose
    private String subtotal;
    @SerializedName("taxes")
    @Expose
    private String taxes;
    @SerializedName("grand_total")
    @Expose
    private String grandTotal;

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }

    public String getGrandTotal() {
        return grandTotal;
    }

    public void setGrandTotal(String grandTotal) {
        this.grandTotal = grandTotal;
    }

    @Override
    public String toString() {
        return "OrderTotal{" +
                "subtotal=" + subtotal +
                ", taxes=" + taxes +
                ", grandTotal=" + grandTotal +
                '}';
    }
}
