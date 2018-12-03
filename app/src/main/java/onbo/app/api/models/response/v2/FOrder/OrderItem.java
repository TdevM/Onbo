
package onbo.app.api.models.response.v2.FOrder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItem {

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
    private String itemPrice;
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
    @SerializedName("is_gst_incl")
    @Expose
    private Boolean isGstIncl;
    @SerializedName("exclude_base")
    @Expose
    private Boolean excludeBase;
    @SerializedName("tax_id")
    @Expose
    private String taxId;
    @SerializedName("tax")
    @Expose
    private Tax tax;
    @SerializedName("item_qty")
    @Expose
    private String itemQty;
    @SerializedName("f_order_variants")
    @Expose
    private List<Object> fOrderVariants = null;
    @SerializedName("f_order_v_extras")
    @Expose
    private List<Object> fOrderVExtras = null;
    @SerializedName("f_order_addons")
    @Expose
    private List<Object> fOrderAddons = null;
    @SerializedName("item_total")
    @Expose
    private String itemTotal;
    @SerializedName("item_q_total")
    @Expose
    private String itemQTotal;
    @SerializedName("item_tax")
    @Expose
    private String itemTax;
    @SerializedName("tax_total")
    @Expose
    private String taxTotal;
    @SerializedName("item_slug")
    @Expose
    private String itemSlug;
    @SerializedName("item_hash")
    @Expose
    private String itemHash;

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

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Boolean getVeg() {
        return isVeg;
    }

    public void setVeg(Boolean veg) {
        isVeg = veg;
    }

    public Boolean getGstIncl() {
        return isGstIncl;
    }

    public void setGstIncl(Boolean gstIncl) {
        isGstIncl = gstIncl;
    }

    public List<Object> getfOrderVariants() {
        return fOrderVariants;
    }

    public void setfOrderVariants(List<Object> fOrderVariants) {
        this.fOrderVariants = fOrderVariants;
    }

    public List<Object> getfOrderVExtras() {
        return fOrderVExtras;
    }

    public void setfOrderVExtras(List<Object> fOrderVExtras) {
        this.fOrderVExtras = fOrderVExtras;
    }

    public List<Object> getfOrderAddons() {
        return fOrderAddons;
    }

    public void setfOrderAddons(List<Object> fOrderAddons) {
        this.fOrderAddons = fOrderAddons;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }

    public String getItemQTotal() {
        return itemQTotal;
    }

    public void setItemQTotal(String itemQTotal) {
        this.itemQTotal = itemQTotal;
    }

    public String getItemTax() {
        return itemTax;
    }

    public void setItemTax(String itemTax) {
        this.itemTax = itemTax;
    }

    public String getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(String taxTotal) {
        this.taxTotal = taxTotal;
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

    public Boolean getIsGstIncl() {
        return isGstIncl;
    }

    public void setIsGstIncl(Boolean isGstIncl) {
        this.isGstIncl = isGstIncl;
    }

    public Boolean getExcludeBase() {
        return excludeBase;
    }

    public void setExcludeBase(Boolean excludeBase) {
        this.excludeBase = excludeBase;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public List<Object> getFOrderVariants() {
        return fOrderVariants;
    }

    public void setFOrderVariants(List<Object> fOrderVariants) {
        this.fOrderVariants = fOrderVariants;
    }

    public List<Object> getFOrderVExtras() {
        return fOrderVExtras;
    }

    public void setFOrderVExtras(List<Object> fOrderVExtras) {
        this.fOrderVExtras = fOrderVExtras;
    }

    public List<Object> getFOrderAddons() {
        return fOrderAddons;
    }

    public void setFOrderAddons(List<Object> fOrderAddons) {
        this.fOrderAddons = fOrderAddons;
    }


    public String getItemSlug() {
        return itemSlug;
    }

    public void setItemSlug(String itemSlug) {
        this.itemSlug = itemSlug;
    }

    public String getItemHash() {
        return itemHash;
    }

    public void setItemHash(String itemHash) {
        this.itemHash = itemHash;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", description='" + description + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemImage='" + itemImage + '\'' +
                ", isVeg=" + isVeg +
                ", restaurantId='" + restaurantId + '\'' +
                ", cuisineId='" + cuisineId + '\'' +
                ", customizable=" + customizable +
                ", isGstIncl=" + isGstIncl +
                ", excludeBase=" + excludeBase +
                ", taxId='" + taxId + '\'' +
                ", tax=" + tax +
                ", itemQty=" + itemQty +
                ", fOrderVariants=" + fOrderVariants +
                ", fOrderVExtras=" + fOrderVExtras +
                ", fOrderAddons=" + fOrderAddons +
                ", itemTotal=" + itemTotal +
                ", itemQTotal=" + itemQTotal +
                ", itemTax=" + itemTax +
                ", taxTotal=" + taxTotal +
                ", itemSlug='" + itemSlug + '\'' +
                ", itemHash='" + itemHash + '\'' +
                '}';
    }
}
