package tdevm.app_ui.di.modules;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.MySharedPreferences;
import tdevm.app_ui.di.scopes.PerActivity;
import tdevm.app_ui.utils.AuthUtils;

/**
 * Created by Tridev on 04-10-2017.
 */

@Module
public class APIModule {

    @PerActivity
    @Provides
    APIService providesAPIService(Retrofit retrofit){
        return retrofit.create(APIService.class);
    }

    @PerActivity
    @Provides
    AuthUtils authUtils(SharedPreferences sharedPreferences){
        return new AuthUtils(new MySharedPreferences(sharedPreferences));
    }

}