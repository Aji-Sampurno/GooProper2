package com.gooproper.ui.registrasi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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
import com.gooproper.LoginActivity;
import com.gooproper.R;
import com.gooproper.util.ServerApi;

import java.util.HashMap;
import java.util.Map;


public class RegistrasiCustomerActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private TextView username, namalengkap, telp, email, password, konfirmpass;
    String pass, konfirmasipas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_customer);

        TextView login = findViewById(R.id.TVLoginRegistrasiCustomer);
        Button regis = findViewById(R.id.BtnSubmitRegistrasiCustomer);
        username = findViewById(R.id.ETUsernameRegistrasiCustomer);
        namalengkap = findViewById(R.id.ETNamaLengkapRegistrasiCustomer);
        telp = findViewById(R.id.ETNoTelpRegistrasiCustomer);
        email = findViewById(R.id.ETEmailRegistrasiCustomer);
        password = findViewById(R.id.ETPasswordRegistrasiCustomer);
        konfirmpass = findViewById(R.id.ETRePasswordRegistrasiCustomer);

        pDialog = new ProgressDialog(RegistrasiCustomerActivity.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrasiCustomerActivity.this, LoginActivity.class));
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    pass = password.getText().toString();
                    konfirmasipas = konfirmpass.getText().toString();
                    if (konfirmasipas.equals(pass)) {
                        Register();
                    } else {
                        konfirmpass.setError("Password Berdeda");
                        konfirmpass.requestFocus();
                    }
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private boolean validateInputs() {
        if (username.getText().toString().equals("")) {
            username.setError("Harap Isi Username");
            username.requestFocus();
            return false;
        }
        if (namalengkap.getText().toString().equals("")) {
            namalengkap.setError("Harap Isi Nama Lengkap");
            namalengkap.requestFocus();
            return false;
        }
        if (telp.getText().toString().equals("")) {
            telp.setError("Harap Isi Nomor WhatsApp");
            telp.requestFocus();
            return false;
        }
        if (email.getText().toString().equals("")) {
            email.setError("Harap Isi Email");
            email.requestFocus();
            return false;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Harap Isi Password");
            password.requestFocus();
            return false;
        }
        if (konfirmpass.getText().toString().equals("")) {
            konfirmpass.setError("Harap Isi Konfirmasi Password");
            konfirmpass.requestFocus();
            return false;
        }
        return true;
    }

    private void Register() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REGISTRASI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(RegistrasiCustomerActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_registrasi);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.TVStatusRegistrasi);
                Button ok = customDialog.findViewById(R.id.BtnOkRegistrasi);
                Button cobalagi = customDialog.findViewById(R.id.BtnCobaLagiRegistrasi);
                ImageView image = customDialog.findViewById(R.id.IVStatusRegistrasi);

                cobalagi.setVisibility(View.GONE);
                dialogTitle.setText("Registrasi Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RegistrasiCustomerActivity.this, LoginActivity.class));
                        finish();
                    }
                });

                Glide.with(RegistrasiCustomerActivity.this)
                        .load(R.mipmap.ic_yes)
                        .into(image);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(RegistrasiCustomerActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_registrasi);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.TVStatusRegistrasi);
                Button ok = customDialog.findViewById(R.id.BtnOkRegistrasi);
                Button cobalagi = customDialog.findViewById(R.id.BtnCobaLagiRegistrasi);
                ImageView image = customDialog.findViewById(R.id.IVStatusRegistrasi);

                ok.setVisibility(View.GONE);
                dialogTitle.setText("Registrasi Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(RegistrasiCustomerActivity.this)
                        .load(R.mipmap.ic_no)
                        .into(image);

                customDialog.show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("Username", username.getText().toString());
                map.put("NamaLengkap", namalengkap.getText().toString());
                map.put("NoTelp", telp.getText().toString());
                map.put("Email", email.getText().toString());
                map.put("Password", password.getText().toString());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}