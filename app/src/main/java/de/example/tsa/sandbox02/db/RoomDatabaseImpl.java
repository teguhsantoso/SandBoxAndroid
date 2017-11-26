package de.example.tsa.sandbox02.db;

import android.content.Context;
import android.os.AsyncTask;

import de.example.tsa.sandbox02.entities.Product;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public class RoomDatabaseImpl implements RoomInteractor {
    private AppDatabase                 appDatabase;
    private OnStoreDataListener         mListener;
    private Product                     product;

    @Override
    public void storeData(Context cTxt, Product product, OnStoreDataListener listener) {
        this.appDatabase = AppDatabase.getAppDatabase(cTxt);
        this.mListener = listener;
        this.product = product;
        AsyncStoreProduct taskStoreData = new AsyncStoreProduct();
        taskStoreData.execute();
    }

    private class AsyncStoreProduct extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return appDatabase.productDao().update(product);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if(result == 0){
                mListener.onError("FAIL to update item-Id " + product.getUid());
            }else{
                mListener.onSuccess("Success to update item-Id " + product.getUid());
            }

        }
    }
}
