package app.onbo.modules.entry;

import android.content.Intent;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import app.onbo.api.models.QRDataRestaurant;
import app.onbo.api.models.RemoteConfig;
import app.onbo.api.models.response.v2.FoodCourt;
import app.onbo.modules.fc.FCActivity;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import app.onbo.api.APIService;
import app.onbo.api.models.QRObject;
import app.onbo.api.models.response.v2.Restaurant;
import app.onbo.api.models.response.v2.RestaurantTable;
import app.onbo.base.BasePresenter;
import app.onbo.utils.PreferenceUtils;
import app.onbo.utils.CartHelper;

/**
 * Created by Tridev on 14-03-2018.
 */

public class MenuEntryPresenter extends BasePresenter implements MenuEntryPresenterContract.RestaurantMenuEntryPresenter {

    public static final String TAG = MenuEntryPresenter.class.getSimpleName();

    private static final String MODE_DINE_IN = "MODE_DINE_IN";
    private static final String MODE_NON_DINE = "MODE_NON_DINE";
    private APIService apiService;
    private PreferenceUtils preferenceUtils;
    private CartHelper cartHelper;
    private CompositeDisposable compositeDisposable;
    private MenuEntryViewContract.RestaurantMenuEntryView view;

    private Double locationLat;
    private Double locationLong;

    static Boolean qrScannerShown = false;

    @Inject
    public MenuEntryPresenter(APIService apiService, PreferenceUtils preferenceUtils, CartHelper cartHelper) {
        this.apiService = apiService;
        this.preferenceUtils = preferenceUtils;
        this.cartHelper = cartHelper;
        compositeDisposable = new CompositeDisposable();
    }

    public void handleLocationUpdates(LocationResult result) {

        if (result != null) {
            Location mCurrentLocation = result.getLastLocation();
            Log.d(TAG, "Provider: " + mCurrentLocation.getProvider());
            if (mCurrentLocation.getAccuracy() < 50.0) {
                if (!qrScannerShown) {
                    qrScannerShown = true;
                    view.startQRScanner();
                }
                locationLat = mCurrentLocation.getLatitude();
                locationLong = mCurrentLocation.getLongitude();
                // view.stopLocationUpdates();
            }
        }
    }

    @Override
    public void clearExistingCart() {
        if (cartHelper.cartItemsExist()) {
            cartHelper.clearCart();
            Log.d(TAG, "Cart Items existing. Cart Cleared.");
        }
    }

    @Override
    public void handleQRContent(String qrContent) {
        Gson gson = new Gson();
        QRObject object;
        try {
            object = gson.fromJson(qrContent, QRObject.class);
            if (object != null) {
                switch (object.getEntity()) {
                    case 1:
                        Log.d(TAG, "Restaurant Case");
                        if (object.getData() != null) {
                            if (object.getData().get("mode").equals(String.valueOf(1))) {
                                verifyRestaurantTableVacant2(object, object.getData());
                            } else if (object.getData().get("mode").equals(String.valueOf(2))) {
                                fetchRestaurantDetails2(object, object.getData());
                            }
                        } else {
                            throw new Error("Object is null");
                        }
                        break;
                    case 3:
                        Log.d(TAG, "Food Court Case");
                        if (object.getData() != null) {
                            fetchFoodCourt(object.getUuid());
                        } else {
                            throw new Error("Object is null");
                        }
                        break;
                }

            } else {
                throw new Error("Object is null");
            }

        } catch (Exception e) {
            view.showMalformedQRCode();
            e.printStackTrace();
            qrScannerShown = false;
        }

    }


    public void handleQRContent2(String qrContent) {
        Gson gson = new Gson();
        QRObject object;
        try {
            object = gson.fromJson(qrContent, QRObject.class);
            if (object != null) {
                switch (object.getEntity()) {
                    case 1:
                        Log.d(TAG, "Restaurant Case");
                        if (object.getData() != null) {
                            //QRDataRestaurant qrDataRestaurant = (QRDataRestaurant) object.getData();
                            Log.d(TAG, "TypeOf" + object.getData().getClass().getSimpleName());
                            Log.d("Map", object.getData().toString());

                            if (object.getData().get("mode").equals(String.valueOf(1))) {
                                verifyRestaurantTableVacant2(object, object.getData());
                            } else if (object.getData().get("mode").equals(String.valueOf(2))) {
                                fetchRestaurantDetails2(object, object.getData());
                            }
                            Log.d(TAG, String.valueOf(object.getData()));
                        } else {
                            throw new Error("Object is null");
                        }
                        break;
                    case 3:
                        Log.d(TAG, "Food Court Case");
                        if (object.getData() != null) {
                            fetchFoodCourt(object.getUuid());
                        } else {
                            throw new Error("Object is null");
                        }
                        break;
                }

            } else {
                throw new Error("Object is null");
            }

        } catch (Exception e) {
            view.showMalformedQRCode2();
            e.printStackTrace();
            qrScannerShown = false;
        }

    }

