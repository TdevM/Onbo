package tdevm.app_ui.api.models.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Tridev on 20-03-2018.
 */

public class DishVariant implements Parcelable {
    @SerializedName("variant_id")
    @Expose
    private String variantId;
    @SerializedName("variant_type")
    @Expose
    private String variantType;
    @SerializedName("variant_price")
    @Expose
    private Double variantPrice;
    @SerializedName("parent_dish_id")
    @Expose
    private String parentDishId;
    @SerializedName("cuisine_id")
    @Expose
    private String cuisineId;
    @SerializedName("is_deleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("restaurant_uuid")
    @Expose
    private String restaurantUuid;
    @SerializedName("dish_variant_name")
    @Expose
    private String dishVariantName;
    @SerializedName("dish_variant_id")
    @Expose
    private String dishVariantId;

    private boolean selected;


    protected DishVariant(Parcel in) {
        variantId = in.readString();
        variantType = in.readString();
        if (in.readByte() == 0) {
            variantPrice = null;
        } else {
            variantPrice = in.readDouble();
        }
        parentDishId = in.readString();
        cuisineId = in.readString();
        byte tmpIsDeleted = in.readByte();
        isDeleted = tmpIsDeleted == 0 ? null : tmpIsDeleted == 1;
        restaurantUuid = in.readString();
        dishVariantName = in.readString();
        dishVariantId = in.readString();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(variantId);
        dest.writeString(variantType);
        if (variantPrice == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(variantPrice);
        }
        dest.writeString(parentDishId);
        dest.writeString(cuisineId);
        dest.writeByte((byte) (isDeleted == null ? 0 : isDeleted ? 1 : 2));
        dest.writeString(restaurantUuid);
        dest.writeString(dishVariantName);
        dest.writeString(dishVariantId);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DishVariant> CREATOR = new Creator<DishVariant>() {
        @Override
        public DishVariant createFromParcel(Parcel in) {
            return new DishVariant(in);
        }

        @Override
        public DishVariant[] newArray(int size) {
            return new DishVariant[size];
        }
    };

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public DishVariant() {
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DishVariant that = (DishVariant) o;

        if (variantId != null ? !variantId.equals(that.variantId) : that.variantId != null)
            return false;
        if (variantType != null ? !variantType.equals(that.variantType) : that.variantType != null)
            return false;
        if (variantPrice != null ? !variantPrice.equals(that.variantPrice) : that.variantPrice != null)
            return false;
        if (parentDishId != null ? !parentDishId.equals(that.parentDishId) : that.parentDishId != null)
            return false;
        if (cuisineId != null ? !cuisineId.equals(that.cuisineId) : that.cuisineId != null)
            return false;
        if (isDeleted != null ? !isDeleted.equals(that.isDeleted) : that.isDeleted != null)
            return false;
        if (restaurantUuid != null ? !restaurantUuid.equals(that.restaurantUuid) : that.restaurantUuid != null)
            return false;
        if (dishVariantName != null ? !dishVariantName.equals(that.dishVariantName) : that.dishVariantName != null)
            return false;
        return dishVariantId != null ? dishVariantId.equals(that.dishVariantId) : that.dishVariantId == null;
    }

    @Override
    public int hashCode() {
        int result = variantId != null ? variantId.hashCode() : 0;
        result = 31 * result + (variantType != null ? variantType.hashCode() : 0);
        result = 31 * result + (variantPrice != null ? variantPrice.hashCode() : 0);
        result = 31 * result + (parentDishId != null ? parentDishId.hashCode() : 0);
        result = 31 * result + (cuisineId != null ? cuisineId.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        result = 31 * result + (restaurantUuid != null ? restaurantUuid.hashCode() : 0);
        result = 31 * result + (dishVariantName != null ? dishVariantName.hashCode() : 0);
        result = 31 * result + (dishVariantId != null ? dishVariantId.hashCode() : 0);
        return result;
    }

    public String getVariantId() {
        return variantId;
    }

    public void setVariantId(String variantId) {
        this.variantId = variantId;
    }

    public String getVariantType() {
        return variantType;
    }

    public void setVariantType(String variantType) {
        this.variantType = variantType;
    }

    public Double getVariantPrice() {
        return variantPrice;
    }

    public void setVariantPrice(Double variantPrice) {
        this.variantPrice = variantPrice;
    }

    public String getParentDishId() {
        return parentDishId;
    }

    public void setParentDishId(String parentDishId) {
        this.parentDishId = parentDishId;
    }

    public String getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(String cuisineId) {
        this.cuisineId = cuisineId;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRestaurantUuid() {
        return restaurantUuid;
    }

    public void setRestaurantUuid(String restaurantUuid) {
        this.restaurantUuid = restaurantUuid;
    }

    public String getDishVariantName() {
        return dishVariantName;
    }

    public void setDishVariantName(String dishVariantName) {
        this.dishVariantName = dishVariantName;
    }

    public String getDishVariantId() {
        return dishVariantId;
    }

    public void setDishVariantId(String dishVariantId) {
        this.dishVariantId = dishVariantId;
    }
}
