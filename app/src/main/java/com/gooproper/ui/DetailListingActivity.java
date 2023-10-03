package com.gooproper.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gooproper.R;
import com.gooproper.adapter.PJPAdapter;
import com.gooproper.adapter.SertifikatAdapter;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.ui.registrasi.RegistrasiAgenActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailListingActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog PDDetailListing;
    TextView TVNamaDetailListing, TVAlamatDetailListing, TVHargaDetailListing, TVViewsDetailListing, TVBedDetailListing, TVNamaAgen, TVBathDetailListing, TVWideDetailListing, TVLandDetailListing, TVTipeDetailListing, TVStatusDetailListing, TVSertifikatDetailListing, TVLuasDetailListing, TVKamarTidurDetailListing, TVKamarMandiDetailListing, TVLantaiDetailListing, TVGarasiDetailListing, TVCarpotDetailListing, TVListrikDetailListing, TVSumberAirDetailListing, TVSizeBanner, TVDeskripsiDetailListing, TVNoData, TVPriority, TVKondisi, TVNoPjp, TVNoDataPjp;
    ImageView IVFlowUp, IVWhatsapp, IVInstagram, IVFavorite, IVFavoriteOn, IVShare, IVEdit, IVStar1, IVStar2, IVStar3, IVStar4, IVStar5 ;
    Button BtnApproveAdmin, BtnApproveManager, BtnTambahMaps;
    TextInputEditText tambahagen, tambahpjp;
    TextInputLayout lytambahagen, lyttambahpjp;
    CheckBox CBMarketable, CBHarga;
    ScrollView scrollView;
    CardView agen;
    String status, idpralisting, idagen, idlisting, agenid, idpengguna, StringNamaListing, StringLuasTanah, StringLuasBangunan, StringKamarTidur, StringKamarTidurArt, StringKamarMandiArt, StringKamarMandi, StringListrik, StringHarga, StringSertifikat;
    String BuyerNama, BuyerTelp, BuyerKeterangan, BuyerTanggal, BuyerIdAgen, BuyerIdListing, BuyerIdInput, BuyerJam, StringNamaBuyer, AgenId;
    String NamaMaps;
    String imageUrl, namaAgen, telpAgen;
    String productId;
    ProgressDialog pDialog;
    ListingModel lm;
    LinearLayout LytSertifikat, LytPJP, LytSize;
    ViewPager viewPager, viewPagerSertifikat, viewPagerPJP;
    ViewPagerAdapter adapter;
    SertifikatAdapter sertifikatAdapter;
    PJPAdapter pjpAdapter;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> sertif = new ArrayList<>();
    ArrayList<String> pjpimage = new ArrayList<>();
    private String[] dataOptions;
    private int selectedOption = -1;
    private AgenManager agenManager;
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    private static final int REQUEST_WRITE_STORAGE = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_listing);

        PDDetailListing = new ProgressDialog(DetailListingActivity.this);
        tambahagen = findViewById(R.id.ETTambahAgenDetailListing);
        tambahpjp = findViewById(R.id.ETTambahNoPjpDetailListing);
        viewPager = findViewById(R.id.VPDetailListing);
        viewPagerSertifikat = findViewById(R.id.VPSertifikatDetailListing);
        viewPagerPJP = findViewById(R.id.VPPJPDetailListing);
        agen = findViewById(R.id.LytAgenDetailListing);
        lytambahagen = findViewById(R.id.LytTambahAgenDetailListing);
        lyttambahpjp = findViewById(R.id.LytTambahNoPjpDetailListing);
        LytSertifikat = findViewById(R.id.LytSertifikat);
        LytPJP = findViewById(R.id.LytViewPjp);
        LytSize = findViewById(R.id.LytUkuranBannerDetailListing);
        scrollView = findViewById(R.id.SVDetailListing);

        BtnApproveAdmin = findViewById(R.id.BtnApproveAdminDetailListing);
        BtnApproveManager = findViewById(R.id.BtnApproveManagerDetailListing);
        BtnTambahMaps = findViewById(R.id.BtnAddMapsDetailListing);

        TVNamaDetailListing = findViewById(R.id.TVNamaDetailListing);
        TVAlamatDetailListing = findViewById(R.id.TVAlamatDetailListing);
        TVHargaDetailListing = findViewById(R.id.TVHargaDetailListing);
        TVViewsDetailListing = findViewById(R.id.TVViewsDetailListing);
        TVBedDetailListing = findViewById(R.id.TVBedDetailListing);
        TVBathDetailListing = findViewById(R.id.TVBathDetailListing);
        TVWideDetailListing = findViewById(R.id.TVWideDetailListing);
        TVLandDetailListing = findViewById(R.id.TVLandDetailListing);
        TVTipeDetailListing = findViewById(R.id.TVTipeHunianDetailListing);
        TVStatusDetailListing = findViewById(R.id.TVStatusHunianDetailListing);
        TVSertifikatDetailListing = findViewById(R.id.TVSertifikatDetailListing);
        TVLuasDetailListing = findViewById(R.id.TVLuasHunianDetailListing);
        TVKamarTidurDetailListing = findViewById(R.id.TVKamarTidurHunianDetailListing);
        TVKamarMandiDetailListing = findViewById(R.id.TVKamarMandiHunianDetailListing);
        TVLantaiDetailListing = findViewById(R.id.TVLevelDetailListing);
        TVGarasiDetailListing = findViewById(R.id.TVGarasiDetailListing);
        TVCarpotDetailListing = findViewById(R.id.TVCarportDetailListing);
        TVListrikDetailListing = findViewById(R.id.TVListrikDetailListing);
        TVSumberAirDetailListing = findViewById(R.id.TVSumberAirDetailListing);
        TVSizeBanner = findViewById(R.id.TVUkuranBannerDetailListing);
        TVDeskripsiDetailListing = findViewById(R.id.TVDeskripsiDetailListing);
        TVNamaAgen = findViewById(R.id.TVNamaAgenDetailListing);
        TVNoData = findViewById(R.id.TVNoData);
        TVNoDataPjp = findViewById(R.id.TVNoDataPjp);
        TVPriority = findViewById(R.id.TVPriority);
        TVKondisi = findViewById(R.id.TVKondisi);
        TVNoPjp = findViewById(R.id.TVNoPjp);

        IVFlowUp = findViewById(R.id.IVFlowUpAgenDetailListing);
        IVWhatsapp = findViewById(R.id.IVNoTelpAgenDetailListing);
        IVInstagram = findViewById(R.id.IVInstagramAgenDetailListing);
        IVFavorite = findViewById(R.id.IVFavoriteDetailListing);
        IVFavoriteOn = findViewById(R.id.IVFavoriteOnDetailListing);
        IVShare = findViewById(R.id.IVShareDetailListing);
        IVEdit = findViewById(R.id.IVEditDetailListing);
        IVStar1 = findViewById(R.id.Star1);
        IVStar2 = findViewById(R.id.Star2);
        IVStar3 = findViewById(R.id.Star3);
        IVStar4 = findViewById(R.id.Star4);
        IVStar5 = findViewById(R.id.Star5);

        CBMarketable = findViewById(R.id.CBMarketable);
        CBHarga = findViewById(R.id.CBHarga);

        mapView = findViewById(R.id.MVDetailListing);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        agenManager = new AgenManager();

        checkStoragePermission();

        FormatCurrency currency = new FormatCurrency();

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdPraListing = data.getStringExtra("IdPraListing");
        String intentIdListing = data.getStringExtra("IdListing");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentIdAgenCo = data.getStringExtra("IdAgenCo");
        String intentIdInput = data.getStringExtra("IdInput");
        String intentNamaListing = data.getStringExtra("NamaListing");
        String intentAlamat = data.getStringExtra("Alamat");
        String intentLatitude = data.getStringExtra("Latitude");
        String intentLongitude = data.getStringExtra("Longitude");
        String intentLocation = data.getStringExtra("Location");
        String intentSelfie = data.getStringExtra("Selfie");
        String intentWide = data.getStringExtra("Wide");
        String intentLand = data.getStringExtra("Land");
        String intentListrik = data.getStringExtra("Listrik");
        String intentLevel = data.getStringExtra("Level");
        String intentBed = data.getStringExtra("Bed");
        String intentBedArt = data.getStringExtra("BedArt");
        String intentBath = data.getStringExtra("Bath");
        String intentBathArt = data.getStringExtra("BathArt");
        String intentGarage = data.getStringExtra("Garage");
        String intentCarpot = data.getStringExtra("Carpot");
        String intentHadap = data.getStringExtra("Hadap");
        String intentSHM = data.getStringExtra("SHM");
        String intentHGB = data.getStringExtra("HGB");
        String intentHSHP = data.getStringExtra("HSHP");
        String intentPPJB = data.getStringExtra("PPJB");
        String intentStratatitle = data.getStringExtra("Stratatitle");
        String intentPjp = data.getStringExtra("Pjp");
        String intentImgSHM = data.getStringExtra("ImgSHM");
        String intentImgHGB = data.getStringExtra("ImgHGB");
        String intentImgHSHP = data.getStringExtra("ImgHSHP");
        String intentImgPPJB = data.getStringExtra("ImgPPJB");
        String intentImgStratatitle = data.getStringExtra("ImgStratatitle");
        String intentImgPjp = data.getStringExtra("ImgPjp");
        String intentImgPjp1 = data.getStringExtra("ImgPjp1");
        String intentNoCertificate = data.getStringExtra("NoCertificate");
        String intentPbb = data.getStringExtra("Pbb");
        String intentJenisProperti = data.getStringExtra("JenisProperti");
        String intentJenisCertificate = data.getStringExtra("JenisCertificate");
        String intentSumberAir = data.getStringExtra("SumberAir");
        String intentKondisi = data.getStringExtra("Kondisi");
        String intentDeskripsi = data.getStringExtra("Deskripsi");
        String intentPrabot = data.getStringExtra("Prabot");
        String intentKetPrabot = data.getStringExtra("KetPrabot");
        String intentPriority = data.getStringExtra("Priority");
        String intentTtd = data.getStringExtra("Ttd");
        String intentBanner = data.getStringExtra("Banner");
        String intentSize = data.getStringExtra("Size");
        String intentHarga = data.getStringExtra("Harga");
        String intentHargaSewa = data.getStringExtra("HargaSewa");
        String intentTglInput = data.getStringExtra("TglInput");
        String intentImg1 = data.getStringExtra("Img1");
        String intentImg2 = data.getStringExtra("Img2");
        String intentImg3 = data.getStringExtra("Img3");
        String intentImg4 = data.getStringExtra("Img4");
        String intentImg5 = data.getStringExtra("Img5");
        String intentImg6 = data.getStringExtra("Img6");
        String intentImg7 = data.getStringExtra("Img7");
        String intentImg8 = data.getStringExtra("Img8");
        String intentVideo = data.getStringExtra("Video");
        String intentLinkFacebook = data.getStringExtra("LinkFacebook");
        String intentLinkTiktok = data.getStringExtra("LinkTiktok");
        String intentLinkInstagram = data.getStringExtra("LinkInstagram");
        String intentLinkYoutube = data.getStringExtra("LinkYoutube");
        String intentIsAdmin = data.getStringExtra("IsAdmin");
        String intentIsManager = data.getStringExtra("IsManager");
        String intentSold = data.getStringExtra("Sold");
        String intentView = data.getStringExtra("View");
        String intentMarketable = data.getStringExtra("Marketable");
        String intentStatusHarga = data.getStringExtra("StatusHarga");
        String intentNama = data.getStringExtra("Nama");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentInstagram = data.getStringExtra("Instagram");

        adapter = new ViewPagerAdapter(this, images);
        sertifikatAdapter = new SertifikatAdapter(this, sertif);
        pDialog = new ProgressDialog(this);
        status = Preferences.getKeyStatus(this);

        idpralisting = intentIdPraListing;
        idlisting = intentIdListing;
        idagen = intentIdAgen;
        NamaMaps = intentNamaListing;
        BuyerIdAgen = intentIdAgen;
        BuyerIdListing = intentIdListing;
        imageUrl = intentImg1;
        namaAgen = intentNama;
        telpAgen = intentNoTelp;
        productId = intentIdListing;
        StringNamaListing = intentNamaListing;
        StringLuasTanah = intentLand;
        StringLuasBangunan = intentWide;
        StringKamarTidur = intentBed;
        StringKamarTidurArt = intentBedArt;
        StringKamarMandi = intentBath;
        StringKamarMandiArt = intentBathArt;
        StringListrik = intentListrik;
        StringSertifikat = intentJenisCertificate;
        StringHarga = currency.formatRupiah(intentHarga);

        if (status.equals("1")) {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            LytSertifikat.setVisibility(View.VISIBLE);
            LytPJP.setVisibility(View.VISIBLE);
            IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String deepLinkUrl = "https://gooproper.com/listing/" + intentIdListing;
                    String message = "Halo! Saya Manager, ingin menanyakan update pada listingan " + intentNamaListing + " yang beralamat di " + intentAlamat + ".\nDetail Listingan :\n" + deepLinkUrl;
                    String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        } else if (status.equals("2")) {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            LytSertifikat.setVisibility(View.VISIBLE);
            LytPJP.setVisibility(View.VISIBLE);
            IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String deepLinkUrl = "https://gooproper.com/listing/" + intentIdListing;
                    String message = "Halo! Saya Admin, ingin menanyakan update pada listingan " + intentNamaListing + " yang beralamat di " + intentAlamat + ".\nDetail Listingan :\n" + deepLinkUrl;
                    String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        } else if (status.equals("3")) {
            StringNamaBuyer = Preferences.getKeyNama(this);
            IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String deepLinkUrl = "https://gooproper.com/listing/" + intentIdListing;
                    String message = "Halo! Saya " + StringNamaBuyer + ", ingin melakukan cobroke pada listingan " + intentNamaListing + " yang beralamat di " + intentAlamat + ".\nApakah bersedia? \nDetail Listingan :\n" + deepLinkUrl;
                    String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            LytSize.setVisibility(View.GONE);
        } else {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            IVFlowUp.setVisibility(View.INVISIBLE);
            LytSize.setVisibility(View.GONE);
            IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String deepLinkUrl = "https://gooproper.com/listing/" + intentIdListing;
                    String message = "Halo! Saya " + StringNamaBuyer + ", ingin menanyakan informasi mengenai listingan " + intentNamaListing + " yang beralamat di " + intentAlamat + ".\nApakah masih ada? atau ada update terbaru?\nDetail Listingan :\n" + deepLinkUrl;
                    String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        }

        if (status.equals("1")) {
            if (intentIsAdmin.equals("0")) {
                BtnApproveAdmin.setVisibility(View.VISIBLE);
                BtnApproveManager.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.INVISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.INVISIBLE);
                IVFavorite.setVisibility(View.GONE);
                CBMarketable.setVisibility(View.VISIBLE);
                CBHarga.setVisibility(View.VISIBLE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            } else if (intentIsManager.equals("0")) {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.VISIBLE);
                IVFlowUp.setVisibility(View.INVISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.INVISIBLE);
                IVFavorite.setVisibility(View.GONE);
                CBMarketable.setVisibility(View.VISIBLE);
                CBHarga.setVisibility(View.VISIBLE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            } else {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.VISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.VISIBLE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            }
        } else if (status.equals("2")) {
            if (intentIsAdmin.equals("0")) {
                BtnApproveAdmin.setVisibility(View.VISIBLE);
                BtnApproveManager.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.INVISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.INVISIBLE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
                if (!intentImgPjp.equals("0")){
                    lyttambahpjp.setVisibility(View.VISIBLE);
                }
            } else if (intentIsManager.equals("0")) {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.VISIBLE);
                IVFlowUp.setVisibility(View.INVISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.INVISIBLE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            } else {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.VISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.INVISIBLE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            }
        } else if (status.equals("3")) {
            if (intentLatitude.equals("0") && intentLongitude.equals("0")){
                if (intentIsAdmin.equals("0")){
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    if (intentIdInput == Preferences.getKeyIdAgen(DetailListingActivity.this)){
                        BtnTambahMaps.setVisibility(View.VISIBLE);
                    }
                } else if (intentIsManager.equals("0")) {
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    if (intentIdInput == Preferences.getKeyIdAgen(DetailListingActivity.this)){
                        BtnTambahMaps.setVisibility(View.VISIBLE);
                    }
                } else {
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                }
            } else {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.VISIBLE);
                idpengguna = "0";
                AgenId = Preferences.getKeyIdAgen(this);
            }
        } else if (status.equals("4")) {
            IVFlowUp.setVisibility(View.INVISIBLE);
            BtnApproveAdmin.setVisibility(View.GONE);
            BtnApproveManager.setVisibility(View.GONE);
            IVFlowUp.setVisibility(View.INVISIBLE);
            idpengguna = Preferences.getKeyIdCustomer(this);
            AgenId = "0";
        }

        if (intentPriority.equals("open")){
            if (intentBanner.equals("Ya")){
                if (intentMarketable.equals("1")){
                    IVStar1.setVisibility(View.VISIBLE);
                    IVStar2.setVisibility(View.VISIBLE);
                    IVStar3.setVisibility(View.VISIBLE);
                } else {
                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b) {
                                IVStar3.setVisibility(View.VISIBLE);
                            } else {
                                IVStar3.setVisibility(View.GONE);
                            }
                        }
                    });
                    IVStar1.setVisibility(View.VISIBLE);
                    IVStar2.setVisibility(View.VISIBLE);
                }
            } else {
                if (intentMarketable.equals("1")){
                    IVStar1.setVisibility(View.VISIBLE);
                    IVStar2.setVisibility(View.VISIBLE);
                } else {
                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (b) {
                                IVStar2.setVisibility(View.VISIBLE);
                            } else {
                                IVStar2.setVisibility(View.GONE);
                            }
                        }
                    });
                    IVStar1.setVisibility(View.VISIBLE);
                }
            }
        } else {
            if (intentBanner.equals("Ya")){
                if (intentMarketable.equals("1")){
                    if (intentStatusHarga.equals("1")){
                        IVStar1.setVisibility(View.VISIBLE);
                        IVStar2.setVisibility(View.VISIBLE);
                        IVStar3.setVisibility(View.VISIBLE);
                        IVStar4.setVisibility(View.VISIBLE);
                        IVStar5.setVisibility(View.VISIBLE);
                    } else {
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    IVStar5.setVisibility(View.VISIBLE);
                                } else {
                                    IVStar5.setVisibility(View.GONE);
                                }
                            }
                        });
                        IVStar1.setVisibility(View.VISIBLE);
                        IVStar2.setVisibility(View.VISIBLE);
                        IVStar3.setVisibility(View.VISIBLE);
                        IVStar4.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (intentStatusHarga.equals("1")){
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    IVStar5.setVisibility(View.VISIBLE);
                                } else {
                                    IVStar5.setVisibility(View.GONE);
                                }
                            }
                        });
                        IVStar1.setVisibility(View.VISIBLE);
                        IVStar2.setVisibility(View.VISIBLE);
                        IVStar3.setVisibility(View.VISIBLE);
                        IVStar4.setVisibility(View.VISIBLE);
                    } else {
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    IVStar4.setVisibility(View.VISIBLE);
                                } else {
                                    IVStar4.setVisibility(View.GONE);
                                }
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    IVStar5.setVisibility(View.VISIBLE);
                                } else {
                                    IVStar5.setVisibility(View.GONE);
                                }
                            }
                        });
                        IVStar1.setVisibility(View.VISIBLE);
                        IVStar2.setVisibility(View.VISIBLE);
                        IVStar3.setVisibility(View.VISIBLE);
                    }
                }
            } else {
                if (intentMarketable.equals("1")){
                    if (intentStatusHarga.equals("1")){
                        IVStar1.setVisibility(View.VISIBLE);
                        IVStar2.setVisibility(View.VISIBLE);
                        IVStar3.setVisibility(View.VISIBLE);
                        IVStar4.setVisibility(View.VISIBLE);
                    } else {
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    IVStar4.setVisibility(View.VISIBLE);
                                } else {
                                    IVStar4.setVisibility(View.GONE);
                                }
                            }
                        });
                        IVStar1.setVisibility(View.VISIBLE);
                        IVStar2.setVisibility(View.VISIBLE);
                        IVStar3.setVisibility(View.VISIBLE);
                    }
                } else {
                    if (intentStatusHarga.equals("1")){
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    IVStar4.setVisibility(View.VISIBLE);
                                } else {
                                    IVStar4.setVisibility(View.GONE);
                                }
                            }
                        });
                        IVStar1.setVisibility(View.VISIBLE);
                        IVStar2.setVisibility(View.VISIBLE);
                        IVStar3.setVisibility(View.VISIBLE);
                    } else {
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    IVStar3.setVisibility(View.VISIBLE);
                                } else {
                                    IVStar3.setVisibility(View.GONE);
                                }
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                if (b) {
                                    IVStar4.setVisibility(View.VISIBLE);
                                } else {
                                    IVStar4.setVisibility(View.GONE);
                                }
                            }
                        });
                        IVStar1.setVisibility(View.VISIBLE);
                        IVStar2.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        AddViews();
        AddSeen();

        viewPager.setOnClickListener(view -> startActivity(new Intent(DetailListingActivity.this, ImageViewActivity.class)));
        IVFavorite.setOnClickListener(v -> AddFavorite());
        IVFlowUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(DetailListingActivity.this, FollowUpActivity.class);
                update.putExtra("IdListing", intentIdListing);
                update.putExtra("IdAgen", intentIdAgen);
                startActivity(update);
            }
        });
        IVShare.setOnClickListener(view -> shareDeepLink(productId));
        IVEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailListingActivity.this, EditListingActivity.class);
                update.putExtra("IdPraListing",intentIdPraListing);
                update.putExtra("IdListing",intentIdListing);
                update.putExtra("IdAgen",intentIdAgen);
                update.putExtra("IdInput",intentIdInput);
                update.putExtra("NamaListing",intentNamaListing);
                update.putExtra("Alamat",intentAlamat);
                update.putExtra("Latitude",intentLatitude);
                update.putExtra("Longitude",intentLongitude);
                update.putExtra("Location",intentLocation);
                update.putExtra("Wide",intentWide);
                update.putExtra("Land",intentLand);
                update.putExtra("Listrik",intentListrik);
                update.putExtra("Level",intentLevel);
                update.putExtra("Bed",intentBed);
                update.putExtra("BedArt",intentBedArt);
                update.putExtra("Bath",intentBath);
                update.putExtra("BathArt",intentBathArt);
                update.putExtra("Garage",intentGarage);
                update.putExtra("Carpot",intentCarpot);
                update.putExtra("Hadap",intentHadap);
                update.putExtra("SHM",intentSHM);
                update.putExtra("HGB",intentHGB);
                update.putExtra("HSHP",intentHSHP);
                update.putExtra("PPJB",intentPPJB);
                update.putExtra("Stratatitle",intentStratatitle);
                update.putExtra("ImgSHM",intentImgSHM);
                update.putExtra("ImgHGB",intentImgHGB);
                update.putExtra("ImgHSHP",intentImgHSHP);
                update.putExtra("ImgPPJB",intentImgPPJB);
                update.putExtra("ImgStratatitle",intentImgStratatitle);
                update.putExtra("NoCertificate",intentNoCertificate);
                update.putExtra("Pbb",intentPbb);
                update.putExtra("JenisProperti",intentJenisProperti);
                update.putExtra("JenisCertificate",intentJenisCertificate);
                update.putExtra("SumberAir",intentSumberAir);
                update.putExtra("Kondisi",intentKondisi);
                update.putExtra("Deskripsi",intentDeskripsi);
                update.putExtra("Prabot",intentPrabot);
                update.putExtra("KetPrabot",intentKetPrabot);
                update.putExtra("Priority",intentPriority);
                update.putExtra("Ttd",intentTtd);
                update.putExtra("Banner",intentBanner);
                update.putExtra("Size",intentSize);
                update.putExtra("Harga",intentHarga);
                update.putExtra("TglInput",intentTglInput);
                update.putExtra("Img1",intentImg1);
                update.putExtra("Img2",intentImg2);
                update.putExtra("Img3",intentImg3);
                update.putExtra("Img4",intentImg4);
                update.putExtra("Img5",intentImg5);
                update.putExtra("Img6",intentImg6);
                update.putExtra("Img7",intentImg7);
                update.putExtra("Img8",intentImg8);
                update.putExtra("Video",intentVideo);
                update.putExtra("LinkFacebook",intentLinkFacebook);
                update.putExtra("LinkTiktok",intentLinkTiktok);
                update.putExtra("LinkInstagram",intentLinkInstagram);
                update.putExtra("LinkYoutube",intentLinkYoutube);
                update.putExtra("IsAdmin",intentIsAdmin);
                update.putExtra("IsManager",intentIsManager);
                update.putExtra("View",intentView);
                update.putExtra("Sold",intentSold);
                update.putExtra("Marketable",intentMarketable);
                update.putExtra("StatusHarga",intentStatusHarga);
                update.putExtra("Nama",intentNama);
                update.putExtra("NoTelp",intentNoTelp);
                update.putExtra("Instagram",intentInstagram);
                startActivity(update);
            }
        });
        tambahagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromApi();
            }
        });
        BtnApproveAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lytambahagen.getVisibility() == View.VISIBLE) {
                    String input = tambahagen.getText().toString();
                    if (input.isEmpty()) {
                        Dialog customDialog = new Dialog(DetailListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                        TextView message = customDialog.findViewById(R.id.TVDialogErorInput);
                        ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                        message.setText("Harap tambahkan agen terlebih dahulu");

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(DetailListingActivity.this)
                                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
                        approveadmin();
                    }
                } else {
                    approveadmin();
                }
            }
        });
        BtnApproveManager.setOnClickListener(v -> approvemanager());
        BtnTambahMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailListingActivity.this, EditPralistingActivity.class);
                update.putExtra("IdPraListing", intentIdPraListing);
                startActivity(update);
            }
        });

        if (update == 1) {
            if (intentKondisi.isEmpty()) {
                TVKondisi.setText("-");
            } else {
                TVKondisi.setText(intentKondisi);
            }

            if (intentPriority.isEmpty() || intentPriority.equals("open")) {
                TVPriority.setVisibility(View.INVISIBLE);
            } else {
                TVPriority.setText(intentPriority);
            }

            if (intentPjp.isEmpty() || intentPjp.equals("0")) {
                TVNoPjp.setVisibility(View.INVISIBLE);
            } else {
                TVNoPjp.setText(intentPjp);
            }

            if (intentNamaListing.isEmpty()) {
                TVNamaDetailListing.setText("-");
            } else {
                TVNamaDetailListing.setText(intentNamaListing);
            }

            if (intentAlamat.isEmpty()) {
                TVAlamatDetailListing.setText(intentAlamat);
            } else {
                TVAlamatDetailListing.setText(intentAlamat);
            }

            if (intentHarga.isEmpty()) {
                TVHargaDetailListing.setText("Rp. -");
            } else {
                TVHargaDetailListing.setText(currency.formatRupiah(intentHarga));
            }

            if (intentView.isEmpty()) {
                TVViewsDetailListing.setText("0 Views");
            } else {
                TVViewsDetailListing.setText(intentView + " Views");
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
                TVWideDetailListing.setText(intentLand + " m2");
            }

            if (intentWide.isEmpty()) {
                TVLandDetailListing.setText("-");
            } else {
                TVLandDetailListing.setText(intentWide + " m2");
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

            if (intentSHM.isEmpty()||intentSHM.equals("0")) {
                if (intentHGB.isEmpty()||intentHGB.equals("0")){
                    if (intentHSHP.isEmpty()||intentHSHP.equals("0")){
                        if (intentPPJB.isEmpty()||intentPPJB.equals("0")){
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": -");
                            } else {
                                TVSertifikatDetailListing.setText(": Stratatitle");
                            }
                        } else {
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": PPJB");
                            } else {
                                TVSertifikatDetailListing.setText(": PPJB, Stratatitle");
                            }
                        }
                    } else {
                        if (intentPPJB.isEmpty()||intentPPJB.equals("0")){
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": HS/HP");
                            } else {
                                TVSertifikatDetailListing.setText(": HS/HP, Stratatitle");
                            }
                        } else {
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": HS/HP, PPJB");
                            } else {
                                TVSertifikatDetailListing.setText(": HS/HP, PPJB, Stratatitle");
                            }
                        }
                    }
                } else {
                    if (intentHSHP.isEmpty()||intentHSHP.equals("0")){
                        if (intentPPJB.isEmpty()||intentPPJB.equals("0")){
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": HGB");
                            } else {
                                TVSertifikatDetailListing.setText(": HGB, Stratatitle");
                            }
                        } else {
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": HGB, PPJB");
                            } else {
                                TVSertifikatDetailListing.setText(": HGB, PPJB, Stratatitle");
                            }
                        }
                    } else {
                        if (intentPPJB.isEmpty()||intentPPJB.equals("0")){
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": HGB, HS/HP");
                            } else {
                                TVSertifikatDetailListing.setText(": HGB, HS/HP, Stratatitle");
                            }
                        } else {
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": HGB, HS/HP, PPJB");
                            } else {
                                TVSertifikatDetailListing.setText(": HGB, HS/HP, PPJB, Stratatitle");
                            }
                        }
                    }
                }
            } else {
                if (intentHGB.isEmpty()||intentHGB.equals("0")){
                    if (intentHSHP.isEmpty()||intentHSHP.equals("0")){
                        if (intentPPJB.isEmpty()||intentPPJB.equals("0")){
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": SHM");
                            } else {
                                TVSertifikatDetailListing.setText(": SHM, Stratatitle");
                            }
                        } else {
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": SHM, PPJB");
                            } else {
                                TVSertifikatDetailListing.setText(": SHM, PPJB, Stratatitle");
                            }
                        }
                    } else {
                        if (intentPPJB.isEmpty()||intentPPJB.equals("0")){
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": SHM, HS/HP");
                            } else {
                                TVSertifikatDetailListing.setText(": SHM, HS/HP, Stratatitle");
                            }
                        } else {
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": SHM, HS/HP, PPJB");
                            } else {
                                TVSertifikatDetailListing.setText(": SHM, HS/HP, PPJB, Stratatitle");
                            }
                        }
                    }
                } else {
                    if (intentHSHP.isEmpty()||intentHSHP.equals("0")){
                        if (intentPPJB.isEmpty()||intentPPJB.equals("0")){
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": SHM, HGB");
                            } else {
                                TVSertifikatDetailListing.setText(": SHM, HGB, Stratatitle");
                            }
                        } else {
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": SHM, HGB, PPJB");
                            } else {
                                TVSertifikatDetailListing.setText(": SHM, HGB, PPJB, Stratatitle");
                            }
                        }
                    } else {
                        if (intentPPJB.isEmpty()||intentPPJB.equals("0")){
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": SHM, HGB, HS/HP");
                            } else {
                                TVSertifikatDetailListing.setText(": SHM, HGB, HS/HP, Stratatitle");
                            }
                        } else {
                            if (intentStratatitle.isEmpty()||intentStratatitle.equals("0")){
                                TVSertifikatDetailListing.setText(": SHM, HGB, HS/HP, PPJB");
                            } else {
                                TVSertifikatDetailListing.setText(": SHM, HGB, HS/HP, PPJB, Stratatitle");
                            }
                        }
                    }
                }
            }

            if (intentWide.isEmpty()) {
                if (intentLand.isEmpty()) {
                    TVLuasDetailListing.setText(": - m2/- m2");
                } else {
                    TVLuasDetailListing.setText(": " +intentLand + " m2/" + " - m2");
                }
            } else {
                if (intentLand.isEmpty()) {
                    TVLuasDetailListing.setText(": - m2/" + intentWide + " m2");
                } else {
                    TVLuasDetailListing.setText(": " + intentLand + " m2/" + intentWide + " m2");
                }
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

            if (intentSize.isEmpty()) {
                TVSizeBanner.setText(": -");
            } else {
                TVSizeBanner.setText(": " + intentSize);
            }

            if (intentDeskripsi.isEmpty()) {
                TVDeskripsiDetailListing.setText("-");
            } else {
                TVDeskripsiDetailListing.setText(intentDeskripsi);
            }
            TVNamaAgen.setText(intentNama);

            if (intentLatitude.equals("0") || intentLongitude.equals("0")){
                lat = Double.parseDouble("0");
                lng = Double.parseDouble("0");
                mapView.setVisibility(View.GONE);
            } else {
                lat = Double.parseDouble(intentLatitude);
                lng = Double.parseDouble(intentLongitude);
            }

            IVInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "http://instagram.com/_u/" + intentInstagram;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            if (intentIdAgen.equals("null")) {
                agen.setVisibility(View.GONE);
                lytambahagen.setVisibility(View.VISIBLE);
                idagen = agenid;
            } else {
                agen.setVisibility(View.VISIBLE);
                lytambahagen.setVisibility(View.GONE);
                idagen = intentIdAgen;
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

            if (intentImgSHM.equals("0")) {
            } else {
                sertif.add(intentImgSHM);
            }

            if (intentImgHGB.equals("0")) {
            } else {
                sertif.add(intentImgHGB);
            }

            if (intentImgHSHP.equals("0")) {
            } else {
                sertif.add(intentImgHSHP);
            }

            if (intentImgPPJB.equals("0")) {
            } else {
                sertif.add(intentImgPPJB);
            }

            if (intentImgStratatitle.equals("0")) {
            } else {
                sertif.add(intentImgStratatitle);
            }

            if (intentImgPjp.equals("0")) {
            } else {
                pjpimage.add(intentImgPjp);
            }

            if (intentImgPjp1.equals("0")) {
            } else {
                pjpimage.add(intentImgPjp1);
            }

            if (intentImgSHM.equals("0") && intentImgHGB.equals("0") && intentImgHSHP.equals("0") && intentImgPPJB.equals("0") && intentImgStratatitle.equals("0")) {
                viewPagerSertifikat.setVisibility(View.GONE);
                TVNoData.setVisibility(View.VISIBLE);
            }

            if (intentImgPjp.equals("0") && intentImgPjp1.equals("0")) {
                viewPagerPJP.setVisibility(View.GONE);
                TVNoDataPjp.setVisibility(View.VISIBLE);
            }

            adapter = new ViewPagerAdapter(this, images);
            viewPager.setPadding(0, 0, 0, 0);
            viewPager.setAdapter(adapter);

            sertifikatAdapter = new SertifikatAdapter(this, sertif);
            viewPagerSertifikat.setPadding(0, 0, 0, 0);
            viewPagerSertifikat.setAdapter(sertifikatAdapter);

            pjpAdapter = new PJPAdapter(this, pjpimage);
            viewPagerPJP.setPadding(0, 0, 0, 0);
            viewPagerPJP.setAdapter(pjpAdapter);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Izin Penyimpanan Dibutuhkan", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void AddViews() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_ADD_VIEWS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                System.out.println(map);
                map.put("IdListing", idlisting);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void AddFavorite() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_ADD_FAVORITE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                IVFavorite.setVisibility(View.GONE);
                IVFavoriteOn.setVisibility(View.VISIBLE);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                System.out.println(map);
                map.put("IdListing", BuyerIdListing);
                map.put("IdAgen", AgenId);
                map.put("IdCustomer", idpengguna);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void AddSeen() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_ADD_SEEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                System.out.println(map);
                map.put("IdListing", BuyerIdListing);
                map.put("IdAgen", AgenId);
                map.put("IdCustomer", idpengguna);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void approveadmin() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_APPROVE_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Berhasil Approve Listing");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Gagal Approve Listing");
                ok.setVisibility(View.GONE);

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
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
                map.put("IdAgen", idagen);
                map.put("IdPraListing", idpralisting);
                map.put("Pjp", tambahpjp.getText().toString().trim());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void approvemanager() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        final String StringMarketable = CBMarketable.isChecked()?"1":"0";
        final String StringHarga = CBHarga.isChecked()?"1":"0";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_APPROVE_MANAGER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Berhasil Approve Listing");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Gagal Approve Listing");
                ok.setVisibility(View.GONE);

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
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
                map.put("IdPraListing", idpralisting);
                map.put("Marketable", StringMarketable);
                map.put("StatusHarga", StringHarga);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void fetchDataFromApi() {
        agenManager.fetchDataFromApi(this, new AgenManager.ApiCallback() {
            @Override
            public void onSuccess(List<AgenManager.DataItem> dataList) {
                showAlertDialog(dataList);
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    private void showAlertDialog(List<AgenManager.DataItem> dataList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Daftar Agen");

        final String[] dataItems = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            AgenManager.DataItem item = dataList.get(i);
            dataItems[i] = item.getName();
        }

        builder.setItems(dataItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AgenManager.DataItem selectedData = dataList.get(which);
                idagen = selectedData.getId();
                handleSelectedData(selectedData);
            }
        });

        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void handleSelectedData(AgenManager.DataItem selectedData) {
        String selectedText = "Selected ID: " + selectedData.getId();
        Toast.makeText(this, selectedText, Toast.LENGTH_SHORT).show();

        tambahagen.setText(selectedData.getName());
        agenid = selectedData.getId();
    }

    private void shareDeepLink(String productId) {
        String deepLinkUrl = "https://gooproper.com/listing/" + productId;
        String shareMessage = "Lihat listingan kami\n" + StringNamaListing + "\nLT " + StringLuasTanah + "m2 LB " + StringLuasBangunan + " m2\nKT " + StringKamarTidur + " + " + StringKamarTidurArt + "\nKM " + StringKamarMandi + " + " + StringKamarMandiArt + "\nListrik " + StringListrik + " Watt\n" + StringSertifikat + "\nHarga " + StringHarga + "\n\nhubungi : \n" + namaAgen + " - " + telpAgen + "\n\n" + deepLinkUrl;

        Picasso.get().load(imageUrl).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                String imagePath = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Image Title", null);
                Uri imageUri = Uri.parse(imagePath);

                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("image/*");

                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);

                startActivity(Intent.createChooser(shareIntent, "Bagikan melalui"));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                e.printStackTrace();
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
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
                    googleMap.addMarker(new MarkerOptions()
                            .position(currentLocation)
                            .title(NamaMaps));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                }
            });

            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    double latitude = lat;
                    double longitude = lng;

                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            });
        }
    }
}