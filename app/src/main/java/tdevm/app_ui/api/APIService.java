package tdevm.app_ui.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import tdevm.app_ui.api.models.OneTimePassword;
import tdevm.app_ui.api.models.request.AddDishReview;
import tdevm.app_ui.api.models.request.AddRestaurantReview;
import tdevm.app_ui.api.models.request.NonDineOrder;
import tdevm.app_ui.api.models.request.Password;
import tdevm.app_ui.api.models.request.PaymentCapture;
import tdevm.app_ui.api.models.request.RestaurantOrder;
import tdevm.app_ui.api.models.request.User;
import tdevm.app_ui.api.models.response.UserApp;
import tdevm.app_ui.api.models.response.v2.FOrder.Checkout;
import tdevm.app_ui.api.models.response.v2.FOrder.FOrder;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.api.models.response.v2.RestaurantTable;
import tdevm.app_ui.api.models.response.v2.menu.Cuisine;
import tdevm.app_ui.api.models.response.v2.menu.CuisineMenuItems;
import tdevm.app_ui.api.models.response.v2.menu.MenuItem;
import tdevm.app_ui.api.models.response.v2.merged.MergedOrder;
import tdevm.app_ui.api.models.response.v2.reviews.DishReviews;
import tdevm.app_ui.api.models.response.v2.reviews.RestaurantReviews;
import tdevm.app_ui.api.models.response.v2.t_orders.TOrder;

/**
 * Created by Tridev on 04-10-2017.
 */

public interface APIService {

    //User
    @POST("m/user/register")
    Observable<Response<Object>> registerUser(@Body User user);
    @GET("verify/mobile/otp")
    Observable<Response<Object>> getMobileOTP(@Header("phone") Long phone);
    @POST("verify/mobile/otp")
    Observable<Response<Object>> verifyMobileOTP(@Body OneTimePassword oneTimePassword);
    @POST("m/user/login")
    Observable<Response<Object>> loginUser(@Body User user);
    @GET("m/user/me")
    Observable<Response<UserApp>> fetchUser(@Header("x-auth") String authToken);
    @PATCH("m/user/password")
    Observable<Response<Object>> changeUserPassword(@Header("x-auth") String authToken, @Body Password password);
    @PATCH("m/user")
    Observable<Response<Object>> updateUser(@Header("x-auth") String authToken, @Body UserApp user);


    //Restaurant Data
    @GET("m/restaurant")
    Observable<Response<Restaurant>> fetchRestaurantDetails(@QueryMap Map<String, String> query, @Header("x-auth") String authToken);
    @GET("m/restaurant")
    Observable<Response<List<Restaurant>>> fetchAllRestaurants(@QueryMap Map<String, String> query, @Header("x-auth") String authToken);
    @GET("m/restaurant/table_status")
    Observable<Response<RestaurantTable>> verifyTableVacancy(@Header("x-auth") String authToken, @QueryMap Map<String, String> query);
    @GET("m/cuisines")
    Observable<ArrayList<Cuisine>> fetchCuisines(@Header("x-auth") String authToken, @QueryMap Map<String, String> options);

    @GET("m/menu_items")
    Observable<ArrayList<MenuItem>> fetchAllMenuItems(@Header("x-auth") String authToken, @QueryMap Map<String, String> options);
    @GET("m/menu_items")
    Observable<ArrayList<MenuItem>> fetchMenuItemsByCuisine(@Header("x-auth") String authToken, @QueryMap Map<String, String> options);
    @GET("m/menu")
    Observable<Response<List<CuisineMenuItems>>> fetchMenuItems(@Header("x-auth") String authToken, @QueryMap Map<String, String> options);


    //Reviews
    @GET("m/menu_item/reviews")
    Observable<Response<ArrayList<DishReviews>>> fetchDishReviewsById(@Header("x-auth") String authToken, @QueryMap Map<String, String> options);
    @POST("m/menu_item/reviews")
    Observable<Response<Object>> addADishReview(@Header("x-auth") String authToken, @Body AddDishReview dishReview);
    @GET("m/restaurant/reviews")
    Observable<Response<ArrayList<RestaurantReviews>>> fetchRestaurantReviewsById(@Header("x-auth") String authToken, @QueryMap Map<String, String> options);
    @POST("m/restaurant/reviews")
    Observable<Response<Object>> addARestaurantReview(@Header("x-auth") String authToken, @Body AddRestaurantReview restaurantReview);


    //Running Orders
    @POST("m/t/orders")
    Observable<Response<TOrder>> createNewTempOrder(@Header("x-auth") String token, @Body RestaurantOrder restaurantOrder);
    @POST("m/t/orders/kot")
    Observable<Response<TOrder>> addItemsToTempOrder(@Header("x-auth") String token, @Body RestaurantOrder restaurantOrder);
    @GET("m/t/orders")
    Observable<Response<TOrder>> fetchMyRunningOrder(@Header("x-auth") String token, @QueryMap Map<String, String> options);
    @GET("m/t/orders/merge")
    Observable<Response<MergedOrder>> fetchMergedOrder(@Header("x-auth") String token, @QueryMap Map<String, String> options);
    @POST("m/t/orders/close")
    Observable<Response<FOrder>> closeRunningOrder(@Header("x-auth") String token, @QueryMap Map<String, String> options);
    @POST("m/t/orders/pay")
    Observable<Response<FOrder>> payOrder(@Header("x-auth") String token, @Body PaymentCapture capture);
    @GET("m/f/orders")
    Observable<Response<FOrder>> fetchClosedOrder(@Header("x-auth") String token, @QueryMap Map<String, String> options);

    //My Orders
    @GET("m/orders")
    Observable<Response<List<FOrder>>> fetchMyOrders(@Header("x-auth") String token);
    //Type2 Orders (Non Dine)
    @POST("m/f/orders/checkout")
    Observable<Response<Checkout>> checkoutOrder(@Header("x-auth") String token, @Body NonDineOrder nonDineOrder);
    @POST("m/f/orders/unpaid")
    Observable<Response<FOrder>> createUnpaidNonDineOrder(@Header("x-auth") String token, @Body NonDineOrder nonDineOrder);
    @POST("m/f/orders")
    Observable<Response<FOrder>> createPaidNonDineOrder(@Header("x-auth") String token, @Body NonDineOrder nonDineOrder);
    @POST("m/f/payment")
    Observable<Response<FOrder>> captureNonDineOrderPayment(@Header("x-auth") String token, @Body PaymentCapture paymentCapture);


}
