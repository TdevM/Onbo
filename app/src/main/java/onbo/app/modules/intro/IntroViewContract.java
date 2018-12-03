package onbo.app.modules.intro;

import onbo.app.api.models.response.v2.FOrder.FOrder;
import onbo.app.api.models.response.v2.t_orders.TOrder;
import onbo.app.base.BaseView;

public interface IntroViewContract {


    interface SplashView extends BaseView{
        void onDineOrderRunning(TOrder tOrder);
        void onNoDineOrderRunning();
        void onOrderFetchFailure();
        void onFOrderFetched(FOrder fOrder);
        void onFOrderFetchFailure();
    }
}
