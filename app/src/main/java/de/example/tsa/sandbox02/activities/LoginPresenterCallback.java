package de.example.tsa.sandbox02.activities;

import de.example.tsa.sandbox02.entities.Product;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public interface LoginPresenterCallback {

    void showProductName(Product selectedProduct);

    void showProgress();

    void hideProgress();

    void showMessage(String s);

    void goBack();
}
