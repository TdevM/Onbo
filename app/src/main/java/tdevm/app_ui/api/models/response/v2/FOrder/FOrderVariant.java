package tdevm.app_ui.api.models.response.v2.FOrder;

public class FOrderVariant {

    private String f_o_v_id;

    private String price;

    private String f_o_i_id;

    private String option_name;

    private String option_id;

    public String getF_o_v_id() {
        return f_o_v_id;
    }

    public void setF_o_v_id(String f_o_v_id) {
        this.f_o_v_id = f_o_v_id;
    }

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

    public String getOption_name() {
        return option_name;
    }

    public void setOption_name(String option_name) {
        this.option_name = option_name;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [f_o_v_id = " + f_o_v_id + ", price = " + price + ", f_o_i_id = " + f_o_i_id + ", option_name = " + option_name + ", option_id = " + option_id + "]";
    }
}
