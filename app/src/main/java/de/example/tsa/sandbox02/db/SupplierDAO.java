package de.example.tsa.sandbox02.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import de.example.tsa.sandbox02.entities.Supplier;

/**
 * Created by Teguh Santoso on 20.11.2017.
 */
@Dao
public interface SupplierDAO {

    @Query("SELECT * FROM suppliers")
    List<Supplier> getAll();

    @Insert
    long insertSupplier(Supplier supplier);

}
