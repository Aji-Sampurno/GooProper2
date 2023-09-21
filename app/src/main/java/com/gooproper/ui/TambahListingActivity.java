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
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
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
import android.widget.Toast;

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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gooproper.R;
import com.gooproper.ui.registrasi.RegistrasiAgenActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TambahListingActivity extends AppCompatActivity {

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
    Button batal, submit, select, maps, BtnSHM, BtnHGB, BtnHSHP, BtnPPJB, BtnSTRA;
    ImageView hps1, hps2, hps3, hps4, hps5, hps6, hps7, hps8;
    TextInputEditText namalengkap, nohp, nik, alamat, tgllhir, rekening, bank, atasnama, jenisproperti, namaproperti, alamatproperti, sertifikat, nosertif, luas, land, lantai, bed, bath, bedart, bathart, garasi, carpot, listrik, air, perabot, ketperabot, banner, status, harga, keterangan, hadap, size;
    TextInputLayout LytSize;
    RadioButton open, exclusive;
    RadioGroup rgpriority;
    CheckBox CBSHM, CBHGB, CBHSHP, CBPPJB, CBSTRA;
    String idagen, idnull, sstatus, priority, namalisting, isAdmin, idadmin, idinput;
    String image1, image2, image3, image4, image5, image6, image7, image8, SHM, HGB, HSHP, PPJB, STRA;
    String latitudeStr, longitudeStr, addressStr;
    Drawable DrawableSHM,DrawableHGB,DrawableHSHP,DrawablePPJB,DrawableSTRA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_listing);

        pDialog = new ProgressDialog(TambahListingActivity.this);

        rgpriority = findViewById(R.id.rgstatus);

        open = findViewById(R.id.rbopen);
        exclusive = findViewById(R.id.rbexclusive);

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

        namalengkap = findViewById(R.id.etnamavendor);
        nohp = findViewById(R.id.etnohpvendor);
        nik = findViewById(R.id.etnikvendor);
        alamat = findViewById(R.id.etalamatvendor);
        tgllhir = findViewById(R.id.ettgllahirvendor);
        rekening = findViewById(R.id.etrekeningvendor);
        bank = findViewById(R.id.etbankvendor);
        atasnama = findViewById(R.id.etatasnamavendor);
        jenisproperti = findViewById(R.id.etjenisproperti);
        namaproperti = findViewById(R.id.etnamaproperti);
        alamatproperti = findViewById(R.id.etalamatproperti);
        sertifikat = findViewById(R.id.ettipesertifikat);
        nosertif = findViewById(R.id.etnomorsertifikat);
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

        namalisting = namaproperti.getText().toString();
        sstatus = Preferences.getKeyIsAkses(TambahListingActivity.this);
        isAdmin = Preferences.getKeyStatus(TambahListingActivity.this);
        idadmin = Preferences.getKeyIdAdmin(TambahListingActivity.this);
        idagen = Preferences.getKeyIdAgen(TambahListingActivity.this);
        idnull = "0";
        DrawableSHM = IVShm.getDrawable();
        DrawableHGB = IVHgb.getDrawable();
        DrawableHSHP = IVHshp.getDrawable();
        DrawablePPJB = IVPpjb.getDrawable();
        DrawableSTRA = IVStratatitle.getDrawable();

        if (isAdmin.equals("2")){
            submit.setOnClickListener(view -> {
                int checkedRadioButtonId = rgpriority.getCheckedRadioButtonId();

                if (checkedRadioButtonId == -1) {
                    Dialog customDialog = new Dialog(TambahListingActivity.this);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.custom_dialog_eror_input);

                    if (customDialog.getWindow() != null) {
                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    }

                    Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                    TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                    tv.setText("Harap pilih Open atau Exclusive pada bagian atas form");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                    Glide.with(TambahListingActivity.this)
                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifImageView);

                    customDialog.show();
                } else {
                    if (longitudeStr == null && latitudeStr == null) {
                        Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                        Glide.with(TambahListingActivity.this)
                                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
                        if (Validate()) {
                            if (bitmap1 == null) {
                                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                                Glide.with(TambahListingActivity.this)
                                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifImageView);

                                customDialog.show();
                            } else {
                                simpanData();
                            }
                        }
                    }
                }
            });
        } else if (isAdmin.equals("3")) {
            if (sstatus.equals("1")){
                submit.setOnClickListener(view -> {
                    int checkedRadioButtonId = rgpriority.getCheckedRadioButtonId();

                    if (checkedRadioButtonId == -1) {
                        Dialog customDialog = new Dialog(TambahListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                        TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                        tv.setText("Harap pilih Open atau Exclusive pada bagian atas form");

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                        Glide.with(TambahListingActivity.this)
                                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    }
                    else {
                        if (longitudeStr == null && latitudeStr == null) {
                            Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                            Glide.with(TambahListingActivity.this)
                                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifImageView);

                            customDialog.show();
                        }
                        else {
                            if (Validate()) {
                                if (bitmap1 == null) {
                                    Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                                    Glide.with(TambahListingActivity.this)
                                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifImageView);

                                    customDialog.show();
                                }
                                else {
                                    simpanDataAgen();
                                }
                            }
                        }
                    }
                });
            } else if (sstatus.equals("2")) {
                submit.setOnClickListener(view -> {
                    int checkedRadioButtonId = rgpriority.getCheckedRadioButtonId();

                    if (checkedRadioButtonId == -1) {
                        Dialog customDialog = new Dialog(TambahListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                        TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                        tv.setText("Harap pilih Open atau Exclusive pada bagian atas form");

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                        Glide.with(TambahListingActivity.this)
                                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    }
                    else {
                        if (longitudeStr == null && latitudeStr == null) {
                            Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                            Glide.with(TambahListingActivity.this)
                                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifImageView);

                            customDialog.show();
                        }
                        else {
                            if (Validate()) {
                                if (bitmap1 == null) {
                                    Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                                    Glide.with(TambahListingActivity.this)
                                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifImageView);

                                    customDialog.show();
                                }
                                else {
                                    simpanDataNonAgen();
                                }
                            }
                        }
                    }
                });
            } else if (sstatus.equals("3")) {
                submit.setOnClickListener(view -> {
                    int checkedRadioButtonId = rgpriority.getCheckedRadioButtonId();

                    if (checkedRadioButtonId == -1) {
                        Dialog customDialog = new Dialog(TambahListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                        TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                        tv.setText("Harap pilih Open atau Exclusive pada bagian atas form");

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                        Glide.with(TambahListingActivity.this)
                                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    }
                    else {
                        if (longitudeStr == null && latitudeStr == null) {
                            Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                            Glide.with(TambahListingActivity.this)
                                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifImageView);

                            customDialog.show();
                        }
                        else {
                            if (Validate()) {
                                if (bitmap1 == null) {
                                    Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                                    Glide.with(TambahListingActivity.this)
                                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifImageView);

                                    customDialog.show();
                                }
                                else {
                                    simpanDataNonAgen();
                                }
                            }
                        }
                    }
                });
            }
        }

        maps.setOnClickListener(view -> startMapsActivityForResult());
        back.setOnClickListener(view -> finish());
        batal.setOnClickListener(view -> finish());
        bank.setOnClickListener(view -> ShowBank(view));
        jenisproperti.setOnClickListener(view -> ShowJenisProperti(view));
        sertifikat.setOnClickListener(view -> ShowTipeSertifikat(view));
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

        BtnSHM.setOnClickListener(view -> showPhotoSHM());
        BtnHGB.setOnClickListener(view -> showPhotoHGB());
        BtnHSHP.setOnClickListener(view -> showPhotoHSHP());
        BtnPPJB.setOnClickListener(view -> showPhotoPPJB());
        BtnSTRA.setOnClickListener(view -> showPhotoSTRA());

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog();
            }
        });

        rgpriority.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbopen) {
                priority = "open";
            } else if (checkedId == R.id.rbexclusive) {
                priority = "exclusive";
            }
        });

        tgllhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDatePicker<Long> materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("Pilih Tanggal")
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        String tanggal = new SimpleDateFormat("yyyy-dd-MM", Locale.getDefault()).format(new Date(selection));
                        tgllhir.setText(MessageFormat.format("{0}", tanggal));
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
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
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST);
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
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_SHM);
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
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_HGB);
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
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_HSHP);
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
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_PPJB);
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
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_STRA);
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
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
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
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
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
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
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
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
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
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
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
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahListingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahListingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahListingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahListingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahListingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahListingActivity.this);
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
                    //IVShm.setImageBitmap(bitmapSHM);
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
                    //IVHgb.setImageBitmap(bitmapHGB);
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
                    //IVHshp.setImageBitmap(bitmapHSHP);
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
                    //IVPpjb.setImageBitmap(bitmapPPJB);
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
                    //IVStratatitle.setImageBitmap(bitmapSTRA);
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
                        //IVShm.setImageBitmap(bitmapSHM);
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
                        //IVHgb.setImageBitmap(bitmapHGB);
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
                        //IVHshp.setImageBitmap(bitmapHSHP);
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
                        //IVPpjb.setImageBitmap(bitmapPPJB);
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
                        //IVStratatitle.setImageBitmap(bitmapSTRA);
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

    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                            Glide.with(TambahListingActivity.this)
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
                        Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                        Glide.with(TambahListingActivity.this)
                                .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (idagen.isEmpty()) {
                    idinput = idadmin;
                } else {
                    idinput = idagen;
                }
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

                map.put("NamaLengkap", namalengkap.getText().toString());
                map.put("NoTelp", nohp.getText().toString());
                map.put("Alamat", alamat.getText().toString());
                map.put("TglLahir", tgllhir.getText().toString());
                map.put("Nik", nik.getText().toString());
                map.put("NoRekening", rekening.getText().toString());
                map.put("Bank", bank.getText().toString());
                map.put("AtasNama", atasnama.getText().toString());
                map.put("IdAgen", idnull);
                map.put("IdInput", idnull);
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
                map.put("Priority", priority);
                map.put("Banner", banner.getText().toString());
                map.put("Size", size.getText().toString());
                map.put("Harga", harga.getText().toString());
                map.put("TglInput", idnull);
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

    private void simpanDataAgen() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                            Glide.with(TambahListingActivity.this)
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
                        Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                        Glide.with(TambahListingActivity.this)
                                .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (idagen.isEmpty()) {
                    idinput = "0";
                } else {
                    idinput = idagen;
                }
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

                map.put("NamaLengkap", namalengkap.getText().toString());
                map.put("NoTelp", nohp.getText().toString());
                map.put("Alamat", alamat.getText().toString());
                map.put("TglLahir", tgllhir.getText().toString());
                map.put("Nik", nik.getText().toString());
                map.put("NoRekening", rekening.getText().toString());
                map.put("Bank", bank.getText().toString());
                map.put("AtasNama", atasnama.getText().toString());
                map.put("IdAgen", idagen);
                map.put("IdInput", idagen);
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
                map.put("Priority", priority);
                map.put("Banner", banner.getText().toString());
                map.put("Size", size.getText().toString());
                map.put("Harga", harga.getText().toString());
                map.put("TglInput", idnull);
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

    private void simpanDataNonAgen() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                            Glide.with(TambahListingActivity.this)
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
                        Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                        Glide.with(TambahListingActivity.this)
                                .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (idagen.isEmpty()) {
                    idinput = "0";
                } else {
                    idinput = idagen;
                }
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

                map.put("NamaLengkap", namalengkap.getText().toString());
                map.put("NoTelp", nohp.getText().toString());
                map.put("Alamat", alamat.getText().toString());
                map.put("TglLahir", tgllhir.getText().toString());
                map.put("Nik", nik.getText().toString());
                map.put("NoRekening", rekening.getText().toString());
                map.put("Bank", bank.getText().toString());
                map.put("AtasNama", atasnama.getText().toString());
                map.put("IdAgen", idnull);
                map.put("IdInput", idagen);
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
                map.put("Priority", priority);
                map.put("Banner", banner.getText().toString());
                map.put("Size", size.getText().toString());
                map.put("Harga", harga.getText().toString());
                map.put("TglInput", idnull);
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

    public void ShowBank(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Bank");

        final CharSequence[] Bank = {"BCA", "BRI"};
        final int[] SelectedBank = {0};

        builder.setSingleChoiceItems(Bank, SelectedBank[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedBank[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bank.setText(Bank[SelectedBank[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
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

    public void ShowTipeSertifikat(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Tipe Sertifikat");

        final CharSequence[] TipeSertifikat = {"SHM", "HGB", "Custom"};
        final int SelectedTipeSertifikat = -1;

        builder.setSingleChoiceItems(TipeSertifikat, SelectedTipeSertifikat, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == TipeSertifikat.length - 1) {
                    showCustomTypeInputDialog();
                } else {
                    sertifikat.setText(TipeSertifikat[which]);
                }
                dialog.dismiss();
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
                if (Banner[SelectedBanner[0]].equals("Ya")){
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
        editTextCustomType.setPadding(50,20,50,0);

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
        if (namalengkap.getText().toString().equals("")) {
            namalengkap.setError("Harap Isi Nama Lengkap Vendor");
            namalengkap.requestFocus();
            return false;
        }
        if (nohp.getText().toString().equals("")) {
            nohp.setError("Harap Isi No WhatsApp Vendor");
            nohp.requestFocus();
            return false;
        }
        if (nik.getText().toString().equals("")) {
            nik.setError("Harap Isi NIK");
            nik.requestFocus();
            return false;
        }
        if (alamat.getText().toString().equals("")) {
            alamat.setError("Harap Isi Alamat Vendor");
            alamat.requestFocus();
            return false;
        }
        if (tgllhir.getText().toString().equals("")) {
            tgllhir.setError("Harap Isi Tanggal Lahir Vendor");
            tgllhir.requestFocus();
            return false;
        }
        if (rekening.getText().toString().equals("")) {
            rekening.setError("Harap Isi Rekening Vendor");
            rekening.requestFocus();
            return false;
        }
        if (bank.getText().toString().equals("")) {
            bank.setError("Harap Isi Bank Vendor");
            bank.requestFocus();
            return false;
        }
        if (atasnama.getText().toString().equals("")) {
            atasnama.setError("Harap Isi Atas Nama Rekening");
            atasnama.requestFocus();
            return false;
        }
        if (jenisproperti.getText().toString().equals("")) {
            jenisproperti.setError("Harap Isi Nama Lengkap Vendor");
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
            if (size.getText().toString().equals("")){
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
        if (CBSHM.isChecked()){
            if (bitmapSHM == null){
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        if (CBHGB.isChecked()){
            if (bitmapHGB == null){
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        if (CBHSHP.isChecked()){
            if (bitmapHSHP == null){
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        if (CBPPJB.isChecked()){
            if (bitmapPPJB == null){
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        if (CBSTRA.isChecked()){
            if (bitmapSTRA == null){
                Dialog customDialog = new Dialog(TambahListingActivity.this);
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

                Glide.with(TambahListingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
                return false;
            }
        }
        return true;
    }
}