package de.example.tsa.sandbox02.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

/**
 * Created by Teguh Santoso on 20.11.2017.
 */
@Entity(tableName = "products")
public class Product implements Serializable{

    private static final long serialVersionUID = 2552005252883370670L;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_uid")
    private int uid;

    @ColumnInfo(name = "barcode_id")
    private String itemBarcodeId;

    @ColumnInfo(name = "product_name")
    private String name;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "sum_orders")
    private int sumOfOrders;

    @ColumnInfo(name = "description")
    private String description;

    @Embedded
    Supplier supplier;

    public Product(String itemBarcodeId, String name, String imageUrl, int sumOfOrders, String description, Supplier supplier) {
        this.itemBarcodeId = itemBarcodeId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.sumOfOrders = sumOfOrders;
        this.description = description;
        this.supplier = supplier;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getSumOfOrders() {
        return sumOfOrders;
    }

    public void setSumOfOrders(int sumOfOrders) {
        this.sumOfOrders = sumOfOrders;
    }

    public String getItemBarcodeId() {
        return itemBarcodeId;
    }

    public void setItemBarcodeId(String itemBarcodeId) {
        this.itemBarcodeId = itemBarcodeId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }
}
