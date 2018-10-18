package tdevm.app_ui.modules.intro;

import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;
import tdevm.app_ui.base.BaseView;

public interface IntroViewContract {


    interface SplashView extends BaseView{
        void onDineOrderRunning(TOrder tOrder);
        void onNoDineOrderRunning();
        void onOrderFetchFailure();
        void onFOrderFetched(FOrder fOrder);
        void onFOrderFetchFailure();
    }
}
