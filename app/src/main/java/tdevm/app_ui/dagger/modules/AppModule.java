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
public class AppModule {

    Application mApplication;
    GoogleLocationApiManager locationApiManager;

    public AppModule(Application application,GoogleLocationApiManager googleLocationApiManager) {
        mApplication = application;
        locationApiManager = googleLocationApiManager;
    }
    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    @Provides
    @Singleton
    GoogleApiClient providesGoogleApi(Context context) {
        return new GoogleApiClient.Builder(context)
                .addOnConnectionFailedListener(locationApiManager)
                .addConnectionCallbacks(locationApiManager)
                .addApi(LocationServices.API)
                .build();
    }
}