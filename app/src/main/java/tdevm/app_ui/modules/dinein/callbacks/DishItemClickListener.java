package tdevm.app_ui.modules.dinein.callbacks;


import tdevm.app_ui.api.models.response.DishesOfCuisine;

/**
 * Created by Tridev on 21-10-2017.
 */

public interface DishItemClickListener {

    void onPlusButtonClicked(DishesOfCuisine dishesOfCuisine, int num);
    void onMinusButtonClicked(DishesOfCuisine dishesOfCuisine, int num);
    void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine);
    void onCustomizableItemClicked(DishesOfCuisine dishesOfCuisine, int flag);                //Child dish was clicked
    void onCustomizableItemClicked(DishesOfCuisine child, DishesOfCuisine parent, int flag);  // Parent dish was clicked;
}
