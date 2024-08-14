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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.adapter.listing.PraListingAdapter;
import com.gooproper.adapter.listing.PraListingTerdekatAdapter;
import com.gooproper.guest.MainGuestActivity;
import com.gooproper.model.ListingModel;
import com.gooproper.model.PraListingModel;
import com.gooproper.model.PraListingTerdekatModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.ImageViewActivity;
import com.gooproper.ui.detail.agen.DetailAgenListingActivity;
import com.gooproper.ui.detail.listing.DetailListingActivity;
import com.gooproper.ui.edit.EditListingActivity;
import com.gooproper.ui.edit.EditListingAgenActivity;
import com.gooproper.ui.edit.EditPraListingAgenActivity;
import com.gooproper.ui.edit.EditPralistingActivity;
import com.gooproper.ui.edit.listing.TambahDataGambarListingActivity;
import com.gooproper.ui.edit.listing.TambahDataLokasiListingActivity;
import com.gooproper.ui.edit.listing.TambahDataSelfieListingActivity;
import com.gooproper.ui.edit.pralisting.EditDataPralistingActivity;
import com.gooproper.ui.edit.pralisting.TambahDataGambarPralistingActivity;
import com.gooproper.ui.edit.pralisting.TambahDataLokasiPralistingActivity;
import com.gooproper.ui.edit.pralisting.TambahDataSelfiePralistingActivity;
import com.gooproper.ui.tambah.TambahSelfieActivity;
import com.gooproper.ui.tambah.template.TambahTemplateActivity;
import com.gooproper.ui.user.SettingActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailPraListingActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog PDDetailListing;
    TextView TVJumlahGambar, TVKeteranganSurvey, TVRangeHarga, TVNamaDetailListing, TVAlamatDetailListing, TVHargaDetailListing, TVHargaSewaDetailListing, TVBedDetailListing, TVNamaAgen, TVNamaAgen2, TVBathDetailListing, TVWideDetailListing, TVLandDetailListing, TVDimensiDetailListing, TVTipeDetailListing, TVStatusDetailListing, TVSertifikatDetailListing, TVLuasDetailListing, TVKamarTidurDetailListing, TVKamarMandiDetailListing, TVLantaiDetailListing, TVGarasiDetailListing, TVCarpotDetailListing, TVListrikDetailListing, TVSumberAirDetailListing, TVPerabotDetailListing, TVSizeBanner, TVDeskripsiDetailListing, TVNoData, TVNoDataPdf, TVPriority, TVKondisi, TVNoPjp, TVNoDataPjp, TVFee, TVTglInput, TVNamaVendor, TVTelpVendor, TVPJP, TVSelfie, TVRejected, TVPoin, TVHadap;
    ImageView IVFlowUp, IVWhatsapp, IVInstagram, IVFlowUp2, IVWhatsapp2, IVInstagram2, IVStar1, IVStar2, IVStar3, IVStar4, IVStar5, IVAlamat, IVSelfie;
    Button BtnApproveAdmin, BtnApproveManager, BtnRejectedAdmin, BtnRejectedManager;
    Button BtnTambahMaps, BtnTambahSelfie, BtnTambahBanner, BtnTambahPjp, BtnTambahCoList, BtnTambahGambar, BtnTambahWilayah;
    Button BtnLihatTemplate, BtnLihatTemplateKosong, BtnUploadTemplate;
    Button BtnTidakMarketable, BtnTidakStatusHarga;
    TextInputEditText tambahagen, tambahcoagen, tambahpjp;
    TextInputLayout lytambahagen, lyttambahcoagen, lyttambahpjp;
    CheckBox CBMarketable, CBHarga, CBSelfie, CBLokasi;
    LinearLayout LytCBMarketable, LytCBHarga, LytCBSelfie, LytCBLokasi;
    ScrollView scrollView;
    CardView agen, agen2;
    String idagen, status, idpralisting, agenid, agencoid, idpengguna, StringNamaListing, StringLuasTanah, StringLuasBangunan, StringKamarTidur, StringKamarTidurArt, StringKamarMandiArt, StringKamarMandi, StringListrik, StringHarga, StringHargaSewa, StringSertifikat, StringAlamat;
    String BuyerNama, BuyerTelp, BuyerKeterangan, BuyerTanggal, BuyerIdAgen, BuyerIdInput, BuyerJam, StringNamaBuyer, AgenId, StringKeteranganReject;
    String NamaMaps;
    String imageUrl, namaAgen, telpAgen, UrlSHM, UrlHGB, UrlHSHP, UrlPPJB, UrlStratatitle, UrlAJB, UrlPetokD;
    String StrIdAgen;
    String intentIdPraListing,intentIdAgen,intentIdAgenCo,intentIdInput,intentNamaListing,intentAlamat,intentAlamatTemplate,intentLatitude,intentLongitude,intentLocation,intentWilayah,intentSelfie,intentWide,intentLand,intentDimensi,intentListrik,intentLevel,intentBed,intentBedArt,intentBath,intentBathArt,intentGarage,intentCarpot,intentHadap,intentSHM,intentHGB,intentHSHP,intentPPJB,intentStratatitle,intentAJB,intentPetokD,intentPjp,intentImgSHM,intentImgHGB,intentImgHSHP,intentImgPPJB,intentImgStratatitle,intentImgAJB,intentImgPetokD,intentImgPjp,intentImgPjp1, intentNoCertificate,intentPbb,intentJenisProperti,intentJenisCertificate,intentSumberAir,intentKondisi,intentDeskripsi,intentPrabot,intentKetPrabot,intentPriority,intentTtd,intentBanner,intentSize,intentHarga,intentHargaSewa,intentRangeHarga,intentTglInput,intentImg1,intentImg2,intentImg3,intentImg4,intentImg5, intentImg6,intentImg7,intentImg8,intentImg9,intentImg10,intentImg11,intentImg12,intentVideo,intentLinkFacebook,intentLinkTiktok,intentLinkInstagram,intentLinkYoutube,intentIsAdmin,intentIsManager,intentIsRejected,intentSold,intentRented,intentSoldAgen,intentRentedAgen,intentView,intentMarketable,intentStatusHarga,intentNama,intentNoTelp,intentInstagram,intentFee,intentNamaVendor,intentNoTelpVendor,intentIsSelfie,intentIsLokasi,intentKeterangan;
    int Poin, FinalPoin, CoPoin;
    ProgressDialog pDialog;
    ListingModel lm;
    LinearLayout LytSertifikat, LytPJP, LytSize, LytFee, LytTglInput, LytBadge, IVEdit, LytNamaVendor, LytTelpVendor, LytRejected, LytSelfie, LytSurvey;
    RecyclerView recycleListingTerkait;
    RecyclerView.Adapter AdapterTerkait;
    RecyclerView.Adapter AdapterTerdekat;
    ViewPager viewPager, viewPagerSertifikat, viewPagerPJP;
    ViewPagerAdapter adapter;
    SertifikatAdapter sertifikatAdapter;
    SertifikatPdfAdapter sertifikatPdfAdapter;
    PJPAdapter pjpAdapter;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> sertif = new ArrayList<>();
    ArrayList<String> sertifpdf = new ArrayList<>();
    ArrayList<String> pjpimage = new ArrayList<>();
    List<PraListingTerdekatModel> nearestLocations = new ArrayList<>();
    List<PraListingTerdekatModel> mItemsTerkait = new ArrayList<>();
    Uri UriPjp1, UriPjp2;
    String PJPHal1, PJPHal2;
    private String[] dataOptions;
    private int selectedOption = -1;
    private AgenManager agenManager, agenCoManager;
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    private Dialog CustomDialogPjp, customDialogBanner, customDialogCoList;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static final int RESULT_LOAD_PJP1 = 1;
    private static final int RESULT_LOAD_PJP2 = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pralisting);

        PDDetailListing = new ProgressDialog(DetailPraListingActivity.this);
        pDialog = new ProgressDialog(this);

        tambahagen = findViewById(R.id.ETTambahAgenDetailListing);
        tambahcoagen = findViewById(R.id.ETTambahCoAgenDetailListing);
        tambahpjp = findViewById(R.id.ETTambahNoPjpDetailListing);
        viewPager = findViewById(R.id.VPDetailListing);
        viewPagerSertifikat = findViewById(R.id.VPSertifikatDetailListing);
        viewPagerPJP = findViewById(R.id.VPPJPDetailListing);
        agen = findViewById(R.id.LytAgenDetailListing);
        agen2 = findViewById(R.id.LytAgen2DetailListing);
        lytambahagen = findViewById(R.id.LytTambahAgenDetailListing);
        lyttambahcoagen = findViewById(R.id.LytTambahCoAgenDetailListing);
        lyttambahpjp = findViewById(R.id.LytTambahNoPjpDetailListing);
        LytSertifikat = findViewById(R.id.LytSertifikat);
        LytPJP = findViewById(R.id.LytViewPjp);
        LytSize = findViewById(R.id.LytUkuranBannerDetailListing);
        LytFee = findViewById(R.id.LytFeeDetailListing);
        LytTglInput = findViewById(R.id.LytTglInputDetailListing);
        LytBadge = findViewById(R.id.LytBadge);
        LytNamaVendor = findViewById(R.id.LytNamaVendorDetailListing);
        LytTelpVendor = findViewById(R.id.LytTelpVendorDetailListing);
        LytRejected = findViewById(R.id.LytRejectedDetailListing);
        LytSelfie = findViewById(R.id.LytViewSelfie);
        LytSurvey = findViewById(R.id.LytSurveyDetailListing);
        scrollView = findViewById(R.id.SVDetailListing);

        BtnApproveAdmin = findViewById(R.id.BtnApproveAdminDetailListing);
        BtnApproveManager = findViewById(R.id.BtnApproveManagerDetailListing);
        BtnRejectedAdmin = findViewById(R.id.BtnRejectedAdminDetailListing);
        BtnRejectedManager = findViewById(R.id.BtnRejectedManagerDetailListing);
        BtnTambahMaps = findViewById(R.id.BtnAddMapsDetailListing);
        BtnTambahSelfie = findViewById(R.id.BtnAddSelfieDetailListing);
        BtnTambahBanner = findViewById(R.id.BtnAddBannerDetailListing);
        BtnTambahPjp = findViewById(R.id.BtnAddPjpDetailListing);
        BtnTambahCoList = findViewById(R.id.BtnColistDetailListing);
        BtnTambahGambar = findViewById(R.id.BtnTambahGambarDetailListing);
        BtnTambahWilayah = findViewById(R.id.BtnTambahWilayahDetailListing);
        BtnLihatTemplate = findViewById(R.id.BtnLihatTemplate);
        BtnLihatTemplateKosong = findViewById(R.id.BtnLihatTemplateKosong);
        BtnUploadTemplate = findViewById(R.id.BtnUploadTemplate);
        BtnTidakMarketable = findViewById(R.id.BtnTidakMarketable);
        BtnTidakStatusHarga = findViewById(R.id.BtnTidakStatusHarga);

        TVNamaDetailListing = findViewById(R.id.TVNamaDetailListing);
        TVAlamatDetailListing = findViewById(R.id.TVAlamatDetailListing);
        TVRangeHarga = findViewById(R.id.TVRangeHargaDetailListing);
        TVHargaDetailListing = findViewById(R.id.TVHargaDetailListing);
        TVHargaSewaDetailListing = findViewById(R.id.TVHargaSewaDetailListing);
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
        TVNamaAgen = findViewById(R.id.TVNamaAgenDetailListing);
        TVNamaAgen2 = findViewById(R.id.TVNamaAgen2DetailListing);
        TVNoData = findViewById(R.id.TVNoData);
        TVNoDataPjp = findViewById(R.id.TVNoDataPjp);
        TVPriority = findViewById(R.id.TVPriority);
        TVKondisi = findViewById(R.id.TVKondisi);
        TVNoPjp = findViewById(R.id.TVNoPjp);
        TVFee = findViewById(R.id.TVFeeDetailListing);
        TVTglInput = findViewById(R.id.TVTglInputDetailListing);
        TVNamaVendor = findViewById(R.id.TVNamaVendorDetailListing);
        TVTelpVendor = findViewById(R.id.TVTelpVendorDetailListing);
        TVPJP = findViewById(R.id.TVPjp);
        TVSelfie = findViewById(R.id.TVNoSelfie);
        TVRejected = findViewById(R.id.TVKeteranganDetailListing);
        TVKeteranganSurvey = findViewById(R.id.TVKeteranganSurveyDetailListing);
        TVPoin = findViewById(R.id.TVPoinListing);
        TVJumlahGambar = findViewById(R.id.TVJumlahGambar);

        IVAlamat = findViewById(R.id.IVAlamatDetailListing);
        IVFlowUp = findViewById(R.id.IVFlowUpAgenDetailListing);
        IVWhatsapp = findViewById(R.id.IVNoTelpAgenDetailListing);
        IVInstagram = findViewById(R.id.IVInstagramAgenDetailListing);
        IVFlowUp2 = findViewById(R.id.IVFlowUpAgen2DetailListing);
        IVWhatsapp2 = findViewById(R.id.IVNoTelpAgen2DetailListing);
        IVInstagram2 = findViewById(R.id.IVInstagramAgen2DetailListing);
        IVEdit = findViewById(R.id.IVEditDetailListing);
        IVSelfie = findViewById(R.id.IVSelfie);

        CBMarketable = findViewById(R.id.CBMarketable);
        CBHarga = findViewById(R.id.CBHarga);
        CBSelfie = findViewById(R.id.CBSelfie);
        CBLokasi = findViewById(R.id.CBLokasi);
        LytCBMarketable = findViewById(R.id.LytCBMarketable);
        LytCBHarga = findViewById(R.id.LytCBHarga);
        LytCBSelfie = findViewById(R.id.LytCBSelfie);
        LytCBLokasi = findViewById(R.id.LytCBLokasi);

        recycleListingTerkait = findViewById(R.id.ListingSerupa);

        mapView = findViewById(R.id.MVDetailListing);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        agenManager = new AgenManager();
        agenCoManager = new AgenManager();

        recycleListingTerkait.setLayoutManager(new LinearLayoutManager(DetailPraListingActivity.this, LinearLayoutManager.HORIZONTAL, false));
        AdapterTerkait = new PraListingTerdekatAdapter(DetailPraListingActivity.this, mItemsTerkait);
        recycleListingTerkait.setAdapter(AdapterTerkait);

        recycleListingTerkait.setLayoutManager(new LinearLayoutManager(DetailPraListingActivity.this, LinearLayoutManager.HORIZONTAL, false));
        AdapterTerdekat = new PraListingTerdekatAdapter(this, nearestLocations);
        recycleListingTerkait.setAdapter(AdapterTerdekat);

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

        status = Preferences.getKeyStatus(this);
        StrIdAgen = Preferences.getKeyIdAgen(this);

        idpralisting = intentIdPraListing;
        NamaMaps = intentNamaListing;
        BuyerIdAgen = intentIdAgen;
        imageUrl = intentImg2;
        namaAgen = intentNama;
        telpAgen = intentNoTelp;
        StringNamaListing = intentNamaListing;
        StringAlamat = intentAlamat;
        StringLuasTanah = intentLand;
        StringLuasBangunan = intentWide;
        StringKamarTidur = intentBed;
        StringKamarTidurArt = intentBedArt;
        StringKamarMandi = intentBath;
        StringKamarMandiArt = intentBathArt;
        StringListrik = intentListrik;
        StringSertifikat = intentJenisCertificate;

        RequestQueue queueSurvey = Volley.newRequestQueue(this);
        JsonArrayRequest reqDataSurvey = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_SURVEYOR_PRALISTING + intentIdPraListing,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDDetailListing.cancel();
                        PDDetailListing.dismiss();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String IntentIdPralisting = data.getString("IdPraListing");
                                String IntentSurveyor = data.getString("Surveyor");
                                String IntentKeterangan = data.getString("Keterangan");
                                String IntentNamaOfficer = data.getString("NamaTemp");

                                TVKeteranganSurvey.setText(IntentNamaOfficer+"\n"+IntentKeterangan);
                            } catch (JSONException e) {
                                PDDetailListing.dismiss();
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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
                        PDDetailListing.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

        queueSurvey.add(reqDataSurvey);

        double currentLatitude = Double.parseDouble(intentLatitude);
        double currentLongitude = Double.parseDouble(intentLongitude);

        if (!intentLongitude.equals("0") && !intentLatitude.equals("0")) {
            fetchLocationsFromAPI(currentLatitude, currentLongitude);
        } else if (intentWilayah.isEmpty()) {
            LoadPraListingTerdekat();
        } else {
            LoadPraListingTerkait();
        }

        if (intentKondisi.equals("Jual")) {
            StringHarga = currency.formatRupiah(intentHarga);
        } else if (intentKondisi.equals("Sewa")) {
            StringHargaSewa = currency.formatRupiah(intentHargaSewa);
        } else {
            StringHarga = currency.formatRupiah(intentHarga);
            StringHargaSewa = currency.formatRupiah(intentHargaSewa);
        }

        if (status.equals("1")) {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            LytSertifikat.setVisibility(View.VISIBLE);
            LytPJP.setVisibility(View.VISIBLE);
            LytNamaVendor.setVisibility(View.VISIBLE);
            LytTelpVendor.setVisibility(View.VISIBLE);
            IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = "Halo! Saya Manager, ingin menanyakan pralisting " + intentNamaListing + " yang beralamat di " + intentAlamat + ".";
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
            LytNamaVendor.setVisibility(View.VISIBLE);
            LytTelpVendor.setVisibility(View.VISIBLE);
            IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = "Halo! Saya Admin, ingin menanyakan update pralisting " + intentNamaListing + " yang beralamat di " + intentAlamat + ".";
                    String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });
        } else if (status.equals("3")) {
            StringNamaBuyer = Preferences.getKeyNama(this);
            LytSize.setVisibility(View.GONE);
            LytFee.setVisibility(View.GONE);
            LytTglInput.setVisibility(View.GONE);
            LytNamaVendor.setVisibility(View.GONE);
            LytTelpVendor.setVisibility(View.GONE);
            lytambahagen.setVisibility(View.GONE);
            LytSelfie.setVisibility(View.GONE);
            IVWhatsapp.setVisibility(View.INVISIBLE);
        } else {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            IVFlowUp.setVisibility(View.INVISIBLE);
            LytSize.setVisibility(View.GONE);
            LytFee.setVisibility(View.GONE);
            LytTglInput.setVisibility(View.GONE);
            LytNamaVendor.setVisibility(View.GONE);
            LytTelpVendor.setVisibility(View.GONE);
            lytambahagen.setVisibility(View.GONE);
            LytSelfie.setVisibility(View.GONE);
            IVWhatsapp.setVisibility(View.INVISIBLE);
        }

        if (status.equals("1")) {
            BtnApproveAdmin.setVisibility(View.GONE);
            BtnApproveManager.setVisibility(View.VISIBLE);
            BtnRejectedAdmin.setVisibility(View.GONE);
            BtnRejectedManager.setVisibility(View.VISIBLE);
            BtnLihatTemplate.setVisibility(View.GONE);
            BtnLihatTemplateKosong.setVisibility(View.GONE);
            BtnUploadTemplate.setVisibility(View.GONE);
            BtnTidakMarketable.setVisibility(View.VISIBLE);
            BtnTidakStatusHarga.setVisibility(View.VISIBLE);
            BtnTambahMaps.setVisibility(View.GONE);
            BtnTambahSelfie.setVisibility(View.GONE);
            IVFlowUp.setVisibility(View.INVISIBLE);
            IVFlowUp2.setVisibility(View.INVISIBLE);
            IVEdit.setVisibility(View.VISIBLE);
            AgenId = "0";
            idpengguna = Preferences.getKeyIdAdmin(this);
        } else if (status.equals("2")) {
            BtnApproveAdmin.setVisibility(View.VISIBLE);
            BtnApproveManager.setVisibility(View.GONE);
            BtnRejectedAdmin.setVisibility(View.VISIBLE);
            BtnRejectedManager.setVisibility(View.GONE);
            BtnTambahMaps.setVisibility(View.GONE);
            BtnTambahSelfie.setVisibility(View.GONE);
            BtnTidakMarketable.setVisibility(View.VISIBLE);
            BtnTidakStatusHarga.setVisibility(View.VISIBLE);
            IVFlowUp.setVisibility(View.INVISIBLE);
            IVFlowUp2.setVisibility(View.INVISIBLE);
            IVEdit.setVisibility(View.VISIBLE);
            IVEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailPraListingActivity.this, EditDataPralistingActivity.class);
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
            AgenId = "0";
            idpengguna = Preferences.getKeyIdAdmin(this);
            if (!intentImgPjp.equals("0")) {
                lyttambahpjp.setVisibility(View.VISIBLE);
            }
        } else if (status.equals("3")) {
            TVAlamatDetailListing.setVisibility(View.GONE);
            IVAlamat.setVisibility(View.GONE);
            BtnApproveAdmin.setVisibility(View.GONE);
            BtnApproveManager.setVisibility(View.GONE);
            BtnRejectedAdmin.setVisibility(View.GONE);
            BtnRejectedManager.setVisibility(View.GONE);
            BtnLihatTemplate.setVisibility(View.GONE);
            BtnLihatTemplateKosong.setVisibility(View.GONE);
            BtnUploadTemplate.setVisibility(View.GONE);
            BtnTidakMarketable.setVisibility(View.GONE);
            BtnTidakStatusHarga.setVisibility(View.GONE);
            IVFlowUp.setVisibility(View.VISIBLE);
            IVFlowUp2.setVisibility(View.VISIBLE);
            idpengguna = "0";
            AgenId = Preferences.getKeyIdAgen(this);
            IVEdit.setVisibility(View.VISIBLE);
            LytCBMarketable.setVisibility(View.GONE);
            LytCBHarga.setVisibility(View.GONE);
            LytCBSelfie.setVisibility(View.GONE);
            LytCBLokasi.setVisibility(View.GONE);
            IVEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailPraListingActivity.this, EditDataPralistingActivity.class);
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
            BtnTambahMaps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailPraListingActivity.this, TambahDataGambarPralistingActivity.class);
                    update.putExtra("IdPraListing", idpralisting);
                    update.putExtra("Selfie", intentSelfie);
                    startActivity(update);
                }
            });
            BtnTambahSelfie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailPraListingActivity.this, TambahDataSelfiePralistingActivity.class);
                    update.putExtra("IdPraListing", idpralisting);
                    startActivity(update);
                }
            });
        } else if (status.equals("4")) {
            IVFlowUp.setVisibility(View.INVISIBLE);
            BtnApproveAdmin.setVisibility(View.GONE);
            BtnApproveManager.setVisibility(View.GONE);
            BtnRejectedAdmin.setVisibility(View.GONE);
            BtnRejectedManager.setVisibility(View.GONE);
            BtnLihatTemplate.setVisibility(View.GONE);
            BtnLihatTemplateKosong.setVisibility(View.GONE);
            BtnUploadTemplate.setVisibility(View.GONE);
            BtnTidakMarketable.setVisibility(View.GONE);
            BtnTidakStatusHarga.setVisibility(View.GONE);
            BtnTambahMaps.setVisibility(View.GONE);
            BtnTambahSelfie.setVisibility(View.GONE);
            IVFlowUp.setVisibility(View.INVISIBLE);
            IVFlowUp2.setVisibility(View.INVISIBLE);
            TVAlamatDetailListing.setVisibility(View.GONE);
            IVAlamat.setVisibility(View.GONE);
            idpengguna = Preferences.getKeyIdCustomer(this);
            AgenId = "0";
            LytCBMarketable.setVisibility(View.GONE);
            LytCBHarga.setVisibility(View.GONE);
            LytCBSelfie.setVisibility(View.GONE);
            LytCBLokasi.setVisibility(View.GONE);
        } else {
            TVAlamatDetailListing.setVisibility(View.GONE);
            BtnLihatTemplate.setVisibility(View.GONE);
            BtnLihatTemplateKosong.setVisibility(View.GONE);
            BtnUploadTemplate.setVisibility(View.GONE);
            BtnTidakMarketable.setVisibility(View.GONE);
            BtnTidakStatusHarga.setVisibility(View.GONE);
            BtnTambahMaps.setVisibility(View.GONE);
            BtnTambahSelfie.setVisibility(View.GONE);
            IVAlamat.setVisibility(View.GONE);
            IVFlowUp.setVisibility(View.INVISIBLE);
            IVFlowUp2.setVisibility(View.INVISIBLE);
            LytCBMarketable.setVisibility(View.GONE);
            LytCBHarga.setVisibility(View.GONE);
            LytCBSelfie.setVisibility(View.GONE);
            LytCBLokasi.setVisibility(View.GONE);
        }

        if (intentIdAgenCo.equals("0")) {
            agen2.setVisibility(View.GONE);
        } else if (intentIdAgenCo.equals(intentIdAgen)) {
            agen2.setVisibility(View.GONE);
        } else {
            LoadCo();
            agen2.setVisibility(View.VISIBLE);
        }

        if (StrIdAgen.equals(intentIdAgen)) {
            TVPoin.setVisibility(View.VISIBLE);
        } else if (status.equals("1")) {
            TVPoin.setVisibility(View.VISIBLE);
        } else if (status.equals("2")) {
            TVPoin.setVisibility(View.VISIBLE);
        } else {
            TVPoin.setVisibility(View.GONE);
        }

        if (StrIdAgen.equals(intentIdAgen)) {
            if (intentLatitude.equals("0") && intentLongitude.equals("0")) {
                if (intentSelfie.equals("0") || intentSelfie.equals("") || intentSelfie.isEmpty()) {
                    BtnTambahSelfie.setVisibility(View.GONE);
                    BtnTambahMaps.setVisibility(View.VISIBLE);
                } else {
                    BtnTambahSelfie.setVisibility(View.GONE);
                    BtnTambahMaps.setVisibility(View.VISIBLE);
                }
            } else {
                if (intentSelfie.equals("0") || intentSelfie.equals("") || intentSelfie.isEmpty()) {
                    BtnTambahSelfie.setVisibility(View.VISIBLE);
                    BtnTambahMaps.setVisibility(View.GONE);
                } else {
                    BtnTambahSelfie.setVisibility(View.GONE);
                    BtnTambahMaps.setVisibility(View.GONE);
                }
            }
            if (intentPjp.isEmpty()) {
                BtnTambahPjp.setVisibility(View.VISIBLE);
            } else {
                BtnTambahPjp.setVisibility(View.GONE);
            }
            if (intentBanner.equals("Tidak")) {
                BtnTambahBanner.setVisibility(View.VISIBLE);
            } else {
                BtnTambahBanner.setVisibility(View.GONE);
            }
            if (intentIdAgenCo.equals(StrIdAgen) || intentIdAgenCo.equals("0")) {
                BtnTambahCoList.setVisibility(View.VISIBLE);
            } else {
                BtnTambahCoList.setVisibility(View.GONE);
            }
            BtnTambahGambar.setVisibility(View.VISIBLE);
            TVPoin.setVisibility(View.VISIBLE);
        } else {
            BtnTambahMaps.setVisibility(View.GONE);
            BtnTambahSelfie.setVisibility(View.GONE);
            BtnTambahPjp.setVisibility(View.GONE);
            BtnTambahBanner.setVisibility(View.GONE);
            BtnTambahCoList.setVisibility(View.GONE);
            BtnTambahGambar.setVisibility(View.GONE);
            TVPoin.setVisibility(View.GONE);
        }

        viewPager.setOnClickListener(view -> startActivity(new Intent(DetailPraListingActivity.this, ImageViewActivity.class)));
        IVFlowUp.setVisibility(View.INVISIBLE);
        IVFlowUp2.setVisibility(View.INVISIBLE);
        tambahagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromApi();
            }
        });
        tambahcoagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromApiCo();
            }
        });
        BtnApproveAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lytambahagen.getVisibility() == View.VISIBLE) {
                    String input = tambahagen.getText().toString();
                    if (input.isEmpty()) {
                        Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                        Glide.with(DetailPraListingActivity.this)
                                .load(R.drawable.alert)
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
        BtnApproveManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lytambahagen.getVisibility() == View.VISIBLE) {
                    String input = tambahagen.getText().toString();
                    if (input.isEmpty()) {
                        Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                        Glide.with(DetailPraListingActivity.this)
                                .load(R.drawable.alert)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
                        approvemanager();
                    }
                } else {
                    approvemanager();
                }
            }
        });
        BtnRejectedAdmin.setOnClickListener(v -> ShowRejected());
        BtnRejectedManager.setOnClickListener(v -> ShowRejected());
        BtnLihatTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentPriority.equals("open")){
                    String url = "https://app.gooproper.com/GooProper/template/" + idpralisting;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    String url = "https://app.gooproper.com/GooProper/templateexclusive/" + idpralisting;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });
        BtnLihatTemplateKosong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentPriority.equals("open")){
                    String url = "https://app.gooproper.com/GooProper/templateblank/" + idpralisting;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    String url = "https://app.gooproper.com/GooProper/templateblankexclusive/" + idpralisting;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }

            }
        });
        BtnUploadTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(DetailPraListingActivity.this, TambahTemplateActivity.class);
                update.putExtra("update",1);
                update.putExtra("IdPraListing",intentIdPraListing);
                startActivity(update);
            }
        });
        BtnTambahMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailPraListingActivity.this, TambahDataGambarPralistingActivity.class);
                update.putExtra("IdPraListing", idpralisting);
                update.putExtra("Selfie", intentSelfie);
                startActivity(update);
            }
        });
        BtnTambahSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailPraListingActivity.this, TambahDataSelfiePralistingActivity.class);
                update.putExtra("IdPraListing", idpralisting);
                startActivity(update);
            }
        });
        BtnTambahGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailPraListingActivity.this, TambahDataGambarPralistingActivity.class);
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
                Intent update = new Intent(DetailPraListingActivity.this, TambahDataLokasiPralistingActivity.class);
                update.putExtra("IdPraListing", intentIdPraListing);
                update.putExtra("Selfie", intentSelfie);
                startActivity(update);
            }
        });
        BtnTambahSelfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent update = new Intent(DetailPraListingActivity.this, TambahDataSelfiePralistingActivity.class);
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
        BtnTambahCoList.setOnClickListener(new View.OnClickListener() {
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
        BtnTidakMarketable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTidakMarketable();
            }
        });
        BtnTidakStatusHarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowTidakStatusHarga();
            }
        });

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

            if (isDatePassed(intentTglInput)) {
                if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 120;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 50;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 120;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 50;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 120 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 50 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 50;
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 100;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 50;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 100;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 50;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 100 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 50 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 50;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 50;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 50;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 50 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 50;
                    }
                } else if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 100;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 40;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 100;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 40;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 100 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 40 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 40;
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 80;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 40;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 80;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 40;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 80 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 40 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 40;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 40;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 40;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 40 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 40;
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 70;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 30;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 70;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 30;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 70 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 30 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 30;
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 60;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 30;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 60;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 30;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 60 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 30 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 30;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 30;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 30;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 30 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 30;
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 60;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 20;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 60;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 20;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 60 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 20 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 20;
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 40;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 20;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 40;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 20;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 40 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 20 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 20;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 20;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 20;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 20 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 20;
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 40;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 40;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 40 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 10;
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 20;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 20;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 20 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 10;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 10;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 10;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 10 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 10;
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 30;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 30;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 30 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 10;
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                if (CountImage >= 8) {
                                    FinalPoin = 20;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                if (CountImage >= 8) {
                                    FinalPoin = 20;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            } else {
                                if (CountImage >= 8) {
                                    FinalPoin = 20 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                } else {
                                    FinalPoin = 10 / 2;
                                    TVPoin.setText(String.valueOf(FinalPoin));
                                }
                            }
                            Poin = 10;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 10;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 10;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 10 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                        Poin = 10;
                    }
                }
            } else {
                if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 120;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 120;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 120 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 100;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 100;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 100 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 50;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 50;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 50 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 100;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 100;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 100 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 80;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 80;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 80 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 40;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 40;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 40 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 70;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 70;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 70 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 60;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 60;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 60 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 30;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 30;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 30 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 60;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 60;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 60 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 40;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 40;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 40 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 20;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 20;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 20 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 40;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 40;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 40 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 20;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 20;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 20 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 10;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 10;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 10 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 30;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 30;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 30 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                FinalPoin = 20;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                FinalPoin = 20;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            } else {
                                FinalPoin = 20 / 2;
                                TVPoin.setText(String.valueOf(FinalPoin));
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            FinalPoin = 10;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            FinalPoin = 10;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        } else {
                            FinalPoin = 10 / 2;
                            TVPoin.setText(String.valueOf(FinalPoin));
                        }
                    }
                }
            }

