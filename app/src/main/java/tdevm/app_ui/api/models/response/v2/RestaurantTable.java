package tdevm.app_ui.api.models.response.v2;

/**
 * Created by Tridev on 19-08-2017.
 */

public class RestaurantTable {

    private String short_id;
    private String restaurant_uuid;
    private Boolean occupancy_status;
    private Boolean order_status;
    private Boolean booking_status;
    private String restaurant_table_id;
    private int table_number;
    private String table_layout_id;
    private String qr_encode_id;
    private String restaurant_id;
    private Boolean is_deleetd;


    public String getShort_id() {
        return short_id;
    }

    public void setShort_id(String short_id) {
        this.short_id = short_id;
    }

    public String getRestaurant_uuid() {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid(String restaurant_uuid) {
        this.restaurant_uuid = restaurant_uuid;
    }

    public Boolean getOccupancy_status() {
        return occupancy_status;
    }

    public void setOccupancy_status(Boolean occupancy_status) {
        this.occupancy_status = occupancy_status;
    }

    public Boolean getOrder_status() {
        return order_status;
    }

    public void setOrder_status(Boolean order_status) {
        this.order_status = order_status;
    }

    public Boolean getBooking_status() {
        return booking_status;
    }

    public void setBooking_status(Boolean booking_status) {
        this.booking_status = booking_status;
    }

    public String getRestaurant_table_id() {
        return restaurant_table_id;
    }

    public void setRestaurant_table_id(String restaurant_table_id) {
        this.restaurant_table_id = restaurant_table_id;
    }

    public int getTable_number() {
        return table_number;
    }

    public void setTable_number(int table_number) {
        this.table_number = table_number;
    }

    public String getTable_layout_id() {
        return table_layout_id;
    }

    public void setTable_layout_id(String table_layout_id) {
        this.table_layout_id = table_layout_id;
    }

    public String getQr_encode_id() {
        return qr_encode_id;
    }

    public void setQr_encode_id(String qr_encode_id) {
        this.qr_encode_id = qr_encode_id;
    }

    public String getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(String restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
