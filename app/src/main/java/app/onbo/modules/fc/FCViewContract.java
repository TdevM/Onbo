package app.onbo.modules.fc;

import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.base.BaseView;

public interface FCViewContract {

    interface FCActivityView extends BaseView{
        void onFCFetched(FoodCourt foodCourt);
        void onFCFetchFailure();
    }
}
