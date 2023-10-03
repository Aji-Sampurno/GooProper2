package com.gooproper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
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
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import java.util.HashMap;
import java.util.Map;

public class UbahSandiActivity extends AppCompatActivity {

    private ProgressDialog PDUbahSandi;
    ImageView back, save;
    EditText oldpass, pass, repass;
    String status, idadmin, idagen, idcustomer, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_sandi);

        back = findViewById(R.id.ivback);
        save = findViewById(R.id.ivsave);
        oldpass = findViewById(R.id.etpassword);
        pass = findViewById(R.id.etpassword);
        repass = findViewById(R.id.etrepassword);

        PDUbahSandi = new ProgressDialog(UbahSandiActivity.this);
        idadmin = Preferences.getKeyIdCustomer(UbahSandiActivity.this);
        idagen = Preferences.getKeyIdAgen(UbahSandiActivity.this);
        idcustomer = Preferences.getKeyIdCustomer(UbahSandiActivity.this);
        status = Preferences.getKeyStatus(UbahSandiActivity.this);
        password = Preferences.getKeyPassword(UbahSandiActivity.this);

        if (status.equals("1")){
            save.setOnClickListener(v -> UpdateSandiAdmin());
        } else if (status.equals("2")) {
            save.setOnClickListener(v -> UpdateSandiAdmin());
        } else if (status.equals("3")) {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UpdateSandiAgen();
                }
            });
        } else {
            save.setOnClickListener(v -> UpdateSandiCustomer());
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void UpdateSandiAdmin() {
        PDUbahSandi.setMessage("Proses Update...");
        PDUbahSandi.setCancelable(false);
        PDUbahSandi.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UBAH_SANDI_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDUbahSandi.cancel();
                Dialog customDialog = new Dialog(UbahSandiActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                cobalagi.setVisibility(View.GONE);
                dialogTitle.setText("Ubah Sandi Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Glide.with(UbahSandiActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDUbahSandi.cancel();
                Dialog customDialog = new Dialog(UbahSandiActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                ok.setVisibility(View.GONE);
                dialogTitle.setText("Ubah Sandi Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(UbahSandiActivity.this)
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
                map.put("IdAdmin", idadmin);
                map.put("Password", password);
                map.put("OldPassword", oldpass.getText().toString().trim());
                map.put("NewPassword", pass.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void UpdateSandiAgen() {
        PDUbahSandi.setMessage("Proses Update...");
        PDUbahSandi.setCancelable(false);
        PDUbahSandi.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UBAH_SANDI_AGEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDUbahSandi.cancel();
                Dialog customDialog = new Dialog(UbahSandiActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                cobalagi.setVisibility(View.GONE);
                dialogTitle.setText("Ubah Sandi Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Glide.with(UbahSandiActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDUbahSandi.cancel();
                Dialog customDialog = new Dialog(UbahSandiActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                ok.setVisibility(View.GONE);
                dialogTitle.setText("Ubah Sandi Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(UbahSandiActivity.this)
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
                map.put("IdAgen", idagen);
                map.put("Password", password);
                map.put("OldPassword", oldpass.getText().toString().trim());
                map.put("NewPassword", pass.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void UpdateSandiCustomer() {
        PDUbahSandi.setMessage("Proses Update...");
        PDUbahSandi.setCancelable(false);
        PDUbahSandi.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UBAH_SANDI_CUSTOMER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDUbahSandi.cancel();
                Dialog customDialog = new Dialog(UbahSandiActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                cobalagi.setVisibility(View.GONE);
                dialogTitle.setText("Ubah Sandi Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Glide.with(UbahSandiActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDUbahSandi.cancel();
                Dialog customDialog = new Dialog(UbahSandiActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                ok.setVisibility(View.GONE);
                dialogTitle.setText("Ubah Sandi Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(UbahSandiActivity.this)
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
                map.put("IdCustomer", idcustomer);
                map.put("Password", password);
                map.put("OldPassword", oldpass.getText().toString().trim());
                map.put("NewPassword", pass.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}