package tdevm.app_ui.modules.entry;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.LocationResult;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import retrofit2.Response;
import tdevm.app_ui.api.APIService;
import tdevm.app_ui.api.models.QRObjectRestaurant;
import tdevm.app_ui.api.models.response.v2.Restaurant;
import tdevm.app_ui.api.models.response.v2.RestaurantTable;
import tdevm.app_ui.base.BasePresenter;
import tdevm.app_ui.utils.PreferenceUtils;
import tdevm.app_ui.utils.CartHelper;

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
        QRObjectRestaurant object = new QRObjectRestaurant();
        try {
            object = gson.fromJson(qrContent, QRObjectRestaurant.class);

        } catch (Exception e) {
            view.showMalformedQRCode();
            e.printStackTrace();
            qrScannerShown = false;
        }

        if (object.getData() != null) {
            if (object.getData().getMode() == 1) {
                verifyRestaurantTableVacant(object);
            } else if ((object.getData().getMode()) == 2) {
                fetchRestaurantDetails(object);
            }
        } else {
            view.showMalformedQRCode();
            qrScannerShown = false;
        }
    }

    public void fetchRestaurantDetails(QRObjectRestaurant qrObjectRestaurant) {
        view.showGettingMenu();
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_uuid", qrObjectRestaurant.getUuid());
        Observable<Response<Restaurant>> observable = apiService.fetchRestaurantDetails(map, "Bearer "+preferenceUtils.getAuthLoginToken());
        subscribe(observable, new Observer<Response<Restaurant>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<Restaurant> restaurantResponse) {
                if (restaurantResponse.isSuccessful()) {
                    if (restaurantResponse.body() != null) {

                        tdevm.app_ui.api.models.response.v2.Location location = restaurantResponse.body().getLocation();
                        if (calculateLocationDistance(location.getLocation_lat(), location.getLocation_long())) {
                            onNonDineQRVerificationSuccess(qrObjectRestaurant, restaurantResponse.body());
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


    public void onDineQRVerificationSuccess(QRObjectRestaurant qrObjectRestaurant, Restaurant restaurant, RestaurantTable table) {
        String tableShortId = restaurant.getRestaurant_uuid() + "_" + table.getTable_number();
        preferenceUtils.saveDineQRTransaction(restaurant.getRestaurant_id(), restaurant.getRestaurant_uuid(), table.getRestaurant_table_id(), tableShortId, MODE_DINE_IN);
        Log.d(TAG, "Saved Restaurant" + preferenceUtils.getScannedRestaurantUuid());
        Log.d(TAG, "Saved Table" + preferenceUtils.getScannedRestaurantTableShortId());
        Log.d(TAG, "Restaurant Mode" + preferenceUtils.getRestaurantMode());
        clearExistingCart();
        view.redirectDineInActivity(restaurant);
    }


    public void onNonDineQRVerificationSuccess(QRObjectRestaurant qrObjectRestaurant, Restaurant restaurant) {
        preferenceUtils.saveNonDineQRTransaction(restaurant.getRestaurant_id(), restaurant.getRestaurant_uuid(), MODE_NON_DINE);
        Log.d(TAG, "Saved Restaurant" + preferenceUtils.getScannedRestaurantUuid());
        Log.d(TAG, "Restaurant Mode" + preferenceUtils.getRestaurantMode());
        Log.d(TAG, "Type 2 valid");
        clearExistingCart();
        view.redirectNonDineActivity(restaurant);
    }

    @Override
    public void verifyRestaurantTableVacant(QRObjectRestaurant qrObjectRestaurant) {
        Map<String, String> getRestData = new HashMap<>();
        getRestData.put("short_id", qrObjectRestaurant.getUuid() + "_" + qrObjectRestaurant.getData().getTable());
        getRestData.put("restaurant_uuid", qrObjectRestaurant.getUuid());
        Observable<Response<RestaurantTable>> observable = apiService.verifyTableVacancy("Bearer "+preferenceUtils.getAuthLoginToken(), getRestData);
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
                        fetchRestaurantDetailsTable(qrObjectRestaurant, restaurantTableResponse.body());
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

    private void fetchRestaurantDetailsTable(QRObjectRestaurant qrObjectRestaurant, RestaurantTable restaurantTable) {
        view.showGettingMenu();
        Map<String, String> map = new HashMap<>();
        map.put("restaurant_uuid", qrObjectRestaurant.getUuid());
        Observable<Response<Restaurant>> observable = apiService.fetchRestaurantDetails(map, "Bearer "+preferenceUtils.getAuthLoginToken());
        subscribe(observable, new Observer<Response<Restaurant>>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(Response<Restaurant> restaurantResponse) {
                if (restaurantResponse.isSuccessful()) {


                    if (restaurantResponse.body() != null) {

                        tdevm.app_ui.api.models.response.v2.Location location = restaurantResponse.body().getLocation();
                        if (calculateLocationDistance(location.getLocation_lat(), location.getLocation_long())) {
                            onDineQRVerificationSuccess(qrObjectRestaurant, restaurantResponse.body(), restaurantTable);
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

    boolean calculateLocationDistance(String lat1, String long1) {
        Double distance = distance(Double.parseDouble(lat1), Double.parseDouble(long1), locationLat, locationLong);
        Log.d(TAG, "calculated distance :" + distance.toString());
        if (distance <= 0.1) {
            return true;
        } else {
            return false;
        }

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


    public void setQrScannerShown(boolean v){
        qrScannerShown = v;
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
