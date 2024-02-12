package com.gooproper.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResourceClient;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.gooproper.R;
import com.gooproper.adapter.ListingAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.pager.SertifikatPdfAdapter;
import com.gooproper.ui.detail.DetailAgenListingActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.PreferencesDevice;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

public class CobaPesanActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_CODE_SIGN_IN = 2;
    ProgressDialog PDAgen;
    EditText judul, isi;
    Button kirim, upload, unggah;
    WebView webview;
    String datatoken, pdf;
    Uri selectedPdfUri;
    String[] firebaseTokens;
    ArrayList<String> sertifpdf = new ArrayList<>();
    String token;
    RecyclerView rvgrid;
    RecyclerView.Adapter adapter;
    List<ListingModel> list;
    private ViewPager viewPager;
    private SertifikatPdfAdapter sertifikatPdfAdapter;
    private static final String UPLOAD_URL = "https://www.googleapis.com/upload/drive/v3/files?uploadType=media";
    private static final String ACCESS_TOKEN = "YOUR_ACCESS_TOKEN";
    private static final String FILE_NAME = "UploadedFile.jpg";
    private static final String FOLDER_ID = "1ZPCHSQ_17q0JNa-AxN-duVBAsUtqE0zl";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coba_pesan);

        PDAgen = new ProgressDialog(CobaPesanActivity.this);
        rvgrid = findViewById(R.id.rvlistingagen);
        list = new ArrayList<>();

        rvgrid.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new ListingAdapter(this, list);
        rvgrid.setAdapter(adapter);

        LoadListing(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        judul = findViewById(R.id.judul);
        isi = findViewById(R.id.isi);
        kirim = findViewById(R.id.kirim);
        upload = findViewById(R.id.upload);
        unggah = findViewById(R.id.unggah);
        viewPager = findViewById(R.id.viewPager);

        sertifpdf.add("https://firebasestorage.googleapis.com/v0/b/gooproper-e1983.appspot.com/o/sertifikat%2FSertifikat_20231030_104735.pdf?alt=media&token=58f199a6-7735-4383-9b41-3d349295df7d&_gl=1*mo9gnn*_ga*NTkxNjY2MDczLjE2OTUwODY1NDc.*_ga_CW55HF8NVT*MTY5ODgyNTgyOS40NC4xLjE2OTg4MjU4NjIuMjcuMC4w");
        sertifpdf.add("https://firebasestorage.googleapis.com/v0/b/gooproper-e1983.appspot.com/o/sertifikat%2FSertifikat_20231030_104559.pdf?alt=media&token=d4402e3a-2e73-461f-bebd-054106e45de8&_gl=1*3kmpyn*_ga*NTkxNjY2MDczLjE2OTUwODY1NDc.*_ga_CW55HF8NVT*MTY5ODgyNTgyOS40NC4xLjE2OTg4MjU4ODMuNi4wLjA.");
        sertifpdf.add("https://firebasestorage.googleapis.com/v0/b/gooproper-e1983.appspot.com/o/sertifikat%2FSertifikat_20231030_104432.pdf?alt=media&token=42e0e052-2784-4d36-a592-001bc206b618&_gl=1*n40w5s*_ga*NTkxNjY2MDczLjE2OTUwODY1NDc.*_ga_CW55HF8NVT*MTY5ODgyNTgyOS40NC4xLjE2OTg4MjU4OTYuNjAuMC4w");

        sertifikatPdfAdapter = new SertifikatPdfAdapter(this, sertifpdf);
        viewPager.setAdapter(sertifikatPdfAdapter);

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String fileSertifikat = "Sertifikat_" + timeStamp + ".pdf";

        unggah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

//        upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                signIn();
//                uploadImageToDrive(selectedPdfUri);
//            }
//        });
//        unggah.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//                StorageReference ImgSertifikatshm = storageRef.child("sertifikat/" + fileSertifikat);
//
//                List<StorageTask<UploadTask.TaskSnapshot>> uploadTasks = new ArrayList<>();
//
//                StorageTask<UploadTask.TaskSnapshot> task1 = ImgSertifikatshm.putFile(selectedPdfUri)
//                        .addOnSuccessListener(taskSnapshot -> {
//                            ImgSertifikatshm.getDownloadUrl()
//                                    .addOnSuccessListener(uri -> {
//                                        String imageUrl = uri.toString();
//                                        Toast.makeText(getBaseContext(), "link: " + imageUrl, Toast.LENGTH_SHORT).show();
//                                        pdf = imageUrl;
//                                    })
//                                    .addOnFailureListener(exception -> {
//                                    });
//                        })
//                        .addOnFailureListener(exception -> {
//                        });
//                uploadTasks.add(task1);
//
//                Tasks.whenAllSuccess(uploadTasks)
//                        .addOnSuccessListener(results -> {
//                            Toast.makeText(getBaseContext(), "link: " + pdf, Toast.LENGTH_SHORT).show();
//                        })
//                        .addOnFailureListener(exception -> {
//                            Dialog customDialog = new Dialog(CobaPesanActivity.this);
//                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                            customDialog.setContentView(R.layout.custom_dialog_eror_input);
//
//                            if (customDialog.getWindow() != null) {
//                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//                            }
//
//                            Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
//                            TextView tv = customDialog.findViewById(R.id.TVDialogErorInput);
//
//                            tv.setText("Gagal Saat Unggah Gambar");
//
//                            ok.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    customDialog.dismiss();
//                                }
//                            });
//
//                            ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);
//
//                            Glide.with(CobaPesanActivity.this)
//                                    .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
//                                    .transition(DrawableTransitionOptions.withCrossFade())
//                                    .into(gifImageView);
//
//                            customDialog.show();
//                        });
//            }
//        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DEVICE, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject tokenObject = response.getJSONObject(i);
                        token = tokenObject.getString("Token");
                        // Tampilkan token dalam pesan toast
                        String tokenMessage = "Token Firebase yang diterima: " + token;
                        Toast.makeText(getApplicationContext(), tokenMessage, Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        requestQueue.add(jsonArrayRequest);

//        upload.setOnClickListener(view -> {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("application/pdf");
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(Intent.createChooser(intent, "Pilih File PDF"), 123);
//        });

        kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Saat tombol diklik, mengambil token dan mengirim pesan
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

                            // Memanggil AsyncTask dan mengirim pesan ke token-token yang telah diambil
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
            }
        });
    }
    private void chooseImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123 && resultCode == RESULT_OK && data != null) {
            selectedPdfUri = data.getData();
            // Salin berkas PDF ke penyimpanan internal aplikasi
//            copyPdfToInternalStorage(selectedPdfUri);
        } else if (requestCode == REQUEST_CODE_SIGN_IN && resultCode == RESULT_OK) {
//            handleSignInResult(data);
        }
    }

