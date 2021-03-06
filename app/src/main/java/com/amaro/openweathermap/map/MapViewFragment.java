package com.amaro.openweathermap.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amaro.openweathermap.R;
import com.amaro.openweathermap.util.OwmApplication;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by amaro on 16/10/16.
 */

public class MapViewFragment extends Fragment implements GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks {

    private MapView mMapView;
    private GoogleMap googleMap;

    private Location mLastLocation;
    private Double lat;
    private Double lng;


    GoogleApiClient mGoogleApiClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_layout, container, false);
        setHasOptionsMenu(true);



        mMapView = (MapView) rootView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);


        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    //.addOnConnectionFailedListener(getContext())
                    .addApi(LocationServices.API)
                    .build();
        }


        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                if (ActivityCompat.checkSelfPermission(MapViewFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapViewFragment.this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }

                googleMap.setMyLocationEnabled(true);
               // googleMap.getUiSettings().setZoomControlsEnabled(true);
                googleMap.setOnMarkerClickListener(MapViewFragment.this);
                googleMap.setOnMapClickListener(MapViewFragment.this);
                //googleMap.setOnMyLocationChangeListener(MapViewFragment.this);


            }
        });

        lat = 0.0;
        lng = 0.0;


        return rootView;
    }

    @Override
    public void onStart(){
        mGoogleApiClient.connect();
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

    }

    @Override
    public void onPause() {
        mGoogleApiClient.disconnect();
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        if(ActivityCompat.checkSelfPermission(MapViewFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapViewFragment.this.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if(lat.equals(0.0) || lng.equals(0.0)) {
                if (mLastLocation != null) {

                    LatLng i = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());

                    CameraPosition cameraPosition = new CameraPosition.Builder().target(i).zoom(7).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }else{

                LatLng i = new LatLng(lat,lng);
                CameraPosition cameraPosition = new CameraPosition.Builder().target(i).zoom(7).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        //Custom Menu
        inflater.inflate(R.menu.map_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_search:

                if(OwmApplication.checkInternetConnection()) {
                    if (lat.equals(0.0) || lng.equals(0.0)) {

                        Toast.makeText(getActivity(), this.getString(R.string.choose_a_place), Toast.LENGTH_LONG).show();

                    } else {
                        //Log.d("OPW","CLICOU: lat : "+ lat+" : lng "+lng);
                        BackgroundTask task = new BackgroundTask(getActivity(), lat + "", lng + "");
                        task.execute();
                    }
                }else{
                    Toast.makeText(getActivity(), this.getString(R.string.enable_internet), Toast.LENGTH_LONG).show();
                }

                break;

        }
        return true;
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {

        //Seleciona as coordenadas de pesquisa
        lat = latLng.latitude;
        lng = latLng.longitude;

        googleMap.clear();
        googleMap.addMarker(new MarkerOptions()
                .position(latLng));

    }
}
