package com.gooproper.ui.detail.followup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.adapter.followup.UpdateFlowUpAdapter;
import com.gooproper.model.UpdateFlowUpModel;
import com.gooproper.ui.followup.FollowUpActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DetailFollowUpActivity extends AppCompatActivity {

    ProgressDialog PDFollowUp;
    TextView TVNamaListing, TVAlamatListing, TVNamaBuyer, TVWaBuyer, TVSumber, TVTanggal, TVJam, TVStatus;
    CheckBox CBChat, CBSurvei, CBTawar, CBLokasi, CBDeal;
    ImageView IVBack, IVSelfie;
    Button BtnBatal, BtnUpdate, BtnSelfie, BtnDelete, BtnClose;
    Bitmap BitmapSelfie;
    Drawable DrawableSelfie;
    String StringIdFollowUp, StringSelfie, Selfie, StrStatus;
    Uri UriSelfie;
    RecyclerView RVUpdateFollowUp;
    RecyclerView.Adapter adapter;
    List<UpdateFlowUpModel> list;
    final int CODE_GALLERY_REQUEST_Selfie = 46;
    final int CODE_CAMERA_REQUEST_Selfie = 47;
    final int KODE_REQUEST_KAMERA_Selfie = 48;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SELFIE = 92;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SELFIE = 93;
    private static final int STORAGE_PERMISSION_CODE = 97;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_follow_up);

        PDFollowUp = new ProgressDialog(DetailFollowUpActivity.this);

        TVNamaListing = findViewById(R.id.TVNamaListingDetailFollowUp);
        TVAlamatListing = findViewById(R.id.TVAlamatListingDetailFollowUp);
        TVNamaBuyer = findViewById(R.id.TVNamaBuyerDetailFollowUp);
        TVWaBuyer = findViewById(R.id.TVWaBuyerDetailFollowUp);
        TVSumber = findViewById(R.id.TVSumberBuyerDetailFollowUp);
        TVTanggal = findViewById(R.id.TVTanggalDetailFollowUp);
        TVJam = findViewById(R.id.TVJamDetailFollowUp);
        TVStatus = findViewById(R.id.TVStatusDetailFollowUp);

        RVUpdateFollowUp = findViewById(R.id.RVUpdateFlowUp);

        CBChat = findViewById(R.id.CBChat);
        CBSurvei = findViewById(R.id.CBSurvei);
        CBTawar = findViewById(R.id.CBTawar);
        CBLokasi = findViewById(R.id.CBLokasi);
        CBDeal = findViewById(R.id.CBDeal);

        IVSelfie = findViewById(R.id.IVSurvei);
        IVBack = findViewById(R.id.IVBackDetailFollowUp);

        BtnBatal = findViewById(R.id.BtnBatalBuyer);
        BtnUpdate = findViewById(R.id.BtnSubmitBuyer);
        BtnSelfie = findViewById(R.id.BtnSelfie);
        BtnDelete = findViewById(R.id.BtnDelete);
        BtnClose = findViewById(R.id.BtnCloseFollowUp);

        DrawableSelfie = IVSelfie.getDrawable();

        list = new ArrayList<>();

        RVUpdateFollowUp.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        adapter = new UpdateFlowUpAdapter(DetailFollowUpActivity.this, list);
        RVUpdateFollowUp.setAdapter(adapter);

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intentIdFlowup = data.getStringExtra("IdFlowup");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentIdInput = data.getStringExtra("IdInput");
        String intentIdListing = data.getStringExtra("IdListing");
        String intentNamaBuyer = data.getStringExtra("NamaBuyer");
        String intentTelpBuyer = data.getStringExtra("TelpBuyer");
        String intentSumberBuyer = data.getStringExtra("SumberBuyer");
        String intentTanggal = data.getStringExtra("Tanggal");
        String intentJam = data.getStringExtra("Jam");
        String intentKeterangan = data.getStringExtra("Keterangan");
        String intentChat = data.getStringExtra("Chat");
        String intentSurvei = data.getStringExtra("Survei");
        String intentTawar = data.getStringExtra("Tawar");
        String intentLokasi = data.getStringExtra("Lokasi");
        String intentDeal = data.getStringExtra("Deal");
        String intentSelfie = data.getStringExtra("Selfie");
        String intentNamaListing = data.getStringExtra("NamaListing");
        String intentAlamat = data.getStringExtra("Alamat");
        String intentLatitude = data.getStringExtra("Latitude");
        String intentLongitude = data.getStringExtra("Longitude");
        String intentHarga = data.getStringExtra("Harga");
        String intentNamaAgen = data.getStringExtra("NamaAgen");
        String intentTelpAgen = data.getStringExtra("TelpAgen");
        String intentNamaInput = data.getStringExtra("NamaInput");
        String intentTelpInput = data.getStringExtra("TelpInput");
        String intentImg1 = data.getStringExtra("Img1");
        String intentImg2 = data.getStringExtra("Img2");
        String intentImg3 = data.getStringExtra("Img3");
        String intentImg4 = data.getStringExtra("Img4");
        String intentImg5 = data.getStringExtra("Img5");
        String intentImg6 = data.getStringExtra("Img6");
        String intentImg7 = data.getStringExtra("Img7");
        String intentImg8 = data.getStringExtra("Img8");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileSelfie = "Selfie_" + timeStamp + ".jpg";

        StringIdFollowUp = intentIdFlowup;
        StringSelfie = intentSelfie;

        StrStatus = Preferences.getKeyStatus(this);

        GetUpdateFlowup();

        if (StrStatus.equals("2")) {
            BtnClose.setVisibility(View.VISIBLE);
        } else {
            BtnClose.setVisibility(View.GONE);
        }

        if (!intentSelfie.equals("0") && !intentSelfie.isEmpty()){
            Picasso.get()
                    .load(intentSelfie)
                    .into(IVSelfie);
        }

        TVNamaListing.setText(intentNamaListing);
        TVAlamatListing.setText(intentAlamat);
        TVNamaBuyer.setText(intentNamaBuyer);
        TVWaBuyer.setText(intentTelpBuyer);
        TVSumber.setText(intentSumberBuyer);
        TVTanggal.setText(intentTanggal);
        TVJam.setText(intentJam);

        if (intentDeal.equals("1")){
            TVStatus.setText("Deal");
        } else if (intentLokasi.equals("1")) {
            TVStatus.setText("Cari Lokasi Lain");
        } else if (intentTawar.equals("1")) {
            TVStatus.setText("Tawar");
        } else if (intentSurvei.equals("1")) {
            TVStatus.setText("Survei");
            IVSelfie.setVisibility(View.VISIBLE);
        } else if (intentChat.equals("1")){
            TVStatus.setText("Chat");
        }

        /*if (!intentSelfie.equals("0")){
            IVSelfie.setVisibility(View.VISIBLE);
            Glide.with(DetailFollowUpActivity.this)
                    .load(intentSelfie)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(IVSelfie);
        }*/

        if (intentDeal.equals("1")){
            CBDeal.setChecked(true);
            CBDeal.setClickable(false);
        }
        if (intentLokasi.equals("1")) {
            CBLokasi.setChecked(true);
            CBLokasi.setClickable(false);
        }
        if (intentTawar.equals("1")) {
            CBTawar.setChecked(true);
            CBTawar.setClickable(false);
        }
        if (intentSurvei.equals("1")) {
            CBSurvei.setChecked(true);
            CBSurvei.setClickable(false);
            IVSelfie.setVisibility(View.VISIBLE);
            Glide.with(DetailFollowUpActivity.this)
                    .load(intentSelfie)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(IVSelfie);
        }
        if (intentChat.equals("1")){
            CBChat.setChecked(true);
            CBChat.setClickable(false);
        }

        CBSurvei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    BtnSelfie.setVisibility(View.VISIBLE);
                } else {
                    BtnSelfie.setVisibility(View.GONE);
                }
            }
        });
        BtnDelete.setOnClickListener(v -> clearBitmapSelfie());
        IVBack.setOnClickListener(view -> finish());
        BtnSelfie.setOnClickListener(view -> requestPermissionsSelfie());
        BtnBatal.setOnClickListener(view -> finish());
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validate()) {
                    PDFollowUp.setMessage("Menyimpan Data");
                    PDFollowUp.setCancelable(false);
                    PDFollowUp.show();

                    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                    StorageReference ImgSelfie = storageRef.child("selfie/" + fileSelfie);
                    List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();

                    if (UriSelfie != null) {
                        StorageTask<UploadTask.TaskSnapshot> task1 = ImgSelfie.putFile(UriSelfie)
                                .addOnSuccessListener(taskSnapshot -> {
                                    ImgSelfie.getDownloadUrl()
                                            .addOnSuccessListener(uri -> {
                                                String imageUrl = uri.toString();
                                                Selfie = imageUrl;
                                            })
                                            .addOnFailureListener(exception -> {
                                            });
                                })
                                .addOnFailureListener(exception -> {
                                });
                        uploadTasks.add(task1);
                    } else {
                        Selfie = StringSelfie;
                    }
                    Tasks.whenAllSuccess(uploadTasks)
                            .addOnSuccessListener(results -> {
                                PDFollowUp.cancel();
                                AddFlowup();
                            })
                            .addOnFailureListener(exception -> {
                                Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
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

                                Glide.with(DetailFollowUpActivity.this)
                                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifImageView);

                                customDialog.show();
                            });
                }
            }
        });
        BtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CloseFlowup();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void requestPermissionsSelfie() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SELFIE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SELFIE);
        }
    }
    private void bukaKamera() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriSelfie = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriSelfie);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_Selfie);
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

        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_SELFIE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_Selfie);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_SELFIE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_Selfie);
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {} else {}
        } else if (requestCode == CODE_GALLERY_REQUEST_Selfie) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_Selfie);
            } else {
                Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
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

                Glide.with(DetailFollowUpActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_Selfie) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailFollowUpActivity.this);
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
        if (requestCode == CODE_GALLERY_REQUEST_Selfie && resultCode == RESULT_OK && data != null) {
            UriSelfie = data.getData();
            IVSelfie.setImageURI(UriSelfie);
            IVSelfie.setVisibility(View.VISIBLE);
            BtnDelete.setVisibility(View.VISIBLE);
            BtnSelfie.setText("Ganti Foto Selfie");
        } else if (requestCode == KODE_REQUEST_KAMERA_Selfie && resultCode == RESULT_OK) {
            IVSelfie.setImageURI(UriSelfie);
            IVSelfie.setVisibility(View.VISIBLE);
            BtnDelete.setVisibility(View.VISIBLE);
            BtnSelfie.setText("Ganti Foto Selfie");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void clearBitmapSelfie() {
        if (BitmapSelfie != null && !BitmapSelfie.isRecycled()) {
            BitmapSelfie.recycle();
            BitmapSelfie = null;
            BtnDelete.setVisibility(View.GONE);
            BtnSelfie.setVisibility(View.VISIBLE);
        } else {
            UriSelfie = null;
            IVSelfie.setVisibility(View.GONE);
            BtnDelete.setVisibility(View.GONE);
            BtnSelfie.setVisibility(View.VISIBLE);
        }
    }
    private void AddFlowup() {
        PDFollowUp.setMessage("Menyimpan Data...");
        PDFollowUp.setCancelable(false);
        PDFollowUp.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_FLOWUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDFollowUp.cancel();
                try {
                    JSONObject res = new JSONObject(response);
                    String status = res.getString("Status");
                    if (status.equals("Sukses")) {
                        Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Berhasil Update Follow Up");
                        cobalagi.setVisibility(View.GONE);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                                finish();
                            }
                        });

                        Glide.with(DetailFollowUpActivity.this)
                                .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    } else if (status.equals("Error")) {
                        Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Gagal Update Follow Up");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(DetailFollowUpActivity.this)
                                .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDFollowUp.cancel();
                Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
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

                Glide.with(DetailFollowUpActivity.this)
                        .load(R.mipmap.ic_eror_network_foreground)
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

                final String StringChat = CBChat.isChecked()?"1":"0";
                final String StringSurvei = CBSurvei.isChecked()?"1":"0";
                final String StringTawar = CBTawar.isChecked()?"1":"0";
                final String StringLokasi = CBLokasi.isChecked()?"1":"0";
                final String StringDeal = CBDeal.isChecked()?"1":"0";

                map.put("IdFlowup", StringIdFollowUp);
                map.put("Chat", StringChat);
                map.put("Survei", StringSurvei);
                map.put("Tawar", StringTawar);
                map.put("Lokasi", StringLokasi);
                map.put("Deal", StringDeal);
                map.put("Selfie", Selfie);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void CloseFlowup() {
        PDFollowUp.setMessage("Menyimpan Data...");
        PDFollowUp.setCancelable(false);
        PDFollowUp.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_CLOSE_FLOWUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDFollowUp.cancel();
                try {
                    JSONObject res = new JSONObject(response);
                    String status = res.getString("Status");
                    if (status.equals("Sukses")) {
                        Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Berhasil Update Follow Up");
                        cobalagi.setVisibility(View.GONE);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                                finish();
                            }
                        });

                        Glide.with(DetailFollowUpActivity.this)
                                .load(R.mipmap.ic_yes)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    } else if (status.equals("Error")) {
                        Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Gagal Update Follow Up");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(DetailFollowUpActivity.this)
                                .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDFollowUp.cancel();
                Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
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

                Glide.with(DetailFollowUpActivity.this)
                        .load(R.mipmap.ic_eror_network_foreground)
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

                map.put("IdFlowup", StringIdFollowUp);
                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
    private void GetUpdateFlowup() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_UPDATE_FLOWUP + StringIdFollowUp, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                UpdateFlowUpModel md = new UpdateFlowUpModel();
                                md.setIdFlowup(data.getString("IdFlowup"));
                                md.setTanggal(data.getString("Tanggal"));
                                md.setJam(data.getString("Jam"));
                                md.setChat(data.getString("Chat"));
                                md.setSurvei(data.getString("Survei"));
                                md.setTawar(data.getString("Tawar"));
                                md.setLokasi(data.getString("Lokasi"));
                                md.setDeal(data.getString("Deal"));
                                list.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
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
                                        customDialog.dismiss();
                                        GetUpdateFlowup();
                                    }
                                });

                                batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                    }
                                });

                                customDialog.show();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
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
                                customDialog.dismiss();
                                GetUpdateFlowup();
                            }
                        });

                        batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                    }
                });
        queue.add(reqData);
    }
    public boolean Validate() {
        if (CBSurvei.isChecked()) {
            if (UriSelfie == null) {
                if (StringSelfie.equals("0")) {
                    Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.custom_dialog_eror_input);

                    if (customDialog.getWindow() != null) {
                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    }

                    Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                    TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                    tv.setText("Harap Selfie Terlebih Dahulu");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                    Glide.with(DetailFollowUpActivity.this)
                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifImageView);

                    customDialog.show();
                    return false;
                }
            }
        }
        return true;
    }
}