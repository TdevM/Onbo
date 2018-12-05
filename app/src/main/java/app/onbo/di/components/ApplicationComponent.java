package app.onbo.di.components;

import android.content.SharedPreferences;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.SettingsClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import app.onbo.api.cart.CartItemDao;
import app.onbo.api.cart.CartSelectionDao;
import app.onbo.di.modules.AppContextModule;
import app.onbo.di.modules.LocationModule;
import app.onbo.di.modules.NetworkModule;
import app.onbo.di.modules.RoomModule;

/**
 * Created by Tridev on 04-10-2017.
 */

@Singleton
@Component(modules = {NetworkModule.class, AppContextModule.class, RoomModule.class, LocationModule.class})
public interface ApplicationComponent {

    Retrofit exposeRetrofit();
    SharedPreferences exposeSharedPreferences();
    CartSelectionDao cartSelectionDao();
    CartItemDao cartItemDao();
    FusedLocationProviderClient exposeFusedClient();
    SettingsClient exposeSettingsClient();

}
