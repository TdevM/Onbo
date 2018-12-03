
package onbo.app.api.models.response.v2.t_orders;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import onbo.app.api.models.request.User;
import onbo.app.api.models.response.v2.Restaurant;
import onbo.app.api.models.response.v2.RestaurantTable;

public class TOrder implements Parcelable {

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
    @SerializedName("closed")
    @Expose
    private Boolean closed;
    @SerializedName("t_order_kots")
    @Expose
    private List<TOrderKot> tOrderKots = null;

    @SerializedName("restaurant_table")
    @Expose
    private RestaurantTable restaurantTable;

    private Restaurant restaurant;

    protected TOrder(Parcel in) {
        orderId = in.readString();
        timestamp = in.readString();
        byte tmpOrderStatus = in.readByte();
        orderStatus = tmpOrderStatus == 0 ? null : tmpOrderStatus == 1;
        restaurantId = in.readString();
        createdFrom = in.readString();
        if (in.readByte() == 0) {
            guestCount = null;
        } else {
            guestCount = in.readInt();
        }
        userId = in.readString();
        tableId = in.readString();
        byte tmpIsDel = in.readByte();
        isDel = tmpIsDel == 0 ? null : tmpIsDel == 1;
        byte tmpClosed = in.readByte();
        closed = tmpClosed == 0 ? null : tmpClosed == 1;
        tOrderKots = in.createTypedArrayList(TOrderKot.CREATOR);
        restaurantTable = in.readParcelable(RestaurantTable.class.getClassLoader());
        restaurant = in.readParcelable(Restaurant.class.getClassLoader());
        user = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<TOrder> CREATOR = new Creator<TOrder>() {
        @Override
        public TOrder createFromParcel(Parcel in) {
            return new TOrder(in);
        }

        @Override
        public TOrder[] newArray(int size) {
            return new TOrder[size];
        }
    };

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public List<TOrderKot> gettOrderKots() {
        return tOrderKots;
    }

    public void settOrderKots(List<TOrderKot> tOrderKots) {
        this.tOrderKots = tOrderKots;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
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

    public List<TOrderKot> getTOrderKots() {
        return tOrderKots;
    }

    public void setTOrderKots(List<TOrderKot> tOrderKots) {
        this.tOrderKots = tOrderKots;
    }

    @Override
    public String toString() {
        return "ClassPojo [t_order_kots = " + tOrderKots + ", created_from = " + createdFrom + ", timestamp = " + timestamp + ", guest_count = " + guestCount + ", restaurant_id = " + restaurantId + ", is_del = " + isDel + ", table_id = " + tableId + ", user_id = " + userId + ", order_id = " + orderId + ", order_status = " + orderStatus + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(timestamp);
        dest.writeByte((byte) (orderStatus == null ? 0 : orderStatus ? 1 : 2));
        dest.writeString(restaurantId);
        dest.writeString(createdFrom);
        if (guestCount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(guestCount);
        }
        dest.writeString(userId);
        dest.writeString(tableId);
        dest.writeByte((byte) (isDel == null ? 0 : isDel ? 1 : 2));
        dest.writeByte((byte) (closed == null ? 0 : closed ? 1 : 2));
        dest.writeTypedList(tOrderKots);
        dest.writeParcelable(restaurantTable, flags);
        dest.writeParcelable(restaurant, flags);
        dest.writeParcelable(user, flags);
    }
}
