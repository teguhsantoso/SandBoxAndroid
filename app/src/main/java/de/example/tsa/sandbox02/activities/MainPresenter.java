package de.example.tsa.sandbox02.activities;

import android.app.Activity;

/**
 * Created by Teguh Santoso on 26.11.2017.
 */

public interface MainPresenter {
    void setContext(Activity currentActivity);
    void onDestroy();
}
