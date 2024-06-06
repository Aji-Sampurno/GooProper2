package com.gooproper.ui.tambah.primary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.gms.tasks.Tasks;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TambahTipeListingPrimaryActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    final int CODE_GALLERY_REQUEST1 = 1;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1 = 11;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES1 = 12;
    Uri Uri1;
    LinearLayout Lyt;
    Button BtnBatal, BtnSubmit, BtnSelect;
    ImageView IV, IVDelete;
    TextInputEditText ETNama, ETHarga, ETDeskripsi;
    String IdListingPrimary, Image1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_tipe_listing_primary);

        pDialog = new ProgressDialog(TambahTipeListingPrimaryActivity.this);

        Lyt = findViewById(R.id.LytGambar);

        BtnBatal = findViewById(R.id.BtnBatal);
        BtnSubmit = findViewById(R.id.BtnSubmit);
        BtnSelect = findViewById(R.id.BtnTambahGambar);

        IV = findViewById(R.id.IV);
        IVDelete = findViewById(R.id.IVDelete);

        ETNama = findViewById(R.id.ETNamaTipeListingPrimary);
        ETHarga = findViewById(R.id.ETHargaTipeListingPrimary);
        ETDeskripsi = findViewById(R.id.ETDeskripsiTipeListingPrimary);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileTipe = "Tipe_" + timeStamp + ".jpg";

        Intent data = getIntent();
        String IdPrimary = data.getStringExtra("IdPrimary");

        IdListingPrimary = IdPrimary;

        BtnSelect.setOnClickListener(view -> showSelect());
        IVDelete.setOnClickListener(view -> clearUri());
        BtnBatal.setOnClickListener(view -> finish());
//        BtnSubmit.setOnClickListener(view -> simpanData());
        BtnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.setMessage("Menyimpan Data");
                pDialog.setCancelable(false);
                pDialog.show();

                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                StorageReference ImgTipe = storageRef.child("tipe/" + fileTipe);

                List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();

                if (Uri1 != null) {
                    StorageTask<UploadTask.TaskSnapshot> task1 = ImgTipe.putFile(Uri1)
                            .addOnSuccessListener(taskSnapshot -> {
                                ImgTipe.getDownloadUrl()
                                        .addOnSuccessListener(uri -> {
                                            String imageUrl = uri.toString();
                                            Image1 = imageUrl;
                                        })
                                        .addOnFailureListener(exception -> {
                                        });
                            })
                            .addOnFailureListener(exception -> {
                            });
                    uploadTasks.add(task1);
                } else {
                    Image1 = "0";
                }

                Tasks.whenAllSuccess(uploadTasks)
                        .addOnSuccessListener(results -> {
                            pDialog.cancel();
                            simpanData();
                        })
                        .addOnFailureListener(exception -> {
                            Dialog customDialog = new Dialog(TambahTipeListingPrimaryActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_eror_input);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                            tv.setText("Gagal Saat Unggah Gambar");

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                }
                            });

                            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                            Glide.with(TambahTipeListingPrimaryActivity.this)
                                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifImageView);

                            customDialog.show();
                        });
            }
        });
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST1);
            } else {

            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_GALLERY_REQUEST1 && resultCode == RESULT_OK && data != null) {
            Uri1 = data.getData();
            IV.setImageURI(Uri1);
            Lyt.setVisibility(View.VISIBLE);
            BtnSelect.setVisibility(View.GONE);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void showSelect() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE1);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES1);
        }
    }
    private void clearUri() {
        if (Uri1 != null) {
            Uri1 = null;
            Lyt.setVisibility(View.GONE);
            BtnSelect.setVisibility(View.VISIBLE);
        }
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_TAMBAH_TIPE_PRIMARY,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
                                Dialog customDialog = new Dialog(TambahTipeListingPrimaryActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Lanjukan Tambah Tipe");
                                cobalagi.setVisibility(View.GONE);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent update = new Intent(TambahTipeListingPrimaryActivity.this, TambahTipeListingPrimaryActivity.class);
                                        update.putExtra("IdPrimary",IdListingPrimary);
                                        startActivity(update);
                                        finish();
                                    }
                                });

                                Glide.with(TambahTipeListingPrimaryActivity.this)
                                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
                                Dialog customDialog = new Dialog(TambahTipeListingPrimaryActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.custom_dialog_sukses);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                                Button ok = customDialog.findViewById(R.id.btnya);
                                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                                dialogTitle.setText("Gagal Menambahkan Listingan");
                                ok.setVisibility(View.GONE);

                                cobalagi.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                    }
                                });

                                Glide.with(TambahTipeListingPrimaryActivity.this)
                                        .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.cancel();
                        Dialog customDialog = new Dialog(TambahTipeListingPrimaryActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Terdapat Masalah Jaringan");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(TambahTipeListingPrimaryActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("IdListingPrimary", IdListingPrimary);
                map.put("NamaTipe", ETNama.getText().toString());
                map.put("DeskripsiTipe", ETDeskripsi.getText().toString());
                map.put("HargaTipe", ETHarga.getText().toString());
                map.put("GambarTipe", Image1);
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }
}