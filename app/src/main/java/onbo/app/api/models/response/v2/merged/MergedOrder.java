
package onbo.app.api.models.response.v2.merged;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import onbo.app.api.models.request.User;
import onbo.app.api.models.response.v2.RestaurantTable;

public class MergedOrder {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("order_status")
    @Expose
    private Boolean orderStatus;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("created_from")
    @Expose
    private String createdFrom;
    @SerializedName("guest_count")
    @Expose
    private Integer guestCount;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("table_id")
    @Expose
    private String tableId;
    @SerializedName("is_del")
    @Expose
    private Boolean isDel;
    @SerializedName("order_items")
    @Expose
    private List<MergedItems> mergedItems = null;

    @SerializedName("restaurant_table")
    @Expose
    private RestaurantTable restaurantTable;

    @SerializedName("order_total")
    @Expose
    private OrderTotal orderTotal;

    public OrderTotal getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(OrderTotal orderTotal) {
        this.orderTotal = orderTotal;
    }

    @SerializedName("user")
    @Expose
    private User user;

    public Boolean getDel() {
        return isDel;
    }

    public void setDel(Boolean del) {
        isDel = del;
    }

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCreatedFrom() {
        return createdFrom;
    }

    public void setCreatedFrom(String createdFrom) {
        this.createdFrom = createdFrom;
    }

    public Integer getGuestCount() {
        return guestCount;
    }

    public void setGuestCount(Integer guestCount) {
        this.guestCount = guestCount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public Boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(Boolean isDel) {
        this.isDel = isDel;
    }

    public List<MergedItems> getMergedItems() {
        return mergedItems;
    }

    public void setMergedItems(List<MergedItems> mergedItems) {
        this.mergedItems = mergedItems;
    }

    @Override
    public String toString() {
        return "ClassPojo [mergedItems = " + mergedItems + ", created_from = " + createdFrom + ", timestamp = " + timestamp + ", guest_count = " + guestCount + ", restaurant_id = " + restaurantId + ", is_del = " + isDel + ", table_id = " + tableId + ", user_id = " + userId + ", order_id = " + orderId + ", order_status = " + orderStatus + "]";
    }

}
