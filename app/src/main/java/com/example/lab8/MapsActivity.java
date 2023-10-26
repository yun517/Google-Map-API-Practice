package com.example.lab8;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.lab8.databinding.ActivityMapsBinding;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //台中火車站
        LatLng taichungtrain = new LatLng(24.13749458526762, 120.68696811559425);
        mMap.addMarker(new MarkerOptions().position(taichungtrain).title("台中火車站"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(taichungtrain));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(16)); //放大地圖 16倍

        mMap.setMyLocationEnabled(true); //啟用「我的位置」功能
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true); //右下的放大縮小
        mMap.getUiSettings().setCompassEnabled(true); //指南針
        mMap.getUiSettings().setMapToolbarEnabled(true); //右下的導覽及開啟google map功能

        //變更目前位置的定位圖標
        // 初始化LocationManager並請求位置更新
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // 定位到目前所在位置
                LatLng myLocation = new LatLng(location.getLatitude(), location.getLongitude());

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(myLocation);
                markerOptions.title(" ");


                Marker originMarker = mMap.addMarker(markerOptions);

                int height = 142;
                int width = 80;
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.anpu);
                Bitmap smallMarker = Bitmap.createScaledBitmap(bitmap, width, height, false);
                BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);
                originMarker.setIcon(smallMarkerIcon);
            }
        };

        // 請求位置更新
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        //定位兩間逢甲附近的飲料店
        LatLng lan = new LatLng(24.180877756205593, 120.64625787327407);
        mMap.addMarker(new MarkerOptions().position(lan));

        LatLng aniceholiday = new LatLng(24.174743856960003, 120.64455843527767);
        mMap.addMarker(new MarkerOptions().position(aniceholiday));
    }
}