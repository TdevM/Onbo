package onbo.app.api.models;

import android.content.SharedPreferences;

import javax.inject.Inject;

/**
 * Created by Tridev on 13-10-2017.
 */

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    public void putDataInt(String key, int value) {
        mSharedPreferences.edit().putInt(key,value).apply();
    }

    public void putDataBool(String key, Boolean value){
        mSharedPreferences.edit().putBoolean(key,value).apply();
    }
    public void putDataLong(String key, Long value) {
        mSharedPreferences.edit().putLong(key,value).apply();
    }

    public void putDataString(String key, String value){
        mSharedPreferences.edit().putString(key,value).apply();
    }

    public boolean contains(String key){
       return mSharedPreferences.contains(key);
    }

    public int getDataInt(String key) {
        return mSharedPreferences.getInt(key,0);
    }

    public Boolean getDataBool(String key){
        return mSharedPreferences.getBoolean(key,false);
    }

    public void remove(String key){
        mSharedPreferences.edit().remove(key).apply();
    }
    public Long getDataLong(String key){
        return mSharedPreferences.getLong(key,Long.valueOf("123456789"));
    }
    public String getDataString(String key){
        return mSharedPreferences.getString(key,"");
    }
}