package app.onbo.modules.account;

import app.onbo.api.models.request.Password;
import app.onbo.api.models.response.UserApp;
import app.onbo.base.BasePresenterMVP;

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
