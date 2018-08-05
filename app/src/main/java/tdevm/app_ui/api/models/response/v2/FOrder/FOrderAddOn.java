package tdevm.app_ui.api.models.response.v2.FOrder;

public class FOrderAddOn {


    private String price;

    private String f_o_i_id;

    private String add_on_name;

    private String add_on_id;

    private String f_o_ad_id;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getF_o_i_id() {
        return f_o_i_id;
    }

    public void setF_o_i_id(String f_o_i_id) {
        this.f_o_i_id = f_o_i_id;
    }

    public String getAdd_on_name() {
        return add_on_name;
    }

    public void setAdd_on_name(String add_on_name) {
        this.add_on_name = add_on_name;
    }

    public String getAdd_on_id() {
        return add_on_id;
    }

    public void setAdd_on_id(String add_on_id) {
        this.add_on_id = add_on_id;
    }

    public String getF_o_ad_id() {
        return f_o_ad_id;
    }

    public void setF_o_ad_id(String f_o_ad_id) {
        this.f_o_ad_id = f_o_ad_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [price = " + price + ", f_o_i_id = " + f_o_i_id + ", add_on_name = " + add_on_name + ", add_on_id = " + add_on_id + ", f_o_ad_id = " + f_o_ad_id + "]";
    }

}
