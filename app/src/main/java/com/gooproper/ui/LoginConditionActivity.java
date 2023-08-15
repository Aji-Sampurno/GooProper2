package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.ui.registrasi.RegistrasiCustomerActivity;
import com.gooproper.admin.MainAdminActivity;
import com.gooproper.agen.MainAgenActivity;
import com.gooproper.customer.MainCustomerActivity;
import com.gooproper.ui.registrasi.RegistrasiAgenActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginConditionActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    Button login, regisagent;
    TextView username, password, regis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_condition);

        login      = findViewById(R.id.btnlogin);
        regisagent = findViewById(R.id.btnregisagent);
        regis      = findViewById(R.id.tvregis);
        username   = findViewById(R.id.etusername);
        password   = findViewById(R.id.etpassword);
        pDialog   = new ProgressDialog(LoginConditionActivity.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateInputs()) {
                    Login();
                }
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginConditionActivity.this, RegistrasiCustomerActivity.class));
            }
        });

        regisagent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginConditionActivity.this, RegistrasiAgenActivity.class));
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void Login() {
        pDialog.setMessage("Sedang diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest updateReq = new StringRequest(Request.Method.POST, ServerApi.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            if (res.getBoolean("status_login")){
                                if(res.getBoolean("admin")){
                                    Preferences.setKeyIdAdmin(getBaseContext(), res.getString("IdAdmin"));
                                    Preferences.setKeyUsername(getBaseContext(), res.getString("Username"));
                                    Preferences.setKeyNamaLengkap(getBaseContext(), res.getString("NamaLengkap"));
                                    Preferences.setKeyNoTelp(getBaseContext(), res.getString("NoTelp"));
                                    Preferences.setKeyAlamat(getBaseContext(), res.getString("Alamat"));
                                    Preferences.setKeyTglLahir(getBaseContext(), res.getString("TglLahir"));
                                    Preferences.setKeyEmail(getBaseContext(), res.getString("Email"));
                                    Preferences.setKeyPhoto(getBaseContext(), res.getString("Photo"));
                                    Preferences.setKeyPassword(getBaseContext(), res.getString("Password"));
                                    Preferences.setKeyStatus(getBaseContext(), res.getString("Status"));

                                    Intent intent = new Intent(LoginConditionActivity.this, MainAdminActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else if (res.getBoolean("customer")) {
                                    Preferences.setKeyIdCustomer(getBaseContext(), res.getString("IdCustomer"));
                                    Preferences.setKeyUsername(getBaseContext(), res.getString("Username"));
                                    Preferences.setKeyNamaLengkap(getBaseContext(), res.getString("NamaLengkap"));
                                    Preferences.setKeyNoTelp(getBaseContext(), res.getString("NoTelp"));
                                    Preferences.setKeyAlamat(getBaseContext(), res.getString("Alamat"));
                                    Preferences.setKeyTglLahir(getBaseContext(), res.getString("TglLahir"));
                                    Preferences.setKeyEmail(getBaseContext(), res.getString("Email"));
                                    Preferences.setKeyPhoto(getBaseContext(), res.getString("Photo"));
                                    Preferences.setKeyPassword(getBaseContext(), res.getString("Password"));
                                    Preferences.setKeyStatus(getBaseContext(), res.getString("Status"));

                                    Intent intent = new Intent(LoginConditionActivity.this, MainCustomerActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                } else if (res.getBoolean("agen")) {
                                    Preferences.setKeyIdAgen(getBaseContext(), res.getString("IdAgen"));
                                    Preferences.setKeyUsername(getBaseContext(), res.getString("Username"));
                                    Preferences.setKeyPassword(getBaseContext(), res.getString("Password"));
                                    Preferences.setKeyNama(getBaseContext(), res.getString("Nama"));
                                    Preferences.setKeyNoTelp(getBaseContext(), res.getString("NoTelp"));
                                    Preferences.setKeyEmail(getBaseContext(), res.getString("Email"));
                                    Preferences.setKeyTglLahir(getBaseContext(), res.getString("TglLahir"));
                                    Preferences.setKeyKotaKelahiran(getBaseContext(), res.getString("KotaKelahiran"));
                                    Preferences.setKeyPendidikan(getBaseContext(), res.getString("Pendidikan"));
                                    Preferences.setKeyNamaSekolah(getBaseContext(), res.getString("NamaSekolah"));
                                    Preferences.setKeyMasaKerja(getBaseContext(), res.getString("MasaKerja"));
                                    Preferences.setKeyJabatan(getBaseContext(), res.getString("Jabatan"));
                                    Preferences.setKeyAlamatDomisili(getBaseContext(), res.getString("AlamatDomisili"));
                                    Preferences.setKeyFacebook(getBaseContext(), res.getString("Facebook"));
                                    Preferences.setKeyInstagram(getBaseContext(), res.getString("Instagram"));
                                    Preferences.setKeyNoKtp(getBaseContext(), res.getString("NoKtp"));
                                    Preferences.setKeyImgKtp(getBaseContext(), res.getString("ImgKtp"));
                                    Preferences.setKeyImgTtd(getBaseContext(), res.getString("ImgTtd"));
                                    Preferences.setKeyNpwp(getBaseContext(), res.getString("Npwp"));
                                    Preferences.setKeyPhoto(getBaseContext(), res.getString("Photo"));
                                    Preferences.setKeyPoin(getBaseContext(), res.getString("Poin"));
                                    Preferences.setKeyStatus(getBaseContext(), res.getString("Status"));
                                    Preferences.setKeyIsAkses(getBaseContext(), res.getString("IsAkses"));

                                    Intent intent = new Intent(LoginConditionActivity.this, MainAgenActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                            }else {
                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LoginConditionActivity.this);
                                builder.setMessage("Username atau Password Anda salah!"+res)
                                        .setNegativeButton("Retry", null).create().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LoginConditionActivity.this);
                            builder.setMessage("Username atau Password Anda salah!" +e)
                                    .setNegativeButton("Retry", null).create().show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.cancel();
                        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(LoginConditionActivity.this);
                        builder.setMessage("Terdapat Kesalahan Jaringan")
                                .setNegativeButton("Retry", null).create().show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("Username",username.getText().toString().trim());
                map.put("Password",password.getText().toString().trim());

                return map;
            }
        };

        queue.add(updateReq);
    }

    private boolean validateInputs() {
        if (username.getText().toString().equals("")) {
            username.setError("Isi dulu Username");
            username.requestFocus();
            return false;
        }
        if (password.getText().toString().equals("")) {
            password.setError("Isi dulu Password");
            password.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed()
    {
        Preferences.clearLoggedInUser(this);
        startActivity(new Intent(this,MainCustomerActivity.class));
        finish();
    }
}