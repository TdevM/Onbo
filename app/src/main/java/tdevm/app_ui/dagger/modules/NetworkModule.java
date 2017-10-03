package tdevm.app_ui.dagger.modules;

/**
 * Created by Tridev on 04-10-2017.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Tridev on 01-10-2017.
 */
@Module
public class NetworkModule {

    private String mBaseUrl;
    private Context mContext;

    public NetworkModule(Context context, String baseUrl) {
        mContext = context;
        mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Singleton
    @Provides
    public Gson provideGson(){
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    @Singleton
    @Provides
    GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }


    @Singleton
    @Provides
    @Named("ok-1")
    OkHttpClient provideOkHttpClient1(Cache cache) {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    @Named("ok-2")
    OkHttpClient provideOkHttpClient2(Cache cache) {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    public Cache providesOkHTTPCache(Application application){
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(),cacheSize);
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit provideRetrofit(@Named("ok-1") OkHttpClient client, GsonConverterFactory converterFactory, RxJava2CallAdapterFactory adapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build();
    }

    @Provides
    @Singleton
    Context provideContext() {
        return mContext;
    }
}
