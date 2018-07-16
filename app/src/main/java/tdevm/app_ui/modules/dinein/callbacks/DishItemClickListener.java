package tdevm.app_ui.modules.dinein.callbacks;


import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;

/**
 * Created by Tridev on 21-10-2017.
 */

public interface DishItemClickListener {

    void onPlusButtonClicked(MenuItem menuItem, int num);
    void onMinusButtonClicked(MenuItem menuItem, int num);
    void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine,int flag);       //Customizable dish (Parent) clicked
    void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine,Long parentDishId, int flag);   //Customizable dish (Child) clicked


    void onDishImageClicked(MenuItem menuItem);
    void onCustomizableItemClicked(MenuItem menuItem);
}
