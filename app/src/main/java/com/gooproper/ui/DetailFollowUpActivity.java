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
import android.util.Base64;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gooproper.R;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DetailFollowUpActivity extends AppCompatActivity {

    ProgressDialog PDFollowUp;
    TextView TVNamaListing, TVAlamatListing, TVNamaBuyer, TVWaBuyer, TVSumber, TVTanggal, TVJam, TVStatus;
    CheckBox CBChat, CBSurvei, CBTawar, CBLokasi, CBDeal;
    ImageView IVBack, IVSelfie;
    Button BtnBatal, BtnUpdate, BtnSelfie, BtnDelete;
    Bitmap BitmapSelfie;
    Drawable DrawableSelfie;
    String StringIdFollowUp, StringSelfie;
    final int CODE_CAMERA_REQUEST = 101;
    final int KODE_REQUEST_KAMERA = 102;

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

        DrawableSelfie = IVSelfie.getDrawable();

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

        StringIdFollowUp = intentIdFlowup;

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
            CBDeal.setChecked(true);
            CBDeal.setClickable(false);
        } else if (intentLokasi.equals("1")) {
            TVStatus.setText("Cari Lokasi Lain");
            CBLokasi.setChecked(true);
            CBLokasi.setClickable(false);
        } else if (intentTawar.equals("1")) {
            TVStatus.setText("Tawar");
            CBTawar.setChecked(true);
            CBTawar.setClickable(false);
        } else if (intentSurvei.equals("1")) {
            TVStatus.setText("Survei");
            CBSurvei.setChecked(true);
            CBSurvei.setClickable(false);
            IVSelfie.setVisibility(View.VISIBLE);
        } else if (intentChat.equals("1")){
            TVStatus.setText("Chat");
            CBChat.setChecked(true);
            CBChat.setClickable(false);
        }

        if (!intentSelfie.equals("0")){
            IVSelfie.setVisibility(View.VISIBLE);
            Glide.with(DetailFollowUpActivity.this)
                    .load(intentSelfie)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(IVSelfie);
        }

        /*if (intentDeal.equals("1")){
            CBDeal.setChecked(true);
        }
        if (intentLokasi.equals("1")) {
            CBLokasi.setChecked(true);
        }
        if (intentTawar.equals("1")) {
            CBTawar.setChecked(true);
        }
        if (intentSurvei.equals("1")) {
            CBSurvei.setChecked(true);
            IVSelfie.setVisibility(View.VISIBLE);
        }
        if (intentChat.equals("1")){
            CBChat.setChecked(true);
        }*/

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
        //IVSelfie.setOnClickListener(view -> ActivityCompat.requestPermissions(DetailFollowUpActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST));
        BtnSelfie.setOnClickListener(view -> ActivityCompat.requestPermissions(DetailFollowUpActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST));

        BtnBatal.setOnClickListener(view -> finish());
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CBSurvei.isChecked()){
                    if (DrawableSelfie != null){
                        AddFlowup();
                    } else if (BitmapSelfie == null){
                        Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
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

                        Glide.with(DetailFollowUpActivity.this)
                                .load(R.drawable.alert)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
                        AddFlowup();
                    }
                } else {
                    AddFlowup();
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void bukaKamera() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODE_CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera();
            } else {
                Dialog customDialog = new Dialog(DetailFollowUpActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Kamera Ditolak");

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
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == KODE_REQUEST_KAMERA && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this,imageBitmap));
                        BitmapSelfie = BitmapFactory.decodeStream(inputStream);
                        IVSelfie.setImageBitmap(BitmapSelfie);
                        IVSelfie.setVisibility(View.VISIBLE);
                        BtnDelete.setVisibility(View.VISIBLE);
                        BtnSelfie.setVisibility(View.GONE);
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

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }

    private void clearBitmapSelfie() {
        if (BitmapSelfie != null && !BitmapSelfie.isRecycled()) {
            BitmapSelfie.recycle();
            BitmapSelfie = null;
            BtnDelete.setVisibility(View.GONE);
            BtnSelfie.setVisibility(View.VISIBLE);
        }
    }

    private void AddFlowup() {
        PDFollowUp.setMessage("Menyimpan Data...");
        PDFollowUp.setCancelable(false);
        PDFollowUp.show();

        final String StringChat = CBChat.isChecked()?"1":"0";
        final String StringSurvei = CBSurvei.isChecked()?"1":"0";
        final String StringTawar = CBTawar.isChecked()?"1":"0";
        final String StringLokasi = CBLokasi.isChecked()?"1":"0";
        final String StringDeal = CBDeal.isChecked()?"1":"0";
        if (BitmapSelfie == null) {
            StringSelfie = "0";
        } else {
            StringSelfie = imageToString(BitmapSelfie);
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_FLOWUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
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

                dialogTitle.setText("Berhasil Update Flowup");
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

                dialogTitle.setText("Gagal Tambah Data FlowUp");
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
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                System.out.println(map);
                map.put("IdFlowup", StringIdFollowUp);
                map.put("Chat", StringChat);
                map.put("Survei", StringSurvei);
                map.put("Tawar", StringTawar);
                map.put("Lokasi", StringLokasi);
                map.put("Deal", StringDeal);
                map.put("Selfie", StringSelfie);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}