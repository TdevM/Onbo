package app.onbo.di.modules;

import android.app.Application;
import android.arch.persistence.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import app.onbo.api.db.AppDatabase;
import app.onbo.api.cart.CartItemDao;
import app.onbo.api.cart.CartSelectionDao;

/**
 * Created by Tridev on 05-01-2018.
 */
@Module(includes = AppContextModule.class)
public class RoomModule {

    @Singleton
    @Provides
    AppDatabase appDatabase(Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, "app.db").allowMainThreadQueries().build();
    }

    @Singleton
    @Provides
    CartItemDao cartItemDao(AppDatabase appDatabase) {
        return appDatabase.cartItemDao();
    }

    @Singleton
    @Provides
    CartSelectionDao cartSelectionDao(AppDatabase appDatabase) {
        return appDatabase.cartSelectionDao();
    }

}
