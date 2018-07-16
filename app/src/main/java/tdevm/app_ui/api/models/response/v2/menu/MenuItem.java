
package tdevm.app_ui.api.models.response.v2.menu;

import java.io.Serializable;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuItem implements Serializable, Parcelable {

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
    @SerializedName("is_visible")
    @Expose
    private Boolean isVisible;
    @SerializedName("cuisine_id")
    @Expose
    private String cuisineId;
    @SerializedName("added_at")
    @Expose
    private String addedAt;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("customizable")
    @Expose
    private Boolean customizable;
    @SerializedName("item_detail")
    @Expose
    private ItemDetail itemDetail;
    @SerializedName("cuisine")
    @Expose
    private Cuisine cuisine;
    @SerializedName("menu_variants")
    @Expose
    private List<MenuVariant> menuVariants = null;
    @SerializedName("menu_add_on_groups")
    @Expose
    private List<MenuAddOnGroup> menuAddOnGroups = null;
    public final static Creator<MenuItem> CREATOR = new Creator<MenuItem>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MenuItem createFromParcel(Parcel in) {
            return new MenuItem(in);
        }

        public MenuItem[] newArray(int size) {
            return (new MenuItem[size]);
        }

    };
    private final static long serialVersionUID = -685315480136505563L;

    protected MenuItem(Parcel in) {
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.itemName = ((String) in.readValue((String.class.getClassLoader())));
        this.description = ((String) in.readValue((String.class.getClassLoader())));
        this.itemPrice = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.itemImage = ((String) in.readValue((String.class.getClassLoader())));
        this.isVeg = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.restaurantId = ((String) in.readValue((String.class.getClassLoader())));
        this.isVisible = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.cuisineId = ((String) in.readValue((String.class.getClassLoader())));
        this.addedAt = ((String) in.readValue((String.class.getClassLoader())));
        this.isDeleted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.customizable = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.itemDetail = ((ItemDetail) in.readValue((ItemDetail.class.getClassLoader())));
        this.cuisine = ((Cuisine) in.readValue((Cuisine.class.getClassLoader())));
        in.readList(this.menuVariants, (MenuVariant.class.getClassLoader()));
        in.readList(this.menuAddOnGroups, (MenuAddOnGroup.class.getClassLoader()));
    }

    public MenuItem() {
    }

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

    public Boolean getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(String cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getCustomizable() {
        return customizable;
    }

    public void setCustomizable(Boolean customizable) {
        this.customizable = customizable;
    }

    public ItemDetail getItemDetail() {
        return itemDetail;
    }

    public void setItemDetail(ItemDetail itemDetail) {
        this.itemDetail = itemDetail;
    }

    public Cuisine getCuisine() {
        return cuisine;
    }

    public void setCuisine(Cuisine cuisine) {
        this.cuisine = cuisine;
    }

    public List<MenuVariant> getMenuVariants() {
        return menuVariants;
    }

    public void setMenuVariants(List<MenuVariant> menuVariants) {
        this.menuVariants = menuVariants;
    }

    public List<MenuAddOnGroup> getMenuAddOnGroups() {
        return menuAddOnGroups;
    }

    public void setMenuAddOnGroups(List<MenuAddOnGroup> menuAddOnGroups) {
        this.menuAddOnGroups = menuAddOnGroups;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(itemId);
        dest.writeValue(itemName);
        dest.writeValue(description);
        dest.writeValue(itemPrice);
        dest.writeValue(itemImage);
        dest.writeValue(isVeg);
        dest.writeValue(restaurantId);
        dest.writeValue(isVisible);
        dest.writeValue(cuisineId);
        dest.writeValue(addedAt);
        dest.writeValue(isDeleted);
        dest.writeValue(customizable);
        dest.writeValue(itemDetail);
        dest.writeValue(cuisine);
        dest.writeList(menuVariants);
        dest.writeList(menuAddOnGroups);
    }

    public int describeContents() {
        return 0;
    }

}
