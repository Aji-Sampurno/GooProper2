package com.gooproper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import com.gooproper.util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class RegistrasiCustomerActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private TextView username,namalengkap,telp,email,password,konfirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_customer);

        TextView login       = findViewById(R.id.tvlogin);
        Button regis         = findViewById(R.id.btnregistrasi);
        username    = findViewById(R.id.etusername);
        namalengkap = findViewById(R.id.etnamalengkap);
        telp        = findViewById(R.id.ettelp);
        email       = findViewById(R.id.etemail);
        password    = findViewById(R.id.etpassword);
        konfirmpass = findViewById(R.id.etrepassword);

        pDialog   = new ProgressDialog(RegistrasiCustomerActivity.this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrasiCustomerActivity.this, LoginActivity.class));
            }
        });

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Register();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void Register(){
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REGISTRASI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);

                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiCustomerActivity.this);
                builder.setTitle("Registrasi Berhasil").
                        setMessage("Registrasi akun anda telah berhasil");
                builder.setPositiveButton("Login",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent intent = new Intent(RegistrasiCustomerActivity.this, LoginActivity.class);
                                RegistrasiCustomerActivity.this.startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();

//                }
//                catch (JSONException e) {
//                    e.printStackTrace();
//
//                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
//                    builder.setTitle("Registrasi Gagal").
//                            setMessage("Terdapat kesalahan saat melakukan registrasi");
//                    builder.setPositiveButton("Coba Lagi",
//                            new DialogInterface.OnClickListener() {
//                                public void onClick(DialogInterface dialog, int id) {
//                                    dialog.cancel();
//                                }
//                            });
//                    AlertDialog alert11 = builder.create();
//                    alert11.show();
//
//                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiCustomerActivity.this);
                builder.setTitle("Kesalahan Jaringan").
                        setMessage("Terdapat kesalahan jaringan saat memuat data");
                builder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert11 = builder.create();
                alert11.show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
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