package de.example.tsa.sandbox02.activities;

import android.app.Activity;
import android.content.Context;

/**
 * Created by Teguh Santoso on 26.11.2017.
 */

public class MainPresenterImpl implements MainPresenter {
    private Context cTxt;
    private MainPresenterCallback presenterCallback;

    public MainPresenterImpl(MainPresenterCallback presenterCallback) {
        this.presenterCallback = presenterCallback;
    }

    @Override
    public void setContext(Activity currentActivity) {
        this.cTxt = currentActivity;
    }

    @Override
    public void onDestroy() {
        presenterCallback = null;
    }
}