//    private void signIn() {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//        if (account == null) {
//            GoogleSignInOptions signInOptions =
//                    new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                            .requestScopes(Drive.SCOPE_FILE, Drive.SCOPE_APPFOLDER)
//                            .build();
//
//            GoogleSignInClient signInClient = GoogleSignIn.getClient(this, signInOptions);
//            Intent signInIntent = signInClient.getSignInIntent();
//            startActivityForResult(signInIntent, REQUEST_CODE_SIGN_IN);
//        }
//    }
//
//    private void handleSignInResult(Intent data) {
//        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//        try {
//            GoogleSignInAccount account = task.getResult(ApiException.class);
//            // Do something with the successfully authenticated account
//        } catch (ApiException e) {
//            Log.e("Google Drive", "Error signing in: " + e.getStatusCode());
//        }
//    }
//
//    private void uploadImageToDrive(Uri imageUri) {
//        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
//
//        if (account != null) {
//            Drive.getDriveResourceClient(this, account)
//                    .getRootFolder()
//                    .continueWithTask(task -> {
//                        DriveFolder parentFolder = task.getResult();
//                        return createImageFile(parentFolder, imageUri);
//                    })
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            Log.d("Google Drive", "File successfully uploaded to the specified folder.");
//                        } else {
//                            Log.e("Google Drive", "Failed to upload file to the specified folder.", task.getException());
//                        }
//                    });
//        }
//    }
//
//    private Task<DriveFile> createImageFile(DriveFolder parentFolder, Uri imageUri) {
//        return Tasks.call(() -> {
//            DriveContents contents = Drive.getDriveResourceClient(this, GoogleSignIn.getLastSignedInAccount(this))
//                    .createContents()
//                    .getResult();
//
//            try (OutputStream outputStream = contents.getOutputStream()) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = getContentResolver().openInputStream(imageUri).read(buffer)) != -1) {
//                    outputStream.write(buffer, 0, bytesRead);
//                }
//            } catch (IOException e) {
//                Log.e("Google Drive", "Error creating image file contents: " + e.getMessage());
//            }
//
//            MetadataChangeSet changeSet = new MetadataChangeSet.Builder()
//                    .setTitle("MyImage.jpg")
//                    .setMimeType("image/jpeg")
//                    .setStarred(true)
//                    .build();
//
//            return parentFolder.createFile(changeSet, contents).getResult();
//        });
//    }




    private void copyPdfToInternalStorage(Uri pdfUri) {
        try {
            InputStream in = getContentResolver().openInputStream(pdfUri);
            File internalPdfFile = new File(getFilesDir(), "temp.pdf");
            OutputStream out = new FileOutputStream(internalPdfFile);

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onKirimButtonClick(View view) {
        new SendMessageTask().execute(firebaseTokens);
    }

    private class SendMessageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            for (String token : params) {
                sendNotificationToToken(token, "pralisting");
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
        String message = "Isi Pesan";

        // Mengirim pesan ke token menggunakan metode SendMessageToFCM.sendMessage
        String response = SendMessageToFCM.sendMessage(token, title, message, notificationType);
    }

    private void openPDF(String pdfUrl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.parse(pdfUrl), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Handle jika tidak ada aplikasi PDF viewer yang tersedia
        }
    }

    private void LoadListing(boolean showProgressDialog) {
        PDAgen.setMessage("Memuat Data...");
        PDAgen.show();
        if (showProgressDialog) PDAgen.show();
        else PDAgen.cancel();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_AGEN + 1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDAgen.cancel();
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdListing(data.getString("IdListing"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setIdAgenCo(data.getString("IdAgenCo"));
                                md.setIdInput(data.getString("IdInput"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
                                md.setSelfie(data.getString("Selfie"));
                                md.setWide(data.getString("Wide"));
                                md.setLand(data.getString("Land"));
                                md.setDimensi(data.getString("Dimensi"));
                                md.setListrik(data.getString("Listrik"));
                                md.setLevel(data.getString("Level"));
                                md.setBed(data.getString("Bed"));
                                md.setBath(data.getString("Bath"));
                                md.setBedArt(data.getString("BedArt"));
                                md.setBathArt(data.getString("BathArt"));
                                md.setGarage(data.getString("Garage"));
                                md.setCarpot(data.getString("Carpot"));
                                md.setHadap(data.getString("Hadap"));
                                md.setSHM(data.getString("SHM"));
                                md.setHGB(data.getString("HGB"));
                                md.setHSHP(data.getString("HSHP"));
                                md.setPPJB(data.getString("PPJB"));
                                md.setStratatitle(data.getString("Stratatitle"));
                                md.setAJB(data.getString("AJB"));
                                md.setPetokD(data.getString("PetokD"));
                                md.setPjp(data.getString("Pjp"));
                                md.setImgSHM(data.getString("ImgSHM"));
                                md.setImgHGB(data.getString("ImgHGB"));
                                md.setImgHSHP(data.getString("ImgHSHP"));
                                md.setImgPPJB(data.getString("ImgPPJB"));
                                md.setImgStratatitle(data.getString("ImgStratatitle"));
                                md.setImgAJB(data.getString("ImgAJB"));
                                md.setImgPetokD(data.getString("ImgPetokD"));
                                md.setImgPjp(data.getString("ImgPjp"));
                                md.setImgPjp1(data.getString("ImgPjp1"));
                                md.setNoCertificate(data.getString("NoCertificate"));
                                md.setPbb(data.getString("Pbb"));
                                md.setJenisProperti(data.getString("JenisProperti"));
                                md.setJenisCertificate(data.getString("JenisCertificate"));
                                md.setSumberAir(data.getString("SumberAir"));
                                md.setKondisi(data.getString("Kondisi"));
                                md.setDeskripsi(data.getString("Deskripsi"));
                                md.setPrabot(data.getString("Prabot"));
                                md.setKetPrabot(data.getString("KetPrabot"));
                                md.setPriority(data.getString("Priority"));
                                md.setTtd(data.getString("Ttd"));
                                md.setBanner(data.getString("Banner"));
                                md.setSize(data.getString("Size"));
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
                                md.setTglInput(data.getString("TglInput"));
                                md.setImg1(data.getString("Img1"));
                                md.setImg2(data.getString("Img2"));
                                md.setImg3(data.getString("Img3"));
                                md.setImg4(data.getString("Img4"));
                                md.setImg5(data.getString("Img5"));
                                md.setImg6(data.getString("Img6"));
                                md.setImg7(data.getString("Img7"));
                                md.setImg8(data.getString("Img8"));
                                md.setVideo(data.getString("Video"));
                                md.setLinkFacebook(data.getString("LinkFacebook"));
                                md.setLinkTiktok(data.getString("LinkTiktok"));
                                md.setLinkInstagram(data.getString("LinkInstagram"));
                                md.setLinkYoutube(data.getString("LinkYoutube"));
                                md.setIsAdmin(data.getString("IsAdmin"));
                                md.setIsManager(data.getString("IsManager"));
                                md.setSold(data.getString("Sold"));
                                md.setRented(data.getString("Rented"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setFee(data.getString("Fee"));
                                list.add(md);
                                PDAgen.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDAgen.dismiss();

                                Dialog customDialog = new Dialog(CobaPesanActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.alert_eror);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                Button ok = customDialog.findViewById(R.id.BTNOkEror);
                                Button batal = customDialog.findViewById(R.id.BTNCloseEror);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        LoadListing(true);
                                    }
                                });

                                batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        customDialog.dismiss();
                                    }
                                });

                                customDialog.show();
                            }
                        }

                        adapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PDAgen.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(CobaPesanActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.alert_eror);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BTNOkEror);
                        Button batal = customDialog.findViewById(R.id.BTNCloseEror);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                LoadListing(true);
                            }
                        });

                        batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                customDialog.dismiss();
                            }
                        });

                        customDialog.show();
                    }
                });

        queue.add(reqData);
    }
}