package onbo.app.di.components;

import android.content.SharedPreferences;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.SettingsClient;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import onbo.app.api.cart.CartItemDao;
import onbo.app.api.cart.CartSelectionDao;
import onbo.app.di.modules.AppContextModule;
import onbo.app.di.modules.LocationModule;
import onbo.app.di.modules.NetworkModule;
import onbo.app.di.modules.RoomModule;

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
