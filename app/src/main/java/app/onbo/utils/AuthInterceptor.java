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
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

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
        Request request = chain.request();
        okhttp3.Response response = chain.proceed(request);
        ((OnboApplication) application).getApiComponent().inject(this);
        if (preferenceUtils.getAuthLoginState()) {
            DecodedToken token = new DecodedToken(preferenceUtils.getAuthLoginToken());
            Log.d(TAG, "TOKEN Parsed" + token.getDecoded().getUser_id());
            Log.d(TAG, "Auth Interceptor says hellO!");
            if (response.code() == 401) {
                // Exchange Token failed
                // User should be kicked out of the app
                RefreshToken refreshToken = new RefreshToken(token.getUser_id(), preferenceUtils.getAuthRefreshToken());



            } else if (response.code() == 402) {
                // Obtain a new Token, Update sharedPrefs and resume the APP.

                Toast.makeText(application.getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(application.getApplicationContext(), AuthenticationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                application.startActivity(intent);
            }
        }


        return response;
    }
}
