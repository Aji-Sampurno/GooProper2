package com.gooproper.ui.detail.pralisting;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
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
import com.github.chrisbanes.photoview.PhotoView;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.adapter.image.PJPAdapter;
import com.gooproper.adapter.image.SertifikatAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.ImageViewActivity;
import com.gooproper.ui.detail.agen.DetailAgenListingActivity;
import com.gooproper.ui.edit.pralisting.EditDataPralistingActivity;
import com.gooproper.ui.edit.pralisting.TambahDataGambarPralistingActivity;
import com.gooproper.ui.edit.pralisting.TambahDataLokasiPralistingActivity;
import com.gooproper.ui.edit.pralisting.TambahDataSelfiePralistingActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailPraListingRejectedActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog PDDetailListing;
    TextView TVJumlahGambar, TVPriority, TVNoPjp, TVKondisi;
    TextView TVNamaListing, TVAlamatListing, TVHargaJualListing, TVHargaSewaListing, TVRangeHargaListing, TVPoinListing;
    TextView TVBedListing, TVBathListing, TVLandListing, TVWideListing;
    TextView TVNamaVendor, TVTelpVendor;
    TextView TVTipeHunianListing, TVStatusHunianListing, TVSertifikatListing, TVLuasHunianListing, TVDimensiListing, TVHadapListing, TVKamarTidurListing, TVKamarMandiListing, TVLantaiListing, TVGarasiListing, TVCarpotListing, TVListrikListing, TVSumberAirListing, TVPerabotListing, TVBannerListing, TVFeeListing, TVTanggalInputListing, TVDeskripsiListing;
    TextView TVNoDataSertifikat, TVPjp, TVNoDataPjp, TVSelfie, TVNoDataSelfie;
    TextView TVKeteranganRejected;
    TextView TVNamaAgen1, TVNamaAgen2;
    ImageView IVVerifiedListing, IVAlamatListing, IVSelfieListing;
    ImageView IVFollowUp1, IVFollowUp2, IVTelpAgen1, IVTelpAgen2, IVInstagramAgen1, IVInstagramAgen2;
    Button BtnTambahGambar, BtnTambahBanner, BtnTambahPjp, BtnTambahMaps, BtnTambahSelfie, BtnTambahColis, BtnAjukanUlang;
    CheckBox CBMarketable, CBHarga, CBSelfie, CBLokasi;
    LinearLayout LytBadge, LytEdit;
    LinearLayout LytCBSelfie, LytCBLokasi, LytCBMarketable, LytCBHarga;
    LinearLayout LytNamaVendor, LytTelpVendor, LytBanner, LytFee, LytTanggalInput;
    LinearLayout LytSertifikat, LytPJP, LytSelfie, LytRejected;
    ScrollView scrollView;
    CardView CVAgen1, CVAgen2;
    ViewPager VPGambarListing, VPSertifikat, VPPjp;
    MapView mapView;
    GoogleMap googleMap;
    double lat, lng;
    String StrStatus, StrIdAgen, StrHargaJual, StrHargaSewa;
    String intentIdPraListing, intentIdAgen, intentIdAgenCo, intentIdInput, intentNamaListing, intentAlamat, intentAlamatTemplate, intentLatitude, intentLongitude, intentLocation, intentWilayah, intentSelfie, intentWide, intentLand, intentDimensi, intentListrik, intentLevel, intentBed, intentBedArt, intentBath, intentBathArt, intentGarage, intentCarpot, intentHadap, intentSHM, intentHGB, intentHSHP, intentPPJB, intentStratatitle, intentAJB, intentPetokD, intentPjp, intentImgSHM, intentImgHGB, intentImgHSHP, intentImgPPJB, intentImgStratatitle, intentImgAJB, intentImgPetokD, intentImgPjp, intentImgPjp1, intentNoCertificate, intentPbb, intentJenisProperti, intentJenisCertificate, intentSumberAir, intentKondisi, intentDeskripsi, intentPrabot, intentKetPrabot, intentPriority, intentTtd, intentBanner, intentSize, intentHarga, intentHargaSewa, intentRangeHarga, intentTglInput, intentImg1, intentImg2, intentImg3, intentImg4, intentImg5, intentImg6, intentImg7, intentImg8, intentImg9, intentImg10, intentImg11, intentImg12, intentVideo, intentLinkFacebook, intentLinkTiktok, intentLinkInstagram, intentLinkYoutube, intentIsAdmin, intentIsManager, intentIsRejected, intentSold, intentRented, intentSoldAgen, intentRentedAgen, intentView, intentMarketable, intentStatusHarga, intentNama, intentNoTelp, intentInstagram, intentFee, intentNamaVendor, intentNoTelpVendor, intentIsSelfie, intentIsLokasi, intentKeterangan;
    String NamaMaps, StrIdAgenCoList;
    String imageUrl, namaAgen, telpAgen, IdCo, UrlSHM, UrlHGB, UrlHSHP, UrlPPJB, UrlStratatitle, UrlAJB, UrlPetokD;
    String PJPHal1, PJPHal2;
    int Poin, FinalPoin, CoPoin;
    Uri UriPjp1, UriPjp2;
    ProgressDialog pDialog;
    ListingModel lm;
    ViewPagerAdapter adapter;
    SertifikatAdapter sertifikatAdapter;
    SertifikatPdfAdapter sertifikatPdfAdapter;
    PJPAdapter pjpAdapter;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> sertif = new ArrayList<>();
    ArrayList<String> sertifpdf = new ArrayList<>();
    ArrayList<String> pjpimage = new ArrayList<>();
    private Dialog CustomDialogPjp, customDialogBanner, customDialogCoList;
    private AgenManager agenManager, agenCoManager;
    private String[] dataOptions;
    private int selectedOption = -1;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static final int RESULT_LOAD_PJP1 = 1;
    private static final int RESULT_LOAD_PJP2 = 2;
    private static final int MAX_TEXT_LENGTH_PRICE = 10;
    private static final int MAX_TEXT_LENGTH_PRICE_MILIAR = 23;
    private static final int MAX_TEXT_LENGTH_PRICE_JUTA = 19;
    private static final int MAX_TEXT_LENGTH_PRICE_RIBU = 15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pralisting_rejected);

        PDDetailListing = new ProgressDialog(DetailPraListingRejectedActivity.this);
        pDialog = new ProgressDialog(this);

        VPGambarListing = findViewById(R.id.VPDetailListing);
        VPSertifikat = findViewById(R.id.VPSertifikatDetailListing);
        VPPjp = findViewById(R.id.VPPJPDetailListing);
        CVAgen1 = findViewById(R.id.LytAgenDetailListing);
        CVAgen2 = findViewById(R.id.LytAgen2DetailListing);
        LytEdit = findViewById(R.id.LytEditDetailListing);
        LytSertifikat = findViewById(R.id.LytSertifikat);
        LytPJP = findViewById(R.id.LytViewPjp);
        LytBanner = findViewById(R.id.LytUkuranBannerDetailListing);
        LytFee = findViewById(R.id.LytFeeDetailListing);
        LytTanggalInput = findViewById(R.id.LytTglInputDetailListing);
        LytBadge = findViewById(R.id.LytBadge);
        LytNamaVendor = findViewById(R.id.LytNamaVendorDetailListing);
        LytTelpVendor = findViewById(R.id.LytTelpVendorDetailListing);
        LytRejected = findViewById(R.id.LytRejectedDetailListing);
        LytSelfie = findViewById(R.id.LytViewSelfie);
        scrollView = findViewById(R.id.SVDetailListing);

        BtnTambahGambar = findViewById(R.id.BtnAddGambarDetailListing);
        BtnTambahMaps = findViewById(R.id.BtnAddMapsDetailListing);
        BtnTambahSelfie = findViewById(R.id.BtnAddSelfieDetailListing);
        BtnTambahBanner = findViewById(R.id.BtnAddBannerDetailListing);
        BtnTambahColis = findViewById(R.id.BtnColistDetailListing);
        BtnTambahPjp = findViewById(R.id.BtnAddPjpDetailListing);
        BtnAjukanUlang = findViewById(R.id.BtnAjukanUlangDetailListing);

        TVNamaListing = findViewById(R.id.TVNamaDetailListing);
        TVAlamatListing = findViewById(R.id.TVAlamatDetailListing);
        TVRangeHargaListing = findViewById(R.id.TVRangeHargaDetailListing);
        TVHargaJualListing = findViewById(R.id.TVHargaDetailListing);
        TVHargaSewaListing = findViewById(R.id.TVHargaSewaDetailListing);
        TVBedListing = findViewById(R.id.TVBedDetailListing);
        TVBathListing = findViewById(R.id.TVBathDetailListing);
        TVWideListing = findViewById(R.id.TVWideDetailListing);
        TVLandListing = findViewById(R.id.TVLandDetailListing);
        TVTipeHunianListing = findViewById(R.id.TVTipeHunianDetailListing);
        TVStatusHunianListing = findViewById(R.id.TVStatusHunianDetailListing);
        TVSertifikatListing = findViewById(R.id.TVSertifikatDetailListing);
        TVLuasHunianListing = findViewById(R.id.TVLuasHunianDetailListing);
        TVDimensiListing = findViewById(R.id.TVDimensiDetailListing);
        TVHadapListing = findViewById(R.id.TVHadapDetailListing);
        TVKamarTidurListing = findViewById(R.id.TVKamarTidurHunianDetailListing);
        TVKamarMandiListing = findViewById(R.id.TVKamarMandiHunianDetailListing);
        TVLantaiListing = findViewById(R.id.TVLevelDetailListing);
        TVGarasiListing = findViewById(R.id.TVGarasiDetailListing);
        TVCarpotListing = findViewById(R.id.TVCarportDetailListing);
        TVListrikListing = findViewById(R.id.TVListrikDetailListing);
        TVSumberAirListing = findViewById(R.id.TVSumberAirDetailListing);
        TVPerabotListing = findViewById(R.id.TVPerabotDetailListing);
        TVBannerListing = findViewById(R.id.TVUkuranBannerDetailListing);
        TVDeskripsiListing = findViewById(R.id.TVDeskripsiDetailListing);
        TVNamaAgen1 = findViewById(R.id.TVNamaAgenDetailListing);
        TVNamaAgen2 = findViewById(R.id.TVNamaAgen2DetailListing);
        TVNoDataSertifikat = findViewById(R.id.TVNoData);
        TVNoDataPjp = findViewById(R.id.TVNoDataPjp);
        TVPriority = findViewById(R.id.TVPriority);
        TVKondisi = findViewById(R.id.TVKondisi);
        TVNoPjp = findViewById(R.id.TVNoPjp);
        TVFeeListing = findViewById(R.id.TVFeeDetailListing);
        TVTanggalInputListing = findViewById(R.id.TVTglInputDetailListing);
        TVNamaVendor = findViewById(R.id.TVNamaVendorDetailListing);
        TVTelpVendor = findViewById(R.id.TVTelpVendorDetailListing);
        TVPjp = findViewById(R.id.TVPjp);
        TVSelfie = findViewById(R.id.TVNoSelfie);
        TVKeteranganRejected = findViewById(R.id.TVKeteranganDetailListing);
        TVPoinListing = findViewById(R.id.TVPoinListing);
        TVJumlahGambar = findViewById(R.id.TVJumlahGambar);

        IVAlamatListing = findViewById(R.id.IVAlamatDetailListing);
        IVFollowUp1 = findViewById(R.id.IVFlowUpAgenDetailListing);
        IVTelpAgen1 = findViewById(R.id.IVNoTelpAgenDetailListing);
        IVInstagramAgen1 = findViewById(R.id.IVInstagramAgenDetailListing);
        IVFollowUp2 = findViewById(R.id.IVFlowUpAgen2DetailListing);
        IVTelpAgen2 = findViewById(R.id.IVNoTelpAgen2DetailListing);
        IVInstagramAgen2 = findViewById(R.id.IVInstagramAgen2DetailListing);
        IVSelfieListing = findViewById(R.id.IVSelfie);

        CBMarketable = findViewById(R.id.CBMarketable);
        CBHarga = findViewById(R.id.CBHarga);
        CBSelfie = findViewById(R.id.CBSelfie);
        CBLokasi = findViewById(R.id.CBLokasi);
        LytCBMarketable = findViewById(R.id.LytCBMarketable);
        LytCBHarga = findViewById(R.id.LytCBHarga);
        LytCBSelfie = findViewById(R.id.LytCBSelfie);
        LytCBLokasi = findViewById(R.id.LytCBLokasi);

        mapView = findViewById(R.id.MVDetailListing);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        agenManager = new AgenManager();
        agenCoManager = new AgenManager();

        FormatCurrency currency = new FormatCurrency();

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        intentIdPraListing = data.getStringExtra("IdPraListing");
        intentIdAgen = data.getStringExtra("IdAgen");
        intentIdAgenCo = data.getStringExtra("IdAgenCo");
        intentIdInput = data.getStringExtra("IdInput");
        intentNamaListing = data.getStringExtra("NamaListing");
        intentAlamat = data.getStringExtra("Alamat");
        intentAlamatTemplate = data.getStringExtra("AlamatTemplate");
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

        StrStatus = Preferences.getKeyStatus(this);
        StrIdAgen = Preferences.getKeyIdAgen(this);

        if (intentKondisi.equals("Jual")) {
            StrHargaJual = currency.formatRupiah(intentHarga);
        } else if (intentKondisi.equals("Sewa")) {
            StrHargaSewa = currency.formatRupiah(intentHargaSewa);
        } else {
            StrHargaJual = currency.formatRupiah(intentHarga);
            StrHargaSewa = currency.formatRupiah(intentHargaSewa);
        }

        if (intentJenisProperti.equals("Rukost")) {
            TVRangeHargaListing.setVisibility(View.VISIBLE);
        } else {
            TVRangeHargaListing.setVisibility(View.GONE);
        }

        if (intentBanner.equals("Tidak")) {
            BtnTambahBanner.setVisibility(View.VISIBLE);
        } else {
            BtnTambahBanner.setVisibility(View.GONE);
        }

        if (intentImgPjp.equals("0") && intentImgPjp1.equals("0")) {
            TVPjp.setText("PJP");
            BtnTambahPjp.setVisibility(View.VISIBLE);
            TVNoDataPjp.setVisibility(View.VISIBLE);
            VPPjp.setVisibility(View.GONE);
        } else if (intentImgPjp.isEmpty() && intentImgPjp1.isEmpty()) {
            TVPjp.setText("PJP");
            BtnTambahPjp.setVisibility(View.VISIBLE);
            TVNoDataPjp.setVisibility(View.VISIBLE);
            VPPjp.setVisibility(View.GONE);
        } else if (!intentImgPjp.equals("0") && intentImgPjp1.equals("0") ) {
            TVPjp.setText("Bukti Chat");
            BtnTambahPjp.setVisibility(View.VISIBLE);
            TVNoDataPjp.setVisibility(View.GONE);
            VPPjp.setVisibility(View.VISIBLE);
        } else if (!intentImgPjp.isEmpty() && intentImgPjp1.isEmpty()) {
            TVPjp.setText("Bukti Chat");
            BtnTambahPjp.setVisibility(View.VISIBLE);
            TVNoDataPjp.setVisibility(View.GONE);
            VPPjp.setVisibility(View.VISIBLE);
        } else {
            TVPjp.setText("PJP");
            BtnTambahPjp.setVisibility(View.GONE);
            TVNoDataPjp.setVisibility(View.GONE);
            VPPjp.setVisibility(View.VISIBLE);
        }

        if (intentIdAgenCo.equals("0")) {
            CVAgen2.setVisibility(View.GONE);
            BtnTambahColis.setVisibility(View.VISIBLE);
        } else if (intentIdAgenCo.equals(intentIdAgen)) {
            CVAgen2.setVisibility(View.GONE);
            BtnTambahColis.setVisibility(View.VISIBLE);
        } else {
            LoadCo();
            CVAgen2.setVisibility(View.VISIBLE);
            BtnTambahColis.setVisibility(View.GONE);
        }

        if (intentLatitude.equals("0") && intentLongitude.equals("0")){
            BtnTambahMaps.setVisibility(View.VISIBLE);
            BtnTambahSelfie.setVisibility(View.GONE);
        } else {
            if (intentSelfie.equals("0") || intentSelfie.isEmpty()) {
                BtnTambahSelfie.setVisibility(View.VISIBLE);
                BtnTambahMaps.setVisibility(View.GONE);
            } else {
                BtnTambahSelfie.setVisibility(View.GONE);
                BtnTambahMaps.setVisibility(View.GONE);
            }
        }

        LytEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailPraListingRejectedActivity.this, EditDataPralistingActivity.class);
                update.putExtra("IdPraListing", intentIdPraListing);
                update.putExtra("IdAgen", intentIdAgen);
                update.putExtra("IdAgenCo", intentIdAgenCo);
                update.putExtra("IdInput", intentIdInput);
                update.putExtra("NamaListing", intentNamaListing);
                update.putExtra("Alamat", intentAlamat);
                update.putExtra("AlamatTemplate", intentAlamatTemplate);
                update.putExtra("Latitude", intentLatitude);
                update.putExtra("Longitude", intentLongitude);
                update.putExtra("Location", intentLocation);
                update.putExtra("Selfie", intentSelfie);
                update.putExtra("Wide", intentWide);
                update.putExtra("Land", intentLand);
                update.putExtra("Dimensi", intentDimensi);
                update.putExtra("Listrik", intentListrik);
                update.putExtra("Level", intentLevel);
                update.putExtra("Bed", intentBed);
                update.putExtra("BedArt", intentBedArt);
                update.putExtra("Bath", intentBath);
                update.putExtra("BathArt", intentBathArt);
                update.putExtra("Garage", intentGarage);
                update.putExtra("Carpot", intentCarpot);
                update.putExtra("Hadap", intentHadap);
                update.putExtra("SHM", intentSHM);
                update.putExtra("HGB", intentHGB);
                update.putExtra("HSHP", intentHSHP);
                update.putExtra("PPJB", intentPPJB);
                update.putExtra("Stratatitle", intentStratatitle);
                update.putExtra("AJB", intentAJB);
                update.putExtra("PetokD", intentPetokD);
                update.putExtra("Pjp", intentPjp);
                update.putExtra("ImgSHM", intentImgSHM);
                update.putExtra("ImgHGB", intentImgHGB);
                update.putExtra("ImgHSHP", intentImgHSHP);
                update.putExtra("ImgPPJB", intentImgPPJB);
                update.putExtra("ImgStratatitle", intentImgStratatitle);
                update.putExtra("ImgAJB", intentImgAJB);
                update.putExtra("ImgPetokD", intentImgPetokD);
                update.putExtra("ImgPjp", intentImgPjp);
                update.putExtra("ImgPjp1", intentImgPjp1);
                update.putExtra("NoCertificate", intentNoCertificate);
                update.putExtra("Pbb", intentPbb);
                update.putExtra("JenisProperti", intentJenisProperti);
                update.putExtra("JenisCertificate", intentJenisCertificate);
                update.putExtra("SumberAir", intentSumberAir);
                update.putExtra("Kondisi", intentKondisi);
                update.putExtra("Deskripsi", intentDeskripsi);
                update.putExtra("Prabot", intentPrabot);
                update.putExtra("KetPrabot", intentKetPrabot);
                update.putExtra("Priority", intentPriority);
                update.putExtra("Ttd", intentTtd);
                update.putExtra("Banner", intentBanner);
                update.putExtra("Size", intentSize);
                update.putExtra("Harga", intentHarga);
                update.putExtra("HargaSewa", intentHargaSewa);
                update.putExtra("TglInput", intentTglInput);
                update.putExtra("Img1", intentImg1);
                update.putExtra("Img2", intentImg2);
                update.putExtra("Img3", intentImg3);
                update.putExtra("Img4", intentImg4);
                update.putExtra("Img5", intentImg5);
                update.putExtra("Img6", intentImg6);
                update.putExtra("Img7", intentImg7);
                update.putExtra("Img8", intentImg8);
                update.putExtra("Img9", intentImg9);
                update.putExtra("Img10", intentImg10);
                update.putExtra("Img11", intentImg11);
                update.putExtra("Img12", intentImg12);
                update.putExtra("Video", intentVideo);
                update.putExtra("LinkFacebook", intentLinkFacebook);
                update.putExtra("LinkTiktok", intentLinkTiktok);
                update.putExtra("LinkInstagram", intentLinkInstagram);
                update.putExtra("LinkYoutube", intentLinkYoutube);
                update.putExtra("IsAdmin", intentIsAdmin);
                update.putExtra("IsManager", intentIsManager);
                update.putExtra("IsRejected", intentIsRejected);
                update.putExtra("Sold", intentSold);
                update.putExtra("Rented", intentRented);
                update.putExtra("View", intentView);
                update.putExtra("Marketable", intentMarketable);
                update.putExtra("StatusHarga", intentStatusHarga);
                update.putExtra("Nama", intentNama);
                update.putExtra("NoTelp", intentNoTelp);
                update.putExtra("Instagram", intentInstagram);
                update.putExtra("Fee", intentFee);
                update.putExtra("NamaVendor", intentNamaVendor);
                update.putExtra("NoTelpVendor", intentNoTelpVendor);
                update.putExtra("IsSelfie", intentIsSelfie);
                update.putExtra("IsLokasi", intentIsLokasi);
                update.putExtra("Keterangan", intentKeterangan);
                startActivity(update);
            }
        });
        BtnTambahGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailPraListingRejectedActivity.this, TambahDataGambarPralistingActivity.class);
                update.putExtra("IdPraListing", intentIdPraListing);
                update.putExtra("Img1", intentImg1);
                update.putExtra("Img2", intentImg2);
                update.putExtra("Img3", intentImg3);
                update.putExtra("Img4", intentImg4);
                update.putExtra("Img5", intentImg5);
                update.putExtra("Img6", intentImg6);
                update.putExtra("Img7", intentImg7);
                update.putExtra("Img8", intentImg8);
                update.putExtra("Img9", intentImg9);
                update.putExtra("Img10", intentImg10);
                update.putExtra("Img11", intentImg11);
                update.putExtra("Img12", intentImg12);
                startActivity(update);
            }
        });
        BtnTambahMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailPraListingRejectedActivity.this, TambahDataLokasiPralistingActivity.class);
                update.putExtra("IdPraListing", intentIdPraListing);
                update.putExtra("Selfie", intentSelfie);
                startActivity(update);
            }
        });
        BtnTambahSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailPraListingRejectedActivity.this, TambahDataSelfiePralistingActivity.class);
                update.putExtra("IdPraListing", intentIdPraListing);
                startActivity(update);
            }
        });
        BtnTambahBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTambahBanner();
            }
        });
        BtnTambahColis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTambahCoList();
            }
        });
        BtnTambahPjp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTambahPjp();
            }
        });
        BtnAjukanUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajukanulang();
            }
        });
        VPGambarListing.setOnClickListener(view -> startActivity(new Intent(DetailPraListingRejectedActivity.this, ImageViewActivity.class)));
        
        if (update == 1) {
            if (intentPriority.equals("exclusive") && !intentImgPjp.equals("0") && !intentImgPjp1.equals("0") && intentBanner.equals("Ya")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 120;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 120;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 120 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 50;
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 100;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 100;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 100 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 50;
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 50;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 50;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 50 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                    Poin = 50;
                }
            } else if (intentPriority.equals("exclusive") && !intentImgPjp.equals("0") && !intentImgPjp1.equals("0") && intentBanner.equals("Tidak")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 100;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 100;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 100 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 40;
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 80;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 80;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 80 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 40;
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 40;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 40;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 40 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                    Poin = 40;
                }
            } else if (intentPriority.equals("open") && !intentImgPjp.equals("0") && !intentImgPjp1.equals("0") && intentBanner.equals("Ya")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 70;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 70;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 70 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 30;
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 60;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 60;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 60 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 30;
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 30;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 30;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 30 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                    Poin = 30;
                }
            } else if (intentPriority.equals("open") && !intentImgPjp.equals("0") && !intentImgPjp1.equals("0") && intentBanner.equals("Tidak")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 60;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 60;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 60 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 20;
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 40;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 40;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 40 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 20;
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 20;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 20;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 20 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                    Poin = 20;
                }
            } else if (intentPriority.equals("open") && intentImgPjp.equals("0") && intentImgPjp1.equals("0") && intentBanner.equals("Ya")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 50;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 50;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 50 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 10;
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 20;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 20;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 20 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 10;
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 10;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 10;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 10 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                    Poin = 10;
                }
            } else if (intentPriority.equals("open") && intentImgPjp.equals("0") && intentImgPjp1.equals("0") && intentBanner.equals("Tidak")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 30;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 30;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 30 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 10;
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 20;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 20;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 20 / 2;
                            TVPoinListing.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 10;
                    }
                } else {
                    if (intentIdAgenCo.equals("0")) {
                        FinalPoin = 10;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
                        FinalPoin = 10;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    } else {
                        FinalPoin = 10 / 2;
                        TVPoinListing.setText(String.valueOf(FinalPoin));
                    }
                    Poin = 10;
                }
            }
            if (intentMarketable.equals("1")) {
                CBMarketable.setChecked(true);
            } else {
                CBMarketable.setChecked(false);
            }
            if (intentStatusHarga.equals("1")) {
                CBHarga.setChecked(true);
            } else {
                CBHarga.setChecked(false);
            }
            if (intentIsSelfie.equals("1")) {
                CBSelfie.setChecked(true);
            } else {
                CBSelfie.setChecked(false);
            }
            if (intentIsLokasi.equals("1")) {
                CBLokasi.setChecked(true);
            } else {
                CBLokasi.setChecked(false);
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
            if (intentKondisi.isEmpty()) {
                TVKondisi.setText("-");
            } else {
                TVKondisi.setText(intentKondisi);
            }
            if (intentPriority.isEmpty() || intentPriority.equals("open")) {
                TVPriority.setVisibility(View.INVISIBLE);
            } else {
                TVPriority.setText("Exclusive");
            }
            if (intentPjp.isEmpty() || intentPjp.equals("0")) {
                TVNoPjp.setVisibility(View.INVISIBLE);
            } else {
                TVNoPjp.setText(intentPjp);
            }
            if (intentNamaListing.isEmpty()) {
                TVNamaListing.setText("-");
            } else {
                TVNamaListing.setText(intentNamaListing);
            }
            if (intentAlamat.isEmpty()) {
                TVAlamatListing.setText("-");
            } else {
                if (intentWilayah.isEmpty()) {
                    TVAlamatListing.setText(intentAlamat);
                } else {
                    TVAlamatListing.setText(intentAlamat+" ( "+intentWilayah+" )");
                }
            }
            if (intentKondisi.equals("Jual")) {
                if (intentHarga.isEmpty()) {
                    TVHargaJualListing.setText("Rp. -");
                    TVHargaSewaListing.setVisibility(View.GONE);
                } else {
                    String priceText = currency.formatRupiah(intentHarga);
                    String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                    TVHargaJualListing.setText(truncatedprice);
                    TVHargaSewaListing.setVisibility(View.GONE);
                }
            } else if (intentKondisi.equals("Sewa")) {
                if (intentHargaSewa.isEmpty()) {
                    TVHargaSewaListing.setText("Rp. -");
                    TVHargaJualListing.setVisibility(View.GONE);
                } else {
                    String priceText = currency.formatRupiah(intentHargaSewa);
                    String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                    TVHargaJualListing.setText(truncatedprice);
                    TVHargaJualListing.setVisibility(View.GONE);
                }
            } else {
                if (intentHarga.isEmpty()) {
                    if (intentHargaSewa.isEmpty()) {
                        TVHargaJualListing.setText("Rp. -");
                        TVHargaSewaListing.setText("Rp. -");
                    } else {
                        TVHargaJualListing.setText("Rp. - /");
                        String priceText = currency.formatRupiah(intentHargaSewa);
                        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                        TVHargaSewaListing.setText(truncatedprice);
                    }
                } else {
                    if (intentHargaSewa.isEmpty()) {
                        String priceText = currency.formatRupiah(intentHarga);
                        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                        TVHargaJualListing.setText(truncatedprice);
                        TVHargaSewaListing.setText("Rp. -");
                    } else {
                        String priceText = currency.formatRupiah(intentHarga);
                        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                        String priceTextSewa = currency.formatRupiah(intentHargaSewa);
                        String truncatedpricesewa = truncateTextWithEllipsisPrice(priceText);
                        TVHargaJualListing.setText(truncatedprice+ " /");
                        TVHargaSewaListing.setText(truncatedpricesewa);
                    }
                }
            }
            if (intentBed.isEmpty()) {
                if (intentBedArt.isEmpty()) {
                    TVBedListing.setText("0 + 0");
                } else {
                    TVBedListing.setText("0 + " + intentBedArt);
                }
            } else {
                if (intentBedArt.isEmpty()) {
                    TVBedListing.setText(intentBed + " + 0");
                } else {
                    TVBedListing.setText(intentBed + " + " + intentBedArt);
                }
            }
            if (intentBath.isEmpty()) {
                if (intentBathArt.isEmpty()) {
                    TVBathListing.setText("0 + 0");
                } else {
                    TVBathListing.setText("0 + " + intentBathArt);
                }
            } else {
                if (intentBathArt.isEmpty()) {
                    TVBathListing.setText(intentBath + " + " + intentBathArt);
                } else {
                    TVBathListing.setText(intentBath + " + " + intentBathArt);
                }
            }
            if (intentLand.isEmpty()) {
                TVWideListing.setText("-");
            } else {
                TVWideListing.setText(intentLand);
            }
            if (intentWide.isEmpty()) {
                TVLandListing.setText("-");
            } else {
                TVLandListing.setText(intentWide);
            }
            if (intentJenisProperti.isEmpty()) {
                TVTipeHunianListing.setText(": -");
            } else {
                TVTipeHunianListing.setText(": " + intentJenisProperti);
            }
            if (intentKondisi.isEmpty()) {
                TVStatusHunianListing.setText(": -");
            } else {
                TVStatusHunianListing.setText(": " + intentKondisi);
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
            TVSertifikatListing.setText(messageBuilder.toString());
            if (intentWide.isEmpty()) {
                if (intentLand.isEmpty()) {
                    TVLuasHunianListing.setText(": -");
                } else {
                    TVLuasHunianListing.setText(": - / " + intentLand);
                }
            } else {
                if (intentLand.isEmpty()) {
                    TVLuasHunianListing.setText(": " + intentWide + "/ -");
                } else {
                    TVLuasHunianListing.setText(": " + intentWide + "/" + intentLand);
                }
            }
            if (intentDimensi.isEmpty()) {
                TVDimensiListing.setText(": -");
            } else {
                TVDimensiListing.setText(": " + intentDimensi);
            }
            if (intentHadap.isEmpty()) {
                TVHadapListing.setText(": -");
            } else {
                TVHadapListing.setText(": " + intentHadap);
            }
            if (intentBed.isEmpty()) {
                if (intentBedArt.isEmpty()) {
                    TVKamarTidurListing.setText(": -");
                } else {
                    TVKamarTidurListing.setText(": -" + " + " + intentBedArt);
                }
            } else {
                if (intentBedArt.isEmpty()) {
                    TVKamarTidurListing.setText(": " + intentBed + " + -");
                } else {
                    TVKamarTidurListing.setText(": " + intentBed + " + " + intentBedArt);
                }
            }
            if (intentBath.isEmpty()) {
                if (intentBathArt.isEmpty()) {
                    TVKamarMandiListing.setText(": -");
                } else {
                    TVKamarMandiListing.setText(": -" + " + " + intentBathArt);
                }
            } else {
                if (intentBathArt.isEmpty()) {
                    TVKamarMandiListing.setText(": " + intentBath + " + -");
                } else {
                    TVKamarMandiListing.setText(": " + intentBath + " + " + intentBathArt);
                }
            }
            if (intentLevel.isEmpty()) {
                TVLantaiListing.setText(": -");
            } else {
                TVLantaiListing.setText(": " + intentLevel);
            }
            if (intentGarage.isEmpty()) {
                TVGarasiListing.setText(": -");
            } else {
                TVGarasiListing.setText(": " + intentGarage);
            }
            if (intentCarpot.isEmpty()) {
                TVCarpotListing.setText(": -");
            } else {
                TVCarpotListing.setText(": " + intentCarpot);
            }
            if (intentListrik.isEmpty()) {
                TVListrikListing.setText(": -");
            } else {
                TVListrikListing.setText(": " + intentListrik + " Watt");
            }
            if (intentSumberAir.isEmpty()) {
                TVSumberAirListing.setText(": -");
            } else {
                TVSumberAirListing.setText(": " + intentSumberAir);
            }
            if (intentPrabot.equals("Tidak")) {
                TVPerabotListing.setText(": -");
            } else {
                TVPerabotListing.setText(": " + intentKetPrabot);
            }
            if (intentSize.isEmpty()) {
                TVBannerListing.setText(": -");
            } else {
                TVBannerListing.setText(": " + intentSize);
            }
            if (intentDeskripsi.isEmpty()) {
                TVDeskripsiListing.setText("-");
            } else {
                SpannableStringBuilder builder = new SpannableStringBuilder(intentDeskripsi);

                int startIndex = intentDeskripsi.indexOf("*");
                int endIndex = intentDeskripsi.lastIndexOf("*");

                if (startIndex >= 0 && endIndex >= 0 && startIndex < endIndex) {
                    builder.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), startIndex, endIndex + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    builder.delete(endIndex, endIndex + 1);
                    builder.delete(startIndex, startIndex + 1);
                }

                TVDeskripsiListing.setText(builder);
            }
            if (intentFee.isEmpty()) {
                TVFeeListing.setText(": -");
            } else {
                TVFeeListing.setText(": " + intentFee);
            }
            if (intentTglInput.isEmpty()) {
                TVTanggalInputListing.setText(": -");
            } else {
                TVTanggalInputListing.setText(": " + intentTglInput);
            }
            if (intentSelfie.equals("0") || intentSelfie.isEmpty()) {
                IVSelfieListing.setVisibility(View.GONE);
                TVSelfie.setVisibility(View.VISIBLE);
            } else {
                IVSelfieListing.setVisibility(View.VISIBLE);
                Glide.with(this).load(intentSelfie).into(IVSelfieListing);
                TVSelfie.setVisibility(View.GONE);
            }
            TVNamaAgen1.setText(intentNama);
            TVNamaAgen1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailPraListingRejectedActivity.this, DetailAgenListingActivity.class);
                    update.putExtra("update", 1);
                    update.putExtra("IdAgen", intentIdAgen);
                    startActivity(update);
                }
            });
            TVNamaAgen2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailPraListingRejectedActivity.this, DetailAgenListingActivity.class);
                    update.putExtra("update", 1);
                    update.putExtra("IdAgen", intentIdAgenCo);
                    startActivity(update);
                }
            });
            if (intentLatitude.equals("0") || intentLongitude.equals("0")) {
                lat = Double.parseDouble("0");
                lng = Double.parseDouble("0");
                mapView.setVisibility(View.GONE);
            } else {
                lat = Double.parseDouble(intentLatitude);
                lng = Double.parseDouble(intentLongitude);
            }
            IVInstagramAgen1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "http://instagram.com/_u/" + intentInstagram;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
            TVKeteranganRejected.setText(intentKeterangan);

            UrlSHM = intentImgSHM;
            UrlHGB = intentImgHGB;
            UrlHSHP = intentImgHSHP;
            UrlPPJB = intentImgPPJB;
            UrlStratatitle = intentImgStratatitle;
            UrlAJB = intentImgAJB;
            UrlPetokD = intentImgPetokD;

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
                VPSertifikat.setVisibility(View.GONE);
                TVNoDataSertifikat.setVisibility(View.VISIBLE);
            }

            if (intentImgPjp.equals("0") && intentImgPjp1.equals("0")) {
                VPPjp.setVisibility(View.GONE);
                TVNoDataPjp.setVisibility(View.VISIBLE);
            } else if (intentImgPjp1.equals("0")) {
                TVPjp.setText("Bukti Chat");
            }

            adapter = new ViewPagerAdapter(this, images);
            VPGambarListing.setPadding(0, 0, 0, 0);
            VPGambarListing.setAdapter(adapter);

            sertifikatPdfAdapter = new SertifikatPdfAdapter(this, sertifpdf);
            VPSertifikat.setAdapter(sertifikatPdfAdapter);

            pjpAdapter = new PJPAdapter(this, pjpimage);
            VPPjp.setPadding(0, 0, 0, 0);
            VPPjp.setAdapter(pjpAdapter);

            VPGambarListing.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
    private void updatePageInfo(int currentPage) {
        String info = (currentPage + 1) + "/" + images.size();
        TVJumlahGambar.setText(info);
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
    @Override
    public void onBackPressed() {
        if (adapter != null && adapter.getCurrentExoPlayer() != null && adapter.getCurrentExoPlayer().isPlaying()) {
            adapter.getCurrentExoPlayer().stop();
            super.onBackPressed();
        } else {
            super.onBackPressed();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_PJP1 && resultCode == RESULT_OK && null != data) {
            UriPjp1 = data.getData();
            ImageView imageView1 = CustomDialogPjp.findViewById(R.id.IVPjp1);
            imageView1.setImageURI(UriPjp1);
            imageView1.setTag(UriPjp1);
            imageView1.setVisibility(View.VISIBLE);
        } else if (requestCode == RESULT_LOAD_PJP2 && resultCode == RESULT_OK && data != null) {
            UriPjp2 = data.getData();
            ImageView imageView2 = CustomDialogPjp.findViewById(R.id.IVPjp2);
            imageView2.setImageURI(UriPjp2);
            imageView2.setTag(UriPjp2);
            imageView2.setVisibility(View.VISIBLE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    public void ShowTambahBanner() {
        customDialogBanner = new Dialog(DetailPraListingRejectedActivity.this);
        customDialogBanner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialogBanner.setContentView(R.layout.dialog_tambah_banner);

        if (customDialogBanner.getWindow() != null) {
            customDialogBanner.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextInputEditText Ukuran = customDialogBanner.findViewById(R.id.ETUkuranBanner);
        TextInputEditText UkuranCustom = customDialogBanner.findViewById(R.id.ETUkuranBannerCustom);

        TextInputLayout LytUkuran = customDialogBanner.findViewById(R.id.lytUkuranBanner);
        TextInputLayout LytUkuranCustom = customDialogBanner.findViewById(R.id.lytUkuranBannerCustom);

        Ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPraListingRejectedActivity.this, R.style.CustomAlertDialogStyle);
                builder.setTitle("Ukuran Banner");

                final CharSequence[] Banner = {"80 X 90", "100 X 125", "180 X 80", "Lainnya"};
                final int[] SelectedBanner = {0};

                builder.setSingleChoiceItems(Banner, SelectedBanner[0], new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == Banner.length - 1) {
                            LytUkuran.setVisibility(View.GONE);
                            LytUkuranCustom.setVisibility(View.VISIBLE);
                        } else {
                            Ukuran.setText(Banner[which]);
                        }
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("Batal", null);

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        Button Batal = customDialogBanner.findViewById(R.id.BtnBatal);
        Button Simpan = customDialogBanner.findViewById(R.id.BtnSimpan);

        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogBanner.dismiss();
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Ukuran.getText().toString().isEmpty() && UkuranCustom.getText().toString().isEmpty()) {
                    Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                    if (customDialog.getWindow() != null) {
                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    }

                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                    Button ok = customDialog.findViewById(R.id.btnya);
                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                    dialogTitle.setText("Harap Pilih Size Terlebih Dahulu");
                    cobalagi.setVisibility(View.GONE);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    Glide.with(DetailPraListingRejectedActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    if (!Ukuran.getText().toString().isEmpty()) {
                        pDialog.setMessage("Menyimpan Data");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingRejectedActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_BANNER_PRALISTING,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pDialog.cancel();
                                        try {
                                            JSONObject res = new JSONObject(response);
                                            String status = res.getString("Status");
                                            if (status.equals("Sukses")) {
                                                Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                                if (customDialog.getWindow() != null) {
                                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                }

                                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                                Button ok = customDialog.findViewById(R.id.btnya);
                                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                                dialogTitle.setText("Berhasil Menambahkan Banner Listingan");
                                                cobalagi.setVisibility(View.GONE);

                                                ok.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        customDialog.dismiss();
                                                        customDialogBanner.dismiss();
                                                    }
                                                });

                                                Glide.with(DetailPraListingRejectedActivity.this)
                                                        .load(R.mipmap.ic_yes)
                                                        .transition(DrawableTransitionOptions.withCrossFade())
                                                        .into(gifimage);

                                                customDialog.show();
                                            } else if (status.equals("Error")) {
                                                Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                                if (customDialog.getWindow() != null) {
                                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                }

                                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                                Button ok = customDialog.findViewById(R.id.btnya);
                                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                                dialogTitle.setText("Gagal Menambahkan Banner Listingan");
                                                ok.setVisibility(View.GONE);

                                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        customDialog.dismiss();
                                                    }
                                                });

                                                Glide.with(DetailPraListingRejectedActivity.this)
                                                        .load(R.mipmap.ic_no)
                                                        .transition(DrawableTransitionOptions.withCrossFade())
                                                        .into(gifimage);

                                                customDialog.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        pDialog.cancel();
                                        Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                                        if (customDialog.getWindow() != null) {
                                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        }

                                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                        Button ok = customDialog.findViewById(R.id.btnya);
                                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                        dialogTitle.setText("Terdapat Masalah Jaringan");
                                        ok.setVisibility(View.GONE);

                                        cobalagi.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                customDialog.dismiss();
                                            }
                                        });

                                        Glide.with(DetailPraListingRejectedActivity.this)
                                                .load(R.mipmap.ic_eror_network_foreground)
                                                .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(gifimage);

                                        customDialog.show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("IdPraListing", intentIdPraListing);
                                map.put("Banner", "Ya");
                                map.put("Size", Ukuran.getText().toString());
                                System.out.println(map);
                                return map;
                            }
                        };

                        requestQueue.add(stringRequest);
                    } else {
                        pDialog.setMessage("Menyimpan Data");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingRejectedActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_BANNER_PRALISTING,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pDialog.cancel();
                                        try {
                                            JSONObject res = new JSONObject(response);
                                            String status = res.getString("Status");
                                            if (status.equals("Sukses")) {
                                                Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                                if (customDialog.getWindow() != null) {
                                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                }

                                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                                Button ok = customDialog.findViewById(R.id.btnya);
                                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                                dialogTitle.setText("Berhasil Menambahkan Banner Listingan");
                                                cobalagi.setVisibility(View.GONE);

                                                ok.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        customDialog.dismiss();
                                                        customDialogBanner.dismiss();
                                                    }
                                                });

                                                Glide.with(DetailPraListingRejectedActivity.this)
                                                        .load(R.mipmap.ic_yes)
                                                        .transition(DrawableTransitionOptions.withCrossFade())
                                                        .into(gifimage);

                                                customDialog.show();
                                            } else if (status.equals("Error")) {
                                                Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                                if (customDialog.getWindow() != null) {
                                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                }

                                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                                Button ok = customDialog.findViewById(R.id.btnya);
                                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                                dialogTitle.setText("Gagal Menambahkan Banner Listingan");
                                                ok.setVisibility(View.GONE);

                                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        customDialog.dismiss();
                                                    }
                                                });

                                                Glide.with(DetailPraListingRejectedActivity.this)
                                                        .load(R.mipmap.ic_no)
                                                        .transition(DrawableTransitionOptions.withCrossFade())
                                                        .into(gifimage);

                                                customDialog.show();
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        pDialog.cancel();
                                        Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                                        if (customDialog.getWindow() != null) {
                                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        }

                                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                        Button ok = customDialog.findViewById(R.id.btnya);
                                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                        dialogTitle.setText("Terdapat Masalah Jaringan");
                                        ok.setVisibility(View.GONE);

                                        cobalagi.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                customDialog.dismiss();
                                            }
                                        });

                                        Glide.with(DetailPraListingRejectedActivity.this)
                                                .load(R.mipmap.ic_eror_network_foreground)
                                                .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(gifimage);

                                        customDialog.show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("IdPraListing", intentIdPraListing);
                                map.put("Banner", "Ya");
                                map.put("Size", Ukuran.getText().toString());
                                System.out.println(map);

                                return map;
                            }
                        };

                        requestQueue.add(stringRequest);
                    }
                }
            }
        });

        customDialogBanner.show();
    }
    public void ShowTambahCoList() {
        customDialogCoList = new Dialog(DetailPraListingRejectedActivity.this);
        customDialogCoList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialogCoList.setContentView(R.layout.dialog_tambah_colist);

        if (customDialogCoList.getWindow() != null) {
            customDialogCoList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextInputEditText ETNamaAgen = customDialogCoList.findViewById(R.id.ETNamaAgen);
        TextInputEditText ETIdAgen = customDialogCoList.findViewById(R.id.ETIdAgen);

        TextInputLayout LytDaftarAgen = customDialogCoList.findViewById(R.id.lytDaftarAgen);

        ETIdAgen.setVisibility(View.GONE);
        ETNamaAgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agenManager.fetchDataFromApi(DetailPraListingRejectedActivity.this, new AgenManager.ApiCallback() {
                    @Override
                    public void onSuccess(List<AgenManager.DataItem> dataList) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPraListingRejectedActivity.this, R.style.CustomAlertDialogStyle);
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
                                ETIdAgen.setText(selectedData.getId());
                                ETNamaAgen.setText(selectedData.getName());
                                ETIdAgen.setVisibility(View.GONE);
                            }
                        });

                        builder.setPositiveButton("OK", null);
                        builder.show();
                        showAlertDialog(dataList);
                    }

                    @Override
                    public void onError(String errorMessage) {
                    }
                });
            }
        });

        Button Batal = customDialogCoList.findViewById(R.id.BtnBatal);
        Button Simpan = customDialogCoList.findViewById(R.id.BtnSimpan);

        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialogCoList.dismiss();
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ETIdAgen.getText().toString().isEmpty()) {
                    Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                    if (customDialog.getWindow() != null) {
                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    }

                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                    Button ok = customDialog.findViewById(R.id.btnya);
                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                    dialogTitle.setText("Harap Pilih Agen Terlebih Dahulu");
                    cobalagi.setVisibility(View.GONE);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    Glide.with(DetailPraListingRejectedActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    pDialog.setMessage("Menyimpan Data");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingRejectedActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_COLIST_PRALISTING,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pDialog.cancel();
                                    try {
                                        JSONObject res = new JSONObject(response);
                                        String status = res.getString("Status");
                                        if (status.equals("Sukses")) {
                                            Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                                            if (customDialog.getWindow() != null) {
                                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            }

                                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                            Button ok = customDialog.findViewById(R.id.btnya);
                                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                            dialogTitle.setText("Berhasil Menambahkan Agen CoList");
                                            cobalagi.setVisibility(View.GONE);

                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    customDialog.dismiss();
                                                    customDialogCoList.dismiss();
                                                }
                                            });

                                            Glide.with(DetailPraListingRejectedActivity.this)
                                                    .load(R.mipmap.ic_yes)
                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                    .into(gifimage);

                                            customDialog.show();
                                        } else if (status.equals("Error")) {
                                            Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                                            if (customDialog.getWindow() != null) {
                                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            }

                                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                            Button ok = customDialog.findViewById(R.id.btnya);
                                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                            dialogTitle.setText("Gagal Menambahkan Agen CoList");
                                            ok.setVisibility(View.GONE);

                                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    customDialog.dismiss();
                                                }
                                            });

                                            Glide.with(DetailPraListingRejectedActivity.this)
                                                    .load(R.mipmap.ic_no)
                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                    .into(gifimage);

                                            customDialog.show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    pDialog.cancel();
                                    Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                    if (customDialog.getWindow() != null) {
                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                    }

                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                    Button ok = customDialog.findViewById(R.id.btnya);
                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                    dialogTitle.setText("Terdapat Masalah Jaringan");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                        }
                                    });

                                    Glide.with(DetailPraListingRejectedActivity.this)
                                            .load(R.mipmap.ic_eror_network_foreground)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();
                            map.put("IdPraListing", intentIdPraListing);
                            map.put("IdAgenCo", ETIdAgen.getText().toString());
                            System.out.println(map);

                            return map;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
            }
        });

        customDialogCoList.show();
    }
    public void ShowTambahPjp() {
        CustomDialogPjp = new Dialog(DetailPraListingRejectedActivity.this);
        CustomDialogPjp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        CustomDialogPjp.setContentView(R.layout.dialog_tambah_pjp);

        if (CustomDialogPjp.getWindow() != null) {
            CustomDialogPjp.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        Button BtnPjp1 = CustomDialogPjp.findViewById(R.id.BtnUploadPjpHal1);
        Button BtnPjp2 = CustomDialogPjp.findViewById(R.id.BtnUploadPjpHal2);
        Button Batal = CustomDialogPjp.findViewById(R.id.BtnBatal);
        Button Simpan = CustomDialogPjp.findViewById(R.id.BtnSimpan);

        ImageView IVPjpHal1 = CustomDialogPjp.findViewById(R.id.IVPjp1);
        ImageView IVPjpHal2 = CustomDialogPjp.findViewById(R.id.IVPjp2);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String filePjp1 = "PJP1_" + timeStamp + ".jpg";
        String filePjp2 = "PJP2_" + timeStamp + ".jpg";

        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference ImgPjp = storageRef.child("pjp/" + filePjp1);
        StorageReference ImgPjp1 = storageRef.child("pjp/" + filePjp2);

        BtnPjp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_PJP1);
            }
        });

        BtnPjp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_LOAD_PJP2);
            }
        });

        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogPjp.dismiss();
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UriPjp1 == null || UriPjp2 == null) {
                    Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                    if (customDialog.getWindow() != null) {
                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    }

                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                    Button ok = customDialog.findViewById(R.id.btnya);
                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                    dialogTitle.setText("Harap Upload PJP");
                    cobalagi.setVisibility(View.GONE);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    Glide.with(DetailPraListingRejectedActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    pDialog.setMessage("Menyimpan Data");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    ImgPjp.putFile(UriPjp1)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    ImgPjp.getDownloadUrl()
                                            .addOnSuccessListener(uri -> {
                                                String imageUrl = uri.toString();
                                                PJPHal1 = imageUrl;

                                                ImgPjp1.putFile(UriPjp2)
                                                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                                            @Override
                                                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                                                ImgPjp1.getDownloadUrl()
                                                                        .addOnSuccessListener(uri -> {
                                                                            String imageUrl = uri.toString();
                                                                            PJPHal2 = imageUrl;

                                                                            RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingRejectedActivity.this);

                                                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_PJP_PRALISTING,
                                                                                    new Response.Listener<String>() {
                                                                                        @Override
                                                                                        public void onResponse(String response) {
                                                                                            pDialog.cancel();
                                                                                            try {
                                                                                                JSONObject res = new JSONObject(response);
                                                                                                String status = res.getString("Status");
                                                                                                if (status.equals("Sukses")) {
                                                                                                    Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                                                                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                                                                                    if (customDialog.getWindow() != null) {
                                                                                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                                                                    }

                                                                                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                                                                                    Button ok = customDialog.findViewById(R.id.btnya);
                                                                                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                                                                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                                                                                    dialogTitle.setText("Berhasil Menambahkan PJP");
                                                                                                    cobalagi.setVisibility(View.GONE);

                                                                                                    ok.setOnClickListener(new View.OnClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(View view) {
                                                                                                            customDialog.dismiss();
                                                                                                            CustomDialogPjp.dismiss();
                                                                                                        }
                                                                                                    });

                                                                                                    Glide.with(DetailPraListingRejectedActivity.this)
                                                                                                            .load(R.mipmap.ic_yes)
                                                                                                            .transition(DrawableTransitionOptions.withCrossFade())
                                                                                                            .into(gifimage);

                                                                                                    customDialog.show();
                                                                                                } else if (status.equals("Error")) {
                                                                                                    Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                                                                                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                                                    customDialog.setContentView(R.layout.custom_dialog_sukses);

                                                                                                    if (customDialog.getWindow() != null) {
                                                                                                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                                                                    }

                                                                                                    TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                                                                                    Button ok = customDialog.findViewById(R.id.btnya);
                                                                                                    Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                                                                                    ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                                                                                    dialogTitle.setText("Gagal Menambahkan PJP");
                                                                                                    ok.setVisibility(View.GONE);

                                                                                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(View view) {
                                                                                                            customDialog.dismiss();
                                                                                                        }
                                                                                                    });

                                                                                                    Glide.with(DetailPraListingRejectedActivity.this)
                                                                                                            .load(R.mipmap.ic_no)
                                                                                                            .transition(DrawableTransitionOptions.withCrossFade())
                                                                                                            .into(gifimage);

                                                                                                    customDialog.show();
                                                                                                }
                                                                                            } catch (JSONException e) {
                                                                                                e.printStackTrace();
                                                                                            }
                                                                                        }
                                                                                    },
                                                                                    new Response.ErrorListener() {
                                                                                        @Override
                                                                                        public void onErrorResponse(VolleyError error) {
                                                                                            pDialog.cancel();
                                                                                            Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                                                                                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                                                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                                                                                            if (customDialog.getWindow() != null) {
                                                                                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                                                                            }

                                                                                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                                                                            Button ok = customDialog.findViewById(R.id.btnya);
                                                                                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                                                                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                                                                            dialogTitle.setText("Terdapat Masalah Jaringan");
                                                                                            ok.setVisibility(View.GONE);

                                                                                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View view) {
                                                                                                    customDialog.dismiss();
                                                                                                }
                                                                                            });

                                                                                            Glide.with(DetailPraListingRejectedActivity.this)
                                                                                                    .load(R.mipmap.ic_eror_network_foreground)
                                                                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                                                                    .into(gifimage);

                                                                                            customDialog.show();
                                                                                        }
                                                                                    }) {
                                                                                @Override
                                                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                                                    Map<String, String> map = new HashMap<>();
                                                                                    map.put("IdPraListing", intentIdPraListing);
                                                                                    map.put("ImgPjp", PJPHal1);
                                                                                    map.put("ImgPjp1", PJPHal2);
                                                                                    System.out.println(map);

                                                                                    return map;
                                                                                }
                                                                            };

                                                                            requestQueue.add(stringRequest);

                                                                        })
                                                                        .addOnFailureListener(exception -> {
                                                                            pDialog.cancel();
                                                                            Toast.makeText(DetailPraListingRejectedActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        });
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                pDialog.cancel();
                                                                Toast.makeText(DetailPraListingRejectedActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            })
                                            .addOnFailureListener(exception -> {
                                                pDialog.cancel();
                                                Toast.makeText(DetailPraListingRejectedActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailPraListingRejectedActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        CustomDialogPjp.show();
    }
    private void LoadCo() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_CO_LISTING + intentIdAgenCo, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String NamaCo = data.getString("Nama");
                                String TelpCo = data.getString("NoTelp");
                                String IGCo = data.getString("Instagram");

                                TVNamaAgen2.setText(NamaCo);
                                IVInstagramAgen2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String url = "http://instagram.com/_u/" + IGCo;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                    }
                                });
                                if (StrStatus.equals("1")) {
                                    IVTelpAgen2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya Manager, ingin menanyakan pralisting " + intentNamaListing + " yang beralamat di " + intentAlamat + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (StrStatus.equals("2")) {
                                    IVTelpAgen2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya Admin, ingin menanyakan pralisting " + intentNamaListing + " yang beralamat di " + intentNamaListing + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (StrStatus.equals("3")) {
                                    IVTelpAgen2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya ingin menanyakan pralisting " + intentNamaListing + " yang beralamat di " + intentNamaListing + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else {
                                    IVTelpAgen2.setVisibility(View.INVISIBLE);
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
                StrIdAgenCoList = selectedData.getId();
            }
        });

        builder.setPositiveButton("OK", null);
        builder.show();
    }
    private void ajukanulang() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_AJUKAN_ULANG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Listing di Ajukan Ulang");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    ArrayList<String> tokens = new ArrayList<>();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject tokenObject = response.getJSONObject(i);
                                        String token = tokenObject.getString("Token");
                                        tokens.add(token);
                                    }
                                    new SendMessageTaskAjukanUlang().execute(tokens.toArray(new String[0]));
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

                Glide.with(DetailPraListingRejectedActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingRejectedActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Gagal Ajukan Ulang Listing");
                ok.setVisibility(View.GONE);

                Glide.with(DetailPraListingRejectedActivity.this)
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
                map.put("IdPraListing", intentIdPraListing);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void tampilkanPreview(View view) {
        Uri imageUri = getImageUriFromImageView((ImageView) view);
        tampilkanDialogGambarBesar(imageUri);
    }
    private Uri getImageUriFromImageView(ImageView imageView) {
        return (Uri) imageView.getTag();
    }
    private void tampilkanDialogGambarBesar(Uri imageUri) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.preview_gambar, null);
        PhotoView imageView = dialogView.findViewById(R.id.imageViewPreview);
        imageView.setImageURI(imageUri);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();
        dialog.show();
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
    private class SendMessageTaskAjukanUlang extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToTokenAjukanUlang(token, "pralisting");
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
    private void sendNotificationToTokenAjukanUlang(String token, String notificationType) {
        String title = Preferences.getKeyNama(this);
        String message = "Pengajuan Ulang Listing";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}