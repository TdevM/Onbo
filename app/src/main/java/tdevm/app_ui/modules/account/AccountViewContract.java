package tdevm.app_ui.modules.account;

import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.base.BaseView;

public interface AccountViewContract {

    interface ChangePasswordView extends BaseView{
        void onPasswordChangeSuccess(Object o);
        void onPasswordChangeFailure(Object o);
    }

    interface EditAccountView extends BaseView{
        void onAccountDetailsUpdated(Object app);
        void onAccountDetailsUpdateFailure();
    }
}
