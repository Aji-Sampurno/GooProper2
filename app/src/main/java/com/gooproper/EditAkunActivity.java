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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import java.util.HashMap;
import java.util.Map;

public class EditAkunActivity extends AppCompatActivity {

    private ProgressDialog PDEditAkun;
    String akses, status, idadmin, idagen, idcustomer;
    ImageView back, save;
    TextInputLayout TILusername,TILnama,TILnamalengkap,TILnamakantor,TILtelephone,TILemail,TILdomisili,TILfacebook,TILinstagram;
    TextInputEditText username, nama, namalengkap, namakantor, telephone, email, alamatdomisili, facebook, instagram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_akun);

        back = findViewById(R.id.IVBackEditAkun);
        save = findViewById(R.id.IVSaveEditAkun);
        username = findViewById(R.id.ETUsernameEditAkun);
        nama = findViewById(R.id.ETNamaEditAkun);
        namalengkap = findViewById(R.id.ETNamaLengkapEditAkun);
        namakantor = findViewById(R.id.ETNamaKantorEditAkun);
        telephone = findViewById(R.id.ETNoTelpEditAkun);
        email = findViewById(R.id.ETEmailEditAkun);
        alamatdomisili = findViewById(R.id.ETAlamatDomisiliEditAkun);
        facebook = findViewById(R.id.ETFacebookEditAkun);
        instagram = findViewById(R.id.ETInstagramEditAkun);
        TILusername = findViewById(R.id.TILUsernameEditAkun);
        TILnama = findViewById(R.id.TILNamaEditAkun);
        TILnamalengkap = findViewById(R.id.TILNamaLengkapEditAkun);
        TILnamakantor = findViewById(R.id.TILNamaKantorEditAkun);
        TILtelephone = findViewById(R.id.TILNoTelpEditAkun);
        TILemail = findViewById(R.id.TILEmailEditAkun);
        TILdomisili = findViewById(R.id.TILAlamatDomisiliEditAkun);
        TILfacebook = findViewById(R.id.TILFacebookEditAkun);
        TILinstagram = findViewById(R.id.TILInstagramEditAkun);

        PDEditAkun = new ProgressDialog(EditAkunActivity.this);
        idadmin = Preferences.getKeyIdCustomer(EditAkunActivity.this);
        idagen = Preferences.getKeyIdAgen(EditAkunActivity.this);
        idcustomer = Preferences.getKeyIdCustomer(EditAkunActivity.this);

        username.setText(Preferences.getKeyUsername(EditAkunActivity.this));
        nama.setText(Preferences.getKeyNama(EditAkunActivity.this));
        namalengkap.setText(Preferences.getKeyNamaLengkap(EditAkunActivity.this));
        namakantor.setText(Preferences.getKeyNama(EditAkunActivity.this));
        telephone.setText(Preferences.getKeyNoTelp(EditAkunActivity.this));
        email.setText(Preferences.getKeyEmail(EditAkunActivity.this));
        alamatdomisili.setText(Preferences.getKeyAlamatDomisili(EditAkunActivity.this));
        facebook.setText(Preferences.getKeyFacebook(EditAkunActivity.this));
        instagram.setText(Preferences.getKeyInstagram(EditAkunActivity.this));

        status = Preferences.getKeyStatus(EditAkunActivity.this);
        akses = Preferences.getKeyIsAkses(EditAkunActivity.this);

        if (status.equals("1")) {
            TILnama.setVisibility(View.GONE);
            TILnamakantor.setVisibility(View.GONE);
            TILfacebook.setVisibility(View.GONE);
            TILinstagram.setVisibility(View.GONE);
            save.setOnClickListener(v -> UpdateAdmin());
        } else if (status.equals("2")) {
            TILnama.setVisibility(View.GONE);
            TILnamakantor.setVisibility(View.GONE);
            TILfacebook.setVisibility(View.GONE);
            TILinstagram.setVisibility(View.GONE);
            TILdomisili.setVisibility(View.GONE);
            save.setOnClickListener(v -> UpdateAdmin());
        } else if (status.equals("3")) {
            if (akses.equals("1")) {
                TILnamalengkap.setVisibility(View.GONE);
                TILnamakantor.setVisibility(View.GONE);
                save.setOnClickListener(v -> UpdateAgen());
            } else if (akses.equals("2")) {
                TILnamalengkap.setVisibility(View.GONE);
                TILnamakantor.setVisibility(View.GONE);
                TILdomisili.setVisibility(View.GONE);
                TILfacebook.setVisibility(View.GONE);
                TILinstagram.setVisibility(View.GONE);
                save.setOnClickListener(v -> UpdateMitra());
            } else if (akses.equals("3")) {
                TILnamalengkap.setVisibility(View.GONE);
                TILnama.setVisibility(View.GONE);
                TILdomisili.setVisibility(View.GONE);
                TILfacebook.setVisibility(View.GONE);
                TILinstagram.setVisibility(View.GONE);
                save.setOnClickListener(v -> UpdateKantorLain());
            }
        } else {
            TILnama.setVisibility(View.GONE);
            TILnamakantor.setVisibility(View.GONE);
            TILdomisili.setVisibility(View.GONE);
            TILfacebook.setVisibility(View.GONE);
            TILinstagram.setVisibility(View.GONE);
            save.setOnClickListener(v -> UpdateCustomer());
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

    private void UpdateAdmin() {
        PDEditAkun.setMessage("Proses Update...");
        PDEditAkun.setCancelable(false);
        PDEditAkun.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Preferences.setKeyUsername(getBaseContext(), username.getText().toString());
                        Preferences.setKeyNamaLengkap(getBaseContext(), namalengkap.getText().toString());
                        Preferences.setKeyNoTelp(getBaseContext(), telephone.getText().toString());
                        Preferences.setKeyEmail(getBaseContext(), email.getText().toString());
                        finish();
                    }
                });

                Glide.with(EditAkunActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(EditAkunActivity.this)
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
                map.put("Username", username.getText().toString());
                map.put("NamaLengkap", namalengkap.getText().toString());
                map.put("NoTelp", telephone.getText().toString());
                map.put("Email", email.getText().toString());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void UpdateCustomer() {
        PDEditAkun.setMessage("Proses Update...");
        PDEditAkun.setCancelable(false);
        PDEditAkun.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_CUSTOMER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Preferences.setKeyUsername(getBaseContext(), username.getText().toString());
                        Preferences.setKeyNamaLengkap(getBaseContext(), namalengkap.getText().toString());
                        Preferences.setKeyNoTelp(getBaseContext(), telephone.getText().toString());
                        Preferences.setKeyEmail(getBaseContext(), email.getText().toString());
                        finish();
                    }
                });

                Glide.with(EditAkunActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(EditAkunActivity.this)
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
                map.put("Username", username.getText().toString());
                map.put("NamaLengkap", namalengkap.getText().toString());
                map.put("NoTelp", telephone.getText().toString());
                map.put("Email", email.getText().toString());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void UpdateAgen() {
        PDEditAkun.setMessage("Proses Update...");
        PDEditAkun.setCancelable(false);
        PDEditAkun.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_AGEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Preferences.setKeyUsername(getBaseContext(),username.getText().toString());
                        Preferences.setKeyNama(getBaseContext(), nama.getText().toString());
                        Preferences.setKeyNoTelp(getBaseContext(), telephone.getText().toString());
                        Preferences.setKeyEmail(getBaseContext(), email.getText().toString());
                        Preferences.setKeyAlamatDomisili(getBaseContext(), alamatdomisili.getText().toString());
                        Preferences.setKeyFacebook(getBaseContext(), facebook.getText().toString());
                        Preferences.setKeyInstagram(getBaseContext(), instagram.getText().toString());
                        finish();
                    }
                });

                Glide.with(EditAkunActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(EditAkunActivity.this)
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
                map.put("Username", username.getText().toString().trim());
                map.put("Nama", nama.getText().toString().trim());
                map.put("NoTelp", telephone.getText().toString().trim());
                map.put("Email", email.getText().toString().trim());
                map.put("AlamatDomisili", alamatdomisili.getText().toString().trim());
                map.put("Facebook", facebook.getText().toString().trim());
                map.put("Instagram", instagram.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void UpdateMitra() {
        PDEditAkun.setMessage("Proses Update...");
        PDEditAkun.setCancelable(false);
        PDEditAkun.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_MITRA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Preferences.setKeyUsername(getBaseContext(), username.getText().toString());
                        Preferences.setKeyNama(getBaseContext(), nama.getText().toString());
                        Preferences.setKeyNoTelp(getBaseContext(), telephone.getText().toString());
                        Preferences.setKeyEmail(getBaseContext(), email.getText().toString());
                        finish();
                    }
                });

                Glide.with(EditAkunActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Edit Akun Gagal");
                ok.setVisibility(View.GONE);

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(EditAkunActivity.this)
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
                map.put("Username", username.getText().toString().trim());
                map.put("Nama", nama.getText().toString().trim());
                map.put("NoTelp", telephone.getText().toString().trim());
                map.put("Email", email.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void UpdateKantorLain() {
        PDEditAkun.setMessage("Proses Update...");
        PDEditAkun.setCancelable(false);
        PDEditAkun.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_KL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Preferences.setKeyUsername(getBaseContext(), username.getText().toString());
                        Preferences.setKeyNama(getBaseContext(), namakantor.getText().toString());
                        Preferences.setKeyNoTelp(getBaseContext(), telephone.getText().toString());
                        Preferences.setKeyEmail(getBaseContext(), email.getText().toString());
                        finish();
                    }
                });

                Glide.with(EditAkunActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDEditAkun.cancel();
                Dialog customDialog = new Dialog(EditAkunActivity.this);
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
                dialogTitle.setText("Edit Akun Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(EditAkunActivity.this)
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
                map.put("Username", username.getText().toString().trim());
                map.put("Nama", namakantor.getText().toString().trim());
                map.put("NoTelp", telephone.getText().toString().trim());
                map.put("Email", email.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}