package tdevm.app_ui.api.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import tdevm.app_ui.api.models.response.Dish;
import tdevm.app_ui.api.models.response.DishVariant;

/**
 * Created by Tridev on 20-03-2018.
 */

public class Converters {

    @TypeConverter
    public String fromDishVariantList(ArrayList<DishVariant> dishVariants) {
        if (dishVariants == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<DishVariant>>() {
        }.getType();
        String json = gson.toJson(dishVariants, type);
        return json;
    }

    @TypeConverter
    public ArrayList<DishVariant> toDishVariantList(String string) {
        if (string == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<DishVariant>>() {
        }.getType();
        ArrayList<DishVariant> dishVariants = gson.fromJson(string, type);
        return dishVariants;
    }
}
