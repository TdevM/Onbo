package tdevm.app_ui.dagger.components;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import retrofit2.Retrofit;
import tdevm.app_ui.dagger.modules.NetworkModule;

/**
 * Created by Tridev on 04-10-2017.
 */

@Singleton
@Component(modules = {NetworkModule.class})
public interface NetworkComponent {


}
