package com.gooproper.ui.edit.primary;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.ui.tambah.primary.TambahListingPrimaryActivity;
import com.gooproper.ui.tambah.primary.TambahTipeListingPrimaryActivity;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditPrimaryActivity extends AppCompatActivity {
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
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1 = 11;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES1 = 12;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2 = 13;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES2 = 14;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3 = 15;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES3 = 16;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4 = 17;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES4 = 18;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5 = 19;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES5 = 20;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6 = 21;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES6 = 22;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7 = 23;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES7 = 24;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8 = 25;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES8 = 26;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9 = 27;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES9 = 28;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10 = 29;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES10 = 30;
    private static final int MAPS_ACTIVITY_REQUEST_CODE = 31;
    Uri Uri1, Uri2, Uri3, Uri4, Uri5, Uri6, Uri7, Uri8, Uri9, Uri10;
    LinearLayout Lyt1, Lyt2, Lyt3, Lyt4, Lyt5, Lyt6, Lyt7, Lyt8, Lyt9, Lyt10;
    Button BtnBatal, BtnSubmit, BtnSelect1, BtnSelect2, BtnSelect3, BtnSelect4, BtnSelect5, BtnSelect6, BtnSelect7, BtnSelect8, BtnSelect9, BtnSelect10;
    ImageView IV1, IV2, IV3, IV4, IV5, IV6, IV7, IV8, IV9, IV10, IVDelete1, IVDelete2, IVDelete3, IVDelete4, IVDelete5, IVDelete6, IVDelete7, IVDelete8, IVDelete9, IVDelete10;
    TextInputEditText ETJudul, ETHarga, ETLokasi, ETDeskripsi, ETKontak1, ETKontak2;
    String latitudeStr, longitudeStr, addressStr, Lat, Lng, token, StrIdListingPrimary;
    String Gambar1, Gambar2, Gambar3, Gambar4, Gambar5, Gambar6, Gambar7, Gambar8, Gambar9, Gambar10;
    String Image1, Image2, Image3, Image4, Image5, Image6, Image7, Image8, Image9, Image10;
    String timeStamp;
    String filePrimary1, filePrimary2, filePrimary3, filePrimary4, filePrimary5, filePrimary6, filePrimary7, filePrimary8, filePrimary9, filePrimary10;
    private StorageReference mStorageRef;
    StorageReference storageRef;
    StorageReference ImgListing1,ImgListing2,ImgListing3,ImgListing4,ImgListing5,ImgListing6,ImgListing7,ImgListing8,ImgListing9,ImgListing10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_primary);
        pDialog = new ProgressDialog(EditPrimaryActivity.this);

        Lyt1 = findViewById(R.id.LytGambar1);
        Lyt2 = findViewById(R.id.LytGambar2);
        Lyt3 = findViewById(R.id.LytGambar3);
        Lyt4 = findViewById(R.id.LytGambar4);
        Lyt5 = findViewById(R.id.LytGambar5);
        Lyt6 = findViewById(R.id.LytGambar6);
        Lyt7 = findViewById(R.id.LytGambar7);
        Lyt8 = findViewById(R.id.LytGambar8);
        Lyt9 = findViewById(R.id.LytGambar9);
        Lyt10 = findViewById(R.id.LytGambar10);

        BtnBatal = findViewById(R.id.BtnBatal);
        BtnSubmit = findViewById(R.id.BtnSubmit);
        BtnSelect1 = findViewById(R.id.BtnTambahGambar1);
        BtnSelect2 = findViewById(R.id.BtnTambahGambar2);
        BtnSelect3 = findViewById(R.id.BtnTambahGambar3);
        BtnSelect4 = findViewById(R.id.BtnTambahGambar4);
        BtnSelect5 = findViewById(R.id.BtnTambahGambar5);
        BtnSelect6 = findViewById(R.id.BtnTambahGambar6);
        BtnSelect7 = findViewById(R.id.BtnTambahGambar7);
        BtnSelect8 = findViewById(R.id.BtnTambahGambar8);
        BtnSelect9 = findViewById(R.id.BtnTambahGambar9);
        BtnSelect10 = findViewById(R.id.BtnTambahGambar10);

        IV1 = findViewById(R.id.IV1);
        IV2 = findViewById(R.id.IV2);
        IV3 = findViewById(R.id.IV3);
        IV4 = findViewById(R.id.IV4);
        IV5 = findViewById(R.id.IV5);
        IV6 = findViewById(R.id.IV6);
        IV7 = findViewById(R.id.IV7);
        IV8 = findViewById(R.id.IV8);
        IV9 = findViewById(R.id.IV9);
        IV10 = findViewById(R.id.IV10);
        IVDelete1 = findViewById(R.id.IVDelete1);
        IVDelete2 = findViewById(R.id.IVDelete2);
        IVDelete3 = findViewById(R.id.IVDelete3);
        IVDelete4 = findViewById(R.id.IVDelete4);
        IVDelete5 = findViewById(R.id.IVDelete5);
        IVDelete6 = findViewById(R.id.IVDelete6);
        IVDelete7 = findViewById(R.id.IVDelete7);
        IVDelete8 = findViewById(R.id.IVDelete8);
        IVDelete9 = findViewById(R.id.IVDelete9);
        IVDelete10 = findViewById(R.id.IVDelete10);

        ETJudul = findViewById(R.id.ETJudulListingPrimary);
        ETLokasi = findViewById(R.id.ETLokasiListingPrimary);
        ETHarga = findViewById(R.id.ETHargaListingPrimary);
        ETDeskripsi = findViewById(R.id.ETDeskripsiListingPrimary);
        ETKontak1 = findViewById(R.id.ETKontak1ListingPrimary);
        ETKontak2 = findViewById(R.id.ETKontak2ListingPrimary);

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        filePrimary1 = "Listing1_" + timeStamp + ".jpg";
        filePrimary2 = "Listing2_" + timeStamp + ".jpg";
        filePrimary3 = "Listing3_" + timeStamp + ".jpg";
        filePrimary4 = "Listing4_" + timeStamp + ".jpg";
        filePrimary5 = "Listing5_" + timeStamp + ".jpg";
        filePrimary6 = "Listing6_" + timeStamp + ".jpg";
        filePrimary7 = "Listing7_" + timeStamp + ".jpg";
        filePrimary8 = "Listing8_" + timeStamp + ".jpg";
        filePrimary9 = "Listing9_" + timeStamp + ".jpg";
        filePrimary10 = "Listing10_" + timeStamp + ".jpg";

        storageRef = FirebaseStorage.getInstance().getReference();
        ImgListing1 = storageRef.child("primary/" + filePrimary1);
        ImgListing2 = storageRef.child("primary/" + filePrimary2);
        ImgListing3 = storageRef.child("primary/" + filePrimary3);
        ImgListing4 = storageRef.child("primary/" + filePrimary4);
        ImgListing5 = storageRef.child("primary/" + filePrimary5);
        ImgListing6 = storageRef.child("primary/" + filePrimary6);
        ImgListing7 = storageRef.child("primary/" + filePrimary7);
        ImgListing8 = storageRef.child("primary/" + filePrimary8);
        ImgListing9 = storageRef.child("primary/" + filePrimary9);
        ImgListing10 = storageRef.child("primary/" + filePrimary10);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdListingPrimary = data.getStringExtra("IdListingPrimary");
        String intentJudulListingPrimary = data.getStringExtra("JudulListingPrimary");
        String intentHargaListingPrimary = data.getStringExtra("HargaListingPrimary");
        String intentDeskripsiListingPrimary = data.getStringExtra("DeskripsiListingPrimary");
        String intentAlamatListingPrimary = data.getStringExtra("AlamatListingPrimary");
        String intentLatitudeListingPrimary = data.getStringExtra("LatitudeListingPrimary");
        String intentLongitudeListingPrimary = data.getStringExtra("LongitudeListingPrimary");
        String intentLocationListingPrimary = data.getStringExtra("LocationListingPrimary");
        String intentKontakPerson1 = data.getStringExtra("KontakPerson1");
        String intentKontakPerson2 = data.getStringExtra("KontakPerson2");
        String intentImg1 = data.getStringExtra("Img1");
        String intentImg2 = data.getStringExtra("Img2");
        String intentImg3 = data.getStringExtra("Img3");
        String intentImg4 = data.getStringExtra("Img4");
        String intentImg5 = data.getStringExtra("Img5");
        String intentImg6 = data.getStringExtra("Img6");
        String intentImg7 = data.getStringExtra("Img7");
        String intentImg8 = data.getStringExtra("Img8");
        String intentImg9 = data.getStringExtra("Img9");
        String intentImg10 = data.getStringExtra("Img10");

        StrIdListingPrimary = intentIdListingPrimary;

        ETJudul.setText(intentJudulListingPrimary);
        ETHarga.setText(intentHargaListingPrimary);
        ETDeskripsi.setText(intentDeskripsiListingPrimary);
        ETLokasi.setText(intentAlamatListingPrimary);
        ETKontak1.setText(intentKontakPerson1);
        ETKontak2.setText(intentKontakPerson2);
        Gambar1 = intentImg1;
        Gambar2 = intentImg2;
        Gambar3 = intentImg3;
        Gambar4 = intentImg4;
        Gambar5 = intentImg5;
        Gambar6 = intentImg6;
        Gambar7 = intentImg7;
        Gambar8 = intentImg8;
        Gambar9 = intentImg9;
        Gambar10 = intentImg10;
        if (!intentImg1.equals("0")) {
            Picasso.get()
                    .load(intentImg1)
                    .into(IV1);
        } else {
            Lyt1.setVisibility(View.GONE);
            BtnSelect1.setVisibility(View.VISIBLE);
        }
        if (!intentImg2.equals("0")) {
            Picasso.get()
                    .load(intentImg2)
                    .into(IV2);
        } else {
            Lyt2.setVisibility(View.GONE);
            BtnSelect2.setVisibility(View.VISIBLE);
        }
        if (!intentImg3.equals("0")) {
            Picasso.get()
                    .load(intentImg3)
                    .into(IV3);
        } else {
            Lyt3.setVisibility(View.GONE);
            BtnSelect3.setVisibility(View.VISIBLE);
        }
        if (!intentImg4.equals("0")) {
            Picasso.get()
                    .load(intentImg4)
                    .into(IV4);
        } else {
            Lyt4.setVisibility(View.GONE);
            BtnSelect4.setVisibility(View.VISIBLE);
        }
        if (!intentImg5.equals("0")) {
            Picasso.get()
                    .load(intentImg5)
                    .into(IV5);
        } else {
            Lyt5.setVisibility(View.GONE);
            BtnSelect5.setVisibility(View.VISIBLE);
        }
        if (!intentImg6.equals("0")) {
            Picasso.get()
                    .load(intentImg6)
                    .into(IV6);
        } else {
            Lyt6.setVisibility(View.GONE);
            BtnSelect6.setVisibility(View.VISIBLE);
        }
        if (!intentImg7.equals("0")) {
            Picasso.get()
                    .load(intentImg7)
                    .into(IV7);
        } else {
            Lyt7.setVisibility(View.GONE);
            BtnSelect7.setVisibility(View.VISIBLE);
        }
        if (!intentImg8.equals("0")) {
            Picasso.get()
                    .load(intentImg8)
                    .into(IV8);
        } else {
            Lyt8.setVisibility(View.GONE);
            BtnSelect8.setVisibility(View.VISIBLE);
        }
        if (!intentImg9.equals("0")) {
            Picasso.get()
                    .load(intentImg9)
                    .into(IV9);
        } else {
            Lyt9.setVisibility(View.GONE);
            BtnSelect9.setVisibility(View.VISIBLE);
        }
        if (!intentImg10.equals("0")) {
            Picasso.get()
                    .load(intentImg10)
                    .into(IV10);
        } else {
            Lyt10.setVisibility(View.GONE);
            BtnSelect10.setVisibility(View.VISIBLE);
        }

        BtnSelect1.setOnClickListener(view -> showSelect1());
        BtnSelect2.setOnClickListener(view -> showSelect2());
        BtnSelect3.setOnClickListener(view -> showSelect3());
        BtnSelect4.setOnClickListener(view -> showSelect4());
        BtnSelect5.setOnClickListener(view -> showSelect5());
        BtnSelect6.setOnClickListener(view -> showSelect6());
        BtnSelect7.setOnClickListener(view -> showSelect7());
        BtnSelect8.setOnClickListener(view -> showSelect8());
        BtnSelect9.setOnClickListener(view -> showSelect9());
        BtnSelect10.setOnClickListener(view -> showSelect10());
        IVDelete1.setOnClickListener(view -> clearUri1());
        IVDelete2.setOnClickListener(view -> clearUri2());
        IVDelete3.setOnClickListener(view -> clearUri3());
        IVDelete4.setOnClickListener(view -> clearUri4());
        IVDelete5.setOnClickListener(view -> clearUri5());
        IVDelete6.setOnClickListener(view -> clearUri6());
        IVDelete7.setOnClickListener(view -> clearUri7());
        IVDelete8.setOnClickListener(view -> clearUri8());
        IVDelete9.setOnClickListener(view -> clearUri9());
        IVDelete10.setOnClickListener(view -> clearUri10());
        BtnBatal.setOnClickListener(view -> finish());
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImage1();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
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
        }  else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            }
        }  else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
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
        }  else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            }
        }  else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST3) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST3);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST4) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST4);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST5) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST5);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST6) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST6);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST7) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST7);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST8) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST8);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST9) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST9);
            } else {

            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST10) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST10);
            } else {

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
            IV1.setImageURI(Uri1);
            Lyt1.setVisibility(View.VISIBLE);
            BtnSelect1.setVisibility(View.GONE);
            BtnSelect2.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST2 && resultCode == RESULT_OK && data != null) {
            Uri2 = data.getData();
            IV2.setImageURI(Uri2);
            Lyt2.setVisibility(View.VISIBLE);
            BtnSelect2.setVisibility(View.GONE);
            BtnSelect3.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST3 && resultCode == RESULT_OK && data != null) {
            Uri3 = data.getData();
            IV3.setImageURI(Uri3);
            Lyt3.setVisibility(View.VISIBLE);
            BtnSelect3.setVisibility(View.GONE);
            BtnSelect4.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST4 && resultCode == RESULT_OK && data != null) {
            Uri4 = data.getData();
            IV4.setImageURI(Uri4);
            Lyt4.setVisibility(View.VISIBLE);
            BtnSelect4.setVisibility(View.GONE);
            BtnSelect5.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST5 && resultCode == RESULT_OK && data != null) {
            Uri5 = data.getData();
            IV5.setImageURI(Uri5);
            Lyt5.setVisibility(View.VISIBLE);
            BtnSelect5.setVisibility(View.GONE);
            BtnSelect6.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST6 && resultCode == RESULT_OK && data != null) {
            Uri6 = data.getData();
            IV6.setImageURI(Uri6);
            Lyt6.setVisibility(View.VISIBLE);
            BtnSelect6.setVisibility(View.GONE);
            BtnSelect7.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST7 && resultCode == RESULT_OK && data != null) {
            Uri7 = data.getData();
            IV7.setImageURI(Uri7);
            Lyt7.setVisibility(View.VISIBLE);
            BtnSelect7.setVisibility(View.GONE);
            BtnSelect8.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST8 && resultCode == RESULT_OK && data != null) {
            Uri8 = data.getData();
            IV8.setImageURI(Uri8);
            Lyt8.setVisibility(View.VISIBLE);
            BtnSelect8.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST9 && resultCode == RESULT_OK && data != null) {
            Uri9 = data.getData();
            IV9.setImageURI(Uri9);
            Lyt9.setVisibility(View.VISIBLE);
            BtnSelect9.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST10 && resultCode == RESULT_OK && data != null) {
            Uri10 = data.getData();
            IV10.setImageURI(Uri10);
            Lyt10.setVisibility(View.VISIBLE);
            BtnSelect10.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showSelect1() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES1);
        }
    }
    private void showSelect2() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE2);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES2);
        }
    }
    private void showSelect3() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE3);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES3);
        }
    }
    private void showSelect4() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE4);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES4);
        }
    }
    private void showSelect5() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE5);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES5);
        }
    }
    private void showSelect6() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE6);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES6);
        }
    }
    private void showSelect7() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE7);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES7);
        }
    }
    private void showSelect8() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE8);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES8);
        }
    }
    private void showSelect9() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE9);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES9);
        }
    }
    private void showSelect10() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE10);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES10);
        }
    }
    private void clearUri1() {
        Uri1 = null;
        Lyt1.setVisibility(View.GONE);
        BtnSelect1.setVisibility(View.VISIBLE);
    }
    private void clearUri2() {
        Uri2 = null;
        Lyt2.setVisibility(View.GONE);
        BtnSelect2.setVisibility(View.VISIBLE);
    }
    private void clearUri3() {
        Uri3 = null;
        Lyt3.setVisibility(View.GONE);
        BtnSelect3.setVisibility(View.VISIBLE);
    }
    private void clearUri4() {
        Uri4 = null;
        Lyt4.setVisibility(View.GONE);
        BtnSelect4.setVisibility(View.VISIBLE);
    }
    private void clearUri5() {
        Uri5 = null;
        Lyt5.setVisibility(View.GONE);
        BtnSelect5.setVisibility(View.VISIBLE);
    }
    private void clearUri6() {
        Uri6 = null;
        Lyt6.setVisibility(View.GONE);
        BtnSelect6.setVisibility(View.VISIBLE);
    }
    private void clearUri7() {
        Uri7 = null;
        Lyt7.setVisibility(View.GONE);
        BtnSelect7.setVisibility(View.VISIBLE);
    }
    private void clearUri8() {
        Uri8 = null;
        Lyt8.setVisibility(View.GONE);
        BtnSelect8.setVisibility(View.VISIBLE);
    }
    private void clearUri9() {
        Uri9 = null;
        Lyt9.setVisibility(View.GONE);
        BtnSelect9.setVisibility(View.VISIBLE);
    }
    private void clearUri10() {
        Uri10 = null;
        Lyt10.setVisibility(View.GONE);
        BtnSelect10.setVisibility(View.VISIBLE);
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
    private void handleImage1() {
        if (Uri1 != null) {
            showProgressDialog();
            ImgListing1.putFile(Uri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing1.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image1 = imageUrl;
                                        handleImage2();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage1();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage1();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image1 = Gambar1;
            handleImage2();
        }
    }
    private void handleImage2() {
        if (Uri2 != null) {
            showProgressDialog();
            ImgListing2.putFile(Uri2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing2.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image2 = imageUrl;
                                        handleImage3();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage2();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage2();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image2 = Gambar2;
            handleImage3();
        }
    }
    private void handleImage3() {
        if (Uri3 != null) {
            showProgressDialog();
            ImgListing3.putFile(Uri3)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing3.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image3 = imageUrl;
                                        handleImage4();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage3();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage3();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image3 = Gambar3;
            handleImage4();
        }
    }
    private void handleImage4() {
        if (Uri4 != null) {
            showProgressDialog();
            ImgListing4.putFile(Uri4)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing4.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image4 = imageUrl;
                                        handleImage5();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage4();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage4();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image4 = Gambar4;
            handleImage5();
        }
    }
    private void handleImage5() {
        if (Uri5 != null) {
            showProgressDialog();
            ImgListing5.putFile(Uri5)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing5.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image5 = imageUrl;
                                        handleImage6();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage5();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage5();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image5 = Gambar5;
            handleImage6();
        }
    }
    private void handleImage6() {
        if (Uri6 != null) {
            showProgressDialog();
            ImgListing6.putFile(Uri6)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing6.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image6 = imageUrl;
                                        handleImage7();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage6();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage6();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image6 = Gambar6;
            handleImage7();
        }
    }
    private void handleImage7() {
        if (Uri7 != null) {
            showProgressDialog();
            ImgListing7.putFile(Uri7)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing7.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image7 = imageUrl;
                                        handleImage8();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage7();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage7();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image7 = Gambar7;
            handleImage8();
        }
    }
    private void handleImage8() {
        if (Uri8 != null) {
            showProgressDialog();
            ImgListing8.putFile(Uri8)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing8.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image8 = imageUrl;
                                        handleImage9();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage8();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage8();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image8 = Gambar8;
            handleImage9();
        }
    }
    private void handleImage9() {
        if (Uri9 != null) {
            showProgressDialog();
            ImgListing9.putFile(Uri9)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing9.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image9 = imageUrl;
                                        handleImage10();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage9();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage9();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image9 = Gambar9;
            handleImage10();
        }
    }
    private void handleImage10() {
        if (Uri10 != null) {
            showProgressDialog();
            ImgListing10.putFile(Uri10)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing10.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        Image10 = imageUrl;
                                        HideProgressDialog();
                                        simpanData();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage10();
                                        Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage10();
                            Toast.makeText(EditPrimaryActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Image10 = Gambar10;
            HideProgressDialog();
            simpanData();
        }
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_PRIMARY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
                                Dialog customDialog = new Dialog(EditPrimaryActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Berhasil Update Primary");
                                cobalagi.setVisibility(View.GONE);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        finish();
                                    }
                                });

                                Glide.with(EditPrimaryActivity.this)
                                        .load(R.mipmap.ic_yes)
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
                                Dialog customDialog = new Dialog(EditPrimaryActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Gagal Update Primary");
                                ok.setVisibility(View.GONE);

                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                    }
                                });

                                Glide.with(EditPrimaryActivity.this)
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
                        Dialog customDialog = new Dialog(EditPrimaryActivity.this);
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

                        Glide.with(EditPrimaryActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                String Lokasi = "0";

                map.put("IdListingPrimary", StrIdListingPrimary);
                map.put("JudulListingPrimary", ETJudul.getText().toString());
                map.put("HargaListingPrimary", ETHarga.getText().toString());
                map.put("DeskripsiListingPrimary", ETDeskripsi.getText().toString());
                map.put("AlamatListingPrimary", ETLokasi.getText().toString());
                map.put("LatitudeListingPrimary", Lokasi);
                map.put("LongitudeListingPrimary", Lokasi);
                map.put("LocationListingPrimary", ETLokasi.getText().toString());
                map.put("KontakPerson1", ETKontak1.getText().toString());
                map.put("KontakPerson2", ETKontak2.getText().toString());
                map.put("Img1", Image1);
                map.put("Img2", Image2);
                map.put("Img3", Image3);
                map.put("Img4", Image4);
                map.put("Img5", Image5);
                map.put("Img6", Image6);
                map.put("Img7", Image7);
                map.put("Img8", Image8);
                map.put("Img9", Image9);
                map.put("Img10", Image10);
                System.out.println(map);
                return map;
            }
        };
        requestQueue.add(stringRequest);
    }
}