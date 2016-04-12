package com.example.ravi.tracking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.renderscript.Int2;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        mMap.setMyLocationEnabled(true);
        marklastloc();

    }

    public void marklastloc(){
        Intent i =getIntent();
        String ans = i.getStringExtra("latlong");
        String array[] = ans.split("  ");
        Double lat = Double.valueOf(array[0]);
        Double longi = Double.valueOf(array[1]);

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        LatLng lastloc = new LatLng(lat,longi);
        mMap.addMarker(new MarkerOptions().position(lastloc).title("Last Location"));
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(lastloc)
                .zoom(20)                   // Sets the zoom
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
       // mMap.moveCamera(CameraUpdateFactory.newLatLng(lastloc));

    }
}
