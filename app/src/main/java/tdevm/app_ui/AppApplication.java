package tdevm.app_ui;

import android.app.Application;

import tdevm.app_ui.dagger.components.DaggerNetworkComponent;
import tdevm.app_ui.dagger.components.NetworkComponent;
import tdevm.app_ui.dagger.modules.AppModule;
import tdevm.app_ui.dagger.modules.NetworkModule;

/**
 * Created by Tridev on 03-10-2017.
 */

public class AppApplication extends Application {

    NetworkComponent networkComponent;
    @Override
    public void onCreate() {
        super.onCreate();
        initializeNetworkComponent();
    }
    private void initializeNetworkComponent() {
        networkComponent = DaggerNetworkComponent
                .builder()
                .networkModule(new NetworkModule(this, "https://tdevmapi.herokuapp.com/api/v3/"))
                .appModule(new AppModule(this))
                .build();

    }

    public NetworkComponent getNetworkComponent() {
        return networkComponent;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
