package tdevm.app_ui.api.models.response.v2.FOrder;

import android.os.Parcel;
import android.os.Parcelable;

public class FOrderDetail implements Parcelable {

    private String dlv_by;

    private String f_o_d_id;

    private String dlv_from;

    private String address;

    private String f_o_id;

    private String tat;

    private String cust_ip;

    private String cust_location;

    private String dlv_at;

    private String dlv_promise;

    protected FOrderDetail(Parcel in) {
        dlv_by = in.readString();
        f_o_d_id = in.readString();
        dlv_from = in.readString();
        address = in.readString();
        f_o_id = in.readString();
        tat = in.readString();
        cust_ip = in.readString();
        cust_location = in.readString();
        dlv_at = in.readString();
        dlv_promise = in.readString();
    }

    public static final Creator<FOrderDetail> CREATOR = new Creator<FOrderDetail>() {
        @Override
        public FOrderDetail createFromParcel(Parcel in) {
            return new FOrderDetail(in);
        }

        @Override
        public FOrderDetail[] newArray(int size) {
            return new FOrderDetail[size];
        }
    };

    public String getDlv_by() {
        return dlv_by;
    }

    public void setDlv_by(String dlv_by) {
        this.dlv_by = dlv_by;
    }

    public String getF_o_d_id() {
        return f_o_d_id;
    }

    public void setF_o_d_id(String f_o_d_id) {
        this.f_o_d_id = f_o_d_id;
    }

    public String getDlv_from() {
        return dlv_from;
    }

    public void setDlv_from(String dlv_from) {
        this.dlv_from = dlv_from;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getF_o_id() {
        return f_o_id;
    }

    public void setF_o_id(String f_o_id) {
        this.f_o_id = f_o_id;
    }

    public String getTat() {
        return tat;
    }

    public void setTat(String tat) {
        this.tat = tat;
    }

    public String getCust_ip() {
        return cust_ip;
    }

    public void setCust_ip(String cust_ip) {
        this.cust_ip = cust_ip;
    }

    public String getCust_location() {
        return cust_location;
    }

    public void setCust_location(String cust_location) {
        this.cust_location = cust_location;
    }

    public String getDlv_at() {
        return dlv_at;
    }

    public void setDlv_at(String dlv_at) {
        this.dlv_at = dlv_at;
    }

    public String getDlv_promise() {
        return dlv_promise;
    }

    public void setDlv_promise(String dlv_promise) {
        this.dlv_promise = dlv_promise;
    }

    @Override
    public String toString() {
        return "ClassPojo [dlv_by = " + dlv_by + ", f_o_d_id = " + f_o_d_id + ", dlv_from = " + dlv_from + ", address = " + address + ", f_o_id = " + f_o_id + ", tat = " + tat + ", cust_ip = " + cust_ip + ", cust_location = " + cust_location + ", dlv_at = " + dlv_at + ", dlv_promise = " + dlv_promise + "]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dlv_by);
        dest.writeString(f_o_d_id);
        dest.writeString(dlv_from);
        dest.writeString(address);
        dest.writeString(f_o_id);
        dest.writeString(tat);
        dest.writeString(cust_ip);
        dest.writeString(cust_location);
        dest.writeString(dlv_at);
        dest.writeString(dlv_promise);
    }
}