//            if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 120;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 50;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 120;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 50;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 120 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 50 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 50;
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 100;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 50;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 100;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 50;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 100 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 50 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 50;
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 50;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 50;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 50 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 50;
//                }
//            } else if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 100;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 40;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 100;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 40;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 100 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 40 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 40;
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 80;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 40;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 80;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 40;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 80 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 40 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 40;
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 40;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 40;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 40 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 40;
//                }
//            } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 70;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 30;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 70;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 30;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 70 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 30 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 30;
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 60;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 30;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 60;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 30;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 60 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 30 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 30;
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 30;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 30;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 30 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 30;
//                }
//            } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 60;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 20;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 60;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 20;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 60 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 20 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 20;
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 40;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 20;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 40;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 20;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 40 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 20 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 20;
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 20;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 20;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 20 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 20;
//                }
//            } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Ya")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 40;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 40;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 40 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 10;
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 20;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 20;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 20 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 10;
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 10;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 10;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 10 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 10;
//                }
//            } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 30;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 30;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 30 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 10;
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 20;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            if (CountImage >= 8) {
//                                FinalPoin = 20;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        } else {
//                            if (CountImage >= 8) {
//                                FinalPoin = 20 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            } else {
//                                FinalPoin = 10 / 2;
//                                TVPoin.setText(String.valueOf(FinalPoin));
//                            }
//                        }
//                        Poin = 10;
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 10;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 10;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 10 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 10;
//                }
//            }



//            if (intentPriority.equals("exclusive") && !intentImgPjp.equals("0") && !intentImgPjp1.equals("0") && intentBanner.equals("Ya")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 120;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 120;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 120 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 50;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin1();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin1();
//                            }
//                        });
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 100;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 100;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 100 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 50;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin1();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin1();
//                            }
//                        });
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 50;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 50;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 50 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 50;
//                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint1();
//                        }
//                    });
//                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint1();
//                        }
//                    });
//                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint1();
//                        }
//                    });
//                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint1();
//                        }
//                    });
//                }
//            } else if (intentPriority.equals("exclusive") && !intentImgPjp.equals("0") && !intentImgPjp1.equals("0") && intentBanner.equals("Tidak")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 100;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 100;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 100 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 40;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin2();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin2();
//                            }
//                        });
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 80;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 80;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 80 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 40;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin2();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin2();
//                            }
//                        });
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 40;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 40;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 40 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 40;
//                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint2();
//                        }
//                    });
//                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint2();
//                        }
//                    });
//                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint2();
//                        }
//                    });
//                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint2();
//                        }
//                    });
//                }
//            } else if (intentPriority.equals("open") && !intentImgPjp.equals("0") && !intentImgPjp1.equals("0") && intentBanner.equals("Ya")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 70;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 70;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 70 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 30;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin3();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin3();
//                            }
//                        });
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 60;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 60;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 60 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 30;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin3();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin3();
//                            }
//                        });
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 30;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 30;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 30 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 30;
//                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint3();
//                        }
//                    });
//                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint3();
//                        }
//                    });
//                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint3();
//                        }
//                    });
//                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint3();
//                        }
//                    });
//                }
//            } else if (intentPriority.equals("open") && !intentImgPjp.equals("0") && !intentImgPjp1.equals("0") && intentBanner.equals("Tidak")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 60;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 60;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 60 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 20;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin4();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin4();
//                            }
//                        });
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 40;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 40;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 40 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 20;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin4();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin4();
//                            }
//                        });
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 20;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 20;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 20 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 20;
//                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint4();
//                        }
//                    });
//                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint4();
//                        }
//                    });
//                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint4();
//                        }
//                    });
//                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint4();
//                        }
//                    });
//                }
//            } else if (intentPriority.equals("open") && intentImgPjp.equals("0") && intentImgPjp1.equals("0") && intentBanner.equals("Ya")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 50;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 50;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 50 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 10;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin5();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin5();
//                            }
//                        });
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 20;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 20;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 20 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 10;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin5();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin5();
//                            }
//                        });
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 10;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 10;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 10 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 10;
//                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint5();
//                        }
//                    });
//                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint5();
//                        }
//                    });
//                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint5();
//                        }
//                    });
//                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint5();
//                        }
//                    });
//                }
//            } else if (intentPriority.equals("open") && intentImgPjp.equals("0") && intentImgPjp1.equals("0") && intentBanner.equals("Tidak")) {
//                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
//                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 30;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 30;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 30 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 10;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin6();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin6();
//                            }
//                        });
//                    } else {
//                        if (intentIdAgenCo.equals("0")) {
//                            FinalPoin = 20;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                            FinalPoin = 20;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        } else {
//                            FinalPoin = 20 / 2;
//                            TVPoin.setText(String.valueOf(FinalPoin));
//                        }
//                        Poin = 10;
//                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin6();
//                            }
//                        });
//                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                            @Override
//                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                                updatepoin6();
//                            }
//                        });
//                    }
//                } else {
//                    if (intentIdAgenCo.equals("0")) {
//                        FinalPoin = 10;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else if (intentIdAgenCo.equals(intentIdAgen)) {
//                        FinalPoin = 10;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    } else {
//                        FinalPoin = 10 / 2;
//                        TVPoin.setText(String.valueOf(FinalPoin));
//                    }
//                    Poin = 10;
//                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint6();
//                        }
//                    });
//                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint6();
//                        }
//                    });
//                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint6();
//                        }
//                    });
//                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                        @Override
//                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                            updatepoint6();
//                        }
//                    });
//                }
//            }
            if (intentIsRejected.equals("0")) {
                LytRejected.setVisibility(View.GONE);
            } else {
                LytRejected.setVisibility(View.VISIBLE);
                TVRejected.setText(intentKeterangan);
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
            if (intentIdAgen.equals("null")) {
                if (intentSold.equals("1")) {
                    LytBadge.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentSoldAgen.equals("1")) {
                    LytBadge.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentRented.equals("1")) {
                    LytBadge.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentRentedAgen.equals("1")) {
                    LytBadge.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else {
                    if (status.equals("1")) {
                        agen.setVisibility(View.GONE);
                        lytambahagen.setVisibility(View.VISIBLE);
                        idagen = agenid;
                    } else if (status.equals("2")) {
                        agen.setVisibility(View.GONE);
                        lytambahagen.setVisibility(View.VISIBLE);
                        idagen = agenid;
                    } else {
                        agen.setVisibility(View.GONE);
                        lytambahagen.setVisibility(View.GONE);
                    }
                }
            } else {
                if (intentSold.equals("1")) {
                    LytBadge.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentSoldAgen.equals("1")) {
                    LytBadge.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentRented.equals("1")) {
                    LytBadge.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentRentedAgen.equals("1")) {
                    LytBadge.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else {
                    agen.setVisibility(View.VISIBLE);
                    lytambahagen.setVisibility(View.GONE);
                    idagen = intentIdAgen;
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
                TVNamaDetailListing.setText("-");
            } else {
                TVNamaDetailListing.setText(intentNamaListing);
            }
            if (intentAlamat.isEmpty()) {
                TVAlamatDetailListing.setText("-");
            } else {
                if (intentWilayah.isEmpty()) {
                    TVAlamatDetailListing.setText(intentAlamat);
                } else {
                    TVAlamatDetailListing.setText(intentAlamat+" ( "+intentWilayah+" )");
                }
            }
            if (intentKondisi.equals("Jual")) {
                if (intentHarga.isEmpty()) {
                    TVHargaDetailListing.setText("Rp. -");
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else {
                    TVHargaDetailListing.setText(currency.formatRupiah(intentHarga));
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                }
            } else if (intentKondisi.equals("Sewa")) {
                if (intentHargaSewa.isEmpty()) {
                    TVHargaSewaDetailListing.setText("Rp. -");
                    TVHargaDetailListing.setVisibility(View.GONE);
                } else {
                    TVHargaSewaDetailListing.setText(currency.formatRupiah(intentHargaSewa));
                    TVHargaDetailListing.setVisibility(View.GONE);
                }
            } else {
                if (intentHarga.isEmpty()) {
                    if (intentHargaSewa.isEmpty()) {
                        TVHargaDetailListing.setText("Rp. -");
                        TVHargaSewaDetailListing.setText("Rp. -");
                    } else {
                        TVHargaDetailListing.setText("Rp. - /");
                        TVHargaSewaDetailListing.setText(currency.formatRupiah(intentHargaSewa));
                    }
                } else {
                    if (intentHargaSewa.isEmpty()) {
                        TVHargaDetailListing.setText(currency.formatRupiah(intentHarga));
                        TVHargaSewaDetailListing.setText("Rp. -");
                    } else {
                        TVHargaDetailListing.setText(currency.formatRupiah(intentHarga) + " /");
                        TVHargaSewaDetailListing.setText(currency.formatRupiah(intentHargaSewa));
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
                Glide.with(this).load(intentSelfie).into(IVSelfie);
                TVSelfie.setVisibility(View.GONE);
            }
            TVNamaAgen.setText(intentNama);
            TVNamaAgen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailPraListingActivity.this, DetailAgenListingActivity.class);
                    update.putExtra("update", 1);
                    update.putExtra("IdAgen", intentIdAgen);
                    startActivity(update);
                }
            });
            TVNamaAgen2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailPraListingActivity.this, DetailAgenListingActivity.class);
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
            IVInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "http://instagram.com/_u/" + intentInstagram;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

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
    private void updatePageInfo(int currentPage) {
        String info = (currentPage + 1) + "/" + images.size();
        TVJumlahGambar.setText(info);
    }
    public boolean isDatePassed(String dateString) {
        if (dateString == null) return false;

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            Date dateFromDatabase = format.parse(dateString);
            Date currentDate = format.parse("2024-05-28");
            return currentDate.after(dateFromDatabase);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
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
    private void updatepoin() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = Poin / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoin1() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoin2() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoin3() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 10) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoin4() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoin5() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 30;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 30;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 30) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }

        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoin6() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 10) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoint1() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();
        boolean isCBMarketableChecked = CBMarketable.isChecked();
        boolean isCBHargaChecked = CBHarga.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked && isCBMarketableChecked && isCBHargaChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = (Poin * 2) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoint2() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();
        boolean isCBMarketableChecked = CBMarketable.isChecked();
        boolean isCBHargaChecked = CBHarga.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked && isCBMarketableChecked && isCBHargaChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = (Poin * 2) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoint3() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();
        boolean isCBMarketableChecked = CBMarketable.isChecked();
        boolean isCBHargaChecked = CBHarga.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked && isCBMarketableChecked && isCBHargaChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 10) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = (Poin * 2) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoint4() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();
        boolean isCBMarketableChecked = CBMarketable.isChecked();
        boolean isCBHargaChecked = CBHarga.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked && isCBMarketableChecked && isCBHargaChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = (Poin * 2) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoint5() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();
        boolean isCBMarketableChecked = CBMarketable.isChecked();
        boolean isCBHargaChecked = CBHarga.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked && isCBMarketableChecked && isCBHargaChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 30;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 30;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 30) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = (Poin * 2) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
        }
    }
    private void updatepoint6() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();
        boolean isCBMarketableChecked = CBMarketable.isChecked();
        boolean isCBHargaChecked = CBHarga.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked && isCBMarketableChecked && isCBHargaChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 10) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (intentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = (Poin * 2) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else {
            TVPoin.setText(String.valueOf(FinalPoin));
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
    private void fetchLocationsFromAPI(double currentLatitude, double currentLongitude) {
        pDialog.setMessage("Memuat Listingan...");
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRALISTING_TERDEKAT, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        pDialog.cancel();
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
                            findNearestLocation(currentLatitude, currentLongitude, locationList, 0.1);
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
    private void findNearestLocation(double currentLatitude, double currentLongitude, List<PraListingTerdekatModel> locationList, double distanceLimit) {
        List<PraListingTerdekatModel> nearbyLocations = new ArrayList<>();

        for (PraListingTerdekatModel location : locationList) {
            double distance = calculateDistance(currentLatitude, currentLongitude, location.getLatitude(), location.getLongitude());
            if (distance <= distanceLimit) {
                nearbyLocations.add(location);
            }
        }

//        for (PraListingTerdekatModel location : locationList) {
//            double distance = calculateDistance(currentLatitude, currentLongitude, location.getLatitude(), location.getLongitude());
//            nearbyLocations.add(location);
//        }

        nearestLocations.clear();
        nearestLocations.addAll(nearbyLocations);
        AdapterTerdekat.notifyDataSetChanged();
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
    private void LoadPraListingTerkait() {
        RequestQueue queue = Volley.newRequestQueue(DetailPraListingActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, String.format(ServerApi.URL_GET_LISTING_TERKAIT,intentJenisProperti,intentKondisi), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsTerkait.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject locationObject = response.getJSONObject(i);
                                double latitude = locationObject.getDouble("Latitude");
                                double longitude = locationObject.getDouble("Longitude");
                                String imageUrl = locationObject.getString("Img1");
                                String idpralisting = locationObject.getString("IdPraListing");
                                String namalisting = locationObject.getString("NamaListing");
                                String alamatlisting = locationObject.getString("Alamat");
                                mItemsTerkait.add(new PraListingTerdekatModel(latitude, longitude, idpralisting, namalisting, alamatlisting, imageUrl));
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
    private void LoadPraListingTerdekat() {
        RequestQueue queue = Volley.newRequestQueue(DetailPraListingActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, String.format(ServerApi.URL_GET_LISTING_TERKAIT,intentJenisProperti,intentKondisi), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsTerkait.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject locationObject = response.getJSONObject(i);
                                double latitude = locationObject.getDouble("Latitude");
                                double longitude = locationObject.getDouble("Longitude");
                                String imageUrl = locationObject.getString("Img1");
                                String idpralisting = locationObject.getString("IdPraListing");
                                String namalisting = locationObject.getString("NamaListing");
                                String alamatlisting = locationObject.getString("Alamat");
                                mItemsTerkait.add(new PraListingTerdekatModel(latitude, longitude, idpralisting, namalisting, alamatlisting, imageUrl));
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
    private void ShowRejected() {
        AlertDialog.Builder customBuilder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
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
                reject();
            }
        });

        customBuilder.setNegativeButton("Batal", null);

        AlertDialog customDialog = customBuilder.create();
        customDialog.show();
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
                                IVInstagram2.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String url = "http://instagram.com/_u/" + IGCo;
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                    }
                                });
                                if (status.equals("1")) {
                                    IVWhatsapp2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya Manager, ingin menanyakan pralisting " + StringNamaListing + " yang beralamat di " + StringAlamat + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (status.equals("2")) {
                                    IVWhatsapp2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya Admin, ingin menanyakan pralisting " + StringNamaListing + " yang beralamat di " + StringAlamat + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else if (status.equals("3")) {
                                    IVWhatsapp2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String message = "Halo! Saya " + StringNamaBuyer + ", ingin menanyakan pralisting " + StringNamaListing + " yang beralamat di " + StringAlamat + ".";
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else {
                                    IVWhatsapp2.setVisibility(View.INVISIBLE);
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
    private void fetchDataFromApiCo() {
        agenCoManager.fetchDataFromApi(this, new AgenManager.ApiCallback() {
            @Override
            public void onSuccess(List<AgenManager.DataItem> dataList) {
                showAlertDialogCo(dataList);
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }
    private void showAlertDialogCo(List<AgenManager.DataItem> dataList) {
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
                agencoid = selectedData.getId();
                handleSelectedData(selectedData);
            }
        });

        builder.setPositiveButton("OK", null);
        builder.show();
    }
    private void handleSelectedDataCo(AgenManager.DataItem selectedData) {
        String selectedText = "ID Agen Co Listing: " + selectedData.getId();
        Toast.makeText(this, selectedText, Toast.LENGTH_SHORT).show();

        tambahcoagen.setText(selectedData.getName());
        agencoid = selectedData.getId();
    }
    private void approveadmin() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_APPROVE_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                Glide.with(DetailPraListingActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                Glide.with(DetailPraListingActivity.this)
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

                final String StringMarketable = CBMarketable.isChecked() ? "1" : "0";
                final String StringHarga = CBHarga.isChecked() ? "1" : "0";
                final String StringSelfie = CBSelfie.isChecked() ? "1" : "0";
                final String StringLokasi = CBLokasi.isChecked() ? "1" : "0";

                map.put("IdAgen", intentIdAgen);
                map.put("IdPraListing", idpralisting);
                map.put("Pjp", tambahpjp.getText().toString().trim());
                map.put("Marketable", StringMarketable);
                map.put("StatusHarga", StringHarga);
                map.put("IsSelfie", StringSelfie);
                map.put("IsLokasi", StringLokasi);

                System.out.println(map);

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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_APPROVE_MANAGER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE_AGEN + intentIdAgen, null, new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                try {
                                    ArrayList<String> tokens = new ArrayList<>();
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject tokenObject = response.getJSONObject(i);
                                        String token = tokenObject.getString("Token");
                                        tokens.add(token);
                                    }
                                    new SendMessageTask().execute(tokens.toArray(new String[0]));
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

                Glide.with(DetailPraListingActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                Glide.with(DetailPraListingActivity.this)
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

                final String StringMarketable = CBMarketable.isChecked() ? "1" : "0";
                final String StringHarga = CBHarga.isChecked() ? "1" : "0";
                final String StringSelfie = CBSelfie.isChecked() ? "1" : "0";
                final String StringLokasi = CBLokasi.isChecked() ? "1" : "0";

                map.put("IdAgen", intentIdAgen);
                map.put("IdPraListing", idpralisting);
                map.put("Marketable", StringMarketable);
                map.put("StatusHarga", StringHarga);
                map.put("IsSelfie", StringSelfie);
                map.put("IsLokasi", StringLokasi);
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void ajukanulang() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_AJUKAN_ULANG, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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
                                // Tangani kesalahan jika terjadi
                            }
                        });
                        requestQueue.add(jsonArrayRequest);
                        customDialog.dismiss();
                        finish();
                    }
                });

                Glide.with(DetailPraListingActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                Glide.with(DetailPraListingActivity.this)
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
                map.put("IdPraListing", idpralisting);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void reject() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REJECTED, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Listing Rejected");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE_AGEN + idagen, null, new Response.Listener<JSONArray>() {
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
                                // Tangani kesalahan jika terjadi
                            }
                        });
                        requestQueue.add(jsonArrayRequest);
                        customDialog.dismiss();
                        finish();
                    }
                });

                Glide.with(DetailPraListingActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                Glide.with(DetailPraListingActivity.this)
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
                map.put("IdPraListing", idpralisting);
                map.put("Keterangan", StringKeteranganReject);
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
        String shareMessage = "Lihat listingan kami\n" + StringNamaListing + "\nLT " + StringLuasTanah + " LB " + StringLuasBangunan + " \nKT " + StringKamarTidur + " + " + StringKamarTidurArt + "\nKM " + StringKamarMandi + " + " + StringKamarMandiArt + "\nListrik " + StringListrik + " Watt\n" + StringSertifikat + "\nHarga " + StringHarga + "\n\n" + deepLinkUrl;

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
    private void shareDeepLinkSewa(String productId) {
        String deepLinkUrl = "https://gooproper.com/listing/" + productId;
        String shareMessage = "Lihat listingan kami\n" + StringNamaListing + "\nLT " + StringLuasTanah + " LB " + StringLuasBangunan + " \nKT " + StringKamarTidur + " + " + StringKamarTidurArt + "\nKM " + StringKamarMandi + " + " + StringKamarMandiArt + "\nListrik " + StringListrik + " Watt\n" + StringSertifikat + "\nHarga " + StringHargaSewa + "\n\n" + deepLinkUrl;

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
    private void shareDeepLinkJualSewa(String productId) {
        String deepLinkUrl = "https://gooproper.com/listing/" + productId;
        String shareMessage = "Lihat listingan kami\n" + StringNamaListing + "\nLT " + StringLuasTanah + " LB " + StringLuasBangunan + " \nKT " + StringKamarTidur + " + " + StringKamarTidurArt + "\nKM " + StringKamarMandi + " + " + StringKamarMandiArt + "\nListrik " + StringListrik + " Watt\n" + StringSertifikat + "\nHarga " + StringHarga + "\nHarga Sewa " + StringHargaSewa + "\n\n" + deepLinkUrl;

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
    public void ShowTambahBanner() {
        customDialogBanner = new Dialog(DetailPraListingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailPraListingActivity.this, R.style.CustomAlertDialogStyle);
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
                    Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                    Glide.with(DetailPraListingActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    if (!Ukuran.getText().toString().isEmpty()) {
                        pDialog.setMessage("Menyimpan Data");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_BANNER_PRALISTING,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pDialog.cancel();
                                        try {
                                            JSONObject res = new JSONObject(response);
                                            String status = res.getString("Status");
                                            if (status.equals("Sukses")) {
                                                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                                Glide.with(DetailPraListingActivity.this)
                                                        .load(R.mipmap.ic_yes)
                                                        .transition(DrawableTransitionOptions.withCrossFade())
                                                        .into(gifimage);

                                                customDialog.show();
                                            } else if (status.equals("Error")) {
                                                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                                Glide.with(DetailPraListingActivity.this)
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
                                        Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                        Glide.with(DetailPraListingActivity.this)
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

                        RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_BANNER_PRALISTING,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pDialog.cancel();
                                        try {
                                            JSONObject res = new JSONObject(response);
                                            String status = res.getString("Status");
                                            if (status.equals("Sukses")) {
                                                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                                Glide.with(DetailPraListingActivity.this)
                                                        .load(R.mipmap.ic_yes)
                                                        .transition(DrawableTransitionOptions.withCrossFade())
                                                        .into(gifimage);

                                                customDialog.show();
                                            } else if (status.equals("Error")) {
                                                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                                Glide.with(DetailPraListingActivity.this)
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
                                        Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                        Glide.with(DetailPraListingActivity.this)
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
        customDialogCoList = new Dialog(DetailPraListingActivity.this);
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
                agenManager.fetchDataFromApi(DetailPraListingActivity.this, new AgenManager.ApiCallback() {
                    @Override
                    public void onSuccess(List<AgenManager.DataItem> dataList) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPraListingActivity.this, R.style.CustomAlertDialogStyle);
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
                    Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                    Glide.with(DetailPraListingActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    pDialog.setMessage("Menyimpan Data");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_COLIST_PRALISTING,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pDialog.cancel();
                                    try {
                                        JSONObject res = new JSONObject(response);
                                        String status = res.getString("Status");
                                        if (status.equals("Sukses")) {
                                            Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                            Glide.with(DetailPraListingActivity.this)
                                                    .load(R.mipmap.ic_yes)
                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                    .into(gifimage);

                                            customDialog.show();
                                        } else if (status.equals("Error")) {
                                            Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                            Glide.with(DetailPraListingActivity.this)
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
                                    Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                    Glide.with(DetailPraListingActivity.this)
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
        CustomDialogPjp = new Dialog(DetailPraListingActivity.this);
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
                    Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                    Glide.with(DetailPraListingActivity.this)
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

                                                                            RequestQueue requestQueue = Volley.newRequestQueue(DetailPraListingActivity.this);

                                                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_PJP_PRALISTING,
                                                                                    new Response.Listener<String>() {
                                                                                        @Override
                                                                                        public void onResponse(String response) {
                                                                                            pDialog.cancel();
                                                                                            try {
                                                                                                JSONObject res = new JSONObject(response);
                                                                                                String status = res.getString("Status");
                                                                                                if (status.equals("Sukses")) {
                                                                                                    Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                                                                                    Glide.with(DetailPraListingActivity.this)
                                                                                                            .load(R.mipmap.ic_yes)
                                                                                                            .transition(DrawableTransitionOptions.withCrossFade())
                                                                                                            .into(gifimage);

                                                                                                    customDialog.show();
                                                                                                } else if (status.equals("Error")) {
                                                                                                    Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                                                                                    Glide.with(DetailPraListingActivity.this)
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
                                                                                            Dialog customDialog = new Dialog(DetailPraListingActivity.this);
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

                                                                                            Glide.with(DetailPraListingActivity.this)
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
                                                                            Toast.makeText(DetailPraListingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        });
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                pDialog.cancel();
                                                                Toast.makeText(DetailPraListingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            })
                                            .addOnFailureListener(exception -> {
                                                pDialog.cancel();
                                                Toast.makeText(DetailPraListingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailPraListingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        CustomDialogPjp.show();
    }
    public void ShowTidakMarketable() {
        Dialog customDialog = new Dialog(DetailPraListingActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.custom_dialog_konfirmasi);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
        Button ya = customDialog.findViewById(R.id.btnya);
        Button tidak = customDialog.findViewById(R.id.btntidak);

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TidakMarketable();
            }
        });

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        dialogTitle.setText("Apakah Anda Yakin Untuk Ubah Marketable");

        ImageView gifImageView = customDialog.findViewById(R.id.ivdialog);

        Glide.with(this)
                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(gifImageView);

        customDialog.show();
    }
    public void ShowTidakStatusHarga() {
        Dialog customDialog = new Dialog(DetailPraListingActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.custom_dialog_konfirmasi);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
        Button ya = customDialog.findViewById(R.id.btnya);
        Button tidak = customDialog.findViewById(R.id.btntidak);

        ya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TidakStatusHarga();
            }
        });

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        dialogTitle.setText("Apakah Anda Yakin Untuk Ubah Status Harga");

        ImageView gifImageView = customDialog.findViewById(R.id.ivdialog);

        Glide.with(this)
                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(gifImageView);

        customDialog.show();
    }
    private void TidakMarketable() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_MARKETABLE_PRALISTING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Listing Updated");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE_AGEN + idagen, null, new Response.Listener<JSONArray>() {
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
                                // Tangani kesalahan jika terjadi
                            }
                        });
                        requestQueue.add(jsonArrayRequest);
                        customDialog.dismiss();
                        finish();
                    }
                });

                Glide.with(DetailPraListingActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Gagal Updated Listing");
                ok.setVisibility(View.GONE);

                Glide.with(DetailPraListingActivity.this)
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
                map.put("IdPraListing", idpralisting);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void TidakStatusHarga() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_STATUS_HARGA_PRALISTING, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Listing Updated");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE_AGEN + idagen, null, new Response.Listener<JSONArray>() {
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
                                // Tangani kesalahan jika terjadi
                            }
                        });
                        requestQueue.add(jsonArrayRequest);
                        customDialog.dismiss();
                        finish();
                    }
                });

                Glide.with(DetailPraListingActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailPraListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Gagal Updated Listing");
                ok.setVisibility(View.GONE);

                Glide.with(DetailPraListingActivity.this)
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
                map.put("IdPraListing", idpralisting);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "listingku");
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
    private class SendMessageTaskAjukanUlang extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "pralisting");
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