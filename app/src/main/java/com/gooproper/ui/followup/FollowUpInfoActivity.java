package com.gooproper.ui.followup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
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

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FollowUpInfoActivity extends AppCompatActivity {

    ProgressDialog PDFollowUp;
    Button Submit, Batal;
    EditText Ket, Tgl, Jam;
    String IdInfo, IdAgen, Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_info);

        PDFollowUp = new ProgressDialog(FollowUpInfoActivity.this);
        Submit = findViewById(R.id.BtnSubmitInfo);
        Batal = findViewById(R.id.BtnBatalInfo);
        Ket = findViewById(R.id.ETKeteranganInfo);
        Tgl = findViewById(R.id.ETTanggalInfo);
        Jam = findViewById(R.id.ETJamInfo);

        Intent data = getIntent();
        IdInfo = data.getStringExtra("IdInfo");

        Status = Preferences.getKeyStatus(this);

        if (Status.equals("1")) {
            IdAgen = "0";
        } else if (Status.equals("2")) {
            IdAgen = "0";
        } else if (Status.equals("3")) {
            IdAgen = Preferences.getKeyIdAgen(this);
        } else if (Status.equals("4")) {
            IdAgen = Preferences.getKeyIdAgen(this);

        }

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
                        FollowUpInfoActivity.this,
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_ADD_FLOWUP_INFO, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDFollowUp.cancel();
                Dialog customDialog = new Dialog(FollowUpInfoActivity.this);
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

                Glide.with(FollowUpInfoActivity.this)
                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDFollowUp.cancel();
                Dialog customDialog = new Dialog(FollowUpInfoActivity.this);
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

                Glide.with(FollowUpInfoActivity.this)
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
                map.put("IdAgen", IdAgen);
                map.put("IdInfo", IdInfo);
                map.put("Keterangan", Ket.getText().toString());
                map.put("Tanggal", Tgl.getText().toString());
                map.put("Jam", Jam.getText().toString());
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}