package app.onbo.di.modules;

/**
 * Created by Tridev on 04-10-2017.
 */

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import app.onbo.api.APIService;
import app.onbo.utils.AuthInterceptor;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.TokenRefresher;
import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = {AppContextModule.class})
public class NetworkModule {

    public static final String SHARED_PREFS = "MyPrefs";
    private String mBaseUrl;

    public NetworkModule(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return application.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    @Singleton
    @Provides
    public Gson providesGson() {
        GsonBuilder builder = new GsonBuilder();
        return builder.create();
    }

    @Singleton
    @Provides
    GsonConverterFactory providesGsonConverterFactory() {
        return GsonConverterFactory.create();
    }


    @Singleton
    @Provides
    HttpLoggingInterceptor providesLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    @Singleton
    @Provides
    @Named("ok-1")
    OkHttpClient providesOkHttpClient1(Cache cache) {
        return new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    AuthInterceptor providesAuthInterceptor(Application application) {
        return new AuthInterceptor(application);
    }

    @Singleton
    @Provides
    TokenRefresher providesTokenRefresher(Application application) {
        return new TokenRefresher(application);
    }




    @Singleton
    @Provides
    @Named("ok-2")
    OkHttpClient providesOkHttpClient2(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor, AuthInterceptor authInterceptor, TokenRefresher tokenRefresher) {
        return new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(authInterceptor)
                .authenticator(tokenRefresher)
                .cache(cache)
                .build();
    }

    @Singleton
    @Provides
    public Cache providesOkHTTPCache(Application application) {
        int cacheSize = 10 * 1024 * 1024;
        return new Cache(application.getCacheDir(), cacheSize);
    }

    @Singleton
    @Provides
    RxJava2CallAdapterFactory providesRxJavaCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    Retrofit providesRetrofit(@Named("ok-2") OkHttpClient client, GsonConverterFactory converterFactory, RxJava2CallAdapterFactory adapterFactory) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .build();
    }

}
