package app.onbo.modules.fc;

import app.onbo.base.BasePresenterMVP;

public interface FCPresenterContract {

    interface FCPresenter extends BasePresenterMVP<FCViewContract.FCActivityView> {
        @Override
        void attachView(FCViewContract.FCActivityView view);

        @Override
        void detachView();

        void fetchFCRestaurants(String fcUUID);
    }

}
