package app.onbo.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.onbo.api.models.RemoteConfig;
import app.onbo.api.models.request.RefreshToken;
import app.onbo.api.models.response.v2.FcRestaurant;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.api.models.response.v2.LoginResponse;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import app.onbo.api.models.OneTimePassword;
import app.onbo.api.models.request.AddDishReview;
import app.onbo.api.models.request.AddRestaurantReview;
import app.onbo.api.models.request.NonDineOrder;
import app.onbo.api.models.request.Password;
import app.onbo.api.models.request.PaymentCapture;
import app.onbo.api.models.request.RestaurantOrder;
import app.onbo.api.models.request.User;
import app.onbo.api.models.response.UserApp;
import app.onbo.api.models.response.v2.FOrder.Checkout;
import app.onbo.api.models.response.v2.FOrder.FOrder;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.RestaurantTable;
import app.onbo.api.models.response.v2.menu.Cuisine;
import app.onbo.api.models.response.v2.menu.CuisineMenuItems;
import app.onbo.api.models.response.v2.menu.MenuItem;
import app.onbo.api.models.response.v2.merged.MergedOrder;
import app.onbo.api.models.response.v2.reviews.DishReviews;
import app.onbo.api.models.response.v2.reviews.RestaurantReviews;
import app.onbo.api.models.response.v2.t_orders.TOrder;

/**
 * Created by Tridev on 04-10-2017.
 */

public interface APIService {

    //User
    @POST("m/user/register")
    Observable<Response<Object>> registerUser(@Body User user);

    //User
    @PATCH("m/user/register")
    Observable<Response<Object>> registerUserUpdate(@Body User user);

    @GET("verify/mobile/otp")
    Observable<Response<Object>> getMobileOTP(@Header("phone") Long phone);

    @POST("verify/mobile/otp")
    Observable<Response<Object>> verifyMobileOTP(@Body OneTimePassword oneTimePassword);

    @POST("m/user/login")
    Observable<Response<LoginResponse>> loginUser(@Body User user);

    @GET("m/user/me")
    Observable<Response<UserApp>> fetchUser();

    @PATCH("m/user/password")
    Observable<Response<Object>> changeUserPassword(@Body Password password);

    @PATCH("m/user")
    Observable<Response<Object>> updateUser(@Body UserApp user);

    @GET("m/user/pos")
    Observable<Response<UserApp>> fetchPOSUser(@QueryMap Map<String, String> options);


    @POST("m/user/token")
    Call<LoginResponse> exchangeAuthToken(@Body RefreshToken token);

    //Restaurant Data
    @GET("m/restaurant")
    Observable<Response<Restaurant>> fetchRestaurantDetails(@QueryMap Map<String, String> query);

    @GET("m/restaurant")
    Observable<Response<List<Restaurant>>> fetchAllRestaurants(@QueryMap Map<String, String> query);

    @GET("m/restaurant/table_status")
    Observable<Response<RestaurantTable>> verifyTableVacancy(@QueryMap Map<String, String> query);

    @GET("m/cuisines")
    Observable<ArrayList<Cuisine>> fetchCuisines(@QueryMap Map<String, String> options);

    @GET("m/menu_items")
    Observable<ArrayList<MenuItem>> fetchAllMenuItems(@QueryMap Map<String, String> options);

    @GET("m/menu_items")
    Observable<ArrayList<MenuItem>> fetchMenuItemsByCuisine(@QueryMap Map<String, String> options);

    @GET("m/menu")
    Observable<Response<List<CuisineMenuItems>>> fetchMenuItems(@QueryMap Map<String, String> options);


    @GET("m/app_version")
    Observable<Response<RemoteConfig>> getRemoteConfig();


    //Reviews
    @GET("m/menu_item/reviews")
    Observable<Response<ArrayList<DishReviews>>> fetchDishReviewsById(@QueryMap Map<String, String> options);

    @POST("m/menu_item/reviews")
    Observable<Response<Object>> addADishReview(@Body AddDishReview dishReview);

    @GET("m/restaurant/reviews")
    Observable<Response<ArrayList<RestaurantReviews>>> fetchRestaurantReviewsById(@QueryMap Map<String, String> options);

    @POST("m/restaurant/reviews")
    Observable<Response<Object>> addARestaurantReview(@Body AddRestaurantReview restaurantReview);


    //Running Orders
    @POST("m/t/orders")
    Observable<Response<TOrder>> createNewTempOrder(@Body RestaurantOrder restaurantOrder);

    @POST("m/t/orders/kot")
    Observable<Response<TOrder>> addItemsToTempOrder(@Body RestaurantOrder restaurantOrder);

    @GET("m/t/orders")
    Observable<Response<TOrder>> fetchMyRunningOrder(@QueryMap Map<String, String> options);

    @GET("m/t/orders/merge")
    Observable<Response<MergedOrder>> fetchMergedOrder(@QueryMap Map<String, String> options);

    @POST("m/t/orders/close")
    Observable<Response<FOrder>> closeRunningOrder(@QueryMap Map<String, String> options);

    @POST("m/t/orders/pay")
    Observable<Response<FOrder>> payOrder(@Body PaymentCapture capture);

    @GET("m/f/orders")
    Observable<Response<FOrder>> fetchClosedOrder(@QueryMap Map<String, String> options);

    //My Orders
    @GET("m/orders")
    Observable<Response<List<FOrder>>> fetchMyOrders();

    //Type2 Orders (Non Dine)
    @POST("m/f/orders/checkout")
    Observable<Response<Checkout>> checkoutOrder(@Body NonDineOrder nonDineOrder);

    @POST("m/f/orders/unpaid")
    Observable<Response<FOrder>> createUnpaidNonDineOrder(@Body NonDineOrder nonDineOrder);

    @POST("m/f/orders")
    Observable<Response<FOrder>> createPaidNonDineOrder(@Body NonDineOrder nonDineOrder);

    @POST("m/f/payment")
    Observable<Response<FOrder>> captureNonDineOrderPayment(@Body PaymentCapture paymentCapture);

    @GET("m/f/orders/unpaid")
    Observable<Response<List<FOrder>>> fetchUnpaidOrders();


    // FC
    @GET("fc")
    Observable<Response<FoodCourt>> fetchFCByUUID(@QueryMap Map<String, String> options);

    @GET("fc/all")
    Observable<Response<List<FoodCourt>>> fetchAllFoodCourts();

    // FC
    @GET("fc/restaurant")
    Observable<Response<List<FcRestaurant>>> fetchFCRestaurants(@QueryMap Map<String, String> options);

}
