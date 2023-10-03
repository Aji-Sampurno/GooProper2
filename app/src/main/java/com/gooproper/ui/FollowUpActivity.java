package com.gooproper.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.gooproper.R;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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

        IVSurvei.setOnClickListener(view -> ActivityCompat.requestPermissions(FollowUpActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST));

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
                    if (BitmapSelfie == null){
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
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    try {
                        InputStream inputStream = getContentResolver().openInputStream(getImageUri(this,imageBitmap));
                        BitmapSelfie = BitmapFactory.decodeStream(inputStream);
                        IVSurvei.setImageBitmap(BitmapSelfie);
                        IVSurvei.setVisibility(View.VISIBLE);
                        Selfie.setVisibility(View.GONE);
                        Delete.setVisibility(View.VISIBLE);
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
        if (BitmapSelfie == null) {
            StringSelfie = "0";
        } else {
            StringSelfie = imageToString(BitmapSelfie);
        }
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