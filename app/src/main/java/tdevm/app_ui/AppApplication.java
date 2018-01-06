package tdevm.app_ui;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import tdevm.app_ui.di.components.APIComponent;
import tdevm.app_ui.di.components.ApplicationComponent;
import tdevm.app_ui.di.components.DaggerAPIComponent;
import tdevm.app_ui.di.components.DaggerApplicationComponent;
import tdevm.app_ui.di.modules.AppContextModule;
import tdevm.app_ui.di.modules.NetworkModule;

/**
 * Created by Tridev on 03-10-2017.
 */

public class AppApplication extends Application {

    ApplicationComponent applicationComponent;
    APIComponent apiComponent;
    @Override
    public void onCreate() {
        initializeApplicationComponent();
        initializeAPIComponent();
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

    }
    private void initializeApplicationComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .networkModule(new NetworkModule("https://z71.herokuapp.com/api/v3/"))
                .appContextModule(new AppContextModule(this))
                .build();

    }
    private void initializeAPIComponent(){
        apiComponent = DaggerAPIComponent
                .builder()
                .applicationComponent(getApplicationComponent())
                .build();
    }

    public APIComponent getApiComponent() {
        return apiComponent;
    }

    public ApplicationComponent getApplicationComponent(){
        return  applicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
