package tdevm.app_ui.modules.account;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.CartHelper;
import tdevm.app_ui.utils.PreferenceUtils;

public class EditAccountDetailPresenter extends BasePresenter implements AccountPresenterContract.EditAccountDetailPresenter {

    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;
    private AccountViewContract.EditAccountView editAccountView;


    @Inject
    public EditAccountDetailPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.cartHelper = cartHelper;
        compositeDisposable = new CompositeDisposable();
    }


    @Override
    public void attachView(AccountViewContract.EditAccountView view) {
        this.editAccountView = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @Override
    public void updateUser(UserApp userApp) {
        Observable<Response<Object>> observable = apiService.updateUser(preferenceUtils.getAuthLoginToken(),userApp);
        subscribe(observable, new Observer<Response<Object>>() {
            @Override
            public void onSubscribe(Disposable disposable) {
                editAccountView.showProgressUI();
                compositeDisposable.add(disposable);
            }

            @Override
            public void onNext(Response<Object> objectResponse) {
                if(objectResponse.isSuccessful()){
                    editAccountView.onAccountDetailsUpdated(objectResponse.body());
                }else if(objectResponse.code()!=200){
                    editAccountView.onAccountDetailsUpdateFailure();
                }
            }

            @Override
            public void onError(Throwable throwable) {
                editAccountView.onAccountDetailsUpdateFailure();
            }

            @Override
            public void onComplete() {
                editAccountView.hideProgressUI();
            }
        });
    }
}
