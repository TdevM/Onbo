package app.onbo.api.models.response.v2;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Tridev on 19-08-2017.
 */

public class RestaurantTable implements Parcelable {

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


    protected RestaurantTable(Parcel in) {
        short_id = in.readString();
        restaurant_uuid = in.readString();
        byte tmpOccupancy_status = in.readByte();
        occupancy_status = tmpOccupancy_status == 0 ? null : tmpOccupancy_status == 1;
        byte tmpOrder_status = in.readByte();
        order_status = tmpOrder_status == 0 ? null : tmpOrder_status == 1;
        byte tmpBooking_status = in.readByte();
        booking_status = tmpBooking_status == 0 ? null : tmpBooking_status == 1;
        restaurant_table_id = in.readString();
        table_number = in.readInt();
        table_layout_id = in.readString();
        qr_encode_id = in.readString();
        restaurant_id = in.readString();
        byte tmpIs_deleetd = in.readByte();
        is_deleetd = tmpIs_deleetd == 0 ? null : tmpIs_deleetd == 1;
    }

    public static final Creator<RestaurantTable> CREATOR = new Creator<RestaurantTable>() {
        @Override
        public RestaurantTable createFromParcel(Parcel in) {
            return new RestaurantTable(in);
        }

        @Override
        public RestaurantTable[] newArray(int size) {
            return new RestaurantTable[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(short_id);
        dest.writeString(restaurant_uuid);
        dest.writeByte((byte) (occupancy_status == null ? 0 : occupancy_status ? 1 : 2));
        dest.writeByte((byte) (order_status == null ? 0 : order_status ? 1 : 2));
        dest.writeByte((byte) (booking_status == null ? 0 : booking_status ? 1 : 2));
        dest.writeString(restaurant_table_id);
        dest.writeInt(table_number);
        dest.writeString(table_layout_id);
        dest.writeString(qr_encode_id);
        dest.writeString(restaurant_id);
        dest.writeByte((byte) (is_deleetd == null ? 0 : is_deleetd ? 1 : 2));
    }
}
