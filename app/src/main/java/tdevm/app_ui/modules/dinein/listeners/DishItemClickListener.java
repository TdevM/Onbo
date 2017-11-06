package tdevm.app_ui.modules.dinein.listeners;

import tdevm.app_ui.api.models.response.DishesOfCuisine;

/**
 * Created by Tridev on 21-10-2017.
 */

public interface DishItemClickListener {

    void getDishItemQuant(DishesOfCuisine dishesOfCuisine, int oldValue, int newValue);
}
