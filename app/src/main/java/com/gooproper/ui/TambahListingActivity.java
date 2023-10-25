package com.gooproper.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TambahListingActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST1 = 1;
    final int CODE_GALLERY_REQUEST2 = 2;
    final int CODE_GALLERY_REQUEST3 = 3;
    final int CODE_GALLERY_REQUEST4 = 4;
    final int CODE_GALLERY_REQUEST5 = 5;
    final int CODE_GALLERY_REQUEST6 = 6;
    final int CODE_GALLERY_REQUEST7 = 7;
    final int CODE_GALLERY_REQUEST8 = 8;
    final int CODE_CAMERA_REQUEST1 = 9;
    final int KODE_REQUEST_KAMERA1 = 10;
    final int CODE_CAMERA_REQUEST2 = 11;
    final int KODE_REQUEST_KAMERA2 = 12;
    final int CODE_CAMERA_REQUEST3 = 13;
    final int KODE_REQUEST_KAMERA3 = 14;
    final int CODE_CAMERA_REQUEST4 = 15;
    final int KODE_REQUEST_KAMERA4 = 16;
    final int CODE_CAMERA_REQUEST5 = 17;
    final int KODE_REQUEST_KAMERA5 = 18;
    final int CODE_CAMERA_REQUEST6 = 19;
    final int KODE_REQUEST_KAMERA6 = 20;
    final int CODE_CAMERA_REQUEST7 = 21;
    final int KODE_REQUEST_KAMERA7 = 22;
    final int CODE_CAMERA_REQUEST8 = 23;
    final int KODE_REQUEST_KAMERA8 = 24;
    final int CODE_GALLERY_REQUEST_SHM = 25;
    final int CODE_CAMERA_REQUEST_SHM = 26;
    final int KODE_REQUEST_KAMERA_SHM = 27;
    final int CODE_GALLERY_REQUEST_HGB = 28;
    final int CODE_CAMERA_REQUEST_HGB = 29;
    final int KODE_REQUEST_KAMERA_HGB = 30;
    final int CODE_GALLERY_REQUEST_HSHP = 31;
    final int CODE_CAMERA_REQUEST_HSHP = 32;
    final int KODE_REQUEST_KAMERA_HSHP = 33;
    final int CODE_GALLERY_REQUEST_PPJB = 34;
    final int CODE_CAMERA_REQUEST_PPJB = 35;
    final int KODE_REQUEST_KAMERA_PPJB = 36;
    final int CODE_GALLERY_REQUEST_STRA = 37;
    final int CODE_CAMERA_REQUEST_STRA = 38;
    final int KODE_REQUEST_KAMERA_STRA = 39;
    final int CODE_GALLERY_REQUEST_PJP = 40;
    final int CODE_CAMERA_REQUEST_PJP = 41;
    final int KODE_REQUEST_KAMERA_PJP = 42;
    final int CODE_GALLERY_REQUEST_PJP1 = 43;
    final int CODE_CAMERA_REQUEST_PJP1 = 44;
    final int KODE_REQUEST_KAMERA_PJP1 = 45;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1 = 46;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES1 = 47;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2 = 48;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES2 = 49;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3 = 50;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES3 = 51;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4 = 52;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES4 = 53;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5 = 54;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES5 = 55;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6 = 56;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES6 = 57;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7 = 58;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES7 = 59;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8 = 60;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES8 = 61;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SHM = 62;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SHM = 63;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HGB = 64;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HGB = 65;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_HSHP = 66;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_HSHP = 67;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PPJB = 68;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PPJB = 69;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_STRA = 70;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_STRA = 71;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP = 72;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP = 73;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP1 = 74;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP1 = 75;
    private static final int MAPS_ACTIVITY_REQUEST_CODE = 3;
    Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmapSHM, bitmapHGB, bitmapHSHP, bitmapPPJB, bitmapSTRA, BitmapWatermark, BitmapPjp, BitmapPjp1;
    Uri Uri1, Uri2, Uri3, Uri4, Uri5, Uri6, Uri7, Uri8, UriSHM, UriHGB, UriHSHP, UriPPJB, UriSTRA, UriPJP, UriPJP1;
    Drawable WatermarkDrawable;
    LinearLayout lyt1, lyt2, lyt3, lyt4, lyt5, lyt6, lyt7, lyt8, LytSHM, LytHGB, LytHSHP, LytPPJB, LytStratatitle, LytPjp, LytPjp1;
    ImageView back, iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, IVShm, IVHgb, IVHshp, IVPpjb, IVStratatitle, IVPjp, IVPjp1;
    Button batal, submit, select, select1, select2, select3, select4, select5, select6, select7, maps, BtnSHM, BtnHGB, BtnHSHP, BtnPPJB, BtnSTRA, BtnPjp, BtnPjp1;
    ImageView hps1, hps2, hps3, hps4, hps5, hps6, hps7, hps8, HpsSHM, HpsHGB, HpsHSHP, HpsPPJB, HpsStratatitle, HpsPjp, HpsPjp1;
    TextInputEditText namalengkap, nohp, nik, alamat, tgllhir, rekening, bank, atasnama, jenisproperti, namaproperti, alamatproperti, sertifikat, nosertif, luas, land, dimensi, lantai, bed, bath, bedart, bathart, garasi, carpot, listrik, air, pjp, perabot, ketperabot, banner, status, harga, hargasewa, keterangan, hadap, size, EtTglInput, EtFee;
    TextInputLayout LytSize, LytTglInput, LytHargaJual, LytHargaSewa;
    RadioButton open, exclusive;
    RadioGroup rgpriority;
    CheckBox CBSHM, CBHGB, CBHSHP, CBPPJB, CBSTRA;
    String idagen, idnull, sstatus, priority, namalisting, isAdmin, idadmin, idinput, HargaString, HargaSewaString, SHarga, SHargaSewa;
    String image1, image2, image3, image4, image5, image6, image7, image8, SHM, HGB, HSHP, PPJB, STRA, PJPHal1, PJPHal2;
    String latitudeStr, longitudeStr, addressStr, Lat, Lng;
    Drawable DrawableSHM, DrawableHGB, DrawableHSHP, DrawablePPJB, DrawableSTRA;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_listing);

        pDialog = new ProgressDialog(TambahListingActivity.this);
        WatermarkDrawable = getResources().getDrawable(R.drawable.watermark);
        BitmapWatermark = ((BitmapDrawable) WatermarkDrawable).getBitmap();

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
        IVShm = findViewById(R.id.IVSHM);
        IVHgb = findViewById(R.id.IVHGB);
        IVHshp = findViewById(R.id.IVHSHP);
        IVPpjb = findViewById(R.id.IVPPJB);
        IVStratatitle = findViewById(R.id.IVStratatitle);
        IVPjp = findViewById(R.id.IVPjp);
        IVPjp1 = findViewById(R.id.IVPjp1);

        lyt1 = findViewById(R.id.lyts1);
        lyt2 = findViewById(R.id.lyts2);
        lyt3 = findViewById(R.id.lyts3);
        lyt4 = findViewById(R.id.lyts4);
        lyt5 = findViewById(R.id.lyts5);
        lyt6 = findViewById(R.id.lyts6);
        lyt7 = findViewById(R.id.lyts7);
        lyt8 = findViewById(R.id.lyts8);
        LytSize = findViewById(R.id.lytUkuranBanner);
        LytTglInput = findViewById(R.id.lyttglinputproperti);
        LytHargaJual = findViewById(R.id.lytharga);
        LytHargaSewa = findViewById(R.id.lythargasewa);
        LytSHM = findViewById(R.id.LytSHM);
        LytHGB = findViewById(R.id.LytHGB);
        LytHSHP = findViewById(R.id.LytHSHP);
        LytPPJB = findViewById(R.id.LytPPJB);
        LytStratatitle = findViewById(R.id.LytStratatitle);
        LytPjp = findViewById(R.id.LytPjp);
        LytPjp1 = findViewById(R.id.LytPjp1);

        back = findViewById(R.id.backFormBtn);

        batal = findViewById(R.id.btnbatal);
        submit = findViewById(R.id.btnsubmit);
        select = findViewById(R.id.btnSelectImage);
        select1 = findViewById(R.id.btnSelectImage1);
        select2 = findViewById(R.id.btnSelectImage2);
        select3 = findViewById(R.id.btnSelectImage3);
        select4 = findViewById(R.id.btnSelectImage4);
        select5 = findViewById(R.id.btnSelectImage5);
        select6 = findViewById(R.id.btnSelectImage6);
        select7 = findViewById(R.id.btnSelectImage7);

        hps1 = findViewById(R.id.IVDelete1);
        hps2 = findViewById(R.id.IVDelete2);
        hps3 = findViewById(R.id.IVDelete3);
        hps4 = findViewById(R.id.IVDelete4);
        hps5 = findViewById(R.id.IVDelete5);
        hps6 = findViewById(R.id.IVDelete6);
        hps7 = findViewById(R.id.IVDelete7);
        hps8 = findViewById(R.id.IVDelete8);
        HpsSHM = findViewById(R.id.IVDeleteSHM);
        HpsHGB = findViewById(R.id.IVDeleteHGB);
        HpsHSHP = findViewById(R.id.IVDeleteHSHP);
        HpsPPJB = findViewById(R.id.IVDeletePPJB);
        HpsStratatitle = findViewById(R.id.IVDeleteStratatitle);
        HpsPjp = findViewById(R.id.IVDeletePjp);
        HpsPjp1 = findViewById(R.id.IVDeletePjp1);

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
        pjp = findViewById(R.id.etkonfirmasipjp);
        nosertif = findViewById(R.id.etnomorsertifikat);
        luas = findViewById(R.id.etluastanah);
        land = findViewById(R.id.etluasbangunan);
        dimensi = findViewById(R.id.etdimensi);
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
        hargasewa = findViewById(R.id.ethargasewa);
        keterangan = findViewById(R.id.etketerangan);
        hadap = findViewById(R.id.ethadap);
        size = findViewById(R.id.etukuranbanner);
        EtTglInput = findViewById(R.id.ettglinputproperti);
        EtFee = findViewById(R.id.etfee);

        maps = findViewById(R.id.map);

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
        BtnPjp = findViewById(R.id.BtnPjp);
        BtnPjp1 = findViewById(R.id.BtnPjp1);

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

        if (isAdmin.equals("2")) {
            LytTglInput.setVisibility(View.VISIBLE);
        } else {
            LytTglInput.setVisibility(View.GONE);
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileListing1 = "Listing1_" + timeStamp + ".jpg";
        String fileListing2 = "Listing2_" + timeStamp + ".jpg";
        String fileListing3 = "Listing3_" + timeStamp + ".jpg";
        String fileListing4 = "Listing4_" + timeStamp + ".jpg";
        String fileListing5 = "Listing5_" + timeStamp + ".jpg";
        String fileListing6 = "Listing6_" + timeStamp + ".jpg";
        String fileListing7 = "Listing7_" + timeStamp + ".jpg";
        String fileListing8 = "Listing8_" + timeStamp + ".jpg";
        String fileSertifikatshm = "SHM_" + timeStamp + ".jpg";
        String fileSertifikathgb = "HGB_" + timeStamp + ".jpg";
        String fileSertifikathshp = "HSHP_" + timeStamp + ".jpg";
        String fileSertifikatppjb = "PPJB_" + timeStamp + ".jpg";
        String fileSertifikatstra = "Stratatitle_" + timeStamp + ".jpg";
        String filePjp1 = "PJP1_" + timeStamp + ".jpg";
        String filePjp2 = "PJP2_" + timeStamp + ".jpg";

        if (isAdmin.equals("2")) {
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
                    if (Validate()) {
                        if (Uri1 == null) {
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
                            pDialog.setMessage("Menyimpan Data");
                            pDialog.setCancelable(false);
                            pDialog.show();

                            StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                            StorageReference ImgListing1 = storageRef.child("listing/" + fileListing1);
                            StorageReference ImgListing2 = storageRef.child("listing/" + fileListing2);
                            StorageReference ImgListing3 = storageRef.child("listing/" + fileListing3);
                            StorageReference ImgListing4 = storageRef.child("listing/" + fileListing4);
                            StorageReference ImgListing5 = storageRef.child("listing/" + fileListing5);
                            StorageReference ImgListing6 = storageRef.child("listing/" + fileListing6);
                            StorageReference ImgListing7 = storageRef.child("listing/" + fileListing7);
                            StorageReference ImgListing8 = storageRef.child("listing/" + fileListing8);
                            StorageReference ImgSertifikatshm = storageRef.child("sertifikat/" + fileSertifikatshm);
                            StorageReference ImgSertifikathgb = storageRef.child("sertifikat/" + fileSertifikathgb);
                            StorageReference ImgSertifikathshp = storageRef.child("sertifikat/" + fileSertifikathshp);
                            StorageReference ImgSertifikatppjb = storageRef.child("sertifikat/" + fileSertifikatppjb);
                            StorageReference ImgSertifikatstra = storageRef.child("sertifikat/" + fileSertifikatstra);
                            StorageReference ImgPjp = storageRef.child("pjp/" + filePjp1);
                            StorageReference ImgPjp1 = storageRef.child("pjp/" + filePjp2);

                            List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();

                            if (Uri1 != null) {
                                StorageTask<UploadTask.TaskSnapshot> task1 = ImgListing1.putFile(Uri1)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgListing1.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        image1 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(task1);
                            } else {
                                image1 = "0";
                            }
                            if (Uri2 != null) {
                                StorageTask<UploadTask.TaskSnapshot> task2 = ImgListing2.putFile(Uri2)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgListing2.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        image2 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(task2);
                            } else {
                                image2 = "0";
                            }
                            if (Uri3 != null) {
                                StorageTask<UploadTask.TaskSnapshot> task3 = ImgListing3.putFile(Uri3)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgListing3.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        image3 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(task3);
                            } else {
                                image3 = "0";
                            }
                            if (Uri4 != null) {
                                StorageTask<UploadTask.TaskSnapshot> task4 = ImgListing4.putFile(Uri4)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgListing4.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        image4 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(task4);
                            } else {
                                image4 = "0";
                            }
                            if (Uri5 != null) {
                                StorageTask<UploadTask.TaskSnapshot> task5 = ImgListing5.putFile(Uri5)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgListing5.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        image5 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(task5);
                            } else {
                                image5 = "0";
                            }
                            if (Uri6 != null) {
                                StorageTask<UploadTask.TaskSnapshot> task6 = ImgListing6.putFile(Uri6)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgListing6.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        image6 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(task6);
                            } else {
                                image6 = "0";
                            }
                            if (Uri7 != null) {
                                StorageTask<UploadTask.TaskSnapshot> task7 = ImgListing7.putFile(Uri7)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgListing7.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        image7 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(task7);
                            } else {
                                image7 = "0";
                            }
                            if (Uri8 != null) {
                                StorageTask<UploadTask.TaskSnapshot> task8 = ImgListing8.putFile(Uri8)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgListing8.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        image8 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(task8);
                            } else {
                                image8 = "0";
                            }
                            if (UriSHM != null) {
                                StorageTask<UploadTask.TaskSnapshot> taskSHM = ImgSertifikatshm.putFile(UriSHM)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgSertifikatshm.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        SHM = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(taskSHM);
                            } else {
                                SHM = "0";
                            }
                            if (UriHGB != null) {
                                StorageTask<UploadTask.TaskSnapshot> taskHGB = ImgSertifikathgb.putFile(UriHGB)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgSertifikathgb.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        HGB = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(taskHGB);
                            } else {
                                HGB = "0";
                            }
                            if (UriHSHP != null) {
                                StorageTask<UploadTask.TaskSnapshot> taskHSHP = ImgSertifikathshp.putFile(UriHSHP)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgSertifikathshp.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        HSHP = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(taskHSHP);
                            } else {
                                HSHP = "0";
                            }
                            if (UriPPJB != null) {
                                StorageTask<UploadTask.TaskSnapshot> taskPPJB = ImgSertifikatppjb.putFile(UriPPJB)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgSertifikatppjb.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        PPJB = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(taskPPJB);
                            } else {
                                PPJB = "0";
                            }
                            if (UriSTRA != null) {
                                StorageTask<UploadTask.TaskSnapshot> taskSTRA = ImgSertifikatstra.putFile(UriSTRA)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgSertifikatstra.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        STRA = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(taskSTRA);
                            } else {
                                STRA = "0";
                            }
                            if (UriPJP != null) {
                                StorageTask<UploadTask.TaskSnapshot> taskPJP = ImgPjp.putFile(UriPJP)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgPjp.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        PJPHal1 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(taskPJP);
                            } else {
                                PJPHal1 = "0";
                            }
                            if (UriPJP1 != null) {
                                StorageTask<UploadTask.TaskSnapshot> taskPJP1 = ImgPjp1.putFile(UriPJP1)
                                        .addOnSuccessListener(taskSnapshot -> {
                                            ImgPjp1.getDownloadUrl()
                                                    .addOnSuccessListener(uri -> {
                                                        String imageUrl = uri.toString();
                                                        PJPHal2 = imageUrl;
                                                    })
                                                    .addOnFailureListener(exception -> {
                                                    });
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                                uploadTasks.add(taskPJP1);
                            } else {
                                PJPHal2 = "0";
                            }

                            Tasks.whenAllSuccess(uploadTasks)
                                    .addOnSuccessListener(results -> {
                                        pDialog.cancel();
                                        simpanData();
                                    })
                                    .addOnFailureListener(exception -> {
                                        Dialog customDialog = new Dialog(TambahListingActivity.this);
                                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                                        if (customDialog.getWindow() != null) {
                                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                        }

                                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                                        TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                                        tv.setText("Gagal Saat Unggah Gambar");

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
                                    });
                        }
                    }
                }
            });
        } else if (isAdmin.equals("3")) {
            if (sstatus.equals("1")) {
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
                        if (Validate()) {
                            if (Uri1 == null) {
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
                                pDialog.setMessage("Menyimpan Data");
                                pDialog.setCancelable(false);
                                pDialog.show();

                                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                StorageReference ImgListing1 = storageRef.child("listing/" + fileListing1);
                                StorageReference ImgListing2 = storageRef.child("listing/" + fileListing2);
                                StorageReference ImgListing3 = storageRef.child("listing/" + fileListing3);
                                StorageReference ImgListing4 = storageRef.child("listing/" + fileListing4);
                                StorageReference ImgListing5 = storageRef.child("listing/" + fileListing5);
                                StorageReference ImgListing6 = storageRef.child("listing/" + fileListing6);
                                StorageReference ImgListing7 = storageRef.child("listing/" + fileListing7);
                                StorageReference ImgListing8 = storageRef.child("listing/" + fileListing8);
                                StorageReference ImgSertifikatshm = storageRef.child("sertifikat/" + fileSertifikatshm);
                                StorageReference ImgSertifikathgb = storageRef.child("sertifikat/" + fileSertifikathgb);
                                StorageReference ImgSertifikathshp = storageRef.child("sertifikat/" + fileSertifikathshp);
                                StorageReference ImgSertifikatppjb = storageRef.child("sertifikat/" + fileSertifikatppjb);
                                StorageReference ImgSertifikatstra = storageRef.child("sertifikat/" + fileSertifikatstra);
                                StorageReference ImgPjp = storageRef.child("pjp/" + filePjp1);
                                StorageReference ImgPjp1 = storageRef.child("pjp/" + filePjp2);

                                List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();

                                if (Uri1 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task1 = ImgListing1.putFile(Uri1)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing1.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image1 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task1);
                                } else {
                                    image1 = "0";
                                }
                                if (Uri2 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task2 = ImgListing2.putFile(Uri2)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing2.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image2 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task2);
                                } else {
                                    image2 = "0";
                                }
                                if (Uri3 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task3 = ImgListing3.putFile(Uri3)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing3.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image3 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task3);
                                } else {
                                    image3 = "0";
                                }
                                if (Uri4 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task4 = ImgListing4.putFile(Uri4)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing4.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image4 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task4);
                                } else {
                                    image4 = "0";
                                }
                                if (Uri5 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task5 = ImgListing5.putFile(Uri5)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing5.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image5 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task5);
                                } else {
                                    image5 = "0";
                                }
                                if (Uri6 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task6 = ImgListing6.putFile(Uri6)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing6.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image6 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task6);
                                } else {
                                    image6 = "0";
                                }
                                if (Uri7 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task7 = ImgListing7.putFile(Uri7)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing7.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image7 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task7);
                                } else {
                                    image7 = "0";
                                }
                                if (Uri8 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task8 = ImgListing8.putFile(Uri8)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing8.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image8 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task8);
                                } else {
                                    image8 = "0";
                                }
                                if (UriSHM != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskSHM = ImgSertifikatshm.putFile(UriSHM)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatshm.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            SHM = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskSHM);
                                } else {
                                    SHM = "0";
                                }
                                if (UriHGB != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskHGB = ImgSertifikathgb.putFile(UriHGB)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikathgb.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            HGB = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskHGB);
                                } else {
                                    HGB = "0";
                                }
                                if (UriHSHP != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskHSHP = ImgSertifikathshp.putFile(UriHSHP)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikathshp.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            HSHP = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskHSHP);
                                } else {
                                    HSHP = "0";
                                }
                                if (UriPPJB != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPPJB = ImgSertifikatppjb.putFile(UriPPJB)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatppjb.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PPJB = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPPJB);
                                } else {
                                    PPJB = "0";
                                }
                                if (UriSTRA != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskSTRA = ImgSertifikatstra.putFile(UriSTRA)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatstra.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            STRA = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskSTRA);
                                } else {
                                    STRA = "0";
                                }
                                if (UriPJP != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPJP = ImgPjp.putFile(UriPJP)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgPjp.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PJPHal1 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPJP);
                                } else {
                                    PJPHal1 = "0";
                                }
                                if (UriPJP1 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPJP1 = ImgPjp1.putFile(UriPJP1)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgPjp1.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PJPHal2 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPJP1);
                                } else {
                                    PJPHal2 = "0";
                                }

                                Tasks.whenAllSuccess(uploadTasks)
                                        .addOnSuccessListener(results -> {
                                            pDialog.cancel();
                                            simpanDataAgen();
                                        })
                                        .addOnFailureListener(exception -> {
                                            Dialog customDialog = new Dialog(TambahListingActivity.this);
                                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            customDialog.setContentView(R.layout.custom_dialog_eror_input);

                                            if (customDialog.getWindow() != null) {
                                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            }

                                            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                                            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                                            tv.setText("Gagal Saat Unggah Gambar");

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
                                        });
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
                    } else {
                        if (Validate()) {
                            if (Uri1 == null) {
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
                                pDialog.setMessage("Menyimpan Data");
                                pDialog.setCancelable(false);
                                pDialog.show();

                                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                StorageReference ImgListing1 = storageRef.child("listing/" + fileListing1);
                                StorageReference ImgListing2 = storageRef.child("listing/" + fileListing2);
                                StorageReference ImgListing3 = storageRef.child("listing/" + fileListing3);
                                StorageReference ImgListing4 = storageRef.child("listing/" + fileListing4);
                                StorageReference ImgListing5 = storageRef.child("listing/" + fileListing5);
                                StorageReference ImgListing6 = storageRef.child("listing/" + fileListing6);
                                StorageReference ImgListing7 = storageRef.child("listing/" + fileListing7);
                                StorageReference ImgListing8 = storageRef.child("listing/" + fileListing8);
                                StorageReference ImgSertifikatshm = storageRef.child("sertifikat/" + fileSertifikatshm);
                                StorageReference ImgSertifikathgb = storageRef.child("sertifikat/" + fileSertifikathgb);
                                StorageReference ImgSertifikathshp = storageRef.child("sertifikat/" + fileSertifikathshp);
                                StorageReference ImgSertifikatppjb = storageRef.child("sertifikat/" + fileSertifikatppjb);
                                StorageReference ImgSertifikatstra = storageRef.child("sertifikat/" + fileSertifikatstra);
                                StorageReference ImgPjp = storageRef.child("pjp/" + filePjp1);
                                StorageReference ImgPjp1 = storageRef.child("pjp/" + filePjp2);

                                List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();

                                if (Uri1 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task1 = ImgListing1.putFile(Uri1)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing1.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image1 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task1);
                                } else {
                                    image1 = "0";
                                }
                                if (Uri2 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task2 = ImgListing2.putFile(Uri2)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing2.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image2 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task2);
                                } else {
                                    image2 = "0";
                                }
                                if (Uri3 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task3 = ImgListing3.putFile(Uri3)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing3.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image3 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task3);
                                } else {
                                    image3 = "0";
                                }
                                if (Uri4 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task4 = ImgListing4.putFile(Uri4)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing4.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image4 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task4);
                                } else {
                                    image4 = "0";
                                }
                                if (Uri5 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task5 = ImgListing5.putFile(Uri5)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing5.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image5 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task5);
                                } else {
                                    image5 = "0";
                                }
                                if (Uri6 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task6 = ImgListing6.putFile(Uri6)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing6.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image6 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task6);
                                } else {
                                    image6 = "0";
                                }
                                if (Uri7 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task7 = ImgListing7.putFile(Uri7)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing7.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image7 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task7);
                                } else {
                                    image7 = "0";
                                }
                                if (Uri8 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task8 = ImgListing8.putFile(Uri8)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing8.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image8 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task8);
                                } else {
                                    image8 = "0";
                                }
                                if (UriSHM != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskSHM = ImgSertifikatshm.putFile(UriSHM)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatshm.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            SHM = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskSHM);
                                } else {
                                    SHM = "0";
                                }
                                if (UriHGB != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskHGB = ImgSertifikathgb.putFile(UriHGB)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikathgb.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            HGB = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskHGB);
                                } else {
                                    HGB = "0";
                                }
                                if (UriHSHP != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskHSHP = ImgSertifikathshp.putFile(UriHSHP)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikathshp.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            HSHP = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskHSHP);
                                } else {
                                    HSHP = "0";
                                }
                                if (UriPPJB != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPPJB = ImgSertifikatppjb.putFile(UriPPJB)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatppjb.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PPJB = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPPJB);
                                } else {
                                    PPJB = "0";
                                }
                                if (UriSTRA != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskSTRA = ImgSertifikatstra.putFile(UriSTRA)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatstra.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            STRA = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskSTRA);
                                } else {
                                    STRA = "0";
                                }
                                if (UriPJP != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPJP = ImgPjp.putFile(UriPJP)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgPjp.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PJPHal1 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPJP);
                                } else {
                                    PJPHal1 = "0";
                                }
                                if (UriPJP1 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPJP1 = ImgPjp1.putFile(UriPJP1)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgPjp1.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PJPHal2 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPJP1);
                                } else {
                                    PJPHal2 = "0";
                                }

                                Tasks.whenAllSuccess(uploadTasks)
                                        .addOnSuccessListener(results -> {
                                            pDialog.cancel();
                                            simpanDataNonAgen();
                                        })
                                        .addOnFailureListener(exception -> {
                                            Dialog customDialog = new Dialog(TambahListingActivity.this);
                                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            customDialog.setContentView(R.layout.custom_dialog_eror_input);

                                            if (customDialog.getWindow() != null) {
                                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            }

                                            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                                            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                                            tv.setText("Gagal Saat Unggah Gambar");

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
                                        });
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
                    } else {
                        if (Validate()) {
                            if (Uri1 == null) {
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
                                pDialog.setMessage("Menyimpan Data");
                                pDialog.setCancelable(false);
                                pDialog.show();

                                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                                StorageReference ImgListing1 = storageRef.child("listing/" + fileListing1);
                                StorageReference ImgListing2 = storageRef.child("listing/" + fileListing2);
                                StorageReference ImgListing3 = storageRef.child("listing/" + fileListing3);
                                StorageReference ImgListing4 = storageRef.child("listing/" + fileListing4);
                                StorageReference ImgListing5 = storageRef.child("listing/" + fileListing5);
                                StorageReference ImgListing6 = storageRef.child("listing/" + fileListing6);
                                StorageReference ImgListing7 = storageRef.child("listing/" + fileListing7);
                                StorageReference ImgListing8 = storageRef.child("listing/" + fileListing8);
                                StorageReference ImgSertifikatshm = storageRef.child("sertifikat/" + fileSertifikatshm);
                                StorageReference ImgSertifikathgb = storageRef.child("sertifikat/" + fileSertifikathgb);
                                StorageReference ImgSertifikathshp = storageRef.child("sertifikat/" + fileSertifikathshp);
                                StorageReference ImgSertifikatppjb = storageRef.child("sertifikat/" + fileSertifikatppjb);
                                StorageReference ImgSertifikatstra = storageRef.child("sertifikat/" + fileSertifikatstra);
                                StorageReference ImgPjp = storageRef.child("pjp/" + filePjp1);
                                StorageReference ImgPjp1 = storageRef.child("pjp/" + filePjp2);

                                List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();

                                if (Uri1 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task1 = ImgListing1.putFile(Uri1)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing1.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image1 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task1);
                                } else {
                                    image1 = "0";
                                }
                                if (Uri2 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task2 = ImgListing2.putFile(Uri2)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing2.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image2 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task2);
                                } else {
                                    image2 = "0";
                                }
                                if (Uri3 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task3 = ImgListing3.putFile(Uri3)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing3.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image3 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task3);
                                } else {
                                    image3 = "0";
                                }
                                if (Uri4 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task4 = ImgListing4.putFile(Uri4)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing4.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image4 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task4);
                                } else {
                                    image4 = "0";
                                }
                                if (Uri5 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task5 = ImgListing5.putFile(Uri5)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing5.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image5 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task5);
                                } else {
                                    image5 = "0";
                                }
                                if (Uri6 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task6 = ImgListing6.putFile(Uri6)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing6.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image6 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task6);
                                } else {
                                    image6 = "0";
                                }
                                if (Uri7 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task7 = ImgListing7.putFile(Uri7)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing7.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image7 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task7);
                                } else {
                                    image7 = "0";
                                }
                                if (Uri8 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> task8 = ImgListing8.putFile(Uri8)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgListing8.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            image8 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(task8);
                                } else {
                                    image8 = "0";
                                }
                                if (UriSHM != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskSHM = ImgSertifikatshm.putFile(UriSHM)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatshm.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            SHM = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskSHM);
                                } else {
                                    SHM = "0";
                                }
                                if (UriHGB != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskHGB = ImgSertifikathgb.putFile(UriHGB)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikathgb.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            HGB = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskHGB);
                                } else {
                                    HGB = "0";
                                }
                                if (UriHSHP != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskHSHP = ImgSertifikathshp.putFile(UriHSHP)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikathshp.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            HSHP = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskHSHP);
                                } else {
                                    HSHP = "0";
                                }
                                if (UriPPJB != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPPJB = ImgSertifikatppjb.putFile(UriPPJB)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatppjb.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PPJB = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPPJB);
                                } else {
                                    PPJB = "0";
                                }
                                if (UriSTRA != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskSTRA = ImgSertifikatstra.putFile(UriSTRA)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgSertifikatstra.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            STRA = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskSTRA);
                                } else {
                                    STRA = "0";
                                }
                                if (UriPJP != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPJP = ImgPjp.putFile(UriPJP)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgPjp.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PJPHal1 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPJP);
                                } else {
                                    PJPHal1 = "0";
                                }
                                if (UriPJP1 != null) {
                                    StorageTask<UploadTask.TaskSnapshot> taskPJP1 = ImgPjp1.putFile(UriPJP1)
                                            .addOnSuccessListener(taskSnapshot -> {
                                                ImgPjp1.getDownloadUrl()
                                                        .addOnSuccessListener(uri -> {
                                                            String imageUrl = uri.toString();
                                                            PJPHal2 = imageUrl;
                                                        })
                                                        .addOnFailureListener(exception -> {
                                                        });
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                    uploadTasks.add(taskPJP1);
                                } else {
                                    PJPHal2 = "0";
                                }

                                Tasks.whenAllSuccess(uploadTasks)
                                        .addOnSuccessListener(results -> {
                                            pDialog.cancel();
                                            simpanDataNonAgen();
                                        })
                                        .addOnFailureListener(exception -> {
                                            Dialog customDialog = new Dialog(TambahListingActivity.this);
                                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                            customDialog.setContentView(R.layout.custom_dialog_eror_input);

                                            if (customDialog.getWindow() != null) {
                                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                            }

                                            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                                            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                                            tv.setText("Gagal Saat Unggah Gambar");

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
                                        });
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
        pjp.setOnClickListener(view -> ShowPjp(view));
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
        HpsSHM.setOnClickListener(view -> clearBitmapSHM());
        HpsHGB.setOnClickListener(view -> clearBitmapHGB());
        HpsHSHP.setOnClickListener(view -> clearBitmapHSHP());
        HpsPPJB.setOnClickListener(view -> clearBitmapPPJB());
        HpsStratatitle.setOnClickListener(view -> clearBitmapSTRA());
        HpsPjp.setOnClickListener(view -> clearBitmapPJP());
        HpsPjp1.setOnClickListener(view -> clearBitmapPJP1());
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
        status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Jual")) {
                    LytHargaJual.setVisibility(View.VISIBLE);
                    LytHargaSewa.setVisibility(View.GONE);
                } else if (editable.toString().equalsIgnoreCase("Sewa")) {
                    LytHargaSewa.setVisibility(View.VISIBLE);
                    LytHargaJual.setVisibility(View.GONE);
                } else if (editable.toString().equalsIgnoreCase("Jual/Sewa")) {
                    LytHargaJual.setVisibility(View.VISIBLE);
                    LytHargaSewa.setVisibility(View.VISIBLE);
                } else {
                    LytHargaJual.setVisibility(View.GONE);
                    LytHargaSewa.setVisibility(View.GONE);
                }
            }
        });
        pjp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Ya")) {
                    if (BitmapPjp == null) {
                        BtnPjp.setVisibility(View.VISIBLE);
                    } else {
                        BtnPjp.setVisibility(View.GONE);
                    }
                    if (BitmapPjp1 == null) {
                        BtnPjp1.setVisibility(View.VISIBLE);
                    } else {
                        BtnPjp1.setVisibility(View.GONE);
                    }
                } else if (editable.toString().equalsIgnoreCase("Tidak")) {
                    BtnPjp.setVisibility(View.GONE);
                    BtnPjp1.setVisibility(View.GONE);
                } else {
                    BtnPjp.setVisibility(View.GONE);
                    BtnPjp1.setVisibility(View.GONE);
                }
            }
        });
        CBSHM.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVShm.setVisibility(View.VISIBLE);
                    HpsSHM.setVisibility(View.VISIBLE);
                    if (UriSHM == null) {
                        BtnSHM.setVisibility(View.VISIBLE);
                    } else {
                        BtnSHM.setVisibility(View.GONE);
                    }
                } else {
                    UriSHM = null;
                    LytSHM.setVisibility(View.GONE);
                    BtnSHM.setVisibility(View.VISIBLE);
                }
            }
        });
        CBHGB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVHgb.setVisibility(View.VISIBLE);
                    HpsHGB.setVisibility(View.VISIBLE);
                    if (UriHGB == null) {
                        BtnHGB.setVisibility(View.VISIBLE);
                    } else {
                        BtnHGB.setVisibility(View.GONE);
                    }
                } else {
                    UriHGB = null;
                    LytHGB.setVisibility(View.GONE);
                    BtnHGB.setVisibility(View.VISIBLE);
                }
            }
        });
        CBHSHP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVHshp.setVisibility(View.VISIBLE);
                    HpsHSHP.setVisibility(View.VISIBLE);
                    if (UriHSHP == null) {
                        BtnHSHP.setVisibility(View.VISIBLE);
                    } else {
                        BtnHSHP.setVisibility(View.GONE);
                    }
                } else {
                    UriHSHP = null;
                    LytHSHP.setVisibility(View.GONE);
                    BtnHSHP.setVisibility(View.VISIBLE);
                }
            }
        });
        CBPPJB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVPpjb.setVisibility(View.VISIBLE);
                    HpsPPJB.setVisibility(View.VISIBLE);
                    if (UriPPJB == null) {
                        BtnPPJB.setVisibility(View.VISIBLE);
                    } else {
                        BtnPPJB.setVisibility(View.GONE);
                    }
                } else {
                    UriPPJB = null;
                    LytPPJB.setVisibility(View.GONE);
                    BtnPPJB.setVisibility(View.VISIBLE);
                }
            }
        });
        CBSTRA.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVStratatitle.setVisibility(View.VISIBLE);
                    HpsStratatitle.setVisibility(View.VISIBLE);
                    if (UriSTRA == null) {
                        BtnSTRA.setVisibility(View.VISIBLE);
                    } else {
                        BtnSTRA.setVisibility(View.GONE);
                    }
                } else {
                    UriSTRA = null;
                    LytStratatitle.setVisibility(View.GONE);
                    BtnSTRA.setVisibility(View.VISIBLE);
                }
            }
        });

        BtnSHM.setOnClickListener(view -> showPhotoSHM());
        BtnHGB.setOnClickListener(view -> showPhotoHGB());
        BtnHSHP.setOnClickListener(view -> showPhotoHSHP());
        BtnPPJB.setOnClickListener(view -> showPhotoPPJB());
        BtnSTRA.setOnClickListener(view -> showPhotoSTRA());
        BtnPjp.setOnClickListener(view -> showPhotoPJP());
        BtnPjp1.setOnClickListener(view -> showPhotoPJP1());

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog8();
            }
        });
        select1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog1();
            }
        });
        select2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog2();
            }
        });
        select3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog3();
            }
        });
        select4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog4();
            }
        });
        select5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog5();
            }
        });
        select6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog6();
            }
        });
        select7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog7();
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
        EtTglInput.setOnClickListener(new View.OnClickListener() {
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
                        EtTglInput.setText(MessageFormat.format("{0}", tanggal));
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });
        harga.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    harga.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Rp,.\\s]", "");
                    if (!cleanString.isEmpty()) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = String.format(Locale.US, "%,.0f", parsed);
                        HargaString = cleanString;
                        current = formatted;
                        harga.setText(formatted);
                        harga.setSelection(formatted.length());
                    } else {
                        harga.setText("");
                        HargaString = "";
                    }

                    harga.addTextChangedListener(this);
                }
            }
        });
        hargasewa.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    hargasewa.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Rp,.\\s]", "");
                    if (!cleanString.isEmpty()) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = String.format(Locale.US, "%,.0f", parsed);
                        HargaSewaString = cleanString;
                        current = formatted;
                        hargasewa.setText(formatted);
                        hargasewa.setSelection(formatted.length());
                    } else {
                        hargasewa.setText("");
                        HargaSewaString = "";
                    }

                    hargasewa.addTextChangedListener(this);
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
    private void showPhotoSelectionDialog1() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST1);
                                break;
                            case 1:
                                requestPermissions1();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog2() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST2);
                                break;
                            case 1:
                                requestPermissions2();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog3() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST3);
                                break;
                            case 1:
                                requestPermissions3();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog4() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST4);
                                break;
                            case 1:
                                requestPermissions4();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog5() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST5);
                                break;
                            case 1:
                                requestPermissions5();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog6() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST6);
                                break;
                            case 1:
                                requestPermissions6();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog7() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST7);
                                break;
                            case 1:
                                requestPermissions7();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog8() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST8);
                                break;
                            case 1:
                                requestPermissions8();
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
    private void showPhotoPJP() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_PJP);
                                break;
                            case 1:
                                requestPermissionsPjp();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoPJP1() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahListingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_PJP1);
                                break;
                            case 1:
                                requestPermissionsPjp1();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void requestPermissions1() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES1);
        }
    }
    private void requestPermissions2() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES2);
        }
    }
    private void requestPermissions3() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES3);
        }
    }
    private void requestPermissions4() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES4);
        }
    }
    private void requestPermissions5() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES5);
        }
    }
    private void requestPermissions6() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES6);
        }
    }
    private void requestPermissions7() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES7);
        }
    }
    private void requestPermissions8() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES8);
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
    private void requestPermissionsPjp() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP);
        }
    }
    private void requestPermissionsPjp1() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP1);
        }
    }
    private void bukaKamera1() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri1 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri1);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA1);
            }
        }
    }
    private void bukaKamera2() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri2 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri2);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA2);
            }
        }
    }
    private void bukaKamera3() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri3 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri3);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA3);
            }
        }
    }
    private void bukaKamera4() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri4 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri4);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA4);
            }
        }
    }
    private void bukaKamera5() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri5 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri5);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA5);
            }
        }
    }
    private void bukaKamera6() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri6 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri6);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA6);
            }
        }
    }
    private void bukaKamera7() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri7 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri7);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA7);
            }
        }
    }
    private void bukaKamera8() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri8 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri8);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA8);
            }
        }
    }
    private void bukaKameraSHM() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriSHM = FileProvider.getUriForFile(this, "com.gooproper", photoFile); // Gantilah "com.example.android.fileprovider" dengan authority Anda
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriSHM);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_SHM);
            }
        }
    }
    private void bukaKameraHGB() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriHGB = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriHGB);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_HGB);
            }
        }
    }
    private void bukaKameraHSHP() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriHSHP = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriHSHP);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_HSHP);
            }
        }
    }
    private void bukaKameraPPJB() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriPPJB = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriPPJB);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_PPJB);
            }
        }
    }
    private void bukaKameraSTRA() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriSTRA = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriSTRA);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_STRA);
            }
        }
    }
    private void bukaKameraPjp() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriPJP = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriPJP);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_PJP);
            }
        }
    }
    private void bukaKameraPjp1() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriPJP1 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriPJP1);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_PJP1);
            }
        }
    }
    private File createImageFile() {
        String imageFileName = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST3);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST4);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST5);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST6);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST7);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST8);
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
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PJP1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST3);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST4);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST5);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST6);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST7);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST8);
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
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PJP1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP1);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
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
        } else if (requestCode == CODE_GALLERY_REQUEST2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
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
        } else if (requestCode == CODE_GALLERY_REQUEST3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST3);
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
        } else if (requestCode == CODE_GALLERY_REQUEST4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST4);
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
        } else if (requestCode == CODE_GALLERY_REQUEST5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST5);
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
        } else if (requestCode == CODE_GALLERY_REQUEST6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST6);
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
        } else if (requestCode == CODE_GALLERY_REQUEST7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST7);
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
        } else if (requestCode == CODE_GALLERY_REQUEST8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST8);
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
        } else if (requestCode == CODE_GALLERY_REQUEST_PJP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP);
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
        } else if (requestCode == CODE_GALLERY_REQUEST_PJP1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_PJP1);
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
        } else if (requestCode == CODE_CAMERA_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera1();
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
        } else if (requestCode == CODE_CAMERA_REQUEST2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera2();
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
        } else if (requestCode == CODE_CAMERA_REQUEST3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera3();
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
        } else if (requestCode == CODE_CAMERA_REQUEST4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera4();
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
        } else if (requestCode == CODE_CAMERA_REQUEST5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera5();
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
        } else if (requestCode == CODE_CAMERA_REQUEST6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera6();
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
        } else if (requestCode == CODE_CAMERA_REQUEST7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera7();
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
        } else if (requestCode == CODE_CAMERA_REQUEST8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera8();
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
        } else if (requestCode == CODE_CAMERA_REQUEST_PJP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraPjp();
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
        } else if (requestCode == CODE_CAMERA_REQUEST_PJP1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraPjp1();
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
            }
        }

        if (requestCode == CODE_GALLERY_REQUEST1 && resultCode == RESULT_OK && data != null) {
            Uri1 = data.getData();
            iv1.setImageURI(Uri1);
            lyt1.setVisibility(View.VISIBLE);
            select1.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST2 && resultCode == RESULT_OK && data != null) {
            Uri2 = data.getData();
            iv2.setImageURI(Uri2);
            lyt2.setVisibility(View.VISIBLE);
            select2.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST3 && resultCode == RESULT_OK && data != null) {
            Uri3 = data.getData();
            iv3.setImageURI(Uri3);
            lyt3.setVisibility(View.VISIBLE);
            select3.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST4 && resultCode == RESULT_OK && data != null) {
            Uri4 = data.getData();
            iv4.setImageURI(Uri4);
            lyt4.setVisibility(View.VISIBLE);
            select4.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST5 && resultCode == RESULT_OK && data != null) {
            Uri5 = data.getData();
            iv5.setImageURI(Uri5);
            lyt5.setVisibility(View.VISIBLE);
            select5.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST6 && resultCode == RESULT_OK && data != null) {
            Uri6 = data.getData();
            iv6.setImageURI(Uri6);
            lyt6.setVisibility(View.VISIBLE);
            select6.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST7 && resultCode == RESULT_OK && data != null) {
            Uri7 = data.getData();
            iv7.setImageURI(Uri7);
            lyt7.setVisibility(View.VISIBLE);
            select7.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST8 && resultCode == RESULT_OK && data != null) {
            Uri8 = data.getData();
            iv8.setImageURI(Uri8);
            lyt8.setVisibility(View.VISIBLE);
            select.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_SHM && resultCode == RESULT_OK && data != null) {
            UriSHM = data.getData();
            IVShm.setImageURI(UriSHM);
            LytSHM.setVisibility(View.VISIBLE);
            BtnSHM.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_HGB && resultCode == RESULT_OK && data != null) {
            UriHGB = data.getData();
            IVHgb.setImageURI(UriHGB);
            LytHGB.setVisibility(View.VISIBLE);
            BtnHGB.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_HSHP && resultCode == RESULT_OK && data != null) {
            UriHSHP = data.getData();
            IVHshp.setImageURI(UriHSHP);
            LytHSHP.setVisibility(View.VISIBLE);
            BtnHSHP.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_PPJB && resultCode == RESULT_OK && data != null) {
            UriPPJB = data.getData();
            IVPpjb.setImageURI(UriPPJB);
            LytPPJB.setVisibility(View.VISIBLE);
            BtnPPJB.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_STRA && resultCode == RESULT_OK && data != null) {
            UriSTRA = data.getData();
            IVStratatitle.setImageURI(UriSTRA);
            LytStratatitle.setVisibility(View.VISIBLE);
            BtnSTRA.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_PJP && resultCode == RESULT_OK && data != null) {
            UriPJP = data.getData();
            IVPjp.setImageURI(UriPJP);
            LytPjp.setVisibility(View.VISIBLE);
            BtnPjp.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST_PJP1 && resultCode == RESULT_OK && data != null) {
            UriPJP1 = data.getData();
            IVPjp1.setImageURI(UriPJP1);
            LytPjp1.setVisibility(View.VISIBLE);
            BtnPjp1.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA1 && resultCode == RESULT_OK) {
            iv1.setImageURI(Uri1);
            lyt1.setVisibility(View.VISIBLE);
            select1.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA2 && resultCode == RESULT_OK) {
            iv2.setImageURI(Uri2);
            lyt2.setVisibility(View.VISIBLE);
            select2.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA3 && resultCode == RESULT_OK) {
            iv3.setImageURI(Uri3);
            lyt3.setVisibility(View.VISIBLE);
            select3.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA4 && resultCode == RESULT_OK) {
            iv4.setImageURI(Uri4);
            lyt4.setVisibility(View.VISIBLE);
            select4.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA5 && resultCode == RESULT_OK) {
            iv5.setImageURI(Uri5);
            lyt5.setVisibility(View.VISIBLE);
            select5.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA6 && resultCode == RESULT_OK) {
            iv6.setImageURI(Uri6);
            lyt6.setVisibility(View.VISIBLE);
            select6.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA7 && resultCode == RESULT_OK) {
            iv7.setImageURI(Uri7);
            lyt7.setVisibility(View.VISIBLE);
            select7.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA8 && resultCode == RESULT_OK) {
            iv8.setImageURI(Uri8);
            lyt8.setVisibility(View.VISIBLE);
            select.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_SHM && resultCode == RESULT_OK) {
            IVShm.setImageURI(UriSHM);
            LytSHM.setVisibility(View.VISIBLE);
            BtnSHM.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_HGB && resultCode == RESULT_OK) {
            IVHgb.setImageURI(UriHGB);
            LytHGB.setVisibility(View.VISIBLE);
            BtnHGB.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_HSHP && resultCode == RESULT_OK) {
            IVHshp.setImageURI(UriHSHP);
            LytHSHP.setVisibility(View.VISIBLE);
            BtnHSHP.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_PPJB && resultCode == RESULT_OK) {
            IVPpjb.setImageURI(UriSHM);
            LytPPJB.setVisibility(View.VISIBLE);
            BtnPPJB.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_STRA && resultCode == RESULT_OK) {
            IVStratatitle.setImageURI(UriSHM);
            LytStratatitle.setVisibility(View.VISIBLE);
            BtnSTRA.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA_PJP && resultCode == RESULT_OK) {
            IVPjp.setImageURI(UriPJP);
            LytPjp.setVisibility(View.VISIBLE);
            BtnPjp.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA_PJP1 && resultCode == RESULT_OK) {
            IVPjp1.setImageURI(UriPJP1);
            LytPjp1.setVisibility(View.VISIBLE);
            BtnPjp1.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void clearBitmap1() {
        if (bitmap1 != null && !bitmap1.isRecycled()) {
            bitmap1.recycle();
            bitmap1 = null;
            lyt1.setVisibility(View.GONE);
            select1.setVisibility(View.VISIBLE);
        } else {
            Uri1 = null;
            lyt1.setVisibility(View.GONE);
            select1.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap2() {
        if (bitmap2 != null && !bitmap2.isRecycled()) {
            bitmap2.recycle();
            bitmap2 = null;
            lyt2.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
        } else {
            Uri2 = null;
            lyt2.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap3() {
        if (bitmap3 != null && !bitmap3.isRecycled()) {
            bitmap3.recycle();
            bitmap3 = null;
            lyt3.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
        } else {
            Uri3 = null;
            lyt3.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap4() {
        if (bitmap4 != null && !bitmap4.isRecycled()) {
            bitmap4.recycle();
            bitmap4 = null;
            lyt4.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
        } else {
            Uri4 = null;
            lyt4.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap5() {
        if (bitmap5 != null && !bitmap5.isRecycled()) {
            bitmap5.recycle();
            bitmap5 = null;
            lyt5.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
        } else {
            Uri5 = null;
            lyt5.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap6() {
        if (bitmap6 != null && !bitmap6.isRecycled()) {
            bitmap6.recycle();
            bitmap6 = null;
            lyt6.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
        } else {
            Uri6 = null;
            lyt6.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap7() {
        if (bitmap7 != null && !bitmap7.isRecycled()) {
            bitmap7.recycle();
            bitmap7 = null;
            lyt7.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
        } else {
            Uri7 = null;
            lyt7.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap8() {
        if (bitmap8 != null && !bitmap8.isRecycled()) {
            bitmap8.recycle();
            bitmap8 = null;
            lyt8.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        } else {
            Uri8 = null;
            lyt8.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapSHM() {
        if (bitmapSHM != null && !bitmapSHM.isRecycled()) {
            bitmapSHM.recycle();
            bitmapSHM = null;
            LytSHM.setVisibility(View.GONE);
            BtnSHM.setVisibility(View.VISIBLE);
        } else {
            UriSHM = null;
            LytSHM.setVisibility(View.GONE);
            BtnSHM.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapHGB() {
        if (bitmapHGB != null && !bitmapHGB.isRecycled()) {
            bitmapHGB.recycle();
            bitmapHGB = null;
            LytHGB.setVisibility(View.GONE);
            BtnHGB.setVisibility(View.VISIBLE);
        } else {
            UriHGB = null;
            LytHGB.setVisibility(View.GONE);
            BtnHGB.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapHSHP() {
        if (bitmapHSHP != null && !bitmapHSHP.isRecycled()) {
            bitmapHSHP.recycle();
            bitmapHSHP = null;
            LytHSHP.setVisibility(View.GONE);
            BtnHSHP.setVisibility(View.VISIBLE);
        } else {
            UriHSHP = null;
            LytHSHP.setVisibility(View.GONE);
            BtnHSHP.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapPPJB() {
        if (bitmapPPJB != null && !bitmapPPJB.isRecycled()) {
            bitmapPPJB.recycle();
            bitmapPPJB = null;
            LytPPJB.setVisibility(View.GONE);
            BtnPPJB.setVisibility(View.VISIBLE);
        } else {
            UriPPJB = null;
            LytPPJB.setVisibility(View.GONE);
            BtnPPJB.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapSTRA() {
        if (bitmapSTRA != null && !bitmapSTRA.isRecycled()) {
            bitmapSTRA.recycle();
            bitmapSTRA = null;
            LytStratatitle.setVisibility(View.GONE);
            BtnSTRA.setVisibility(View.VISIBLE);
        } else {
            UriSTRA = null;
            LytStratatitle.setVisibility(View.GONE);
            BtnSTRA.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapPJP() {
        if (BitmapPjp != null && !BitmapPjp.isRecycled()) {
            BitmapPjp.recycle();
            BitmapPjp = null;
            LytPjp.setVisibility(View.GONE);
            BtnPjp.setVisibility(View.VISIBLE);
        } else {
            UriPJP = null;
            LytPjp.setVisibility(View.GONE);
            BtnPjp.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmapPJP1() {
        if (BitmapPjp1 != null && !BitmapPjp1.isRecycled()) {
            BitmapPjp1.recycle();
            BitmapPjp1 = null;
            LytPjp1.setVisibility(View.GONE);
            BtnPjp1.setVisibility(View.VISIBLE);
        } else {
            UriPJP1 = null;
            LytPjp1.setVisibility(View.GONE);
            BtnPjp1.setVisibility(View.VISIBLE);
        }
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
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
                            } else if (status.equals("Error")) {
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

                                dialogTitle.setText("Gagal Menambahkan Listingan");
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

                        dialogTitle.setText("Terdapat Masalah Jaringan");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(TambahListingActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground)
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
                if (latitudeStr == null) {
                    Lat = "0";
                } else {
                    Lat = latitudeStr;
                }
                if (longitudeStr == null) {
                    Lng = "0";
                } else {
                    Lng = longitudeStr;
                }
                if (HargaString == null){
                    SHarga = "0";
                } else {
                    SHarga = HargaString;
                }
                if (HargaSewaString == null){
                    SHargaSewa = "0";
                } else {
                    SHargaSewa = HargaSewaString;
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
                map.put("IdAgenCo", idnull);
                map.put("IdInput", idnull);
                map.put("NamaListing", namaproperti.getText().toString());
                map.put("Alamat", alamatproperti.getText().toString());
                map.put("Latitude", Lat);
                map.put("Longitude", Lng);
                map.put("Location", alamatproperti.getText().toString());
                map.put("Wide", luas.getText().toString());
                map.put("Land", land.getText().toString());
                map.put("Dimensi", dimensi.getText().toString());
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
                map.put("ImgPjp", PJPHal1);
                map.put("ImgPjp1", PJPHal2);
                map.put("JenisProperti", jenisproperti.getText().toString());
                map.put("SumberAir", air.getText().toString());
                map.put("Kondisi", status.getText().toString());
                map.put("Deskripsi", keterangan.getText().toString());
                map.put("Prabot", perabot.getText().toString());
                map.put("KetPrabot", ketperabot.getText().toString());
                map.put("Priority", priority);
                map.put("Banner", banner.getText().toString());
                map.put("Size", size.getText().toString());
                map.put("Harga", SHarga);
                map.put("HargaSewa", SHargaSewa);
                map.put("TglInput", EtTglInput.getText().toString());
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
                map.put("Fee", EtFee.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void simpanDataAgen() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
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
                            } else if (status.equals("Error")) {
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

                                dialogTitle.setText("Gagal Menambahkan Listingan");
                                ok.setVisibility(View.GONE);

                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                        finish();
                                    }
                                });

                                Glide.with(TambahListingActivity.this)
                                        .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
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

                        dialogTitle.setText("Terdapat Masalah Jaringan");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(TambahListingActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground)
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
                if (latitudeStr == null) {
                    Lat = "0";
                } else {
                    Lat = latitudeStr;
                }
                if (longitudeStr == null) {
                    Lng = "0";
                } else {
                    Lng = longitudeStr;
                }
                if (HargaString == null){
                    SHarga = "0";
                } else {
                    SHarga = HargaString;
                }
                if (HargaSewaString == null){
                    SHargaSewa = "0";
                } else {
                    SHargaSewa = HargaSewaString;
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
                map.put("IdAgenCo", idagen);
                map.put("IdInput", idagen);
                map.put("NamaListing", namaproperti.getText().toString());
                map.put("Alamat", alamatproperti.getText().toString());
                map.put("Latitude", Lat);
                map.put("Longitude", Lng);
                map.put("Location", alamatproperti.getText().toString());
                map.put("Wide", luas.getText().toString());
                map.put("Land", land.getText().toString());
                map.put("Dimensi", dimensi.getText().toString());
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
                map.put("ImgPjp", PJPHal1);
                map.put("ImgPjp1", PJPHal2);
                map.put("JenisProperti", jenisproperti.getText().toString());
                map.put("SumberAir", air.getText().toString());
                map.put("Kondisi", status.getText().toString());
                map.put("Deskripsi", keterangan.getText().toString());
                map.put("Prabot", perabot.getText().toString());
                map.put("KetPrabot", ketperabot.getText().toString());
                map.put("Priority", priority);
                map.put("Banner", banner.getText().toString());
                map.put("Size", size.getText().toString());
                map.put("Harga", SHarga);
                map.put("HargaSewa", SHargaSewa);
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
                map.put("Fee", EtFee.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void simpanDataNonAgen() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
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
                            } else if (status.equals("Error")) {
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

                                dialogTitle.setText("Gagal Menambahkan Listingan");
                                ok.setVisibility(View.GONE);

                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                        finish();
                                    }
                                });

                                Glide.with(TambahListingActivity.this)
                                        .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
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

                        dialogTitle.setText("Terdapat Masalah Jaringan");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(TambahListingActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
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
                if (latitudeStr == null) {
                    Lat = "0";
                } else {
                    Lat = latitudeStr;
                }
                if (longitudeStr == null) {
                    Lng = "0";
                } else {
                    Lng = longitudeStr;
                }
                if (HargaString == null){
                    SHarga = "0";
                } else {
                    SHarga = HargaString;
                }
                if (HargaSewaString == null){
                    SHargaSewa = "0";
                } else {
                    SHargaSewa = HargaSewaString;
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
                map.put("IdAgenCo", idnull);
                map.put("IdInput", idagen);
                map.put("NamaListing", namaproperti.getText().toString());
                map.put("Alamat", alamatproperti.getText().toString());
                map.put("Latitude", Lat);
                map.put("Longitude", Lng);
                map.put("Location", alamatproperti.getText().toString());
                map.put("Wide", luas.getText().toString());
                map.put("Land", land.getText().toString());
                map.put("Dimensi", dimensi.getText().toString());
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
                map.put("ImgPjp", PJPHal1);
                map.put("ImgPjp1", PJPHal2);
                map.put("JenisProperti", jenisproperti.getText().toString());
                map.put("SumberAir", air.getText().toString());
                map.put("Kondisi", status.getText().toString());
                map.put("Deskripsi", keterangan.getText().toString());
                map.put("Prabot", perabot.getText().toString());
                map.put("KetPrabot", ketperabot.getText().toString());
                map.put("Priority", priority);
                map.put("Banner", banner.getText().toString());
                map.put("Size", size.getText().toString());
                map.put("Harga", SHarga);
                map.put("HargaSewa", SHargaSewa);
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
                map.put("Fee", EtFee.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    public void ShowBank(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Bank");

        final CharSequence[] Bank = {"BCA", "BRI", "BTN", "Mandiri", "Permata", "Bank Lainnya.."};
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
                if (SelectedBank[0] == Bank.length - 1) {
                    // The user selected "Bank Lainnya..," show the custom input dialog
                    showCustomBankInputDialog();
                } else {
                    bank.setText(Bank[SelectedBank[0]]);
                }
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }
    private void showCustomBankInputDialog() {
        AlertDialog.Builder customBuilder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        customBuilder.setTitle("Bank Lainnya");

        LinearLayout containerLayout = new LinearLayout(this);
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        containerLayout.setPadding(50, 20, 50, 0);

        final EditText customBankInput = new EditText(this);
        customBankInput.setHint("Masukkan Nama Bank");
        customBankInput.setTextColor(getResources().getColor(android.R.color.black));
        customBankInput.setHintTextColor(getResources().getColor(android.R.color.black));
        //customBankInput.setBackgroundResource(R.drawable.backgroundbox);
        //customBankInput.setPadding(10,10,10,10);

        containerLayout.addView(customBankInput);

        customBuilder.setView(containerLayout);

        customBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String customBankName = customBankInput.getText().toString();
                bank.setText(customBankName);
            }
        });

        customBuilder.setNegativeButton("Batal", null);

        AlertDialog customDialog = customBuilder.create();
        customDialog.show();
    }
    public void ShowJenisProperti(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Jenis Properti");

        final CharSequence[] JenisProperti = {"Rumah", "Ruko", "Tanah", "Gudang", "Ruang Usaha", "Villa", "Apartemen", "Pabrik", "Kantor", "Hotel", "Rukost"};
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
    public void ShowPjp(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ketersediaan PJP");

        final CharSequence[] JenisProperti = {"Ya", "Tidak"};
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
                pjp.setText(JenisProperti[SelectedJenisProperti[0]]);
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

        final CharSequence[] Status = {"Jual", "Sewa", "Jual/Sewa"};
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
            Dialog customDialog = new Dialog(TambahListingActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Pilih Status Properti");

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
        } else if (status.getText().toString().equals("Jual")) {
            if (harga.getText().toString().equals("")) {
                harga.setError("Harap Isi Harga Properti");
                harga.requestFocus();
                return false;
            }
        } else if (status.getText().toString().equals("Sewa")) {
            if (hargasewa.getText().toString().equals("")) {
                hargasewa.setError("Harap Isi Harga Properti");
                hargasewa.requestFocus();
                return false;
            }
        } else {
            if (harga.getText().toString().equals("")) {
                harga.setError("Harap Isi Harga Properti");
                harga.requestFocus();
                return false;
            }
            if (hargasewa.getText().toString().equals("")) {
                hargasewa.setError("Harap Isi Harga Properti");
                hargasewa.requestFocus();
                return false;
            }
        }
        if (banner.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(TambahListingActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Isi Pemasangan Banner");

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
        } else if (banner.getText().toString().equals("Ya")) {
            if (size.getText().toString().equals("")) {
                Dialog customDialog = new Dialog(TambahListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Isi Ukuran Banner");

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
        if (CBSHM.isChecked()) {
            if (UriSHM == null) {
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
        if (CBHGB.isChecked()) {
            if (UriHGB == null) {
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
        if (CBHSHP.isChecked()) {
            if (UriHSHP == null) {
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
        if (CBPPJB.isChecked()) {
            if (UriPPJB == null) {
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
        if (CBSTRA.isChecked()) {
            if (UriSTRA == null) {
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