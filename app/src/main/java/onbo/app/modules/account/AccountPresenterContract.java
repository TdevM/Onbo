package onbo.app.modules.account;

import onbo.app.api.models.request.Password;
import onbo.app.api.models.response.UserApp;
import onbo.app.base.BasePresenterMVP;

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
