package tdevm.app_ui.di.modules;

import android.app.Application;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.SettingsClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Tridev on 14-03-2018.
 */


@Module(includes = AppContextModule.class)
public class LocationModule {

    @Provides
    @Singleton
    FusedLocationProviderClient fusedLocationProviderClient(Application application){
        return LocationServices.getFusedLocationProviderClient(application);
    }

    @Provides
    @Singleton
    SettingsClient settingsClient(Application application){
        return LocationServices.getSettingsClient(application);
    }
}
