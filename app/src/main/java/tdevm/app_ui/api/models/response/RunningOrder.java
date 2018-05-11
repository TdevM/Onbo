
package tdevm.app_ui.api.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import tdevm.app_ui.api.models.request.KOTUserMessage;
import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.api.models.response.v2.RestaurantTable;

public class RunningOrder {

    @SerializedName("temp_table_order_id")
    @Expose
    private String tempTableOrderId;
    @SerializedName("temp_table_order_timestamp")
    @Expose
    private String tempTableOrderTimestamp;
    @SerializedName("temp_table_order_running_status")
    @Expose
    private Boolean tempTableOrderRunningStatus;
    @SerializedName("user_mobile")
    @Expose
    private String userMobile;
    @SerializedName("temp_table_order_table_short_id")
    @Expose
    private String tempTableOrderTableShortId;
    @SerializedName("restaurant_uuid")
    @Expose
    private String restaurantUuid;
    @SerializedName("temp_table_user_message")
    @Expose
    private ArrayList<KOTUserMessage> tempTableUserMessage;

    public ArrayList<KOTUserMessage> getTempTableUserMessage() {
        return tempTableUserMessage;
    }

    public void setTempTableUserMessage(ArrayList<KOTUserMessage> tempTableUserMessage) {
        this.tempTableUserMessage = tempTableUserMessage;
    }

    @SerializedName("temp_table_order_placed_from")
    @Expose

    private String tempTableOrderPlacedFrom;
    @SerializedName("temp_table_guest_count")
    @Expose
    private Integer tempTableGuestCount;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("restaurant_table")
    @Expose
    private RestaurantTable restaurantTable;

    public String getTempTableOrderId() {
        return tempTableOrderId;
    }

    public void setTempTableOrderId(String tempTableOrderId) {
        this.tempTableOrderId = tempTableOrderId;
    }

    public String getTempTableOrderTimestamp() {
        return tempTableOrderTimestamp;
    }

    public void setTempTableOrderTimestamp(String tempTableOrderTimestamp) {
        this.tempTableOrderTimestamp = tempTableOrderTimestamp;
    }

    public Boolean getTempTableOrderRunningStatus() {
        return tempTableOrderRunningStatus;
    }

    public void setTempTableOrderRunningStatus(Boolean tempTableOrderRunningStatus) {
        this.tempTableOrderRunningStatus = tempTableOrderRunningStatus;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getTempTableOrderTableShortId() {
        return tempTableOrderTableShortId;
    }

    public void setTempTableOrderTableShortId(String tempTableOrderTableShortId) {
        this.tempTableOrderTableShortId = tempTableOrderTableShortId;
    }

    public String getRestaurantUuid() {
        return restaurantUuid;
    }

    public void setRestaurantUuid(String restaurantUuid) {
        this.restaurantUuid = restaurantUuid;
    }

    public String getTempTableOrderPlacedFrom() {
        return tempTableOrderPlacedFrom;
    }

    public void setTempTableOrderPlacedFrom(String tempTableOrderPlacedFrom) {
        this.tempTableOrderPlacedFrom = tempTableOrderPlacedFrom;
    }

    public Integer getTempTableGuestCount() {
        return tempTableGuestCount;
    }

    public void setTempTableGuestCount(Integer tempTableGuestCount) {
        this.tempTableGuestCount = tempTableGuestCount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RestaurantTable getRestaurantTable() {
        return restaurantTable;
    }

    public void setRestaurantTable(RestaurantTable restaurantTable) {
        this.restaurantTable = restaurantTable;
    }

    @Override
    public String toString() {
        return "RunningOrder" +"tempTableOrderId"+ tempTableOrderId+"tempTableOrderTimestamp"+tempTableOrderTimestamp+"tempTableOrderRunningStatus"+ tempTableOrderRunningStatus+"userMobile"+ userMobile+"tempTableOrderTableShortId"+ tempTableOrderTableShortId+"restaurantUuid"+ restaurantUuid+"tempTableUserMessage"+ tempTableUserMessage+"tempTableOrderPlacedFrom"+ tempTableOrderPlacedFrom+"tempTableGuestCount"+ tempTableGuestCount+"user"+ user+"restaurantTable"+ restaurantTable;
    }

}
