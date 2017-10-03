package tdevm.app_ui.api;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import tdevm.app_ui.api.models.OneTimePassword;
import tdevm.app_ui.api.models.request.User;

/**
 * Created by Tridev on 04-10-2017.
 */

public interface APIService {
    @POST("user/register")
    Observable<User> registerUser(@Body User user);

    @GET("verify/mobile/otp")
    Observable<String> getMobileOTP(@Header("phone") String phone);

    @POST("verify/mobile/otp")
    Observable<OneTimePassword> verifyMobileOTP(@Body OneTimePassword oneTimePassword);

    @POST("user/login")
    Observable<User> loginUser(@Body User user);

    @GET("user/me")
    Observable<String> fetchUser(@Header("x-auth") String authToken);
}
