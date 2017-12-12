package tdevm.app_ui.dagger.modules;

import android.app.Application;
import android.content.Context;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import tdevm.app_ui.utils.GoogleLocationApiManager;

/**
 * Created by Tridev on 04-10-2017.
 */

@Module
public class AppContextModule {

    Application mApplication;

    public AppContextModule(Application application) {
        mApplication = application;
    }
    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

}