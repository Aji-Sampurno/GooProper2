package com.gooproper.ui.officer;

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

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

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

public class TambahCekLokasiActivity extends AppCompatActivity {

    private ProgressDialog PDialog;
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
    TextInputEditText ETKeterangan;
    Button BtnLokasi, BtnSelfie, BtnProperty, BtnBatal, BtnSubmit;
    LinearLayout LytSelfie, LytProperty;
    ImageView IVBack, IVSelfie, IVProperty, IVDeleteSelfie, IVDeleteProperty;
    String StrIdListing, StrIdAgen, StrNamaAgen, StrIdInput, StrLatitude, StrLongitude, StrLokasi, StrAlamat, StrLat, StrLng, StrAl, StrLok, StrKinerja, StrImageSelfie, StrImageProperty, StrSelfieImg, StrPropertyImg;
    Uri UriSelfie, UriProperty;
    String StrTimeStamp,StrFileSelfie,StrFileProperty;
    private StorageReference mStorageRef;
    StorageReference storageRef,ImgSelfie,ImgProperty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_cek_lokasi);

        PDialog = new ProgressDialog(TambahCekLokasiActivity.this);

        ETKeterangan = findViewById(R.id.ETKetReportCek);

        BtnLokasi = findViewById(R.id.BtnLokasiCek);
        BtnSelfie = findViewById(R.id.BtnSelfieCek);
        BtnProperty = findViewById(R.id.BtnPropertyCek);
        BtnBatal = findViewById(R.id.BtnBatalCek);
        BtnSubmit = findViewById(R.id.BtnSimpanCek);

        LytSelfie = findViewById(R.id.LytSelfieCek);
        LytProperty = findViewById(R.id.LytPropertyCek);

        IVBack = findViewById(R.id.IVBackCek);
        IVProperty = findViewById(R.id.IVPropertyCek);
        IVSelfie = findViewById(R.id.IVSelfieCek);
        IVDeleteProperty = findViewById(R.id.IVDeletePropertyCek);
        IVDeleteSelfie = findViewById(R.id.IVDeleteSelfieCek);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdListing = data.getStringExtra("IdListing");

        StrIdListing = intentIdListing;

        StrIdAgen = Preferences.getKeyIdAgen(TambahCekLokasiActivity.this);
        StrNamaAgen = Preferences.getKeyUsername(TambahCekLokasiActivity.this);
        StrKinerja = "Recheck";

        StrTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        StrFileProperty = StrNamaAgen + "Listing1_" + StrTimeStamp + ".jpg";
        StrFileSelfie = StrNamaAgen + "_Selfie_" + StrTimeStamp + ".jpg";

        storageRef = FirebaseStorage.getInstance().getReference();
        ImgProperty = storageRef.child("property/" + StrFileProperty);
        ImgSelfie = storageRef.child("selfie/" + StrFileSelfie);

        IVBack.setOnClickListener(view -> finish());
        IVDeleteSelfie.setOnClickListener(view -> ClearSelfie());
        IVDeleteProperty.setOnClickListener(view -> ClearProperty());

        BtnSelfie.setOnClickListener(view -> requestPermissionsSelfie());
        BtnProperty.setOnClickListener(view -> showPhotoProperty());
        BtnLokasi.setOnClickListener(view -> startMapsActivityForResult());
        BtnBatal.setOnClickListener(view -> finish());
        BtnSubmit.setOnClickListener(view -> {
            if (ValidateLokasi()) {
                handleImage1Success();
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
                                ActivityCompat.requestPermissions(TambahCekLokasiActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST1);
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
                                ActivityCompat.requestPermissions(TambahCekLokasiActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST2);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahCekLokasiActivity.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(TambahCekLokasiActivity.this);
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

                StrLatitude = data.getStringExtra("latitude");
                StrLongitude = data.getStringExtra("longitude");
                StrAlamat = data.getStringExtra("address");
                StrLokasi = data.getStringExtra("lokasi");
                BtnLokasi.setVisibility(View.GONE);

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
        PDialog.setMessage("Unggah Gambar");
        PDialog.setCancelable(false);
        PDialog.show();
    }
    private void HideProgressDialog() {
        PDialog.dismiss();
        PDialog.cancel();
    }
    private void handleImage1Success() {
        if (UriProperty != null) {
            showProgressDialog();
            ImgProperty.putFile(UriProperty)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgProperty.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        StrImageProperty = imageUrl;
                                        handleImage2Success();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage1Success();
                                        Toast.makeText(TambahCekLokasiActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage1Success();
                            Toast.makeText(TambahCekLokasiActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            StrImageProperty = "0";
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
                                        StrImageSelfie = imageUrl;
                                        HideProgressDialog();
                                        simpanData();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImage2Success();
                                        Toast.makeText(TambahCekLokasiActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImage2Success();
                            Toast.makeText(TambahCekLokasiActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            StrImageSelfie = "0";
            HideProgressDialog();
            simpanData();
        }
    }
    private void simpanData() {
        PDialog.setMessage("Menyimpan Data");
        PDialog.setCancelable(false);
        PDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_CEK_LOKASI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
                                Dialog customDialog = new Dialog(TambahCekLokasiActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Berhasil Menambahkan Report");
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

                                Glide.with(TambahCekLokasiActivity.this)
                                        .load(R.mipmap.ic_yes)
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
                                Dialog customDialog = new Dialog(TambahCekLokasiActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Gagal Menambahkan Report");
                                ok.setVisibility(View.GONE);

                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                    }
                                });

                                Glide.with(TambahCekLokasiActivity.this)
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
                        PDialog.cancel();
                        Dialog customDialog = new Dialog(TambahCekLokasiActivity.this);
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

                        Glide.with(TambahCekLokasiActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (StrIdAgen.isEmpty()) {
                    StrIdInput = "0";
                } else {
                    StrIdInput = StrIdAgen;
                }
                if (StrLatitude == null) {
                    StrLat = "0";
                } else {
                    StrLat = StrLatitude;
                }
                if (StrLongitude == null) {
                    StrLng = "0";
                } else {
                    StrLng = StrLongitude;
                }
                if (StrAlamat == null) {
                    StrAl = "0";
                } else {
                    StrAl = StrAlamat;
                }
                if (StrLokasi == null) {
                    StrLok = "0";
                } else {
                    StrLok = StrLokasi;
                }
                if (StrImageSelfie == null) {
                    StrSelfieImg = "0";
                } else {
                    StrSelfieImg = StrImageSelfie;
                }
                if (StrImageProperty == null) {
                    StrPropertyImg = "0";
                } else {
                    StrPropertyImg = StrImageProperty;
                }

                map.put("IdAgen", StrIdInput);
                map.put("IdListing", StrIdListing);
                map.put("Kinerja", StrKinerja);
                map.put("Keterangan", ETKeterangan.getText().toString());
                map.put("Lokasi", StrLok);
                map.put("Alamat", StrAl);
                map.put("Latitude", StrLat);
                map.put("Longitude", StrLng);
                map.put("ImgSelfie", StrSelfieImg);
                map.put("ImgProperty", StrPropertyImg);
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    public boolean ValidateLokasi() {
        if (ETKeterangan.getText().toString().equals("")) {
            Dialog customDialog = new Dialog(TambahCekLokasiActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Masukkan Keterangan Report");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahCekLokasiActivity.this)
                    .load(R.drawable.alert)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (UriProperty == null) {
            Dialog customDialog = new Dialog(TambahCekLokasiActivity.this);
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

            Glide.with(TambahCekLokasiActivity.this)
                    .load(R.drawable.alert)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (UriSelfie == null) {
            Dialog customDialog = new Dialog(TambahCekLokasiActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Foto Selfie");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahCekLokasiActivity.this)
                    .load(R.drawable.alert)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }
        if (StrLatitude == null && StrLongitude == null) {
            Dialog customDialog = new Dialog(TambahCekLokasiActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Tambahkan Lokasi");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(TambahCekLokasiActivity.this)
                    .load(R.drawable.alert)
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
                sendNotificationToToken(token, "report");
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
        String message = "Menambahkan Report Kinerja";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}