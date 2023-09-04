package com.gooproper.ui.registrasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.snackbar.Snackbar;
import com.gooproper.LoginActivity;
import com.gooproper.R;
import com.gooproper.SettingActivity;
import com.gooproper.customer.MainCustomerActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrasiAgenActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST = 100;
    final int CODE_CAMERA_REQUEST = 101;
    final int KODE_REQUEST_KAMERA = 102;
    final int CODE_GALLERY_REQUEST_KTP = 103;
    final int CODE_CAMERA_REQUEST_KTP = 104;
    final int KODE_REQUEST_KAMERA_KTP = 105;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PAS = 123;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PAS = 456;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_KTP = 123;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_KTP = 456;
    Bitmap bitmap, bitmappas, bitsig;
    String sktp, spas, sttd, Password, RePassword, RandomPassword;
    SignaturePad signaturePad;
    LinearLayout agen, Mitra, KantorLain;
    EditText namalengkap, nowa, email, kotakelahiran, tglkelahiran, pendterakhir, sekolahterakhir, masakerja, jabatan, konfirmasi, domisili, facebook, instagram, noktp;
    EditText UsernameKantorLain, NamaKantorLain, NoTelpKantorLain, EmailKantorLain, NPWP, PasswordKantorLain, RePasswordKantorLain;
    EditText UsernameMitra, NamaLengkapMitra, NoTelpMitra, EmailMitra, KotaKelahiranMitra, TglKelahiranMitra, PasswordMitra, RePasswordMitra;
    RadioGroup radioGroup;
    RadioButton rbagen, rbmitra, rbkantorlain;
    Button submit, batal, SubmitMitra, BatalMitra, UploadFotoMitra, SubmitKantorLain, BatalKantroLain, UploadFotoKantorLain, upload, pas, clear;
    ImageView back, imgktp, imgpas, IVFotoMitra, IVFotoKantorLain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_agen);

        signaturePad = findViewById(R.id.signature);
        radioGroup = findViewById(R.id.rgakses);
        rbagen = findViewById(R.id.rbagen);
        rbmitra = findViewById(R.id.rbmitra);
        rbkantorlain = findViewById(R.id.rbkantorlain);
        agen = findViewById(R.id.lytregisagen);
        upload = findViewById(R.id.uploadktp);
        pas = findViewById(R.id.uploadpasfoto);
        clear = findViewById(R.id.clearsignature);
        submit = findViewById(R.id.btnsubmit);
        batal = findViewById(R.id.btnbatal);
        back = findViewById(R.id.backFormBtn);
        imgktp = findViewById(R.id.imageViewPhoto);
        imgpas = findViewById(R.id.imageViewPasPhoto);
        namalengkap = findViewById(R.id.etnamalengkap);
        nowa = findViewById(R.id.etnowa);
        email = findViewById(R.id.etemail);
        kotakelahiran = findViewById(R.id.etkotakelahiran);
        tglkelahiran = findViewById(R.id.ettgllahir);
        sekolahterakhir = findViewById(R.id.etnamasekolahterakhir);
        masakerja = findViewById(R.id.etmasakerja);
        jabatan = findViewById(R.id.etjabatan);
        konfirmasi = findViewById(R.id.etkonfirmasi);
        domisili = findViewById(R.id.etdomisili);
        facebook = findViewById(R.id.etfacebook);
        instagram = findViewById(R.id.etinstagram);
        noktp = findViewById(R.id.etnoktp);
        pendterakhir = findViewById(R.id.etpendidikanterakhir);

        Mitra = findViewById(R.id.LytRegistrasiMitra);
        UsernameMitra = findViewById(R.id.ETUsernameRegistrasiMitra);
        NamaLengkapMitra = findViewById(R.id.ETNamaLengkapRegistrasiMitra);
        NoTelpMitra = findViewById(R.id.ETNoTelpRegistrasiMitra);
        EmailMitra = findViewById(R.id.ETEmailRegistrasiMitra);
        KotaKelahiranMitra = findViewById(R.id.ETKotaKelahiranRegistrasiMitra);
        TglKelahiranMitra = findViewById(R.id.ETTglLahirRegistrasiMitra);
        PasswordMitra = findViewById(R.id.ETPasswordRegistrasiMitra);
        RePasswordMitra = findViewById(R.id.ETRePasswordRegistrasiMitra);
        UploadFotoMitra = findViewById(R.id.BtnUploadFotoRegistrasiMitra);
        SubmitMitra = findViewById(R.id.BtnSubmitRegistrasiMitra);
        BatalMitra = findViewById(R.id.BtnBatalRegistrasiMitra);
        IVFotoMitra = findViewById(R.id.IVFotoRegistrasiMitra);

        KantorLain = findViewById(R.id.LytRegistrasiKantorLain);
        UsernameKantorLain = findViewById(R.id.ETUsernameRegistrasiKantorLain);
        NamaKantorLain = findViewById(R.id.ETNamaKantorRegistrasiKantorLain);
        NoTelpKantorLain = findViewById(R.id.ETNoTelpRegistrasiKantorLain);
        EmailKantorLain = findViewById(R.id.ETEmailRegistrasiKantorLain);
        NPWP = findViewById(R.id.ETNPWPRegistrasiKantorLain);
        PasswordKantorLain = findViewById(R.id.ETPasswordRegistrasiKantorLain);
        RePasswordKantorLain = findViewById(R.id.ETRePasswordRegistrasiKantorLain);
        SubmitKantorLain = findViewById(R.id.BtnSubmitRegistrasiKantorLain);
        BatalKantroLain = findViewById(R.id.BtnBatalRegistrasiKantorLain);
        UploadFotoKantorLain = findViewById(R.id.BtnUploadFotoRegistrasiKantorLain);
        IVFotoKantorLain = findViewById(R.id.IVFotoRegistrasiKantorLain);

        pDialog = new ProgressDialog(RegistrasiAgenActivity.this);

        RandomPassword = generateRandomPassword(12);

        pendterakhir.setOnClickListener(v -> showEducationPopup(v));
        konfirmasi.setOnClickListener(v -> showKonfirmasiPopup(v));
        back.setOnClickListener(v -> finish());
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showKtpSelectionDialog();
            }
        });
        pas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bitmap == null) {
                    Dialog customDialog = new Dialog(RegistrasiAgenActivity.this);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.custom_dialog_eror_input);

                    if (customDialog.getWindow() != null) {
                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    }

                    Button ok = customDialog.findViewById(R.id.BtnOkErorInput);

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                    Glide.with(RegistrasiAgenActivity.this)
                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifImageView);

                    customDialog.show();
                } else {
                    showPhotoSelectionDialog();
                }
            }
        });
        UploadFotoMitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog();
            }
        });
        UploadFotoKantorLain.setOnClickListener(v -> showPhotoSelectionDialog());
        clear.setOnClickListener(v -> signaturePad.clear());
        submit.setOnClickListener(v -> {
            if (validateAgent()) {
                regisagen();
            }
        });
        SubmitMitra.setOnClickListener(v -> {
            if (validateMitra()) {
                Password = PasswordMitra.getText().toString();
                RePassword = RePasswordMitra.getText().toString();
                if (Password.length() >= 8) {
                    if (RePassword.equals(Password)) {
                        regismitra();
                    } else {
                        RePasswordMitra.setError("Password Berdeda");
                        RePasswordMitra.requestFocus();
                    }
                } else {
                    PasswordMitra.setError("Password kurang dari 8 karakter");
                    PasswordMitra.requestFocus();
                }

            }
        });
        SubmitKantorLain.setOnClickListener(v -> {
            if (validateKantorLain()) {
                Password = PasswordKantorLain.getText().toString();
                RePassword = RePasswordKantorLain.getText().toString();
                if (Password.length() >= 8) {
                    if (RePassword.equals(Password)) {
                        regiskl();
                    } else {
                        RePasswordKantorLain.setError("Password Berdeda");
                        RePasswordKantorLain.requestFocus();
                    }
                } else {
                    PasswordKantorLain.setError("Password Berdeda");
                    PasswordKantorLain.requestFocus();
                }

            }
        });
        batal.setOnClickListener(v -> finish());
        BatalMitra.setOnClickListener(v -> finish());
        BatalKantroLain.setOnClickListener(v -> finish());

        tglkelahiran.setOnClickListener(new View.OnClickListener() {
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
                        tglkelahiran.setText(MessageFormat.format("{0}", tanggal));
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        TglKelahiranMitra.setOnClickListener(new View.OnClickListener() {
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
                        TglKelahiranMitra.setText(MessageFormat.format("{0}", tanggal));
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                bitsig = signaturePad.getSignatureBitmap();
            }

            @Override
            public void onClear() {
                signaturePad.clear();
            }
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbagen) {
                agen.setVisibility(View.VISIBLE);
                Mitra.setVisibility(View.GONE);
                KantorLain.setVisibility(View.GONE);
                signaturePad.setVisibility(View.VISIBLE);
            } else if (checkedId == R.id.rbmitra) {
                agen.setVisibility(View.GONE);
                Mitra.setVisibility(View.VISIBLE);
                KantorLain.setVisibility(View.GONE);
                signaturePad.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbkantorlain) {
                agen.setVisibility(View.GONE);
                Mitra.setVisibility(View.GONE);
                KantorLain.setVisibility(View.VISIBLE);
                signaturePad.setVisibility(View.GONE);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void regisagen() {
        pDialog.setMessage("Proses Registrasi...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REGISTRASI_AGEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            pDialog.cancel();
                            Dialog customDialog = new Dialog(RegistrasiAgenActivity.this);
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
                            dialogTitle.setText("Registrasi Berhasil");

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(RegistrasiAgenActivity.this, LoginActivity.class));
                                    finish();
                                }
                            });

                            Glide.with(RegistrasiAgenActivity.this)
                                    .load(R.mipmap.ic_yes)
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.cancel();
                        pDialog.cancel();
                        Dialog customDialog = new Dialog(RegistrasiAgenActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Registrasi Gagal");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(RegistrasiAgenActivity.this)
                                .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (bitmap == null) {
                    sktp = "0";
                } else {
                    sktp = imageToString(bitmap);
                }
                if (bitmappas == null) {
                    spas = "0";
                } else {
                    spas = imageToString(bitmappas);
                }
                if (bitsig == null) {
                    sttd = "0";
                } else {
                    sttd = imageToString(bitsig);
                }
                map.put("Username", namalengkap.getText().toString().trim());
                map.put("Password", RandomPassword);
                map.put("Nama", namalengkap.getText().toString().trim());
                map.put("NoTelp", nowa.getText().toString().trim());
                map.put("Email", email.getText().toString().trim());
                map.put("KotaKelahiran", kotakelahiran.getText().toString().trim());
                map.put("TglLahir", tglkelahiran.getText().toString().trim());
                map.put("Pendidikan", pendterakhir.getText().toString().trim());
                map.put("NamaSekolah", sekolahterakhir.getText().toString().trim());
                map.put("MasaKerja", masakerja.getText().toString().trim());
                map.put("Jabatan", jabatan.getText().toString().trim());
                map.put("AlamatDomisili", domisili.getText().toString().trim());
                map.put("Facebook", facebook.getText().toString().trim());
                map.put("Instagram", instagram.getText().toString().trim());
                map.put("NoKtp", noktp.getText().toString().trim());
                map.put("ImgKtp", sktp);
                map.put("ImgTtd", sttd);
                map.put("Photo", spas);
                System.out.println(map);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void regismitra() {
        pDialog.setMessage("Proses Registrasi...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REGISTRASI_MITRA, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(RegistrasiAgenActivity.this);
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
                dialogTitle.setText("Registrasi Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RegistrasiAgenActivity.this, LoginActivity.class));
                        finish();
                    }
                });

                Glide.with(RegistrasiAgenActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(RegistrasiAgenActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Registrasi Gagal");
                ok.setVisibility(View.GONE);

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(RegistrasiAgenActivity.this)
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
                if (bitmap == null) {
                    spas = "0";
                } else {
                    spas = imageToString(bitmappas);
                }
                map.put("Username", UsernameMitra.getText().toString().trim());
                map.put("Nama", NamaLengkapMitra.getText().toString().trim());
                map.put("NoTelp", NoTelpMitra.getText().toString().trim());
                map.put("Email", EmailMitra.getText().toString().trim());
                map.put("KotaKelahiran", KotaKelahiranMitra.getText().toString().trim());
                map.put("TglLahir", TglKelahiranMitra.getText().toString().trim());
                map.put("Password", PasswordMitra.getText().toString().trim());
                map.put("Photo", spas);
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void regiskl() {
        pDialog.setMessage("Proses Registrasi...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_REGISTRASI_KL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(RegistrasiAgenActivity.this);
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
                dialogTitle.setText("Registrasi Berhasil");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(RegistrasiAgenActivity.this, LoginActivity.class));
                        finish();
                    }
                });

                Glide.with(RegistrasiAgenActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(RegistrasiAgenActivity.this);
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
                dialogTitle.setText("Registrasi Gagal");

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(RegistrasiAgenActivity.this)
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
                if (bitmap == null) {
                    spas = "0";
                } else {
                    spas = imageToString(bitmappas);
                }
                map.put("Username", UsernameKantorLain.getText().toString().trim());
                map.put("Nama", NamaKantorLain.getText().toString().trim());
                map.put("NoTelp", NoTelpKantorLain.getText().toString().trim());
                map.put("Email", EmailKantorLain.getText().toString().trim());
                map.put("Npwp", NPWP.getText().toString().trim());
                map.put("Photo", spas);
                map.put("Password", PasswordKantorLain.getText().toString().trim());
                System.out.println(map);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean validateAgent() {
        if (namalengkap.getText().toString().equals("")) {
            namalengkap.setError("Harap Isi Nama Lengkap");
            namalengkap.requestFocus();
            return false;
        }
        if (nowa.getText().toString().equals("")) {
            nowa.setError("Harap Isi Nomor WhatsApp");
            nowa.requestFocus();
            return false;
        }
        if (email.getText().toString().equals("")) {
            email.setError("Harap Isi Email");
            email.requestFocus();
            return false;
        }
        if (kotakelahiran.getText().toString().equals("")) {
            kotakelahiran.setError("Harap Isi Kota Kelahiran");
            kotakelahiran.requestFocus();
            return false;
        }
        if (tglkelahiran.getText().toString().equals("")) {
            tglkelahiran.setError("Harap Isi Tanggal Kelahiran");
            tglkelahiran.requestFocus();
            return false;
        }
        if (pendterakhir.getText().toString().equals("")) {
            pendterakhir.setError("Harap Isi Pendidikan Terakhir");
            pendterakhir.requestFocus();
            return false;
        }
        if (domisili.getText().toString().equals("")) {
            domisili.setError("Harap Isi Domisili");
            domisili.requestFocus();
            return false;
        }
        if (noktp.getText().toString().equals("")) {
            noktp.setError("Harap Isi Nomor KTP");
            noktp.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateKantorLain() {
        if (NamaKantorLain.getText().toString().equals("")) {
            NamaKantorLain.setError("Harap Isi Nama Kantor");
            NamaKantorLain.requestFocus();
            return false;
        }
        if (NoTelpKantorLain.getText().toString().equals("")) {
            NamaKantorLain.setError("Harap Isi Nomor WhatsApp");
            NamaKantorLain.requestFocus();
            return false;
        }
        if (EmailKantorLain.getText().toString().equals("")) {
            EmailKantorLain.setError("Harap Isi Email");
            EmailKantorLain.requestFocus();
            return false;
        }
        if (NPWP.getText().toString().equals("")) {
            NPWP.setError("Harap Isi NPWP");
            NPWP.requestFocus();
            return false;
        }
        if (PasswordKantorLain.getText().toString().equals("")) {
            PasswordKantorLain.setError("Harap Isi Password");
            PasswordKantorLain.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateMitra() {
        if (UsernameMitra.getText().toString().equals("")) {
            UsernameMitra.setError("Harap Isi Username");
            UsernameMitra.requestFocus();
            return false;
        }
        if (NamaLengkapMitra.getText().toString().equals("")) {
            NamaLengkapMitra.setError("Harap Isi Nama Lengkap");
            NamaLengkapMitra.requestFocus();
            return false;
        }
        if (NoTelpMitra.getText().toString().equals("")) {
            NoTelpMitra.setError("Harap Isi Nomor WhatsApp");
            NoTelpMitra.requestFocus();
            return false;
        }
        if (EmailMitra.getText().toString().equals("")) {
            EmailMitra.setError("Harap Isi Email");
            EmailMitra.requestFocus();
            return false;
        }
        if (KotaKelahiranMitra.getText().toString().equals("")) {
            KotaKelahiranMitra.setError("Harap Isi Kota Kelahiran");
            KotaKelahiranMitra.requestFocus();
            return false;
        }
        if (TglKelahiranMitra.getText().toString().equals("")) {
            TglKelahiranMitra.setError("Harap Isi Tanggal Kelahiran");
            TglKelahiranMitra.requestFocus();
            return false;
        }
        if (PasswordMitra.getText().toString().equals("")) {
            PasswordMitra.setError("Harap Isi Password");
            PasswordMitra.requestFocus();
            return false;
        }
        return true;
    }

    public void showEducationPopup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pendidikan terakhir");

        final CharSequence[] educations = {"SMA", "D3", "S1", "S2", "S3"};
        final int[] selectedEducationIndex = {0}; // to store the index of the selected education

        builder.setSingleChoiceItems(educations, selectedEducationIndex[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedEducationIndex[0] = which; // update the selected education index
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the TextView with the selected education
                TextView textViewEducation = findViewById(R.id.etpendidikanterakhir);
                textViewEducation.setText(educations[selectedEducationIndex[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showKonfirmasiPopup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah Anda Dari Agen Properti Lain?");

        final CharSequence[] educations = {"Ya", "Tidak"};
        final int[] selectedKonfirmasiIndex = {0}; // to store the index of the selected education

        builder.setSingleChoiceItems(educations, selectedKonfirmasiIndex[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedKonfirmasiIndex[0] = which; // update the selected education index
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Update the TextView with the selected education
                TextView textViewEducation = findViewById(R.id.etkonfirmasi);
                textViewEducation.setText(educations[selectedKonfirmasiIndex[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showPhotoSelectionDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Upload Foto")
                .setItems(new CharSequence[]{"Ambil Dari Kamera", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(RegistrasiAgenActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST);
                                break;
                            case 1:
                                requestPermissionsPas();
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void showKtpSelectionDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("Upload KTP")
                .setItems(new CharSequence[]{"Ambil Dari Kamera", "Pilih Dari Galeri"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(RegistrasiAgenActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST_KTP);
                                break;
                            case 1:
                                requestPermissionsKtp();
                                break;
                        }
                    }
                });

        builder.show();
    }

    private void bukaKamera() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA);
        }
    }

    private void bukaKameraKtp() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_KTP);
        }
    }

    private void requestPermissionsPas() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PAS);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PAS);
        }
    }

    private void requestPermissionsKtp() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_KTP);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_KTP);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_PAS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_PAS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_KTP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_KTP);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_KTP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_KTP);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), CODE_GALLERY_REQUEST);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiAgenActivity.this);
                builder.setTitle("Gagal").
                        setMessage("Tidak Mendapatkan Izin Untuk Mengakses Galeri");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert11 = builder.create();
                alert11.show();
            }
            return;
        } else if (requestCode == CODE_GALLERY_REQUEST_KTP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), CODE_GALLERY_REQUEST_KTP);
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiAgenActivity.this);
                builder.setTitle("Gagal").
                        setMessage("Tidak Mendapatkan Izin Untuk Mengakses Galeri");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert11 = builder.create();
                alert11.show();
            }

            return;
        } else if (requestCode == CODE_CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiAgenActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

            return;
        } else if (requestCode == CODE_CAMERA_REQUEST_KTP) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKameraKtp();
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegistrasiAgenActivity.this);
                builder.setTitle("Izin Kamera Ditolak").
                        setMessage("Aplikasi memerlukan izin kamera untuk mengambil gambar.");
                builder.setPositiveButton("OK",
                        (dialog, id) -> dialog.cancel());
                AlertDialog alert = builder.create();
                alert.show();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            cropImage(filePath);
        } else if (requestCode == CODE_GALLERY_REQUEST_KTP && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            cropImageKtp(filePath);
        } else if (requestCode == KODE_REQUEST_KAMERA && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    cropImage(getImageUri(this, imageBitmap));
                }
            }
        } else if (requestCode == KODE_REQUEST_KAMERA_KTP && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    cropImageKtp(getImageUri(this, imageBitmap));
                }
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri croppedUri = result.getUri();
                try {
                    if (agen.getVisibility() == View.VISIBLE) {
                        if (bitmap == null) {
                            InputStream inputStream = getContentResolver().openInputStream(croppedUri);
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            imgktp.setImageBitmap(bitmap);
                        } else {
                            InputStream inputStream = getContentResolver().openInputStream(croppedUri);
                            bitmappas = BitmapFactory.decodeStream(inputStream);
                            imgpas.setImageBitmap(bitmappas);
                        }
                    } else if (Mitra.getVisibility() == View.VISIBLE) {
                        if (bitmap == null) {
                            InputStream inputStream = getContentResolver().openInputStream(croppedUri);
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            IVFotoMitra.setImageBitmap(bitmap);
                        }
                    } else {
                        if (bitmap == null) {
                            InputStream inputStream = getContentResolver().openInputStream(croppedUri);
                            bitmap = BitmapFactory.decodeStream(inputStream);
                            IVFotoKantorLain.setImageBitmap(bitmap);
                        }
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                error.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void cropImage(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image"));
        CropImage.activity(sourceUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3, 4)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setOutputUri(destinationUri)
                .start(this);
    }

    private void cropImageKtp(Uri sourceUri) {
        Uri destinationUri = Uri.fromFile(new File(getCacheDir(), "cropped_image"));
        CropImage.activity(sourceUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(3, 2)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setOutputUri(destinationUri)
                .start(this);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
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

    private String generateRandomPassword(int length) {
        final String charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();

        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(charset.length());
            char randomChar = charset.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }

}