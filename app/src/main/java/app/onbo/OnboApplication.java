package app.onbo;

import android.support.multidex.MultiDexApplication;

import com.onesignal.OneSignal;

import app.onbo.di.components.APIComponent;
import app.onbo.di.components.ApplicationComponent;

import app.onbo.di.components.DaggerAPIComponent;
import app.onbo.di.components.DaggerApplicationComponent;
import app.onbo.di.modules.AppContextModule;
import app.onbo.di.modules.NetworkModule;

/**
 * Created by Tridev on 03-10-2017.
 */

public class OnboApplication extends MultiDexApplication {

    ApplicationComponent applicationComponent;
    APIComponent apiComponent;

    @Override
    public void onCreate() {
        initializeApplicationComponent();
        initializeAPIComponent();
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);

        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();


    }

    private void initializeApplicationComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .networkModule(new NetworkModule("https://api.onbo.app/v1/"))
                .appContextModule(new AppContextModule(this))
                .build();

    }

    private void initializeAPIComponent() {
        apiComponent = DaggerAPIComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .build();
    }

    public APIComponent getApiComponent() {
        return apiComponent;
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
