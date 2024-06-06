package com.gooproper.ui.detail.listing;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager.widget.ViewPager;

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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.adapter.image.PJPAdapter;
import com.gooproper.adapter.image.SertifikatAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.officer.report.TambahCekLokasiActivity;
import com.gooproper.ui.officer.survey.TambahSurveyPralistingActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DetailPralistingTerdekatActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_WRITE_STORAGE = 1;
    private static final int RESULT_LOAD_PJP1 = 2;
    private static final int RESULT_LOAD_PJP2 = 3;
    private static final int MAX_TEXT_LENGTH_PRICE = 10;
    private static final int MAX_TEXT_LENGTH_PRICE_MILIAR = 23;
    private static final int MAX_TEXT_LENGTH_PRICE_JUTA = 19;
    private static final int MAX_TEXT_LENGTH_PRICE_RIBU = 15;
    ProgressDialog PDialog;
    TextView TVNamaListing, TVAlamat, TVKondisi, TVStatus, TVHargaJual, TVHargaSewa, TVRangeHarga, TVBedDetailListing, TVBathDetailListing, TVWideDetailListing, TVLandDetailListing, TVDimensiDetailListing, TVTipeDetailListing, TVStatusDetailListing, TVSertifikatDetailListing, TVLuasDetailListing, TVKamarTidurDetailListing, TVKamarMandiDetailListing, TVLantaiDetailListing, TVGarasiDetailListing, TVCarpotDetailListing, TVListrikDetailListing, TVSumberAirDetailListing, TVPerabotDetailListing, TVSizeBanner, TVDeskripsiDetailListing, TVHadap, TVFee, TVTglInput, TVNamaVendor, TVTelpVendor;
    TextView TVPJP, TVNoDataPjp, TVNoData, TVSelfie;
    TextView TVNamaAgen1, TVNamaAgen2;
    ImageView IVWa1, IVWa2, IVSelfie;
    Button BtnApprove, BtnReject;
    ScrollView scrollView;
    CardView CVAgen1, CVAgen2;
    ViewPager viewPager, viewPagerSertifikat, viewPagerPjp;
    ViewPagerAdapter adapter;
    SertifikatPdfAdapter sertifikatPdfAdapter;
    PJPAdapter pjpAdapter;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> sertifpdf = new ArrayList<>();
    ArrayList<String> pjpimage = new ArrayList<>();
    private String[] dataOptions;
    private Dialog CustomDialogPjp;
    private int selectedOption = -1;
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    String StringKeteranganReject, StringIdPralisting, StringIdAgen, StrIdPraListing;
    Uri UriPjp1, UriPjp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pralisting_terdekat);

        PDialog = new ProgressDialog(this);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdPraListing = data.getStringExtra("IdPraListing");

        StrIdPraListing = intentIdPraListing;

        viewPager = findViewById(R.id.VPDetailListing);
        viewPagerSertifikat = findViewById(R.id.VPSertifikatDetailListing);
        viewPagerPjp = findViewById(R.id.VPPJPDetailListing);
        scrollView = findViewById(R.id.SVDetailListing);
        CVAgen1 = findViewById(R.id.LytAgen1DetailListing);
        CVAgen2 = findViewById(R.id.LytAgen2DetailListing);

        mapView = findViewById(R.id.MVDetailListing);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(DetailPralistingTerdekatActivity.this);

        TVNamaListing = findViewById(R.id.TVNamaDetailListing);
        TVAlamat = findViewById(R.id.TVAlamatDetailListing);
        TVRangeHarga = findViewById(R.id.TVRangeHargaDetailListing);
        TVHargaJual = findViewById(R.id.TVHargaDetailListing);
        TVHargaSewa = findViewById(R.id.TVHargaSewaDetailListing);
        TVBedDetailListing = findViewById(R.id.TVBedDetailListing);
        TVBathDetailListing = findViewById(R.id.TVBathDetailListing);
        TVWideDetailListing = findViewById(R.id.TVWideDetailListing);
        TVLandDetailListing = findViewById(R.id.TVLandDetailListing);
        TVTipeDetailListing = findViewById(R.id.TVTipeHunianDetailListing);
        TVStatusDetailListing = findViewById(R.id.TVStatusHunianDetailListing);
        TVSertifikatDetailListing = findViewById(R.id.TVSertifikatDetailListing);
        TVLuasDetailListing = findViewById(R.id.TVLuasHunianDetailListing);
        TVDimensiDetailListing = findViewById(R.id.TVDimensiDetailListing);
        TVHadap = findViewById(R.id.TVHadapDetailListing);
        TVKamarTidurDetailListing = findViewById(R.id.TVKamarTidurHunianDetailListing);
        TVKamarMandiDetailListing = findViewById(R.id.TVKamarMandiHunianDetailListing);
        TVLantaiDetailListing = findViewById(R.id.TVLevelDetailListing);
        TVGarasiDetailListing = findViewById(R.id.TVGarasiDetailListing);
        TVCarpotDetailListing = findViewById(R.id.TVCarportDetailListing);
        TVListrikDetailListing = findViewById(R.id.TVListrikDetailListing);
        TVSumberAirDetailListing = findViewById(R.id.TVSumberAirDetailListing);
        TVPerabotDetailListing = findViewById(R.id.TVPerabotDetailListing);
        TVSizeBanner = findViewById(R.id.TVUkuranBannerDetailListing);
        TVDeskripsiDetailListing = findViewById(R.id.TVDeskripsiDetailListing);
        TVFee = findViewById(R.id.TVFeeDetailListing);
        TVTglInput = findViewById(R.id.TVTglInputDetailListing);
        TVNamaVendor = findViewById(R.id.TVNamaVendorDetailListing);
        TVTelpVendor = findViewById(R.id.TVTelpVendorDetailListing);
        TVNamaAgen1 = findViewById(R.id.TVNamaAgenDetailListing);
        TVNamaAgen2 = findViewById(R.id.TVNamaAgen2DetailListing);
        TVStatus = findViewById(R.id.TVPriority);
        TVKondisi = findViewById(R.id.TVKondisi);
        TVSelfie = findViewById(R.id.TVNoSelfie);
        TVPJP = findViewById(R.id.TVPjp);
        TVNoData = findViewById(R.id.TVNoData);
        TVNoDataPjp = findViewById(R.id.TVNoDataPjp);

        BtnApprove = findViewById(R.id.BtnApprove);
        BtnReject = findViewById(R.id.BtnReject);

        IVWa1 = findViewById(R.id.IVNoTelpAgenDetailListing);
        IVWa2 = findViewById(R.id.IVNoTelpAgenDetailListing);
        IVSelfie = findViewById(R.id.IVSelfie);

        PDialog.setMessage("Memuat Data...");
        PDialog.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRALISTING_SURVEY+intentIdPraListing,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDialog.cancel();
                        PDialog.dismiss();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                FormatCurrency currency = new FormatCurrency();
                                String intentIdListing = data.getString("IdPraListing");
                                String intentIdAgen = data.getString("IdAgen");
                                String intentIdAgenCo = data.getString("IdAgenCo");
                                String intentNamaListing = data.getString("NamaListing");
                                String intentAlamat = data.getString("Alamat");
                                String intentLatitude = data.getString("Latitude");
                                String intentLongitude = data.getString("Longitude");
                                String intentLocation = data.getString("Location");
                                String intentWilayah = data.getString("Wilayah");
                                String intentSelfie = data.getString("Selfie");
                                String intentWide = data.getString("Wide");
                                String intentLand = data.getString("Land");
                                String intentDimensi = data.getString("Dimensi");
                                String intentListrik = data.getString("Listrik");
                                String intentLevel = data.getString("Level");
                                String intentBed = data.getString("Bed");
                                String intentBedArt = data.getString("BedArt");
                                String intentBath = data.getString("Bath");
                                String intentBathArt = data.getString("BathArt");
                                String intentGarage = data.getString("Garage");
                                String intentCarpot = data.getString("Carpot");
                                String intentHadap = data.getString("Hadap");
                                String intentSHM = data.getString("SHM");
                                String intentHGB = data.getString("HGB");
                                String intentHSHP = data.getString("HSHP");
                                String intentPPJB = data.getString("PPJB");
                                String intentStratatitle = data.getString("Stratatitle");
                                String intentAJB = data.getString("AJB");
                                String intentPetokD = data.getString("PetokD");
                                String intentPjp = data.getString("Pjp");
                                String intentImgSHM = data.getString("ImgSHM");
                                String intentImgHGB = data.getString("ImgHGB");
                                String intentImgHSHP = data.getString("ImgHSHP");
                                String intentImgPPJB = data.getString("ImgPPJB");
                                String intentImgStratatitle = data.getString("ImgStratatitle");
                                String intentImgAJB = data.getString("ImgAJB");
                                String intentImgPetokD = data.getString("ImgPetokD");
                                String intentImgPjp = data.getString("ImgPjp");
                                String intentImgPjp1 = data.getString("ImgPjp1");
                                String intentNoCertificate = data.getString("NoCertificate");
                                String intentPbb = data.getString("Pbb");
                                String intentJenisProperti = data.getString("JenisProperti");
                                String intentJenisCertificate = data.getString("JenisCertificate");
                                String intentSumberAir = data.getString("SumberAir");
                                String intentKondisi = data.getString("Kondisi");
                                String intentDeskripsi = data.getString("Deskripsi");
                                String intentPrabot = data.getString("Prabot");
                                String intentKetPrabot = data.getString("KetPrabot");
                                String intentPriority = data.getString("Priority");
                                String intentTtd = data.getString("Ttd");
                                String intentBanner = data.getString("Banner");
                                String intentSize = data.getString("Size");
                                String intentHarga = data.getString("Harga");
                                String intentHargaSewa = data.getString("HargaSewa");
                                String intentRangeHarga = data.getString("RangeHarga");
                                String intentTglInput = data.getString("TglInput");
                                String intentFee = data.getString("Fee");
                                String intentImg1 = data.getString("Img1");
                                String intentImg2 = data.getString("Img2");
                                String intentImg3 = data.getString("Img3");
                                String intentImg4 = data.getString("Img4");
                                String intentImg5 = data.getString("Img5");
                                String intentImg6 = data.getString("Img6");
                                String intentImg7 = data.getString("Img7");
                                String intentImg8 = data.getString("Img8");
                                String intentImg9 = data.getString("Img9");
                                String intentImg10 = data.getString("Img10");
                                String intentImg11 = data.getString("Img11");
                                String intentImg12 = data.getString("Img12");
                                String intentNamaVendor = data.getString("NamaVendor");
                                String intentNoTelpVendor = data.getString("NoTelpVendor");
                                String intentNamaAgen1 = data.getString("NamaAgen1");
                                String intentNamaAgen2 = data.getString("NamaAgen2");
                                String intentTelp1 = data.getString("Telp1");
                                String intentTelp2 = data.getString("Telp2");

