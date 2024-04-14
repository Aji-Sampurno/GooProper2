package com.gooproper.ui.detail;

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
import android.view.LayoutInflater;
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
import com.gooproper.guest.MainGuestActivity;
import com.gooproper.model.ListingModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.SettingActivity;
import com.gooproper.ui.edit.EditListingActivity;
import com.gooproper.ui.edit.EditListingAgenActivity;
import com.gooproper.ui.edit.EditMapsListingActivity;
import com.gooproper.ui.edit.EditPraListingAgenActivity;
import com.gooproper.ui.edit.EditPralistingActivity;
import com.gooproper.ui.followup.FollowUpActivity;
import com.gooproper.ui.ImageViewActivity;
import com.gooproper.ui.tambah.TambahListingActivity;
import com.gooproper.ui.tambah.TambahSelfieActivity;
import com.gooproper.ui.tambah.TambahSelfieListingActivity;
import com.gooproper.ui.tambah.TambahTemplateActivity;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailListingActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog PDDetailListing;
    TextView TVRangeHarga, TVNamaDetailListing, TVAlamatDetailListing, TVHargaDetailListing, TVHargaSewaDetailListing, TVViewsDetailListing, TVLikeDetailListing, TVBedDetailListing, TVNamaAgen, TVNamaAgen2, TVBathDetailListing, TVWideDetailListing, TVLandDetailListing, TVDimensiDetailListing, TVTipeDetailListing, TVStatusDetailListing, TVSertifikatDetailListing, TVLuasDetailListing, TVKamarTidurDetailListing, TVKamarMandiDetailListing, TVLantaiDetailListing, TVGarasiDetailListing, TVCarpotDetailListing, TVListrikDetailListing, TVSumberAirDetailListing, TVPerabotDetailListing, TVSizeBanner, TVDeskripsiDetailListing, TVNoData, TVNoDataPdf, TVPriority, TVKondisi, TVNoPjp, TVNoDataPjp, TVFee, TVTglInput, TVNamaVendor, TVTelpVendor, TVPJP, TVSelfie, TVRejected, TVPoin, TVHadap;
    ImageView IVFlowUp, IVWhatsapp, IVInstagram, IVFlowUp2, IVWhatsapp2, IVInstagram2, IVFavorite, IVFavoriteOn, IVShare, IVStar1, IVStar2, IVStar3, IVStar4, IVStar5, IVAlamat, IVNextImg, IVPrevImg, IVSelfie;
    Button BtnApproveAdmin, BtnApproveManager, BtnRejectedAdmin, BtnRejectedManager, BtnAjukanUlang, BtnLihatTemplate, BtnLihatTemplateKosong, BtnUploadTemplate;
    Button BtnTambahPjp, BtnTambahBanner, BtnTambahCoList, BtnUpgrade, BtnTambahMaps, BtnTambahSelfie, BtnApproveUpgrade;
    TextInputEditText tambahagen, tambahcoagen, tambahpjp;
    TextInputLayout lytambahagen, lyttambahcoagen, lyttambahpjp;
    CheckBox CBMarketable, CBHarga, CBSelfie, CBLokasi;
    LinearLayout LytCBMarketable, LytCBHarga, LytCBSelfie, LytCBLokasi;
    ScrollView scrollView;
    CardView agen, agen2, CVSold, CVRented;
    String status, idpralisting, idagen, idlisting, agenid, agencoid, idpengguna, StringNamaListing, StringLuasTanah, StringLuasBangunan, StringKamarTidur, StringKamarTidurArt, StringKamarMandiArt, StringKamarMandi, StringListrik, StringHarga, StringHargaSewa, StringSertifikat, StringAlamat;
    String BuyerNama, BuyerTelp, BuyerKeterangan, BuyerTanggal, BuyerIdAgen, BuyerIdListing, BuyerIdInput, BuyerJam, StringNamaBuyer, AgenId, StringKeteranganReject;
    String NamaMaps;
    int PoinSekarang, PoinTambah, PoinKurang;
    String imageUrl, namaAgen, telpAgen, IdCo, UrlSHM, UrlHGB, UrlHSHP, UrlPPJB, UrlStratatitle, UrlAJB, UrlPetokD;
    String productId, StrIdAgen, StrIntentIdAgenCo, StrIntentIdAgen;
    String PJPHal1, PJPHal2;
    int Poin, FinalPoin, CoPoin;
    ProgressDialog pDialog;
    ListingModel lm;
    LinearLayout LytSertifikat, LytPJP, LytSize, LytFee, LytTglInput, LytBadge, LytBadgeSold, LytBadgeRented, IVEdit, LytNamaVendor, LytTelpVendor, LytRejected, LytSelfie;
    ViewPager viewPager, viewPagerSertifikat, viewPagerPJP;
    ViewPagerAdapter adapter;
    SertifikatAdapter sertifikatAdapter;
    private SertifikatPdfAdapter sertifikatPdfAdapter;
    PJPAdapter pjpAdapter;
    ArrayList<String> images = new ArrayList<>();
    ArrayList<String> sertif = new ArrayList<>();
    ArrayList<String> sertifpdf = new ArrayList<>();
    ArrayList<String> pjpimage = new ArrayList<>();
    private String[] dataOptions;
    private int selectedOption = -1;
    private AgenManager agenManager, agenCoManager;
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    private static final int REQUEST_WRITE_STORAGE = 112;
    private static final int RESULT_LOAD_PJP1 = 1;
    private static final int RESULT_LOAD_PJP2 = 2;
    Uri UriPjp1, UriPjp2;
    private Dialog CustomDialogPjp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_listing);

        PDDetailListing = new ProgressDialog(DetailListingActivity.this);
        pDialog = new ProgressDialog(this);

        tambahagen = findViewById(R.id.ETTambahAgenDetailListing);
        tambahcoagen = findViewById(R.id.ETTambahCoAgenDetailListing);
        tambahpjp = findViewById(R.id.ETTambahNoPjpDetailListing);
        viewPager = findViewById(R.id.VPDetailListing);
        viewPagerSertifikat = findViewById(R.id.VPSertifikatDetailListing);
        viewPagerPJP = findViewById(R.id.VPPJPDetailListing);
        agen = findViewById(R.id.LytAgenDetailListing);
        agen2 = findViewById(R.id.LytAgen2DetailListing);
        CVSold = findViewById(R.id.LytSoldDetailListing);
        CVRented = findViewById(R.id.LytRentedDetailListing);
        lytambahagen = findViewById(R.id.LytTambahAgenDetailListing);
        lyttambahcoagen = findViewById(R.id.LytTambahCoAgenDetailListing);
        lyttambahpjp = findViewById(R.id.LytTambahNoPjpDetailListing);
        LytSertifikat = findViewById(R.id.LytSertifikat);
        LytPJP = findViewById(R.id.LytViewPjp);
        LytSize = findViewById(R.id.LytUkuranBannerDetailListing);
        LytFee = findViewById(R.id.LytFeeDetailListing);
        LytTglInput = findViewById(R.id.LytTglInputDetailListing);
        LytBadge = findViewById(R.id.LytBadge);
        LytBadgeSold = findViewById(R.id.LytBadgeSold);
        LytBadgeRented = findViewById(R.id.LytBadgeRented);
        LytNamaVendor = findViewById(R.id.LytNamaVendorDetailListing);
        LytTelpVendor = findViewById(R.id.LytTelpVendorDetailListing);
        LytRejected = findViewById(R.id.LytRejectedDetailListing);
        LytSelfie = findViewById(R.id.LytViewSelfie);
        scrollView = findViewById(R.id.SVDetailListing);

        BtnApproveAdmin = findViewById(R.id.BtnApproveAdminDetailListing);
        BtnApproveManager = findViewById(R.id.BtnApproveManagerDetailListing);
        BtnRejectedAdmin = findViewById(R.id.BtnRejectedAdminDetailListing);
        BtnRejectedManager = findViewById(R.id.BtnRejectedManagerDetailListing);
        BtnAjukanUlang = findViewById(R.id.BtnAjukanUlangDetailListing);

        BtnTambahMaps = findViewById(R.id.BtnAddMapsDetailListing);
        BtnTambahSelfie = findViewById(R.id.BtnAddSelfieDetailListing);
        BtnTambahPjp = findViewById(R.id.BtnAddPjpDetailListing);
        BtnTambahBanner = findViewById(R.id.BtnAddBannerDetailListing);
        BtnTambahCoList = findViewById(R.id.BtnColistDetailListing);
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
        TVRejected = findViewById(R.id.TVKeteranganDetailListing);
        TVPoin = findViewById(R.id.TVPoinListing);

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
        IVEdit = findViewById(R.id.IVEditDetailListing);
        IVNextImg = findViewById(R.id.IVNextImg);
        IVPrevImg = findViewById(R.id.IVPrevImg);
        IVSelfie = findViewById(R.id.IVSelfie);

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

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_WRITE_STORAGE);
        }

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
        String intentDimensi = data.getStringExtra("Dimensi");
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
        String intentAJB = data.getStringExtra("AJB");
        String intentPetokD = data.getStringExtra("PetokD");
        String intentPjp = data.getStringExtra("Pjp");
        String intentImgSHM = data.getStringExtra("ImgSHM");
        String intentImgHGB = data.getStringExtra("ImgHGB");
        String intentImgHSHP = data.getStringExtra("ImgHSHP");
        String intentImgPPJB = data.getStringExtra("ImgPPJB");
        String intentImgStratatitle = data.getStringExtra("ImgStratatitle");
        String intentImgAJB = data.getStringExtra("ImgAJB");
        String intentImgPetokD = data.getStringExtra("ImgPetokD");
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
        String intentRangeHarga = data.getStringExtra("RangeHarga");
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
        String intentIsRejected = data.getStringExtra("IsRejected");
        String intentSold = data.getStringExtra("Sold");
        String intentRented = data.getStringExtra("Rented");
        String intentSoldAgen = data.getStringExtra("SoldAgen");
        String intentRentedAgen = data.getStringExtra("RentedAgen");
        String intentView = data.getStringExtra("View");
        String intentMarketable = data.getStringExtra("Marketable");
        String intentStatusHarga = data.getStringExtra("StatusHarga");
        String intentNama = data.getStringExtra("Nama");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentInstagram = data.getStringExtra("Instagram");
        String intentFee = data.getStringExtra("Fee");
        String intentNamaVendor = data.getStringExtra("NamaVendor");
        String intentNoTelpVendor = data.getStringExtra("NoTelpVendor");
        String intentIsSelfie = data.getStringExtra("IsSelfie");
        String intentIsLokasi = data.getStringExtra("IsLokasi");
        String intentKeterangan = data.getStringExtra("Keterangan");
        String intentIdTemplate = data.getStringExtra("IdTemplate");
        String intentTemplate = data.getStringExtra("Template");
        String intentTemplateBlank = data.getStringExtra("TemplateBlank");

        status = Preferences.getKeyStatus(this);
        StrIdAgen = Preferences.getKeyIdAgen(this);

        StrIntentIdAgen = intentIdAgen;
        StrIntentIdAgenCo = intentIdAgenCo;
        idpralisting = intentIdPraListing;
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

        if (intentIsRejected.equals("1")) {
            BtnAjukanUlang.setVisibility(View.VISIBLE);
        } else {
            BtnAjukanUlang.setVisibility(View.GONE);
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
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            LytSertifikat.setVisibility(View.VISIBLE);
            LytPJP.setVisibility(View.VISIBLE);
            LytNamaVendor.setVisibility(View.VISIBLE);
            LytTelpVendor.setVisibility(View.VISIBLE);
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
            LytNamaVendor.setVisibility(View.VISIBLE);
            LytTelpVendor.setVisibility(View.VISIBLE);
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
            LytFee.setVisibility(View.GONE);
            LytTglInput.setVisibility(View.GONE);
            LytNamaVendor.setVisibility(View.GONE);
            LytTelpVendor.setVisibility(View.GONE);
            lytambahagen.setVisibility(View.GONE);
            LytSelfie.setVisibility(View.GONE);
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
            if (intentIsManager.equals("0")) {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.VISIBLE);
                BtnRejectedAdmin.setVisibility(View.GONE);
                BtnRejectedManager.setVisibility(View.VISIBLE);
                BtnLihatTemplate.setVisibility(View.GONE);
                BtnLihatTemplateKosong.setVisibility(View.GONE);
                BtnUploadTemplate.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.INVISIBLE);
                IVFlowUp2.setVisibility(View.INVISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.INVISIBLE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            } else if (intentIsAdmin.equals("0")) {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.VISIBLE);
                BtnRejectedAdmin.setVisibility(View.GONE);
                BtnRejectedManager.setVisibility(View.VISIBLE);
                BtnLihatTemplate.setVisibility(View.GONE);
                BtnLihatTemplateKosong.setVisibility(View.GONE);
                BtnUploadTemplate.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.INVISIBLE);
                IVFlowUp2.setVisibility(View.INVISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.INVISIBLE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            } else {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.GONE);
                BtnRejectedAdmin.setVisibility(View.GONE);
                BtnRejectedManager.setVisibility(View.GONE);
                BtnLihatTemplate.setVisibility(View.GONE);
                BtnLihatTemplateKosong.setVisibility(View.GONE);
                BtnUploadTemplate.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.VISIBLE);
                IVFlowUp2.setVisibility(View.VISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVShare.setVisibility(View.VISIBLE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            }
        } else if (status.equals("2")) {
            if (intentIsManager.equals("0")) {
                BtnApproveAdmin.setVisibility(View.VISIBLE);
                BtnApproveManager.setVisibility(View.GONE);
                BtnRejectedAdmin.setVisibility(View.VISIBLE);
                BtnRejectedManager.setVisibility(View.GONE);
                BtnLihatTemplate.setVisibility(View.GONE);
                BtnLihatTemplateKosong.setVisibility(View.GONE);
                BtnUploadTemplate.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.INVISIBLE);
                IVFlowUp2.setVisibility(View.INVISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent update = new Intent(DetailListingActivity.this, EditListingActivity.class);
                        update.putExtra("IdPraListing", idpralisting);
                        update.putExtra("IdListing", intentIdListing);
                        update.putExtra("IdAgen", intentIdAgen);
                        update.putExtra("IdInput", intentIdInput);
                        update.putExtra("NamaListing", intentNamaListing);
                        update.putExtra("Alamat", intentAlamat);
                        update.putExtra("Latitude", intentLatitude);
                        update.putExtra("Longitude", intentLongitude);
                        update.putExtra("Location", intentLocation);
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
                        update.putExtra("Video", intentVideo);
                        update.putExtra("LinkFacebook", intentLinkFacebook);
                        update.putExtra("LinkTiktok", intentLinkTiktok);
                        update.putExtra("LinkInstagram", intentLinkInstagram);
                        update.putExtra("LinkYoutube", intentLinkYoutube);
                        update.putExtra("IsAdmin", intentIsAdmin);
                        update.putExtra("IsManager", intentIsManager);
                        update.putExtra("View", intentView);
                        update.putExtra("Sold", intentSold);
                        update.putExtra("Marketable", intentMarketable);
                        update.putExtra("StatusHarga", intentStatusHarga);
                        update.putExtra("Nama", intentNama);
                        update.putExtra("NoTelp", intentNoTelp);
                        update.putExtra("Instagram", intentInstagram);
                        update.putExtra("Fee", intentFee);
                        startActivity(update);
                    }
                });
                IVShare.setVisibility(View.GONE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            } else if (intentIsAdmin.equals("0")) {
                BtnApproveAdmin.setVisibility(View.VISIBLE);
                BtnApproveManager.setVisibility(View.GONE);
                BtnRejectedAdmin.setVisibility(View.VISIBLE);
                BtnRejectedManager.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.INVISIBLE);
                IVFlowUp2.setVisibility(View.INVISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent update = new Intent(DetailListingActivity.this, EditListingActivity.class);
                        update.putExtra("IdPraListing", idpralisting);
                        update.putExtra("IdListing", intentIdListing);
                        update.putExtra("IdAgen", intentIdAgen);
                        update.putExtra("IdInput", intentIdInput);
                        update.putExtra("NamaListing", intentNamaListing);
                        update.putExtra("Alamat", intentAlamat);
                        update.putExtra("Latitude", intentLatitude);
                        update.putExtra("Longitude", intentLongitude);
                        update.putExtra("Location", intentLocation);
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
                        update.putExtra("Video", intentVideo);
                        update.putExtra("LinkFacebook", intentLinkFacebook);
                        update.putExtra("LinkTiktok", intentLinkTiktok);
                        update.putExtra("LinkInstagram", intentLinkInstagram);
                        update.putExtra("LinkYoutube", intentLinkYoutube);
                        update.putExtra("IsAdmin", intentIsAdmin);
                        update.putExtra("IsManager", intentIsManager);
                        update.putExtra("View", intentView);
                        update.putExtra("Sold", intentSold);
                        update.putExtra("Marketable", intentMarketable);
                        update.putExtra("StatusHarga", intentStatusHarga);
                        update.putExtra("Nama", intentNama);
                        update.putExtra("NoTelp", intentNoTelp);
                        update.putExtra("Instagram", intentInstagram);
                        update.putExtra("Fee", intentFee);
                        startActivity(update);
                    }
                });
                IVShare.setVisibility(View.GONE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
                if (!intentImgPjp.equals("0")) {
                    lyttambahpjp.setVisibility(View.VISIBLE);
                }
            } else {
                BtnApproveAdmin.setVisibility(View.GONE);
                BtnApproveManager.setVisibility(View.GONE);
                BtnRejectedAdmin.setVisibility(View.GONE);
                BtnRejectedManager.setVisibility(View.GONE);
                BtnLihatTemplate.setVisibility(View.GONE);
                BtnLihatTemplateKosong.setVisibility(View.GONE);
                BtnUploadTemplate.setVisibility(View.GONE);
                IVFlowUp.setVisibility(View.VISIBLE);
                IVFlowUp2.setVisibility(View.VISIBLE);
                IVEdit.setVisibility(View.VISIBLE);
                IVEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent update = new Intent(DetailListingActivity.this, EditListingAgenActivity.class);
                        update.putExtra("IdPraListing", idpralisting);
                        update.putExtra("IdListing", intentIdListing);
                        update.putExtra("IdAgen", intentIdAgen);
                        update.putExtra("IdInput", intentIdInput);
                        update.putExtra("NamaListing", intentNamaListing);
                        update.putExtra("Alamat", intentAlamat);
                        update.putExtra("Latitude", intentLatitude);
                        update.putExtra("Longitude", intentLongitude);
                        update.putExtra("Location", intentLocation);
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
                        update.putExtra("Video", intentVideo);
                        update.putExtra("LinkFacebook", intentLinkFacebook);
                        update.putExtra("LinkTiktok", intentLinkTiktok);
                        update.putExtra("LinkInstagram", intentLinkInstagram);
                        update.putExtra("LinkYoutube", intentLinkYoutube);
                        update.putExtra("IsAdmin", intentIsAdmin);
                        update.putExtra("IsManager", intentIsManager);
                        update.putExtra("View", intentView);
                        update.putExtra("Sold", intentSold);
                        update.putExtra("Marketable", intentMarketable);
                        update.putExtra("StatusHarga", intentStatusHarga);
                        update.putExtra("Nama", intentNama);
                        update.putExtra("NoTelp", intentNoTelp);
                        update.putExtra("Instagram", intentInstagram);
                        update.putExtra("Fee", intentFee);
                        startActivity(update);
                    }
                });
                IVShare.setVisibility(View.GONE);
                IVFavorite.setVisibility(View.GONE);
                AgenId = "0";
                idpengguna = Preferences.getKeyIdAdmin(this);
            }
        } else if (status.equals("3")) {
            if (intentLatitude.equals("0") && intentLongitude.equals("0")) {
                if (intentIsManager.equals("0")) {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    BtnTambahMaps.setVisibility(View.VISIBLE);
                    IVEdit.setVisibility(View.VISIBLE);
                    IVShare.setVisibility(View.GONE);
                    IVFavorite.setVisibility(View.GONE);
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
                    IVEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent update = new Intent(DetailListingActivity.this, EditPraListingAgenActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            update.putExtra("IdListing", intentIdListing);
                            update.putExtra("IdAgen", intentIdAgen);
                            update.putExtra("IdAgenCo", intentIdAgenCo);
                            update.putExtra("IdInput", intentIdInput);
                            update.putExtra("NamaListing", intentNamaListing);
                            update.putExtra("Alamat", intentAlamat);
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
                            Intent update = new Intent(DetailListingActivity.this, EditPralistingActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            update.putExtra("Selfie", intentSelfie);
                            startActivity(update);
                        }
                    });
                } else if (intentIsAdmin.equals("0")) {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    BtnTambahMaps.setVisibility(View.VISIBLE);
                    IVEdit.setVisibility(View.VISIBLE);
                    IVShare.setVisibility(View.GONE);
                    IVFavorite.setVisibility(View.GONE);
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
                    IVEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent update = new Intent(DetailListingActivity.this, EditPraListingAgenActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            update.putExtra("IdListing", intentIdListing);
                            update.putExtra("IdAgen", intentIdAgen);
                            update.putExtra("IdAgenCo", intentIdAgenCo);
                            update.putExtra("IdInput", intentIdInput);
                            update.putExtra("NamaListing", intentNamaListing);
                            update.putExtra("Alamat", intentAlamat);
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
                            Intent update = new Intent(DetailListingActivity.this, EditPralistingActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            update.putExtra("Selfie", intentSelfie);
                            startActivity(update);
                        }
                    });
                } else {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
                    if (StrIdAgen.equals(intentIdAgen)) {
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
                        BtnTambahMaps.setVisibility(View.VISIBLE);

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

                        BtnTambahMaps.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent update = new Intent(DetailListingActivity.this, EditMapsListingActivity.class);
                                update.putExtra("IdListing", idlisting);
                                update.putExtra("Selfie", intentSelfie);
                                update.putExtra("PoinTambah", String.valueOf(PoinTambah));
                                startActivity(update);
                            }
                        });
                    } else {
                        BtnTambahMaps.setVisibility(View.GONE);
                    }
                }
            } else if (intentSelfie.equals("0") || intentSelfie.equals("") || intentSelfie.isEmpty()) {
                if (intentIsManager.equals("0")) {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    BtnTambahSelfie.setVisibility(View.VISIBLE);
                    IVEdit.setVisibility(View.VISIBLE);
                    IVShare.setVisibility(View.GONE);
                    IVFavorite.setVisibility(View.GONE);
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
                    IVEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent update = new Intent(DetailListingActivity.this, EditPraListingAgenActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            update.putExtra("IdListing", intentIdListing);
                            update.putExtra("IdAgen", intentIdAgen);
                            update.putExtra("IdAgenCo", intentIdAgenCo);
                            update.putExtra("IdInput", intentIdInput);
                            update.putExtra("NamaListing", intentNamaListing);
                            update.putExtra("Alamat", intentAlamat);
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
                    BtnTambahSelfie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent update = new Intent(DetailListingActivity.this, TambahSelfieActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            startActivity(update);
                        }
                    });
                } else if (intentIsAdmin.equals("0")) {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    BtnTambahSelfie.setVisibility(View.VISIBLE);
                    IVEdit.setVisibility(View.VISIBLE);
                    IVShare.setVisibility(View.GONE);
                    IVFavorite.setVisibility(View.GONE);
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
                    IVEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent update = new Intent(DetailListingActivity.this, EditPraListingAgenActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            update.putExtra("IdListing", intentIdListing);
                            update.putExtra("IdAgen", intentIdAgen);
                            update.putExtra("IdAgenCo", intentIdAgenCo);
                            update.putExtra("IdInput", intentIdInput);
                            update.putExtra("NamaListing", intentNamaListing);
                            update.putExtra("Alamat", intentAlamat);
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
                    BtnTambahSelfie.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent update = new Intent(DetailListingActivity.this, TambahSelfieActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            startActivity(update);
                        }
                    });
                } else {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
                    if (StrIdAgen.equals(intentIdAgen)) {
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
                        BtnTambahSelfie.setVisibility(View.VISIBLE);
                        BtnTambahMaps.setVisibility(View.GONE);

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
                                Intent update = new Intent(DetailListingActivity.this, TambahSelfieListingActivity.class);
                                update.putExtra("IdListing", idlisting);
                                update.putExtra("PoinTambah", String.valueOf(PoinTambah));
                                startActivity(update);
                            }
                        });
                    } else {
                        BtnTambahSelfie.setVisibility(View.GONE);
                        BtnTambahMaps.setVisibility(View.GONE);
                    }
                }
            } else {
                if (intentIsManager.equals("0")) {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    BtnTambahMaps.setVisibility(View.GONE);
                    BtnTambahSelfie.setVisibility(View.GONE);
                    IVEdit.setVisibility(View.VISIBLE);
                    IVShare.setVisibility(View.GONE);
                    IVFavorite.setVisibility(View.GONE);
                    IVEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent update = new Intent(DetailListingActivity.this, EditPraListingAgenActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            update.putExtra("IdListing", intentIdListing);
                            update.putExtra("IdAgen", intentIdAgen);
                            update.putExtra("IdAgenCo", intentIdAgenCo);
                            update.putExtra("IdInput", intentIdInput);
                            update.putExtra("NamaListing", intentNamaListing);
                            update.putExtra("Alamat", intentAlamat);
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
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
                } else if (intentIsAdmin.equals("0")) {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    BtnTambahMaps.setVisibility(View.GONE);
                    BtnTambahSelfie.setVisibility(View.GONE);
                    IVEdit.setVisibility(View.VISIBLE);
                    IVShare.setVisibility(View.GONE);
                    IVFavorite.setVisibility(View.GONE);
                    IVEdit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent update = new Intent(DetailListingActivity.this, EditPraListingAgenActivity.class);
                            update.putExtra("IdPraListing", idpralisting);
                            update.putExtra("IdListing", intentIdListing);
                            update.putExtra("IdAgen", intentIdAgen);
                            update.putExtra("IdAgenCo", intentIdAgenCo);
                            update.putExtra("IdInput", intentIdInput);
                            update.putExtra("NamaListing", intentNamaListing);
                            update.putExtra("Alamat", intentAlamat);
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
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
                } else {
                    TVAlamatDetailListing.setVisibility(View.GONE);
                    IVAlamat.setVisibility(View.GONE);
                    BtnApproveAdmin.setVisibility(View.GONE);
                    BtnApproveManager.setVisibility(View.GONE);
                    BtnRejectedAdmin.setVisibility(View.GONE);
                    BtnRejectedManager.setVisibility(View.GONE);
                    BtnTambahMaps.setVisibility(View.GONE);
                    BtnTambahSelfie.setVisibility(View.GONE);
                    BtnLihatTemplate.setVisibility(View.GONE);
                    BtnLihatTemplateKosong.setVisibility(View.GONE);
                    BtnUploadTemplate.setVisibility(View.GONE);
                    IVFlowUp.setVisibility(View.VISIBLE);
                    IVFlowUp2.setVisibility(View.VISIBLE);
                    idpengguna = "0";
                    AgenId = Preferences.getKeyIdAgen(this);
                    LytCBMarketable.setVisibility(View.GONE);
                    LytCBHarga.setVisibility(View.GONE);
                    LytCBSelfie.setVisibility(View.GONE);
                    LytCBLokasi.setVisibility(View.GONE);
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
                }
            }
        } else if (status.equals("4")) {
            IVFlowUp.setVisibility(View.INVISIBLE);
            BtnApproveAdmin.setVisibility(View.GONE);
            BtnApproveManager.setVisibility(View.GONE);
            BtnRejectedAdmin.setVisibility(View.GONE);
            BtnRejectedManager.setVisibility(View.GONE);
            BtnLihatTemplate.setVisibility(View.GONE);
            BtnLihatTemplateKosong.setVisibility(View.GONE);
            BtnUploadTemplate.setVisibility(View.GONE);
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
        BtnApproveManager.setOnClickListener(new View.OnClickListener() {
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
                        approvemanager();
                    }
                } else {
                    approvemanager();
                }
            }
        });
        BtnRejectedAdmin.setOnClickListener(v -> ShowRejected());
        BtnRejectedManager.setOnClickListener(v -> ShowRejected());
        BtnAjukanUlang.setOnClickListener(v -> ajukanulang());
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

        if (update == 1) {
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
                        Poin = 50;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin1();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin1();
                            }
                        });
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
                        Poin = 50;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin1();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin1();
                            }
                        });
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
                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint1();
                        }
                    });
                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint1();
                        }
                    });
                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint1();
                        }
                    });
                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint1();
                        }
                    });
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
                        Poin = 40;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin2();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin2();
                            }
                        });
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
                        Poin = 40;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin2();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin2();
                            }
                        });
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
                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint2();
                        }
                    });
                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint2();
                        }
                    });
                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint2();
                        }
                    });
                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint2();
                        }
                    });
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
                        Poin = 30;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin3();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin3();
                            }
                        });
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
                        Poin = 30;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin3();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin3();
                            }
                        });
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
                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint3();
                        }
                    });
                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint3();
                        }
                    });
                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint3();
                        }
                    });
                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint3();
                        }
                    });
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
                        Poin = 20;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin4();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin4();
                            }
                        });
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
                        Poin = 20;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin4();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin4();
                            }
                        });
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
                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint4();
                        }
                    });
                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint4();
                        }
                    });
                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint4();
                        }
                    });
                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint4();
                        }
                    });
                }
            } else if (intentPriority.equals("open") && intentPjp.isEmpty() && intentBanner.equals("Ya")) {
                if (intentIsSelfie.equals("1") && intentIsLokasi.equals("1")) {
                    if (intentMarketable.equals("1") && intentStatusHarga.equals("1")) {
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
                        Poin = 10;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin5();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin5();
                            }
                        });
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
                        Poin = 10;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin5();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin5();
                            }
                        });
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
                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint5();
                        }
                    });
                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint5();
                        }
                    });
                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint5();
                        }
                    });
                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint5();
                        }
                    });
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
                        Poin = 10;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin6();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin6();
                            }
                        });
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
                        Poin = 10;
                        CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin6();
                            }
                        });
                        CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                                updatepoin6();
                            }
                        });
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
                    CBSelfie.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint6();
                        }
                    });
                    CBLokasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint6();
                        }
                    });
                    CBMarketable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint6();
                        }
                    });
                    CBHarga.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            updatepoint6();
                        }
                    });
                }
            }
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
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    LytBadge.setVisibility(View.GONE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentSoldAgen.equals("1")) {
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    LytBadge.setVisibility(View.GONE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentRented.equals("1")) {
                    LytBadgeRented.setVisibility(View.VISIBLE);
                    LytBadgeSold.setVisibility(View.GONE);
                    LytBadge.setVisibility(View.GONE);
                    CVSold.setVisibility(View.GONE);
                    CVRented.setVisibility(View.VISIBLE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentRentedAgen.equals("1")) {
                    LytBadgeRented.setVisibility(View.VISIBLE);
                    LytBadgeSold.setVisibility(View.GONE);
                    LytBadge.setVisibility(View.GONE);
                    CVSold.setVisibility(View.GONE);
                    CVRented.setVisibility(View.VISIBLE);
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
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    LytBadge.setVisibility(View.GONE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentSoldAgen.equals("1")) {
                    LytBadgeRented.setVisibility(View.GONE);
                    LytBadgeSold.setVisibility(View.VISIBLE);
                    LytBadge.setVisibility(View.GONE);
                    CVSold.setVisibility(View.VISIBLE);
                    CVRented.setVisibility(View.GONE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentRented.equals("1")) {
                    LytBadgeRented.setVisibility(View.VISIBLE);
                    LytBadgeSold.setVisibility(View.GONE);
                    LytBadge.setVisibility(View.GONE);
                    CVSold.setVisibility(View.GONE);
                    CVRented.setVisibility(View.VISIBLE);
                    agen.setVisibility(View.GONE);
                    lytambahagen.setVisibility(View.GONE);
                    TVHargaDetailListing.setVisibility(View.GONE);
                    TVHargaSewaDetailListing.setVisibility(View.GONE);
                } else if (intentRentedAgen.equals("1")) {
                    LytBadgeRented.setVisibility(View.VISIBLE);
                    LytBadgeSold.setVisibility(View.GONE);
                    LytBadge.setVisibility(View.GONE);
                    CVSold.setVisibility(View.GONE);
                    CVRented.setVisibility(View.VISIBLE);
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
                TVAlamatDetailListing.setText(intentAlamat);
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

            if (intentImg2.equals("0") && intentImg3.equals("0") && intentImg4.equals("0") && intentImg5.equals("0") && intentImg6.equals("0") && intentImg7.equals("0") && intentImg8.equals("0")) {
                IVNextImg.setVisibility(View.GONE);
                IVPrevImg.setVisibility(View.GONE);
            } else {
                IVNextImg.setVisibility(View.VISIBLE);
                IVPrevImg.setVisibility(View.VISIBLE);
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
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

                final String StringMarketable = CBMarketable.isChecked() ? "1" : "0";
                final String StringHarga = CBHarga.isChecked() ? "1" : "0";
                final String StringSelfie = CBSelfie.isChecked() ? "1" : "0";
                final String StringLokasi = CBLokasi.isChecked() ? "1" : "0";

                map.put("IdAgen", idagen);
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
                                    new SendMessageTask().execute(tokens.toArray(new String[0]));
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

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
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

                final String StringMarketable = CBMarketable.isChecked() ? "1" : "0";
                final String StringHarga = CBHarga.isChecked() ? "1" : "0";
                final String StringSelfie = CBSelfie.isChecked() ? "1" : "0";
                final String StringLokasi = CBLokasi.isChecked() ? "1" : "0";

                map.put("IdAgen", idagen);
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

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
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

                dialogTitle.setText("Gagal Ajukan Ulang Listing");
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

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
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

                dialogTitle.setText("Gagal Reject Listing");
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
    public void ShowTambahBanner(int Poin, String IdListing) {
        Dialog customDialog = new Dialog(DetailListingActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_tambah_banner);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextInputEditText Ukuran = customDialog.findViewById(R.id.ETUkuranBanner);
        TextInputEditText UkuranCustom = customDialog.findViewById(R.id.ETUkuranBannerCustom);

        TextInputLayout LytUkuran = customDialog.findViewById(R.id.lytUkuranBanner);
        TextInputLayout LytUkuranCustom = customDialog.findViewById(R.id.lytUkuranBannerCustom);

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
                    if (!Ukuran.getText().toString().isEmpty()) {
                        pDialog.setMessage("Menyimpan Data");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        RequestQueue requestQueue = Volley.newRequestQueue(DetailListingActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_BANNER,
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
                        pDialog.setMessage("Menyimpan Data");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        RequestQueue requestQueue = Volley.newRequestQueue(DetailListingActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_BANNER,
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

                                String Keterangan = "Tambah Banner Ukuran " + UkuranCustom.getText().toString();

                                map.put("IdListing", IdListing);
                                map.put("Keterangan", Keterangan);
                                map.put("PoinTambahan", String.valueOf(Poin));
                                map.put("Banner", "Ya");
                                map.put("Size", UkuranCustom.getText().toString());
                                map.put("PoinBerkurang", "0");
                                System.out.println(map);

                                return map;
                            }
                        };

                        requestQueue.add(stringRequest);
                    }
                }
            }
        });

        customDialog.show();
    }
    public void ShowTambahCoList(int Poin, String IdListing) {
        Dialog customDialog = new Dialog(DetailListingActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.dialog_tambah_colist);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextInputEditText ETNamaAgen = customDialog.findViewById(R.id.ETNamaAgen);
        TextInputEditText ETIdAgen = customDialog.findViewById(R.id.ETIdAgen);

        TextInputLayout LytDaftarAgen = customDialog.findViewById(R.id.lytDaftarAgen);

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
                        showAlertDialog(dataList);
                    }

                    @Override
                    public void onError(String errorMessage) {
                    }
                });
            }
        });

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

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_COLIST,
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

        customDialog.show();
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

                                                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_PJP,
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