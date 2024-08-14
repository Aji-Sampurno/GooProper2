package com.gooproper.ui.detail.listing;

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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.adapter.image.PJPAdapter;
import com.gooproper.adapter.image.SertifikatAdapter;
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.ImageViewActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailListingSoldActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog pDialog;
    TextView TVNamaDetailListing, TVBedDetailListing, TVBathDetailListing, TVWideDetailListing, TVLandDetailListing, TVDimensiDetailListing, TVTipeDetailListing, TVStatusDetailListing, TVSertifikatDetailListing, TVLuasDetailListing, TVKamarTidurDetailListing, TVKamarMandiDetailListing, TVLantaiDetailListing, TVGarasiDetailListing, TVCarpotDetailListing, TVListrikDetailListing, TVSumberAirDetailListing, TVPerabotDetailListing, TVSizeBanner, TVDeskripsiDetailListing, TVNoData, TVNoDataPdf, TVPriority, TVKondisi, TVNoPjp, TVNoDataPjp, TVFee, TVTglInput, TVNamaVendor, TVTelpVendor, TVPJP, TVSelfie, TVRejected, TVPoin, TVHadap, TVJumlahFoto, TVJumlahGambar;
    TextView SeeAllSekitar, SeeAllTerkait;
    ImageView IVSelfie;
    Button BtnNaikkanListing;
    ScrollView scrollView;
    CardView CVSold, CVRented;
    RecyclerView recycleListingSekitar, recycleListingTerkait;
    RecyclerView.Adapter AdapterSekitar, AdapterTerkait;
    String NamaMaps;
    String intentIdListing,intentIdAgen,intentIdAgenCo,intentIdInput,intentNamaListing,intentAlamat,intentAlamatTemplate,intentLatitude,intentLongitude,intentLocation,intentWilayah,intentSelfie,intentWide,intentLand,intentDimensi,intentListrik,intentLevel,intentBed,intentBedArt,intentBath,intentBathArt,intentGarage,intentCarpot,intentHadap,intentSHM,intentHGB,intentHSHP,intentPPJB,intentStratatitle,intentAJB,intentPetokD,intentPjp,intentImgSHM,intentImgHGB,intentImgHSHP,intentImgPPJB,intentImgStratatitle,intentImgAJB,intentImgPetokD,intentImgPjp,intentImgPjp1, intentNoCertificate,intentPbb,intentJenisProperti,intentJenisCertificate,intentSumberAir,intentKondisi,intentDeskripsi,intentPrabot,intentKetPrabot,intentPriority,intentTtd,intentBanner,intentSize,intentHarga,intentHargaSewa,intentRangeHarga,intentTglInput,intentImg1,intentImg2,intentImg3,intentImg4,intentImg5, intentImg6,intentImg7,intentImg8,intentImg9,intentImg10,intentImg11,intentImg12,intentVideo,intentLinkFacebook,intentLinkTiktok,intentLinkInstagram,intentLinkYoutube,intentIsAdmin,intentIsManager,intentIsRejected,intentSold,intentRented,intentSoldAgen,intentRentedAgen,intentView,intentMarketable,intentStatusHarga,intentNama,intentNoTelp,intentInstagram,intentFee,intentNamaVendor,intentNoTelpVendor,intentIsSelfie,intentIsLokasi,intentKeterangan,intentIdTemplate,intentTemplate,intentTemplateBlank;
    String StrStatus, StrIdAgen;
    String PJPHal1, PJPHal2;
    ListingModel lm;
    LinearLayout LytSertifikat, LytPJP, LytSize, LytFee, LytTglInput, LytBadge, LytBadgeSold, LytBadgeRented, IVEdit, LytNamaVendor, LytTelpVendor, LytRejected, LytSelfie, LytBtnHapus;
    ViewPager viewPager, viewPagerSertifikat, viewPagerPJP;
    ViewPagerAdapter adapter;
    SertifikatAdapter sertifikatAdapter;
    SertifikatPdfAdapter sertifikatPdfAdapter;
    PJPAdapter pjpAdapter;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> sertif = new ArrayList<>();
    ArrayList<String> sertifpdf = new ArrayList<>();
    ArrayList<String> pjpimage = new ArrayList<>();
    List<ListingModel> mItemsSekitar;
    List<ListingModel> mItemsTerkait;
    private String[] dataOptions;
    private int selectedOption = -1;
    private AgenManager agenManager, agenCoManager;
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    Uri UriPjp1, UriPjp2;
    private Dialog CustomDialogPjp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_listing_sold);

        pDialog = new ProgressDialog(this);

        viewPager = findViewById(R.id.VPDetailListing);
        viewPagerSertifikat = findViewById(R.id.VPSertifikatDetailListing);
        viewPagerPJP = findViewById(R.id.VPPJPDetailListing);
        CVSold = findViewById(R.id.LytSoldDetailListing);
        CVRented = findViewById(R.id.LytRentedDetailListing);
        LytSertifikat = findViewById(R.id.LytSertifikat);
        LytPJP = findViewById(R.id.LytViewPjp);
        LytSize = findViewById(R.id.LytUkuranBannerDetailListing);
        LytFee = findViewById(R.id.LytFeeDetailListing);
        LytTglInput = findViewById(R.id.LytTglInputDetailListing);
        LytBadgeSold = findViewById(R.id.LytBadgeSold);
        LytBadgeRented = findViewById(R.id.LytBadgeRented);
        LytNamaVendor = findViewById(R.id.LytNamaVendorDetailListing);
        LytTelpVendor = findViewById(R.id.LytTelpVendorDetailListing);
        LytSelfie = findViewById(R.id.LytViewSelfie);
        scrollView = findViewById(R.id.SVDetailListing);

        BtnNaikkanListing = findViewById(R.id.BtnNaikkanListing);

        TVNamaDetailListing = findViewById(R.id.TVNamaDetailListing);
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
        TVNoData = findViewById(R.id.TVNoData);
        TVNoDataPjp = findViewById(R.id.TVNoDataPjp);
        TVFee = findViewById(R.id.TVFeeDetailListing);
        TVTglInput = findViewById(R.id.TVTglInputDetailListing);
        TVNamaVendor = findViewById(R.id.TVNamaVendorDetailListing);
        TVTelpVendor = findViewById(R.id.TVTelpVendorDetailListing);
        TVPJP = findViewById(R.id.TVPjp);
        TVSelfie = findViewById(R.id.TVNoSelfie);
        TVJumlahGambar = findViewById(R.id.TVJumlahGambar);
        SeeAllSekitar = findViewById(R.id.SeeAllSekitar);
        SeeAllTerkait = findViewById(R.id.SeeAllTerkait);

        IVSelfie = findViewById(R.id.IVSelfie);

        recycleListingSekitar = findViewById(R.id.ListingSekitar);
        recycleListingTerkait = findViewById(R.id.ListingSerupa);

        mapView = findViewById(R.id.MVDetailListing);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        agenManager = new AgenManager();
        agenCoManager = new AgenManager();

        mItemsSekitar = new ArrayList<>();
        mItemsTerkait = new ArrayList<>();

        recycleListingSekitar.setLayoutManager(new LinearLayoutManager(DetailListingSoldActivity.this, LinearLayoutManager.HORIZONTAL, false));
        AdapterSekitar = new ListingAdapter(DetailListingSoldActivity.this, mItemsSekitar);
        recycleListingSekitar.setAdapter(AdapterSekitar);

        recycleListingTerkait.setLayoutManager(new LinearLayoutManager(DetailListingSoldActivity.this, LinearLayoutManager.HORIZONTAL, false));
        AdapterTerkait = new ListingAdapter(DetailListingSoldActivity.this, mItemsTerkait);
        recycleListingTerkait.setAdapter(AdapterTerkait);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        intentIdListing = data.getStringExtra("IdListing");
        intentIdAgen = data.getStringExtra("IdAgen");
        intentIdAgenCo = data.getStringExtra("IdAgenCo");
        intentIdInput = data.getStringExtra("IdInput");
        intentNamaListing = data.getStringExtra("NamaListing");
        intentAlamat = data.getStringExtra("Alamat");
        intentLatitude = data.getStringExtra("Latitude");
        intentLongitude = data.getStringExtra("Longitude");
        intentLocation = data.getStringExtra("Location");
        intentWilayah = data.getStringExtra("Wilayah");
        intentSelfie = data.getStringExtra("Selfie");
        intentWide = data.getStringExtra("Wide");
        intentLand = data.getStringExtra("Land");
        intentDimensi = data.getStringExtra("Dimensi");
        intentListrik = data.getStringExtra("Listrik");
        intentLevel = data.getStringExtra("Level");
        intentBed = data.getStringExtra("Bed");
        intentBedArt = data.getStringExtra("BedArt");
        intentBath = data.getStringExtra("Bath");
        intentBathArt = data.getStringExtra("BathArt");
        intentGarage = data.getStringExtra("Garage");
        intentCarpot = data.getStringExtra("Carpot");
        intentHadap = data.getStringExtra("Hadap");
        intentSHM = data.getStringExtra("SHM");
        intentHGB = data.getStringExtra("HGB");
        intentHSHP = data.getStringExtra("HSHP");
        intentPPJB = data.getStringExtra("PPJB");
        intentStratatitle = data.getStringExtra("Stratatitle");
        intentAJB = data.getStringExtra("AJB");
        intentPetokD = data.getStringExtra("PetokD");
        intentPjp = data.getStringExtra("Pjp");
        intentImgSHM = data.getStringExtra("ImgSHM");
        intentImgHGB = data.getStringExtra("ImgHGB");
        intentImgHSHP = data.getStringExtra("ImgHSHP");
        intentImgPPJB = data.getStringExtra("ImgPPJB");
        intentImgStratatitle = data.getStringExtra("ImgStratatitle");
        intentImgAJB = data.getStringExtra("ImgAJB");
        intentImgPetokD = data.getStringExtra("ImgPetokD");
        intentImgPjp = data.getStringExtra("ImgPjp");
        intentImgPjp1 = data.getStringExtra("ImgPjp1");
        intentNoCertificate = data.getStringExtra("NoCertificate");
        intentPbb = data.getStringExtra("Pbb");
        intentJenisProperti = data.getStringExtra("JenisProperti");
        intentJenisCertificate = data.getStringExtra("JenisCertificate");
        intentSumberAir = data.getStringExtra("SumberAir");
        intentKondisi = data.getStringExtra("Kondisi");
        intentDeskripsi = data.getStringExtra("Deskripsi");
        intentPrabot = data.getStringExtra("Prabot");
        intentKetPrabot = data.getStringExtra("KetPrabot");
        intentPriority = data.getStringExtra("Priority");
        intentTtd = data.getStringExtra("Ttd");
        intentBanner = data.getStringExtra("Banner");
        intentSize = data.getStringExtra("Size");
        intentHarga = data.getStringExtra("Harga");
        intentHargaSewa = data.getStringExtra("HargaSewa");
        intentRangeHarga = data.getStringExtra("RangeHarga");
        intentTglInput = data.getStringExtra("TglInput");
        intentImg1 = data.getStringExtra("Img1");
        intentImg2 = data.getStringExtra("Img2");
        intentImg3 = data.getStringExtra("Img3");
        intentImg4 = data.getStringExtra("Img4");
        intentImg5 = data.getStringExtra("Img5");
        intentImg6 = data.getStringExtra("Img6");
        intentImg7 = data.getStringExtra("Img7");
        intentImg8 = data.getStringExtra("Img8");
        intentImg9 = data.getStringExtra("Img9");
        intentImg10 = data.getStringExtra("Img10");
        intentImg11 = data.getStringExtra("Img11");
        intentImg12 = data.getStringExtra("Img12");
        intentVideo = data.getStringExtra("Video");
        intentLinkFacebook = data.getStringExtra("LinkFacebook");
        intentLinkTiktok = data.getStringExtra("LinkTiktok");
        intentLinkInstagram = data.getStringExtra("LinkInstagram");
        intentLinkYoutube = data.getStringExtra("LinkYoutube");
        intentIsAdmin = data.getStringExtra("IsAdmin");
        intentIsManager = data.getStringExtra("IsManager");
        intentIsRejected = data.getStringExtra("IsRejected");
        intentSold = data.getStringExtra("Sold");
        intentRented = data.getStringExtra("Rented");
        intentSoldAgen = data.getStringExtra("SoldAgen");
        intentRentedAgen = data.getStringExtra("RentedAgen");
        intentView = data.getStringExtra("View");
        intentMarketable = data.getStringExtra("Marketable");
        intentStatusHarga = data.getStringExtra("StatusHarga");
        intentNama = data.getStringExtra("Nama");
        intentNoTelp = data.getStringExtra("NoTelp");
        intentInstagram = data.getStringExtra("Instagram");
        intentFee = data.getStringExtra("Fee");
        intentNamaVendor = data.getStringExtra("NamaVendor");
        intentNoTelpVendor = data.getStringExtra("NoTelpVendor");
        intentIsSelfie = data.getStringExtra("IsSelfie");
        intentIsLokasi = data.getStringExtra("IsLokasi");
        intentKeterangan = data.getStringExtra("Keterangan");
        intentIdTemplate = data.getStringExtra("IdTemplate");
        intentTemplate = data.getStringExtra("Template");
        intentTemplateBlank = data.getStringExtra("TemplateBlank");

        StrStatus = Preferences.getKeyStatus(this);
        StrIdAgen = Preferences.getKeyIdAgen(this);

        NamaMaps = intentNamaListing;

        LoadListingSekitar();
        LoadListingTerkait();

        if (StrStatus.equals("1")) {
            LytSertifikat.setVisibility(View.VISIBLE);
            LytPJP.setVisibility(View.VISIBLE);
            LytNamaVendor.setVisibility(View.VISIBLE);
            LytTelpVendor.setVisibility(View.VISIBLE);
        } else if (StrStatus.equals("2")) {
            LytSertifikat.setVisibility(View.VISIBLE);
            LytPJP.setVisibility(View.VISIBLE);
            LytNamaVendor.setVisibility(View.VISIBLE);
            LytTelpVendor.setVisibility(View.VISIBLE);
        } else if (StrStatus.equals("3")) {
            LytSize.setVisibility(View.GONE);
            LytFee.setVisibility(View.GONE);
            LytTglInput.setVisibility(View.GONE);
            LytNamaVendor.setVisibility(View.GONE);
            LytTelpVendor.setVisibility(View.GONE);
            LytSelfie.setVisibility(View.GONE);
        } else {
            LytSize.setVisibility(View.GONE);
            LytFee.setVisibility(View.GONE);
            LytTglInput.setVisibility(View.GONE);
            LytNamaVendor.setVisibility(View.GONE);
            LytTelpVendor.setVisibility(View.GONE);
            LytSelfie.setVisibility(View.GONE);
        }

        if (StrStatus.equals("1")) {

        } else if (StrStatus.equals("2")) {

        } else if (StrStatus.equals("3")) {

        } else if (StrStatus.equals("4")) {

        } else {

        }

        viewPager.setOnClickListener(view -> startActivity(new Intent(DetailListingSoldActivity.this, ImageViewActivity.class)));

        if (update == 1) {

            int CountImage = 0;

            String[] intentImages = {
                    data.getStringExtra("Img1"),
                    data.getStringExtra("Img2"),
                    data.getStringExtra("Img3"),
                    data.getStringExtra("Img4"),
                    data.getStringExtra("Img5"),
                    data.getStringExtra("Img6"),
                    data.getStringExtra("Img7"),
                    data.getStringExtra("Img8"),
                    data.getStringExtra("Img9"),
                    data.getStringExtra("Img10"),
                    data.getStringExtra("Img11"),
                    data.getStringExtra("Img12")
            };

            for (String img : intentImages) {
                if (!img.equals("0")) {
                    CountImage++;
                }
            }

            if (intentIdAgen.equals("null")) {
                if (intentSold.equals("1")) {
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                } else if (intentSoldAgen.equals("1")) {
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                } else if (intentRented.equals("1")) {
                    LytBadgeRented.setVisibility(View.VISIBLE);
                    LytBadgeSold.setVisibility(View.GONE);
                    CVSold.setVisibility(View.GONE);
                    CVRented.setVisibility(View.VISIBLE);
                } else if (intentRentedAgen.equals("1")) {
                    LytBadgeRented.setVisibility(View.VISIBLE);
                    LytBadgeSold.setVisibility(View.GONE);
                    CVSold.setVisibility(View.GONE);
                    CVRented.setVisibility(View.VISIBLE);
                } else {
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                }
            } else {
                if (intentSold.equals("1")) {
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                } else if (intentSoldAgen.equals("1")) {
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                } else if (intentRented.equals("1")) {
                    LytBadgeRented.setVisibility(View.VISIBLE);
                    LytBadgeSold.setVisibility(View.GONE);
                    CVSold.setVisibility(View.GONE);
                    CVRented.setVisibility(View.VISIBLE);
                } else if (intentRentedAgen.equals("1")) {
                    LytBadgeRented.setVisibility(View.VISIBLE);
                    LytBadgeSold.setVisibility(View.GONE);
                    CVSold.setVisibility(View.GONE);
                    CVRented.setVisibility(View.VISIBLE);
                } else {
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                }
            }
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
            if (intentNamaListing.isEmpty()) {
                TVNamaDetailListing.setText("-");
            } else {
                TVNamaDetailListing.setText(intentNamaListing);
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
                Glide.with(this).load(intentSelfie).into(IVSelfie);
                TVSelfie.setVisibility(View.GONE);
            }
            if (intentLatitude.equals("0") || intentLongitude.equals("0")) {
                lat = Double.parseDouble("0");
                lng = Double.parseDouble("0");
                mapView.setVisibility(View.GONE);
            } else {
                lat = Double.parseDouble(intentLatitude);
                lng = Double.parseDouble(intentLongitude);
            }
            if (!intentTemplate.equals("null")) {
                images.add(intentTemplate);
            }
            if (!intentTemplateBlank.equals("null")) {
                images.add(intentTemplateBlank);
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
            if (!intentVideo.equals("0")) {
                images.add(intentVideo);
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
                viewPagerPJP.setVisibility(View.GONE);
                TVNoDataPjp.setVisibility(View.VISIBLE);
            } else if (intentImgPjp1.equals("0")) {
                TVPJP.setText("Bukti Chat");
            }

            adapter = new ViewPagerAdapter(this, images);
            viewPager.setPadding(0, 0, 0, 0);
            viewPager.setAdapter(adapter);

            sertifikatPdfAdapter = new SertifikatPdfAdapter(this, sertifpdf);
            viewPagerSertifikat.setAdapter(sertifikatPdfAdapter);

            pjpAdapter = new PJPAdapter(this, pjpimage);
            viewPagerPJP.setPadding(0, 0, 0, 0);
            viewPagerPJP.setAdapter(pjpAdapter);

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    updatePageInfo(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });

            updatePageInfo(0);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    @Override
    public void onBackPressed() {
        if (adapter != null && adapter.getCurrentExoPlayer() != null && adapter.getCurrentExoPlayer().isPlaying()) {
            adapter.getCurrentExoPlayer().stop();
            super.onBackPressed();
        } else {
            super.onBackPressed();
        }
    }
    private void updatePageInfo(int currentPage) {
        String info = (currentPage + 1) + "/" + images.size();
        TVJumlahGambar.setText(info);
    }
    private void LoadListingSekitar() {
        RequestQueue queue = Volley.newRequestQueue(DetailListingSoldActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, String.format(ServerApi.URL_GET_LISTING_SEKITAR,intentWilayah,intentJenisProperti,intentKondisi), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsSekitar.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdListing(data.getString("IdListing"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setIdAgenCo(data.getString("IdAgenCo"));
                                md.setIdInput(data.getString("IdInput"));
                                md.setNoArsip(data.getString("NoArsip"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setAlamatTemplate(data.getString("AlamatTemplate"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
                                md.setWilayah(data.getString("Wilayah"));
                                md.setSelfie(data.getString("Selfie"));
                                md.setWide(data.getString("Wide"));
                                md.setLand(data.getString("Land"));
                                md.setDimensi(data.getString("Dimensi"));
                                md.setListrik(data.getString("Listrik"));
                                md.setLevel(data.getString("Level"));
                                md.setBed(data.getString("Bed"));
                                md.setBath(data.getString("Bath"));
                                md.setBedArt(data.getString("BedArt"));
                                md.setBathArt(data.getString("BathArt"));
                                md.setGarage(data.getString("Garage"));
                                md.setCarpot(data.getString("Carpot"));
                                md.setHadap(data.getString("Hadap"));
                                md.setSHM(data.getString("SHM"));
                                md.setHGB(data.getString("HGB"));
                                md.setHSHP(data.getString("HSHP"));
                                md.setPPJB(data.getString("PPJB"));
                                md.setStratatitle(data.getString("Stratatitle"));
                                md.setAJB(data.getString("AJB"));
                                md.setPetokD(data.getString("PetokD"));
                                md.setPjp(data.getString("Pjp"));
                                md.setImgSHM(data.getString("ImgSHM"));
                                md.setImgHGB(data.getString("ImgHGB"));
                                md.setImgHSHP(data.getString("ImgHSHP"));
                                md.setImgPPJB(data.getString("ImgPPJB"));
                                md.setImgStratatitle(data.getString("ImgStratatitle"));
                                md.setImgAJB(data.getString("ImgAJB"));
                                md.setImgPetokD(data.getString("ImgPetokD"));
                                md.setImgPjp(data.getString("ImgPjp"));
                                md.setImgPjp1(data.getString("ImgPjp1"));
                                md.setNoCertificate(data.getString("NoCertificate"));
                                md.setPbb(data.getString("Pbb"));
                                md.setJenisProperti(data.getString("JenisProperti"));
                                md.setJenisCertificate(data.getString("JenisCertificate"));
                                md.setSumberAir(data.getString("SumberAir"));
                                md.setKondisi(data.getString("Kondisi"));
                                md.setDeskripsi(data.getString("Deskripsi"));
                                md.setPrabot(data.getString("Prabot"));
                                md.setKetPrabot(data.getString("KetPrabot"));
                                md.setPriority(data.getString("Priority"));
                                md.setTtd(data.getString("Ttd"));
                                md.setBanner(data.getString("Banner"));
                                md.setSize(data.getString("Size"));
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
                                md.setRangeHarga(data.getString("RangeHarga"));
                                md.setTglInput(data.getString("TglInput"));
                                md.setImg1(data.getString("Img1"));
                                md.setImg2(data.getString("Img2"));
                                md.setImg3(data.getString("Img3"));
                                md.setImg4(data.getString("Img4"));
                                md.setImg5(data.getString("Img5"));
                                md.setImg6(data.getString("Img6"));
                                md.setImg7(data.getString("Img7"));
                                md.setImg8(data.getString("Img8"));
                                md.setImg9(data.getString("Img9"));
                                md.setImg10(data.getString("Img10"));
                                md.setImg11(data.getString("Img11"));
                                md.setImg12(data.getString("Img12"));
                                md.setVideo(data.getString("Video"));
                                md.setLinkFacebook(data.getString("LinkFacebook"));
                                md.setLinkTiktok(data.getString("LinkTiktok"));
                                md.setLinkInstagram(data.getString("LinkInstagram"));
                                md.setLinkYoutube(data.getString("LinkYoutube"));
                                md.setIsAdmin(data.getString("IsAdmin"));
                                md.setIsManager(data.getString("IsManager"));
                                md.setIsRejected(data.getString("IsRejected"));
                                md.setSold(data.getString("Sold"));
                                md.setRented(data.getString("Rented"));
                                md.setSoldAgen(data.getString("SoldAgen"));
                                md.setRentedAgen(data.getString("RentedAgen"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setFee(data.getString("Fee"));
                                md.setNamaVendor(data.getString("NamaVendor"));
                                md.setNoTelpVendor(data.getString("NoTelpVendor"));
                                md.setIsSelfie(data.getString("IsSelfie"));
                                md.setIsLokasi(data.getString("IsLokasi"));
                                md.setIdTemplate(data.getString("IdTemplate"));
                                md.setTemplate(data.getString("Template"));
                                md.setTemplateBlank(data.getString("TemplateBlank"));
                                mItemsSekitar.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        AdapterSekitar.notifyDataSetChanged();
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
    private void LoadListingTerkait() {
        RequestQueue queue = Volley.newRequestQueue(DetailListingSoldActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, String.format(ServerApi.URL_GET_LISTING_TERKAIT,intentJenisProperti,intentKondisi), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsTerkait.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdListing(data.getString("IdListing"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setIdAgenCo(data.getString("IdAgenCo"));
                                md.setIdInput(data.getString("IdInput"));
                                md.setNoArsip(data.getString("NoArsip"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setAlamatTemplate(data.getString("AlamatTemplate"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
                                md.setWilayah(data.getString("Wilayah"));
                                md.setSelfie(data.getString("Selfie"));
                                md.setWide(data.getString("Wide"));
                                md.setLand(data.getString("Land"));
                                md.setDimensi(data.getString("Dimensi"));
                                md.setListrik(data.getString("Listrik"));
                                md.setLevel(data.getString("Level"));
                                md.setBed(data.getString("Bed"));
                                md.setBath(data.getString("Bath"));
                                md.setBedArt(data.getString("BedArt"));
                                md.setBathArt(data.getString("BathArt"));
                                md.setGarage(data.getString("Garage"));
                                md.setCarpot(data.getString("Carpot"));
                                md.setHadap(data.getString("Hadap"));
                                md.setSHM(data.getString("SHM"));
                                md.setHGB(data.getString("HGB"));
                                md.setHSHP(data.getString("HSHP"));
                                md.setPPJB(data.getString("PPJB"));
                                md.setStratatitle(data.getString("Stratatitle"));
                                md.setAJB(data.getString("AJB"));
                                md.setPetokD(data.getString("PetokD"));
                                md.setPjp(data.getString("Pjp"));
                                md.setImgSHM(data.getString("ImgSHM"));
                                md.setImgHGB(data.getString("ImgHGB"));
                                md.setImgHSHP(data.getString("ImgHSHP"));
                                md.setImgPPJB(data.getString("ImgPPJB"));
                                md.setImgStratatitle(data.getString("ImgStratatitle"));
                                md.setImgAJB(data.getString("ImgAJB"));
                                md.setImgPetokD(data.getString("ImgPetokD"));
                                md.setImgPjp(data.getString("ImgPjp"));
                                md.setImgPjp1(data.getString("ImgPjp1"));
                                md.setNoCertificate(data.getString("NoCertificate"));
                                md.setPbb(data.getString("Pbb"));
                                md.setJenisProperti(data.getString("JenisProperti"));
                                md.setJenisCertificate(data.getString("JenisCertificate"));
                                md.setSumberAir(data.getString("SumberAir"));
                                md.setKondisi(data.getString("Kondisi"));
                                md.setDeskripsi(data.getString("Deskripsi"));
                                md.setPrabot(data.getString("Prabot"));
                                md.setKetPrabot(data.getString("KetPrabot"));
                                md.setPriority(data.getString("Priority"));
                                md.setTtd(data.getString("Ttd"));
                                md.setBanner(data.getString("Banner"));
                                md.setSize(data.getString("Size"));
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
                                md.setRangeHarga(data.getString("RangeHarga"));
                                md.setTglInput(data.getString("TglInput"));
                                md.setImg1(data.getString("Img1"));
                                md.setImg2(data.getString("Img2"));
                                md.setImg3(data.getString("Img3"));
                                md.setImg4(data.getString("Img4"));
                                md.setImg5(data.getString("Img5"));
                                md.setImg6(data.getString("Img6"));
                                md.setImg7(data.getString("Img7"));
                                md.setImg8(data.getString("Img8"));
                                md.setImg9(data.getString("Img9"));
                                md.setImg10(data.getString("Img10"));
                                md.setImg11(data.getString("Img11"));
                                md.setImg12(data.getString("Img12"));
                                md.setVideo(data.getString("Video"));
                                md.setLinkFacebook(data.getString("LinkFacebook"));
                                md.setLinkTiktok(data.getString("LinkTiktok"));
                                md.setLinkInstagram(data.getString("LinkInstagram"));
                                md.setLinkYoutube(data.getString("LinkYoutube"));
                                md.setIsAdmin(data.getString("IsAdmin"));
                                md.setIsManager(data.getString("IsManager"));
                                md.setIsRejected(data.getString("IsRejected"));
                                md.setSold(data.getString("Sold"));
                                md.setRented(data.getString("Rented"));
                                md.setSoldAgen(data.getString("SoldAgen"));
                                md.setRentedAgen(data.getString("RentedAgen"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setFee(data.getString("Fee"));
                                md.setNamaVendor(data.getString("NamaVendor"));
                                md.setNoTelpVendor(data.getString("NoTelpVendor"));
                                md.setIsSelfie(data.getString("IsSelfie"));
                                md.setIsLokasi(data.getString("IsLokasi"));
                                md.setIdTemplate(data.getString("IdTemplate"));
                                md.setTemplate(data.getString("Template"));
                                md.setTemplateBlank(data.getString("TemplateBlank"));
                                mItemsTerkait.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        AdapterTerkait.notifyDataSetChanged();
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
                            .title(NamaMaps)
                            .icon(smallMarkerIcon);

                    googleMap.addMarker(markerOptions);
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));
                }
            });
        }
    }
}