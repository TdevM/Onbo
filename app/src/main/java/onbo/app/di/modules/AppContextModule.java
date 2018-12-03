package onbo.app.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

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