package com.gooproper.ui.tambah.template;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.util.ServerApi;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GantiTemplateActivity extends AppCompatActivity {

    private static final int STORAGE_PERMISSION_CODE = 1;
    final int CODE_GALLERY_REQUEST_TEMPLATE_FULL = 2;
    final int CODE_GALLERY_REQUEST_TEMPLATE_BLANK = 3;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_TEMPLATE_FULL = 4;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_TEMPLATE_FULL = 5;
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_TEMPLATE_BLANK = 6;
    private static final int PERMISSION_REQUEST_CODE_MEDIA_IMAGES_TEMPLATE_BLANK = 7;
    private ProgressDialog pDialog;
    Button Batal, Submit, UploadTemplateFull, UploadTemplateBlank;
    ImageView Back, IVTemplateFull, IVTemplateBlank;
    Uri UriTemplateFull, UriTemplateBlank;
    String StrIdTemplate, TemplateFull, TemplateBlank;
    String TimeStamp,FileTemplateFull, FileTemplateBlank;
    StorageReference storageRef,ImageTemplateFull, ImageTemplateBlank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ganti_template);

        pDialog = new ProgressDialog(GantiTemplateActivity.this);

        Back = findViewById(R.id.IVBack);
        UploadTemplateFull = findViewById(R.id.BtnUploadTemplateFull);
        UploadTemplateBlank = findViewById(R.id.BtnUploadTemplateBlank);
        IVTemplateFull = findViewById(R.id.IVTemplateFull);
        IVTemplateBlank = findViewById(R.id.IVTemplateBlank);
        Batal = findViewById(R.id.BtnBatal);
        Submit = findViewById(R.id.BtnSubmit);

        UploadTemplateFull.setOnClickListener(view -> RequestPermissionsTemplateFull());
        UploadTemplateBlank.setOnClickListener(view -> RequestPermissionsTemplateBlank());
        Back.setOnClickListener(view -> finish());
        Batal.setOnClickListener(view -> finish());

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdTemplate = data.getStringExtra("IdTemplate");

        TimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        FileTemplateFull = "TemplateFull_" + TimeStamp + ".jpg";
        FileTemplateBlank = "TemplateBlank_" + TimeStamp + ".jpg";

        storageRef = FirebaseStorage.getInstance().getReference();
        ImageTemplateFull = storageRef.child("template/" + FileTemplateFull);
        ImageTemplateBlank = storageRef.child("template/" + FileTemplateBlank);

        StrIdTemplate = intentIdTemplate;

        Submit.setOnClickListener(view -> {
            if (UriTemplateFull == null) {
                Dialog customDialog = new Dialog(GantiTemplateActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Tambahkan Gambar Template Foto");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(GantiTemplateActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            } else if (UriTemplateBlank == null) {
                Dialog customDialog = new Dialog(GantiTemplateActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Harap Tambahkan Gambar Template Kosong");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(GantiTemplateActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            } else {
                handleImageTemplateFull();
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void RequestPermissionsTemplateFull() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_TEMPLATE_FULL);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_TEMPLATE_FULL);
        }
    }
    private void RequestPermissionsTemplateBlank() {
        boolean externalStoragePermissionGranted = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if (externalStoragePermissionGranted) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_TEMPLATE_BLANK);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, PERMISSION_REQUEST_CODE_MEDIA_IMAGES_TEMPLATE_BLANK);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_TEMPLATE_FULL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_TEMPLATE_FULL);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_TEMPLATE_FULL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_TEMPLATE_FULL);
            }
        } else if (requestCode == CODE_GALLERY_REQUEST_TEMPLATE_FULL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_TEMPLATE_FULL);
            } else {
                Dialog customDialog = new Dialog(GantiTemplateActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(GantiTemplateActivity.this)
                        .load(R.drawable.alert)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifImageView);

                customDialog.show();
            }
            return;
        } else if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE_TEMPLATE_BLANK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_TEMPLATE_BLANK);
            }
        } else if (requestCode == PERMISSION_REQUEST_CODE_MEDIA_IMAGES_TEMPLATE_BLANK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_TEMPLATE_BLANK);
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {} else {}
        } else if (requestCode == CODE_GALLERY_REQUEST_TEMPLATE_BLANK) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, CODE_GALLERY_REQUEST_TEMPLATE_BLANK);
            } else {
                Dialog customDialog = new Dialog(GantiTemplateActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_eror_input);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);

                tv.setText("Akses Galeri Ditolak");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                Glide.with(GantiTemplateActivity.this)
                        .load(R.drawable.alert)
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
        if (requestCode == CODE_GALLERY_REQUEST_TEMPLATE_FULL && resultCode == RESULT_OK && data != null) {
            UriTemplateFull = data.getData();
            IVTemplateFull.setImageURI(UriTemplateFull);
            IVTemplateFull.setVisibility(View.VISIBLE);
            UploadTemplateFull.setText("Ganti Template Full");
        } else if (requestCode == CODE_GALLERY_REQUEST_TEMPLATE_BLANK && resultCode == RESULT_OK && data != null) {
            UriTemplateBlank = data.getData();
            IVTemplateBlank.setImageURI(UriTemplateBlank);
            IVTemplateBlank.setVisibility(View.VISIBLE);
            UploadTemplateBlank.setText("Ganti Template Blank");
        }

        super.onActivityResult(requestCode, resultCode, data);
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
    private void handleImageTemplateFull() {
        if (UriTemplateFull != null) {
            showProgressDialog();
            ImageTemplateFull.putFile(UriTemplateFull)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageTemplateFull.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        TemplateFull = imageUrl;
                                        handleImageTemplateBlank();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageTemplateFull();
                                        Toast.makeText(GantiTemplateActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageTemplateFull();
                            Toast.makeText(GantiTemplateActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            TemplateFull = "0";
            handleImageTemplateBlank();
        }
    }
    private void handleImageTemplateBlank() {
        if (UriTemplateBlank != null) {
            showProgressDialog();
            ImageTemplateBlank.putFile(UriTemplateBlank)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            ImageTemplateBlank.getDownloadUrl()
                                    .addOnSuccessListener(uri -> {
                                        String imageUrl = uri.toString();
                                        TemplateBlank = imageUrl;
                                        HideProgressDialog();
                                        simpanData();
                                    })
                                    .addOnFailureListener(exception -> {
                                        handleImageTemplateBlank();
                                        Toast.makeText(GantiTemplateActivity.this, "Upload failed: " + exception.getMessage(), Toast.LENGTH_SHORT).show();
                                    });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            handleImageTemplateBlank();
                            Toast.makeText(GantiTemplateActivity.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            TemplateBlank = "0";
            HideProgressDialog();
            simpanData();
        }
    }
    private void simpanData() {
        pDialog.setMessage("Menyimpan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPLOAD_UPDATE_TEMPLATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            Dialog customDialog = new Dialog(GantiTemplateActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Berhasil Menambahkan Template");
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(GantiTemplateActivity.this)
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
                        Dialog customDialog = new Dialog(GantiTemplateActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Gagal Tambah Template");
                        ok.setVisibility(View.GONE);

                        cobalagi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(GantiTemplateActivity.this)
                                .load(R.mipmap.ic_no)
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("IdTemplate", StrIdTemplate);
                map.put("TemplateFull", TemplateFull);
                map.put("TemplateBlank", TemplateBlank);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}