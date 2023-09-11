package com.gooproper.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gooproper.R;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class EditListingActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST = 100;
    final int CODE_CAMERA_REQUEST = 101;
    final int KODE_REQUEST_KAMERA = 102;
    final int CODE_GALLERY_REQUEST_SHM = 103;
    final int CODE_CAMERA_REQUEST_SHM = 104;
    final int KODE_REQUEST_KAMERA_SHM = 105;
    final int CODE_GALLERY_REQUEST_HGB = 106;
    final int CODE_CAMERA_REQUEST_HGB = 107;
    final int KODE_REQUEST_KAMERA_HGB = 108;
    final int CODE_GALLERY_REQUEST_HSHP = 109;
    final int CODE_CAMERA_REQUEST_HSHP = 110;
    final int KODE_REQUEST_KAMERA_HSHP = 111;
    final int CODE_GALLERY_REQUEST_PPJB = 112;
    final int CODE_CAMERA_REQUEST_PPJB = 113;
    final int KODE_REQUEST_KAMERA_PPJB = 114;
    final int CODE_GALLERY_REQUEST_STRA = 115;
    final int CODE_CAMERA_REQUEST_STRA = 116;
    final int KODE_REQUEST_KAMERA_STRA = 117;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE = 123;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES = 456;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SHM = 124;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SHM = 457;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HGB = 125;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HGB = 458;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HSHP = 126;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HSHP = 459;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PPJB = 127;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PPJB = 450;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_STRA = 128;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_STRA = 455;
    private static final int MAPS_ACTIVITY_REQUEST_CODE = 3;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmapSHM, bitmapHGB, bitmapHSHP, bitmapPPJB, bitmapSTRA;
    LinearLayout lyt1, lyt2, lyt3, lyt4, lyt5, lyt6, lyt7, lyt8;
    ImageView back, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, IVShm, IVHgb, IVHshp, IVPpjb, IVStratatitle;
    Button batal, submit, select, clear, maps, BtnSHM, BtnHGB, BtnHSHP, BtnPPJB, BtnSTRA;
    ImageView hps1, hps2, hps3, hps4, hps5, hps6, hps7, hps8;
    TextInputEditText namalengkap, nohp, nik, alamat, tgllhir, rekening, bank, atasnama, jenisproperti, namaproperti, alamatproperti, sertifikat, nosertif, luas, land, lantai, bed, bath, bedart, bathart, garasi, carpot, listrik, air, perabot, ketperabot, banner, status, harga, keterangan, hadap, size;
    TextInputLayout LytSize;
    CheckBox CBSHM, CBHGB, CBHSHP, CBPPJB, CBSTRA;
    String image1, image2, image3, image4, image5, image6, image7, image8, ttd, SHM, HGB, HSHP, PPJB, STRA;
    String latitudeStr, longitudeStr, addressStr, idnull;
    Drawable DrawableSHM, DrawableHGB, DrawableHSHP, DrawablePPJB, DrawableSTRA, Drawable1, Drawable2, Drawable3, Drawable4, Drawable5, Drawable6, Drawable7, Drawable8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_listing);

        pDialog = new ProgressDialog(EditListingActivity.this);

        iv1 = findViewById(R.id.ivs1);
        iv2 = findViewById(R.id.ivs2);
        iv3 = findViewById(R.id.ivs3);
        iv4 = findViewById(R.id.ivs4);
        iv5 = findViewById(R.id.ivs5);
        iv6 = findViewById(R.id.ivs6);
        iv7 = findViewById(R.id.ivs7);
        iv8 = findViewById(R.id.ivs8);
        lyt1 = findViewById(R.id.lyts1);
        lyt2 = findViewById(R.id.lyts2);
        lyt3 = findViewById(R.id.lyts3);
        lyt4 = findViewById(R.id.lyts4);
        lyt5 = findViewById(R.id.lyts5);
        lyt6 = findViewById(R.id.lyts6);
        lyt7 = findViewById(R.id.lyts7);
        lyt8 = findViewById(R.id.lyts8);
        LytSize = findViewById(R.id.lytUkuranBanner);

        back = findViewById(R.id.backFormBtn);

        batal = findViewById(R.id.btnbatal);
        submit = findViewById(R.id.btnsubmit);
        select = findViewById(R.id.btnSelectImage);

        hps1 = findViewById(R.id.IVDelete1);
        hps2 = findViewById(R.id.IVDelete2);
        hps3 = findViewById(R.id.IVDelete3);
        hps4 = findViewById(R.id.IVDelete4);
        hps5 = findViewById(R.id.IVDelete5);
        hps6 = findViewById(R.id.IVDelete6);
        hps7 = findViewById(R.id.IVDelete7);
        hps8 = findViewById(R.id.IVDelete8);

        jenisproperti = findViewById(R.id.etjenisproperti);
        namaproperti = findViewById(R.id.etnamaproperti);
        alamatproperti = findViewById(R.id.etalamatproperti);
        luas = findViewById(R.id.etluastanah);
        land = findViewById(R.id.etluasbangunan);
        lantai = findViewById(R.id.etjumlahlantai);
        bed = findViewById(R.id.etkamartidur);
        bath = findViewById(R.id.etkamarmandi);
        bedart = findViewById(R.id.etkamartidurart);
        bathart = findViewById(R.id.etkamarmandiart);
        garasi = findViewById(R.id.etgarasi);
        carpot = findViewById(R.id.etcarpot);
        listrik = findViewById(R.id.etdayalistrik);
        air = findViewById(R.id.etsumberair);
        perabot = findViewById(R.id.etperabot);
        ketperabot = findViewById(R.id.etketperabot);
        banner = findViewById(R.id.etbanner);
        status = findViewById(R.id.etstatusproperti);
        harga = findViewById(R.id.etharga);
        keterangan = findViewById(R.id.etketerangan);
        hadap = findViewById(R.id.ethadap);
        size = findViewById(R.id.etukuranbanner);

        maps = findViewById(R.id.map);

        IVShm = findViewById(R.id.IVSHM);
        IVHgb = findViewById(R.id.IVHGB);
        IVHshp = findViewById(R.id.IVHSHP);
        IVPpjb = findViewById(R.id.IVPPJB);
        IVStratatitle = findViewById(R.id.IVStratatitle);

        CBSHM = findViewById(R.id.CBSHM);
        CBHGB = findViewById(R.id.CBHGB);
        CBHSHP = findViewById(R.id.CBHSHP);
        CBPPJB = findViewById(R.id.CBPPJB);
        CBSTRA = findViewById(R.id.CBStratatitle);

        BtnSHM = findViewById(R.id.BtnSHM);
        BtnHGB = findViewById(R.id.BtnHGB);
        BtnHSHP = findViewById(R.id.BtnHSHP);
        BtnPPJB = findViewById(R.id.BtnPPJB);
        BtnSTRA = findViewById(R.id.BtnStratatitle);

        Intent data = getIntent();
        String intentIdPraListing = data.getStringExtra("IdPraListing");
        String intentIdListing = data.getStringExtra("IdListing");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentIdInput = data.getStringExtra("IdInput");
        String intentNamaListing = data.getStringExtra("NamaListing");
        String intentAlamat = data.getStringExtra("Alamat");
        String intentLatitude = data.getStringExtra("Latitude");
        String intentLongitude = data.getStringExtra("Longitude");
        String intentLocation = data.getStringExtra("Location");
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
        String intentImgSHM = data.getStringExtra("ImgSHM");
        String intentImgHGB = data.getStringExtra("ImgHGB");
        String intentImgHSHP = data.getStringExtra("ImgHSHP");
        String intentImgPPJB = data.getStringExtra("ImgPPJB");
        String intentImgStratatitle = data.getStringExtra("ImgStratatitle");
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

        if (intentJenisProperti != null && !intentJenisProperti.isEmpty()) {
            jenisproperti.setText(intentJenisProperti);
        }
        if (intentNamaListing != null && !intentNamaListing.isEmpty()) {
            namaproperti.setText(intentNamaListing);
        }
        if (intentAlamat != null && !intentAlamat.isEmpty()) {
            alamatproperti.setText(intentAlamat);
        }
        if (intentLatitude != null && !intentLatitude.isEmpty()) {
            latitudeStr = intentLatitude;
        }
        if (intentLongitude != null && !intentLongitude.isEmpty()) {
            longitudeStr = intentLongitude;
        }
        if (intentHadap != null && !intentHadap.isEmpty()) {
            hadap.setText(intentHadap);
        }
        if (intentLand != null && !intentLand.isEmpty()) {
            land.setText(intentLand);
        }
        if (intentWide != null && !intentWide.isEmpty()) {
            luas.setText(intentWide);
        }
        if (intentLevel != null && !intentLevel.isEmpty()) {
            lantai.setText(intentLevel);
        }
        if (intentBed != null && !intentBed.isEmpty()) {
            bed.setText(intentBed);
        }
        if (intentBath != null && !intentBath.isEmpty()) {
            bath.setText(intentBath);
        }
        if (intentBedArt != null && !intentBedArt.isEmpty()) {
            bedart.setText(intentBedArt);
        }
        if (intentBathArt != null && !intentBathArt.isEmpty()) {
            bathart.setText(intentBathArt);
        }
        if (intentGarage != null && !intentGarage.isEmpty()) {
            garasi.setText(intentGarage);
        }
        if (intentCarpot != null && !intentCarpot.isEmpty()) {
            carpot.setText(intentCarpot);
        }
        if (intentListrik != null && !intentListrik.isEmpty()) {
            listrik.setText(intentListrik);
        }
        if (intentSumberAir != null && !intentSumberAir.isEmpty()) {
            air.setText(intentSumberAir);
        }
        if (intentPrabot != null && !intentPrabot.isEmpty()) {
            perabot.setText(intentPrabot);
        }
        if (intentKetPrabot != null && !intentKetPrabot.isEmpty()) {
            ketperabot.setText(intentKetPrabot);
        }
        if (intentBanner != null && !intentBanner.isEmpty()) {
            banner.setText(intentBanner);
        }
        if (intentKondisi != null && !intentKondisi.isEmpty()) {
            status.setText(intentKondisi);
        }
        if (intentDeskripsi != null && !intentDeskripsi.isEmpty()) {
            keterangan.setText(intentDeskripsi);
        }
        if (intentHarga != null && !intentHarga.isEmpty()) {
            harga.setText(intentHarga);
        }

        if (intentSHM.equals("1")){
            CBSHM.setChecked(true);
            IVShm.setVisibility(View.VISIBLE);
            BtnSHM.setVisibility(View.VISIBLE);
        }
        if (intentHGB.equals("1")){
            CBHGB.setChecked(true);
            IVHgb.setVisibility(View.VISIBLE);
            BtnHGB.setVisibility(View.VISIBLE);
        }
        if (intentHSHP.equals("1")){
            CBHSHP.setChecked(true);
            IVHshp.setVisibility(View.VISIBLE);
            BtnHSHP.setVisibility(View.VISIBLE);
        }
        if (intentPPJB.equals("1")){
            CBPPJB.setChecked(true);
            IVPpjb.setVisibility(View.VISIBLE);
            BtnPPJB.setVisibility(View.VISIBLE);
        }
        if (intentStratatitle.equals("1")){
            CBSTRA.setChecked(true);
            IVStratatitle.setVisibility(View.VISIBLE);
            BtnSTRA.setVisibility(View.VISIBLE);
        }

        if (!intentImgSHM.equals("0")){
            Picasso.get()
                    .load(intentImgSHM)
                    .into(IVShm);
        }
        if (!intentImgHGB.equals("0")){
            Picasso.get()
                    .load(intentImgHGB)
                    .into(IVHgb);
        }
        if (!intentImgHSHP.equals("0")){
            Picasso.get()
                    .load(intentImgHSHP)
                    .into(IVHshp);
        }
        if (!intentImgPPJB.equals("0")){
            Picasso.get()
                    .load(intentImgPPJB)
                    .into(IVPpjb);
        }
        if (!intentImgStratatitle.equals("0")){
            Picasso.get()
                    .load(intentImgStratatitle)
                    .into(IVStratatitle);
        }
        if (!intentImg1.equals("0")){
            Picasso.get()
                    .load(intentImg1)
                    .into(iv1);
        }
        if (!intentImg2.equals("0")){
            Picasso.get()
                    .load(intentImg2)
                    .into(iv2);
        }
        if (!intentImg3.equals("0")){
            Picasso.get()
                    .load(intentImg3)
                    .into(iv3);
        }
        if (!intentImg4.equals("0")){
            Picasso.get()
                    .load(intentImg4)
                    .into(iv4);
        }
        if (!intentImg5.equals("0")){
            Picasso.get()
                    .load(intentImg5)
                    .into(iv5);
        }
        if (!intentImg6.equals("0")){
            Picasso.get()
                    .load(intentImg6)
                    .into(iv6);
        }
        if (!intentImg7.equals("0")){
            Picasso.get()
                    .load(intentImg7)
                    .into(iv7);
        }
        if (!intentImg8.equals("0")){
            Picasso.get()
                    .load(intentImg8)
                    .into(iv8);
        }

        DrawableSHM = IVShm.getDrawable();
        DrawableHGB = IVHgb.getDrawable();
        DrawableHSHP = IVHshp.getDrawable();
        DrawablePPJB = IVPpjb.getDrawable();
        DrawableSTRA = IVStratatitle.getDrawable();
        Drawable1 = iv1.getDrawable();
        Drawable2 = iv2.getDrawable();
        Drawable3 = iv3.getDrawable();
        Drawable4 = iv4.getDrawable();
        Drawable5 = iv5.getDrawable();
        Drawable6 = iv6.getDrawable();
        Drawable7 = iv7.getDrawable();
        Drawable8 = iv8.getDrawable();
        idnull = "0";

        if (DrawableSHM != null) {
            BtnSHM.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            BtnSHM.setText("Hapus Sertifikat SHM");
            BtnSHM.setTextColor(getResources().getColor(android.R.color.white));
            BtnSHM.setOnClickListener(v -> clearShm());
        } else {
            BtnSHM.setOnClickListener(view -> showPhotoSHM());
        }

        if (DrawableHGB != null) {
            BtnHGB.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            BtnHGB.setText("Hapus Sertifikat HGB");
            BtnHGB.setTextColor(getResources().getColor(android.R.color.white));
            BtnHGB.setOnClickListener(view -> clearHgb());
        } else {
            BtnHGB.setOnClickListener(view -> showPhotoHGB());
        }

        if (DrawableHSHP != null) {
            BtnHSHP.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            BtnHSHP.setText("Hapus Sertifikat HS/HP");
            BtnHSHP.setTextColor(getResources().getColor(android.R.color.white));
            BtnHSHP.setOnClickListener(view -> clearHshp());
        } else {
            BtnHSHP.setOnClickListener(view -> showPhotoHSHP());
        }

        if (DrawablePPJB != null) {
            BtnPPJB.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            BtnPPJB.setText("Hapus Sertifikat PPJB");
            BtnPPJB.setTextColor(getResources().getColor(android.R.color.white));
            BtnPPJB.setOnClickListener(view -> clearPpjb());
        } else {
            BtnPPJB.setOnClickListener(view -> showPhotoPPJB());
        }

        if (DrawableSTRA != null) {
            BtnSTRA.setBackgroundColor(getResources().getColor(android.R.color.holo_red_light));
            BtnSTRA.setText("Hapus Sertifikat Stratatitle");
            BtnSTRA.setTextColor(getResources().getColor(android.R.color.white));
            BtnSTRA.setOnClickListener(view -> clearStra());
        } else {
            BtnSTRA.setOnClickListener(view -> showPhotoSTRA());
        }

        maps.setOnClickListener(view -> startMapsActivityForResult());
        back.setOnClickListener(view -> finish());
        batal.setOnClickListener(view -> finish());
        jenisproperti.setOnClickListener(view -> ShowJenisProperti(view));
        air.setOnClickListener(view -> ShowSumberAir(view));
        perabot.setOnClickListener(view -> ShowPerabot(view));
        banner.setOnClickListener(view -> ShowBanner(view));
        size.setOnClickListener(view -> ShowSize(view));
        status.setOnClickListener(view -> ShowStatus(view));
        hps1.setOnClickListener(view -> clearBitmap1());
        hps2.setOnClickListener(view -> clearBitmap2());
        hps3.setOnClickListener(view -> clearBitmap3());
        hps4.setOnClickListener(view -> clearBitmap4());
        hps5.setOnClickListener(view -> clearBitmap5());
        hps6.setOnClickListener(view -> clearBitmap6());
        hps7.setOnClickListener(view -> clearBitmap7());
        hps8.setOnClickListener(view -> clearBitmap8());
        banner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Ya")) {
                    LytSize.setVisibility(View.VISIBLE);
                } else {
                    LytSize.setVisibility(View.GONE);
                }
            }
        });

        CBSHM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVShm.setVisibility(View.VISIBLE);
                    BtnSHM.setVisibility(View.VISIBLE);
                } else {
                    IVShm.setVisibility(View.GONE);
                    BtnSHM.setVisibility(View.GONE);
                }
            }
        });

        CBHGB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVHgb.setVisibility(View.VISIBLE);
                    BtnHGB.setVisibility(View.VISIBLE);
                } else {
                    IVHgb.setVisibility(View.GONE);
                    BtnHGB.setVisibility(View.GONE);
                }
            }
        });

        CBHSHP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVHshp.setVisibility(View.VISIBLE);
                    BtnHSHP.setVisibility(View.VISIBLE);
                } else {
                    IVHshp.setVisibility(View.GONE);
                    BtnHSHP.setVisibility(View.GONE);
                }
            }
        });

        CBPPJB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVPpjb.setVisibility(View.VISIBLE);
                    BtnPPJB.setVisibility(View.VISIBLE);
                } else {
                    IVPpjb.setVisibility(View.GONE);
                    BtnPPJB.setVisibility(View.GONE);
                }
            }
        });

        CBSTRA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVStratatitle.setVisibility(View.VISIBLE);
                    BtnSTRA.setVisibility(View.VISIBLE);
                } else {
                    IVStratatitle.setVisibility(View.GONE);
                    BtnSTRA.setVisibility(View.GONE);
                }
            }
        });

        BtnSHM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DrawableSHM == null || bitmapSHM == null){
                    showPhotoSHM();
                } else {

                }
            }
        });
        BtnHGB.setOnClickListener(view -> showPhotoHGB());
        BtnHSHP.setOnClickListener(view -> showPhotoHSHP());
        BtnPPJB.setOnClickListener(view -> showPhotoPPJB());
        BtnSTRA.setOnClickListener(view -> showPhotoSTRA());

        submit.setOnClickListener(view -> {
            if (longitudeStr == null && latitudeStr == null) {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap tambah lokasi terlebih dahulu");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            } else {
                if (Validate()) {
                    if (bitmap1 == null) {
                        Dialog customDialog = new Dialog(EditListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                        TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                        tv.setText("Harap Tambahkan Gambar");

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                        Glide.with(EditListingActivity.this)
                                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
                        UpdatePraListing();
                    }
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void startMapsActivityForResult() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivityForResult(intent, MAPS_ACTIVITY_REQUEST_CODE);
    }

    private void showPhotoSelectionDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST);
                                break;
                            case 1:
                                requestPermissions();
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void showPhotoSHM() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_SHM);
                                break;
                            case 1:
                                requestPermissionsSHM();
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void showPhotoHGB() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_HGB);
                                break;
                            case 1:
                                requestPermissionsHGB();
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void showPhotoHSHP() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_HSHP);
                                break;
                            case 1:
                                requestPermissionsHSHP();
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void showPhotoPPJB() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_PPJB);
                                break;
                            case 1:
                                requestPermissionsPPJB();
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void showPhotoSTRA() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(EditListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_STRA);
                                break;
                            case 1:
                                requestPermissionsSTRA();
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void requestPermissions() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES);
        }
    }

    private void requestPermissionsSHM() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SHM);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SHM);
        }
    }

    private void requestPermissionsHGB() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HGB);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HGB);
        }
    }

    private void requestPermissionsHSHP() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HSHP);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HSHP);
        }
    }

    private void requestPermissionsPPJB() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PPJB);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PPJB);
        }
    }

    private void requestPermissionsSTRA() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_STRA);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_STRA);
        }
    }

    private void bukaKamera() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA);
        }
    }

    private void bukaKameraSHM() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_SHM);
        }
    }

    private void bukaKameraHGB() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_HGB);
        }
    }

    private void bukaKameraHSHP() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_HSHP);
        }
    }

    private void bukaKameraPPJB() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_PPJB);
        }
    }

    private void bukaKameraSTRA() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_STRA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SHM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_SHM);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HGB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HGB);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HSHP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HSHP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PPJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PPJB);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_STRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_STRA);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SHM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_SHM);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HGB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HGB);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HSHP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HSHP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PPJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PPJB);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_STRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_STRA);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
            } else {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }

            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_SHM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_SHM);
            } else {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }

            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_HGB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HGB);
            } else {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }

            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_HSHP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_HSHP);
            } else {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }

            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_PPJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PPJB);
            } else {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }

            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_STRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_STRA);
            } else {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }

            return;
        } else if (requestCode == CODE_CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditListingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_SHM) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraSHM();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditListingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_HGB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraHGB();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditListingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_HSHP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraHSHP();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditListingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_PPJB) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraPPJB();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditListingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_STRA) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraSTRA();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditListingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAPS_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {

                latitudeStr = data.getStringExtra("latitude");
                longitudeStr = data.getStringExtra("longitude");
                addressStr = data.getStringExtra("address");
                alamatproperti.setText(addressStr);
            }
        }

        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                if (bitmap1 == null) {
                    bitmap1 = BitmapFactory.decodeStream(inputStream);
                    lyt1.setVisibility(View.VISIBLE);
                    iv1.setImageBitmap(bitmap1);
                } else if (bitmap2 == null) {
                    bitmap2 = BitmapFactory.decodeStream(inputStream);
                    lyt2.setVisibility(View.VISIBLE);
                    iv2.setImageBitmap(bitmap2);
                } else if (bitmap3 == null) {
                    bitmap3 = BitmapFactory.decodeStream(inputStream);
                    lyt3.setVisibility(View.VISIBLE);
                    iv3.setImageBitmap(bitmap3);
                } else if (bitmap4 == null) {
                    bitmap4 = BitmapFactory.decodeStream(inputStream);
                    lyt4.setVisibility(View.VISIBLE);
                    iv4.setImageBitmap(bitmap4);
                } else if (bitmap5 == null) {
                    bitmap5 = BitmapFactory.decodeStream(inputStream);
                    lyt5.setVisibility(View.VISIBLE);
                    iv5.setImageBitmap(bitmap5);
                } else if (bitmap6 == null) {
                    bitmap6 = BitmapFactory.decodeStream(inputStream);
                    lyt6.setVisibility(View.VISIBLE);
                    iv6.setImageBitmap(bitmap6);
                } else if (bitmap7 == null) {
                    bitmap7 = BitmapFactory.decodeStream(inputStream);
                    lyt7.setVisibility(View.VISIBLE);
                    iv7.setImageBitmap(bitmap7);
                } else if (bitmap8 == null) {
                    bitmap8 = BitmapFactory.decodeStream(inputStream);
                    lyt8.setVisibility(View.VISIBLE);
                    iv8.setImageBitmap(bitmap8);
                } else {

                    select.setVisibility(View.GONE);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CODE_GALLERY_REQUEST_SHM && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                if (bitmapSHM == null) {
                    bitmapSHM = BitmapFactory.decodeStream(inputStream);
                    IVShm.setImageBitmap(bitmapSHM);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CODE_GALLERY_REQUEST_HGB && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                if (bitmapHGB == null) {
                    bitmapHGB = BitmapFactory.decodeStream(inputStream);
                    IVHgb.setImageBitmap(bitmapHGB);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CODE_GALLERY_REQUEST_HSHP && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                if (bitmapHSHP == null) {
                    bitmapHSHP = BitmapFactory.decodeStream(inputStream);
                    IVHshp.setImageBitmap(bitmapHSHP);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CODE_GALLERY_REQUEST_PPJB && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                if (bitmapPPJB == null) {
                    bitmapPPJB = BitmapFactory.decodeStream(inputStream);
                    IVPpjb.setImageBitmap(bitmapPPJB);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CODE_GALLERY_REQUEST_STRA && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                if (bitmapSTRA == null) {
                    bitmapSTRA = BitmapFactory.decodeStream(inputStream);
                    IVStratatitle.setImageBitmap(bitmapSTRA);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == KODE_REQUEST_KAMERA && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this, imageBitmap));
                        if (bitmap1 == null) {
                            bitmap1 = BitmapFactory.decodeStream(inputStream);
                            lyt1.setVisibility(View.VISIBLE);
                            iv1.setImageBitmap(bitmap1);
                        } else if (bitmap2 == null) {
                            bitmap2 = BitmapFactory.decodeStream(inputStream);
                            lyt2.setVisibility(View.VISIBLE);
                            iv2.setImageBitmap(bitmap2);
                        } else if (bitmap3 == null) {
                            bitmap3 = BitmapFactory.decodeStream(inputStream);
                            lyt3.setVisibility(View.VISIBLE);
                            iv3.setImageBitmap(bitmap3);
                        } else if (bitmap4 == null) {
                            bitmap4 = BitmapFactory.decodeStream(inputStream);
                            lyt4.setVisibility(View.VISIBLE);
                            iv4.setImageBitmap(bitmap4);
                        } else if (bitmap5 == null) {
                            bitmap5 = BitmapFactory.decodeStream(inputStream);
                            lyt5.setVisibility(View.VISIBLE);
                            iv5.setImageBitmap(bitmap5);
                        } else if (bitmap6 == null) {
                            bitmap6 = BitmapFactory.decodeStream(inputStream);
                            lyt6.setVisibility(View.VISIBLE);
                            iv6.setImageBitmap(bitmap6);
                        } else if (bitmap7 == null) {
                            bitmap7 = BitmapFactory.decodeStream(inputStream);
                            lyt7.setVisibility(View.VISIBLE);
                            iv7.setImageBitmap(bitmap7);
                        } else if (bitmap8 == null) {
                            bitmap8 = BitmapFactory.decodeStream(inputStream);
                            lyt8.setVisibility(View.VISIBLE);
                            iv8.setImageBitmap(bitmap8);
                        } else {

                            select.setVisibility(View.GONE);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == KODE_REQUEST_KAMERA_SHM && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this, imageBitmap));
                        bitmapSHM = BitmapFactory.decodeStream(inputStream);
                        IVShm.setImageBitmap(bitmapSHM);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == KODE_REQUEST_KAMERA_HGB && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this, imageBitmap));
                        bitmapHGB = BitmapFactory.decodeStream(inputStream);
                        IVHgb.setImageBitmap(bitmapHGB);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == KODE_REQUEST_KAMERA_HSHP && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this, imageBitmap));
                        bitmapHSHP = BitmapFactory.decodeStream(inputStream);
                        IVHshp.setImageBitmap(bitmapHSHP);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == KODE_REQUEST_KAMERA_PPJB && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this, imageBitmap));
                        bitmapPPJB = BitmapFactory.decodeStream(inputStream);
                        IVPpjb.setImageBitmap(bitmapPPJB);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else if (requestCode == KODE_REQUEST_KAMERA_STRA && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this, imageBitmap));
                        bitmapSTRA = BitmapFactory.decodeStream(inputStream);
                        IVStratatitle.setImageBitmap(bitmapSTRA);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image"));
        CropImage.activity(sourceUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setOutputUri(destinationUri)
                .start(this);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }

    private void clearShm() {
        if (bitmapSHM != null && !bitmapSHM.isRecycled()) {
            bitmapSHM.recycle();
            bitmapSHM = null;
            IVShm.setImageDrawable(null);
        }
    }

    private void clearHgb() {
        if (bitmapHGB != null && !bitmapHGB.isRecycled()) {
            bitmapHGB.recycle();
            bitmapHGB = null;
            IVHgb.setImageDrawable(null);
        }
    }

    private void clearHshp() {
        if (bitmapHSHP != null && !bitmapHSHP.isRecycled()) {
            bitmapHSHP.recycle();
            bitmapHSHP = null;
            IVHshp.setImageDrawable(null);
        }
    }

    private void clearPpjb() {
        if (bitmapPPJB != null && !bitmapPPJB.isRecycled()) {
            bitmapPPJB.recycle();
            bitmapPPJB = null;
            IVPpjb.setImageDrawable(null);
        }
    }

    private void clearStra() {
        if (bitmapSTRA != null && !bitmapSTRA.isRecycled()) {
            bitmapSTRA.recycle();
            bitmapSTRA = null;
            IVStratatitle.setImageDrawable(null);
        }
    }

    private void clearBitmap1() {
        if (bitmap1 != null && !bitmap1.isRecycled()) {
            bitmap1.recycle();
            bitmap1 = null;
            lyt1.setVisibility(View.GONE);
        }
    }

    private void clearBitmap2() {
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            bitmap2.recycle();
            bitmap2 = null;
            lyt2.setVisibility(View.GONE);
        }
    }

    private void clearBitmap3() {
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            bitmap3.recycle();
            bitmap3 = null;
            lyt3.setVisibility(View.GONE);
        }
    }

    private void clearBitmap4() {
        if (bitmap4 != null && !bitmap4.isRecycled()) {
            bitmap4.recycle();
            bitmap4 = null;
            lyt4.setVisibility(View.GONE);
        }
    }

    private void clearBitmap5() {
        if (bitmap5 != null && !bitmap5.isRecycled()) {
            bitmap5.recycle();
            bitmap5 = null;
            lyt5.setVisibility(View.GONE);
        }
    }

    private void clearBitmap6() {
        if (bitmap6 != null && !bitmap6.isRecycled()) {
            bitmap6.recycle();
            bitmap6 = null;
            lyt6.setVisibility(View.GONE);
        }
    }

    private void clearBitmap7() {
        if (bitmap7 != null && !bitmap7.isRecycled()) {
            bitmap7.recycle();
            bitmap7 = null;
            lyt7.setVisibility(View.GONE);
        }
    }

    private void clearBitmap8() {
        if (bitmap8 != null && !bitmap8.isRecycled()) {
            bitmap8.recycle();
            bitmap8 = null;
            lyt8.setVisibility(View.GONE);
        }
    }

    public void ShowJenisProperti(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Jenis Properti");

        final CharSequence[] JenisProperti = {"Rumah", "Ruko", "Tanah", "Gudang", "Ruang Usaha", "Villa", "Apartemen", "Pabrik", "Kantor", "Hotel", "Kondohotel"};
        final int[] SelectedJenisProperti = {0};

        builder.setSingleChoiceItems(JenisProperti, SelectedJenisProperti[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedJenisProperti[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jenisproperti.setText(JenisProperti[SelectedJenisProperti[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showCustomTypeInputDialog() {
        final EditText editTextCustomType = new EditText(this);
        editTextCustomType.setHint("Masukkan Tipe Sertifikat");

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Tipe Sertifikat")
                .setView(editTextCustomType)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String customType = editTextCustomType.getText().toString().trim();
                        if (!customType.isEmpty()) {
                            sertifikat.setText(customType);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowSumberAir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Sumber Air");

        final CharSequence[] SumberAir = {"PAM atau PDAM", "Sumur Pompa", "Sumur Bor", "Sumur Resapan", "Sumur Galian"};
        final int[] SelectedSumberAir = {0};

        builder.setSingleChoiceItems(SumberAir, SelectedSumberAir[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedSumberAir[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                air.setText(SumberAir[SelectedSumberAir[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowPerabot(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ketersediaan Prabot");

        final CharSequence[] Perabot = {"Ada", "Tidak"};
        final int[] SelectedPerabot = {0};

        builder.setSingleChoiceItems(Perabot, SelectedPerabot[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedPerabot[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                perabot.setText(Perabot[SelectedPerabot[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowBanner(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Pemasangan Banner");

        final CharSequence[] Banner = {"Ya", "Tidak"};
        final int[] SelectedBanner = {0};

        builder.setSingleChoiceItems(Banner, SelectedBanner[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedBanner[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                banner.setText(Banner[SelectedBanner[0]]);
                if (Banner[SelectedBanner[0]].equals("Ya")) {
                    size.setVisibility(View.VISIBLE);
                }
                /*if (Banner[SelectedBanner[0]] == "Ya"){
                    size.setVisibility(View.VISIBLE);
                }*/
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowSize(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ukuran Banner");

        final CharSequence[] Banner = {"80 X 90", "100 X 125", "180 X 80", "Lainnya"};
        final int[] SelectedBanner = {0};

        builder.setSingleChoiceItems(Banner, SelectedBanner[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == Banner.length - 1) {
                    ShowCustomSize();
                } else {
                    size.setText(Banner[which]);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ShowCustomSize() {
        final EditText editTextCustomType = new EditText(this);
        editTextCustomType.setHint("Masukkan Ukuran Banner");
        editTextCustomType.setTextColor(getResources().getColor(android.R.color.black));
        editTextCustomType.setHintTextColor(getResources().getColor(android.R.color.black));
        editTextCustomType.setBackgroundColor(getResources().getColor(android.R.color.white));
        editTextCustomType.setPadding(50, 20, 50, 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ukuran Banner")
                .setView(editTextCustomType)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String customType = editTextCustomType.getText().toString().trim();
                        if (!customType.isEmpty()) {
                            size.setText(customType);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowStatus(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Status Properti");

        final CharSequence[] Status = {"Jual", "Sewa"};
        final int[] SelectedStatus = {0};

        builder.setSingleChoiceItems(Status, SelectedStatus[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedStatus[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                status.setText(Status[SelectedStatus[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }

    public boolean Validate() {
        if (jenisproperti.getText().toString().equals("")) {
            jenisproperti.setError("Harap Isi Jenis Properti");
            jenisproperti.requestFocus();
            return false;
        }
        if (namaproperti.getText().toString().equals("")) {
            namaproperti.setError("Harap Isi Nama Properti");
            namaproperti.requestFocus();
            return false;
        }
        if (alamatproperti.getText().toString().equals("")) {
            alamatproperti.setError("Harap Isi Alamat Properti");
            alamatproperti.requestFocus();
            return false;
        }
        if (status.getText().toString().equals("")) {
            status.setError("Harap Isi Status Properti");
            status.requestFocus();
            return false;
        }
        if (banner.getText().toString().equals("")) {
            banner.setError("Harap Isi Pemasangan Banner");
            banner.requestFocus();
            return false;
        } else if (banner.getText().toString().equals("Ya")) {
            if (size.getText().toString().equals("")) {
                size.setError("Harap Isi Ukuran Banner");
                banner.requestFocus();
            }
        }
        if (harga.getText().toString().equals("")) {
            harga.setError("Harap Isi Harga Properti");
            harga.requestFocus();
            return false;
        }
        if (keterangan.getText().toString().equals("")) {
            keterangan.setError("Harap Isi Deskripsi Properti");
            keterangan.requestFocus();
            return false;
        }
        if (CBSHM.isChecked()) {
            if (DrawableSHM == null || bitmapSHM == null){
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Tambahkan Gambar SHM");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        if (CBHGB.isChecked()) {
            if (DrawableHGB == null || bitmapHGB == null) {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Tambahkan Gambar HGB");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        if (CBHSHP.isChecked()) {
            if (DrawableHSHP == null || bitmapHSHP == null) {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Tambahkan Gambar HS/HP");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        if (CBPPJB.isChecked()) {
            if (DrawablePPJB == null || bitmapPPJB == null) {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Tambahkan Gambar PPJB");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        if (CBSTRA.isChecked()) {
            if (DrawableSTRA == null || bitmapSTRA == null) {
                Dialog customDialog = new Dialog(EditListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Tambahkan Gambar STratatitle");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        return true;
    }

    private void UpdatePraListing() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Dialog customDialog = new Dialog(EditListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Berhasil Menambahkan Listingan");
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(EditListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.cancel();
                        Dialog customDialog = new Dialog(EditListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Gagal Tambah Listingan");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(EditListingActivity.this)
                                .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (bitmap1 == null) {
                    image1 = "0";
                } else {
                    image1 = imageToString(bitmap1);
                }
                if (bitmap2 == null) {
                    image2 = "0";
                } else {
                    image2 = imageToString(bitmap2);
                }
                if (bitmap3 == null) {
                    image3 = "0";
                } else {
                    image3 = imageToString(bitmap3);
                }
                if (bitmap4 == null) {
                    image4 = "0";
                } else {
                    image4 = imageToString(bitmap4);
                }
                if (bitmap5 == null) {
                    image5 = "0";
                } else {
                    image5 = imageToString(bitmap5);
                }
                if (bitmap6 == null) {
                    image6 = "0";
                } else {
                    image6 = imageToString(bitmap6);
                }
                if (bitmap7 == null) {
                    image7 = "0";
                } else {
                    image7 = imageToString(bitmap7);
                }
                if (bitmap8 == null) {
                    image8 = "0";
                } else {
                    image8 = imageToString(bitmap8);
                }
                if (bitmapSHM == null) {
                    SHM = "0";
                } else {
                    SHM = imageToString(bitmapSHM);
                }
                if (bitmapHGB == null) {
                    HGB = "0";
                } else {
                    HGB = imageToString(bitmapHGB);
                }
                if (bitmapHSHP == null) {
                    HSHP = "0";
                } else {
                    HSHP = imageToString(bitmapHSHP);
                }
                if (bitmapPPJB == null) {
                    PPJB = "0";
                } else {
                    PPJB = imageToString(bitmapPPJB);
                }
                if (bitmapSTRA == null) {
                    STRA = "0";
                } else {
                    STRA = imageToString(bitmapSTRA);
                }

                final String StringSHM = CBSHM.isChecked() ? "1" : "0";
                final String StringHGB = CBHGB.isChecked() ? "1" : "0";
                final String StringHSHP = CBHSHP.isChecked() ? "1" : "0";
                final String StringPPJB = CBPPJB.isChecked() ? "1" : "0";
                final String StringSTRA = CBSTRA.isChecked() ? "1" : "0";

                map.put("NamaListing", namaproperti.getText().toString());
                map.put("Alamat", alamatproperti.getText().toString());
                map.put("Latitude", latitudeStr);
                map.put("Longitude", longitudeStr);
                map.put("Location", alamatproperti.getText().toString());
                map.put("Wide", luas.getText().toString());
                map.put("Land", land.getText().toString());
                map.put("Listrik", listrik.getText().toString());
                map.put("Level", lantai.getText().toString());
                map.put("Bed", bed.getText().toString());
                map.put("Bath", bath.getText().toString());
                map.put("BedArt", bedart.getText().toString());
                map.put("BathArt", bathart.getText().toString());
                map.put("Garage", garasi.getText().toString());
                map.put("Carpot", carpot.getText().toString());
                map.put("Hadap", hadap.getText().toString());
                map.put("SHM", StringSHM);
                map.put("HGB", StringHGB);
                map.put("HSHP", StringHSHP);
                map.put("PPJB", StringPPJB);
                map.put("Stratatitle", StringSTRA);
                map.put("ImgSHM", SHM);
                map.put("ImgHGB", HGB);
                map.put("ImgHSHP", HSHP);
                map.put("ImgPPJB", PPJB);
                map.put("ImgStratatitle", STRA);
                map.put("JenisProperti", jenisproperti.getText().toString());
                map.put("SumberAir", air.getText().toString());
                map.put("Kondisi", status.getText().toString());
                map.put("Deskripsi", keterangan.getText().toString());
                map.put("Prabot", perabot.getText().toString());
                map.put("KetPrabot", ketperabot.getText().toString());
                map.put("Ttd", ttd);
                map.put("Banner", banner.getText().toString());
                map.put("Size", size.getText().toString());
                map.put("Harga", harga.getText().toString());
                map.put("Img1", image1);
                map.put("Img2", image2);
                map.put("Img3", image3);
                map.put("Img4", image4);
                map.put("Img5", image5);
                map.put("Img6", image6);
                map.put("Img7", image7);
                map.put("Img8", image8);
                map.put("Video", idnull);
                map.put("LinkFacebook", idnull);
                map.put("LinkTiktok", idnull);
                map.put("LinkInstagram", idnull);
                map.put("LinkYoutube", idnull);
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}