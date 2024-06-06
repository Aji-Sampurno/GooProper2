package com.gooproper.ui.detail.listing;

import androidx.annotation.NonNull;
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
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DetailListingDeepActivity extends AppCompatActivity implements OnMapReadyCallback {

    ProgressDialog PDDetailListingDeep;
    TextView TVNamaDetailListing, TVAlamatDetailListing, TVHargaDetailListing, TVHargaSewaDetailListing, TVViewsDetailListing, TVLikeDetailListing, TVBedDetailListing, TVNamaAgen, TVBathDetailListing, TVWideDetailListing, TVLandDetailListing, TVDimensiDetailListing, TVTipeDetailListing, TVStatusDetailListing, TVSertifikatDetailListing, TVLuasDetailListing, TVKamarTidurDetailListing, TVKamarMandiDetailListing, TVLantaiDetailListing, TVGarasiDetailListing, TVCarpotDetailListing, TVListrikDetailListing, TVSumberAirDetailListing, TVPerabotDetailListing, TVSizeBanner, TVDeskripsiDetailListing, TVNoData, TVNoDataPdf, TVPriority, TVKondisi, TVNoPjp, TVNoDataPjp, TVFee;
    ImageView IVFlowUp, IVWhatsapp, IVInstagram, IVFavorite, IVFavoriteOn, IVShare, IVStar1, IVStar2, IVStar3, IVStar4, IVStar5 ;
    ScrollView scrollView;
    CardView agen;
    LinearLayout LytBadge;
    String status, idpralisting, idagen, idlisting, agenid, idpengguna;
    String BuyerNama, BuyerTelp, BuyerKeterangan, BuyerTanggal, BuyerIdAgen, BuyerIdListing, BuyerIdInput, BuyerJam;
    String NamaMaps;
    String imageUrl, namaAgen, telpAgen;
    Intent intent;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    ArrayList<String> images = new ArrayList<>();
    private MapView mapView;
    private GoogleMap googleMap;
    double lat, lng;
    String productId, path, StringNamaBuyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_listing_deep);

        PDDetailListingDeep = new ProgressDialog(DetailListingDeepActivity.this);
        viewPager = findViewById(R.id.VPDetailListingDeep);
        agen = findViewById(R.id.LytAgenDetailListingDeep);
        scrollView = findViewById(R.id.SVDetailListingDeep);
        LytBadge = findViewById(R.id.LytBadgeDeep);

        TVNamaDetailListing = findViewById(R.id.TVNamaDetailListingDeep);
        TVAlamatDetailListing = findViewById(R.id.TVAlamatDetailListingDeep);
        TVHargaDetailListing = findViewById(R.id.TVHargaDetailListingDeep);
        TVHargaSewaDetailListing = findViewById(R.id.TVHargaSewaDetailListingDeep);
        TVViewsDetailListing = findViewById(R.id.TVViewsDetailListingDeep);
        TVLikeDetailListing = findViewById(R.id.TVLikeDetailListingDeep);
        TVBedDetailListing = findViewById(R.id.TVBedDetailListingDeep);
        TVBathDetailListing = findViewById(R.id.TVBathDetailListingDeep);
        TVWideDetailListing = findViewById(R.id.TVWideDetailListingDeep);
        TVLandDetailListing = findViewById(R.id.TVLandDetailListingDeep);
        TVTipeDetailListing = findViewById(R.id.TVTipeHunianDetailListingDeep);
        TVStatusDetailListing = findViewById(R.id.TVStatusHunianDetailListingDeep);
        TVSertifikatDetailListing = findViewById(R.id.TVSertifikatDetailListingDeep);
        TVLuasDetailListing = findViewById(R.id.TVLuasHunianDetailListingDeep);
        TVDimensiDetailListing = findViewById(R.id.TVDimensiDetailListingDeep);
        TVKamarTidurDetailListing = findViewById(R.id.TVKamarTidurHunianDetailListingDeep);
        TVKamarMandiDetailListing = findViewById(R.id.TVKamarMandiHunianDetailListingDeep);
        TVLantaiDetailListing = findViewById(R.id.TVLevelDetailListingDeep);
        TVGarasiDetailListing = findViewById(R.id.TVGarasiDetailListingDeep);
        TVCarpotDetailListing = findViewById(R.id.TVCarportDetailListingDeep);
        TVListrikDetailListing = findViewById(R.id.TVListrikDetailListingDeep);
        TVSumberAirDetailListing = findViewById(R.id.TVSumberAirDetailListingDeep);
        TVPerabotDetailListing = findViewById(R.id.TVPerabotDetailListingDeep);
        TVDeskripsiDetailListing = findViewById(R.id.TVDeskripsiDetailListingDeep);
        TVNamaAgen = findViewById(R.id.TVNamaAgenDetailListingDeep);
        TVPriority = findViewById(R.id.TVPriorityDeep);
        TVKondisi = findViewById(R.id.TVKondisiDeep);
        TVNoPjp = findViewById(R.id.TVNoPjpDeep);

        IVFlowUp = findViewById(R.id.IVFlowUpAgenDetailListingDeep);
        IVWhatsapp = findViewById(R.id.IVNoTelpAgenDetailListingDeep);
        IVInstagram = findViewById(R.id.IVInstagramAgenDetailListingDeep);
        IVFavorite = findViewById(R.id.IVFavoriteDetailListingDeep);
        IVFavoriteOn = findViewById(R.id.IVFavoriteOnDetailListingDeep);
        IVShare = findViewById(R.id.IVShareDetailListingDeep);
        IVStar1 = findViewById(R.id.Star1Deep);
        IVStar2 = findViewById(R.id.Star2Deep);
        IVStar3 = findViewById(R.id.Star3Deep);
        IVStar4 = findViewById(R.id.Star4Deep);
        IVStar5 = findViewById(R.id.Star5Deep);

        mapView = findViewById(R.id.MVDetailListingDeep);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        IVShare.setVisibility(View.GONE);
        IVFavorite.setVisibility(View.GONE);
        IVFavoriteOn.setVisibility(View.GONE);

        status = Preferences.getKeyStatus(this);

        if (status.equals("1")){
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
        } else if (status.equals("2")) {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
        } else if (status.equals("3")){
            StringNamaBuyer = Preferences.getKeyNama(this);
        } else {
            StringNamaBuyer = Preferences.getKeyNamaLengkap(this);
            IVFlowUp.setVisibility(View.INVISIBLE);
        }

        intent = getIntent();
        Uri data = intent.getData();
        path = data.getPath();
        productId = path.substring(path.lastIndexOf('/') + 1);
        displayProductDetails(productId);
        
        IVShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String deepLinkUrl = "https://gooproper.com/listing/"+productId;
                String shareMessage = "Lihat listingan kami \n hubungi : \n "+namaAgen+" - "+telpAgen+"\n" + deepLinkUrl;

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
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void displayProductDetails(String productId) {
        PDDetailListingDeep.setMessage("Memuat Data...");
        PDDetailListingDeep.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_DEEP+productId,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDDetailListingDeep.cancel();
                        PDDetailListingDeep.dismiss();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                FormatCurrency currency = new FormatCurrency();
                                String intentIdListing = data.getString("IdListing");
                                String intentIdAgen = data.getString("IdAgen");
                                String intentIdAgenCo = data.getString("IdAgenCo");
                                String intentIdInput = data.getString("IdInput");
                                String intentNamaListing = data.getString("NamaListing");
                                String intentAlamat = data.getString("Alamat");
                                String intentLatitude = data.getString("Latitude");
                                String intentLongitude = data.getString("Longitude");
                                String intentLocation = data.getString("Location");
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
                                String intentTglInput = data.getString("TglInput");
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
                                String intentVideo = data.getString("Video");
                                String intentLinkFacebook = data.getString("LinkFacebook");
                                String intentLinkTiktok = data.getString("LinkTiktok");
                                String intentLinkInstagram = data.getString("LinkInstagram");
                                String intentLinkYoutube = data.getString("LinkYoutube");
                                String intentIsAdmin = data.getString("IsAdmin");
                                String intentIsManager = data.getString("IsManager");
                                String intentSold = data.getString("Sold");
                                String intentRented = data.getString("Rented");
                                String intentView = data.getString("View");
                                String intentMarketable = data.getString("Marketable");
                                String intentStatusHarga = data.getString("StatusHarga");
                                String intentNama = data.getString("Nama");
                                String intentNoTelp = data.getString("NoTelp");
                                String intentInstagram = data.getString("Instagram");
                                String intentFee = data.getString("Fee");
                                String intentNamaVendor = data.getString("NamaVendor");
                                String intentNoTelpVendor = data.getString("NoTelpVendor");
                                String intentIdTemplate = data.getString("IdTemplate");
                                String intentTemplate = data.getString("Template");
                                String intentTemplateBlank = data.getString("TemplateBlank");

                                if (intentIdAgen.equals("null")) {
                                    if (intentSold.equals("1")){
                                        LytBadge.setVisibility(View.GONE);
                                        TVHargaDetailListing.setVisibility(View.GONE);
                                        TVHargaSewaDetailListing.setVisibility(View.GONE);
                                    } else if (intentRented.equals("1")) {
                                        LytBadge.setVisibility(View.GONE);
                                        TVHargaDetailListing.setVisibility(View.GONE);
                                        TVHargaSewaDetailListing.setVisibility(View.GONE);
                                    } else {
                                        idagen = agenid;
                                    }
                                } else {
                                    if (intentSold.equals("1")){
                                        LytBadge.setVisibility(View.GONE);
                                        TVHargaDetailListing.setVisibility(View.GONE);
                                        TVHargaSewaDetailListing.setVisibility(View.GONE);
                                    } else if (intentRented.equals("1")) {
                                        LytBadge.setVisibility(View.GONE);
                                        TVHargaDetailListing.setVisibility(View.GONE);
                                        TVHargaSewaDetailListing.setVisibility(View.GONE);
                                    } else {
                                        idagen = intentIdAgen;
                                    }
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
                                    TVNamaDetailListing.setText("-");
                                } else {
                                    TVNamaDetailListing.setText(intentNamaListing);
                                }
                                if (intentAlamat.isEmpty()) {
                                    TVAlamatDetailListing.setText(intentAlamat);
                                } else {
                                    TVAlamatDetailListing.setText(intentAlamat);
                                }
                                if (intentKondisi.equals("Jual")){
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
                                        if (intentHargaSewa.isEmpty()){
                                            TVHargaDetailListing.setText("Rp. -");
                                            TVHargaSewaDetailListing.setText("Rp. -");
                                        } else {
                                            TVHargaDetailListing.setText("Rp. - /");
                                            TVHargaSewaDetailListing.setText(currency.formatRupiah(intentHargaSewa));
                                        }
                                    } else {
                                        if (intentHargaSewa.isEmpty()){
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
                                    messageBuilder.append(" HGB");
                                }
                                if (!intentHSHP.isEmpty() && !intentHSHP.equals("0")) {
                                    messageBuilder.append(" HS/HP");
                                }
                                if (!intentPPJB.isEmpty() && !intentPPJB.equals("0")) {
                                    messageBuilder.append(" PPJB");
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
                                if (intentDimensi.isEmpty()){
                                    TVDimensiDetailListing.setText(": -");
                                } else {
                                    TVDimensiDetailListing.setText(": " + intentDimensi);
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
                                TVNamaAgen.setText(intentNama);
                                if (intentLatitude.equals("0") || intentLongitude.equals("0")){
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

                                TVNamaAgen.setText(intentNama);
                                NamaMaps = intentNamaListing;

                                RequestQueue queue = Volley.newRequestQueue(getBaseContext());
                                JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LIKE + intentIdListing,null,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                for(int i = 0 ; i < response.length(); i++)
                                                {
                                                    try {
                                                        JSONObject data = response.getJSONObject(i);
                                                        String countlike = data.getString("fav");

                                                        int like = Integer.parseInt(countlike);

                                                        if (like >= 50){
                                                            TVLikeDetailListing.setText(countlike+" Favorite");
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

                                adapter = new ViewPagerAdapter(DetailListingDeepActivity.this, images);
                                viewPager.setPadding(0, 0, 0, 0);
                                viewPager.setAdapter(adapter);
                            } catch (JSONException e) {
                                PDDetailListingDeep.dismiss();
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(DetailListingDeepActivity.this);
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
                                        displayProductDetails(productId);
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
                        PDDetailListingDeep.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(DetailListingDeepActivity.this);
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
                                displayProductDetails(productId);
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

    private void shareDeepLink(String productId) {
        String deepLinkUrl = "https://gooproper.com/listing/"+productId;
        String shareMessage = "Lihat listingan kami \n hubungi : \n "+namaAgen+" - "+telpAgen+"\n" + deepLinkUrl;

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
                    RequestQueue queue = Volley.newRequestQueue(this);
                    JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_DEEP+productId,null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    for(int i = 0 ; i < response.length(); i++)
                                    {
                                        try {
                                            JSONObject data = response.getJSONObject(i);
                                            String intentLatitude = data.getString("Latitude");
                                            String intentLongitude = data.getString("Longitude");

                                            lat = Double.parseDouble(intentLatitude);
                                            lng = Double.parseDouble(intentLongitude);

                                            LatLng currentLocation = new LatLng(lat, lng);
                                            googleMap.addMarker(new MarkerOptions()
                                                    .position(currentLocation)
                                                    .title(NamaMaps));
                                            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15f));

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