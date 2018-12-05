package app.onbo.modules.account;

import app.onbo.base.BaseView;

public interface AccountViewContract {

    interface ChangePasswordView extends BaseView{
        void onPasswordChangeSuccess(Object o);
        void onPasswordChangeFailure(Object o);
        void onGenericError();
    }

    interface EditAccountView extends BaseView{
        void onAccountDetailsUpdated(Object app);
        void onAccountDetailsUpdateFailure();
    }
}
