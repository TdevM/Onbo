
package tdevm.app_ui.api.models.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderDetail {

    @SerializedName("dish_id")
    @Expose
    private String dishId;
    @SerializedName("dish_name")
    @Expose
    private String dishName;
    @SerializedName("dish_quantity")
    @Expose
    private String dishQuantity;
    @SerializedName("dish_price")
    @Expose
    private Integer dishPrice;

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishQuantity() {
        return dishQuantity;
    }

    public void setDishQuantity(String dishQuantity) {
        this.dishQuantity = dishQuantity;
    }

    public Integer getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(Integer dishPrice) {
        this.dishPrice = dishPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail"+"dishId"+ dishId+"dishName"+ dishName+"dishQuantity"+"dishPrice";
    }

}