//                                if (intentIsRejected.equals("0")) {
//                                    LytRejected.setVisibility(View.GONE);
//                                } else {
//                                    LytRejected.setVisibility(View.VISIBLE);
//                                    TVRejected.setText(intentKeterangan);
//                                }
//                                if (intentMarketable.equals("1")) {
//                                    CBMarketable.setChecked(true);
//                                } else {
//                                    CBMarketable.setChecked(false);
//                                }
//                                if (intentStatusHarga.equals("1")) {
//                                    CBHarga.setChecked(true);
//                                } else {
//                                    CBHarga.setChecked(false);
//                                }
//                                if (intentIsSelfie.equals("1")) {
//                                    CBSelfie.setChecked(true);
//                                } else {
//                                    CBSelfie.setChecked(false);
//                                }
//                                if (intentIsLokasi.equals("1")) {
//                                    CBLokasi.setChecked(true);
//                                } else {
//                                    CBLokasi.setChecked(false);
//                                }
                                if (intentNamaVendor.isEmpty()) {
                                    TVNamaVendor.setText("-");
                                } else {
                                    TVNamaVendor.setText(": " + intentNamaVendor);
                                }
                                if (intentNoTelpVendor.isEmpty()) {
                                    TVTelpVendor.setText("-");
                                } else {
                                    TVTelpVendor.setText(": " + intentNoTelpVendor);
                                }
                                if (intentKondisi.isEmpty()) {
                                    TVKondisi.setText("-");
                                } else {
                                    TVKondisi.setText(intentKondisi);
                                }
                                if (intentJenisProperti.equals("Rukost")) {
                                    TVRangeHarga.setVisibility(View.GONE);
                                } else {
                                    TVRangeHarga.setVisibility(View.GONE);
                                }
                                if (intentNamaListing.isEmpty()) {
                                    TVNamaListing.setText("-");
                                } else {
                                    TVNamaListing.setText(intentNamaListing);
                                }
                                if (intentAlamat.isEmpty()) {
                                    TVAlamat.setText("-");
                                } else {
                                    if (intentWilayah.isEmpty()) {
                                        TVAlamat.setText(intentAlamat);
                                    } else {
                                        TVAlamat.setText(intentAlamat+" ( "+intentWilayah+" )");
                                    }
                                }
                                if (intentKondisi.equals("Jual")) {
                                    if (intentHarga.isEmpty()) {
                                        TVHargaJual.setText("Rp. -");
                                        TVHargaSewa.setVisibility(View.GONE);
                                    } else {
                                        String priceText = currency.formatRupiah(intentHarga);
                                        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                                        TVHargaJual.setText(truncatedprice);
                                        TVHargaSewa.setVisibility(View.GONE);
                                    }
                                } else if (intentKondisi.equals("Sewa")) {
                                    if (intentHargaSewa.isEmpty()) {
                                        TVHargaSewa.setText("Rp. -");
                                        TVHargaJual.setVisibility(View.GONE);
                                    } else {
                                        String priceText = currency.formatRupiah(intentHargaSewa);
                                        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                                        TVHargaSewa.setText(truncatedprice);
                                        TVHargaJual.setVisibility(View.GONE);
                                    }
                                } else {
                                    if (intentHarga.isEmpty()) {
                                        if (intentHargaSewa.isEmpty()) {
                                            TVHargaJual.setText("Rp. -");
                                            TVHargaSewa.setText("Rp. -");
                                        } else {
                                            String priceText = currency.formatRupiah(intentHargaSewa);
                                            String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                                            TVHargaJual.setText("Rp. - /");
                                            TVHargaSewa.setText(truncatedprice);
                                        }
                                    } else {
                                        if (intentHargaSewa.isEmpty()) {
                                            String priceText = currency.formatRupiah(intentHarga);
                                            String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                                            TVHargaJual.setText(truncatedprice + " /");
                                            TVHargaSewa.setText("Rp. -");
                                        } else {
                                            String priceText = currency.formatRupiah(intentHarga);
                                            String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                                            String priceTextSewa = currency.formatRupiah(intentHargaSewa);
                                            String truncatedpriceSewa = truncateTextWithEllipsisPrice(priceTextSewa);
                                            TVHargaJual.setText(truncatedprice + " /");
                                            TVHargaSewa.setText(truncatedpriceSewa);
                                        }
                                    }
                                }
                                if (intentBed.isEmpty()) {
                                    if (intentBedArt.isEmpty()) {
                                        TVBedDetailListing.setText("0 + 0");
                                    } else {
                                        TVBedDetailListing.setText("0 + " + intentBedArt);
                                    }
                                } else {
                                    if (intentBedArt.isEmpty()) {
                                        TVBedDetailListing.setText(intentBed + " + 0");
                                    } else {
                                        TVBedDetailListing.setText(intentBed + " + " + intentBedArt);
                                    }
                                }
                                if (intentBath.isEmpty()) {
                                    if (intentBathArt.isEmpty()) {
                                        TVBathDetailListing.setText("0 + 0");
                                    } else {
                                        TVBathDetailListing.setText("0 + " + intentBathArt);
                                    }
                                } else {
                                    if (intentBathArt.isEmpty()) {
                                        TVBathDetailListing.setText(intentBath + " + " + intentBathArt);
                                    } else {
                                        TVBathDetailListing.setText(intentBath + " + " + intentBathArt);
                                    }
                                }
                                if (intentLand.isEmpty()) {
                                    TVWideDetailListing.setText("-");
                                } else {
                                    TVWideDetailListing.setText(intentLand);
                                }
                                if (intentWide.isEmpty()) {
                                    TVLandDetailListing.setText("-");
                                } else {
                                    TVLandDetailListing.setText(intentWide);
                                }
                                if (intentJenisProperti.isEmpty()) {
                                    TVTipeDetailListing.setText(": -");
                                } else {
                                    TVTipeDetailListing.setText(": " + intentJenisProperti);
                                }
                                if (intentKondisi.isEmpty()) {
                                    TVStatusDetailListing.setText(": -");
                                } else {
                                    TVStatusDetailListing.setText(": " + intentKondisi);
                                }
                                StringBuilder messageBuilder = new StringBuilder(":");
                                if (intentImgSHM.equals("0") && intentImgHGB.equals("0") && intentImgHSHP.equals("0") && intentImgPPJB.equals("0") && intentImgStratatitle.equals("0") && intentImgAJB.equals("0") && intentImgPetokD.equals("0")) {
                                    messageBuilder.append(" -");
                                }
                                if (!intentSHM.isEmpty() && !intentSHM.equals("0")) {
                                    messageBuilder.append(" SHM");
                                }
                                if (!intentHGB.isEmpty() && !intentHGB.equals("0")) {
                                    if (!intentSHM.equals("0")) {
                                        messageBuilder.append(",HGB");
                                    } else {
                                        messageBuilder.append(" HGB");
                                    }

                                }
                                if (!intentHSHP.isEmpty() && !intentHSHP.equals("0")) {
                                    if (!intentSHM.equals("0") || !intentHGB.equals("0")) {
                                        messageBuilder.append(",HS/HP");
                                    } else {
                                        messageBuilder.append(" HS/HP");
                                    }
                                }
                                if (!intentPPJB.isEmpty() && !intentPPJB.equals("0")) {
                                    if (!intentSHM.equals("0") || !intentHGB.equals("0") || intentHSHP.equals("0")) {
                                        messageBuilder.append(",PPJB");
                                    } else {
                                        messageBuilder.append(" PPJB");
                                    }
                                }
                                if (!intentStratatitle.isEmpty() && !intentStratatitle.equals("0")) {
                                    messageBuilder.append(" Stratatitle");
                                }
                                if (!intentAJB.isEmpty() && !intentAJB.equals("0")) {
                                    messageBuilder.append(" AJB");
                                }
                                if (!intentPetokD.isEmpty() && !intentPetokD.equals("0")) {
                                    messageBuilder.append(" Petok D");
                                }
                                TVSertifikatDetailListing.setText(messageBuilder.toString());
                                if (intentWide.isEmpty()) {
                                    if (intentLand.isEmpty()) {
                                        TVLuasDetailListing.setText(": -");
                                    } else {
                                        TVLuasDetailListing.setText(": - / " + intentLand);
                                    }
                                } else {
                                    if (intentLand.isEmpty()) {
                                        TVLuasDetailListing.setText(": " + intentWide + "/ -");
                                    } else {
                                        TVLuasDetailListing.setText(": " + intentWide + "/" + intentLand);
                                    }
                                }
                                if (intentDimensi.isEmpty()) {
                                    TVDimensiDetailListing.setText(": -");
                                } else {
                                    TVDimensiDetailListing.setText(": " + intentDimensi);
                                }
                                if (intentHadap.isEmpty()) {
                                    TVHadap.setText(": -");
                                } else {
                                    TVHadap.setText(": " + intentHadap);
                                }
                                if (intentBed.isEmpty()) {
                                    if (intentBedArt.isEmpty()) {
                                        TVKamarTidurDetailListing.setText(": -");
                                    } else {
                                        TVKamarTidurDetailListing.setText(": -" + " + " + intentBedArt);
                                    }
                                } else {
                                    if (intentBedArt.isEmpty()) {
                                        TVKamarTidurDetailListing.setText(": " + intentBed + " + -");
                                    } else {
                                        TVKamarTidurDetailListing.setText(": " + intentBed + " + " + intentBedArt);
                                    }
                                }
                                if (intentBath.isEmpty()) {
                                    if (intentBathArt.isEmpty()) {
                                        TVKamarMandiDetailListing.setText(": -");
                                    } else {
                                        TVKamarMandiDetailListing.setText(": -" + " + " + intentBathArt);
                                    }
                                } else {
                                    if (intentBathArt.isEmpty()) {
                                        TVKamarMandiDetailListing.setText(": " + intentBath + " + -");
                                    } else {
                                        TVKamarMandiDetailListing.setText(": " + intentBath + " + " + intentBathArt);
                                    }
                                }
                                if (intentLevel.isEmpty()) {
                                    TVLantaiDetailListing.setText(": -");
                                } else {
                                    TVLantaiDetailListing.setText(": " + intentLevel);
                                }
                                if (intentGarage.isEmpty()) {
                                    TVGarasiDetailListing.setText(": -");
                                } else {
                                    TVGarasiDetailListing.setText(": " + intentGarage);
                                }
                                if (intentCarpot.isEmpty()) {
                                    TVCarpotDetailListing.setText(": -");
                                } else {
                                    TVCarpotDetailListing.setText(": " + intentCarpot);
                                }
                                if (intentListrik.isEmpty()) {
                                    TVListrikDetailListing.setText(": -");
                                } else {
                                    TVListrikDetailListing.setText(": " + intentListrik + " Watt");
                                }
                                if (intentSumberAir.isEmpty()) {
                                    TVSumberAirDetailListing.setText(": -");
                                } else {
                                    TVSumberAirDetailListing.setText(": " + intentSumberAir);
                                }
                                if (intentPrabot.equals("Tidak")) {
                                    TVPerabotDetailListing.setText(": -");
                                } else {
                                    TVPerabotDetailListing.setText(": " + intentKetPrabot);
                                }
                                if (intentSize.isEmpty()) {
                                    TVSizeBanner.setText(": -");
                                } else {
                                    TVSizeBanner.setText(": " + intentSize);
                                }
                                if (intentDeskripsi.isEmpty()) {
                                    TVDeskripsiDetailListing.setText("-");
                                } else {
                                    SpannableStringBuilder builder = new SpannableStringBuilder(intentDeskripsi);

                                    int startIndex = intentDeskripsi.indexOf("*");
                                    int endIndex = intentDeskripsi.lastIndexOf("*");

                                    if (startIndex >= 0 && endIndex >= 0 && startIndex < endIndex) {
                                        builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                                        builder.delete(endIndex, endIndex + 1);
                                        builder.delete(startIndex, startIndex + 1);
                                    }

                                    TVDeskripsiDetailListing.setText(builder);
                                }
                                if (intentFee.isEmpty()) {
                                    TVFee.setText(": -");
                                } else {
                                    TVFee.setText(": " + intentFee);
                                }
                                if (intentTglInput.isEmpty()) {
                                    TVTglInput.setText(": -");
                                } else {
                                    TVTglInput.setText(": " + intentTglInput);
                                }
                                if (intentSelfie.equals("0") || intentSelfie.isEmpty()) {
                                    IVSelfie.setVisibility(View.GONE);
                                    TVSelfie.setVisibility(View.VISIBLE);
                                } else {
                                    IVSelfie.setVisibility(View.VISIBLE);
                                    Glide.with(DetailPralistingTerdekatActivity.this).load(intentSelfie).into(IVSelfie);
                                    TVSelfie.setVisibility(View.GONE);
                                }
                                if (intentLatitude.equals("0") || intentLongitude.equals("0")){
                                    lat = Double.parseDouble("0");
                                    lng = Double.parseDouble("0");
                                    mapView.setVisibility(View.GONE);
                                } else {
                                    lat = Double.parseDouble(intentLatitude);
                                    lng = Double.parseDouble(intentLongitude);
                                }
                                if (!intentImg1.equals("0")) {
                                    images.add(intentImg1);
                                }
                                if (!intentImg2.equals("0")) {
                                    images.add(intentImg2);
                                }
                                if (!intentImg3.equals("0")) {
                                    images.add(intentImg3);
                                }
                                if (!intentImg4.equals("0")) {
                                    images.add(intentImg4);
                                }
                                if (!intentImg5.equals("0")) {
                                    images.add(intentImg5);
                                }
                                if (!intentImg6.equals("0")) {
                                    images.add(intentImg6);
                                }
                                if (!intentImg7.equals("0")) {
                                    images.add(intentImg7);
                                }
                                if (!intentImg8.equals("0")) {
                                    images.add(intentImg8);
                                }
                                if (!intentImg9.equals("0")) {
                                    images.add(intentImg9);
                                }
                                if (!intentImg10.equals("0")) {
                                    images.add(intentImg10);
                                }
                                if (!intentImg11.equals("0")) {
                                    images.add(intentImg11);
                                }
                                if (!intentImg12.equals("0")) {
                                    images.add(intentImg12);
                                }
                                if (!intentImgSHM.equals("0")) {
                                    sertifpdf.add(intentImgSHM);
                                }
                                if (!intentImgHGB.equals("0")) {
                                    sertifpdf.add(intentImgHGB);
                                }
                                if (!intentImgHSHP.equals("0")) {
                                    sertifpdf.add(intentImgHSHP);
                                }
                                if (!intentImgPPJB.equals("0")) {
                                    sertifpdf.add(intentImgPPJB);
                                }
                                if (!intentImgStratatitle.equals("0")) {
                                    sertifpdf.add(intentImgStratatitle);
                                }
                                if (!intentImgAJB.equals("0")) {
                                    sertifpdf.add(intentImgAJB);
                                }
                                if (!intentImgPetokD.equals("0")) {
                                    sertifpdf.add(intentImgPetokD);
                                }
                                if (!intentImgPjp.equals("0")) {
                                    pjpimage.add(intentImgPjp);
                                }
                                if (!intentImgPjp1.equals("0")) {
                                    pjpimage.add(intentImgPjp1);
                                }

                                if (intentImgSHM.equals("0") && intentImgHGB.equals("0") && intentImgHSHP.equals("0") && intentImgPPJB.equals("0") && intentImgStratatitle.equals("0") && intentImgAJB.equals("0") && intentImgPetokD.equals("0")) {
                                    viewPagerSertifikat.setVisibility(View.GONE);
                                    TVNoData.setVisibility(View.VISIBLE);
                                }

                                if (intentImgPjp.equals("0") && intentImgPjp1.equals("0")) {
                                    viewPagerPjp.setVisibility(View.GONE);
                                    TVNoDataPjp.setVisibility(View.VISIBLE);
                                } else if (intentImgPjp1.equals("0")) {
                                    TVPJP.setText("Bukti Chat");
                                }

                                if (intentIdAgenCo.equals("0")) {
                                    CVAgen2.setVisibility(View.GONE);
                                    TVNamaAgen1.setText(intentNamaAgen1);
                                    IVWa1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya Officer, ingin menanyakan pralistingan yang beralamat di " + intentAlamat + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + intentTelp1 + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                    CVAgen2.setVisibility(View.GONE);
                                    TVNamaAgen1.setText(intentNamaAgen1);
                                    IVWa1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya Officer, ingin menanyakan pralistingan yang beralamat di " + intentAlamat + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + intentTelp1 + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else {
                                    TVNamaAgen1.setText(intentNamaAgen1);
                                    IVWa1.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya Officer, ingin menanyakan pralistingan yang beralamat di " + intentAlamat + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + intentTelp1 + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                    TVNamaAgen2.setText(intentNamaAgen1);
                                    IVWa2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya Officer, ingin menanyakan pralistingan yang beralamat di " + intentAlamat + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + intentTelp2 + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                }

                                adapter = new ViewPagerAdapter(DetailPralistingTerdekatActivity.this, images);
                                viewPager.setPadding(0, 0, 0, 0);
                                viewPager.setAdapter(adapter);

                                sertifikatPdfAdapter = new SertifikatPdfAdapter(DetailPralistingTerdekatActivity.this, sertifpdf);
                                viewPagerSertifikat.setAdapter(sertifikatPdfAdapter);

                                pjpAdapter = new PJPAdapter(DetailPralistingTerdekatActivity.this, pjpimage);
                                viewPagerPjp.setPadding(0, 0, 0, 0);
                                viewPagerPjp.setAdapter(pjpAdapter);

                                BtnApprove.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent update = new Intent(DetailPralistingTerdekatActivity.this, TambahSurveyPralistingActivity.class);
                                        update.putExtra("IdPraListing", intentIdListing);
                                        startActivity(update);
                                    }
                                });

                                BtnReject.setOnClickListener(v -> ShowRejected(intentIdPraListing, intentIdAgen));
                            } catch (JSONException e) {
                                PDialog.dismiss();
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(DetailPralistingTerdekatActivity.this);
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PDialog.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(DetailPralistingTerdekatActivity.this);
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

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private String truncateTextWithEllipsisPrice(String text) {
        if (text.length() > MAX_TEXT_LENGTH_PRICE) {
            if (text.length() < MAX_TEXT_LENGTH_PRICE_RIBU) {
                String truncatedText = removeTrailingZeroK(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " Rb";
                return truncatedText;
            } else if (text.length() < MAX_TEXT_LENGTH_PRICE_JUTA) {
                String truncatedText = removeTrailingZeroJ(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " Jt";
                return truncatedText;
            } else if (text.length() < MAX_TEXT_LENGTH_PRICE_MILIAR) {
                String truncatedText = removeTrailingZeroM(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " M";
                return truncatedText;
            } else {
                String truncatedText = removeTrailingZeroT(text.substring(0, MAX_TEXT_LENGTH_PRICE)) + " T";
                return truncatedText;
            }
        } else {
            return text;
        }
    }
    private String removeTrailingZeroT(String text) {
        if (text.endsWith(".000")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        } else if (text.endsWith(".000.")) {
            return text.substring(0, text.length() - 5);
        } else if (text.endsWith("000.")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith("00.")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith("0.")) {
            return text.substring(0, text.length() - 2);
        } else {
            return text;
        }
    }
    private String removeTrailingZeroM(String text) {
        if (text.endsWith(".000")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        } else if (text.endsWith(".000.")) {
            return text.substring(0, text.length() - 5);
        } else if (text.endsWith("000.")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith("00.")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith("0.")) {
            return text.substring(0, text.length() - 2);
        } else {
            return text;
        }
    }
    private String removeTrailingZeroJ(String text) {
        if (text.endsWith(".000")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        } else if (text.endsWith(".000.")) {
            return text.substring(0, text.length() - 5);
        } else if (text.endsWith("000.")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith("00.")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".")) {
            return text.substring(0, text.length() - 1);
        } else if (text.endsWith("00")) {
            return text.substring(0, text.length() - 2);
        } else {
            return text;
        }
    }
    private String removeTrailingZeroK(String text) {
        if (text.endsWith(".000")) {
            return text.substring(0, text.length() - 4);
        } else if (text.endsWith(".00")) {
            return text.substring(0, text.length() - 3);
        } else if (text.endsWith(".0")) {
            return text.substring(0, text.length() - 2);
        } else {
            return text;
        }
    }

    private void ShowRejected(String Id, String IdAgen) {
        AlertDialog.Builder customBuilder = new AlertDialog.Builder(DetailPralistingTerdekatActivity.this, R.style.CustomAlertDialogStyle);
        customBuilder.setTitle("Keterangan Reject");

        LinearLayout containerLayout = new LinearLayout(this);
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        containerLayout.setPadding(30, 20, 30, 0);

        final EditText customKetInput = new EditText(this);

        customKetInput.setPadding(15, 15, 15, 15);
        customKetInput.setTextColor(getResources().getColor(android.R.color.black));
        customKetInput.setHint("Masukkan Keterangan");
        customKetInput.setHintTextColor(getResources().getColor(android.R.color.black));
        customKetInput.setBackgroundResource(R.drawable.backgroundbox);

        containerLayout.addView(customKetInput);

        customBuilder.setView(containerLayout);

        customBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String customBankName = customKetInput.getText().toString();
                StringKeteranganReject = customBankName;
                StringIdPralisting = Id;
                StringIdAgen = IdAgen;
                reject();
            }
        });

        customBuilder.setNegativeButton("Batal", null);

        AlertDialog customDialog = customBuilder.create();
        customDialog.show();
    }

    private void reject() {
        PDialog.setMessage("Sedang Diproses...");
        PDialog.setCancelable(false);
        PDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REJECTED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDialog.cancel();
                Dialog customDialog = new Dialog(DetailPralistingTerdekatActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Listing Rejected" + StringIdPralisting + StringIdAgen);
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE_AGEN + StringIdAgen, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    ArrayList<String> tokens = new ArrayList<>();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject tokenObject = response.getJSONObject(i);
                                        String token = tokenObject.getString("Token");
                                        tokens.add(token);
                                    }
                                    new SendMessageTaskReject().execute(tokens.toArray(new String[0]));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                            }
                        });
                        requestQueue.add(jsonArrayRequest);
                        customDialog.dismiss();
                        finish();
                    }
                });

                Glide.with(DetailPralistingTerdekatActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDialog.cancel();
                Dialog customDialog = new Dialog(DetailPralistingTerdekatActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Gagal Reject Listing");
                ok.setVisibility(View.GONE);

                Glide.with(DetailPralistingTerdekatActivity.this)
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
                map.put("IdPraListing", StringIdPralisting);
                map.put("Keterangan", StringKeteranganReject);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap map) {
        googleMap = map;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
            googleMap.getUiSettings().setMyLocationButtonEnabled(true);

            RequestQueue requestQueue = Volley.newRequestQueue(DetailPralistingTerdekatActivity.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRALISTING_SURVEY+StrIdPraListing, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            googleMap.clear();

                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    JSONObject markerObject = response.getJSONObject(i);
                                    double lat = markerObject.getDouble("Latitude");
                                    double lng = markerObject.getDouble("Longitude");
                                    String title = markerObject.getString("NamaListing");

                                    LatLng position = new LatLng(lat, lng);

                                    int width = 50;
                                    int height = 70;
                                    Bitmap smallMarker = Bitmap.createScaledBitmap(((BitmapDrawable) getResources().getDrawable(R.drawable.markerlocation)).getBitmap(), width, height, false);
                                    BitmapDescriptor smallMarkerIcon = BitmapDescriptorFactory.fromBitmap(smallMarker);

                                    MarkerOptions markerOptions = new MarkerOptions()
                                            .position(position)
                                            .title(title)
                                            .icon(smallMarkerIcon);

                                    googleMap.addMarker(markerOptions);
                                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 15f));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            );

            requestQueue.add(request);
        }
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

    private void sendNotificationToToken(String token, String notificationType) {
        String title = "Admin Goo Proper";
        String message = "Listing Anda Sudah di Approve";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }

    private class SendMessageTaskReject extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "rejected");
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void sendNotificationToTokenReject(String token, String notificationType) {
        String title = "Admin Goo Proper";
        String message = "Listing Anda Ditolak";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}