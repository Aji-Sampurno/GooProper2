package com.gooproper.ui.detail;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.bumptech.glide.Glide;
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
import com.gooproper.adapter.SertifikatAdapter;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.model.InfoModel;
import com.gooproper.model.ListingModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.edit.EditInfoActivity;
import com.gooproper.ui.tambah.TambahListingInfoActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog pDialog;
    TextView TVJudul, TVLokasi, TVHarga, TVHargaSewa, TVNamaAgen, TVLuas, TVLTanah, TVLBangunan, TVJenis, TVStatus, TVDeskripsi, TVTglInput, TVNarahubung, TVTelpNarahubung, TVNoSelfie, TVPoin;
    LinearLayout LytNarahubung, LytTelpNarahubung, LytTglInput;
    ImageView IVAddListing, IVWhatsapp, IVInstagram, IVSelfie;
    Button BtnTambah, BtnTambahSpek;
    LinearLayout LytSelfie;
    ScrollView scrollView;
    CardView agen;
    String NamaMaps, StringStatus, StringIdAgen, StrIdAgen, StringJudul, StringLokasi, StringNamaBuyer;
    InfoModel infoModel;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    ArrayList<String> images = new ArrayList<>();
    private String[] dataOptions;
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    private static final int REQUEST_WRITE_STORAGE = 112;
    int Poin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        pDialog = new ProgressDialog(DetailInfoActivity.this);
        viewPager = findViewById(R.id.VPDetailInfo);
        agen = findViewById(R.id.LytAgenDetailInfo);
        scrollView = findViewById(R.id.SVDetailInfo);
        LytSelfie = findViewById(R.id.LytViewSelfieDetailInfo);

        IVSelfie = findViewById(R.id.IVSelfieDetailInfo);
        IVInstagram = findViewById(R.id.IVInstagramAgenDetailInfo);
        IVWhatsapp = findViewById(R.id.IVNoTelpAgenDetailInfo);
        IVAddListing = findViewById(R.id.IVAddAgenDetailInfo);

        BtnTambah = findViewById(R.id.BtnAddListing);
        BtnTambahSpek = findViewById(R.id.BtnAddSpek);

        TVJudul = findViewById(R.id.TVJudulDetailInfo);
        TVLokasi = findViewById(R.id.TVLokasiDetailInfo);
        TVHarga = findViewById(R.id.TVHargaDetailInfo);
        TVHargaSewa = findViewById(R.id.TVHargaSewaDetailInfo);
        TVNamaAgen = findViewById(R.id.TVNamaAgenDetailInfo);
        TVNoSelfie = findViewById(R.id.TVNoSelfieDetailInfo);
        TVLuas = findViewById(R.id.TVLuasProperty);
        TVLTanah = findViewById(R.id.TVLuasTanah);
        TVLBangunan = findViewById(R.id.TVLuasBangunan);
        TVJenis = findViewById(R.id.TVJenisProperty);
        TVStatus = findViewById(R.id.TVStatusProperty);
        TVDeskripsi = findViewById(R.id.TVDeskripsi);
        TVTglInput = findViewById(R.id.TVTglInput);
        TVNarahubung = findViewById(R.id.TVNarahubung);
        TVTelpNarahubung = findViewById(R.id.TVTelpNarahubung);
        TVPoin = findViewById(R.id.TVPoinDetailInfo);

        LytNarahubung = findViewById(R.id.LytNarahubungProperti);
        LytTelpNarahubung = findViewById(R.id.LytTelpNarahubung);
        LytTglInput = findViewById(R.id.LytTglInput);

        mapView = findViewById(R.id.MVDetailInfo);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String IntentIdInfo = data.getStringExtra("IdInfo");
        String IntentIdAgen = data.getStringExtra("IdAgen");
        String IntentJenisProperty = data.getStringExtra("JenisProperty");
        String IntentStatusProperty = data.getStringExtra("StatusProperty");
        String IntentNarahubung = data.getStringExtra("Narahubung");
        String IntentImgSelfie = data.getStringExtra("ImgSelfie");
        String IntentImgProperty = data.getStringExtra("ImgProperty");
        String IntentLokasi = data.getStringExtra("Lokasi");
        String IntentAlamat = data.getStringExtra("Alamat");
        String IntentNoTelp = data.getStringExtra("NoTelp");
        String IntentLatitude = data.getStringExtra("Latitude");
        String IntentLongitude = data.getStringExtra("Longitude");
        String IntentTglInput = data.getStringExtra("TglInput");
        String IntentJamInput = data.getStringExtra("JamInput");
        String IntentIsListing = data.getStringExtra("IsListing");
        String IntentLBangunan = data.getStringExtra("LBangunan");
        String IntentLTanah = data.getStringExtra("LTanah");
        String IntentHarga = data.getStringExtra("Harga");
        String IntentHargaSewa = data.getStringExtra("HargaSewa");
        String IntentKeterangan = data.getStringExtra("Keterangan");
        String IntentIsSpek = data.getStringExtra("IsSpek");

        StringStatus = Preferences.getKeyStatus(this);
        StringIdAgen = Preferences.getKeyIdAgen(this);
        StrIdAgen = IntentIdAgen;
        StringJudul = IntentStatusProperty + " " + IntentJenisProperty;
        StringLokasi = IntentLokasi;

        LoadAgen();

        FormatCurrency currency = new FormatCurrency();

        BtnTambah.setOnClickListener(v -> startActivity(new Intent(DetailInfoActivity.this, TambahListingInfoActivity.class)));
        BtnTambahSpek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailInfoActivity.this, EditInfoActivity.class);
                update.putExtra("IdInfo",IntentIdInfo);
                update.putExtra("Status",IntentStatusProperty);
                startActivity(update);
            }
        });
        IVAddListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailInfoActivity.this, TambahListingInfoActivity.class);
            }
        });

        if (StringStatus.equals("1")) {
            LytSelfie.setVisibility(View.VISIBLE);
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
        } else if (StringStatus.equals("2")) {
            LytSelfie.setVisibility(View.VISIBLE);
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
        } else if (StringStatus.equals("3")) {
            LytSelfie.setVisibility(View.GONE);
            StringNamaBuyer = Preferences.getKeyNama(this);
        } else {
            LytSelfie.setVisibility(View.GONE);
            BtnTambahSpek.setVisibility(View.GONE);
            TVPoin.setVisibility(View.GONE);
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
        }

        if (StringIdAgen.equals(IntentIdAgen) || StringIdAgen == IntentIdAgen) {
//            IVAddListing.setVisibility(View.VISIBLE);
            if (IntentIsSpek.equals("0")) {
                BtnTambahSpek.setVisibility(View.VISIBLE);
                TVPoin.setVisibility(View.VISIBLE);
            } else {
                BtnTambahSpek.setVisibility(View.GONE);
                TVPoin.setVisibility(View.VISIBLE);
            }
        } else {
//            IVAddListing.setVisibility(View.INVISIBLE);
            BtnTambahSpek.setVisibility(View.GONE);
            TVPoin.setVisibility(View.GONE);
        }

        if (update == 1) {
            if (IntentIsSpek.equals("0")){
                Poin = 0;
                TVPoin.setText(String.valueOf(Poin));
            } else if (IntentIsSpek.equals("1")) {
                if (IntentImgSelfie.equals("0") && IntentLatitude.equals("") && IntentLongitude.equals("")) {
                    Poin = 10;
                    TVPoin.setText(String.valueOf(Poin));
                } else {
                    Poin = 10 * 2;
                    TVPoin.setText(String.valueOf(Poin));
                }
            }
            if (IntentStatusProperty.isEmpty() && IntentJenisProperty.isEmpty()) {
                TVJudul.setText("-");
            } else {
                TVJudul.setText(IntentStatusProperty + " " + IntentJenisProperty);
            }
            if (IntentStatusProperty.equals("Jual")) {
                TVHargaSewa.setVisibility(View.GONE);
            } else if (IntentStatusProperty.equals("Sewa")) {
                TVHarga.setVisibility(View.GONE);
            } else {
                TVHarga.setVisibility(View.VISIBLE);
                TVHargaSewa.setVisibility(View.VISIBLE);
            }
            if (IntentHarga.isEmpty() || IntentHarga.equals("0")) {
                TVHarga.setText("-");
            } else {
                TVHarga.setText(currency.formatRupiah(IntentHarga));
            }
            if (IntentHargaSewa.isEmpty() || IntentHargaSewa.equals("0")) {
                TVHargaSewa.setText("-");
            } else {
                TVHargaSewa.setText(currency.formatRupiah(IntentHargaSewa));
            }
            if (IntentLokasi.isEmpty()) {
                TVLokasi.setText("-");
            } else {
                TVLokasi.setText(IntentLokasi);
            }
            if (IntentLTanah.isEmpty() || IntentLTanah.equals(" ")) {
                TVLTanah.setText("-");
            } else {
                TVLTanah.setText(IntentLTanah);
            }
            if (IntentLBangunan.isEmpty() || IntentLBangunan.equals(" ")) {
                TVLBangunan.setText("-");
            } else {
                TVLBangunan.setText(IntentLBangunan);
            }
            if (IntentNarahubung.isEmpty()) {
                TVNarahubung.setText(": -");
            } else {
                TVNarahubung.setText(": "+IntentNarahubung);
            }
            if (IntentNoTelp.isEmpty()) {
                TVTelpNarahubung.setText(": -");
            } else {
                TVTelpNarahubung.setText(": "+IntentNoTelp);
            }
            if (IntentJenisProperty.isEmpty()) {
                TVJenis.setText(": -");
            } else {
                TVJenis.setText(IntentJenisProperty);
            }
            if (IntentStatusProperty.isEmpty()) {
                TVStatus.setText(": -");
            } else {
                TVStatus.setText(": "+IntentStatusProperty);
            }
            if (IntentLBangunan.equals(" ") && IntentLTanah.equals(" ") && IntentLBangunan.isEmpty() && IntentLTanah.isEmpty()) {
                TVLuas.setText(": -");
            } else if (!IntentLBangunan.equals(" ") && !IntentLTanah.equals(" ") && !IntentLBangunan.isEmpty() && !IntentLTanah.isEmpty()) {
                TVLuas.setText(": "+IntentLTanah+" / "+IntentLBangunan);
            } else if (!IntentLBangunan.equals(" ") && !IntentLBangunan.isEmpty()) {
                TVLuas.setText(": "+IntentLBangunan);
            } else if (!IntentLTanah.equals(" ") && !IntentLTanah.isEmpty()) {
                TVLuas.setText(": "+IntentLTanah);
            } else {
                TVLuas.setText(": -");
            }
            if (IntentTglInput.isEmpty() && IntentJamInput.isEmpty()) {
                TVTglInput.setText(": -");
            } else {
                TVTglInput.setText(": "+IntentTglInput+"/"+IntentJamInput);
            }
            if (IntentKeterangan.isEmpty()) {
                TVDeskripsi.setText("-");
            } else {
                TVDeskripsi.setText(IntentKeterangan);
            }
            if (IntentImgSelfie.equals("0")) {
                LytSelfie.setVisibility(View.GONE);
                TVNoSelfie.setVisibility(View.VISIBLE);
            } else {
                Glide.with(DetailInfoActivity.this).load(IntentImgProperty).into(IVSelfie);
                TVNoSelfie.setVisibility(View.GONE);
            }
            if (IntentLatitude.equals("0") || IntentLongitude.equals("0")) {
                lat = Double.parseDouble("0");
                lng = Double.parseDouble("0");
                mapView.setVisibility(View.GONE);
            } else {
                lat = Double.parseDouble(IntentLatitude);
                lng = Double.parseDouble(IntentLongitude);
            }
            if (!IntentImgProperty.equals("0")) {
                images.add(IntentImgProperty);
            }

            adapter = new ViewPagerAdapter(this, images);
            viewPager.setPadding(0, 0, 0, 0);
            viewPager.setAdapter(adapter);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
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
                            .title(NamaMaps)
                            .icon(smallMarkerIcon);

                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                }
            });
        }
    }
    private void LoadAgen() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_AGEN_INFO + StrIdAgen, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String NamaCo = data.getString("Nama");
                                String TelpCo = data.getString("NoTelp");
                                String IGCo = data.getString("Instagram");

                                TVNamaAgen.setText(NamaCo);
                                IVInstagram.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String url = "http://instagram.com/_u/" + IGCo;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                    }
                                });
                                if (StringStatus.equals("1")) {
                                    IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
//                                            String deepLinkUrl = "https://gooproper.com/listing/" + idlisting;
                                            String message = "Halo! Saya Manager, ingin menanyakan update pada info property " + StringJudul + " yang beralamat di " + StringLokasi + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (StringStatus.equals("2")) {
                                    IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
//                                            String deepLinkUrl = "https://gooproper.com/listing/" + idlisting;
                                            String message = "Halo! Saya Admin, ingin menanyakan update pada listingan " + StringJudul + " yang beralamat di " + StringLokasi + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (StringStatus.equals("3")) {
                                    IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
//                                            String deepLinkUrl = "https://gooproper.com/listing/" + idlisting;
                                            String message = "Halo! Saya " + StringNamaBuyer + ", ingin melakukan cobroke pada listingan " + StringJudul + " yang beralamat di " + StringLokasi + ".\nApakah bersedia?";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else {
                                    IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
//                                            String deepLinkUrl = "https://gooproper.com/listing/" + idlisting;
                                            String message = "Halo! Saya " + StringNamaBuyer + ", ingin menanyakan informasi mengenai listingan " + StringJudul + " yang beralamat di " + StringLokasi + ".\nApakah masih ada? atau ada update terbaru?";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }
}