    public void fetchRestaurantDetails(QRObject qrObject, QRDataRestaurant qrDataRestaurant) {
        view.showGettingMenu();
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_uuid", qrObject.getUuid());
        Observable<Response<Restaurant>> observable = apiService.fetchRestaurantDetails(map);
        subscribe(observable, new Observer<Response<Restaurant>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<Restaurant> restaurantResponse) {
                if (restaurantResponse.isSuccessful()) {
                    if (restaurantResponse.body() != null) {

                        app.onbo.api.models.response.v2.Location location = restaurantResponse.body().getLocation();
                        if (calculateLocationDistance(location.getLocation_lat(), location.getLocation_long())) {
                            onNonDineQRVerificationSuccess(qrObject, restaurantResponse.body());
                            view.stopLocationUpdates();
                            qrScannerShown = false;
                        } else {
                            view.showWrongLocationError();
                            qrScannerShown = false;
                        }

                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void fetchRestaurantDetails2(QRObject qrObject, Map<String, String> map1) {
        view.showGettingMenu();
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_uuid", qrObject.getUuid());
        Observable<Response<Restaurant>> observable = apiService.fetchRestaurantDetails(map);
        subscribe(observable, new Observer<Response<Restaurant>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<Restaurant> restaurantResponse) {
                if (restaurantResponse.isSuccessful()) {
                    if (restaurantResponse.body() != null) {
                        app.onbo.api.models.response.v2.Location location = restaurantResponse.body().getLocation();
                        onNonDineQRVerificationSuccess(qrObject, restaurantResponse.body());
                        qrScannerShown = false;

                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }


    public void onDineQRVerificationSuccess(QRObject qrObject, Restaurant restaurant, RestaurantTable table) {
        String tableShortId = restaurant.getRestaurant_uuid() + "_" + table.getTable_number();
        preferenceUtils.saveDineQRTransaction(restaurant.getRestaurant_id(), restaurant.getRestaurant_uuid(), table.getRestaurant_table_id(), tableShortId, MODE_DINE_IN);
        Log.d(TAG, "Saved Restaurant" + preferenceUtils.getScannedRestaurantUuid());
        Log.d(TAG, "Saved Table" + preferenceUtils.getScannedRestaurantTableShortId());
        Log.d(TAG, "Restaurant Mode" + preferenceUtils.getRestaurantMode());
        clearExistingCart();
        view.redirectDineInActivity(restaurant);
    }


    public void onNonDineQRVerificationSuccess(QRObject qrObject, Restaurant restaurant) {
        preferenceUtils.saveNonDineQRTransaction(restaurant.getRestaurant_id(), restaurant.getRestaurant_uuid(), MODE_NON_DINE);
        Log.d(TAG, "Saved Restaurant" + preferenceUtils.getScannedRestaurantUuid());
        Log.d(TAG, "Restaurant Mode" + preferenceUtils.getRestaurantMode());
        Log.d(TAG, "Type 2 valid");
        clearExistingCart();
        view.redirectNonDineActivity(restaurant);
    }

    @Override
    public void verifyRestaurantTableVacant(QRObject qrObject, QRDataRestaurant qrDataRestaurant) {
        Map<String, String> getRestData = new HashMap<>();
        getRestData.put("short_id", qrObject.getUuid() + "_" + qrDataRestaurant.getTable());
        getRestData.put("restaurant_uuid", qrObject.getUuid());
        Observable<Response<RestaurantTable>> observable = apiService.verifyTableVacancy(getRestData);
        subscribe(observable, new Observer<Response<RestaurantTable>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Response<RestaurantTable> restaurantTableResponse) {
                if (restaurantTableResponse.code() == 200) {
                    if (restaurantTableResponse.body() != null) {
                        Log.d(TAG, restaurantTableResponse.body().getRestaurant_table_id());
                        fetchRestaurantDetailsTable(qrObject, restaurantTableResponse.body());
                    }
                } else if (restaurantTableResponse.code() == 400) {
                    //Log.d(TAG, restaurantTableResponse.body().toString());
                    view.showTableOccupiedError();
                    qrScannerShown = false;
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void fetchRestaurantDetailsTable(QRObject qrObject, RestaurantTable restaurantTable) {
        view.showGettingMenu();
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_uuid", qrObject.getUuid());
        Observable<Response<Restaurant>> observable = apiService.fetchRestaurantDetails(map);
        subscribe(observable, new Observer<Response<Restaurant>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<Restaurant> restaurantResponse) {
                if (restaurantResponse.isSuccessful()) {


                    if (restaurantResponse.body() != null) {

                        app.onbo.api.models.response.v2.Location location = restaurantResponse.body().getLocation();
                        if (calculateLocationDistance(location.getLocation_lat(), location.getLocation_long())) {
                            onDineQRVerificationSuccess(qrObject, restaurantResponse.body(), restaurantTable);
                            view.stopLocationUpdates();
                            qrScannerShown = false;
                        } else {
                            view.showWrongLocationError();
                            qrScannerShown = false;
                        }


                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }


    public void verifyRestaurantTableVacant2(QRObject qrObject, Map<String, String> map) {
        Map<String, String> getRestData = new HashMap<>();
        getRestData.put("short_id", qrObject.getUuid() + "_" + map.get("table"));
        getRestData.put("restaurant_uuid", qrObject.getUuid());
        Observable<Response<RestaurantTable>> observable = apiService.verifyTableVacancy(getRestData);
        subscribe(observable, new Observer<Response<RestaurantTable>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(@NonNull Response<RestaurantTable> restaurantTableResponse) {
                if (restaurantTableResponse.code() == 200) {
                    if (restaurantTableResponse.body() != null) {
                        Log.d(TAG, restaurantTableResponse.body().getRestaurant_table_id());
                        fetchRestaurantDetailsTable2(qrObject, restaurantTableResponse.body());
                    }
                } else if (restaurantTableResponse.code() == 400) {
                    //Log.d(TAG, restaurantTableResponse.body().toString());
                    view.showTableOccupiedError2();
                    qrScannerShown = false;
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void fetchRestaurantDetailsTable2(QRObject qrObject, RestaurantTable restaurantTable) {
        view.showGettingMenu();
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_uuid", qrObject.getUuid());
        Observable<Response<Restaurant>> observable = apiService.fetchRestaurantDetails(map);
        subscribe(observable, new Observer<Response<Restaurant>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<Restaurant> restaurantResponse) {
                if (restaurantResponse.isSuccessful()) {


                    if (restaurantResponse.body() != null) {

                        app.onbo.api.models.response.v2.Location location = restaurantResponse.body().getLocation();
                        onDineQRVerificationSuccess(qrObject, restaurantResponse.body(), restaurantTable);
                        qrScannerShown = false;
                    }
                }
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    boolean calculateLocationDistance(String lat1, String long1) {
        Double distance = distance(Double.parseDouble(lat1), Double.parseDouble(long1), locationLat, locationLong);
        Log.d(TAG, "calculated distance :" + distance.toString());
        if (distance <= 0.1) {
            return true;
        } else {
            return false;
        }

    }


    public void checkLocationVerifiedAccess() {
        Observable<Response<RemoteConfig>> checkRemoteConfig = apiService.getRemoteConfig();
        subscribe(checkRemoteConfig, new Observer<Response<RemoteConfig>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<RemoteConfig> remoteConfigResponse) {
                view.onLocationVerifiedAccessFetched(remoteConfigResponse.body());
            }

            @Override
            public void onError(Throwable e) {
                view.onLocationVerifiedAccessFailure();
                e.printStackTrace();
            }

            @Override
            public void onComplete() {

            }
        });


    }


    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    public void setQrScannerShown(boolean v) {
        qrScannerShown = v;
    }

    @Override
    public void fetchFoodCourt(String foodCourtUUID) {
        Map<String, String> map = new HashMap<>();
        map.put("fc_uuid", foodCourtUUID);
        Observable<Response<FoodCourt>> observable = apiService.fetchFCByUUID(map);
        subscribe(observable, new Observer<Response<FoodCourt>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                view.showProgressUI();
            }

            @Override
            public void onNext(Response<FoodCourt> foodCourtResponse) {
                if (foodCourtResponse.code() == 200) {
                    view.showFCActivity(foodCourtResponse.body());
                } else {

                }
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {
                view.hideProgressUI();
            }
        });

        Log.d(TAG, "Go fetch the Food Court");
    }

    @Override
    public void attachView(MenuEntryViewContract.RestaurantMenuEntryView view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }
}
