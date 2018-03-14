package tdevm.app_ui.modules.entry;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.modules.dinein.DineInViewContract;

/**
 * Created by Tridev on 14-03-2018.
 */

public class MenuEntryPresenter extends BasePresenter implements MenuEntryPresenterContract.RestaurantMenuEntryPresenter{

    public static final String TAG = MenuEntryPresenter.class.getSimpleName();

    private APIService apiService;
    private CompositeDisposable compositeDisposable;

    @Inject
    public MenuEntryPresenter(APIService apiService) {
        this.apiService = apiService;
        compositeDisposable = new CompositeDisposable();
    }

    private MenuEntryViewContract.RestaurantMenuEntryView view;

    @Override
    public void attachView(MenuEntryViewContract.RestaurantMenuEntryView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
