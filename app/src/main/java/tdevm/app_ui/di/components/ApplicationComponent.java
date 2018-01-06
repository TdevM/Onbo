package tdevm.app_ui.di.components;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import tdevm.app_ui.api.cart.CartItemDao;
import tdevm.app_ui.api.cart.CartSelectionDao;
import tdevm.app_ui.di.modules.AppContextModule;
import tdevm.app_ui.di.modules.NetworkModule;
import tdevm.app_ui.di.modules.RoomModule;

/**
 * Created by Tridev on 04-10-2017.
 */

@Singleton
@Component(modules = {NetworkModule.class, AppContextModule.class, RoomModule.class})
public interface ApplicationComponent {

    Retrofit exposeRetrofit();
    SharedPreferences exposeSharedPreferences();
    CartSelectionDao cartSelectionDao();
    CartItemDao cartItemDao();

}
