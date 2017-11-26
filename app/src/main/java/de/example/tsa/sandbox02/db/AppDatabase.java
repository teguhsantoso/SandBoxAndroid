package de.example.tsa.sandbox02.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import de.example.tsa.sandbox02.entities.Product;
import de.example.tsa.sandbox02.entities.Supplier;

/**
 * Created by Teguh Santoso on 20.11.2017.
 */
@Database(entities = {Product.class, Supplier.class}, version = 8, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract ProductDAO productDao();

    public abstract SupplierDAO supplierDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app-database").fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}
