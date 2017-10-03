package tdevm.app_ui.dagger.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.dagger.scopes.PerActivity;

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
}
