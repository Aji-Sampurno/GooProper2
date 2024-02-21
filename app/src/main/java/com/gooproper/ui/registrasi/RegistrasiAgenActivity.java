package com.gooproper.ui.registrasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.ui.LoginActivity;
import com.gooproper.R;
import com.gooproper.ui.tambah.TambahListingActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RegistrasiAgenActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST = 1;
    final int CODE_CAMERA_REQUEST = 2;
    final int KODE_REQUEST_KAMERA = 3;
    final int CODE_GALLERY_REQUEST_KTP = 4;
    final int CODE_CAMERA_REQUEST_KTP = 5;
    final int KODE_REQUEST_KAMERA_KTP = 6;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE= 7;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES = 8;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_KTP = 9;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_KTP = 10;
    Bitmap bitmap, bitmappas;
    Uri UriPas, UriKtp;
    String sktp, spas, Password, RePassword, RandomPassword, StringPas, StringKtp;
    LinearLayout agen, Mitra, KantorLain;
    EditText namalengkap, nowa, email, kotakelahiran, tglkelahiran, pendterakhir, sekolahterakhir, masakerja, jabatan, konfirmasi, domisili, facebook, instagram, noktp, konfirmasinpwp, nonpwp;
    EditText UsernameKantorLain, NamaKantorLain, NoTelpKantorLain, EmailKantorLain, NPWP, PasswordKantorLain, RePasswordKantorLain;
    EditText UsernameMitra, NamaLengkapMitra, NoTelpMitra, EmailMitra, KotaKelahiranMitra, TglKelahiranMitra, PasswordMitra, RePasswordMitra;
    RadioGroup radioGroup;
    RadioButton rbagen, rbmitra, rbkantorlain;
    Button submit, batal, SubmitMitra, BatalMitra, SubmitKantorLain, BatalKantroLain, upload, pas;
    ImageView back, imgktp, imgpas;
    TextInputLayout LytNpwp;
    String timeStamp, FotoPas, FotoKtp;
    StorageReference storageRef, ImgPas, ImgKtp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi_agen);

        radioGroup = findViewById(R.id.rgakses);
        rbagen = findViewById(R.id.rbagen);
        rbmitra = findViewById(R.id.rbmitra);
        rbkantorlain = findViewById(R.id.rbkantorlain);

        agen = findViewById(R.id.lytregisagen);
        LytNpwp = findViewById(R.id.lytnonpwp);
        upload = findViewById(R.id.uploadktp);
        pas = findViewById(R.id.uploadpasfoto);
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
        konfirmasinpwp = findViewById(R.id.etkonfirmasinpwp);
        nonpwp = findViewById(R.id.etnonpwp);
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
        SubmitMitra = findViewById(R.id.BtnSubmitRegistrasiMitra);
        BatalMitra = findViewById(R.id.BtnBatalRegistrasiMitra);

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

        pDialog = new ProgressDialog(RegistrasiAgenActivity.this);

        RandomPassword = generateRandomPassword(12);
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        FotoPas = "Pas_" + timeStamp + ".jpg";
        FotoKtp = "KTP_" + timeStamp + ".jpg";

        storageRef = FirebaseStorage.getInstance().getReference();
        ImgPas = storageRef.child("profil/" + FotoPas);
        ImgKtp = storageRef.child("ktp/" + FotoKtp);

        pendterakhir.setOnClickListener(v -> showEducationPopup(v));
        konfirmasi.setOnClickListener(v -> showKonfirmasiPopup(v));
        konfirmasinpwp.setOnClickListener(v -> showKonfirmasiNpwpPopup(v));
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
                if (UriKtp == null) {
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
        submit.setOnClickListener(v -> {
            if (validateAgent()) {
                handleImagePasSuccess();
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
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbagen) {
                agen.setVisibility(View.VISIBLE);
                Mitra.setVisibility(View.GONE);
                KantorLain.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbmitra) {
                agen.setVisibility(View.GONE);
                Mitra.setVisibility(View.VISIBLE);
                KantorLain.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbkantorlain) {
                agen.setVisibility(View.GONE);
                Mitra.setVisibility(View.GONE);
                KantorLain.setVisibility(View.VISIBLE);
            }
        });
        konfirmasinpwp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Ya")) {
                    LytNpwp.setVisibility(View.VISIBLE);
                } else {
                    LytNpwp.setVisibility(View.GONE);
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void showProgressDialog() {
        pDialog.setMessage("Unggah Gambar");
        pDialog.setCancelable(false);
        pDialog.show();
    }
    private void HideProgressDialog() {
        pDialog.dismiss();
        pDialog.cancel();
    }
    private void handleImagePasSuccess() {
        if (UriPas != null) {
            showProgressDialog();
            ImgPas.putFile(UriPas)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgPas.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        StringPas = imageUrl;
                                        handleImageKTPSuccess();
                                    })
                                    .addOnFailureListener(exception -> {
                                        HideProgressDialog();
                                        handleImagePasSuccess();
                                        Toast.makeText(RegistrasiAgenActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            HideProgressDialog();
                            handleImagePasSuccess();
                            Toast.makeText(RegistrasiAgenActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            StringPas = "0";
            handleImageKTPSuccess();
        }
    }
    private void handleImageKTPSuccess() {
        if (UriKtp != null) {
            ImgKtp.putFile(UriKtp)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImgKtp.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        StringKtp = imageUrl;
                                        HideProgressDialog();
                                        regisagen();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageKTPSuccess();
                                        Toast.makeText(RegistrasiAgenActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageKTPSuccess();
                            Toast.makeText(RegistrasiAgenActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            StringKtp = "0";
            HideProgressDialog();
            regisagen();
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
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
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

                                dialogTitle.setText("Berhasil Registrasi Agen");
                                cobalagi.setVisibility(View.GONE);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                        finish();
                                    }
                                });

                                Glide.with(RegistrasiAgenActivity.this)
                                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
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

                                dialogTitle.setText("Gagal Registrasi Agen");
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
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                map.put("Konfirmasi", konfirmasi.getText().toString().trim());
                map.put("AlamatDomisili", domisili.getText().toString().trim());
                map.put("Facebook", facebook.getText().toString().trim());
                map.put("Instagram", instagram.getText().toString().trim());
                map.put("Npwp", nonpwp.getText().toString().trim());
                map.put("NoKtp", noktp.getText().toString().trim());
                map.put("ImgKtp", StringKtp);
                map.put("Photo", StringPas);
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
                map.put("Username", UsernameMitra.getText().toString().trim());
                map.put("Nama", NamaLengkapMitra.getText().toString().trim());
                map.put("NoTelp", NoTelpMitra.getText().toString().trim());
                map.put("Email", EmailMitra.getText().toString().trim());
                map.put("KotaKelahiran", KotaKelahiranMitra.getText().toString().trim());
                map.put("TglLahir", TglKelahiranMitra.getText().toString().trim());
                map.put("Password", PasswordMitra.getText().toString().trim());
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
                map.put("Username", UsernameKantorLain.getText().toString().trim());
                map.put("Nama", NamaKantorLain.getText().toString().trim());
                map.put("NoTelp", NoTelpKantorLain.getText().toString().trim());
                map.put("Email", EmailKantorLain.getText().toString().trim());
                map.put("Npwp", NPWP.getText().toString().trim());
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
    public void showKonfirmasiNpwpPopup(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Apakah Anda Memiliki NPWP?");

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
                TextView textViewEducation = findViewById(R.id.etkonfirmasinpwp);
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
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriPas = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriPas);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA);
            }
        }
    }
    private void bukaKameraKtp() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                UriKtp = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, UriKtp);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA_KTP);
            }
        }
    }
    private File createImageFile() {
        String imageFileName = "JPEG_" + new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
    private void requestPermissionsPas() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES);
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
        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES) {
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
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST);
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
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_KTP);
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
            UriPas = data.getData();
            imgpas.setImageURI(UriPas);
            imgpas.setVisibility(View.VISIBLE);
        } else if (requestCode == CODE_GALLERY_REQUEST_KTP && resultCode == RESULT_OK && data != null) {
            UriKtp = data.getData();
            imgktp.setImageURI(UriKtp);
            imgktp.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA && resultCode == RESULT_OK) {
            imgpas.setImageURI(UriPas);
            imgpas.setVisibility(View.VISIBLE);
        } else if (requestCode == KODE_REQUEST_KAMERA_KTP && resultCode == RESULT_OK) {
            imgktp.setImageURI(UriKtp);
            imgktp.setVisibility(View.VISIBLE);
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "pelamar");
            }
            return null;
        }
        @Override
        protected void onPostExecute(String response) {
            if (response != null) {
                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void sendNotificationToToken(String token, String notificationType) {
        String title = Preferences.getKeyNama(this);
        String message = "Terdapat Pelamar Agen Baru";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}