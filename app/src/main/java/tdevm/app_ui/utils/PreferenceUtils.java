package tdevm.app_ui.utils;

import tdevm.app_ui.api.models.MySharedPreferences;

/**
 * Created by Tridev on 18-10-2017.
 */

public class PreferenceUtils {
    private static final String AUTH_LOGIN_STATE = "AUTH_LOGIN_STATE ";
    private static final String AUTH_LOGIN_TOKEN = "AUTH_LOGIN_TOKEN";
    private static final String AUTH_LOGIN_PHONE = "AUTH_LOGIN_PHONE";
    private static final String INTRO_SCREEN_DISPLAYED = "INTRO_SCREEN_DISPLAYED";

    private static final String FETCHED_RESTAURANT_UUID = "FETCHED_RESTAURANT_UUID";
    private static final String FETCHED_RESTAURANT_ID = "FETCHED_RESTAURANT_ID";
    private static final String FETCHED_RESTAURANT_TABLE_NO = "FETCHED_RESTAURANT_TABLE_NO";
    private static final String RESTAURANT_MODE = "RESTAURANT_MODE";
    private static final String FETCHED_RESTAURANT_TABLE_ID = "FETCHED_RESTAURANT_TABLE_ID";

    private MySharedPreferences sharedPreferences;

    public PreferenceUtils(MySharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void saveAuthTransaction(String token, Long phone, Boolean state) {
        sharedPreferences.putDataString(AUTH_LOGIN_TOKEN, token);
        sharedPreferences.putDataBool(AUTH_LOGIN_STATE, state);
        sharedPreferences.putDataLong(AUTH_LOGIN_PHONE, phone);
    }

    public void saveNonDineQRTransaction(String restaurantID, String uuid, String restaurantMode) {
        clearQRTransaction();
        sharedPreferences.putDataString(FETCHED_RESTAURANT_UUID, uuid);
        sharedPreferences.putDataString(RESTAURANT_MODE, restaurantMode);
        sharedPreferences.putDataString(FETCHED_RESTAURANT_ID, restaurantID);
        sharedPreferences.putDataString(FETCHED_RESTAURANT_TABLE_NO, "0");
    }


    public void saveDineQRTransaction(String restaurantID, String uuid, String tableId, String tableShortID, String restaurantMode) {
        clearQRTransaction();
        sharedPreferences.putDataString(FETCHED_RESTAURANT_UUID, uuid);
        sharedPreferences.putDataString(FETCHED_RESTAURANT_ID, restaurantID);
        sharedPreferences.putDataString(FETCHED_RESTAURANT_TABLE_ID, tableId);
        sharedPreferences.putDataString(FETCHED_RESTAURANT_TABLE_NO, tableShortID);
        sharedPreferences.putDataString(RESTAURANT_MODE, restaurantMode);
    }

    public void clearQRTransaction() {
        sharedPreferences.remove(FETCHED_RESTAURANT_UUID);
        sharedPreferences.remove(FETCHED_RESTAURANT_ID);
        sharedPreferences.remove(FETCHED_RESTAURANT_TABLE_NO);
        sharedPreferences.remove(FETCHED_RESTAURANT_TABLE_ID);
        sharedPreferences.remove(RESTAURANT_MODE);

    }

    public void clearAuth() {
        sharedPreferences.remove(AUTH_LOGIN_TOKEN);
        sharedPreferences.remove(AUTH_LOGIN_STATE);
        sharedPreferences.remove(AUTH_LOGIN_PHONE);
    }

    public Boolean getAuthLoginState() {
        return sharedPreferences.getDataBool(AUTH_LOGIN_STATE);
    }

    public Long getAuthLoginPhone() {
        return sharedPreferences.getDataLong(AUTH_LOGIN_PHONE);
    }

    public Boolean getIntroScreenDisplayed() {
        return sharedPreferences.getDataBool(INTRO_SCREEN_DISPLAYED);
    }

    public void setIntroScreenDisplayed(Boolean b) {
        sharedPreferences.putDataBool(INTRO_SCREEN_DISPLAYED, b);
    }

    public void removeIntroScreenDisplayed() {
        sharedPreferences.remove(INTRO_SCREEN_DISPLAYED);
    }

    public String getAuthLoginToken() {
        return sharedPreferences.getDataString(AUTH_LOGIN_TOKEN);
    }

    public String getScannedRestaurantUuid() {
        return sharedPreferences.getDataString(FETCHED_RESTAURANT_UUID);
    }

    public String getScannedRestaurantId() {
        return sharedPreferences.getDataString(FETCHED_RESTAURANT_ID);
    }

    public String getRestaurantMode() {
        return sharedPreferences.getDataString(RESTAURANT_MODE);
    }

    public String getScannedRestaurantTableShortId() {
        return sharedPreferences.getDataString(FETCHED_RESTAURANT_TABLE_NO);
    }

    public String getFetchedRestaurantTableId() {
        return sharedPreferences.getDataString(FETCHED_RESTAURANT_TABLE_ID);
    }
}

