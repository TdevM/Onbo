package app.onbo.utils;

import android.app.Application;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import javax.inject.Inject;

import app.onbo.OnboApplication;
import app.onbo.api.APIService;
import app.onbo.api.models.DecodedToken;
import app.onbo.api.models.request.RefreshToken;
import app.onbo.api.models.response.v2.LoginResponse;
import app.onbo.modules.auth.AuthenticationActivity;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

public class TokenRefresher implements Authenticator {

    public static final String TAG = TokenRefresher.class.getSimpleName();

    private Application application;

    @Inject
    public APIService apiService;

    @Inject
    public PreferenceUtils preferenceUtils;

    public TokenRefresher(Application application) {
        this.application = application;
    }

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        ((OnboApplication) application).getApiComponent().inject(this);
        Log.d(TAG, "Token Refresher");

        if (preferenceUtils.getAuthLoginState()) {
            DecodedToken token = new DecodedToken(preferenceUtils.getAuthLoginToken());
            Log.d(TAG, "TOKEN Parsed" + token.getDecoded().getUser_id());
            Log.d(TAG, "Token Refresher says hellO!");
            if (response.code() == 401) {
                RefreshToken refreshToken = new RefreshToken(token.getDecoded().getUser_id(), preferenceUtils.getAuthRefreshToken());
                Call<LoginResponse> exchangeTokenCall = apiService.exchangeAuthToken(refreshToken);
                retrofit2.Response<LoginResponse> exchangeToken = exchangeTokenCall.execute();
                Log.d(TAG, "Token refresher 401 Case");
                if (exchangeToken.body() != null && exchangeToken.code() == 200) {
                    preferenceUtils.updateAuthToken(exchangeToken.body().getToken());
                    return response.request().newBuilder()
                            .header("Authorization", "Bearer " + preferenceUtils.getAuthLoginToken())
                            .build();
                } else if (exchangeToken.code() == 403) {
                    preferenceUtils.clearAuth();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(application.getApplicationContext(), "Your session is expired", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(application.getApplicationContext(), AuthenticationActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            application.startActivity(intent);
                        }
                    });

                }
            }
        } else {
            return null;
        }


        return null;
    }
}
