package de.example.tsa.sandbox02.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by Teguh Santoso on 20.11.2017.
 */
@Entity(tableName = "products", indices=@Index(value="supplier_id"), foreignKeys = @ForeignKey(onDelete = CASCADE, entity = Supplier.class, parentColumns = "uid", childColumns = "supplier_id"))
public class Product implements Serializable{

    private static final long serialVersionUID = 2552005252883370670L;

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "barcode_id")
    private String itemBarcodeId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "image_url")
    private String imageUrl;

    @ColumnInfo(name = "sum_orders")
    private int sumOfOrders;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "supplier_id")
    private int supplierId;

    public Product(String itemBarcodeId, String name, String imageUrl, int sumOfOrders, String description, int supplierId) {
        this.itemBarcodeId = itemBarcodeId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.sumOfOrders = sumOfOrders;
        this.description = description;
        this.supplierId = supplierId;
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

    public int getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(int supplierId) {
        this.supplierId = supplierId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
