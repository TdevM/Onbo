package tdevm.app_ui.api.models.cart;

import android.arch.persistence.room.Ignore;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import tdevm.app_ui.api.models.response.v2.menu.Cuisine;
import tdevm.app_ui.api.models.response.v2.menu.ItemDetail;
import tdevm.app_ui.api.models.response.v2.menu.MenuAddOn;
import tdevm.app_ui.api.models.response.v2.menu.MenuVOption;

public class MenuItem  implements Serializable, Parcelable{

    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("item_name")
    @Expose
    private String itemName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("item_price")
    @Expose
    private Integer itemPrice;
    @SerializedName("item_image")
    @Expose
    private String itemImage;
    @SerializedName("is_veg")
    @Expose
    private Boolean isVeg;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("cuisine_id")
    @Expose
    private String cuisineId;
    @SerializedName("customizable")
    @Expose
    private Boolean customizable;
    @SerializedName("menu_variants")
    @Expose
    private List<MenuVOption> menuVariantOptions = null;
    @SerializedName("menu_add_on_groups")
    @Expose
    private List<MenuAddOn> menuAddOns = null;

    public MenuItem() {
    }

    @Ignore
    public MenuItem(String itemId, String itemName, String description, Integer itemPrice, String itemImage, Boolean isVeg, String restaurantId, String cuisineId, Boolean customizable, List<MenuVOption> menuVariantOptions, List<MenuAddOn> menuAddOns) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.isVeg = isVeg;
        this.restaurantId = restaurantId;
        this.cuisineId = cuisineId;
        this.customizable = customizable;
        this.menuVariantOptions = menuVariantOptions;
        this.menuAddOns = menuAddOns;
    }


    @Ignore
    public MenuItem(String itemId, String itemName, String description, Integer itemPrice, String itemImage, Boolean isVeg, String restaurantId, String cuisineId, Boolean customizable) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.description = description;
        this.itemPrice = itemPrice;
        this.itemImage = itemImage;
        this.isVeg = isVeg;
        this.restaurantId = restaurantId;
        this.cuisineId = cuisineId;
        this.customizable = customizable;
    }

    protected MenuItem(Parcel in) {
        itemId = in.readString();
        itemName = in.readString();
        description = in.readString();
        if (in.readByte() == 0) {
            itemPrice = null;
        } else {
            itemPrice = in.readInt();
        }
        itemImage = in.readString();
        byte tmpIsVeg = in.readByte();
        isVeg = tmpIsVeg == 0 ? null : tmpIsVeg == 1;
        restaurantId = in.readString();
        cuisineId = in.readString();
        byte tmpCustomizable = in.readByte();
        customizable = tmpCustomizable == 0 ? null : tmpCustomizable == 1;
        menuVariantOptions = in.createTypedArrayList(MenuVOption.CREATOR);
        menuAddOns = in.createTypedArrayList(MenuAddOn.CREATOR);
    }

    public static final Creator<MenuItem> CREATOR = new Creator<MenuItem>() {
        @Override
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        @Override
        public MenuItem[] newArray(int size) {
            return new MenuItem[size];
        }
    };

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(Integer itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemImage() {
        return itemImage;
    }

    public void setItemImage(String itemImage) {
        this.itemImage = itemImage;
    }

    public Boolean getVeg() {
        return isVeg;
    }

    public void setVeg(Boolean veg) {
        isVeg = veg;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(String cuisineId) {
        this.cuisineId = cuisineId;
    }

    public Boolean getCustomizable() {
        return customizable;
    }

    public void setCustomizable(Boolean customizable) {
        this.customizable = customizable;
    }

    public List<MenuVOption> getMenuVariantOptions() {
        return menuVariantOptions;
    }

    public void setMenuVariantOptions(List<MenuVOption> menuVariantOptions) {
        this.menuVariantOptions = menuVariantOptions;
    }

    public List<MenuAddOn> getMenuAddOns() {
        return menuAddOns;
    }

    public void setMenuAddOns(List<MenuAddOn> menuAddOns) {
        this.menuAddOns = menuAddOns;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(itemId);
        dest.writeString(itemName);
        dest.writeString(description);
        if (itemPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(itemPrice);
        }
        dest.writeString(itemImage);
        dest.writeByte((byte) (isVeg == null ? 0 : isVeg ? 1 : 2));
        dest.writeString(restaurantId);
        dest.writeString(cuisineId);
        dest.writeByte((byte) (customizable == null ? 0 : customizable ? 1 : 2));
        dest.writeTypedList(menuVariantOptions);
        dest.writeTypedList(menuAddOns);
    }

    @Override
    public String toString() {
        return "MenuItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemImage='" + itemImage + '\'' +
                ", isVeg=" + isVeg +
                ", restaurantId='" + restaurantId + '\'' +
                ", cuisineId='" + cuisineId + '\'' +
                ", customizable=" + customizable +
                ", menuVariantOptions=" + menuVariantOptions +
                ", menuAddOns=" + menuAddOns +
                '}';
    }
}
