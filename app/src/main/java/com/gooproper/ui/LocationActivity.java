package com.gooproper.ui;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.gooproper.R;
import com.gooproper.databinding.ActivityLocationBinding;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1; // You can use any integer value
    private GoogleMap mMap;
    private LatLng selectedLocation;
    private ActivityLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLocationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Button shareButton = findViewById(R.id.BtnLocation);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSelectedLocation();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true); // Enable user's location button
            googleMap.getUiSettings().setMyLocationButtonEnabled(true); // Enable user's location button in UI

            // Get the last known location using FusedLocationProviderClient
            FusedLocationProviderClient fusedLocationClient =  LocationServices.getFusedLocationProviderClient(this);
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            // Get latitude and longitude
                            double latitude = location.getLatitude();
                            double longitude = location.getLongitude();

                            // Create LatLng object
                            LatLng currentLocation = new LatLng(latitude, longitude);

                            // Add a marker at current location
                            mMap.addMarker(new MarkerOptions().position(currentLocation).title("Property"));

                            // Move camera to the current location
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLocation, 15);
                            mMap.moveCamera(cameraUpdate);

                            // Set the selectedLocation to the current location
                            selectedLocation = currentLocation;
                        }
                    });
        }
    }


    private void shareSelectedLocation() {
        if (selectedLocation != null) {

            String latitudeStr = String.valueOf(selectedLocation.latitude);
            String longitudeStr = String.valueOf(selectedLocation.longitude);

            // Create a Geocoder instance
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());

            try {
                List<Address> addresses = geocoder.getFromLocationName(latitudeStr + "," + longitudeStr, 1);
                if (!addresses.isEmpty()) {
                    Address address = addresses.get(0);

                    String addressLine = address.getAddressLine(0);  // Complete address
                    String city = address.getLocality();
                    String state = address.getAdminArea();
                    String country = address.getCountryName();
                    String postalCode = address.getPostalCode();

                    // Create a formatted complete address
                    String completeAddress = addressLine + ", " + city + ", " + state + ", " + country + ", " + postalCode;

                    Intent intent = new Intent();
                    intent.putExtra("latitude", latitudeStr);
                    intent.putExtra("longitude", longitudeStr);
                    intent.putExtra("address", completeAddress);
                    setResult(RESULT_OK, intent);

                    finish();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // ... (Rest of your code)
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                LatLng latLng = place.getLatLng();

                if (mMap != null) {
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15)); // Adjust zoom level as needed
                }
            }
        }
    }
}