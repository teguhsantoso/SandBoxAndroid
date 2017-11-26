package de.example.tsa.sandbox02.db;

import android.content.Context;

import de.example.tsa.sandbox02.entities.Product;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public interface RoomInteractor {

    interface OnStoreDataListener {
        void onSuccess(String message);
        void onError(String message);
    }

    void storeData(Context cTxt, Product product, OnStoreDataListener listener);

}
