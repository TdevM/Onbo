package tdevm.app_ui.api.models.response.v2.merged;

public class OrderTotal {

    String subtotal;
    String grand_total;
    String taxes;

    public OrderTotal(String subtotal, String grand_total, String taxes) {
        this.subtotal = subtotal;
        this.grand_total = grand_total;
        this.taxes = taxes;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getGrand_total() {
        return grand_total;
    }

    public void setGrand_total(String grand_total) {
        this.grand_total = grand_total;
    }

    public String getTaxes() {
        return taxes;
    }

    public void setTaxes(String taxes) {
        this.taxes = taxes;
    }
}
