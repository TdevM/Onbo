package app.onbo.utils;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import javax.inject.Inject;

import app.onbo.OnboApplication;
import app.onbo.api.APIService;
import app.onbo.api.models.DecodedToken;
import app.onbo.api.models.request.RefreshToken;
import app.onbo.modules.auth.AuthenticationActivity;
import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class AuthInterceptor implements Interceptor {

    private static final String AUTH_LOGIN_TOKEN = "AUTH_LOGIN_TOKEN";
    public String exchangeTokenURL = "https://api.onbo.app/v1/m/user/token";

    @Inject
    public PreferenceUtils preferenceUtils;
    @Inject
    public APIService apiService;

    private Application application;


    public AuthInterceptor(Application application) {
        this.application = application;
    }

    public static final String TAG = AuthInterceptor.class.getSimpleName();


    @Override
    public Response intercept(Chain chain) throws IOException {
        ((OnboApplication) application).getApiComponent().inject(this);
        Request request = chain.request();
        Log.d(TAG, "Auth Interceptor says hellO!");

        if (preferenceUtils.getAuthLoginState()) {
            request = request.newBuilder()
                    .addHeader("Authorization", "Bearer " + preferenceUtils.getAuthLoginToken())
                    .build();

        }

        return chain.proceed(request);


    }


}
