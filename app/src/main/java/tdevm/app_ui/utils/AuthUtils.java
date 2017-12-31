package tdevm.app_ui.utils;

import tdevm.app_ui.api.models.MySharedPreferences;

/**
 * Created by Tridev on 18-10-2017.
 */

public class AuthUtils {
    private static final String AUTH_LOGIN_STATE = "AUTH_LOGIN_STATE ";
    private static final String AUTH_LOGIN_TOKEN = "AUTH_LOGIN_TOKEN";
    private static final String AUTH_LOGIN_PHONE = "AUTH_LOGIN_PHONE";

    private static final String SCANNED_RESTAURANT_UUID = "SCANNED_RESTAURANT_UUID";
    private static final String SCANNED_RESTAURANT_TABLE_SHORT_ID = "SCANNED_RESTAURANT_TABLE_SHORT_ID";
    private static final String RESTAURANT_MODE = "RESTAURANT_MODE";

    private MySharedPreferences sharedPreferences;

    public AuthUtils(MySharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveAuthTransaction(String token, Long phone, Boolean state) {
        sharedPreferences.putDataString(AUTH_LOGIN_TOKEN, token);
        sharedPreferences.putDataBool(AUTH_LOGIN_STATE, state);
        sharedPreferences.putDataLong(AUTH_LOGIN_PHONE, phone);
    }

    public void saveNonDineQRTransaction(String restaurantUUID, String restaurantMode) {
            clearQRTransaction();
            sharedPreferences.putDataString(SCANNED_RESTAURANT_UUID, restaurantUUID);
            sharedPreferences.putDataString(RESTAURANT_MODE,restaurantMode);
    }

    public void saveDineQRTransaction(String restaurantUUID, String tableShortID,String restaurantMode) {
            clearQRTransaction();
            sharedPreferences.putDataString(SCANNED_RESTAURANT_UUID, restaurantUUID);
            sharedPreferences.putDataString(SCANNED_RESTAURANT_TABLE_SHORT_ID, tableShortID);
            sharedPreferences.putDataString(RESTAURANT_MODE,restaurantMode);
    }
    public void clearQRTransaction(){
           sharedPreferences.remove(SCANNED_RESTAURANT_UUID);
           sharedPreferences.remove(RESTAURANT_MODE);
           sharedPreferences.remove(SCANNED_RESTAURANT_TABLE_SHORT_ID);

    }

    public Boolean getAuthLoginState() {
        return sharedPreferences.getDataBool(AUTH_LOGIN_STATE);
    }

    public Long getAuthLoginPhone() {
        return sharedPreferences.getDataLong(AUTH_LOGIN_PHONE);
    }

    public String getAuthLoginToken() {
        return sharedPreferences.getDataString(AUTH_LOGIN_TOKEN);
    }

    public String getScannedRestaurantUuid() {
        return sharedPreferences.getDataString(SCANNED_RESTAURANT_UUID);
    }

    public  String getRestaurantMode() {
        return sharedPreferences.getDataString(RESTAURANT_MODE);
    }

    public String getScannedRestaurantTableShortId() {
        return sharedPreferences.getDataString(SCANNED_RESTAURANT_TABLE_SHORT_ID);
    }
}

