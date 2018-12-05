
package app.onbo.api.models.response.v2.menu;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuAddOnGroup implements Serializable, Parcelable
{

    @SerializedName("group_id")
    @Expose
    private String groupId;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("add_on_limit")
    @Expose
    private Integer addOnLimit;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("menu_add_ons")
    @Expose
    private List<MenuAddOn> menuAddOns = null;
    public final static Creator<MenuAddOnGroup> CREATOR = new Creator<MenuAddOnGroup>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MenuAddOnGroup createFromParcel(Parcel in) {
            return new MenuAddOnGroup(in);
        }

        public MenuAddOnGroup[] newArray(int size) {
            return (new MenuAddOnGroup[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2189920308981516094L;

    protected MenuAddOnGroup(Parcel in) {
        this.groupId = ((String) in.readValue((String.class.getClassLoader())));
        this.groupName = ((String) in.readValue((String.class.getClassLoader())));
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.addOnLimit = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.restaurantId = ((String) in.readValue((String.class.getClassLoader())));
        this.isDeleted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.menuAddOns, (MenuAddOn.class.getClassLoader()));
    }

    public MenuAddOnGroup() {
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Integer getAddOnLimit() {
        return addOnLimit;
    }

    public void setAddOnLimit(Integer addOnLimit) {
        this.addOnLimit = addOnLimit;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<MenuAddOn> getMenuAddOns() {
        return menuAddOns;
    }

    public void setMenuAddOns(List<MenuAddOn> menuAddOns) {
        this.menuAddOns = menuAddOns;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(groupId);
        dest.writeValue(groupName);
        dest.writeValue(itemId);
        dest.writeValue(addOnLimit);
        dest.writeValue(restaurantId);
        dest.writeValue(isDeleted);
        dest.writeList(menuAddOns);
    }

    public int describeContents() {
        return  0;
    }

}
