package de.example.tsa.sandbox02;

import android.Manifest;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import de.example.tsa.sandbox02.activities.LoginActivity;
import de.example.tsa.sandbox02.adapters.ProductsItemAdapter;
import de.example.tsa.sandbox02.db.AppDatabase;
import de.example.tsa.sandbox02.entities.Product;
import de.example.tsa.sandbox02.entities.Supplier;
import de.example.tsa.sandbox02.fragments.DashboardFragment;
import de.example.tsa.sandbox02.fragments.HomeFragment;
import de.example.tsa.sandbox02.fragments.NotificationFragment;

public class MainActivity extends AppCompatActivity implements ProductsItemAdapter.OnAdapterInteractionListener, HomeFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener, NotificationFragment.OnFragmentInteractionListener {
    private static final String         LOGGER = "LOGGER";
    private static final String         INTENT_NAME_PRODUCT = "selectedProduct";
    private static final int            REQUEST_PERMISSIONS_REQUEST_CODE = 34;
    private long                        UPDATE_INTERVAL = 6 * 1000;
    private long                        FASTEST_INTERVAL = 2 * 1000;
    private FusedLocationProviderClient mFusedLocationClient;
    private BottomNavigationView        bottomNavigation;
    private AppDatabase                 appDatabase;
    protected Location                  mLastLocation;
    private LocationRequest             mLocationRequest;

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, HomeFragment.newInstance("GET_ALL_PRODUCTS"), "HOME");
                    transaction.commit();
                    return true;
                case R.id.navigation_dashboard:
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, DashboardFragment.newInstance(mLastLocation), "DASHBOARD");
                    transaction.commit();
                    return true;
                case R.id.navigation_notifications:
                    transaction = getFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, NotificationFragment.newInstance("",""), "NOTIFICATION");
                    transaction.commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set reference room database for activity.
        this.appDatabase = AppDatabase.getAppDatabase(getApplicationContext());

        // Set reference for location service.
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Prepare the navigation element for fragments.
        this.bottomNavigation = findViewById(R.id.navigation);
        this.bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        // Set first menu as default navigation item.
        this.bottomNavigation.setSelectedItemId(R.id.navigation_home);

        // Initialize android room database.
        initDB();

    }

    /**
     * Provides a simple way of getting a device's location and is well suited for
     * applications that do not require a fine-grained location and that do not need location
     * updates. Gets the best and most recent location currently available, which may be null
     * in rare cases when a location is not available.
     * <p>
     * Note: this method should be called after location permission has been granted.
     */
    @SuppressWarnings("MissingPermission")
    private void getLastLocation() {
        mFusedLocationClient.getLastLocation()
                .addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful() && task.getResult() != null) {
                            mLastLocation = task.getResult();
                        } else {
                            showSnackbar("No location detected.");
                        }
                    }
                });
    }

    @SuppressWarnings("MissingPermission")
    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);
        //noinspection MissingPermission
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        onLocationChanged(locationResult.getLastLocation());
                    }
                }, Looper.myLooper());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!checkPermissions()) {
            requestPermissions();
        } else {
            getLastLocation();

            startLocationUpdates();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // If fragment home is visible, refresh the list of products.
        HomeFragment myFragment = (HomeFragment) getFragmentManager().findFragmentByTag("HOME");
        if (myFragment != null && myFragment.isVisible()) {
            myFragment.getAllProductsItems();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Destroy the singleton instance room database.
        AppDatabase.destroyInstance();

    }

    @Override
    public void onEmployeeSelected() {
        // TODO
        // Not yet implemented.
    }

    @Override
    public void onDepartmentSelected() {
        // TODO
        // Not yet implemented
    }

    @Override
    public void onFragmentInteraction() {
        // TODO
        // Not yet implemented
    }

    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }

        // Report to the UI that the location was updated
        mLastLocation = location;
        String msg = "Updated Location: " + Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
        DashboardFragment.newInstance(location);

        // Validate the fragment and show the last data position.
        DashboardFragment myFragment = (DashboardFragment) getFragmentManager().findFragmentByTag("DASHBOARD");
        if (myFragment != null && myFragment.isVisible()) {
            myFragment.updateLocation(location);
        }
    }

    /**
     * Shows a {@link Snackbar} using {@code text}.
     *
     * @param text The Snackbar text.
     */
    private void showSnackbar(final String text) {
        View container = findViewById(R.id.container);
        if (container != null) {
            Snackbar.make(container, text, Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    private void startLocationPermissionRequest() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    private void requestPermissions() {
        boolean shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION);

        // Provide an additional rationale to the user. This would happen if the user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            // TODO
            // Show snack bar.
        } else {
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the user denied the permission
            // previously and checked "Never ask again".
            startLocationPermissionRequest();
        }
    }

    private void initDB() {
        AsyncInitDatabase task = new AsyncInitDatabase();
        task.execute();
    }

    @Override
    public void onOrderItemClicked(Product product) {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(INTENT_NAME_PRODUCT, product);
        startActivity(intent);
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }

    private class AsyncInitDatabase extends AsyncTask<Void, Void, Void> {
        private void populateDatabase() {
            if(appDatabase.supplierDao().getAll().isEmpty()){
                appDatabase.supplierDao().insertSupplier(new Supplier("Lenovo DE"));
                appDatabase.supplierDao().insertSupplier(new Supplier("Toshiba Europe "));
                appDatabase.supplierDao().insertSupplier(new Supplier("ACER Germany"));
                appDatabase.supplierDao().insertSupplier(new Supplier("Verbatim DE"));
                appDatabase.supplierDao().insertSupplier(new Supplier("BELKIN California"));
            }
            if(appDatabase.productDao().getAll().isEmpty()){
                appDatabase.productDao().insertProduct(new Product("23009871120", "Netbook Thinkpad10", "N/A", 5, "N/A", 1));
                appDatabase.productDao().insertProduct(new Product("23009871121", "Netbook Toshiba Sattelite Pro", "N/A", 1, "N/A", 2));
                appDatabase.productDao().insertProduct(new Product("23009871122", "USB Verbatim 32GB", "N/A", 12, "N/A", 4));
                appDatabase.productDao().insertProduct(new Product("23009871123", "WLAN Router BELKIN", "N/A", 10, "N/A", 5));
                appDatabase.productDao().insertProduct(new Product("23009871124", "Notebook T440s", "N/A", 2, "N/A", 1));
                appDatabase.productDao().insertProduct(new Product("23009871125", "Notebook Thinkpad YOGA", "N/A", 6, "N/A", 1));
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            populateDatabase();
            return null;
        }
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                // TODO
                // Send notification to user.
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted.
                getLastLocation();
            } else {
                // TODO
                // Permission was denied.
                // Send notification to user that the App is not able to find current location.
            }
        }
    }

}
