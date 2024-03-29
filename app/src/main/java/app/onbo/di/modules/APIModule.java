package app.onbo.di.modules;

import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import app.onbo.api.APIService;
import app.onbo.api.models.MySharedPreferences;
import app.onbo.di.scopes.PerActivity;
import app.onbo.utils.PreferenceUtils;

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
