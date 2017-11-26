package de.example.tsa.sandbox02.util;

import android.content.Context;

/**
 * Singleton class for storing class member and also utilities method.
 *
 */
public class AppUtil {
    private Context cTxt;

    /**
     * Initialize the self singleton class object.
     */
    private static AppUtil instance;

    public static AppUtil getInstance() {
        if (instance == null) {
            instance = new AppUtil();
        }
        return instance;
    }

    public Context getcTxt() {
        return cTxt;
    }

    public void setcTxt(Context cTxt) {
        this.cTxt = cTxt;
    }

}
