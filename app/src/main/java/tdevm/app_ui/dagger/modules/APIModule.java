package tdevm.app_ui.dagger.modules;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.cart.CartItemDao;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.cart.CartSelectionDao;
import tdevm.app_ui.api.models.MySharedPreferences;
import tdevm.app_ui.dagger.scopes.PerActivity;
import tdevm.app_ui.utils.AuthUtils;
import tdevm.app_ui.utils.CartHelper;

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
