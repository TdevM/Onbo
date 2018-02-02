package tdevm.app_ui.modules.nondinein;

import tdevm.app_ui.base.BasePresenterMVP;

/**
 * Created by Tridev on 02-02-2018.
 */

public interface NonDinePresenterContract {

    interface NonDineRestaurantDetails extends BasePresenterMVP<NonDineViewContract.NonDineRestaurantDetailsView>{
        void attachView(NonDineViewContract.NonDineRestaurantDetailsView view);
        void detachView();
    }
}
