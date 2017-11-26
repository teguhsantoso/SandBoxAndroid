package de.example.tsa.sandbox02.activities;

import android.app.Activity;
import android.content.Context;

import de.example.tsa.sandbox02.db.RoomDatabaseImpl;
import de.example.tsa.sandbox02.db.RoomInteractor;
import de.example.tsa.sandbox02.entities.Product;

/**
 * Created by Teguh Santoso on 25.11.2017.
 */

public class LoginPresenterImpl implements LoginPresenter, RoomInteractor.OnStoreDataListener {
    private Context                 cTxt;
    private LoginPresenterCallback  presenterCallback;
    private RoomInteractor          roomInteractor;
    private Product                 selectedProduct;

    public LoginPresenterImpl(LoginPresenterCallback presenterCallback, Product selectedProduct) {
        this.presenterCallback = presenterCallback;
        this.selectedProduct = selectedProduct;
        this.roomInteractor = new RoomDatabaseImpl();
    }

    @Override
    public void setContext(Activity currentActivity) {
        this.cTxt = currentActivity;
    }

    @Override
    public void getProductData() {
        if (presenterCallback != null) {
            presenterCallback.showProductName(this.selectedProduct);
        }
    }

    @Override
    public void cancelEditData() {
        if (presenterCallback != null) {
            presenterCallback.goBack();
        }
    }

    @Override
    public void confirmEditData(int sumOrder, String description) {
        if (presenterCallback != null) {
            this.selectedProduct.setSumOfOrders(sumOrder);
            this.selectedProduct.setDescription(description);
            roomInteractor.storeData(cTxt, selectedProduct, this);
            presenterCallback.showProgress();
        }
    }

    @Override
    public void onDestroy() {
        presenterCallback = null;
    }

    @Override
    public void onSuccess(String message) {
        presenterCallback.hideProgress();
        presenterCallback.showMessage(message);
        presenterCallback.goBack();
    }

    @Override
    public void onError(String message) {
        presenterCallback.hideProgress();
        presenterCallback.showMessage(message);
        presenterCallback.goBack();
    }
}
