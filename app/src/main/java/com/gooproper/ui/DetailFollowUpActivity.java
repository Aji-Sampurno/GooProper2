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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gooproper.R;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;

public class DetailFollowUpActivity extends AppCompatActivity {

    ProgressDialog PDFollowUp;
    TextView TVNamaListing, TVAlamatListing, TVNamaBuyer, TVWaBuyer, TVSumber, TVTanggal, TVJam, TVStatus;
    CheckBox CBChat, CBSurvei, CBTawar, CBLokasi, CBDeal;
    ImageView IVBack, IVSelfie;
    Button BtnBatal, BtnUpdate;
    Bitmap BitmapSelfie;
    String StringIdFollowUp;
    final int CODE_GALLERY_REQUEST = 100;
    final int CODE_CAMERA_REQUEST = 101;
    final int KODE_REQUEST_KAMERA = 102;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE = 123;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES = 456;

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

        Intent data = getIntent();
        final int update = data.getIntExtra("update",0);
        String intentIdFlowup = data.getStringExtra("IdFlowup");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentIdInput = data.getStringExtra("IdInput");
        String intentNamaBuyer = data.getStringExtra("NamaBuyer");
        String intentIdListing = data.getStringExtra("IdListing");
        String intentTanggal = data.getStringExtra("Tanggal");
        String intentJam = data.getStringExtra("Jam");
        String intentKeterangan = data.getStringExtra("Keterangan");
        String intentChat = data.getStringExtra("Chat");
        String intentSurvei = data.getStringExtra("Survei");
        String intentTawar = data.getStringExtra("Tawar");
        String intentLokasi = data.getStringExtra("Lokasi");
        String intentDeal = data.getStringExtra("Deal");
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

        TVNamaListing.setText(intentNamaListing);
        TVAlamatListing.setText(intentAlamat);
        TVNamaBuyer.setText(intentNamaBuyer);
        //TVWaBuyer.setText(inten);
        //TVSumber.setText();
        TVTanggal.setText(intentTanggal);
        TVJam.setText(intentJam);
        if (intentDeal.equals("1")){
            TVStatus.setText("Deal");
            CBDeal.setChecked(true);
        } else if (intentLokasi.equals("1")) {
            TVStatus.setText("Cari Lokasi Lain");
            CBLokasi.setChecked(true);
        } else if (intentTawar.equals("1")) {
            TVStatus.setText("Tawar");
            CBTawar.setChecked(true);
        } else if (intentSurvei.equals("1")) {
            TVStatus.setText("Survei");
            CBSurvei.setChecked(true);
        } else if (intentChat.equals("1")){
            TVStatus.setText("Chat");
            CBChat.setChecked(true);
        }

        CBSurvei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    IVSelfie.setVisibility(View.VISIBLE);
                } else {
                    IVSelfie.setVisibility(View.GONE);
                }
            }
        });

        IVBack.setOnClickListener(view -> finish());
        IVSelfie.setOnClickListener(view -> showPhotoSelectionDialog());

        BtnBatal.setOnClickListener(view -> finish());
        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CBSurvei.isChecked()){
                    if (BitmapSelfie == null){
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
                    }
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void showPhotoSelectionDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Unggah Gambar")
                .setItems(new CharSequence[]{"Ambil Foto", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(DetailFollowUpActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST);
                                break;
                            case 1:
                                requestPermissions();
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

    private void bukaKamera() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA);
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
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
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
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                BitmapSelfie = BitmapFactory.decodeStream(inputStream);
                IVSelfie.setImageBitmap(BitmapSelfie);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (requestCode == KODE_REQUEST_KAMERA && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this,imageBitmap));
                        BitmapSelfie = BitmapFactory.decodeStream(inputStream);
                        IVSelfie.setImageBitmap(BitmapSelfie);
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
}