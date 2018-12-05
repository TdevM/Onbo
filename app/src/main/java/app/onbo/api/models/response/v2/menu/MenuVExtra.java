
package app.onbo.api.models.response.v2.menu;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MenuVExtra implements Serializable, Parcelable
{

    @SerializedName("extra_id")
    @Expose
    private String extraId;
    @SerializedName("v_option_id")
    @Expose
    private String vOptionId;
    @SerializedName("option_extra")
    @Expose
    private String optionExtra;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("item_id")
    @Expose
    private String itemId;
    @SerializedName("is_veg")
    @Expose
    private Boolean isVeg;
    public final static Creator<MenuVExtra> CREATOR = new Creator<MenuVExtra>() {


        @SuppressWarnings({
            "unchecked"
        })
        public MenuVExtra createFromParcel(Parcel in) {
            return new MenuVExtra(in);
        }

        public MenuVExtra[] newArray(int size) {
            return (new MenuVExtra[size]);
        }

    }
    ;
    private final static long serialVersionUID = -3197943760863757981L;

    protected MenuVExtra(Parcel in) {
        this.extraId = ((String) in.readValue((String.class.getClassLoader())));
        this.vOptionId = ((String) in.readValue((String.class.getClassLoader())));
        this.optionExtra = ((String) in.readValue((String.class.getClassLoader())));
        this.price = ((Integer) in.readValue((Integer.class.getClassLoader())));
        this.itemId = ((String) in.readValue((String.class.getClassLoader())));
        this.isVeg = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
    }

    public MenuVExtra() {
    }

    public String getExtraId() {
        return extraId;
    }

    public void setExtraId(String extraId) {
        this.extraId = extraId;
    }

    public String getVOptionId() {
        return vOptionId;
    }

    public void setVOptionId(String vOptionId) {
        this.vOptionId = vOptionId;
    }

    public String getOptionExtra() {
        return optionExtra;
    }

    public void setOptionExtra(String optionExtra) {
        this.optionExtra = optionExtra;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public Boolean getIsVeg() {
        return isVeg;
    }

    public void setIsVeg(Boolean isVeg) {
        this.isVeg = isVeg;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(extraId);
        dest.writeValue(vOptionId);
        dest.writeValue(optionExtra);
        dest.writeValue(price);
        dest.writeValue(itemId);
        dest.writeValue(isVeg);
    }

    public int describeContents() {
        return  0;
    }

}
