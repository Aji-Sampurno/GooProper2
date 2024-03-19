package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.gooproper.R;
import com.gooproper.customer.MainCustomerActivity;
import com.gooproper.guest.MainGuestActivity;
import com.gooproper.ui.edit.EditAkunActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SettingActivity extends AppCompatActivity {

    String Token, Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        LinearLayout editakun  = findViewById(R.id.lyteditakun);
        LinearLayout ubahsandi = findViewById(R.id.lytubahsandi);
        LinearLayout logout    = findViewById(R.id.lytkeluar);
        ImageView keluar       = findViewById(R.id.ivcancel);

        Status = Preferences.getKeyStatus(this);

        editakun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, EditAkunActivity.class));
            }
        });

        ubahsandi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SettingActivity.this, UbahSandiActivity.class));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseMessaging.getInstance().getToken()
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                return;
                            }
                            Token = task.getResult();
                            showCustomAlertDialog(Token);
                        });
            }
        });

        keluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void showCustomAlertDialog(String token) {
        Dialog customDialog = new Dialog(SettingActivity.this);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.setContentView(R.layout.custom_dialog_konfirmasi);

        if (customDialog.getWindow() != null) {
            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }

        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
        Button ya = customDialog.findViewById(R.id.btnya);
        Button tidak = customDialog.findViewById(R.id.btntidak);

        if (Status.equals("3")) {
            ya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_DEVICE_AGEN,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject res = new JSONObject(response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getBaseContext(), "Gagal Hapus Data. Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("Token",token);
                            System.out.println(map);

                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                    requestQueue.add(stringRequest);

                    Preferences.clearLoggedInUser(SettingActivity.this);
                    startActivity(new Intent(SettingActivity.this, MainGuestActivity.class));
                    finish();
                }
            });
        } else if (Status.equals("2")) {
            ya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_DEVICE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject res = new JSONObject(response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getBaseContext(), "Gagal Hapus Data. Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("Token",token);
                            System.out.println(map);

                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                    requestQueue.add(stringRequest);

                    Preferences.clearLoggedInUser(SettingActivity.this);
                    startActivity(new Intent(SettingActivity.this, MainGuestActivity.class));
                    finish();
                }
            });
        } else if (Status.equals("1")) {
            ya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_DELETE_DEVICE,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject res = new JSONObject(response);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getBaseContext(), "Gagal Hapus Data. Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> map = new HashMap<>();
                            map.put("Token",token);
                            System.out.println(map);

                            return map;
                        }
                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(getBaseContext());
                    requestQueue.add(stringRequest);

                    Preferences.clearLoggedInUser(SettingActivity.this);
                    startActivity(new Intent(SettingActivity.this, MainGuestActivity.class));
                    finish();
                }
            });
        } else {
            ya.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Preferences.clearLoggedInUser(SettingActivity.this);
                    startActivity(new Intent(SettingActivity.this, MainGuestActivity.class));
                    finish();
                }
            });
        }

        tidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customDialog.dismiss();
            }
        });

        dialogTitle.setText("Apakah Anda Yakin Untuk Keluar");

        ImageView gifImageView = customDialog.findViewById(R.id.ivdialog);

        Glide.with(this)
                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(gifImageView);

        customDialog.show();
    }
}