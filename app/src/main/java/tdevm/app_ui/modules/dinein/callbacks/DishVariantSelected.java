package tdevm.app_ui.modules.dinein.callbacks;


import tdevm.app_ui.api.models.response.DishVariant;
import tdevm.app_ui.api.models.response.DishesOfCuisine;

/**
 * Created by Tridev on 20-11-2017.
 */

public interface DishVariantSelected{
    void onDishVariantSelected(DishesOfCuisine childDish, DishesOfCuisine parentDish);
    void onDishVariantSelected(DishVariant dish, DishesOfCuisine parentDish);
}