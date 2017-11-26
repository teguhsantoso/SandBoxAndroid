package de.example.tsa.sandbox02.fragments;

import android.app.Fragment;
import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.example.tsa.sandbox02.R;

public class DashboardFragment extends Fragment {

    public interface OnFragmentInteractionListener {
        void onDepartmentSelected();
    }

    public interface OnRecyclerViewInteractionListener {
        void onOrderItemClicked();
    }

    private static final String             LOGGER = "LOGGER";
    private static final String             PARAM_LATITUDE = "paramLatitude";
    private static final String             PARAM_LONGITUDE = "paramLongitude";
    private String                          mParamLatitude;
    private String                          mParamLongitude;
    private OnFragmentInteractionListener   mListener;
    private View                            rootView;
    private TextView                        textViewLatitude;
    private TextView                        textViewLongitude;

    public DashboardFragment() {
        // Required empty public constructor
    }

    public static DashboardFragment newInstance(Location lastLocation) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_LATITUDE, String.valueOf(lastLocation.getLatitude()));
        args.putString(PARAM_LONGITUDE, String.valueOf(lastLocation.getLongitude()));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamLatitude = getArguments().getString(PARAM_LATITUDE);
            mParamLongitude = getArguments().getString(PARAM_LONGITUDE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
        textViewLatitude = rootView.findViewById(R.id.textViewLatitude);
        textViewLongitude = rootView.findViewById(R.id.textViewLongitude);
        textViewLatitude.setText("Latitude: " + mParamLatitude);
        textViewLongitude.setText("Longitude: " + mParamLongitude);
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onDepartmentSelected();
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

    public void updateLocation(Location location){
        textViewLatitude.setText("Latitude: " + location.getLatitude());
        textViewLongitude.setText("Longitude: " + location.getLongitude());
    }
}
