package tdevm.app_ui;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import tdevm.app_ui.dagger.components.ApplicationComponent;
import tdevm.app_ui.dagger.components.DaggerApplicationComponent;
import tdevm.app_ui.dagger.modules.AppModule;
import tdevm.app_ui.dagger.modules.NetworkModule;

/**
 * Created by Tridev on 03-10-2017.
 */

public class AppApplication extends Application {

    ApplicationComponent applicationComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
      initializeApplicationComponent();
    }
    private void initializeApplicationComponent() {
        applicationComponent = DaggerApplicationComponent
                .builder()
                .networkModule(new NetworkModule(this, "https://tdevmapi.herokuapp.com/api/v3/"))
                .appModule(new AppModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent(){
        return  applicationComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
