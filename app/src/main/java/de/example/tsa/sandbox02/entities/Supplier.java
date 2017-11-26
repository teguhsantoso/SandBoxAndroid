package de.example.tsa.sandbox02.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Teguh Santoso on 21.11.2017.
 */
@Entity(tableName = "suppliers")
public class Supplier implements Serializable {

    private static final long serialVersionUID = -3984977601419180775L;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    public Supplier(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
