package tdevm.app_ui;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import tdevm.app_ui.dagger.components.APIComponent;
import tdevm.app_ui.dagger.components.ApplicationComponent;
import tdevm.app_ui.dagger.components.DaggerAPIComponent;
import tdevm.app_ui.dagger.components.DaggerApplicationComponent;
import tdevm.app_ui.dagger.modules.APIModule;
import tdevm.app_ui.dagger.modules.AppModule;
import tdevm.app_ui.dagger.modules.NetworkModule;

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
                .networkModule(new NetworkModule(this, "https://tdevmapi.herokuapp.com/api/v3/"))
                .appModule(new AppModule(this))
                .build();

    }
    private void initializeAPIComponent(){
        apiComponent = DaggerAPIComponent
                .builder()
                .aPIModule(new APIModule())
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
