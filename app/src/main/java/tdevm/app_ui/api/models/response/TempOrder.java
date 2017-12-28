package tdevm.app_ui.api.models.response;

import java.util.ArrayList;

/**
 * Created by Tridev on 24-11-2017.
 */

public class TempOrder
{
    private Boolean kot_completed;

    private ArrayList<KOT_items> kot_items;

    private String kot_id;

    private String table_no;

    private String kot_timestamp;

    private String order_id;

    public Boolean getKot_completed() {
        return kot_completed;
    }

    public void setKot_completed(Boolean kot_completed) {
        this.kot_completed = kot_completed;
    }

    public ArrayList<KOT_items> getKot_items() {
        return kot_items;
    }

    public void setKot_items(ArrayList<KOT_items> kot_items) {
        this.kot_items = kot_items;
    }

    public String getKot_id ()
    {
        return kot_id;
    }

    public void setKot_id (String kot_id)
    {
        this.kot_id = kot_id;
    }

    public String getTable_no ()
    {
        return table_no;
    }

    public void setTable_no (String table_no)
    {
        this.table_no = table_no;
    }

    public String getKot_timestamp ()
    {
        return kot_timestamp;
    }

    public void setKot_timestamp (String kot_timestamp)
    {
        this.kot_timestamp = kot_timestamp;
    }

    public String getOrder_id ()
    {
        return order_id;
    }

    public void setOrder_id (String order_id)
    {
        this.order_id = order_id;
    }

    @Override
    public String toString()
    {
        return "TempOrder [kot_completed = "+kot_completed+", kot_items = "+kot_items+", kot_id = "+kot_id+", table_no = "+table_no+", kot_timestamp = "+kot_timestamp+", order_id = "+order_id+"]";
    }
}
