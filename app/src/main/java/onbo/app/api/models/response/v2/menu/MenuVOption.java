
package onbo.app.api.models.response.v2.menu;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuVOption implements Serializable, Parcelable
{

    @SerializedName("option_id")
    @Expose
    private String optionId;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("option_name")
    @Expose
    private String optionName;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("is_veg")
    @Expose
    private Boolean isVeg;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("menu_v_extras")
    @Expose
    private List<MenuVExtra> menuVExtras = null;
    public final static Creator<MenuVOption> CREATOR = new Creator<MenuVOption>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MenuVOption createFromParcel(Parcel in) {
            return new MenuVOption(in);
        }

        public MenuVOption[] newArray(int size) {
            return (new MenuVOption[size]);
        }

    }
    ;
    private final static long serialVersionUID = -5008624859540472275L;

    protected MenuVOption(Parcel in) {
        this.optionId = ((String) in.readValue((String.class.getClassLoader())));
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.optionName = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.variantId = ((String) in.readValue((String.class.getClassLoader())));
        this.isVeg = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        this.restaurantId = ((String) in.readValue((String.class.getClassLoader())));
        this.isDeleted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.menuVExtras, (MenuVExtra.class.getClassLoader()));
    }

    public MenuVOption() {
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<MenuVExtra> getMenuVExtras() {
        return menuVExtras;
    }

    public void setMenuVExtras(List<MenuVExtra> menuVExtras) {
        this.menuVExtras = menuVExtras;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(optionId);
        dest.writeValue(itemId);
        dest.writeValue(optionName);
        dest.writeValue(price);
        dest.writeValue(variantId);
        dest.writeValue(isVeg);
        dest.writeValue(restaurantId);
        dest.writeValue(isDeleted);
        dest.writeList(menuVExtras);
    }

    public int describeContents() {
        return  0;
    }

}
