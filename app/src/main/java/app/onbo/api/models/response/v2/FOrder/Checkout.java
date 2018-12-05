
package app.onbo.api.models.response.v2.FOrder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Checkout {

    @SerializedName("order_total")
    @Expose
    private OrderTotal orderTotal;
    @SerializedName("order_items")
    @Expose
    private List<OrderItem> orderItems = null;

    public OrderTotal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(OrderTotal orderTotal) {
        this.orderTotal = orderTotal;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {

        return "orderTotal: " + orderTotal + "orderItems: " + orderItems;
    }

}
