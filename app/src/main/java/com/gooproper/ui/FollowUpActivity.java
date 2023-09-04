package com.gooproper.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FollowUpActivity extends AppCompatActivity {

    ProgressDialog PDFollowUp;
    Button Submit, Batal;
    EditText Nama, NoWa, Ket, Tgl, Jam;
    CheckBox Chat, Survei, Tawar, Lokasi, Deal;
    ImageView IVSurvei;
    Bitmap Selfie;
    String BuyerNama, BuyerTelp, BuyerKeterangan, BuyerTanggal, BuyerIdAgen, BuyerIdListing, BuyerIdInput, BuyerJam, StringNamaBuyer, PenggunaId, PenggunaStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up);

        PDFollowUp = new ProgressDialog(FollowUpActivity.this);
        Submit = findViewById(R.id.BtnSubmitBuyer);
        Batal = findViewById(R.id.BtnBatalBuyer);
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

        Survei.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    IVSurvei.setVisibility(View.VISIBLE);
                } else {
                    IVSurvei.setVisibility(View.GONE);
                }
            }
        });

        IVSurvei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        Batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFlowup();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
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
                map.put("Tanggal", Tgl.getText().toString());
                map.put("Jam", Jam.getText().toString());
                map.put("Keterangan", Ket.getText().toString());
                map.put("Chat", StringChat);
                map.put("Survei", StringSurvei);
                map.put("Tawar", StringTawar);
                map.put("Lokasi", StringLokasi);
                map.put("Deal", StringDeal);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}