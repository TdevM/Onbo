package app.onbo.api.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import app.onbo.api.models.response.v2.menu.MenuAddOn;
import app.onbo.api.models.response.v2.menu.MenuVOption;

/**
 * Created by Tridev on 20-03-2018.
 */

public class Converters {

    @TypeConverter
    public String fromMenuAddOnList(List<MenuAddOn> menuAddOns){
        if (menuAddOns == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MenuAddOn>>() {
        }.getType();
        String json = gson.toJson(menuAddOns, type);
        return json;
    }


    @TypeConverter
    public List<MenuAddOn> toMenuAddOnList(String string){
        if (string == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MenuAddOn>>() {
        }.getType();
        List<MenuAddOn> addOns = gson.fromJson(string, type);
        return addOns;
    }


    @TypeConverter
    public String fromMenuVariantList(List<MenuVOption> menuVOptions){
        if (menuVOptions == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MenuVOption>>() {
        }.getType();
        String json = gson.toJson(menuVOptions, type);
        return json;
    }


    @TypeConverter
    public List<MenuVOption> toMenuVariantList(String string){
        if (string == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<MenuVOption>>() {
        }.getType();
        List<MenuVOption> menuVOptions = gson.fromJson(string, type);
        return menuVOptions;
    }
}
