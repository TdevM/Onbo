package tdevm.app_ui.api.models.response.v2.FOrder;

public class FOrderDetail {

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
}
