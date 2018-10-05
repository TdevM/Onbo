package tdevm.app_ui.modules.account;

import tdevm.app_ui.api.models.request.Password;
import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.base.BasePresenterMVP;

public interface AccountPresenterContract {

    interface ChangePasswordPresenter extends BasePresenterMVP<AccountViewContract.ChangePasswordView>{
        void attachView(AccountViewContract.ChangePasswordView view);
        void detachView();
        void changeUserPassword(Password password);
    }


    interface EditAccountDetailPresenter extends BasePresenterMVP<AccountViewContract.EditAccountView>{
        void attachView(AccountViewContract.EditAccountView view);
        void detachView();
        void updateUser(UserApp userApp);
    }
}
