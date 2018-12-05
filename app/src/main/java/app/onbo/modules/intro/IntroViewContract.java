package app.onbo.modules.intro;

import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.t_orders.TOrder;
import app.onbo.base.BaseView;

public interface IntroViewContract {


    interface SplashView extends BaseView{
        void onDineOrderRunning(TOrder tOrder);
        void onNoDineOrderRunning();
        void onOrderFetchFailure();
        void onFOrderFetched(FOrder fOrder);
        void onFOrderFetchFailure();
    }
}
