package tdevm.app_ui.api.models.response.v2.merged;

import tdevm.app_ui.api.models.response.v2.t_orders.TOrderAddOn;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrderVExtra;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrderVariant;

public class ItemData {

    private String customizable;

    private String item_image;

    private TOrderVExtra[] t_order_v_extras;

    private String item_price;

    private String item_name;

    private String description;

    private String item_id;

    private Boolean is_veg;

    private String restaurant_id;

    private TOrderAddOn[] t_order_add_ons;

    private TOrderVariant[] t_order_variants;

    private String cuisine_id;

    private String item_hash;

    public String getCustomizable ()
    {
        return customizable;
    }

    public void setCustomizable (String customizable)
    {
        this.customizable = customizable;
    }

    public String getItem_image ()
    {
        return item_image;
    }

    public void setItem_image (String item_image)
    {
        this.item_image = item_image;
    }


    public String getItem_price ()
    {
        return item_price;
    }

    public void setItem_price (String item_price)
    {
        this.item_price = item_price;
    }

    public String getItem_name ()
    {
        return item_name;
    }

    public void setItem_name (String item_name)
    {
        this.item_name = item_name;
    }

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getItem_id ()
    {
        return item_id;
    }

    public void setItem_id (String item_id)
    {
        this.item_id = item_id;
    }

    public Boolean getIs_veg ()
    {
        return is_veg;
    }

    public void setIs_veg (Boolean is_veg)
    {
        this.is_veg = is_veg;
    }

    public String getRestaurant_id ()
    {
        return restaurant_id;
    }

    public void setRestaurant_id (String restaurant_id)
    {
        this.restaurant_id = restaurant_id;
    }

    public TOrderVExtra[] getT_order_v_extras() {
        return t_order_v_extras;
    }

    public void setT_order_v_extras(TOrderVExtra[] t_order_v_extras) {
        this.t_order_v_extras = t_order_v_extras;
    }

    public TOrderAddOn[] getT_order_add_ons() {
        return t_order_add_ons;
    }

    public void setT_order_add_ons(TOrderAddOn[] t_order_add_ons) {
        this.t_order_add_ons = t_order_add_ons;
    }

    public TOrderVariant[] getT_order_variants() {
        return t_order_variants;
    }

    public void setT_order_variants(TOrderVariant[] t_order_variants) {
        this.t_order_variants = t_order_variants;
    }

    public String getCuisine_id ()
    {
        return cuisine_id;
    }

    public void setCuisine_id (String cuisine_id)
    {
        this.cuisine_id = cuisine_id;
    }

    public String getItem_hash ()
    {
        return item_hash;
    }

    public void setItem_hash (String item_hash)
    {
        this.item_hash = item_hash;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [customizable = "+customizable+", item_image = "+item_image+", t_order_v_extras = "+t_order_v_extras+", item_price = "+item_price+", item_name = "+item_name+", description = "+description+", item_id = "+item_id+", is_veg = "+is_veg+", restaurant_id = "+restaurant_id+", t_order_add_ons = "+t_order_add_ons+", t_order_variants = "+t_order_variants+", cuisine_id = "+cuisine_id+", item_hash = "+item_hash+"]";
    }

}
