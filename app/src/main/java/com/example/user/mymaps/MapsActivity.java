package com.example.user.mymaps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera

        LatLng Chatuchak = new LatLng(13.835925, 100.563730);
        mMap.addMarker(new MarkerOptions().position(Chatuchak).title("Chatuchak"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Chatuchak, 15));


        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(Chatuchak)
                .title("โปรดระวังจุดจุดอันตราย")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        markerOptions.position(new LatLng(13.818375, 100.575546))
                .title("ปรดระวังจุดจุดอันตราย1");
        mMap.addMarker(markerOptions);
        Marker m1 = mMap.addMarker(markerOptions);
        m1.showInfoWindow();

        markerOptions.position(new LatLng(13.835259, 100.572921))
                .title("ปรดระวังจุดจุดอันตราย2");
        Marker m2 = mMap.addMarker(markerOptions);
        m2.showInfoWindow();

        markerOptions.position(new LatLng(13.838548, 100.557243))
                .title("ปรดระวังจุดจุดอันตราย3");
        Marker m3 = mMap.addMarker(markerOptions);
        m3.showInfoWindow();

        markerOptions.position(new LatLng(13.812768, 100.550180))
                .title("ปรดระวังจุดจุดอันตราย4");
        Marker m4 = mMap.addMarker(markerOptions);
        m4.showInfoWindow();


        Circle circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(13.860361, 100.706523))
                .radius(450)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

        circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(13.818375, 100.575546))
                .radius(450)
                .strokeColor(Color.RED));

        circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(13.835259, 100.572921))
                .radius(450)
                .strokeColor(Color.RED));

        circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(13.838548, 100.557243))
                .radius(450)
                .strokeColor(Color.RED));

        circle = googleMap.addCircle(new CircleOptions()
                .center(new LatLng(13.812768, 100.550180))
                .radius(450)
                .strokeColor(Color.RED)
                .fillColor(Color.BLUE));

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(3000)
                    .setFastestInterval(1000);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, (LocationListener) this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        googleApiClient.disconnect();
    }

    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lag = location.getLongitude();

        LatLng latLng = new LatLng(lat, lag);

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Here");
        marker = mMap.addMarker(markerOptions);
    }
}
