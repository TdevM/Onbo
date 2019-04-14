package app.onbo.modules.fc;

import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.base.BasePresenterMVP;

public interface FCPresenterContract {

    interface FCPresenter extends BasePresenterMVP<FCViewContract.FCActivityView> {
        @Override
        void attachView(FCViewContract.FCActivityView view);

        @Override
        void detachView();

        void fetchFCRestaurants(String fcUUID);
    }

    interface FCPremisePresenter extends BasePresenterMVP<FCViewContract.FCPremiseDetailView>{
        @Override
        void attachView(FCViewContract.FCPremiseDetailView view);

        @Override
        void detachView();
        void fetchMenuItems(Restaurant restaurant);
        void saveFCQRTransaction(Restaurant restaurant);
        void clearCart();

    }

    interface FCListPresenterContract extends BasePresenterMVP<FCViewContract.FCListActivity>{
        @Override
        void attachView(FCViewContract.FCListActivity view);

        void fetchFCList();

        @Override
        void detachView();
    }

}
