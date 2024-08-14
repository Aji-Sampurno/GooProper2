package com.gooproper.ui.detail.listing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import android.os.Environment;
import android.provider.MediaStore;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
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
import com.gooproper.adapter.image.PJPAdapter;
import com.gooproper.adapter.image.SertifikatAdapter;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.detail.agen.DetailAgenListingActivity;
import com.gooproper.ui.edit.listing.EditDataListingActivity;
import com.gooproper.ui.edit.listing.TambahDataGambarListingActivity;
import com.gooproper.ui.edit.listing.TambahDataLokasiListingActivity;
import com.gooproper.ui.edit.listing.TambahDataSelfieListingActivity;
import com.gooproper.ui.followup.FollowUpActivity;
import com.gooproper.ui.ImageViewActivity;
import com.gooproper.ui.officer.report.TambahCekLokasiActivity;
import com.gooproper.ui.tambah.template.GantiTemplateActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailListingActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog PDDetailListing;
    TextView TVRangeHarga, TVNamaDetailListing, TVAlamatDetailListing, TVHargaDetailListing, TVHargaSewaDetailListing, TVViewsDetailListing, TVLikeDetailListing, TVBedDetailListing, TVNamaAgen, TVNamaAgen2, TVBathDetailListing, TVWideDetailListing, TVLandDetailListing, TVDimensiDetailListing, TVTipeDetailListing, TVStatusDetailListing, TVSertifikatDetailListing, TVLuasDetailListing, TVKamarTidurDetailListing, TVKamarMandiDetailListing, TVLantaiDetailListing, TVGarasiDetailListing, TVCarpotDetailListing, TVListrikDetailListing, TVSumberAirDetailListing, TVPerabotDetailListing, TVSizeBanner, TVDeskripsiDetailListing, TVNoData, TVNoDataPdf, TVPriority, TVKondisi, TVNoPjp, TVNoDataPjp, TVFee, TVTglInput, TVNamaVendor, TVTelpVendor, TVPJP, TVSelfie, TVPoin, TVHadap, TVJumlahGambar;
    TextView SeeAllSekitar, SeeAllTerkait;
    ImageView IVVerified, IVFlowUp, IVWhatsapp, IVInstagram, IVFlowUp2, IVWhatsapp2, IVInstagram2, IVFavorite, IVFavoriteOn, IVShare, IVStar1, IVStar2, IVStar3, IVStar4, IVStar5, IVAlamat, IVNextImg, IVPrevImg, IVSelfie;
    Button BtnLihatTemplate, BtnLihatTemplateKosong, BtnUploadTemplate, BtnTemplateDouble, BtnDataDouble, BtnHapusListing;
    Button BtnTambahPjp, BtnTambahBanner, BtnTambahCoList, BtnUpgrade, BtnTambahMaps, BtnTambahSelfie, BtnApproveUpgrade, BtnTambahWilayah, BtnCekLokasi, BtnSimpanPDF, BtnTambahNoArsip, BtnTambahGambar;
    TextInputEditText tambahpjp;
    TextInputLayout lyttambahcoagen, lyttambahpjp;
    CheckBox CBMarketable, CBHarga, CBSelfie, CBLokasi;
    LinearLayout LytCBMarketable, LytCBHarga, LytCBSelfie, LytCBLokasi;
    ScrollView scrollView;
    CardView agen, agen2, CVSold, CVRented;
    RecyclerView recycleListingSekitar, recycleListingTerkait;
    RecyclerView.Adapter AdapterSekitar, AdapterTerkait;
    String status, idpralisting, idagen, idlisting, agenid, agencoid, idpengguna, StringNamaListing, StringLuasTanah, StringLuasBangunan, StringKamarTidur, StringKamarTidurArt, StringKamarMandiArt, StringKamarMandi, StringListrik, StringHarga, StringHargaSewa, StringSertifikat, StringAlamat, StringJenisProperty;
    String BuyerNama, BuyerTelp, BuyerKeterangan, BuyerTanggal, BuyerIdAgen, BuyerIdListing, BuyerIdInput, BuyerJam, StringNamaBuyer, AgenId, StringKeteranganReject, StringKondisi;
    String NamaMaps, IsOfficer, IsCekLokasi, IsArsip, IsTemplate;
    String imageUrl, namaAgen, telpAgen, IdCo, UrlSHM, UrlHGB, UrlHSHP, UrlPPJB, UrlStratatitle, UrlAJB, UrlPetokD;
    String productId, StrIdAgen, StrIntentIdAgenCo, StrIntentIdAgen, StrJudulLaporanPdf;
    String PJPHal1, PJPHal2;
    String StrWilayah, StrJenis, StrKondisi;
    String intentIdListing,intentIdAgen,intentIdAgenCo,intentIdInput,intentNamaListing,intentAlamat,intentLatitude,intentLongitude,intentLocation,intentWilayah,intentSelfie,intentWide,intentLand,intentDimensi,intentListrik,intentLevel,intentBed,intentBedArt,intentBath,intentBathArt,intentGarage,intentCarpot,intentHadap,intentSHM,intentHGB,intentHSHP,intentPPJB,intentStratatitle,intentAJB,intentPetokD,intentPjp,intentImgSHM,intentImgHGB,intentImgHSHP,intentImgPPJB,intentImgStratatitle,intentImgAJB,intentImgPetokD,intentImgPjp,intentImgPjp1,intentNoCertificate,intentPbb,intentJenisProperti,intentJenisCertificate,intentSumberAir,intentKondisi,intentDeskripsi,intentPrabot,intentKetPrabot,intentPriority,intentTtd,intentBanner,intentSize,intentHarga,intentHargaSewa,intentRangeHarga,intentTglInput,intentImg1,intentImg2,intentImg3,intentImg4,intentImg5,intentImg6,intentImg7,intentImg8,intentImg9,intentImg10,intentImg11,intentImg12,intentVideo,intentLinkFacebook,intentLinkTiktok,intentLinkInstagram,intentLinkYoutube,intentIsAdmin,intentIsManager,intentIsRejected,intentSold,intentRented,intentSoldAgen,intentRentedAgen,intentView,intentMarketable,intentStatusHarga,intentNama,intentNoTelp,intentInstagram,intentFee,intentNamaVendor,intentNoTelpVendor,intentIsSelfie,intentIsLokasi,intentKeterangan,intentIdTemplate,intentTemplate,intentTemplateBlank;
    int PoinSekarang, PoinTambah, PoinKurang;
    int Poin, FinalPoin, CoPoin;
    ProgressDialog pDialog;
    ListingModel lm;
    LinearLayout LytSertifikat, LytPJP, LytSize, LytFee, LytTglInput, LytBadge, LytEdit, LytNamaVendor, LytTelpVendor, LytSelfie, LytBtnHapus;
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
    private Dialog CustomDialogPjp, CustomDialogArsip, CustomDialogBanner, CustomDialogCoList;
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
        setContentView(R.layout.activity_detail_listing);

        PDDetailListing = new ProgressDialog(DetailListingActivity.this);
        pDialog = new ProgressDialog(this);

        viewPager = findViewById(R.id.VPDetailListing);
        viewPagerSertifikat = findViewById(R.id.VPSertifikatDetailListing);
        viewPagerPJP = findViewById(R.id.VPPJPDetailListing);
        agen = findViewById(R.id.LytAgenDetailListing);
        agen2 = findViewById(R.id.LytAgen2DetailListing);

        LytSertifikat = findViewById(R.id.LytSertifikat);
        LytPJP = findViewById(R.id.LytViewPjp);
        LytSize = findViewById(R.id.LytUkuranBannerDetailListing);
        LytFee = findViewById(R.id.LytFeeDetailListing);
        LytTglInput = findViewById(R.id.LytTglInputDetailListing);
        LytBadge = findViewById(R.id.LytBadge);
        LytEdit = findViewById(R.id.LytEditDetailListing);
        LytNamaVendor = findViewById(R.id.LytNamaVendorDetailListing);
        LytTelpVendor = findViewById(R.id.LytTelpVendorDetailListing);
        LytSelfie = findViewById(R.id.LytViewSelfie);
        scrollView = findViewById(R.id.SVDetailListing);

        BtnTemplateDouble = findViewById(R.id.BtnTemplateDouble);
        BtnDataDouble = findViewById(R.id.BtnDataDouble);
        BtnHapusListing = findViewById(R.id.BtnHapusListing);

        BtnTambahNoArsip = findViewById(R.id.BtnTambahNoArsipListing);
        BtnSimpanPDF = findViewById(R.id.BtnSimpanPDF);
        BtnCekLokasi = findViewById(R.id.BtnCekLokasi);
        BtnTambahMaps = findViewById(R.id.BtnAddMapsDetailListing);
        BtnTambahSelfie = findViewById(R.id.BtnAddSelfieDetailListing);
        BtnTambahPjp = findViewById(R.id.BtnAddPjpDetailListing);
        BtnTambahBanner = findViewById(R.id.BtnAddBannerDetailListing);
        BtnTambahCoList = findViewById(R.id.BtnColistDetailListing);
        BtnTambahWilayah = findViewById(R.id.BtnTambahWilayahDetailListing);
        BtnTambahGambar = findViewById(R.id.BtnTambahGambarDetailListing);
        BtnUpgrade = findViewById(R.id.BtnUpgradeDetailListing);
        BtnApproveUpgrade = findViewById(R.id.BtnApproveUpgradeDetailListing);

        BtnLihatTemplate = findViewById(R.id.BtnLihatTemplate);
        BtnLihatTemplateKosong = findViewById(R.id.BtnLihatTemplateKosong);
        BtnUploadTemplate = findViewById(R.id.BtnUploadTemplate);

        TVNamaDetailListing = findViewById(R.id.TVNamaDetailListing);
        TVAlamatDetailListing = findViewById(R.id.TVAlamatDetailListing);
        TVRangeHarga = findViewById(R.id.TVRangeHargaDetailListing);
        TVHargaDetailListing = findViewById(R.id.TVHargaDetailListing);
        TVHargaSewaDetailListing = findViewById(R.id.TVHargaSewaDetailListing);
        TVViewsDetailListing = findViewById(R.id.TVViewsDetailListing);
        TVLikeDetailListing = findViewById(R.id.TVLikeDetailListing);
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
        TVPoin = findViewById(R.id.TVPoinListing);
        TVJumlahGambar = findViewById(R.id.TVJumlahGambar);
        SeeAllSekitar = findViewById(R.id.SeeAllSekitar);
        SeeAllTerkait = findViewById(R.id.SeeAllTerkait);

        IVVerified = findViewById(R.id.IVVerified);
        IVAlamat = findViewById(R.id.IVAlamatDetailListing);
        IVFlowUp = findViewById(R.id.IVFlowUpAgenDetailListing);
        IVWhatsapp = findViewById(R.id.IVNoTelpAgenDetailListing);
        IVInstagram = findViewById(R.id.IVInstagramAgenDetailListing);
        IVFlowUp2 = findViewById(R.id.IVFlowUpAgen2DetailListing);
        IVWhatsapp2 = findViewById(R.id.IVNoTelpAgen2DetailListing);
        IVInstagram2 = findViewById(R.id.IVInstagramAgen2DetailListing);
        IVFavorite = findViewById(R.id.IVFavoriteDetailListing);
        IVFavoriteOn = findViewById(R.id.IVFavoriteOnDetailListing);
        IVShare = findViewById(R.id.IVShareDetailListing);
        IVSelfie = findViewById(R.id.IVSelfie);

        CBMarketable = findViewById(R.id.CBMarketable);
        CBHarga = findViewById(R.id.CBHarga);
        CBSelfie = findViewById(R.id.CBSelfie);
        CBLokasi = findViewById(R.id.CBLokasi);
        LytCBMarketable = findViewById(R.id.LytCBMarketable);
        LytCBHarga = findViewById(R.id.LytCBHarga);
        LytCBSelfie = findViewById(R.id.LytCBSelfie);
        LytCBLokasi = findViewById(R.id.LytCBLokasi);
        LytBtnHapus = findViewById(R.id.LytBtnHapusListing);

        recycleListingSekitar = findViewById(R.id.ListingSekitar);
        recycleListingTerkait = findViewById(R.id.ListingSerupa);

        mapView = findViewById(R.id.MVDetailListing);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        agenManager = new AgenManager();
        agenCoManager = new AgenManager();

        mItemsSekitar = new ArrayList<>();
        mItemsTerkait = new ArrayList<>();

        recycleListingSekitar.setLayoutManager(new LinearLayoutManager(DetailListingActivity.this, LinearLayoutManager.HORIZONTAL, false));
        AdapterSekitar = new ListingAdapter(DetailListingActivity.this, mItemsSekitar);
        recycleListingSekitar.setAdapter(AdapterSekitar);

        recycleListingTerkait.setLayoutManager(new LinearLayoutManager(DetailListingActivity.this, LinearLayoutManager.HORIZONTAL, false));
        AdapterTerkait = new ListingAdapter(DetailListingActivity.this, mItemsTerkait);
        recycleListingTerkait.setAdapter(AdapterTerkait);

        FormatCurrency currency = new FormatCurrency();

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

        status = Preferences.getKeyStatus(this);
        StrIdAgen = Preferences.getKeyIdAgen(this);

        StrJudulLaporanPdf = "Listingan_"+intentJenisProperti+"_"+intentKondisi;
        StrIntentIdAgen = intentIdAgen;
        StrIntentIdAgenCo = intentIdAgenCo;
        idlisting = intentIdListing;
        idagen = intentIdAgen;
        IdCo = intentIdAgenCo;
        NamaMaps = intentNamaListing;
        BuyerIdAgen = intentIdAgen;
        BuyerIdListing = intentIdListing;
        imageUrl = intentImg2;
        namaAgen = intentNama;
        telpAgen = intentNoTelp;
        productId = intentIdListing;
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
        StringJenisProperty = intentJenisProperti;
        StringKondisi = intentKondisi;
        StrWilayah = intentWilayah;
        StrJenis = intentJenisProperti;
        StrKondisi = intentKondisi;

        LoadListingSekitar();
        LoadListingTerkait();

        if (status.equals("3")) {
            RequestQueue queuereport = Volley.newRequestQueue(DetailListingActivity.this);
            JsonArrayRequest reqDatareport = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_CEK_OFFICER+ Preferences.getKeyIdAgen(DetailListingActivity.this),null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i = 0 ; i < response.length(); i++)
                            {
                                try {
                                    JSONObject data = response.getJSONObject(i);

                                    IsOfficer = data.getString("Officer");
                                    if (IsOfficer.equals("1")) {
                                        RequestQueue queuelokasi = Volley.newRequestQueue(DetailListingActivity.this);
                                        JsonArrayRequest reqDatalokasi = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_CEK_ISLOKASI+ intentIdListing,null,
                                                new Response.Listener<JSONArray>() {
                                                    @Override
                                                    public void onResponse(JSONArray response) {
                                                        for(int i = 0 ; i < response.length(); i++)
                                                        {
                                                            try {
                                                                JSONObject data = response.getJSONObject(i);

                                                                IsCekLokasi = data.getString("IsCekLokasi");
                                                                if (IsCekLokasi.equals("1")) {
                                                                    IVVerified.setVisibility(View.VISIBLE);
                                                                    BtnCekLokasi.setVisibility(View.GONE);
                                                                } else {
                                                                    IVVerified.setVisibility(View.INVISIBLE);
                                                                    BtnCekLokasi.setVisibility(View.VISIBLE);
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

                                        queuelokasi.add(reqDatalokasi);
                                    } else {
                                        RequestQueue queuelokasi = Volley.newRequestQueue(DetailListingActivity.this);
                                        JsonArrayRequest reqDatalokasi = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_CEK_ISLOKASI+ intentIdListing,null,
                                                new Response.Listener<JSONArray>() {
                                                    @Override
                                                    public void onResponse(JSONArray response) {
                                                        for(int i = 0 ; i < response.length(); i++)
                                                        {
                                                            try {
                                                                JSONObject data = response.getJSONObject(i);

                                                                IsCekLokasi = data.getString("IsCekLokasi");
                                                                if (IsCekLokasi.equals("1")) {
                                                                    IVVerified.setVisibility(View.VISIBLE);
                                                                    BtnCekLokasi.setVisibility(View.GONE);
                                                                } else {
                                                                    IVVerified.setVisibility(View.INVISIBLE);
                                                                    BtnCekLokasi.setVisibility(View.GONE);
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

                                        queuelokasi.add(reqDatalokasi);
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

            queuereport.add(reqDatareport);
        } else if (status.equals("2")) {
            RequestQueue queuearsip = Volley.newRequestQueue(DetailListingActivity.this);
            JsonArrayRequest reqDataarsip = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_CEK_NO_ARSIP+ intentIdListing,null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i = 0 ; i < response.length(); i++)
                            {
                                try {
                                    JSONObject data = response.getJSONObject(i);

                                    IsArsip = data.getString("NoArsip");
                                    if (IsArsip.equals("0")) {
                                        BtnTambahNoArsip.setVisibility(View.VISIBLE);
                                    } else if (IsArsip.isEmpty()) {
                                        BtnTambahNoArsip.setVisibility(View.VISIBLE);
                                    } else {
                                        BtnTambahNoArsip.setVisibility(View.GONE);
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

            queuearsip.add(reqDataarsip);

            RequestQueue queuetemplate = Volley.newRequestQueue(DetailListingActivity.this);
            JsonArrayRequest reqDataTemplate = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_CEK_TEMPLATE_DOUBLE+ intentIdListing,null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            for(int i = 0 ; i < response.length(); i++)
                            {
                                try {
                                    JSONObject data = response.getJSONObject(i);

                                    IsTemplate = data.getString("total");

                                    if (IsTemplate.isEmpty() || IsTemplate.equals("0") || IsTemplate.equals("1")) {
                                        BtnTemplateDouble.setVisibility(View.GONE);
                                        BtnDataDouble.setVisibility(View.VISIBLE);
                                    } else {
                                        BtnTemplateDouble.setVisibility(View.VISIBLE);
                                        BtnDataDouble.setVisibility(View.GONE);
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

            queuetemplate.add(reqDataTemplate);
        }

        if (intentKondisi.equals("Jual")) {
            StringHarga = currency.formatRupiah(intentHarga);
            IVShare.setOnClickListener(view -> shareDeepLink(productId));
        } else if (intentKondisi.equals("Sewa")) {
            StringHargaSewa = currency.formatRupiah(intentHargaSewa);
            IVShare.setOnClickListener(view -> shareDeepLinkSewa(productId));
        } else {
            StringHarga = currency.formatRupiah(intentHarga);
            StringHargaSewa = currency.formatRupiah(intentHargaSewa);
            IVShare.setOnClickListener(view -> shareDeepLinkJualSewa(productId));
        }

        if (status.equals("1")) {
            AgenId = "0";
            idpengguna = Preferences.getKeyIdAdmin(this);
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            LytSertifikat.setVisibility(View.VISIBLE);
            LytPJP.setVisibility(View.VISIBLE);
            LytNamaVendor.setVisibility(View.VISIBLE);
            LytTelpVendor.setVisibility(View.VISIBLE);
            BtnLihatTemplate.setVisibility(View.GONE);
            BtnLihatTemplateKosong.setVisibility(View.GONE);
            BtnUploadTemplate.setVisibility(View.GONE);
            BtnTambahGambar.setVisibility(View.GONE);
            TVPoin.setVisibility(View.VISIBLE);
            IVFlowUp.setVisibility(View.VISIBLE);
            IVFlowUp2.setVisibility(View.VISIBLE);
            LytEdit.setVisibility(View.VISIBLE);
            IVShare.setVisibility(View.VISIBLE);
            IVFavorite.setVisibility(View.GONE);
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
            AgenId = "0";
            idpengguna = Preferences.getKeyIdAdmin(this);
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            LytSertifikat.setVisibility(View.VISIBLE);
            LytPJP.setVisibility(View.VISIBLE);
            LytNamaVendor.setVisibility(View.VISIBLE);
            LytTelpVendor.setVisibility(View.VISIBLE);
            LytBtnHapus.setVisibility(View.VISIBLE);
            BtnTambahWilayah.setVisibility(View.VISIBLE);
            BtnSimpanPDF.setVisibility(View.VISIBLE);
            BtnTambahGambar.setVisibility(View.VISIBLE);
            TVPoin.setVisibility(View.VISIBLE);
            IVFlowUp.setVisibility(View.VISIBLE);
            IVFlowUp2.setVisibility(View.VISIBLE);
            LytEdit.setVisibility(View.VISIBLE);
            IVShare.setVisibility(View.GONE);
            IVFavorite.setVisibility(View.GONE);
            BtnTambahGambar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailListingActivity.this, TambahDataGambarListingActivity.class);
                    update.putExtra("IdListing", intentIdListing);
                    update.putExtra("IdAgen", intentIdAgen);
                    update.putExtra("IdAgenCo", intentIdAgenCo);
                    update.putExtra("Pjp", intentPjp);
                    update.putExtra("Priority", intentPriority);
                    update.putExtra("Banner", intentBanner);
                    update.putExtra("Marketable", intentMarketable);
                    update.putExtra("StatusHarga", intentStatusHarga);
                    update.putExtra("IsSelfie", intentIsSelfie);
                    update.putExtra("IsLokasi", intentIsLokasi);
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
            LytEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailListingActivity.this, EditDataListingActivity.class);
                    update.putExtra("IdListing", intentIdListing);
                    update.putExtra("IdAgen", intentIdAgen);
                    update.putExtra("IdAgenCo", intentIdAgenCo);
                    update.putExtra("IdInput", intentIdInput);
                    update.putExtra("NamaListing", intentNamaListing);
                    update.putExtra("Alamat", intentAlamat);
//                    update.putExtra("AlamatTemplate", intentAlamatTemplate);
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
            if (!intentIdTemplate.equals("null")) {
                BtnUploadTemplate.setText("Ganti Template");
                BtnLihatTemplate.setVisibility(View.VISIBLE);
                BtnLihatTemplateKosong.setVisibility(View.VISIBLE);
                BtnUploadTemplate.setVisibility(View.VISIBLE);
            } else {
                BtnUploadTemplate.setVisibility(View.GONE);
                BtnLihatTemplate.setVisibility(View.GONE);
                BtnLihatTemplateKosong.setVisibility(View.GONE);
            }
        } else if (status.equals("3")) {
            idpengguna = "0";
            AgenId = Preferences.getKeyIdAgen(this);
            StringNamaBuyer = Preferences.getKeyNama(this);
            LytSize.setVisibility(View.GONE);
            LytFee.setVisibility(View.GONE);
            LytTglInput.setVisibility(View.GONE);
            LytNamaVendor.setVisibility(View.GONE);
            LytTelpVendor.setVisibility(View.GONE);
            LytSelfie.setVisibility(View.GONE);
            LytCBMarketable.setVisibility(View.GONE);
            LytCBHarga.setVisibility(View.GONE);
            LytCBSelfie.setVisibility(View.GONE);
            LytCBLokasi.setVisibility(View.GONE);
            BtnLihatTemplate.setVisibility(View.GONE);
            BtnLihatTemplateKosong.setVisibility(View.GONE);
            BtnUploadTemplate.setVisibility(View.GONE);
            TVAlamatDetailListing.setVisibility(View.GONE);
            IVAlamat.setVisibility(View.GONE);
            IVFlowUp.setVisibility(View.VISIBLE);
            IVFlowUp2.setVisibility(View.VISIBLE);
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
                if (intentPriority.equals("open")) {
                    BtnUpgrade.setVisibility(View.VISIBLE);
                } else {
                    BtnUpgrade.setVisibility(View.GONE);
                }
                BtnTambahGambar.setVisibility(View.VISIBLE);
                TVPoin.setVisibility(View.VISIBLE);

                if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 70;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 70;
                        } else {
                            PoinTambah = 70 / 2;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 50;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 50;
                        } else {
                            PoinTambah = 50 / 2;
                        }
                    }
                } else if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 60;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 60;
                        } else {
                            PoinTambah = 60 / 2;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 40;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 40;
                        } else {
                            PoinTambah = 40 / 2;
                        }
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 40;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 40;
                        } else {
                            PoinTambah = 40 / 2;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 30;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 30;
                        } else {
                            PoinTambah = 30 / 2;
                        }
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 40;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 40;
                        } else {
                            PoinTambah = 40 / 2;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 20;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 20;
                        } else {
                            PoinTambah = 20 / 2;
                        }
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 40;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 40;
                        } else {
                            PoinTambah = 40 / 2;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 10;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 10;
                        } else {
                            PoinTambah = 10 / 2;
                        }
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 20;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 20;
                        } else {
                            PoinTambah = 20 / 2;
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 10;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 10;
                        } else {
                            PoinTambah = 10 / 2;
                        }
                    }
                }

                BtnTambahSelfie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent update = new Intent(DetailListingActivity.this, TambahDataSelfieListingActivity.class);
                        update.putExtra("IdListing", intentIdListing);
                        update.putExtra("PoinTambah", String.valueOf(PoinTambah));
                        startActivity(update);
                    }
                });
                BtnTambahMaps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent update = new Intent(DetailListingActivity.this, TambahDataLokasiListingActivity.class);
                        update.putExtra("IdListing", intentIdListing);
                        update.putExtra("Selfie", intentSelfie);
                        update.putExtra("PoinTambah", String.valueOf(PoinTambah));
                        startActivity(update);
                    }
                });
                BtnTambahGambar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent update = new Intent(DetailListingActivity.this, TambahDataGambarListingActivity.class);
                        update.putExtra("IdListing", intentIdListing);
                        update.putExtra("IdAgen", intentIdAgen);
                        update.putExtra("IdAgenCo", intentIdAgenCo);
                        update.putExtra("Pjp", intentPjp);
                        update.putExtra("Priority", intentPriority);
                        update.putExtra("Banner", intentBanner);
                        update.putExtra("Marketable", intentMarketable);
                        update.putExtra("StatusHarga", intentStatusHarga);
                        update.putExtra("IsSelfie", intentIsSelfie);
                        update.putExtra("IsLokasi", intentIsLokasi);
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
            } else {
                BtnTambahMaps.setVisibility(View.GONE);
                BtnTambahSelfie.setVisibility(View.GONE);
                BtnTambahPjp.setVisibility(View.GONE);
                BtnTambahBanner.setVisibility(View.GONE);
                BtnTambahCoList.setVisibility(View.GONE);
                BtnTambahGambar.setVisibility(View.GONE);
                BtnUpgrade.setVisibility(View.GONE);
                TVPoin.setVisibility(View.GONE);
            }
        } else {
            AgenId = "0";
            idpengguna = Preferences.getKeyIdCustomer(this);
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            IVFlowUp.setVisibility(View.INVISIBLE);
            LytSize.setVisibility(View.GONE);
            LytFee.setVisibility(View.GONE);
            LytTglInput.setVisibility(View.GONE);
            LytNamaVendor.setVisibility(View.GONE);
            LytTelpVendor.setVisibility(View.GONE);
            LytSelfie.setVisibility(View.GONE);
            BtnLihatTemplate.setVisibility(View.GONE);
            BtnLihatTemplateKosong.setVisibility(View.GONE);
            BtnUploadTemplate.setVisibility(View.GONE);
            IVFlowUp.setVisibility(View.INVISIBLE);
            IVFlowUp2.setVisibility(View.INVISIBLE);
            IVAlamat.setVisibility(View.GONE);
            TVAlamatDetailListing.setVisibility(View.GONE);
            TVPoin.setVisibility(View.GONE);
            LytCBMarketable.setVisibility(View.GONE);
            LytCBHarga.setVisibility(View.GONE);
            LytCBSelfie.setVisibility(View.GONE);
            LytCBLokasi.setVisibility(View.GONE);
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

        if (intentIdAgenCo.equals("0")) {
            agen2.setVisibility(View.GONE);
        } else if (intentIdAgenCo.equals(intentIdAgen)) {
            agen2.setVisibility(View.GONE);
        } else {
            LoadCo();
            agen2.setVisibility(View.VISIBLE);
        }

        CountLike(idlisting);
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
        IVFlowUp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(DetailListingActivity.this, FollowUpActivity.class);
                update.putExtra("IdListing", intentIdListing);
                update.putExtra("IdAgen", intentIdAgen);
                startActivity(update);
            }
        });
        BtnLihatTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentPriority.equals("open")){
                    String url = "https://app.gooproper.com/GooProper/newtemplate/" + idlisting;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    String url = "https://app.gooproper.com/GooProper/newtemplateexclusive/" + idlisting;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            }
        });
        BtnLihatTemplateKosong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentPriority.equals("open")){
                    String url = "https://app.gooproper.com/GooProper/newtemplateblank/" + idlisting;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } else {
                    String url = "https://app.gooproper.com/GooProper/newtemplateblankexclusive/" + idlisting;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }

            }
        });
        BtnUploadTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(DetailListingActivity.this, GantiTemplateActivity.class);
                update.putExtra("update",1);
                update.putExtra("IdTemplate",intentIdTemplate);
                startActivity(update);

            }
        });
        BtnTambahPjp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 20;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 20;
                            } else {
                                PoinTambah = 20 / 2;
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 40;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 40;
                            } else {
                                PoinTambah = 40 / 2;
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 20;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 20;
                        } else {
                            PoinTambah = 20 / 2;
                        }
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 30;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 30;
                            } else {
                                PoinTambah = 30 / 2;
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 20;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 20;
                            } else {
                                PoinTambah = 20 / 2;
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 10;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 10;
                        } else {
                            PoinTambah = 10 / 2;
                        }
                    }
                }

                ShowTambahPjp(PoinTambah, intentIdListing);
            }
        });
        BtnTambahBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 20;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 20;
                            } else {
                                PoinTambah = 20 / 2;
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 20;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 20;
                            } else {
                                PoinTambah = 20 / 2;
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 10;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 10;
                        } else {
                            PoinTambah = 10 / 2;
                        }
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 10;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 10;
                            } else {
                                PoinTambah = 10 / 2;
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 20;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 20;
                            } else {
                                PoinTambah = 20 / 2;
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 10;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 10;
                        } else {
                            PoinTambah = 10 / 2;
                        }
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 20;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 20;
                            } else {
                                PoinTambah = 20 / 2;
                            }
                        } else {
                            if (intentIdAgenCo.equals("0")) {
                                PoinTambah = 0;
                            } else if (intentIdAgenCo.equals(intentIdAgen)) {
                                PoinTambah = 0;
                            } else {
                                PoinTambah = 0 / 2;
                            }
                        }
                    } else {
                        if (intentIdAgenCo.equals("0")) {
                            PoinTambah = 0;
                        } else if (intentIdAgenCo.equals(intentIdAgen)) {
                            PoinTambah = 0;
                        } else {
                            PoinTambah = 0 / 2;
                        }
                    }
                }

                ShowTambahBanner(PoinTambah, intentIdListing);
            }
        });
        BtnTambahCoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            PoinKurang = 120 / 2;
                        } else {
                            PoinKurang = 100 / 2;
                        }
                    } else {
                        PoinKurang = 50 / 2;
                    }
                } else if (intentPriority.equals("exclusive") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            PoinKurang = 100 / 2;
                        } else {
                            PoinKurang = 80 / 2;
                        }
                    } else {
                        PoinKurang = 40 / 2;
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            PoinKurang = 70 / 2;
                        } else {
                            PoinKurang = 60 / 2;
                        }
                    } else {
                        PoinKurang = 30 / 2;
                    }
                } else if (intentPriority.equals("open") && !intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            PoinKurang = 60 / 2;
                        } else {
                            PoinKurang = 40 / 2;
                        }
                    } else {
                        PoinKurang = 20 / 2;
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            PoinKurang = 50 / 2;
                        } else {
                            PoinKurang = 20 / 2;
                        }
                    } else {
                        PoinKurang = 10 / 2;
                    }
                } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Tidak")) {
                    if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                        if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
                            PoinKurang = 30 / 2;
                        } else {
                            PoinKurang = 20 / 2;
                        }
                    } else {
                        PoinKurang = 10 / 2;
                    }
                }

                ShowTambahCoList(PoinKurang, intentIdListing);
            }
        });
        BtnTambahWilayah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowTambahWilayah(intentIdListing);
            }
        });
        BtnTambahNoArsip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowTambahArsip(intentIdListing);
            }
        });
        BtnCekLokasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent update = new Intent(DetailListingActivity.this, TambahCekLokasiActivity.class);
                update.putExtra("IdListing", intentIdListing);
                startActivity(update);
            }
        });
        BtnSimpanPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpanPdf(intentIdListing);
            }
        });
        BtnTemplateDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TemplateDouble();
            }
        });
        BtnDataDouble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListingDouble();
            }
        });
        BtnHapusListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ListingDelete();
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
                if (status.equals("1")) {
                    agen.setVisibility(View.GONE);
                    idagen = agenid;
                } else if (status.equals("2")) {
                    agen.setVisibility(View.GONE);
                    idagen = agenid;
                } else {
                    agen.setVisibility(View.GONE);
                }
            } else {
                agen.setVisibility(View.VISIBLE);
                idagen = intentIdAgen;
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
                    String priceText = currency.formatRupiah(intentHarga);
                    String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                    TVHargaDetailListing.setText(truncatedprice);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                }
            } else if (intentKondisi.equals("Sewa")) {
                if (intentHargaSewa.isEmpty()) {
                    TVHargaSewaDetailListing.setText("Rp. -");
                    TVHargaDetailListing.setVisibility(View.GONE);
                } else {
                    String priceText = currency.formatRupiah(intentHargaSewa);
                    String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                    TVHargaSewaDetailListing.setText(truncatedprice);
                    TVHargaDetailListing.setVisibility(View.GONE);
                }
            } else {
                if (intentHarga.isEmpty()) {
                    if (intentHargaSewa.isEmpty()) {
                        TVHargaDetailListing.setText("Rp. -");
                        TVHargaSewaDetailListing.setText("Rp. -");
                    } else {
                        String priceText = currency.formatRupiah(intentHargaSewa);
                        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                        TVHargaDetailListing.setText("Rp. - /");
                        TVHargaSewaDetailListing.setText(truncatedprice);
                    }
                } else {
                    if (intentHargaSewa.isEmpty()) {
                        String priceText = currency.formatRupiah(intentHarga);
                        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                        TVHargaDetailListing.setText(truncatedprice + " /");
                        TVHargaSewaDetailListing.setText("Rp. -");
                    } else {
                        String priceText = currency.formatRupiah(intentHarga);
                        String truncatedprice = truncateTextWithEllipsisPrice(priceText);
                        String priceTextSewa = currency.formatRupiah(intentHargaSewa);
                        String truncatedpriceSewa = truncateTextWithEllipsisPrice(priceTextSewa);
                        TVHargaDetailListing.setText(truncatedprice + " /");
                        TVHargaSewaDetailListing.setText(truncatedpriceSewa);
                    }
                }
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
                    Intent update = new Intent(DetailListingActivity.this, DetailAgenListingActivity.class);
                    update.putExtra("update", 1);
                    update.putExtra("IdAgen", intentIdAgen);
                    startActivity(update);
                }
            });
            TVNamaAgen2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent update = new Intent(DetailListingActivity.this, DetailAgenListingActivity.class);
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
    private void updatepoin() {
        boolean isCBLokasiChecked = CBLokasi.isChecked();
        boolean isCBSelfieChecked = CBSelfie.isChecked();

        if (isCBLokasiChecked && isCBSelfieChecked) {
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 30;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 10) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 20;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 20) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 30;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 30;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 30) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
                int UpdatePoin = (Poin * 2) + 10;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else {
                int UpdatePoin = ((Poin * 2) + 10) / 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            }
        } else if (isCBLokasiChecked && isCBSelfieChecked) {
            if (StrIntentIdAgenCo.equals("0")) {
                int UpdatePoin = Poin * 2;
                TVPoin.setText(String.valueOf(UpdatePoin));
            } else if (StrIntentIdAgenCo.equals(StrIntentIdAgen)) {
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
    private void LoadListingSekitar() {
        RequestQueue queue = Volley.newRequestQueue(DetailListingActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, String.format(ServerApi.URL_GET_LISTING_SEKITAR,StrWilayah,StrJenis,StrKondisi), null,
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
        RequestQueue queue = Volley.newRequestQueue(DetailListingActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, String.format(ServerApi.URL_GET_LISTING_TERKAIT,StrJenis,StrKondisi), null,
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
    private void LoadCo() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_CO_LISTING + IdCo, null,
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
                                            String deepLinkUrl = "https://gooproper.com/listing/" + idlisting;
                                            String message = "Halo! Saya Manager, ingin menanyakan update pada listingan " + StringNamaListing + " yang beralamat di " + StringAlamat + ".\nDetail Listingan :\n" + deepLinkUrl;
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
                                            String deepLinkUrl = "https://gooproper.com/listing/" + idlisting;
                                            String message = "Halo! Saya Admin, ingin menanyakan update pada listingan " + StringNamaListing + " yang beralamat di " + StringAlamat + ".\nDetail Listingan :\n" + deepLinkUrl;
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
                                            String deepLinkUrl = "https://gooproper.com/listing/" + idlisting;
                                            String message = "Halo! Saya " + StringNamaBuyer + ", ingin melakukan cobroke pada listingan " + StringNamaListing + " yang beralamat di " + StringAlamat + ".\nApakah bersedia? \nDetail Listingan :\n" + deepLinkUrl;
                                            String url = "https://api.whatsapp.com/send?phone=+62" + TelpCo + "&text=" + message;
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    });
                                } else {
                                    IVWhatsapp2.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            String deepLinkUrl = "https://gooproper.com/listing/" + idlisting;
                                            String message = "Halo! Saya " + StringNamaBuyer + ", ingin menanyakan informasi mengenai listingan " + StringNamaListing + " yang beralamat di " + StringAlamat + ".\nApakah masih ada? atau ada update terbaru?\nDetail Listingan :\n" + deepLinkUrl;
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
    private void CountLike(String listingId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LIKE + listingId, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("fav");

                                int like = Integer.parseInt(countlike);

                                if (like >= 50) {
                                    TVLikeDetailListing.setText(countlike + " Favorite");
                                } else {
                                    TVLikeDetailListing.setVisibility(View.INVISIBLE);
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
    private void TemplateDouble() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TEMPLATE_DOUBLE, new Response.Listener<String>() {
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

                dialogTitle.setText("Listing Deleted");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
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

                dialogTitle.setText("Gagal Delete Listing");
                ok.setVisibility(View.GONE);

                Glide.with(DetailListingActivity.this)
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
                map.put("IdTemplate", intentIdTemplate);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void ListingDouble() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_LISTING_DOUBLE, new Response.Listener<String>() {
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

                dialogTitle.setText("Listing Deleted");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
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

                dialogTitle.setText("Gagal Delete Listing");
                ok.setVisibility(View.GONE);

                Glide.with(DetailListingActivity.this)
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
                map.put("IdListing", idlisting);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void ListingDelete() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_LISTING_DELETE, new Response.Listener<String>() {
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

                dialogTitle.setText("Listing Deleted");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
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

                dialogTitle.setText("Gagal Delete Listing");
                ok.setVisibility(View.GONE);

                Glide.with(DetailListingActivity.this)
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
                map.put("IdListing", idlisting);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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

//        tambahagen.setText(selectedData.getName());
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
    public void ShowTambahBanner(int Poin, String IdListing) {
        CustomDialogBanner = new Dialog(DetailListingActivity.this);
        CustomDialogBanner.requestWindowFeature(Window.FEATURE_NO_TITLE);
        CustomDialogBanner.setContentView(R.layout.dialog_tambah_banner);

        if (CustomDialogBanner.getWindow() != null) {
            CustomDialogBanner.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextInputEditText Ukuran = CustomDialogBanner.findViewById(R.id.ETUkuranBanner);
        TextInputEditText UkuranCustom = CustomDialogBanner.findViewById(R.id.ETUkuranBannerCustom);

        TextInputLayout LytUkuran = CustomDialogBanner.findViewById(R.id.lytUkuranBanner);
        TextInputLayout LytUkuranCustom = CustomDialogBanner.findViewById(R.id.lytUkuranBannerCustom);

        Ukuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailListingActivity.this, R.style.CustomAlertDialogStyle);
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

        Button Batal = CustomDialogBanner.findViewById(R.id.BtnBatal);
        Button Simpan = CustomDialogBanner.findViewById(R.id.BtnSimpan);

        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogBanner.dismiss();
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Ukuran.getText().toString().isEmpty() && UkuranCustom.getText().toString().isEmpty()) {
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

                    dialogTitle.setText("Harap Pilih Size Banner Terlebih Dahulu");
                    cobalagi.setVisibility(View.GONE);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    Glide.with(DetailListingActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    if (!Ukuran.getText().toString().isEmpty()) {
                        pDialog.setMessage("Menyimpan Data");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        RequestQueue requestQueue = Volley.newRequestQueue(DetailListingActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_BANNER_LISTING,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        pDialog.cancel();
                                        try {
                                            JSONObject res = new JSONObject(response);
                                            String status = res.getString("Status");
                                            if (status.equals("Sukses")) {
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

                                                dialogTitle.setText("Berhasil Menambahkan Banner Listingan");
                                                cobalagi.setVisibility(View.GONE);

                                                ok.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        customDialog.dismiss();
                                                        CustomDialogBanner.dismiss();
                                                    }
                                                });

                                                Glide.with(DetailListingActivity.this)
                                                        .load(R.mipmap.ic_yes)
                                                        .transition(DrawableTransitionOptions.withCrossFade())
                                                        .into(gifimage);

                                                customDialog.show();
                                            } else if (status.equals("Error")) {
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

                                                dialogTitle.setText("Gagal Menambahkan Banner Listingan");
                                                ok.setVisibility(View.GONE);

                                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {
                                                        customDialog.dismiss();
                                                    }
                                                });

                                                Glide.with(DetailListingActivity.this)
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

                                        dialogTitle.setText("Terdapat Masalah Jaringan");
                                        ok.setVisibility(View.GONE);

                                        cobalagi.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                customDialog.dismiss();
                                            }
                                        });

                                        Glide.with(DetailListingActivity.this)
                                                .load(R.mipmap.ic_eror_network_foreground)
                                                .transition(DrawableTransitionOptions.withCrossFade())
                                                .into(gifimage);

                                        customDialog.show();
                                    }
                                }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();

                                String Keterangan = "Tambah Banner Ukuran " + Ukuran.getText().toString();

                                map.put("IdListing", IdListing);
                                map.put("Keterangan", Keterangan);
                                map.put("PoinTambahan", String.valueOf(Poin));
                                map.put("Banner", "Ya");
                                map.put("Size", Ukuran.getText().toString());
                                map.put("PoinBerkurang", "0");
                                System.out.println(map);

                                return map;
                            }
                        };

                        requestQueue.add(stringRequest);
                    } else {
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

                        dialogTitle.setText("Harap Pilih Size Banner Terlebih Dahulu");
                        cobalagi.setVisibility(View.GONE);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(DetailListingActivity.this)
                                .load(R.drawable.alert)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }
            }
        });

        CustomDialogBanner.show();
    }
    public void ShowTambahCoList(int Poin, String IdListing) {
        CustomDialogCoList = new Dialog(DetailListingActivity.this);
        CustomDialogCoList.requestWindowFeature(Window.FEATURE_NO_TITLE);
        CustomDialogCoList.setContentView(R.layout.dialog_tambah_colist);

        if (CustomDialogCoList.getWindow() != null) {
            CustomDialogCoList.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextInputEditText ETNamaAgen = CustomDialogCoList.findViewById(R.id.ETNamaAgen);
        TextInputEditText ETIdAgen = CustomDialogCoList.findViewById(R.id.ETIdAgen);

        ETIdAgen.setVisibility(View.GONE);

        TextInputLayout LytDaftarAgen = CustomDialogCoList.findViewById(R.id.lytDaftarAgen);

        ETNamaAgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agenManager.fetchDataFromApi(DetailListingActivity.this, new AgenManager.ApiCallback() {
                    @Override
                    public void onSuccess(List<AgenManager.DataItem> dataList) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(DetailListingActivity.this, R.style.CustomAlertDialogStyle);
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
//                        showAlertDialog(dataList);
                    }

                    @Override
                    public void onError(String errorMessage) {
                    }
                });
            }
        });

        Button Batal = CustomDialogCoList.findViewById(R.id.BtnBatal);
        Button Simpan = CustomDialogCoList.findViewById(R.id.BtnSimpan);

        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogCoList.dismiss();
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ETIdAgen.getText().toString().isEmpty()) {
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

                    dialogTitle.setText("Harap Pilih Agen Terlebih Dahulu");
                    cobalagi.setVisibility(View.GONE);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    Glide.with(DetailListingActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    pDialog.setMessage("Menyimpan Data");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(DetailListingActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_COLIST_LISTING,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pDialog.cancel();
                                    try {
                                        JSONObject res = new JSONObject(response);
                                        String status = res.getString("Status");
                                        if (status.equals("Sukses")) {
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

                                            dialogTitle.setText("Berhasil Menambahkan Agen CoList");
                                            cobalagi.setVisibility(View.GONE);

                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    customDialog.dismiss();
                                                    CustomDialogCoList.dismiss();
                                                }
                                            });

                                            Glide.with(DetailListingActivity.this)
                                                    .load(R.mipmap.ic_yes)
                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                    .into(gifimage);

                                            customDialog.show();
                                        } else if (status.equals("Error")) {
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

                                            dialogTitle.setText("Gagal Menambahkan Agen CoList");
                                            ok.setVisibility(View.GONE);

                                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    customDialog.dismiss();
                                                }
                                            });

                                            Glide.with(DetailListingActivity.this)
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

                                    dialogTitle.setText("Terdapat Masalah Jaringan");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                        }
                                    });

                                    Glide.with(DetailListingActivity.this)
                                            .load(R.mipmap.ic_eror_network_foreground)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();

                            String Keterangan = "Tambah Agen CoList dengan " + ETNamaAgen.getText().toString();

                            map.put("IdListing", IdListing);
                            map.put("Keterangan", Keterangan);
                            map.put("PoinTambahan", "0");
                            map.put("IdAgenCo", ETIdAgen.getText().toString());
                            map.put("PoinBerkurang", String.valueOf(Poin));
                            System.out.println(map);

                            return map;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
            }
        });

        CustomDialogCoList.show();
    }
    public void ShowTambahWilayah(String IdListing) {
        Dialog customDialog = new Dialog(DetailListingActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_tambah_wilayah);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextInputEditText ETWilayah = customDialog.findViewById(R.id.ETWilayah);

        Button Batal = customDialog.findViewById(R.id.BtnBatal);
        Button Simpan = customDialog.findViewById(R.id.BtnSimpan);

        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ETWilayah.getText().toString().isEmpty()) {
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

                    dialogTitle.setText("Harap Masukkan Wilayah");
                    cobalagi.setVisibility(View.GONE);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    Glide.with(DetailListingActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    pDialog.setMessage("Menyimpan Data");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(DetailListingActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_WILAYAH,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pDialog.cancel();
                                    try {
                                        JSONObject res = new JSONObject(response);
                                        String status = res.getString("Status");
                                        if (status.equals("Sukses")) {
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

                                            dialogTitle.setText("Berhasil Menambahkan Wilayah");
                                            cobalagi.setVisibility(View.GONE);

                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    customDialog.dismiss();
                                                }
                                            });

                                            Glide.with(DetailListingActivity.this)
                                                    .load(R.mipmap.ic_yes)
                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                    .into(gifimage);

                                            customDialog.show();
                                        } else if (status.equals("Error")) {
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

                                            dialogTitle.setText("Gagal Menambahkan Wilayah");
                                            ok.setVisibility(View.GONE);

                                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    customDialog.dismiss();
                                                }
                                            });

                                            Glide.with(DetailListingActivity.this)
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

                                    dialogTitle.setText("Terdapat Masalah Jaringan");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                        }
                                    });

                                    Glide.with(DetailListingActivity.this)
                                            .load(R.mipmap.ic_eror_network_foreground)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();

                            map.put("IdListing", IdListing);
                            map.put("Wilayah", ETWilayah.getText().toString());
                            System.out.println(map);

                            return map;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
            }
        });

        customDialog.show();
    }
    public void ShowTambahArsip(String IdListing) {
        CustomDialogArsip = new Dialog(DetailListingActivity.this);
        CustomDialogArsip.requestWindowFeature(Window.FEATURE_NO_TITLE);
        CustomDialogArsip.setContentView(R.layout.dialog_tambah_no_arsip);

        if (CustomDialogArsip.getWindow() != null) {
            CustomDialogArsip.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextInputEditText ETNoArsip = CustomDialogArsip.findViewById(R.id.ETNoArsip);

        Button Batal = CustomDialogArsip.findViewById(R.id.BtnBatal);
        Button Simpan = CustomDialogArsip.findViewById(R.id.BtnSimpan);

        if (StringJenisProperty.equals("Rumah")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("RMHJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("RMHS");
            } else {
                ETNoArsip.setText("RMHJ");
            }
        } else if (StringJenisProperty.equals("Ruko")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("RKOJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("RKOS");
            } else {
                ETNoArsip.setText("RKOJ");
            }
        } else if (StringJenisProperty.equals("Tanah")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("TNHJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("TNHS");
            } else {
                ETNoArsip.setText("TNHJ");
            }
        } else if (StringJenisProperty.equals("Gudang")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("GDGJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("GDGS");
            } else {
                ETNoArsip.setText("GDGJ");
            }
        } else if (StringJenisProperty.equals("Ruang Usaha")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("RUHJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("RUHS");
            } else {
                ETNoArsip.setText("RUHJ");
            }
        } else if (StringJenisProperty.equals("Villa")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("VLAJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("VLAS");
            } else {
                ETNoArsip.setText("VLAJ");
            }
        } else if (StringJenisProperty.equals("Apartemen")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("APRJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("APRS");
            } else {
                ETNoArsip.setText("APRJ");
            }
        } else if (StringJenisProperty.equals("Pabrik")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("RUHJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("RUHS");
            } else {
                ETNoArsip.setText("RUHJ");
            }
        } else if (StringJenisProperty.equals("Kantor")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("RUHJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("RUHS");
            } else {
                ETNoArsip.setText("RUHJ");
            }
        } else if (StringJenisProperty.equals("Hotel")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("HTLJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("HTLS");
            } else {
                ETNoArsip.setText("HTLJ");
            }
        } else if (StringJenisProperty.equals("Rukost")) {
            if (StringKondisi.equals("Jual")){
                ETNoArsip.setText("RKSJ");
            } else if (StringKondisi.equals("Sewa")) {
                ETNoArsip.setText("RKSS");
            } else {
                ETNoArsip.setText("RKSJ");
            }
        } else {
            ETNoArsip.setText("");
        }

        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialogArsip.dismiss();
            }
        });

        Simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ETNoArsip.getText().toString().isEmpty()) {
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

                    dialogTitle.setText("Harap Masukkan No Arsip");
                    cobalagi.setVisibility(View.GONE);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    Glide.with(DetailListingActivity.this)
                            .load(R.drawable.alert)
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifimage);

                    customDialog.show();
                } else {
                    pDialog.setMessage("Menyimpan Data");
                    pDialog.setCancelable(false);
                    pDialog.show();

                    RequestQueue requestQueue = Volley.newRequestQueue(DetailListingActivity.this);

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_NO_ARSIP,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    pDialog.cancel();
                                    try {
                                        JSONObject res = new JSONObject(response);
                                        String status = res.getString("Status");
                                        if (status.equals("Sukses")) {
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

                                            dialogTitle.setText("Berhasil Menambahkan No Arsip");
                                            cobalagi.setVisibility(View.GONE);

                                            ok.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    customDialog.dismiss();
                                                    CustomDialogArsip.dismiss();
                                                }
                                            });

                                            Glide.with(DetailListingActivity.this)
                                                    .load(R.mipmap.ic_yes)
                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                    .into(gifimage);

                                            customDialog.show();
                                        } else if (status.equals("Error")) {
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

                                            dialogTitle.setText("Gagal Menambahkan No Arsip");
                                            ok.setVisibility(View.GONE);

                                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    customDialog.dismiss();
                                                }
                                            });

                                            Glide.with(DetailListingActivity.this)
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

                                    dialogTitle.setText("Terdapat Masalah Jaringan");
                                    ok.setVisibility(View.GONE);

                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            customDialog.dismiss();
                                        }
                                    });

                                    Glide.with(DetailListingActivity.this)
                                            .load(R.mipmap.ic_eror_network_foreground)
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifimage);

                                    customDialog.show();
                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> map = new HashMap<>();

                            map.put("IdListing", IdListing);
                            map.put("NoArsip", ETNoArsip.getText().toString());
                            System.out.println(map);

                            return map;
                        }
                    };

                    requestQueue.add(stringRequest);
                }
            }
        });

        CustomDialogArsip.show();
    }
    public void ShowTambahPjp(int Poin, String IdListing) {
        CustomDialogPjp = new Dialog(DetailListingActivity.this);
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

                    dialogTitle.setText("Harap Upload PJP");
                    cobalagi.setVisibility(View.GONE);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    Glide.with(DetailListingActivity.this)
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

                                                                            RequestQueue requestQueue = Volley.newRequestQueue(DetailListingActivity.this);

                                                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_PJP_LISTING,
                                                                                    new Response.Listener<String>() {
                                                                                        @Override
                                                                                        public void onResponse(String response) {
                                                                                            pDialog.cancel();
                                                                                            try {
                                                                                                JSONObject res = new JSONObject(response);
                                                                                                String status = res.getString("Status");
                                                                                                if (status.equals("Sukses")) {
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

                                                                                                    dialogTitle.setText("Berhasil Menambahkan PJP");
                                                                                                    cobalagi.setVisibility(View.GONE);

                                                                                                    ok.setOnClickListener(new View.OnClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(View view) {
                                                                                                            customDialog.dismiss();
                                                                                                            CustomDialogPjp.dismiss();
                                                                                                        }
                                                                                                    });

                                                                                                    Glide.with(DetailListingActivity.this)
                                                                                                            .load(R.mipmap.ic_yes)
                                                                                                            .transition(DrawableTransitionOptions.withCrossFade())
                                                                                                            .into(gifimage);

                                                                                                    customDialog.show();
                                                                                                } else if (status.equals("Error")) {
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

                                                                                                    dialogTitle.setText("Gagal Menambahkan PJP");
                                                                                                    ok.setVisibility(View.GONE);

                                                                                                    cobalagi.setOnClickListener(new View.OnClickListener() {
                                                                                                        @Override
                                                                                                        public void onClick(View view) {
                                                                                                            customDialog.dismiss();
                                                                                                        }
                                                                                                    });

                                                                                                    Glide.with(DetailListingActivity.this)
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

                                                                                            dialogTitle.setText("Terdapat Masalah Jaringan");
                                                                                            ok.setVisibility(View.GONE);

                                                                                            cobalagi.setOnClickListener(new View.OnClickListener() {
                                                                                                @Override
                                                                                                public void onClick(View view) {
                                                                                                    customDialog.dismiss();
                                                                                                }
                                                                                            });

                                                                                            Glide.with(DetailListingActivity.this)
                                                                                                    .load(R.mipmap.ic_eror_network_foreground)
                                                                                                    .transition(DrawableTransitionOptions.withCrossFade())
                                                                                                    .into(gifimage);

                                                                                            customDialog.show();
                                                                                        }
                                                                                    }) {
                                                                                @Override
                                                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                                                    Map<String, String> map = new HashMap<>();

                                                                                    String Keterangan = "Tambah PJP";

                                                                                    map.put("IdListing", IdListing);
                                                                                    map.put("Keterangan", Keterangan);
                                                                                    map.put("PoinTambahan", String.valueOf(Poin));
                                                                                    map.put("ImgPjp", PJPHal1);
                                                                                    map.put("ImgPjp1", PJPHal2);
                                                                                    map.put("PoinBerkurang", "0");
                                                                                    System.out.println(map);

                                                                                    return map;
                                                                                }
                                                                            };

                                                                            requestQueue.add(stringRequest);

                                                                        })
                                                                        .addOnFailureListener(exception -> {
                                                                            pDialog.cancel();
                                                                            Toast.makeText(DetailListingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                                                        });
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                pDialog.cancel();
                                                                Toast.makeText(DetailListingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });

                                            })
                                            .addOnFailureListener(exception -> {
                                                pDialog.cancel();
                                                Toast.makeText(DetailListingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(DetailListingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });

        CustomDialogPjp.show();
    }
    public void tampilkanPreview(View view) {
        Uri imageUri = getImageUriFromImageView((ImageView) view);
        tampilkanDialogGambarBesar(imageUri);
    }
    private void SimpanPdf(String Id) {
        PDDetailListing.setMessage("Menyimpan Data");
        PDDetailListing.setCancelable(false);
        PDDetailListing.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_PDF + Id, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray) {
                        PDDetailListing.cancel();
                        try {
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String IdListing = jsonObject.getString("IdListing");
                                String IdAgen = jsonObject.getString("IdAgen");
                                String IdAgenCo = jsonObject.getString("IdAgenCo");
                                String IdInput = jsonObject.getString("IdInput");
                                String IdVendor = jsonObject.getString("IdVendor");
                                String NoArsip = jsonObject.getString("NoArsip");
                                String NamaListing = jsonObject.getString("NamaListing");
                                String Alamat = jsonObject.getString("Alamat");
                                String AlamatTemplate = jsonObject.getString("AlamatTemplate");
                                String Latitude = jsonObject.getString("Latitude");
                                String Longitude = jsonObject.getString("Longitude");
                                String Location = jsonObject.getString("Location");
                                String Wilayah = jsonObject.getString("Wilayah");
                                String Selfie = jsonObject.getString("Selfie");
                                String Wide = jsonObject.getString("Wide");
                                String Land = jsonObject.getString("Land");
                                String Dimensi = jsonObject.getString("Dimensi");
                                String Listrik = jsonObject.getString("Listrik");
                                String Level = jsonObject.getString("Level");
                                String Bed = jsonObject.getString("Bed");
                                String Bath = jsonObject.getString("Bath");
                                String BedArt = jsonObject.getString("BedArt");
                                String BathArt = jsonObject.getString("BathArt");
                                String Garage = jsonObject.getString("Garage");
                                String Carpot = jsonObject.getString("Carpot");
                                String Hadap = jsonObject.getString("Hadap");
                                String SHM = jsonObject.getString("SHM");
                                String HGB = jsonObject.getString("HGB");
                                String HSHP = jsonObject.getString("HSHP");
                                String PPJB = jsonObject.getString("PPJB");
                                String Stratatitle = jsonObject.getString("Stratatitle");
                                String AJB = jsonObject.getString("AJB");
                                String PetokD = jsonObject.getString("PetokD");
                                String PJP = jsonObject.getString("Pjp");
                                String ImgShm = jsonObject.getString("ImgSHM");
                                String ImgHgb = jsonObject.getString("ImgHGB");
                                String ImgHshp = jsonObject.getString("ImgHSHP");
                                String ImgPpjb = jsonObject.getString("ImgPPJB");
                                String ImgStatatitle = jsonObject.getString("ImgStratatitle");
                                String ImgAjb = jsonObject.getString("ImgAJB");
                                String ImgPetokD = jsonObject.getString("ImgPetokD");
                                String ImgPjp = jsonObject.getString("ImgPjp");
                                String ImgPjp1 = jsonObject.getString("ImgPjp1");
                                String NoCertificate = jsonObject.getString("NoCertificate");
                                String Pbb = jsonObject.getString("Pbb");
                                String JenisProperti = jsonObject.getString("JenisProperti");
                                String JenisCertificate = jsonObject.getString("JenisCertificate");
                                String SumberAir = jsonObject.getString("SumberAir");
                                String Kondisi = jsonObject.getString("Kondisi");
                                String Deskripsi = jsonObject.getString("Deskripsi");
                                String Prabot = jsonObject.getString("Prabot");
                                String KetPrabot = jsonObject.getString("KetPrabot");
                                String Priority = jsonObject.getString("Priority");
                                String Ttd = jsonObject.getString("Ttd");
                                String Banner = jsonObject.getString("Banner");
                                String Size = jsonObject.getString("Size");
                                String Harga = jsonObject.getString("Harga");
                                String HargaSewa = jsonObject.getString("HargaSewa");
                                String RangeHarga = jsonObject.getString("RangeHarga");
                                String TglInput = jsonObject.getString("TglInput");
                                String Img1 = jsonObject.getString("Img1");
                                String Img2 = jsonObject.getString("Img2");
                                String Img3 = jsonObject.getString("Img3");
                                String Img4 = jsonObject.getString("Img4");
                                String Img5 = jsonObject.getString("Img5");
                                String Img6 = jsonObject.getString("Img6");
                                String Img7 = jsonObject.getString("Img7");
                                String Img8 = jsonObject.getString("Img8");
                                String Img9 = jsonObject.getString("Img9");
                                String Img10 = jsonObject.getString("Img10");
                                String Img11 = jsonObject.getString("Img11");
                                String Img12 = jsonObject.getString("Img12");
                                String Video = jsonObject.getString("Video");
                                String LinkFacebook = jsonObject.getString("LinkFacebook");
                                String LinkTiktok = jsonObject.getString("LinkTiktok");
                                String LinkInstagram = jsonObject.getString("LinkInstagram");
                                String LinkYaoutube = jsonObject.getString("LinkYoutube");
                                String IsAdmin = jsonObject.getString("IsAdmin");
                                String IsManager = jsonObject.getString("IsManager");
                                String IsRejected = jsonObject.getString("IsRejected");
                                String Sold = jsonObject.getString("Sold");
                                String Rented = jsonObject.getString("Rented");
                                String SoldAgen = jsonObject.getString("SoldAgen");
                                String RentedAgen = jsonObject.getString("RentedAgen");
                                String View = jsonObject.getString("View");
                                String Marketable = jsonObject.getString("Marketable");
                                String StatusHarga = jsonObject.getString("StatusHarga");
                                String IsSelfie = jsonObject.getString("IsSelfie");
                                String IsLokasi = jsonObject.getString("IsLokasi");
                                String Fee = jsonObject.getString("Fee");
                                String NoKtp = jsonObject.getString("NoKtp");
                                String ImgKtp = jsonObject.getString("ImgKtp");
                                String TipeHarga = jsonObject.getString("TipeHarga");
                                String Pending = jsonObject.getString("Pending");
                                String IsCekLokasi = jsonObject.getString("IsCekLokasi");
                                String IsDouble = jsonObject.getString("IsDouble");
                                String IsDelete = jsonObject.getString("IsDelete");
                                String NamaLengkap = jsonObject.getString("NamaLengkap");
                                String NoTelp = jsonObject.getString("NoTelp");
                                String TglLahir = jsonObject.getString("TglLahir");
                                String NIK = jsonObject.getString("Nik");
                                String NoRekening = jsonObject.getString("NoRekening");
                                String Bank = jsonObject.getString("Bank");
                                String AtasNama = jsonObject.getString("AtasNama");
                                String IdTemplate = jsonObject.getString("IdTemplate");
                                String Template = jsonObject.getString("Template");
                                String TemplateBlank = jsonObject.getString("TemplateBlank");
                                String NamaAgen = jsonObject.getString("NamaAgen");
                                String NamaAgenCo = jsonObject.getString("NamaAgenCo");

                                String No = String.valueOf(i + 1);

                                FormatCurrency currency = new FormatCurrency();

                                File sdCard = Environment.getExternalStorageDirectory();
                                File directory = new File(sdCard.getAbsolutePath() + "/Download");
                                directory.mkdirs();

                                File file = new File(directory, "Listing_"+NoArsip+".pdf");
                                Document document = new Document();

                                PdfWriter.getInstance(document, new FileOutputStream(file));
                                document.open();

                                Paragraph archiveNumber = new Paragraph(NoArsip);
                                archiveNumber.setAlignment(Element.ALIGN_RIGHT);
                                document.add(archiveNumber);

                                Paragraph title = new Paragraph("DATA LISTING", FontFactory.getFont(FontFactory.TIMES_ROMAN, 20, Font.BOLD));
                                title.setAlignment(Element.ALIGN_CENTER);
                                document.add(title);

                                Paragraph paragraphpjp = new Paragraph(PJP, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL));
                                paragraphpjp.setAlignment(Element.ALIGN_CENTER);
                                document.add(paragraphpjp);

                                document.add(new Paragraph("A. Informasi Pemilik", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));

                                Paragraph paragraph1 = new Paragraph();
                                paragraph1.add(Chunk.TABBING);
                                paragraph1.add(new Chunk("1. Nama Pemilik", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph1.add(Chunk.TABBING);
                                paragraph1.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph1.add(new Chunk(NamaLengkap, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph1);

                                Paragraph paragraph2 = new Paragraph();
                                paragraph2.add(Chunk.TABBING);
                                paragraph2.add(new Chunk("2. Nomor Pemilik", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph2.add(Chunk.TABBING);
                                paragraph2.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph2.add(new Chunk(NoTelp, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph2);

                                document.add(new Paragraph("B. Spesifikasi Property", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));

                                Paragraph paragraph3 = new Paragraph();
                                paragraph3.add(Chunk.TABBING);
                                paragraph3.add(new Chunk("Status Property", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph3.add(Chunk.TABBING);
                                paragraph3.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph3.add(new Chunk(Kondisi, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph3);

                                Paragraph paragraph4 = new Paragraph();
                                paragraph4.add(Chunk.TABBING);
                                paragraph4.add(new Chunk("Jenis Property", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph4.add(Chunk.TABBING);
                                paragraph4.add(Chunk.TABBING);
                                paragraph4.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph4.add(new Chunk(JenisProperti, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph4);

                                if (Kondisi.equals("Jual")) {
                                    Paragraph paragraph5 = new Paragraph();
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(new Chunk("Harga Jual", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    paragraph5.add(new Chunk(currency.formatRupiah(Harga), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    document.add(paragraph5);
                                } else if (Kondisi.equals("Sewa")) {
                                    Paragraph paragraph5 = new Paragraph();
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(new Chunk("Harga Sewa", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    paragraph5.add(new Chunk(currency.formatRupiah(HargaSewa), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    document.add(paragraph5);
                                } else {
                                    Paragraph paragraph5 = new Paragraph();
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(new Chunk("Harga Jual", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(Chunk.TABBING);
                                    paragraph5.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    paragraph5.add(new Chunk(currency.formatRupiah(Harga), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    document.add(paragraph5);

                                    Paragraph paragraph6 = new Paragraph();
                                    paragraph6.add(Chunk.TABBING);
                                    paragraph6.add(new Chunk("Harga Sewa", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph6.add(Chunk.TABBING);
                                    paragraph6.add(Chunk.TABBING);
                                    paragraph6.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    paragraph6.add(new Chunk(currency.formatRupiah(HargaSewa), FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    document.add(paragraph6);
                                }

                                Paragraph paragraph7 = new Paragraph();
                                paragraph7.add(Chunk.TABBING);
                                paragraph7.add(new Chunk("Alamat Property", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph7.add(Chunk.TABBING);
                                paragraph7.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph7.add(new Chunk(Alamat, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph7.add(Chunk.TABBING);
                                paragraph7.add(Chunk.TABBING);
                                paragraph7.add(Chunk.TABBING);
                                document.add(paragraph7);

                                Paragraph paragraph8 = new Paragraph();
                                paragraph8.add(Chunk.TABBING);
                                paragraph8.add(new Chunk("Wilayah Property", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph8.add(Chunk.TABBING);
                                paragraph8.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph8.add(new Chunk(Wilayah, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph8);

                                Paragraph paragraph9 = new Paragraph();
                                paragraph9.add(Chunk.TABBING);
                                paragraph9.add(new Chunk("Luas Tanah", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph9.add(Chunk.TABBING);
                                paragraph9.add(Chunk.TABBING);
                                paragraph9.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph9.add(new Chunk(Wide, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph9);

                                Paragraph paragraph10 = new Paragraph();
                                paragraph10.add(Chunk.TABBING);
                                paragraph10.add(new Chunk("Luas Bangunan", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph10.add(Chunk.TABBING);
                                paragraph10.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph10.add(new Chunk(Land, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph10);

                                Paragraph paragraph11 = new Paragraph();
                                paragraph11.add(Chunk.TABBING);
                                paragraph11.add(new Chunk("Dimensi", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph11.add(Chunk.TABBING);
                                paragraph11.add(Chunk.TABBING);
                                paragraph11.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph11.add(new Chunk(Dimensi, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph11);

                                Paragraph paragraph12 = new Paragraph();
                                paragraph12.add(Chunk.TABBING);
                                paragraph12.add(new Chunk("Lantai", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph12.add(Chunk.TABBING);
                                paragraph12.add(Chunk.TABBING);
                                paragraph12.add(Chunk.TABBING);
                                paragraph12.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph12.add(new Chunk(Level, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph12);

                                Paragraph paragraph13 = new Paragraph();
                                paragraph13.add(Chunk.TABBING);
                                paragraph13.add(new Chunk("Hadap", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph13.add(Chunk.TABBING);
                                paragraph13.add(Chunk.TABBING);
                                paragraph13.add(Chunk.TABBING);
                                paragraph13.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph13.add(new Chunk(Hadap, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph13);

                                Paragraph paragraph14 = new Paragraph();
                                paragraph14.add(Chunk.TABBING);
                                paragraph14.add(new Chunk("Kamar Tidur", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph14.add(Chunk.TABBING);
                                paragraph14.add(Chunk.TABBING);
                                paragraph14.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph14.add(new Chunk(Bed + " + " + BedArt, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph14);

                                Paragraph paragraph15 = new Paragraph();
                                paragraph15.add(Chunk.TABBING);
                                paragraph15.add(new Chunk("Kamar Mandi", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph15.add(Chunk.TABBING);
                                paragraph15.add(Chunk.TABBING);
                                paragraph15.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph15.add(new Chunk(Bath + " + " + BathArt, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph15);

                                Paragraph paragraph16 = new Paragraph();
                                paragraph16.add(Chunk.TABBING);
                                paragraph16.add(new Chunk("Garasi", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph16.add(Chunk.TABBING);
                                paragraph16.add(Chunk.TABBING);
                                paragraph16.add(Chunk.TABBING);
                                paragraph16.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph16.add(new Chunk(Garage, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph16);

                                Paragraph paragraph17 = new Paragraph();
                                paragraph17.add(Chunk.TABBING);
                                paragraph17.add(new Chunk("Carpot", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph17.add(Chunk.TABBING);
                                paragraph17.add(Chunk.TABBING);
                                paragraph17.add(Chunk.TABBING);
                                paragraph17.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph17.add(new Chunk(Carpot, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph17);

                                Paragraph paragraph18 = new Paragraph();
                                paragraph18.add(Chunk.TABBING);
                                paragraph18.add(new Chunk("Listrik", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph18.add(Chunk.TABBING);
                                paragraph18.add(Chunk.TABBING);
                                paragraph18.add(Chunk.TABBING);
                                paragraph18.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph18.add(new Chunk(Listrik, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph18);

                                Paragraph paragraph19 = new Paragraph();
                                paragraph19.add(Chunk.TABBING);
                                paragraph19.add(new Chunk("Air", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph19.add(Chunk.TABBING);
                                paragraph19.add(Chunk.TABBING);
                                paragraph19.add(Chunk.TABBING);
                                paragraph19.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph19.add(new Chunk(SumberAir, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph19);

                                Paragraph paragraph20 = new Paragraph();
                                paragraph20.add(Chunk.TABBING);
                                paragraph20.add(new Chunk("Perabot", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph20.add(Chunk.TABBING);
                                paragraph20.add(Chunk.TABBING);
                                paragraph20.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph20.add(new Chunk(KetPrabot, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph20);

                                Paragraph paragraph21 = new Paragraph();
                                paragraph21.add(Chunk.TABBING);
                                paragraph21.add(new Chunk("Banner", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph21.add(Chunk.TABBING);
                                paragraph21.add(Chunk.TABBING);
                                paragraph21.add(Chunk.TABBING);
                                paragraph21.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph21.add(new Chunk(Size, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph21);

                                Paragraph paragraph22 = new Paragraph();
                                paragraph22.add(Chunk.TABBING);
                                paragraph22.add(new Chunk("Fee", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph22.add(Chunk.TABBING);
                                paragraph22.add(Chunk.TABBING);
                                paragraph22.add(Chunk.TABBING);
                                paragraph22.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph22.add(new Chunk(Fee, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph22);

                                Paragraph paragraph23 = new Paragraph();
                                paragraph23.add(Chunk.TABBING);
                                paragraph23.add(new Chunk("Tanggal Input", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                paragraph23.add(Chunk.TABBING);
                                paragraph23.add(Chunk.TABBING);
                                paragraph23.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                paragraph23.add(new Chunk(TglInput, FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                document.add(paragraph23);

                                document.add(new Paragraph("C. Gambar Property", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));
                                
                                if (!Img1.equals("0")) {
                                    Paragraph paragraph24 = new Paragraph();
                                    paragraph24.add(Chunk.TABBING);
                                    paragraph24.add(new Chunk("Gambar 1", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph24.add(Chunk.TABBING);
                                    paragraph24.add(Chunk.TABBING);
                                    paragraph24.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 1");
                                    anchor.setReference(Img1);
                                    paragraph24.add(anchor);
                                    document.add(paragraph24);

                                    // Mendownload gambar dari URL
//                                    ImagePdf imagePdf = new ImagePdf(DetailListingActivity.this, document);
//                                    imagePdf.execute(Img1);
                                }
                                if (!Img2.equals("0")) {
                                    Paragraph paragraph25 = new Paragraph();
                                    paragraph25.add(Chunk.TABBING);
                                    paragraph25.add(new Chunk("Gambar 2", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph25.add(Chunk.TABBING);
                                    paragraph25.add(Chunk.TABBING);
                                    paragraph25.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 2");
                                    anchor.setReference(Img2);
                                    paragraph25.add(anchor);
                                    document.add(paragraph25);
                                }
                                if (!Img3.equals("0")) {
                                    Paragraph paragraph26 = new Paragraph();
                                    paragraph26.add(Chunk.TABBING);
                                    paragraph26.add(new Chunk("Gambar 3", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph26.add(Chunk.TABBING);
                                    paragraph26.add(Chunk.TABBING);
                                    paragraph26.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 3");
                                    anchor.setReference(Img3);
                                    paragraph26.add(anchor);
                                    document.add(paragraph26);
                                }
                                if (!Img4.equals("0")) {
                                    Paragraph paragraph27 = new Paragraph();
                                    paragraph27.add(Chunk.TABBING);
                                    paragraph27.add(new Chunk("Gambar 4", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph27.add(Chunk.TABBING);
                                    paragraph27.add(Chunk.TABBING);
                                    paragraph27.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 4");
                                    anchor.setReference(Img4);
                                    paragraph27.add(anchor);
                                    document.add(paragraph27);
                                }
                                if (!Img5.equals("0")) {
                                    Paragraph paragraph28 = new Paragraph();
                                    paragraph28.add(Chunk.TABBING);
                                    paragraph28.add(new Chunk("Gambar 5", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph28.add(Chunk.TABBING);
                                    paragraph28.add(Chunk.TABBING);
                                    paragraph28.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 5");
                                    anchor.setReference(Img5);
                                    paragraph28.add(anchor);
                                    document.add(paragraph28);
                                }
                                if (!Img6.equals("0")) {
                                    Paragraph paragraph29 = new Paragraph();
                                    paragraph29.add(Chunk.TABBING);
                                    paragraph29.add(new Chunk("Gambar 6", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph29.add(Chunk.TABBING);
                                    paragraph29.add(Chunk.TABBING);
                                    paragraph29.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 6");
                                    anchor.setReference(Img6);
                                    paragraph29.add(anchor);
                                    document.add(paragraph29);
                                }
                                if (!Img7.equals("0")) {
                                    Paragraph paragraph30 = new Paragraph();
                                    paragraph30.add(Chunk.TABBING);
                                    paragraph30.add(new Chunk("Gambar 7", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph30.add(Chunk.TABBING);
                                    paragraph30.add(Chunk.TABBING);
                                    paragraph30.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 7");
                                    anchor.setReference(Img7);
                                    paragraph30.add(anchor);
                                    document.add(paragraph30);
                                }
                                if (!Img8.equals("0")) {
                                    Paragraph paragraph31 = new Paragraph();
                                    paragraph31.add(Chunk.TABBING);
                                    paragraph31.add(new Chunk("Gambar 8", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph31.add(Chunk.TABBING);
                                    paragraph31.add(Chunk.TABBING);
                                    paragraph31.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 8");
                                    anchor.setReference(Img8);
                                    paragraph31.add(anchor);
                                    document.add(paragraph31);
                                }
                                if (!Img9.equals("0")) {
                                    Paragraph paragraph32 = new Paragraph();
                                    paragraph32.add(Chunk.TABBING);
                                    paragraph32.add(new Chunk("Gambar 9", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph32.add(Chunk.TABBING);
                                    paragraph32.add(Chunk.TABBING);
                                    paragraph32.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 9");
                                    anchor.setReference(Img9);
                                    paragraph32.add(anchor);
                                    document.add(paragraph32);
                                }
                                if (!Img10.equals("0")) {
                                    Paragraph paragraph33 = new Paragraph();
                                    paragraph33.add(Chunk.TABBING);
                                    paragraph33.add(new Chunk("Gambar 10", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph33.add(Chunk.TABBING);
                                    paragraph33.add(Chunk.TABBING);
                                    paragraph33.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 10");
                                    anchor.setReference(Img10);
                                    paragraph33.add(anchor);
                                    document.add(paragraph33);
                                }
                                if (!Img11.equals("0")) {
                                    Paragraph paragraph34 = new Paragraph();
                                    paragraph34.add(Chunk.TABBING);
                                    paragraph34.add(new Chunk("Gambar 11", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph34.add(Chunk.TABBING);
                                    paragraph34.add(Chunk.TABBING);
                                    paragraph34.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 11");
                                    anchor.setReference(Img11);
                                    paragraph34.add(anchor);
                                    document.add(paragraph34);
                                }
                                if (!Img12.equals("0")) {
                                    Paragraph paragraph35 = new Paragraph();
                                    paragraph35.add(Chunk.TABBING);
                                    paragraph35.add(new Chunk("Gambar 12", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph35.add(Chunk.TABBING);
                                    paragraph35.add(Chunk.TABBING);
                                    paragraph35.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar 12");
                                    anchor.setReference(Img8);
                                    paragraph35.add(anchor);
                                    document.add(paragraph35);
                                }

                                document.add(new Paragraph("D. Lampiran", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.BOLD)));

                                if (!SHM.isEmpty() && !SHM.equals("0")) {
                                    Paragraph paragraph36 = new Paragraph();
                                    paragraph36.add(Chunk.TABBING);
                                    paragraph36.add(new Chunk("Gambar SHM", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph36.add(Chunk.TABBING);
                                    paragraph36.add(Chunk.TABBING);
                                    paragraph36.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar SHM");
                                    anchor.setReference(ImgShm);
                                    paragraph36.add(anchor);
                                    document.add(paragraph36);
                                }
                                if (!HGB.isEmpty() && !HGB.equals("0")) {
                                    Paragraph paragraph37 = new Paragraph();
                                    paragraph37.add(Chunk.TABBING);
                                    paragraph37.add(new Chunk("Gambar HGB", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph37.add(Chunk.TABBING);
                                    paragraph37.add(Chunk.TABBING);
                                    paragraph37.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar HGB");
                                    anchor.setReference(ImgHgb);
                                    paragraph37.add(anchor);
                                    document.add(paragraph37);
                                }
                                if (!HSHP.isEmpty() && !HSHP.equals("0")) {
                                    Paragraph paragraph38 = new Paragraph();
                                    paragraph38.add(Chunk.TABBING);
                                    paragraph38.add(new Chunk("Gambar HSHP", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph38.add(Chunk.TABBING);
                                    paragraph38.add(Chunk.TABBING);
                                    paragraph38.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar HSHP");
                                    anchor.setReference(ImgHshp);
                                    paragraph38.add(anchor);
                                    document.add(paragraph38);
                                }
                                if (!PPJB.isEmpty() && !PPJB.equals("0")) {
                                    Paragraph paragraph39 = new Paragraph();
                                    paragraph39.add(Chunk.TABBING);
                                    paragraph39.add(new Chunk("Gambar PPJB", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph39.add(Chunk.TABBING);
                                    paragraph39.add(Chunk.TABBING);
                                    paragraph39.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar PPJB");
                                    anchor.setReference(ImgPpjb);
                                    paragraph39.add(anchor);
                                    document.add(paragraph39);
                                }
                                if (!Stratatitle.isEmpty() && !Stratatitle.equals("0")) {
                                    Paragraph paragraph40 = new Paragraph();
                                    paragraph40.add(Chunk.TABBING);
                                    paragraph40.add(new Chunk("Gambar Stratatitle", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph40.add(Chunk.TABBING);
                                    paragraph40.add(Chunk.TABBING);
                                    paragraph40.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar Stratatitle");
                                    anchor.setReference(ImgStatatitle);
                                    paragraph40.add(anchor);
                                    document.add(paragraph40);
                                }
                                if (!AJB.isEmpty() && !AJB.equals("0")) {
                                    Paragraph paragraph41 = new Paragraph();
                                    paragraph41.add(Chunk.TABBING);
                                    paragraph41.add(new Chunk("Gambar AJB", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph41.add(Chunk.TABBING);
                                    paragraph41.add(Chunk.TABBING);
                                    paragraph41.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar AJB");
                                    anchor.setReference(ImgAjb);
                                    paragraph41.add(anchor);
                                    document.add(paragraph41);
                                }
                                if (!PetokD.isEmpty() && !PetokD.equals("0")) {
                                    Paragraph paragraph42 = new Paragraph();
                                    paragraph42.add(Chunk.TABBING);
                                    paragraph42.add(new Chunk("Gambar Petok D", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph42.add(Chunk.TABBING);
                                    paragraph42.add(Chunk.TABBING);
                                    paragraph42.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar Petok D");
                                    anchor.setReference(ImgPetokD);
                                    paragraph42.add(anchor);
                                    document.add(paragraph42);
                                }
                                if (!ImgPjp.equals("0")) {
                                    Paragraph paragraph43 = new Paragraph();
                                    paragraph43.add(Chunk.TABBING);
                                    paragraph43.add(new Chunk("Gambar PJP Hal 1", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph43.add(Chunk.TABBING);
                                    paragraph43.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar PJP Hal 1");
                                    anchor.setReference(ImgPjp);
                                    paragraph43.add(anchor);
                                    document.add(paragraph43);
                                }
                                if (!ImgPjp1.equals("0")) {
                                    Paragraph paragraph44 = new Paragraph();
                                    paragraph44.add(Chunk.TABBING);
                                    paragraph44.add(new Chunk("Gambar PJP Hal 1", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph44.add(Chunk.TABBING);
                                    paragraph44.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar PJP Hal 2");
                                    anchor.setReference(ImgPjp1);
                                    paragraph44.add(anchor);
                                    document.add(paragraph44);
                                }
                                if (!Selfie.equals("0") || !Selfie.isEmpty()) {
                                    Paragraph paragraph45 = new Paragraph();
                                    paragraph45.add(Chunk.TABBING);
                                    paragraph45.add(new Chunk("Gambar Selfie", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Font.NORMAL)));
                                    paragraph45.add(Chunk.TABBING);
                                    paragraph45.add(Chunk.TABBING);
                                    paragraph45.add(new Chunk(": ", FontFactory.getFont(FontFactory.TIMES_ROMAN, 12)));
                                    Anchor anchor = new Anchor("Lihat Gambar Selfie");
                                    anchor.setReference(Selfie);
                                    paragraph45.add(anchor);
                                    document.add(paragraph45);
                                }

                                document.close();
                                Toast.makeText(DetailListingActivity.this, "PDF saved", Toast.LENGTH_SHORT).show();
                            }

                        } catch (
                                DocumentException |
                                FileNotFoundException | JSONException e) {
                            PDDetailListing.cancel();
                            e.printStackTrace();
                            Toast.makeText(DetailListingActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        PDDetailListing.cancel();
                    }
                });
        requestQueue.add(jsonArrayRequest);
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

//            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//                @Override
//                public void onMapClick(LatLng latLng) {
//                    double latitude = lat;
//                    double longitude = lng;
//
//                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//
//                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                        startActivity(mapIntent);
//                    }
//                }
//            });
        }
    }
    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "pending");
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
        String title = Preferences.getKeyNama(this);
        String message = "Tambah Data Listing";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}