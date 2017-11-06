package tdevm.app_ui.modules.dinein;

import java.util.ArrayList;

import tdevm.app_ui.api.models.response.Cuisine;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.base.BaseView;

/**
 * Created by Tridev on 06-11-2017.
 */

public interface DineInContract {

    interface DishMenuView extends BaseView{
        void onCuisinesFetched(ArrayList<Cuisine> arrayList);
    }

    interface TopRatedView extends BaseView{

    }

    interface CartView extends BaseView{

    }

    interface SingleCuisineGridView extends BaseView{
        void onDishesOfCuisinesFetched(ArrayList<DishesOfCuisine> arrayList);
    }


}
