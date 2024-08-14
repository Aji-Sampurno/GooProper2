package com.gooproper.ui.edit.pralisting;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.util.Preferences;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class TambahDataGambarPralistingActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST1 = 1;
    final int CODE_GALLERY_REQUEST2 = 2;
    final int CODE_GALLERY_REQUEST3 = 3;
    final int CODE_GALLERY_REQUEST4 = 4;
    final int CODE_GALLERY_REQUEST5 = 5;
    final int CODE_GALLERY_REQUEST6 = 6;
    final int CODE_GALLERY_REQUEST7 = 7;
    final int CODE_GALLERY_REQUEST8 = 8;
    final int CODE_GALLERY_REQUEST9 = 9;
    final int CODE_GALLERY_REQUEST10 = 10;
    final int CODE_GALLERY_REQUEST11 = 11;
    final int CODE_GALLERY_REQUEST12 = 12;
    final int CODE_CAMERA_REQUEST1 = 13;
    final int CODE_CAMERA_REQUEST2 = 14;
    final int CODE_CAMERA_REQUEST3 = 15;
    final int CODE_CAMERA_REQUEST4 = 16;
    final int CODE_CAMERA_REQUEST5 = 17;
    final int CODE_CAMERA_REQUEST6 = 18;
    final int CODE_CAMERA_REQUEST7 = 19;
    final int CODE_CAMERA_REQUEST8 = 20;
    final int CODE_CAMERA_REQUEST9 = 21;
    final int CODE_CAMERA_REQUEST10 = 22;
    final int CODE_CAMERA_REQUEST11 = 23;
    final int CODE_CAMERA_REQUEST12 = 24;
    final int KODE_REQUEST_KAMERA1 = 25;
    final int KODE_REQUEST_KAMERA2 = 26;
    final int KODE_REQUEST_KAMERA3 = 27;
    final int KODE_REQUEST_KAMERA4 = 28;
    final int KODE_REQUEST_KAMERA5 = 29;
    final int KODE_REQUEST_KAMERA6 = 30;
    final int KODE_REQUEST_KAMERA7 = 31;
    final int KODE_REQUEST_KAMERA8 = 32;
    final int KODE_REQUEST_KAMERA9 = 33;
    final int KODE_REQUEST_KAMERA10 = 34;
    final int KODE_REQUEST_KAMERA11 = 35;
    final int KODE_REQUEST_KAMERA12 = 36;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1 = 70;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES1 = 71;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2 = 72;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES2 = 73;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3 = 74;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES3 = 75;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4 = 76;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES4 = 77;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5 = 78;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES5 = 79;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6 = 80;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES6 = 81;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7 = 82;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES7 = 83;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8 = 84;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES8 = 85;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9 = 86;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES9 = 87;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10 = 88;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES10 = 89;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE11 = 90;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES11 = 91;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE12 = 92;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES12 = 93;
    private static final int STORAGE_PERMISSION_CODE = 117;
    Uri Uri1, Uri2, Uri3, Uri4, Uri5, Uri6, Uri7, Uri8, Uri9, Uri10, Uri11, Uri12;
    LinearLayout lyt1, lyt2, lyt3, lyt4, lyt5, lyt6, lyt7, lyt8, lyt9, lyt10, lyt11, lyt12;
    ImageView back;
    ImageView iv1, iv2, iv3, iv4, iv5, iv6, iv7, iv8, iv9, iv10, iv11, iv12;
    ImageView hps1, hps2, hps3, hps4, hps5, hps6, hps7, hps8, hps9, hps10, hps11, hps12;
    Button batal, submit;
    Button select, select1, select2, select3, select4, select5, select6, select7, select9, select10, select11, select12;
    String intentIdPraListing,intentIdAgen,intentIdAgenCo,intentPjp,intentPriority,intentBanner,intentImg1,intentImg2,intentImg3,intentImg4,intentImg5,intentImg6,intentImg7,intentImg8,intentImg9,intentImg10,intentImg11,intentImg12,intentMarketable,intentStatusHarga,intentIsSelfie,intentIsLokasi;
    int PoinSekarang, PoinTambah, PoinKurang;
    String image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12;
    String timeStamp;
    String fileListing1,fileListing2,fileListing3,fileListing4,fileListing5,fileListing6,fileListing7,fileListing8,fileListing9,fileListing10,fileListing11,fileListing12;
    private StorageReference mStorageRef;
    StorageReference storageRef;
    StorageReference ImgListing1,ImgListing2,ImgListing3,ImgListing4,ImgListing5,ImgListing6,ImgListing7,ImgListing8,ImgListing9,ImgListing10,ImgListing11,ImgListing12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data_gambar_pralisting);

        pDialog = new ProgressDialog(TambahDataGambarPralistingActivity.this);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        iv1 = findViewById(R.id.ivs1);
        iv2 = findViewById(R.id.ivs2);
        iv3 = findViewById(R.id.ivs3);
        iv4 = findViewById(R.id.ivs4);
        iv5 = findViewById(R.id.ivs5);
        iv6 = findViewById(R.id.ivs6);
        iv7 = findViewById(R.id.ivs7);
        iv8 = findViewById(R.id.ivs8);
        iv9 = findViewById(R.id.ivs9);
        iv10 = findViewById(R.id.ivs10);
        iv11 = findViewById(R.id.ivs11);
        iv12 = findViewById(R.id.ivs12);

        lyt1 = findViewById(R.id.lyts1);
        lyt2 = findViewById(R.id.lyts2);
        lyt3 = findViewById(R.id.lyts3);
        lyt4 = findViewById(R.id.lyts4);
        lyt5 = findViewById(R.id.lyts5);
        lyt6 = findViewById(R.id.lyts6);
        lyt7 = findViewById(R.id.lyts7);
        lyt8 = findViewById(R.id.lyts8);
        lyt9 = findViewById(R.id.lyts9);
        lyt10 = findViewById(R.id.lyts10);
        lyt11 = findViewById(R.id.lyts11);
        lyt12 = findViewById(R.id.lyts12);

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
        select9 = findViewById(R.id.btnSelectImage9);
        select10 = findViewById(R.id.btnSelectImage10);
        select11 = findViewById(R.id.btnSelectImage11);
        select12 = findViewById(R.id.btnSelectImage12);

        hps1 = findViewById(R.id.IVDelete1);
        hps2 = findViewById(R.id.IVDelete2);
        hps3 = findViewById(R.id.IVDelete3);
        hps4 = findViewById(R.id.IVDelete4);
        hps5 = findViewById(R.id.IVDelete5);
        hps6 = findViewById(R.id.IVDelete6);
        hps7 = findViewById(R.id.IVDelete7);
        hps8 = findViewById(R.id.IVDelete8);
        hps9 = findViewById(R.id.IVDelete9);
        hps10 = findViewById(R.id.IVDelete10);
        hps11 = findViewById(R.id.IVDelete11);
        hps12 = findViewById(R.id.IVDelete12);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        intentIdPraListing = data.getStringExtra("IdPraListing");
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

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        fileListing1 = "Listing1_" + timeStamp + ".jpg";
        fileListing2 = "Listing2_" + timeStamp + ".jpg";
        fileListing3 = "Listing3_" + timeStamp + ".jpg";
        fileListing4 = "Listing4_" + timeStamp + ".jpg";
        fileListing5 = "Listing5_" + timeStamp + ".jpg";
        fileListing6 = "Listing6_" + timeStamp + ".jpg";
        fileListing7 = "Listing7_" + timeStamp + ".jpg";
        fileListing8 = "Listing8_" + timeStamp + ".jpg";
        fileListing9 = "Listing9_" + timeStamp + ".jpg";
        fileListing10 = "Listing10_" + timeStamp + ".jpg";
        fileListing11 = "Listing11_" + timeStamp + ".jpg";
        fileListing12 = "Listing12_" + timeStamp + ".jpg";

        storageRef = FirebaseStorage.getInstance().getReference();
        ImgListing1 = storageRef.child("listing/" + fileListing1);
        ImgListing2 = storageRef.child("listing/" + fileListing2);
        ImgListing3 = storageRef.child("listing/" + fileListing3);
        ImgListing4 = storageRef.child("listing/" + fileListing4);
        ImgListing5 = storageRef.child("listing/" + fileListing5);
        ImgListing6 = storageRef.child("listing/" + fileListing6);
        ImgListing7 = storageRef.child("listing/" + fileListing7);
        ImgListing8 = storageRef.child("listing/" + fileListing8);
        ImgListing9 = storageRef.child("listing/" + fileListing9);
        ImgListing10 = storageRef.child("listing/" + fileListing10);
        ImgListing11 = storageRef.child("listing/" + fileListing11);
        ImgListing12 = storageRef.child("listing/" + fileListing12);

        back.setOnClickListener(view -> finish());
        batal.setOnClickListener(view -> finish());
        submit.setOnClickListener(view -> handleImage1Success());
        hps1.setOnClickListener(view -> clearBitmap1());
        hps2.setOnClickListener(view -> clearBitmap2());
        hps3.setOnClickListener(view -> clearBitmap3());
        hps4.setOnClickListener(view -> clearBitmap4());
        hps5.setOnClickListener(view -> clearBitmap5());
        hps6.setOnClickListener(view -> clearBitmap6());
        hps7.setOnClickListener(view -> clearBitmap7());
        hps8.setOnClickListener(view -> clearBitmap8());
        hps9.setOnClickListener(view -> clearBitmap9());
        hps10.setOnClickListener(view -> clearBitmap10());
        hps11.setOnClickListener(view -> clearBitmap11());
        hps12.setOnClickListener(view -> clearBitmap12());

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
        select9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog9();
            }
        });
        select10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog10();
            }
        });
        select11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog11();
            }
        });
        select12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog12();
            }
        });

        if (!intentImg1.equals("0")) {
            select1.setVisibility(View.GONE);
        } else {
            select1.setVisibility(View.VISIBLE);
        }
        if (!intentImg2.equals("0")) {
            select2.setVisibility(View.GONE);
        } else {
            select2.setVisibility(View.VISIBLE);
        }
        if (!intentImg3.equals("0")) {
            select3.setVisibility(View.GONE);
        } else {
            select3.setVisibility(View.VISIBLE);
        }
        if (!intentImg4.equals("0")) {
            select4.setVisibility(View.GONE);
        } else {
            select4.setVisibility(View.VISIBLE);
        }
        if (!intentImg5.equals("0")) {
            select5.setVisibility(View.GONE);
        } else {
            select5.setVisibility(View.VISIBLE);
        }
        if (!intentImg6.equals("0")) {
            select6.setVisibility(View.GONE);
        } else {
            select6.setVisibility(View.VISIBLE);
        }
        if (!intentImg7.equals("0")) {
            select7.setVisibility(View.GONE);
        } else {
            select7.setVisibility(View.VISIBLE);
        }
        if (!intentImg8.equals("0")) {
            select.setVisibility(View.GONE);
        } else {
            select.setVisibility(View.VISIBLE);
        }
        if (!intentImg9.equals("0")) {
            select9.setVisibility(View.GONE);
        } else {
            select9.setVisibility(View.VISIBLE);
        }
        if (!intentImg10.equals("0")) {
            select10.setVisibility(View.GONE);
        } else {
            select10.setVisibility(View.VISIBLE);
        }
        if (!intentImg11.equals("0")) {
            select11.setVisibility(View.GONE);
        } else {
            select11.setVisibility(View.VISIBLE);
        }
        if (!intentImg12.equals("0")) {
            select12.setVisibility(View.GONE);
        } else {
            select12.setVisibility(View.VISIBLE);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void showPhotoSelectionDialog1() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST1);
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
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST2);
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
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST3);
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
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST4);
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
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST5);
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
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST6);
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
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST7);
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
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST8);
                                break;
                            case 1:
                                requestPermissions8();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog9() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST9);
                                break;
                            case 1:
                                requestPermissions9();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog10() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST10);
                                break;
                            case 1:
                                requestPermissions10();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog11() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST11);
                                break;
                            case 1:
                                requestPermissions11();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoSelectionDialog12() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahDataGambarPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST12);
                                break;
                            case 1:
                                requestPermissions12();
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
    private void requestPermissions9() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES9);
        }
    }
    private void requestPermissions10() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES10);
        }
    }
    private void requestPermissions11() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE11);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES11);
        }
    }
    private void requestPermissions12() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE12);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES12);
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
    private void bukaKamera9() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri9 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri9);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA9);
            }
        }
    }
    private void bukaKamera10() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri10 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri10);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA10);
            }
        }
    }
    private void bukaKamera11() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri11 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri11);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA11);
            }
        }
    }
    private void bukaKamera12() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                Uri12 = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri12);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA12);
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
    private String getFileNameFromUri(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
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
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST11);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST12);
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
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST11);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST12);
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {} else {}
        } else if (requestCode == CODE_GALLERY_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            } else {
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
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
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
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
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
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
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
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
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
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
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
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
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
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
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            } else {
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            } else {
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST11);
            } else {
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST12);
            } else {
                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                Glide.with(TambahDataGambarPralistingActivity.this)
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera9();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera10();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST11) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera11();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST12) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera12();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahDataGambarPralistingActivity.this);
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
        if (requestCode == CODE_GALLERY_REQUEST1 && resultCode == RESULT_OK && data != null) {
            Uri1 = data.getData();
            Glide.with(this)
                    .load(Uri1)
                    .into(iv1);
            iv1.setTag(Uri1);
            lyt1.setVisibility(View.VISIBLE);
            select1.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST2 && resultCode == RESULT_OK && data != null) {
            Uri2 = data.getData();
            Glide.with(this)
                    .load(Uri2)
                    .into(iv2);
            iv2.setTag(Uri2);
            lyt2.setVisibility(View.VISIBLE);
            select2.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST3 && resultCode == RESULT_OK && data != null) {
            Uri3 = data.getData();
            Glide.with(this)
                    .load(Uri3)
                    .into(iv3);
            iv3.setTag(Uri3);
            lyt3.setVisibility(View.VISIBLE);
            select3.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST4 && resultCode == RESULT_OK && data != null) {
            Uri4 = data.getData();
            Glide.with(this)
                    .load(Uri4)
                    .into(iv4);
            iv4.setTag(Uri4);
            lyt4.setVisibility(View.VISIBLE);
            select4.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST5 && resultCode == RESULT_OK && data != null) {
            Uri5 = data.getData();
            Glide.with(this)
                    .load(Uri5)
                    .into(iv5);
            iv5.setTag(Uri5);
            lyt5.setVisibility(View.VISIBLE);
            select5.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST6 && resultCode == RESULT_OK && data != null) {
            Uri6 = data.getData();
            Glide.with(this)
                    .load(Uri6)
                    .into(iv6);
            iv6.setTag(Uri6);
            lyt6.setVisibility(View.VISIBLE);
            select6.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST7 && resultCode == RESULT_OK && data != null) {
            Uri7 = data.getData();
            Glide.with(this)
                    .load(Uri7)
                    .into(iv7);
            iv7.setTag(Uri7);
            lyt7.setVisibility(View.VISIBLE);
            select7.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST8 && resultCode == RESULT_OK && data != null) {
            Uri8 = data.getData();
            Glide.with(this)
                    .load(Uri8)
                    .into(iv8);
            iv8.setTag(Uri8);
            lyt8.setVisibility(View.VISIBLE);
            select.setVisibility(View.GONE);
            select9.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST9 && resultCode == RESULT_OK && data != null) {
            Uri9 = data.getData();
            Glide.with(this)
                    .load(Uri9)
                    .into(iv9);
            iv9.setTag(Uri9);
            lyt9.setVisibility(View.VISIBLE);
            select9.setVisibility(View.GONE);
            select10.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST10 && resultCode == RESULT_OK && data != null) {
            Uri10 = data.getData();
            Glide.with(this)
                    .load(Uri10)
                    .into(iv10);
            iv10.setTag(Uri10);
            lyt10.setVisibility(View.VISIBLE);
            select10.setVisibility(View.GONE);
            select11.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST11 && resultCode == RESULT_OK && data != null) {
            Uri11 = data.getData();
            Glide.with(this)
                    .load(Uri11)
                    .into(iv11);
            iv11.setTag(Uri11);
            lyt11.setVisibility(View.VISIBLE);
            select11.setVisibility(View.GONE);
            select12.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST12 && resultCode == RESULT_OK && data != null) {
            Uri12 = data.getData();
            Glide.with(this)
                    .load(Uri12)
                    .into(iv12);
            iv12.setTag(Uri12);
            lyt12.setVisibility(View.VISIBLE);
            select12.setVisibility(View.GONE);
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
            select9.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA9 && resultCode == RESULT_OK) {
            iv9.setImageURI(Uri9);
            lyt9.setVisibility(View.VISIBLE);
            select9.setVisibility(View.GONE);
            select10.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA10 && resultCode == RESULT_OK) {
            iv10.setImageURI(Uri10);
            lyt10.setVisibility(View.VISIBLE);
            select10.setVisibility(View.GONE);
            select11.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA11 && resultCode == RESULT_OK) {
            iv11.setImageURI(Uri11);
            lyt11.setVisibility(View.VISIBLE);
            select11.setVisibility(View.GONE);
            select12.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA12 && resultCode == RESULT_OK) {
            iv12.setImageURI(Uri12);
            lyt12.setVisibility(View.VISIBLE);
            select12.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void clearBitmap1() {
        if (Uri1 != null) {
            Uri1 = null;
            lyt1.setVisibility(View.GONE);
            select1.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap2() {
        if (Uri2 != null) {
            Uri2 = null;
            lyt2.setVisibility(View.GONE);
            select2.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap3() {
        if (Uri3 != null) {
            Uri3 = null;
            lyt3.setVisibility(View.GONE);
            select3.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap4() {
        if (Uri4 != null) {
            Uri4 = null;
            lyt4.setVisibility(View.GONE);
            select4.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap5() {
        if (Uri5 != null) {
            Uri5 = null;
            lyt5.setVisibility(View.GONE);
            select5.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap6() {
        if (Uri6 != null) {
            Uri6 = null;
            lyt6.setVisibility(View.GONE);
            select6.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap7() {
        if (Uri7 != null) {
            Uri7 = null;
            lyt7.setVisibility(View.GONE);
            select7.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap8() {
        if (Uri8 != null) {
            Uri8 = null;
            lyt8.setVisibility(View.GONE);
            select.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap9() {
        if (Uri9 != null) {
            Uri9 = null;
            lyt9.setVisibility(View.GONE);
            select9.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap10() {
        if (Uri10 != null) {
            Uri10 = null;
            lyt10.setVisibility(View.GONE);
            select10.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap11() {
        if (Uri11 != null) {
            Uri11 = null;
            lyt11.setVisibility(View.GONE);
            select11.setVisibility(View.VISIBLE);
        }
    }
    private void clearBitmap12() {
        if (Uri12 != null) {
            Uri12 = null;
            lyt12.setVisibility(View.GONE);
            select12.setVisibility(View.VISIBLE);
        }
    }
    private void showProgressDialog() {
        pDialog.setMessage("Unggah Gambar");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void HideProgressDialog() {
        pDialog.dismiss();
        pDialog.cancel();
    }
    private void handleImage1Success() {
        if (Uri1 != null) {
            showProgressDialog();
            ImgListing1.putFile(Uri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing1.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image1 = imageUrl;
                                        handleImage2Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        HideProgressDialog();
                                        handleImage1Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            HideProgressDialog();
                            handleImage1Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            showProgressDialog();
            image1 = intentImg1;
            handleImage2Success();
        }
    }
    private void handleImage2Success() {
        if (Uri2 != null) {
            ImgListing2.putFile(Uri2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing2.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image2 = imageUrl;
                                        handleImage3Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage2Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage2Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image2 = intentImg2;
            handleImage3Success();
        }
    }
    private void handleImage3Success() {
        if (Uri3 != null) {
            ImgListing3.putFile(Uri3)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing3.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image3 = imageUrl;
                                        handleImage4Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage3Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage3Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image3 = intentImg3;
            handleImage4Success();
        }
    }
    private void handleImage4Success() {
        if (Uri4 != null) {
            ImgListing4.putFile(Uri4)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing4.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image4 = imageUrl;
                                        handleImage5Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage4Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage4Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image4 = intentImg4;
            handleImage5Success();
        }
    }
    private void handleImage5Success() {
        if (Uri5 != null) {
            ImgListing5.putFile(Uri5)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing5.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image5 = imageUrl;
                                        handleImage6Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage5Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage5Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image5 = intentImg5;
            handleImage6Success();
        }
    }
    private void handleImage6Success() {
        if (Uri6 != null) {
            ImgListing6.putFile(Uri6)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing6.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image6 = imageUrl;
                                        handleImage7Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage6Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage6Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image6 = intentImg6;
            handleImage7Success();
        }
    }
    private void handleImage7Success() {
        if (Uri7 != null) {
            ImgListing7.putFile(Uri7)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing7.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image7 = imageUrl;
                                        handleImage8Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage7Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage7Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image7 = intentImg7;
            handleImage8Success();
        }
    }
    private void handleImage8Success() {
        if (Uri8 != null) {
            ImgListing8.putFile(Uri8)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing8.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image8 = imageUrl;
                                        handleImage9Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage8Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage8Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image8 = intentImg8;
            handleImage9Success();
        }
    }
    private void handleImage9Success() {
        if (Uri9 != null) {
            ImgListing9.putFile(Uri9)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing9.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image9 = imageUrl;
                                        handleImage10Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage9Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage9Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image9 = intentImg9;
            handleImage10Success();
        }
    }
    private void handleImage10Success() {
        if (Uri10 != null) {
            ImgListing10.putFile(Uri10)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing10.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image10 = imageUrl;
                                        handleImage11Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage10Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage10Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image10 = intentImg10;
            handleImage11Success();
        }
    }
    private void handleImage11Success() {
        if (Uri11 != null) {
            ImgListing11.putFile(Uri11)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing11.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image11 = imageUrl;
                                        handleImage12Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage11Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage11Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image11 = intentImg11;
            handleImage12Success();
        }
    }
    private void handleImage12Success() {
        if (Uri12 != null) {
            ImgListing12.putFile(Uri12)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing12.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        image12 = imageUrl;
                                        HideProgressDialog();
                                        simpanData();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage12Success();
                                        Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage12Success();
                            Toast.makeText(TambahDataGambarPralistingActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            image12 = intentImg12;
            HideProgressDialog();
            simpanData();
        }
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_DATA_GAMBAR_PRALISTING,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
                                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Berhasil Menambahkan Gambar Listingan");
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

                                Glide.with(TambahDataGambarPralistingActivity.this)
                                        .load(R.mipmap.ic_yes)
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
                                Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Gagal Menambahkan Gambar Listingan");
                                ok.setVisibility(View.GONE);

                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                    }
                                });

                                Glide.with(TambahDataGambarPralistingActivity.this)
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
                        Dialog customDialog = new Dialog(TambahDataGambarPralistingActivity.this);
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

                        Glide.with(TambahDataGambarPralistingActivity.this)
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
                map.put("Img1", image1);
                map.put("Img2", image2);
                map.put("Img3", image3);
                map.put("Img4", image4);
                map.put("Img5", image5);
                map.put("Img6", image6);
                map.put("Img7", image7);
                map.put("Img8", image8);
                map.put("Img9", image9);
                map.put("Img10", image10);
                map.put("Img11", image11);
                map.put("Img12", image12);
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    private class SendMessageTask extends AsyncTask<String, Void, String> {
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
    private void sendNotificationToToken(String token, String notificationType) {
        String title = Preferences.getKeyNama(this);
        String message = "Menambahkan Gambar Pralisting";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}