package tdevm.app_ui.api.models.response;

/**
 * Created by Tridev on 24-11-2017.
 */


public class KOT_items
{
    private Dish dish;

    private String kot_completed;

    private String dish_id;

    private String restaurant_uuid;

    private String temp_table_order_id;

    private String temp_table_order_details_id;

    private String kot_id;

    private String dish_quantity;

    private String kot_timestamp;

    public Dish getDish ()
    {
        return dish;
    }

    public void setDish (Dish dish)
    {
        this.dish = dish;
    }

    public String getKot_completed ()
    {
        return kot_completed;
    }

    public void setKot_completed (String kot_completed)
    {
        this.kot_completed = kot_completed;
    }

    public String getDish_id ()
    {
        return dish_id;
    }

    public void setDish_id (String dish_id)
    {
        this.dish_id = dish_id;
    }

    public String getRestaurant_uuid ()
    {
        return restaurant_uuid;
    }

    public void setRestaurant_uuid (String restaurant_uuid)
    {
        this.restaurant_uuid = restaurant_uuid;
    }

    public String getTemp_table_order_id ()
    {
        return temp_table_order_id;
    }

    public void setTemp_table_order_id (String temp_table_order_id)
    {
        this.temp_table_order_id = temp_table_order_id;
    }

    public String getTemp_table_order_details_id ()
    {
        return temp_table_order_details_id;
    }

    public void setTemp_table_order_details_id (String temp_table_order_details_id)
    {
        this.temp_table_order_details_id = temp_table_order_details_id;
    }

    public String getKot_id ()
    {
        return kot_id;
    }

    public void setKot_id (String kot_id)
    {
        this.kot_id = kot_id;
    }

    public String getDish_quantity ()
    {
        return dish_quantity;
    }

    public void setDish_quantity (String dish_quantity)
    {
        this.dish_quantity = dish_quantity;
    }

    public String getKot_timestamp ()
    {
        return kot_timestamp;
    }

    public void setKot_timestamp (String kot_timestamp)
    {
        this.kot_timestamp = kot_timestamp;
    }

    @Override
    public String toString()
    {
        return "KOT_items [dish = "+dish+", kot_completed = "+kot_completed+", dish_id = "+dish_id+", restaurant_uuid = "+restaurant_uuid+", temp_table_order_id = "+temp_table_order_id+", temp_table_order_details_id = "+temp_table_order_details_id+", kot_id = "+kot_id+", dish_quantity = "+dish_quantity+", kot_timestamp = "+kot_timestamp+"]";
    }
}