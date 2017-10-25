package tdevm.app_ui.utils;

import tdevm.app_ui.api.models.MySharedPreferences;

/**
 * Created by Tridev on 18-10-2017.
 */

public class AuthUtils {
    private static final String AUTH_LOGIN_STATE = "AUTH_LOGIN_STATE ";
    private static final String AUTH_LOGIN_TOKEN = "AUTH_LOGIN_TOKEN";
    private static final String AUTH_LOGIN_PHONE = "AUTH_LOGIN_PHONE";

    private MySharedPreferences sharedPreferences;
    public AuthUtils(MySharedPreferences sharedPreferences) {
        this.sharedPreferences  = sharedPreferences;
    }

    public void saveAuthTransaction(String token, Long phone, Boolean state){
        sharedPreferences.putDataString(AUTH_LOGIN_TOKEN,token);
        sharedPreferences.putDataBool(AUTH_LOGIN_STATE,state);
        sharedPreferences.putDataLong(AUTH_LOGIN_PHONE,phone);
    }

    public Boolean getAuthLoginState(){
        return sharedPreferences.getDataBool(AUTH_LOGIN_STATE);
    }

    public Long getAuthLoginPhone(){
        return sharedPreferences.getDataLong(AUTH_LOGIN_PHONE);
    }

    public String getAuthLoginToken(){
        return sharedPreferences.getDataString(AUTH_LOGIN_TOKEN);
    }
}

