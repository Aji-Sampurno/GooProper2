package com.gooproper.ui.registrasi;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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
import com.gooproper.LoginActivity;
import com.gooproper.R;
import com.gooproper.util.ServerApi;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class RegistrasiAgenActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST = 999;
    final int CODE_CAMERA_REQUEST = 101;
    final int KODE_REQUEST_KAMERA = 102;
    Bitmap bitmap, bitmappas, bitsig;
    String sktp, spas, sttd;
    SignaturePad signaturePad;
    EditText namalengkap, nowa, email, kotakelahiran, tglkelahiran, pendterakhir, sekolahterakhir, masakerja, jabatan, konfirmasi, domisili, facebook, instagram, noktp, namakantor, nowakantor, emailkantor, npwp, namamitra, nowamitra, emailmitra, kotakelahiranmitra, tglkelahiranmitra;
    RadioGroup radioGroup;
    RadioButton rbagen, rbmitra, rbkantorlain;
    LinearLayout agen, mitra, kantorlain;
    Button submit, batal, submitmitra, batalmitra, submitkl, batalkl, upload, pas, clear;
    ImageView back, imgktp, imgpas;

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
        mitra = findViewById(R.id.lytregismitra);
        kantorlain = findViewById(R.id.lytregiskantorlain);
        upload = findViewById(R.id.uploadktp);
        pas = findViewById(R.id.uploadpasfoto);
        clear = findViewById(R.id.clearsignature);
        submit = findViewById(R.id.btnsubmit);
        batal = findViewById(R.id.btnbatal);
        submitmitra = findViewById(R.id.btnsubmitmitra);
        batalmitra = findViewById(R.id.btnbatalmitra);
        submitkl = findViewById(R.id.btnsubmitkl);
        batalkl = findViewById(R.id.btnbatalkl);
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
        namakantor = findViewById(R.id.etnamakantor);
        nowakantor = findViewById(R.id.etnowakantor);
        emailkantor = findViewById(R.id.etemailkantor);
        npwp = findViewById(R.id.etnpwp);
        namamitra = findViewById(R.id.etnamalengkapmitra);
        nowamitra = findViewById(R.id.etnowamitra);
        emailmitra = findViewById(R.id.etemailmitra);
        kotakelahiranmitra = findViewById(R.id.etkotakelahiranmitra);
        tglkelahiranmitra = findViewById(R.id.ettgllahirmitra);
        pendterakhir = findViewById(R.id.etpendidikanterakhir);
        pDialog = new ProgressDialog(RegistrasiAgenActivity.this);

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

        tglkelahiranmitra.setOnClickListener(new View.OnClickListener() {
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
                        tglkelahiranmitra.setText(MessageFormat.format("{0}", tanggal));
                    }
                });
                materialDatePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        pendterakhir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEducationPopup(view);
            }
        });

        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showKonfirmasiPopup(view);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog();
            }
        });

        pas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPhotoSelectionDialog();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signaturePad.clear();
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

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateAgent()) {
                    regisagen();
                }
            }
        });

        submitmitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateMitra()) {
                    regismitra();
                }
            }
        });

        submitkl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateKantorLain()) {
                    regiskl();
                }
            }
        });

        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        batalmitra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        batalkl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbagen) {
                agen.setVisibility(View.VISIBLE);
                mitra.setVisibility(View.GONE);
                kantorlain.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbmitra) {
                agen.setVisibility(View.GONE);
                mitra.setVisibility(View.VISIBLE);
                kantorlain.setVisibility(View.GONE);
            } else if (checkedId == R.id.rbkantorlain) {
                agen.setVisibility(View.GONE);
                mitra.setVisibility(View.GONE);
                kantorlain.setVisibility(View.VISIBLE);
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
                map.put("Password", "agen" + namalengkap.getText().toString().trim());
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
                map.put("Username", namamitra.getText().toString().trim());
                map.put("Nama", namamitra.getText().toString().trim());
                map.put("NoTelp", nowamitra.getText().toString().trim());
                map.put("Email", emailmitra.getText().toString().trim());
                map.put("KotaKelahiran", kotakelahiranmitra.getText().toString().trim());
                map.put("TglLahir", tglkelahiranmitra.getText().toString().trim());
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
                map.put("Username", namakantor.getText().toString().trim());
                map.put("Nama", namakantor.getText().toString().trim());
                map.put("NoTelp", nowakantor.getText().toString().trim());
                map.put("Email", emailkantor.getText().toString().trim());
                map.put("Npwp", npwp.getText().toString().trim());
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
        if (namakantor.getText().toString().equals("")) {
            namakantor.setError("Harap Isi Nama Kantor");
            namakantor.requestFocus();
            return false;
        }
        if (nowakantor.getText().toString().equals("")) {
            nowakantor.setError("Harap Isi Nomor WhatsApp");
            nowakantor.requestFocus();
            return false;
        }
        if (emailkantor.getText().toString().equals("")) {
            emailkantor.setError("Harap Isi Email");
            emailkantor.requestFocus();
            return false;
        }
        if (npwp.getText().toString().equals("")) {
            npwp.setError("Harap Isi NPWP");
            npwp.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validateMitra() {
        if (namamitra.getText().toString().equals("")) {
            namamitra.setError("Harap Isi Nama Lengkap");
            namamitra.requestFocus();
            return false;
        }
        if (nowamitra.getText().toString().equals("")) {
            nowamitra.setError("Harap Isi Nomor WhatsApp");
            nowamitra.requestFocus();
            return false;
        }
        if (emailmitra.getText().toString().equals("")) {
            emailmitra.setError("Harap Isi Email");
            emailmitra.requestFocus();
            return false;
        }
        if (kotakelahiranmitra.getText().toString().equals("")) {
            kotakelahiranmitra.setError("Harap Isi Kota Kelahiran");
            kotakelahiranmitra.requestFocus();
            return false;
        }
        if (tglkelahiranmitra.getText().toString().equals("")) {
            tglkelahiranmitra.setError("Harap Isi Tanggal Kelahiran");
            tglkelahiranmitra.requestFocus();
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
        builder.setTitle("Select Photo")
                .setItems(new CharSequence[]{"Take Photo", "Choose from Gallery"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                ActivityCompat.requestPermissions(RegistrasiAgenActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST);
                                break;
                            case 1:
                                ActivityCompat.requestPermissions(RegistrasiAgenActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, CODE_GALLERY_REQUEST);
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST) {
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
        }

        if (requestCode == CODE_CAMERA_REQUEST) {
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
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri filePath = data.getData();
            cropImage(filePath);
        } else if (requestCode == KODE_REQUEST_KAMERA && resultCode == RESULT_OK) {
            if (data != null && data.getExtras() != null) {
                Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
                if (imageBitmap != null) {
                    cropImage(getImageUri(this, imageBitmap));
                }
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri croppedUri = result.getUri();
                try {
                    if (bitmap == null) {
                        InputStream inputStream = getContentResolver().openInputStream(croppedUri);
                        bitmap = BitmapFactory.decodeStream(inputStream);
                        imgktp.setImageBitmap(bitmap);
                    } else {
                        InputStream inputStream = getContentResolver().openInputStream(croppedUri);
                        bitmappas = BitmapFactory.decodeStream(inputStream);
                        imgpas.setImageBitmap(bitmappas);
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
        double ktpRatio = 85.6 / 53.98;
        CropImage.activity(sourceUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .setOutputUri(destinationUri)
                .start(this);
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private String imageToString(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        byte[] imageBytes = outputStream.toByteArray();

        String encodeImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodeImage;
    }

}