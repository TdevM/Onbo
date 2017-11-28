package tdevm.app_ui.api;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import tdevm.app_ui.api.models.OneTimePassword;
import tdevm.app_ui.api.models.request.RestaurantOrder;
import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.api.models.response.Cuisine;
import tdevm.app_ui.api.models.response.Dish;
import tdevm.app_ui.api.models.response.DishesOfCuisine;
import tdevm.app_ui.api.models.response.RestaurantTable;

/**
 * Created by Tridev on 04-10-2017.
 */

public interface APIService {

    //User
    @POST("user/register")
    Observable<Response<Object>> registerUser(@Body User user);
    @GET("verify/mobile/otp")
    Observable<Response<Object>> getMobileOTP(@Header("phone") Long phone);
    @POST("verify/mobile/otp")
    Observable<Response<Object>> verifyMobileOTP(@Body OneTimePassword oneTimePassword);
    @POST("user/login")
    Observable<Response<Object>> loginUser(@Body User user);
    @GET("user/me")
    Observable<String> fetchUser(@Header("x-auth") String authToken);


    //Restaurant Data
    @GET("restaurant/table_status")
    Observable<Response<RestaurantTable>> verifyTableVacancy(@Header("x-auth") String authToken, @QueryMap Map<String,String> query);
    @GET("restaurant/cuisines")
    Observable<ArrayList<Cuisine>> fetchCuisines(@Header("x-auth") String authToken,@QueryMap Map<String, String> options);
    @GET("restaurant/dishes/all")
    Observable<ArrayList<Dish>> fetchAllDishes(@Header("x-auth") String authToken,@QueryMap Map<String, String> options);
    @GET("restaurant/dishes")
    Observable<ArrayList<DishesOfCuisine>> fetchDishesByCuisine(@Header("x-auth") String authToken,@QueryMap Map<String, String> options);
    @GET("restaurant/dishes/variants")
    Observable<ArrayList<DishesOfCuisine>> fetchDishVariantsByCuisines(@Header("x-auth") String authToken,@QueryMap Map<String, String> options);



    //Running Orders
    @POST("m/orders/temp")
    Observable<Response<Object>> createNewOrder(@Header("x-auth") String token, @Body RestaurantOrder restaurantOrder);

//    @GET("m/orders/temp")
//    Observable<ArrayList<TempOrder>> fetchMyRunningOrder(@Header("x-auth") String token, @QueryMap Map<String,String> options);

    @PATCH("m/orders/temp")
    Observable<Response<Object>> addItemsToOrder(@Header("x-auth") String token, @Body RestaurantOrder restaurantOrder);

}
