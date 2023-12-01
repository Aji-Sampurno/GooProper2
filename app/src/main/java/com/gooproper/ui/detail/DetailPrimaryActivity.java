package com.gooproper.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gooproper.R;
import com.gooproper.adapter.PJPAdapter;
import com.gooproper.adapter.PrimaryAdapter;
import com.gooproper.adapter.SertifikatAdapter;
import com.gooproper.adapter.TipeAdapter;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.model.TipeModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.NewActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailPrimaryActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog pDialog;
    TextView TVNamaDetailPrimary, TVAlamatDetailPrimary, TVHargaDetailPrimary, TVViewsDetailPrimary, TVLikeDetailPrimary, TVDeskripsiDetailPrimary;
    ImageView IVFlowUp, IVFlowup2, IVWhatsapp, IVWhatsapp2, IVInstagram, IVInstagram2, IVShare, IVAlamat;
    ScrollView scrollView;
    CardView agen;
    RecyclerView RVTipe;
    RecyclerView.Adapter tipeAdapter;
    List<TipeModel> tipeModelList;
    String status, idPrimary, JudulMaps;
    LinearLayout LytEdit, LytTipe;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    ArrayList<String> images = new ArrayList<>();
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_primary);

        pDialog = new ProgressDialog(this);
        status = Preferences.getKeyStatus(this);

        agen = findViewById(R.id.LytKontakDetailPrimary);
        scrollView = findViewById(R.id.SVDetailPrimary);
        viewPager = findViewById(R.id.VPDetailPrimary);
        RVTipe = findViewById(R.id.RVTipePrimary);
        TVNamaDetailPrimary = findViewById(R.id.TVNamaDetailPrimary);
        TVAlamatDetailPrimary = findViewById(R.id.TVAlamatDetailPrimary);
        TVHargaDetailPrimary = findViewById(R.id.TVHargaDetailPrimary);
        TVViewsDetailPrimary = findViewById(R.id.TVViewsDetailPrimary);
        TVLikeDetailPrimary = findViewById(R.id.TVLikeDetailPrimary);
        TVDeskripsiDetailPrimary = findViewById(R.id.TVDeskripsiDetailPrimary);
        IVAlamat = findViewById(R.id.IVAlamatDetailPrimary);
        IVFlowUp = findViewById(R.id.IVFlowUpDetailPrimary);
        IVFlowup2 = findViewById(R.id.IVFlowUp2DetailPrimary);
        IVWhatsapp = findViewById(R.id.IVNoTelpDetailPrimary);
        IVWhatsapp2 = findViewById(R.id.IVNoTelp2DetailPrimary);
        IVInstagram = findViewById(R.id.IVInstagramDetailPrimary);
        IVInstagram2 = findViewById(R.id.IVInstagram2DetailPrimary);
        IVShare = findViewById(R.id.IVShareDetailListing);
        LytTipe = findViewById(R.id.LytTipe);
        mapView = findViewById(R.id.MVDetailPrimary);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        tipeModelList = new ArrayList<>();

        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailPrimaryActivity.this, LinearLayoutManager.VERTICAL, false);
        RVTipe.setLayoutManager(layoutManager);
        tipeAdapter = new TipeAdapter(DetailPrimaryActivity.this, tipeModelList);
        RVTipe.setAdapter(tipeAdapter);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdListingPrimary = data.getStringExtra("IdListingPrimary");
        String intentJudulListingPrimary = data.getStringExtra("JudulListingPrimary");
        String intentHargaListingPrimary = data.getStringExtra("HargaListingPrimary");
        String intentDeskripsiListingPrimary = data.getStringExtra("DeskripsiListingPrimary");
        String intentAlamatListingPrimary = data.getStringExtra("AlamatListingPrimary");
        String intentLatitudeListingPrimary = data.getStringExtra("LatitudeListingPrimary");
        String intentLongitudeListingPrimary = data.getStringExtra("LongitudeListingPrimary");
        String intentLocationListingPrimary = data.getStringExtra("LocationListingPrimary");
        String intentKontakPerson1 = data.getStringExtra("KontakPerson1");
        String intentKontakPerson2 = data.getStringExtra("KontakPerson2");
        String intentImg1 = data.getStringExtra("Img1");
        String intentImg2 = data.getStringExtra("Img2");
        String intentImg3 = data.getStringExtra("Img3");
        String intentImg4 = data.getStringExtra("Img4");
        String intentImg5 = data.getStringExtra("Img5");
        String intentImg6 = data.getStringExtra("Img6");
        String intentImg7 = data.getStringExtra("Img7");
        String intentImg8 = data.getStringExtra("Img8");
        String intentImg9 = data.getStringExtra("Img9");
        String intentImg10 = data.getStringExtra("Img10");

        FormatCurrency currency = new FormatCurrency();

        idPrimary = intentIdListingPrimary;

        LoadTipe();

        if (intentJudulListingPrimary.isEmpty()) {
            TVNamaDetailPrimary.setText("-");
            JudulMaps = "-";
        } else {
            TVNamaDetailPrimary.setText(intentJudulListingPrimary);
            JudulMaps = intentJudulListingPrimary;
        }
        if (intentHargaListingPrimary.isEmpty()) {
            TVHargaDetailPrimary.setText("Rp. -");
        } else {
            TVHargaDetailPrimary.setText(currency.formatRupiah(intentHargaListingPrimary));
        }
        if (intentAlamatListingPrimary.isEmpty()) {
            TVAlamatDetailPrimary.setText("-");
        } else {
            TVAlamatDetailPrimary.setText(intentAlamatListingPrimary);
        }
        if (intentDeskripsiListingPrimary.isEmpty()) {
            TVDeskripsiDetailPrimary.setText("-");
        } else {
            TVDeskripsiDetailPrimary.setText(intentDeskripsiListingPrimary);
        }
        if (intentLatitudeListingPrimary.equals("0") || intentLongitudeListingPrimary.equals("0")){
            lat = Double.parseDouble("0");
            lng = Double.parseDouble("0");
            mapView.setVisibility(View.GONE);
        } else {
            lat = Double.parseDouble(intentLatitudeListingPrimary);
            lng = Double.parseDouble(intentLongitudeListingPrimary);
        }

        if (intentImg1.equals("0")) {
        } else {
            images.add(intentImg1);
        }
        if (intentImg2.equals("0")) {
        } else {
            images.add(intentImg2);
        }
        if (intentImg3.equals("0")) {
        } else {
            images.add(intentImg3);
        }
        if (intentImg4.equals("0")) {
        } else {
            images.add(intentImg4);
        }
        if (intentImg5.equals("0")) {
        } else {
            images.add(intentImg5);
        }
        if (intentImg6.equals("0")) {
        } else {
            images.add(intentImg6);
        }
        if (intentImg7.equals("0")) {
        } else {
            images.add(intentImg7);
        }
        if (intentImg8.equals("0")) {
        } else {
            images.add(intentImg8);
        }
        if (intentImg9.equals("0")) {
        } else {
            images.add(intentImg9);
        }
        if (intentImg10.equals("0")) {
        } else {
            images.add(intentImg10);
        }

        adapter = new ViewPagerAdapter(this, images);
        viewPager.setPadding(0, 0, 0, 0);
        viewPager.setAdapter(adapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void LoadTipe() {
        pDialog.setMessage("Memuat Data...");
        pDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_TIPE_PRIMARY + Integer.valueOf(idPrimary),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        tipeModelList.clear();
                        for(int i = 0 ; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                TipeModel md = new TipeModel();
                                md.setIdTipePrimary(data.getString("IdTipePrimary"));
                                md.setIdListingPrimary(data.getString("IdListingPrimary"));
                                md.setNamaTipe(data.getString("NamaTipe"));
                                md.setDeskripsiTipe(data.getString("DeskripsiTipe"));
                                md.setHargaTipe(data.getString("HargaTipe"));
                                md.setGambarTipe(data.getString("GambarTipe"));
                                tipeModelList.add(md);
                                pDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                pDialog.dismiss();

                                Dialog customDialog = new Dialog(DetailPrimaryActivity.this);
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
                                        LoadTipe();
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

                        tipeAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(DetailPrimaryActivity.this);
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
                                LoadTipe();
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
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if (location != null) {
                    LatLng currentLocation = new LatLng(lat, lng);

                    BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.markerlocation);

                    int width = 50;
                    int height = 70;
                    Bitmap smallMarker = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.markerlocation)).getBitmap(), width, height, false);
                    BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(currentLocation)
                            .title(JudulMaps)
                            .icon(smallMarkerIcon);

                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                }
            });
        }
    }
}