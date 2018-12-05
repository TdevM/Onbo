
package app.onbo.api.models.response.v2.menu;

import java.io.Serializable;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuVariant implements Serializable, Parcelable
{

    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("variant_name")
    @Expose
    private String variantName;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("menu_v_options")
    @Expose
    private List<MenuVOption> menuVOptions = null;
    public final static Creator<MenuVariant> CREATOR = new Creator<MenuVariant>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MenuVariant createFromParcel(Parcel in) {
            return new MenuVariant(in);
        }

        public MenuVariant[] newArray(int size) {
            return (new MenuVariant[size]);
        }

    }
    ;
    private final static long serialVersionUID = -2615708278820891180L;

    protected MenuVariant(Parcel in) {
        this.variantId = ((String) in.readValue((String.class.getClassLoader())));
        this.variantName = ((String) in.readValue((String.class.getClassLoader())));
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.restaurantId = ((String) in.readValue((String.class.getClassLoader())));
        this.isDeleted = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
        in.readList(this.menuVOptions, (MenuVOption.class.getClassLoader()));
    }

    public MenuVariant() {
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getVariantName() {
        return variantName;
    }

    public void setVariantName(String variantName) {
        this.variantName = variantName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
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

    public List<MenuVOption> getMenuVOptions() {
        return menuVOptions;
    }

    public void setMenuVOptions(List<MenuVOption> menuVOptions) {
        this.menuVOptions = menuVOptions;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(variantId);
        dest.writeValue(variantName);
        dest.writeValue(itemId);
        dest.writeValue(restaurantId);
        dest.writeValue(isDeleted);
        dest.writeList(menuVOptions);
    }

    public int describeContents() {
        return  0;
    }

}
