package tdevm.app_ui.api.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import tdevm.app_ui.api.cart.CartItem;
import tdevm.app_ui.api.cart.CartItemDao;
import tdevm.app_ui.api.cart.CartSelection;
import tdevm.app_ui.api.cart.CartSelectionDao;

/**
 * Created by Tridev on 05-01-2018.
 */
@Database(entities = {CartItem.class, CartSelection.class}, version = AppDatabase.VERSION)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase{
    static final int VERSION = 1;
    public abstract CartItemDao cartItemDao();
    public abstract CartSelectionDao cartSelectionDao();
}
