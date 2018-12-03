package onbo.app.di.modules;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import onbo.app.api.APIService;
import onbo.app.api.models.MySharedPreferences;
import onbo.app.di.scopes.PerActivity;
import onbo.app.utils.PreferenceUtils;

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
    PreferenceUtils authUtils(SharedPreferences sharedPreferences){
        return new PreferenceUtils(new MySharedPreferences(sharedPreferences));
    }

}
