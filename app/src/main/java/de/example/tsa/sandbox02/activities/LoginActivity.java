package de.example.tsa.sandbox02.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import de.example.tsa.sandbox02.R;
import de.example.tsa.sandbox02.entities.Product;

public class LoginActivity extends AppCompatActivity implements LoginPresenterCallback {
    private static final String     INTENT_NAME_PRODUCT = "selectedProduct";
    private TextView                textViewLogin;
    private TextView                textViewSupplierName;
    private EditText                editTextSumOrder;
    private EditText                editTextDescription;
    private TextView                textViewPleaseWait;
    private ProgressBar             progressBar;
    private Product                 selectedProduct;
    private LoginPresenter          presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.selectedProduct = (Product)getIntent().getSerializableExtra(INTENT_NAME_PRODUCT);
        this.textViewLogin = findViewById(R.id.textViewLogin);
        this.textViewSupplierName = findViewById(R.id.textViewSupplierId);
        this.textViewPleaseWait = findViewById(R.id.textViewPleaseWait);
        this.editTextSumOrder = findViewById(R.id.editTextSumOrder);
        this.editTextDescription = findViewById(R.id.editTextDescription);
        this.progressBar = findViewById(R.id.progressBar);

        this.presenter = new LoginPresenterImpl(this, selectedProduct);
        this.presenter.setContext(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.presenter.getProductData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_right_out);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.presenter.onDestroy();
    }

    @Override
    public void showProductName(Product selectedProduct) {
        if(selectedProduct == null){
            this.textViewLogin.setText("Product is not found.");
            return;
        }
        this.textViewLogin.setText("Product Id: " + this.selectedProduct.getUid() + " - " + this.selectedProduct.getName());
        this.textViewSupplierName.setText("Supplier-Name: " + this.selectedProduct.getSupplier().getName());
        this.editTextSumOrder.setText(String.valueOf(this.selectedProduct.getSumOfOrders()));
        this.editTextDescription.setText(this.selectedProduct.getDescription());
    }

    @Override
    public void showProgress() {
        this.progressBar.setVisibility(View.VISIBLE);
        this.textViewPleaseWait.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        this.progressBar.setVisibility(View.VISIBLE);
        this.textViewPleaseWait.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void goBack() {
        finish();
    }

    public void cancelAction(View view){
        this.presenter.cancelEditData();
    }

    public void confirmAction(View view){
        View parent = (View)view.getParent();
        if (parent != null) {
            EditText editTextSumOrder = parent.findViewById(R.id.editTextSumOrder);
            EditText editTextDescription = parent.findViewById(R.id.editTextDescription);
            this.presenter.confirmEditData(Integer.valueOf(editTextSumOrder.getText().toString().trim()), editTextDescription.getText().toString().trim());
        }
    }
}
