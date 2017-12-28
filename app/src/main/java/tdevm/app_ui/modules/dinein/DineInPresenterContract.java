package tdevm.app_ui.modules.dinein;

import java.util.ArrayList;
import java.util.Map;

import tdevm.app_ui.api.models.response.Cuisine;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.base.BasePresenterMVP;
import tdevm.app_ui.base.BaseView;
import tdevm.app_ui.modules.auth.AuthViewContract;

/**
 * Created by Tridev on 06-11-2017.
 */

public interface DineInPresenterContract {

    interface DishMenuPresenter extends BasePresenterMVP<DineInViewContract.DishMenuView>{
        void attachView(DineInViewContract.DishMenuView view);
        void detachView();
        void FetchAllCuisines(Map<String,String> map);
    }

    interface SingleCuisineGridPresenter extends BasePresenterMVP<DineInViewContract.SingleCuisineGridView>{
        void attachView(DineInViewContract.SingleCuisineGridView view);
        void detachView();
        void fetchDishesByCuisines(Map<String,String> map);
        void fetchVariantsOfADish(Map<String,String> map);
    }

}
