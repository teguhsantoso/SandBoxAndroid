package de.example.tsa.sandbox02.activities;

import android.app.Activity;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public interface LoginPresenter {
    void setContext(Activity activity);
    void getProductData();
    void cancelEditData();
    void confirmEditData(int sumOrder, String description);
    void onDestroy();
}
