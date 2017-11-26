package de.example.tsa.sandbox02;

import android.content.Context;

/**
 * Singleton class for storing class member and also utilities method.
 *
 */
public class AppController {
    private Context cTxt;

    /**
     * Initialize the self singleton class object.
     */
    private static AppController instance;

    public static AppController getInstance() {
        if (instance == null) {
            instance = new AppController();
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
