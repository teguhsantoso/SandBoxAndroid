package de.example.tsa.sandbox02.fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import de.example.tsa.sandbox02.R;
import de.example.tsa.sandbox02.adapters.ProductsItemAdapter;
import de.example.tsa.sandbox02.db.AppDatabase;
import de.example.tsa.sandbox02.entities.Product;
import de.example.tsa.sandbox02.entities.Supplier;

public class HomeFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onEmployeeSelected();
    }

    private static final String             LOGGER = "LOGGER";
    private static final String             MODE_OPERATION = "ModeOperation";
    private static final String             PARAM_SELECT_ALL = "All";
    private String                          mParamMode;
    private View                            rootView;
    private Spinner                         spinnerSuplliers;
    private ImageButton                     buttonSearch;
    private RecyclerView                    mRecyclerView;
    private ProductsItemAdapter             mAdapter;
    private OnFragmentInteractionListener   mListener;
    private AppDatabase                     appDatabase;
    private List<Product>                   listProducts;
    private List<Supplier>                  listSuppliers;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(MODE_OPERATION, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamMode = getArguments().getString(MODE_OPERATION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Set reference room database for activity.
        this.appDatabase = AppDatabase.getAppDatabase(getActivity());

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        spinnerSuplliers = rootView.findViewById(R.id.spinnerSuppliers);
        buttonSearch = rootView.findViewById(R.id.imageButtonSearch);
        mRecyclerView = rootView.findViewById(R.id.products);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        if(mParamMode.equals("GET_ALL_PRODUCTS")){
            getAllProductsItems();
        }

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedSupplier = (String) spinnerSuplliers.getSelectedItem();
                if(selectedSupplier.equals("All")){
                    AsyncDBOperation task = new AsyncDBOperation(PARAM_SELECT_ALL);
                    task.execute();
                }else{
                    AsyncDBOperation task = new AsyncDBOperation(selectedSupplier);
                    task.execute();
                }
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onEmployeeSelected();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void getAllProductsItems() {
        AsyncInitDatabase task = new AsyncInitDatabase();
        task.execute();
    }

    private class AsyncDBOperation extends AsyncTask<Void, Void, Void> {
        private String supplierName;
        private int supplierId = -1;

        public AsyncDBOperation(String supplierName) {
            this.supplierName = supplierName;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            if(supplierName.equals(PARAM_SELECT_ALL)){
                listProducts = appDatabase.productDao().getAll();
            }else{
                for (Supplier s : appDatabase.supplierDao().getAll()) {
                    if(s.getName().equals(this.supplierName)){
                        this.supplierId = s.getId();
                        break;
                    }
                }
                listProducts = appDatabase.productDao().findProductsBySupplierId(this.supplierId);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mAdapter = new ProductsItemAdapter(getContext(), listProducts);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

    private class AsyncInitDatabase extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            listProducts = appDatabase.productDao().getAll();
            listSuppliers = appDatabase.supplierDao().getAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void  result) {
            super.onPostExecute(result);

            List<String> listSuppliersName = new ArrayList<String>();
            listSuppliersName.add("All");
            for (Supplier supplier : listSuppliers) {
                listSuppliersName.add(supplier.getName());
            }
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listSuppliersName);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerSuplliers.setAdapter(dataAdapter);

            mAdapter = new ProductsItemAdapter(getContext(), listProducts);
            mRecyclerView.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }

}
