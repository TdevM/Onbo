package tdevm.app_ui.modules.dinein.callbacks;


import tdevm.app_ui.api.models.response.DishesOfCuisine;

/**
 * Created by Tridev on 21-10-2017.
 */

public interface DishItemClickListener {

    void onPlusButtonClicked(DishesOfCuisine dishesOfCuisine, int num);
    void onMinusButtonClicked(DishesOfCuisine dishesOfCuisine, int num);
    void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine,int flag);       //Customizable dish (Parent) clicked
    void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine,Long parentDishId, int flag);   //Customizable dish (Child) clicked
}
