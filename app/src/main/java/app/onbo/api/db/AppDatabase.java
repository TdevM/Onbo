package app.onbo.api.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import app.onbo.api.cart.CartItem;
import app.onbo.api.cart.CartItemDao;
import app.onbo.api.cart.CartSelection;
import app.onbo.api.cart.CartSelectionDao;

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
