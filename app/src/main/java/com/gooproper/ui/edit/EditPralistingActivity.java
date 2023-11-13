package com.gooproper.ui.edit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
import com.gooproper.ui.FollowUpActivity;
import com.gooproper.ui.LocationActivity;
import com.gooproper.ui.TambahListingActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class EditPralistingActivity extends AppCompatActivity {

    private static final int MAPS_ACTIVITY_REQUEST_CODE = 1;
    private static final int CODE_CAMERA_REQUEST = 2;
    final int KODE_REQUEST_KAMERA = 3;
    private ProgressDialog pDialog;
    Button batal, submit, selfiebtn, maps;
    ImageView back, IVselfie;
    TextInputEditText longitude, latitude;
    Uri bselfie;
    String latitudeStr, longitudeStr, addressStr, Selfie, StrIdPraListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pralisting);

        pDialog = new ProgressDialog(EditPralistingActivity.this);

        maps = findViewById(R.id.map);
        back = findViewById(R.id.ivback);
        longitude = findViewById(R.id.etlongitude);
        latitude = findViewById(R.id.etlatitude);
        selfiebtn = findViewById(R.id.uploadselfie);
        IVselfie = findViewById(R.id.ivselfie);
        batal = findViewById(R.id.btnbatal);
        submit = findViewById(R.id.btnsubmit);

        selfiebtn.setOnClickListener(view -> ActivityCompat.requestPermissions(EditPralistingActivity.this, new String[]{Manifest.permission.CAMERA}, CODE_CAMERA_REQUEST));
        maps.setOnClickListener(view -> startMapsActivityForResult());
        back.setOnClickListener(view -> finish());
        batal.setOnClickListener(view -> finish());

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdPraListing = data.getStringExtra("IdPraListing");

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String selfie = "Selfie_" + timeStamp + ".jpg";

        StrIdPraListing = intentIdPraListing;

        submit.setOnClickListener(view -> {
            if (Validate()) {
                if (bselfie == null) {
                    Dialog customDialog = new Dialog(EditPralistingActivity.this);
                    customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    customDialog.setContentView(R.layout.custom_dialog_eror_input);

                    if (customDialog.getWindow() != null) {
                        customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    }

                    Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                    TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                    tv.setText("Harap Tambahkan Selfie");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            customDialog.dismiss();
                        }
                    });

                    ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                    Glide.with(EditPralistingActivity.this)
                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                            .transition(DrawableTransitionOptions.withCrossFade())
                            .into(gifImageView);

                    customDialog.show();
                } else {
                    if (longitudeStr == null && latitudeStr == null) {
                        Dialog customDialog = new Dialog(EditPralistingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                        TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                        tv.setText("Harap tambah lokasi terlebih dahulu");

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                        Glide.with(EditPralistingActivity.this)
                                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
                        pDialog.setMessage("Menyimpan Data");
                        pDialog.setCancelable(false);
                        pDialog.show();

                        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                        StorageReference ImgSelfie = storageRef.child("selfie/" + selfie);

                        List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();

                        if ( bselfie != null) {
                            StorageTask<UploadTask.TaskSnapshot> task1 = ImgSelfie.putFile(bselfie)
                                    .addOnSuccessListener(taskSnapshot -> {
                                        ImgSelfie.getDownloadUrl()
                                                .addOnSuccessListener(uri -> {
                                                    String imageUrl = uri.toString();
                                                    Selfie = imageUrl;
                                                })
                                                .addOnFailureListener(exception -> {
                                                });
                                    })
                                    .addOnFailureListener(exception -> {
                                    });
                            uploadTasks.add(task1);
                        } else {
                            Selfie = "0";
                        }
                        Tasks.whenAllSuccess(uploadTasks)
                                .addOnSuccessListener(results -> {
                                    pDialog.cancel();
                                    simpanData();
                                })
                                .addOnFailureListener(exception -> {
                                    Dialog customDialog = new Dialog(EditPralistingActivity.this);
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

                                    Glide.with(EditPralistingActivity.this)
                                            .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                            .transition(DrawableTransitionOptions.withCrossFade())
                                            .into(gifImageView);

                                    customDialog.show();
                                });
                    }
                }
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    public void startMapsActivityForResult() {
        Intent intent = new Intent(this, LocationActivity.class);
        startActivityForResult(intent, MAPS_ACTIVITY_REQUEST_CODE);
    }
    private void bukaKamera() {
        Intent intentKamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentKamera.resolveActivity(getPackageManager()) != null) {
            File photoFile = createImageFile();
            if (photoFile != null) {
                bselfie = FileProvider.getUriForFile(this, "com.gooproper", photoFile);
                intentKamera.putExtra(MediaStore.EXTRA_OUTPUT, bselfie);
                startActivityForResult(intentKamera, KODE_REQUEST_KAMERA);
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
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CODE_CAMERA_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bukaKamera();
            } else {
                Dialog customDialog = new Dialog(EditPralistingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Kamera Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(EditPralistingActivity.this)
                        .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }

            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MAPS_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {

                latitudeStr = data.getStringExtra("latitude");
                latitude.setText(latitudeStr);
                longitudeStr = data.getStringExtra("longitude");
                longitude.setText(longitudeStr);
                addressStr = data.getStringExtra("address");
            }

            if (requestCode == KODE_REQUEST_KAMERA && resultCode == RESULT_OK) {
                IVselfie.setImageURI(bselfie);
                IVselfie.setVisibility(View.VISIBLE);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_PRALISTING_MAPS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Dialog customDialog = new Dialog(EditPralistingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Berhasil Menambahkan Listingan");
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE, null, new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            try {
                                                ArrayList<String> tokens = new ArrayList<>();
                                                for (int i = 0; i < response.length(); i++) {
                                                    JSONObject tokenObject = response.getJSONObject(i);
                                                    String token = tokenObject.getString("Token");
                                                    tokens.add(token);
                                                }
                                                new SendMessageTask().execute(tokens.toArray(new String[0]));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // Tangani kesalahan jika terjadi
                                        }
                                    });
                                    requestQueue.add(jsonArrayRequest);
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(EditPralistingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
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
                        Dialog customDialog = new Dialog(EditPralistingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Gagal Tambah Listingan");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(EditPralistingActivity.this)
                                .load(R.mipmap.ic_no) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("IdPraListing", StrIdPraListing);
                map.put("Latitude", latitudeStr);
                map.put("Longitude", longitudeStr);
                map.put("Selfie", Selfie);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public boolean Validate() {
        if (latitude.getText().toString().equals("")) {
            latitude.setError("Harap Isi koordinat latitude");
            latitude.requestFocus();
            return false;
        }
        if (longitude.getText().toString().equals("")) {
            longitude.setError("Harap Isi Nama Lengkap Vendor");
            longitude.requestFocus();
            return false;
        }
        if (bselfie == null) {
            Dialog customDialog = new Dialog(EditPralistingActivity.this);
            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            customDialog.setContentView(R.layout.custom_dialog_eror_input);

            if (customDialog.getWindow() != null) {
                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            }

            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

            tv.setText("Harap Tambahkan Selfie");

            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog.dismiss();
                }
            });

            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

            Glide.with(EditPralistingActivity.this)
                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(gifImageView);

            customDialog.show();
            return false;
        }

        return true;
    }
    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "pesan");
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
        String message = "Melakukan Update Listing";
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }
}