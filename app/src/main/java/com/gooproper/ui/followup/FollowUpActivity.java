package com.gooproper.ui.followup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FollowUpActivity extends AppCompatActivity {

    ProgressDialog PDFollowUp;
    Button Submit, Batal, Selfie, Delete;
    EditText Nama, NoWa, Ket, Tgl, Jam;
    CheckBox Chat, Survei, Tawar, Lokasi, Deal;
    ImageView IVSurvei;
    String BuyerNama, BuyerTelp, BuyerKeterangan, BuyerTanggal, BuyerIdAgen, BuyerIdListing, BuyerIdInput, BuyerJam, StringNamaBuyer, PenggunaId, PenggunaStatus, StringSelfie;
    Bitmap BitmapSelfie;
    Uri UriSelfie;
    final int CODE_CAMERA_REQUEST = 101;
    final int KODE_REQUEST_KAMERA = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up);

        PDFollowUp = new ProgressDialog(FollowUpActivity.this);
        Submit = findViewById(R.id.BtnSubmitBuyer);
        Batal = findViewById(R.id.BtnBatalBuyer);
        Selfie = findViewById(R.id.BtnSelfie);
        Delete = findViewById(R.id.BtnDelete);
        Nama = findViewById(R.id.ETNamaBuyer);
        NoWa = findViewById(R.id.ETTelpBuyer);
        Ket = findViewById(R.id.ETKeteranganBuyer);
        Tgl = findViewById(R.id.ETTanggalBuyer);
        Jam = findViewById(R.id.ETJamBuyer);
        Chat = findViewById(R.id.CBChat);
        Survei = findViewById(R.id.CBSurvei);
        Tawar = findViewById(R.id.CBTawar);
        Lokasi = findViewById(R.id.CBLokasi);
        Deal = findViewById(R.id.CBDeal);
        IVSurvei = findViewById(R.id.IVSurvei);

        Intent data = getIntent();
        String intentIdListing = data.getStringExtra("IdListing");
        String intentIdAgen = data.getStringExtra("IdAgen");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileSelfie = "Selfie_" + timeStamp + ".jpg";

        BuyerIdAgen = intentIdAgen;
        BuyerIdListing = intentIdListing;
        BuyerNama = Nama.getText().toString();
        BuyerTelp = NoWa.getText().toString();
        BuyerKeterangan = Ket.getText().toString();
        BuyerTanggal = Tgl.getText().toString();
        BuyerJam = Jam.getText().toString();

        PenggunaStatus = Preferences.getKeyStatus(this);

        if (PenggunaStatus.equals("1")) {
            PenggunaId = "0";
        } else if (PenggunaStatus.equals("2")) {
            PenggunaId = "0";
        } else if (PenggunaStatus.equals("3")) {
            PenggunaId = Preferences.getKeyIdAgen(this);
        } else if (PenggunaStatus.equals("4")) {
            PenggunaId = Preferences.getKeyIdCustomer(this);
        }

        Delete.setOnClickListener(v -> clearBitmapSelfie());
        Tgl.setOnClickListener(new View.OnClickListener() {
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
                        Tgl.setText(MessageFormat.format("{0}", tanggal));
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });
        Jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(
                        FollowUpActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                Jam.setText(selectedTime);
                            }
                        },
                        hour,
                        minute,
                        false
                );

                timePickerDialog.show();
            }
        });
        Survei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    Selfie.setVisibility(View.VISIBLE);
                } else {
                    Selfie.setVisibility(View.GONE);
                }
            }
        });
        Selfie.setOnClickListener(view -> ActivityCompat.requestPermissions(FollowUpActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST));
        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Survei.isChecked()){
                    if (UriSelfie == null){
                        Dialog customDialog = new Dialog(FollowUpActivity.this);
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

                        Glide.with(FollowUpActivity.this)
                                .load(R.drawable.alert)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
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
                                                    StringSelfie = imageUrl;
                                                })
                                                .addOnFailureListener(exception -> {
                                                });
                                    })
                                    .addOnFailureListener(exception -> {
                                    });
                            uploadTasks.add(task1);
                        } else {
                            StringSelfie = "0";
                        }

                        Tasks.whenAllSuccess(uploadTasks)
                                .addOnSuccessListener(results -> {
                                    PDFollowUp.cancel();
                                    AddFlowup();
                                })
                                .addOnFailureListener(exception -> {
                                    Dialog customDialog = new Dialog(FollowUpActivity.this);
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

                                    Glide.with(FollowUpActivity.this)
                                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifImageView);

                                    customDialog.show();
                                });
                    }
                } else {
                    StringSelfie = "0";
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
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriSelfie = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriSelfie);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA);
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

        if (requestCode == CODE_CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera();
            } else {
                Dialog customDialog = new Dialog(FollowUpActivity.this);
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

                Glide.with(FollowUpActivity.this)
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
            IVSurvei.setImageURI(UriSelfie);
            IVSurvei.setVisibility(View.VISIBLE);
            Delete.setVisibility(View.VISIBLE);
            Selfie.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void clearBitmapSelfie() {
        if (BitmapSelfie != null && !BitmapSelfie.isRecycled()) {
            BitmapSelfie.recycle();
            BitmapSelfie = null;
            Delete.setVisibility(View.GONE);
            Selfie.setVisibility(View.VISIBLE);
        } else {
            UriSelfie = null;
            IVSurvei.setVisibility(View.GONE);
            Delete.setVisibility(View.GONE);
            Selfie.setVisibility(View.VISIBLE);
        }
    }
    private void AddFlowup() {
        PDFollowUp.setMessage("Menyimpan Data...");
        PDFollowUp.setCancelable(false);
        PDFollowUp.show();

        final String StringChat = Chat.isChecked()?"1":"0";
        final String StringSurvei = Survei.isChecked()?"1":"0";
        final String StringTawar = Tawar.isChecked()?"1":"0";
        final String StringLokasi = Lokasi.isChecked()?"1":"0";
        final String StringDeal = Deal.isChecked()?"1":"0";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_ADD_FLOWUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDFollowUp.cancel();
                Dialog customDialog = new Dialog(FollowUpActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Berhasil Menambahkan Flowup");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                        finish();
                    }
                });

                Glide.with(FollowUpActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDFollowUp.cancel();
                Dialog customDialog = new Dialog(FollowUpActivity.this);
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

                Glide.with(FollowUpActivity.this)
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
                map.put("IdAgen", BuyerIdAgen);
                map.put("IdInput", PenggunaId);
                map.put("IdListing", BuyerIdListing);
                map.put("NamaBuyer", Nama.getText().toString());
                map.put("TelpBuyer", NoWa.getText().toString());
                map.put("SumberBuyer", Ket.getText().toString());
                map.put("Tanggal", Tgl.getText().toString());
                map.put("Jam", Jam.getText().toString());
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