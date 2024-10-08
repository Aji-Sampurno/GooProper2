package com.gooproper.ui.tambah.info;

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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.ui.LocationActivity;
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

public class TambahInfoActivity extends AppCompatActivity {
    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST1 = 1;
    final int CODE_GALLERY_REQUEST2 = 2;
    final int KODE_REQUEST_KAMERA1 = 3;
    final int KODE_REQUEST_KAMERA2 = 4;
    final int CODE_CAMERA_REQUEST1 = 5;
    final int CODE_CAMERA_REQUEST2 = 6;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_1 = 7;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_1 = 8;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_2 = 9;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_2 = 10;
    private static final int MAPS_ACTIVITY_REQUEST_CODE = 11;
    private static final int STORAGE_PERMISSION_CODE = 12;
    private ArrayList<Uri> uriList = new ArrayList<>();
    Uri UriSelfie, UriProperty;
    TextInputEditText ETJenisProperty, ETStatusProperty, ETStatusNarahubung, ETNama, ETTelp, ETLTanah, ETLBangunan, ETSLTanah, ETSLBangunan, ETHJual, ETHSewa, ETKeterangan;
    TextInputLayout LytNarahubung, LytHJual, LytHSewa;
    LinearLayout LytSelfie, LytProperty;
    ImageView Back, IVSelfie, IVProperty;
    Button Batal, Submit, BtnSelfie, BtnProperty, BtnMap;
    ImageView IVDeleteSelfie, IVDeleteProperty;
    String IdAgen, IdNull, IdInput, NamaAgen;
    String ImageSelfie, ImageProperty, IsSpek, StrHJual, StrHSewa, StrHargaJual, StrHargaSewa;
    String Lat, Lng, token,StringLatitude, StringLongitude, StringAlamat, StringLokasi;
    String timeStamp,fileListing1,fileSelfie;
    private StorageReference mStorageRef;
    StorageReference storageRef,ImgListing1,ImgSelfie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_info);

        pDialog = new ProgressDialog(TambahInfoActivity.this);

        IVSelfie = findViewById(R.id.IVSelfie);
        IVProperty = findViewById(R.id.IVProperty);
        IVDeleteSelfie = findViewById(R.id.IVDeleteSelfie);
        IVDeleteProperty = findViewById(R.id.IVDeleteProperty);

        LytSelfie = findViewById(R.id.LytSelfie);
        LytProperty = findViewById(R.id.LytProperty);
        LytNarahubung = findViewById(R.id.LytNarahubungProperti);
        LytHJual = findViewById(R.id.lytharga);
        LytHSewa = findViewById(R.id.lythargasewa);

        Back = findViewById(R.id.backFormBtn);

        ETJenisProperty = findViewById(R.id.ETJenisProperti);
        ETStatusProperty = findViewById(R.id.ETStatusProperti);
        ETStatusNarahubung = findViewById(R.id.ETStatusNarahubung);
        ETNama = findViewById(R.id.ETNarahubungProperti);
        ETTelp = findViewById(R.id.ETTelpVendorProperti);
        ETLTanah = findViewById(R.id.etluastanah);
        ETLBangunan = findViewById(R.id.etluasbangunan);
        ETSLTanah = findViewById(R.id.etsatuanluastanah);
        ETSLBangunan = findViewById(R.id.etsatuanluasbangunan);
        ETHJual = findViewById(R.id.etharga);
        ETHSewa = findViewById(R.id.ethargasewa);
        ETKeterangan = findViewById(R.id.etketerangan);

        Batal = findViewById(R.id.btnbatal);
        Submit = findViewById(R.id.btnsubmit);
        BtnMap = findViewById(R.id.map);
        BtnSelfie = findViewById(R.id.BtnSelfie);
        BtnProperty = findViewById(R.id.BtnProperty);

        IdAgen = Preferences.getKeyIdAgen(TambahInfoActivity.this);
        IdNull = "0";
        NamaAgen = Preferences.getKeyUsername(TambahInfoActivity.this);

        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        fileListing1 = "Listing1_" + timeStamp + ".jpg";
        fileSelfie = NamaAgen + "_Selfie_" + timeStamp + ".jpg";

        storageRef = FirebaseStorage.getInstance().getReference();
        ImgListing1 = storageRef.child("listing/" + fileListing1);
        ImgSelfie = storageRef.child("selfie/" + fileSelfie);

        Submit.setOnClickListener(view -> {
            if (Validate()) {
                handleImage1Success();
            }
        });
        BtnMap.setOnClickListener(view -> startMapsActivityForResult());
        Back.setOnClickListener(view -> finish());
        Batal.setOnClickListener(view -> finish());
        IVDeleteSelfie.setOnClickListener(view -> ClearSelfie());
        IVDeleteProperty.setOnClickListener(view -> ClearProperty());
        BtnSelfie.setOnClickListener(view -> requestPermissionsSelfie());
        BtnProperty.setOnClickListener(view -> showPhotoProperty());
        ETStatusProperty.setOnClickListener(view -> ShowStatus(view));
        ETJenisProperty.setOnClickListener(view -> ShowJenisProperti(view));
        ETStatusNarahubung.setOnClickListener(view -> ShowStatusNarahubung(view));
        ETSLTanah.setOnClickListener(view -> ShowSatuanLTanah(view));
        ETSLBangunan.setOnClickListener(view -> ShowSatuanLBangunan(view));

        ETHJual.addTextChangedListener(new TextWatcher() {
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
                    ETHJual.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Rp,.\\s]", "");
                    if (!cleanString.isEmpty()) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = String.format(Locale.US, "%,.0f", parsed);
                        StrHJual = cleanString;
                        current = formatted;
                        ETHJual.setText(formatted);
                        ETHJual.setSelection(formatted.length());
                    } else {
                        ETHJual.setText("");
                        StrHJual = "0";
                    }
                    ETHJual.addTextChangedListener(this);
                }
            }
        });
        ETHSewa.addTextChangedListener(new TextWatcher() {
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
                    ETHSewa.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Rp,.\\s]", "");
                    if (!cleanString.isEmpty()) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = String.format(Locale.US, "%,.0f", parsed);
                        StrHSewa = cleanString;
                        current = formatted;
                        ETHSewa.setText(formatted);
                        ETHSewa.setSelection(formatted.length());
                    } else {
                        ETHSewa.setText("");
                        StrHSewa = "0";
                    }
                    ETHSewa.addTextChangedListener(this);
                }
            }
        });
        ETStatusProperty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Jual")) {
                    LytHJual.setVisibility(View.VISIBLE);
                    LytHSewa.setVisibility(View.GONE);
                } else if (editable.toString().equalsIgnoreCase("Sewa")) {
                    LytHSewa.setVisibility(View.VISIBLE);
                    LytHJual.setVisibility(View.GONE);
                } else if (editable.toString().equalsIgnoreCase("Jual/Sewa")) {
                    LytHJual.setVisibility(View.VISIBLE);
                    LytHSewa.setVisibility(View.VISIBLE);
                } else {
                    LytHJual.setVisibility(View.GONE);
                    LytHSewa.setVisibility(View.GONE);
                }
            }
        });
        ETStatusNarahubung.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Agen Kantor Lain")) {
                    LytNarahubung.setVisibility(View.VISIBLE);
                } else if (editable.toString().equalsIgnoreCase("Broker Tradisional")) {
                    LytNarahubung.setVisibility(View.VISIBLE);
                } else if (editable.toString().equalsIgnoreCase("Owner")) {
                    LytNarahubung.setVisibility(View.VISIBLE);
                } else {
                    LytNarahubung.setVisibility(View.GONE);
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
    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    private void showPhotoSelfie() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahInfoActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST1);
                                break;
                            case 1:
                                requestPermissionsSelfie();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void showPhotoProperty() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(TambahInfoActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST2);
                                break;
                            case 1:
                                requestPermissionsProperty();
                                break;
                        }
                    }
                });

        builder.show();
    }
    private void requestPermissionsSelfie() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_1);
        }
    }
    private void requestPermissionsProperty() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_2);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_2);
        }
    }
    private void BukaKameraSelfie() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriSelfie = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriSelfie);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA1);
            }
        }
    }
    private void BukaKameraProperty() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriProperty = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriProperty);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA2);
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

        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            }
        }else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST2);
            }
        } else if (requestCode == CODE_CAMERA_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                BukaKameraSelfie();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahInfoActivity.this);
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
                BukaKameraProperty();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahInfoActivity.this);
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

                StringLatitude = data.getStringExtra("latitude");
                StringLongitude = data.getStringExtra("longitude");
                StringAlamat = data.getStringExtra("address");
                StringLokasi = data.getStringExtra("lokasi");

            }
        }

        if (requestCode == CODE_GALLERY_REQUEST1 && resultCode == RESULT_OK && data != null) {
            UriSelfie = data.getData();
            IVSelfie.setImageURI(UriSelfie);
            LytSelfie.setVisibility(View.VISIBLE);
            BtnSelfie.setVisibility(View.GONE);
        } else if (requestCode == CODE_GALLERY_REQUEST2 && resultCode == RESULT_OK && data != null) {
            UriProperty = data.getData();
            IVProperty.setImageURI(UriProperty);
            LytProperty.setVisibility(View.VISIBLE);
            BtnProperty.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA1 && resultCode == RESULT_OK) {
            IVSelfie.setImageURI(UriSelfie);
            LytSelfie.setVisibility(View.VISIBLE);
            BtnSelfie.setVisibility(View.GONE);
        } else if (requestCode == KODE_REQUEST_KAMERA2 && resultCode == RESULT_OK) {
            IVProperty.setImageURI(UriProperty);
            LytProperty.setVisibility(View.VISIBLE);
            BtnProperty.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void ClearSelfie() {
        if (UriSelfie != null) {
            UriSelfie = null;
            LytSelfie.setVisibility(View.GONE);
            BtnSelfie.setVisibility(View.VISIBLE);
        }
    }
    private void ClearProperty() {
        if (UriProperty != null) {
            UriProperty = null;
            LytProperty.setVisibility(View.GONE);
            BtnProperty.setVisibility(View.VISIBLE);
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
        if (UriProperty != null) {
            showProgressDialog();
            ImgListing1.putFile(UriProperty)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgListing1.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        ImageProperty = imageUrl;
                                        handleImage2Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage1Success();
                                        Toast.makeText(TambahInfoActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage1Success();
                            Toast.makeText(TambahInfoActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            ImageProperty = "0";
            handleImage2Success();
        }
    }
    private void handleImage2Success() {
        if (UriSelfie != null) {
            ImgSelfie.putFile(UriSelfie)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgSelfie.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        ImageSelfie = imageUrl;
                                        HideProgressDialog();
                                        simpanData();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage2Success();
                                        Toast.makeText(TambahInfoActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage2Success();
                            Toast.makeText(TambahInfoActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            ImageSelfie = "0";
            HideProgressDialog();
            simpanData();
        }
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_ADD_DATA_INFO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
                                Dialog customDialog = new Dialog(TambahInfoActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Berhasil Menambahkan Info Property");
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
                                                // Tangani kesalahan jika terjadi
                                            }
                                        });
                                        requestQueue.add(jsonArrayRequest);
                                        customDialog.dismiss();
                                        finish();
                                    }
                                });

                                Glide.with(TambahInfoActivity.this)
                                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
                                Dialog customDialog = new Dialog(TambahInfoActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Gagal Menambahkan Info Property");
                                ok.setVisibility(View.GONE);

                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                    }
                                });

                                Glide.with(TambahInfoActivity.this)
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
                        Dialog customDialog = new Dialog(TambahInfoActivity.this);
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

                        Glide.with(TambahInfoActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (IdAgen.isEmpty()) {
                    IdInput = IdNull;
                } else {
                    IdInput = IdAgen;
                }
                if (StringLatitude == null) {
                    Lat = "0";
                } else {
                    Lat = StringLatitude;
                }
                if (StringLongitude == null) {
                    Lng = "0";
                } else {
                    Lng = StringLongitude;
                }
                if (StrHJual == null){
                    StrHargaJual = "0";
                } else {
                    StrHargaJual = StrHJual;
                }
                if (StrHSewa == null){
                    StrHargaSewa = "0";
                } else {
                    StrHargaSewa = StrHSewa;
                }

                String tanah = ETLTanah.getText().toString() + " " + ETSLTanah.getText().toString();
                String bangunan = ETLBangunan.getText().toString() + " " + ETSLBangunan.getText().toString();

                map.put("IdAgen", IdInput);
                map.put("JenisProperty", ETJenisProperty.getText().toString());
                map.put("StatusProperty", ETStatusProperty.getText().toString());
                map.put("StatusNarahubung", ETStatusNarahubung.getText().toString());
                map.put("Narahubung", ETNama.getText().toString());
                map.put("NoTelp", ETTelp.getText().toString());
                map.put("LuasTanah", tanah);
                map.put("LuasBangunan", bangunan);
                map.put("Keterangan", ETKeterangan.getText().toString());
                map.put("HargaJual", StrHargaJual);
                map.put("HargaSewa", StrHargaSewa);
                map.put("Lokasi", StringLokasi);
                map.put("Alamat", StringAlamat);
                map.put("Latitude", Lat);
                map.put("Longitude", Lng);
                map.put("ImgSelfie", ImageSelfie);
                map.put("ImgProperty", ImageProperty);
                map.put("IsSpek", IsSpek);
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
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
                ETJenisProperty.setText(JenisProperti[SelectedJenisProperti[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

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
                ETStatusProperty.setText(Status[SelectedStatus[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }
    public void ShowStatusNarahubung(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Status Narahubung");

        final CharSequence[] Status = {"Agen Kantor Lain", "Broker Tradisional", "Owner"};
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
                ETStatusNarahubung.setText(Status[SelectedStatus[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }
    public void ShowSatuanLTanah(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Satuan Luas");

        final CharSequence[] Status = {"m²", "are", "ha", "m² (Semigros)"};
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
                ETSLTanah.setText(Status[SelectedStatus[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }
    public void ShowSatuanLBangunan(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Satuan Luas");

        final CharSequence[] Status = {"m²", "are", "ha", "m² (Semigros)"};
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
                ETSLBangunan.setText(Status[SelectedStatus[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();

        dialog.show();
    }
    public boolean Validate() {
        if (ETJenisProperty.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(TambahInfoActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Pilih Jenis Properti");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahInfoActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (ETStatusProperty.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(TambahInfoActivity.this);
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

            Glide.with(TambahInfoActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (ETStatusNarahubung.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(TambahInfoActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Pilih Status Narahubung");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahInfoActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (ETNama.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(TambahInfoActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Masukkan Nama Narahubung");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahInfoActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (ETTelp.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(TambahInfoActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Masukkan Nama");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahInfoActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (ETLTanah.getText().toString().equals("") && ETLBangunan.getText().toString().equals("") && ETHJual.getText().toString().equals("") && ETHSewa.getText().toString().equals("") && ETKeterangan.getText().toString().equals("")) {
            IsSpek = "0";
        } else {
            IsSpek = "1";
        }
        if (UriProperty == null) {
            Dialog customDialog = new Dialog(TambahInfoActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Foto Property");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahInfoActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (StringLatitude == null && StringLongitude == null) {
            Dialog customDialog = new Dialog(TambahInfoActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Tambahkan Lokasi Property");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahInfoActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        return true;
    }
    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "infoproperty");
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
        String message = "Menambahkan Info Properti Baru";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}