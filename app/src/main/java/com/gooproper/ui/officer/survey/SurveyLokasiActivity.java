package com.gooproper.ui.officer.survey;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.gooproper.R;
import com.gooproper.adapter.listing.ListingPendingAdapter;
import com.gooproper.adapter.listing.PraListingTerdekatAdapter;
import com.gooproper.adapter.officer.ReportKinerjaAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.model.PraListingTerdekatModel;
import com.gooproper.model.ReportKinerjaModel;
import com.gooproper.ui.listing.ListingPendingActivity;
import com.gooproper.ui.listing.ListingkuActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SurveyLokasiActivity extends AppCompatActivity {

    ProgressDialog PDialog;
    Button BtnLokasiLain;
    SwipeRefreshLayout SRSurvey;
    RecyclerView RVSurvey;
    PraListingTerdekatAdapter adapter;
    List<PraListingTerdekatModel> nearestLocations = new ArrayList<>();
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_REQUEST_CODE = 1;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_lokasi);

        PDialog = new ProgressDialog(SurveyLokasiActivity.this);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        requestQueue = Volley.newRequestQueue(this);

        BtnLokasiLain = findViewById(R.id.BtnLokasiLain);

        SRSurvey = findViewById(R.id.SRSurveyLokasi);

        RVSurvey = findViewById(R.id.RVSurveyLokasi);

        RVSurvey.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PraListingTerdekatAdapter(this, nearestLocations);
        RVSurvey.setAdapter(adapter);

        BtnLokasiLain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadListing();
            }
        });

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double currentLatitude = location.getLatitude();
                            double currentLongitude = location.getLongitude();
                            fetchLocationsFromAPI(currentLatitude, currentLongitude);
                        }
                    }
                });

        SRSurvey.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (ActivityCompat.checkSelfPermission(SurveyLokasiActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(SurveyLokasiActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SurveyLokasiActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                    return;
                }

                fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(SurveyLokasiActivity.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location != null) {
                                    double currentLatitude = location.getLatitude();
                                    double currentLongitude = location.getLongitude();
                                    fetchLocationsFromAPI(currentLatitude, currentLongitude);
                                }
                            }
                        });
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void fetchLocationsFromAPI(double currentLatitude, double currentLongitude) {
        PDialog.setMessage("Memuat Listingan...");
        PDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRALISTING_TERDEKAT, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDialog.cancel();
                        SRSurvey.setRefreshing(false);
                        nearestLocations.clear();
                        List<PraListingTerdekatModel> locationList = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject locationObject = response.getJSONObject(i);
                                double latitude = locationObject.getDouble("Latitude");
                                double longitude = locationObject.getDouble("Longitude");
                                String imageUrl = locationObject.getString("Img1");
                                String idpralisting = locationObject.getString("IdPraListing");
                                String namalisting = locationObject.getString("NamaListing");
                                String alamatlisting = locationObject.getString("Alamat");
                                locationList.add(new PraListingTerdekatModel(latitude, longitude, idpralisting, namalisting, alamatlisting, imageUrl));
                            }
                            findNearestLocation(currentLatitude, currentLongitude, locationList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    private void findNearestLocation(double currentLatitude, double currentLongitude, List<PraListingTerdekatModel> locationList) {
        List<PraListingTerdekatModel> nearbyLocations = new ArrayList<>();

//        for (PraListingTerdekatModel location : locationList) {
//            double distance = calculateDistance(currentLatitude, currentLongitude, location.getLatitude(), location.getLongitude());
//            if (distance <= distanceLimit) {
//                nearbyLocations.add(location);
//            }
//        }

        for (PraListingTerdekatModel location : locationList) {
            double distance = calculateDistance(currentLatitude, currentLongitude, location.getLatitude(), location.getLongitude());
            nearbyLocations.add(location);
        }

        nearestLocations.clear();
        nearestLocations.addAll(nearbyLocations);
        adapter.notifyDataSetChanged();

        if (nearbyLocations.isEmpty()) {
            BtnLokasiLain.setVisibility(View.VISIBLE);
        } else {
            BtnLokasiLain.setVisibility(View.GONE);
        }
    }

    public double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return distance;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recreate();
            }
        }
    }

    private void LoadListing() {
        PDialog.setMessage("Memuat Data...");
        PDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRALISTING_TERDEKAT,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDialog.cancel();
                        SRSurvey.setRefreshing(false);
                        nearestLocations.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                PraListingTerdekatModel md = new PraListingTerdekatModel();
                                md.setIdPraListing(data.getString("IdPraListing"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setLatitude(data.getDouble("Latitude"));
                                md.setLongitude(data.getDouble("Longitude"));
                                md.setWilayah(data.getString("Wilayah"));
                                md.setImg1(data.getString("Img1"));
                                nearestLocations.add(md);
                                PDialog.cancel();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDialog.cancel();
                                SRSurvey.setRefreshing(false);

                                Dialog customDialog = new Dialog(SurveyLokasiActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.alert_eror);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                Button ok = customDialog.findViewById(R.id.BTNOkEror);
                                Button batal = customDialog.findViewById(R.id.BTNCloseEror);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LoadListing();
                                    }
                                });

                                batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        customDialog.dismiss();
                                    }
                                });

                                customDialog.show();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PDialog.cancel();
                        SRSurvey.setRefreshing(false);
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(SurveyLokasiActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.alert_eror);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BTNOkEror);
                        Button batal = customDialog.findViewById(R.id.BTNCloseEror);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LoadListing();
                            }
                        });

                        batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });

                        customDialog.show();
                    }
                });

        queue.add(reqData);
    }
}