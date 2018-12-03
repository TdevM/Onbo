
package onbo.app.api.models.response.v2.menu;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuAddOn implements Serializable, Parcelable
{

    @SerializedName("add_on_id")
    @Expose
    private String addOnId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("add_on_name")
    @Expose
    private String addOnName;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("group_id")
    @Expose
    private String groupId;
    public final static Creator<MenuAddOn> CREATOR = new Creator<MenuAddOn>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MenuAddOn createFromParcel(Parcel in) {
            return new MenuAddOn(in);
        }

        public MenuAddOn[] newArray(int size) {
            return (new MenuAddOn[size]);
        }

    }
    ;
    private final static long serialVersionUID = 6840149104080487303L;

    protected MenuAddOn(Parcel in) {
        this.addOnId = ((String) in.readValue((String.class.getClassLoader())));
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.addOnName = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.groupId = ((String) in.readValue((String.class.getClassLoader())));
    }

    public MenuAddOn() {
    }

    public String getAddOnId() {
        return addOnId;
    }

    public void setAddOnId(String addOnId) {
        this.addOnId = addOnId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getAddOnName() {
        return addOnName;
    }

    public void setAddOnName(String addOnName) {
        this.addOnName = addOnName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(addOnId);
        dest.writeValue(itemId);
        dest.writeValue(addOnName);
        dest.writeValue(price);
        dest.writeValue(groupId);
    }

    public int describeContents() {
        return  0;
    }

}
