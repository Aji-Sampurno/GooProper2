package com.gooproper.ui.detail.info;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
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
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.model.InfoModel;
import com.gooproper.ui.detail.listing.DetailListingActivity;
import com.gooproper.ui.edit.info.EditDetailInfoActivity;
import com.gooproper.ui.edit.info.EditInfoActivity;
import com.gooproper.ui.followup.FollowUpInfoActivity;
import com.gooproper.ui.tambah.listing.TambahListingInfoActivity;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DetailInfoActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog pDialog;
    TextView TVJudul, TVLokasi, TVHarga, TVHargaSewa, TVNamaAgen, TVLuas, TVLTanah, TVLBangunan, TVJenis, TVStatus, TVDeskripsi, TVTglInput, TVNarahubung, TVTelpNarahubung, TVNoSelfie, TVPoin;
    LinearLayout LytNarahubung, LytTelpNarahubung, LytTglInput, LytEdit;
    ImageView IVAddListing, IVWhatsapp, IVInstagram, IVSelfie, IVFollowUp;
    Button BtnTambah, BtnTambahSpek, BtnHapusInfo;
    LinearLayout LytSelfie;
    ScrollView scrollView;
    CardView agen;
    String NamaMaps, StringStatus, StringIdAgen, StrIdAgen, StringJudul, StringLokasi, StringNamaBuyer;
    String IntentIdInfo, IntentIdAgen, IntentJenisProperty, IntentStatusProperty, IntentNarahubung, IntentImgSelfie, IntentImgProperty, IntentLokasi, IntentAlamat, IntentNoTelp, IntentLatitude, IntentLongitude, IntentTglInput, IntentJamInput, IntentIsListing, IntentLBangunan, IntentLTanah, IntentHarga, IntentHargaSewa, IntentKeterangan, IntentIsSpek;
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
        IVFollowUp = findViewById(R.id.IVFollowUpDetailInfo);

        BtnTambah = findViewById(R.id.BtnAddListing);
        BtnTambahSpek = findViewById(R.id.BtnAddSpek);
        BtnHapusInfo = findViewById(R.id.BtnHapusInfo);

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

        LytTelpNarahubung = findViewById(R.id.LytTelpNarahubung);
        LytTglInput = findViewById(R.id.LytTglInput);
        LytEdit = findViewById(R.id.LytEditDetailInfo);

        mapView = findViewById(R.id.MVDetailInfo);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        IntentIdInfo = data.getStringExtra("IdInfo");
        IntentIdAgen = data.getStringExtra("IdAgen");
        IntentJenisProperty = data.getStringExtra("JenisProperty");
        IntentStatusProperty = data.getStringExtra("StatusProperty");
        IntentNarahubung = data.getStringExtra("Narahubung");
        IntentImgSelfie = data.getStringExtra("ImgSelfie");
        IntentImgProperty = data.getStringExtra("ImgProperty");
        IntentLokasi = data.getStringExtra("Lokasi");
        IntentAlamat = data.getStringExtra("Alamat");
        IntentNoTelp = data.getStringExtra("NoTelp");
        IntentLatitude = data.getStringExtra("Latitude");
        IntentLongitude = data.getStringExtra("Longitude");
        IntentTglInput = data.getStringExtra("TglInput");
        IntentJamInput = data.getStringExtra("JamInput");
        IntentIsListing = data.getStringExtra("IsListing");
        IntentLBangunan = data.getStringExtra("LBangunan");
        IntentLTanah = data.getStringExtra("LTanah");
        IntentHarga = data.getStringExtra("Harga");
        IntentHargaSewa = data.getStringExtra("HargaSewa");
        IntentKeterangan = data.getStringExtra("Keterangan");
        IntentIsSpek = data.getStringExtra("IsSpek");

        StringStatus = Preferences.getKeyStatus(this);
        StringIdAgen = Preferences.getKeyIdAgen(this);
        StrIdAgen = IntentIdAgen;
        StringJudul = IntentStatusProperty + " " + IntentJenisProperty;
        StringLokasi = IntentLokasi;

        if (StrIdAgen.equals(StringIdAgen)) {
            LytEdit.setVisibility(View.VISIBLE);
        } else {
            LytEdit.setVisibility(View.GONE);
        }

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
        BtnHapusInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HapusInfo();
            }
        });
        LytEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailInfoActivity.this, EditDetailInfoActivity.class);
                update.putExtra("IdInfo",IntentIdInfo);
                update.putExtra("JenisProperty",IntentJenisProperty);
                update.putExtra("StatusProperty",IntentStatusProperty);
                update.putExtra("Narahubung",IntentNarahubung);
                update.putExtra("ImgSelfie",IntentImgSelfie);
                update.putExtra("ImgProperty",IntentImgProperty);
                update.putExtra("Lokasi",IntentLokasi);
                update.putExtra("Alamat",IntentAlamat);
                update.putExtra("NoTelp",IntentNoTelp);
                update.putExtra("Latitude",IntentLatitude);
                update.putExtra("Longitude",IntentLongitude);
                update.putExtra("TglInput",IntentTglInput);
                update.putExtra("JamInput",IntentJamInput);
                update.putExtra("IsListing",IntentIsListing);
                update.putExtra("LBangunan",IntentLBangunan);
                update.putExtra("LTanah",IntentLTanah);
                update.putExtra("Harga",IntentHarga);
                update.putExtra("HargaSewa",IntentHargaSewa);
                update.putExtra("Keterangan",IntentKeterangan);
                update.putExtra("IsSpek",IntentIsSpek);
                startActivity(update);
            }
        });
        IVAddListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(DetailInfoActivity.this, TambahListingInfoActivity.class);
                update.putExtra("IdInfo",IntentIdInfo);
                update.putExtra("JenisProperty",IntentJenisProperty);
                update.putExtra("StatusProperty",IntentStatusProperty);
                update.putExtra("Narahubung",IntentNarahubung);
                update.putExtra("ImgSelfie",IntentImgSelfie);
                update.putExtra("ImgProperty",IntentImgProperty);
                update.putExtra("Lokasi",IntentLokasi);
                update.putExtra("Alamat",IntentAlamat);
                update.putExtra("NoTelp",IntentNoTelp);
                update.putExtra("Latitude",IntentLatitude);
                update.putExtra("Longitude",IntentLongitude);
                update.putExtra("TglInput",IntentTglInput);
                update.putExtra("JamInput",IntentJamInput);
                update.putExtra("IsListing",IntentIsListing);
                update.putExtra("LBangunan",IntentLBangunan);
                update.putExtra("LTanah",IntentLTanah);
                update.putExtra("Harga",IntentHarga);
                update.putExtra("HargaSewa",IntentHargaSewa);
                update.putExtra("Keterangan",IntentKeterangan);
                update.putExtra("IsSpek",IntentIsSpek);
                startActivity(update);
            }
        });
        IVFollowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(DetailInfoActivity.this, FollowUpInfoActivity.class);
                update.putExtra("IdInfo",IntentIdInfo);
                startActivity(update);
            }
        });

        if (StringStatus.equals("1")) {
            LytSelfie.setVisibility(View.VISIBLE);
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            BtnHapusInfo.setVisibility(View.VISIBLE);
        } else if (StringStatus.equals("2")) {
            LytSelfie.setVisibility(View.VISIBLE);
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            BtnHapusInfo.setVisibility(View.VISIBLE);
        } else if (StringStatus.equals("3")) {
            LytSelfie.setVisibility(View.GONE);
            StringNamaBuyer = Preferences.getKeyNama(this);
            BtnHapusInfo.setVisibility(View.GONE);
        } else {
            LytSelfie.setVisibility(View.GONE);
            BtnTambahSpek.setVisibility(View.GONE);
            TVPoin.setVisibility(View.GONE);
            BtnHapusInfo.setVisibility(View.GONE);
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
                if (!IntentImgSelfie.equals("0") && !IntentLatitude.equals("") && !IntentLongitude.equals("")) {
                    Poin = 20;
                    TVPoin.setText(String.valueOf(Poin));
                } else {
                    Poin = 10;
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
                TVLokasi.setText(IntentAlamat);
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
                SpannableStringBuilder builder = new SpannableStringBuilder(IntentKeterangan);

                int startIndex = IntentKeterangan.indexOf("*");
                int endIndex = IntentKeterangan.lastIndexOf("*");

                if (startIndex >= 0 && endIndex >= 0 && startIndex < endIndex) {
                    builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.delete(endIndex, endIndex + 1);
                    builder.delete(startIndex, startIndex + 1);
                }

                TVDeskripsi.setText(builder);
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
    private void HapusInfo() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_HIDE_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailInfoActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Info Deleted");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                        finish();
                    }
                });

                Glide.with(DetailInfoActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailInfoActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Gagal Delete Info");
                ok.setVisibility(View.GONE);

                Glide.with(DetailInfoActivity.this)
                        .load(R.mipmap.ic_no)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                System.out.println(map);
                map.put("IdInfo", IntentIdInfo);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}