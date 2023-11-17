package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.EditText;
import android.widget.SearchView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
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
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1; // You can use any integer value
    private GoogleMap mMap;
    private SearchView searchedit;
    private Button shareButton;
    private LatLng selectedLocation;
    private ActivityLocationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        searchedit = findViewById(R.id.searchedittext);
        shareButton = findViewById(R.id.BtnLocation);

        //binding = ActivityLocationBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        searchedit.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String location = searchedit.getQuery().toString();
                List<Address> addressList = null;
                if (location != null){
                    Geocoder geocoder = new Geocoder(LocationActivity.this);
                    try {
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address = addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    selectedLocation = latLng;
                    mMap.clear();
                    mMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,20));
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(this);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareSelectedLocation();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(location -> {
                        if (location != null) {
                            LatLng currentLocation = new LatLng(location.getLatitude(), location.getLongitude());
                            googleMap.addMarker(new MarkerOptions()
                                    .position(currentLocation)
                                    .title("Lokasi Saya"));
                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 20));
                        }
                    });
        }

        mMap.setOnMapLongClickListener(this);

        /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
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
        }*/
    }
    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(latLng).title("Lokasi yang Dipilih"));
        selectedLocation = latLng;
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
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                }
            }
        }
    }
}