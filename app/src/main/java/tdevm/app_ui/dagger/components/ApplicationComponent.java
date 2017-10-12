package tdevm.app_ui.dagger.components;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import tdevm.app_ui.dagger.modules.AppModule;
import tdevm.app_ui.dagger.modules.NetworkModule;
import tdevm.app_ui.modules.auth.fragments.AuthLoginFragment;

/**
 * Created by Tridev on 04-10-2017.
 */

@Singleton
@Component(modules = {NetworkModule.class, AppModule.class})
public interface ApplicationComponent {

    Retrofit exposeRetrofit();
    Context exposeContext();
    SharedPreferences exposeSharedPreferences();

}
