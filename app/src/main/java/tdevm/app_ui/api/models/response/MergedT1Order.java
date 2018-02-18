
package tdevm.app_ui.api.models.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MergedT1Order {

    @SerializedName("running_order")
    @Expose
    private RunningOrder runningOrder;
    @SerializedName("order_details")
    @Expose
    private List<OrderDetail> orderDetails = null;

    public RunningOrder getRunningOrder() {
        return runningOrder;
    }

    public void setRunningOrder(RunningOrder runningOrder) {
        this.runningOrder = runningOrder;
    }

    public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }

    @Override
    public String toString() {
        return "Merged T1 Order"+ "runningOrder"+ runningOrder+"orderDetails"+ orderDetails;
    }

}
