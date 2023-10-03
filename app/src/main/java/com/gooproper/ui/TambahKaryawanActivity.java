package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.gooproper.R;
import com.gooproper.util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TambahKaryawanActivity extends AppCompatActivity {

    private ProgressDialog PDKaryawan;
    TextInputEditText Nama, Posisi, Telp;
    Button Batal, Submit;
    ImageView IVBack;
    String StringIdAgen;
    private static final int MAPS_ACTIVITY_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_karyawan);

        PDKaryawan = new ProgressDialog(TambahKaryawanActivity.this);

        Nama = findViewById(R.id.ETNamaLengkapKaryawan);
        Posisi = findViewById(R.id.ETPosisiKaryawan);
        Telp = findViewById(R.id.ETNoTelpKaryawan);

        Batal = findViewById(R.id.btnbatal);
        Submit = findViewById(R.id.btnsubmit);

        IVBack = findViewById(R.id.backFormBtn);

        StringIdAgen = "0";

        IVBack.setOnClickListener(view -> finish());
        Batal.setOnClickListener(view -> finish());
        Submit.setOnClickListener(view -> AddKaryawan());
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void AddKaryawan() {
        PDKaryawan.setMessage("Menyimpan Data");
        PDKaryawan.setCancelable(false);
        PDKaryawan.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_KARYAWAN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        PDKaryawan.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            AlertDialog.Builder builder = new AlertDialog.Builder(TambahKaryawanActivity.this);
                            builder.setTitle("Berhasil").
                                    setMessage("Berhasil tambah karyawan");
                            builder.setPositiveButton("OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            finish();
                                        }
                                    });
                            AlertDialog alert11 = builder.create();
                            alert11.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PDKaryawan.cancel();
                        AlertDialog.Builder builder = new AlertDialog.Builder(TambahKaryawanActivity.this);
                        builder.setTitle("Gagal").
                                setMessage("Gagal tambah karyawan");
                        builder.setPositiveButton("Coba Lagi",
                                (dialog, id) -> dialog.cancel());
                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("IdAgen", StringIdAgen);
                map.put("Nama", Nama.getText().toString());
                map.put("Posisi", Posisi.getText().toString());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}