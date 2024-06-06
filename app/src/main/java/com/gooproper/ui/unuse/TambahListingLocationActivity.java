package com.gooproper.ui.unuse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gooproper.R;

import java.io.IOException;
import java.util.List;

public class TambahListingLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    TextView lat,longi;
    String agenId, path;
    Intent intent;
    private double latitude;
    private double longitude;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_listing_location);

//        lat = findViewById(R.id.latitude);
//        longi = findViewById(R.id.longitude);

        Intent intent = getIntent();
        String action = intent.getAction();

        // Memeriksa apakah intent adalah intent lokasi dari WhatsApp
        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {
                // Mendapatkan URI geo
                String geoUri = uri.toString();

                // Proses URI geo untuk mendapatkan latitude dan longitude
                String[] parts = geoUri.split(":|,|\\?|=");
                latitude = Double.parseDouble(parts[1]);
                longitude = Double.parseDouble(parts[2]);

                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);

//                lat.setText(latitude+","+longitude);
                // Gunakan nilai latitude dan longitude sesuai kebutuhan aplikasi Anda
                // Misalnya, tampilkan lokasi di peta atau simpan ke database.
            }
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        if (googleMap != null) {
            // Mendapatkan latitude dan longitude dari intent
            LatLng location = new LatLng(latitude, longitude);

            // Menambahkan marker ke peta
            googleMap.addMarker(new MarkerOptions().position(location).title("Lokasi dari WhatsApp"));

            // Menggerakkan kamera ke lokasi dengan zoom level tertentu
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 15));
        }
    }
}