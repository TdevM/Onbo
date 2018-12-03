package onbo.app;

import android.support.multidex.MultiDexApplication;

import com.onesignal.OneSignal;

import onbo.app.di.components.APIComponent;
import onbo.app.di.components.ApplicationComponent;

import onbo.app.di.components.DaggerAPIComponent;
import onbo.app.di.components.DaggerApplicationComponent;
import onbo.app.di.modules.AppContextModule;
import onbo.app.di.modules.NetworkModule;

/**
 * Created by Tridev on 03-10-2017.
 */

public class AppApplication extends MultiDexApplication {

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
                .networkModule(new NetworkModule("https://t71.herokuapp.com/api/v3/"))
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
