package com.gooproper.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gooproper.R;
import com.gooproper.adapter.ListingAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailAgenActivity extends AppCompatActivity {

    ProgressDialog PDAgen;
    TextView nama, telp, instagram, listing, jual, sewa, NamaPelamar, WaPelamar, EmailPelamar, KotaPelamar, LahirPelamar, PendidikanPelamar, SekolahPelamar, PengalamanPelamar, PosisiPelamar, KonfirmasiPelamar, DomisisliPelamar, FacebookPelamar, InstgramPelamar;
    Button HubungiPelamar, TerimaPelamar;
    ScrollView SV1, SV2;
    ImageView qrCodeImageView;
    CircleImageView cvagen, cvpelamar;
    RecyclerView rvbadge;
    String imgurl, profile, agen, StringIdAgen, StringNamaAgen, StringApprove, StringStatus;
    RecyclerView rvgrid;
    RecyclerView.Adapter adapter;
    List<ListingModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agen);

        PDAgen = new ProgressDialog(DetailAgenActivity.this);
        nama = findViewById(R.id.tvnamaagen);
        telp = findViewById(R.id.tvtelpagen);
        instagram = findViewById(R.id.tvinstagramagen);
        listing = findViewById(R.id.tvlisting);
        jual = findViewById(R.id.tvjual);
        sewa = findViewById(R.id.tvsewa);
        cvagen = findViewById(R.id.cvagen);
        rvbadge = findViewById(R.id.rvbadge);
        rvgrid = findViewById(R.id.rvlistingagen);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);

        SV1 = findViewById(R.id.sv1);
        SV2 = findViewById(R.id.sv2);

        NamaPelamar = findViewById(R.id.TVNamaPelamar);
        WaPelamar = findViewById(R.id.TVTelpPelamar);
        EmailPelamar = findViewById(R.id.TVEmailPelamar);
        KotaPelamar = findViewById(R.id.TVKotaKelahiranPelamar);
        LahirPelamar = findViewById(R.id.TVTanggalKelahiranPelamar);
        PendidikanPelamar = findViewById(R.id.TVPendidikanTerakhirPelamar);
        SekolahPelamar = findViewById(R.id.TVNamaSekolahPelamar);
        PengalamanPelamar = findViewById(R.id.TVPengalamanPelamar);
        PosisiPelamar = findViewById(R.id.TVPosisiPelamar);
        KonfirmasiPelamar = findViewById(R.id.TVKonfirmasiPelamar);
        DomisisliPelamar = findViewById(R.id.TVAlamatPelamar);
        FacebookPelamar = findViewById(R.id.TVFacebookPelamar);
        InstgramPelamar = findViewById(R.id.TVInstagramPelamar);
        HubungiPelamar = findViewById(R.id.BtnHubungiPelamar);
        TerimaPelamar = findViewById(R.id.BtnTerimaPelamar);
        cvpelamar = findViewById(R.id.cvpelamar);

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentUsername = data.getStringExtra("Username");
        String intentPassword = data.getStringExtra("Password");
        String intentNama = data.getStringExtra("Nama");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentEmail = data.getStringExtra("Email");
        String intentTglLahir = data.getStringExtra("TglLahir");
        String intentKotaKelahiran = data.getStringExtra("KotaKelahiran");
        String intentPendidikan = data.getStringExtra("Pendidikan");
        String intentNamaSekolah = data.getStringExtra("NamaSekolah");
        String intentMasaKerja = data.getStringExtra("MasaKerja");
        String intentJabatan = data.getStringExtra("Jabatan");
        String intentKonfirmasi = data.getStringExtra("Konfirmasi");
        String intentStatus = data.getStringExtra("Status");
        String intentAlamatDomisili = data.getStringExtra("AlamatDomisili");
        String intentFacebook = data.getStringExtra("Facebook");
        String intentInstagram = data.getStringExtra("Instagram");
        String intentNoKtp = data.getStringExtra("NoKtp");
        String intentImgKtp = data.getStringExtra("ImgKtp");
        String intentImgTtd = data.getStringExtra("ImgTtd");
        String intentNpwp = data.getStringExtra("Npwp ");
        String intentPhoto = data.getStringExtra("Photo");
        String intentPoin = data.getStringExtra("Poin ");
        String intentIsAkses = data.getStringExtra("IsAkses");
        String intentApprove = data.getStringExtra("Approve");

        StringStatus = Preferences.getKeyStatus(DetailAgenActivity.this);
        profile = intentPhoto;
        agen = intentIdAgen;
        StringIdAgen = intentIdAgen;
        StringNamaAgen = intentNama;
        StringApprove = intentApprove;
        String deepLinkUrl = "https://gooproper.com/agen/" + agen;
        Bitmap qrCodeBitmap = generateQRCode(deepLinkUrl);
        qrCodeImageView.setImageBitmap(qrCodeBitmap);

        if (profile.isEmpty()) {
            imgurl = "https://testingadnro.000webhostapp.com/gambar/profile/user%20default.png";
        } else {
            imgurl = profile;
        }

        list = new ArrayList<>();

        rvgrid.setLayoutManager(new LinearLayoutManager(DetailAgenActivity.this, LinearLayoutManager.HORIZONTAL, false));
        adapter = new ListingAdapter(this, list);
        rvgrid.setAdapter(adapter);

        if (StringStatus.equals("1")){
            cvagen.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.CustomAlertDialogStyle);
                    builder.setTitle("Konfirmasi Unduhan");
                    builder.setMessage("Apakah Anda ingin mengunduh gambar ini?");
                    builder.setPositiveButton("Ya", (dialog, which) -> {
                        downloadImage(imgurl);
                    });
                    builder.setNegativeButton("Batal", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    builder.create().show();
                    return true;
                }
            });
        } else if (StringStatus.equals("2")) {
            cvagen.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext(),R.style.CustomAlertDialogStyle);
                    builder.setTitle("Konfirmasi Unduhan");
                    builder.setMessage("Apakah Anda ingin mengunduh gambar ini?");
                    builder.setPositiveButton("Ya", (dialog, which) -> {
                        downloadImage(imgurl);
                    });
                    builder.setNegativeButton("Batal", (dialog, which) -> {
                        dialog.dismiss();
                    });
                    builder.create().show();
                    return true;
                }
            });
        } else {

        }

        LoadListing(true);
        CountSewa(agen);
        CountJual(agen);
        CountListing(agen);

        if (StringApprove.equals("1")){
            SV1.setVisibility(View.VISIBLE);
            SV2.setVisibility(View.GONE);

            if (update == 1) {
                nama.setText(intentNama);
                telp.setText(intentNoTelp);
                instagram.setText(intentInstagram);
                Picasso.get()
                        .load(imgurl)
                        .into(cvagen);
            }
        } else {
            SV1.setVisibility(View.GONE);
            SV2.setVisibility(View.VISIBLE);

            if (update == 1) {
                NamaPelamar.setText(" : "+intentNama);
                WaPelamar.setText(" : "+intentNoTelp);
                EmailPelamar.setText(" : "+intentEmail);
                KotaPelamar.setText(" : "+intentKotaKelahiran);
                LahirPelamar.setText(" : "+intentTglLahir);
                PendidikanPelamar.setText(" : "+intentPendidikan);
                SekolahPelamar.setText(" : "+intentNamaSekolah);
                PengalamanPelamar.setText(" : "+intentMasaKerja);
                PosisiPelamar.setText(" : "+intentJabatan);
                KonfirmasiPelamar.setText(" : "+intentKonfirmasi);
                DomisisliPelamar.setText(" : "+intentAlamatDomisili);
                FacebookPelamar.setText(" : "+intentFacebook);
                InstgramPelamar.setText(" : "+intentInstagram);
                HubungiPelamar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String message = "Halo! Saya dari Pihak Goo Proper, setelah melihat profile pendaftaran saudara " + intentNama + " sebagai AGEN Goo Proper\nMaka dengan pesan ini anda di undang untuk melakukan tahap interview\nSekian informasi yang diberikan, Dimohon konfirmasi kesediaan saudara\nTerima Kasih";
                        String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                TerimaPelamar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Terima();
                    }
                });
                Picasso.get()
                        .load(imgurl)
                        .into(cvpelamar);
            }
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

    }

    private Bitmap generateQRCode(String content) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(R.color.black) : getResources().getColor(R.color.white));
                }
            }
            return bitmap;
        } catch (WriterException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void LoadListing(boolean showProgressDialog) {
        PDAgen.setMessage("Memuat Data...");
        PDAgen.show();
        if (showProgressDialog) PDAgen.show();
        else PDAgen.cancel();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_AGEN + agen, null,
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
                                md.setIdInput(data.getString("IdInput"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
                                md.setWide(data.getString("Wide"));
                                md.setLand(data.getString("Land"));
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
                                md.setImgSHM(data.getString("ImgSHM"));
                                md.setImgHGB(data.getString("ImgHGB"));
                                md.setImgHSHP(data.getString("ImgHSHP"));
                                md.setImgPPJB(data.getString("ImgPPJB"));
                                md.setImgStratatitle(data.getString("ImgStratatitle"));
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
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                list.add(md);
                                PDAgen.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDAgen.dismiss();

                                Dialog customDialog = new Dialog(DetailAgenActivity.this);
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

                        Dialog customDialog = new Dialog(DetailAgenActivity.this);
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

    private void CountSewa(String agenId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_SEWA + agenId,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countsewa = data.getString("sewa");

                                sewa.setText(countsewa);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }

    private void CountJual(String agenId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_JUAL + agenId,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countjual = data.getString("jual");

                                jual.setText(countjual);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }

    private void CountListing(String agenId) {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING + agenId,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlisting = data.getString("listing");

                                listing.setText(countlisting);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        queue.add(reqData);
    }

    private void Terima() {
        PDAgen.setMessage("Sedang Diproses...");
        PDAgen.setCancelable(false);
        PDAgen.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_APPROVE_AGEN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                PDAgen.cancel();
                Dialog customDialog = new Dialog(DetailAgenActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Proses Berhasil");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Glide.with(DetailAgenActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                PDAgen.cancel();
                Dialog customDialog = new Dialog(DetailAgenActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Gagal Approve Listing");
                ok.setVisibility(View.GONE);

                cobalagi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        customDialog.dismiss();
                    }
                });

                Glide.with(DetailAgenActivity.this)
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
                System.out.println(map);
                map.put("IdAgen", StringIdAgen);
                map.put("Nama", StringNamaAgen);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void downloadImage(String imageUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // Membuat URL dari alamat gambar
                    URL url = new URL(imageUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();

                    // Membaca gambar dari input stream
                    InputStream input = connection.getInputStream();

                    // Menyimpan gambar di penyimpanan eksternal
                    String customFileName = "image_" + System.currentTimeMillis();
                    String fileName = customFileName + ".jpg";
                    File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    File file = new File(storageDir, fileName);

                    if (!storageDir.exists()) {
                        storageDir.mkdirs();
                    }

                    FileOutputStream output = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                    output.close();
                    input.close();

                    showDownloadSuccessNotification();
                } catch (IOException e) {
                    e.printStackTrace();
                    showDownloadErrorNotification();
                }
            }
        }).start();
    }

    private void showDownloadSuccessNotification() {
        // Menampilkan pemberitahuan unduhan berhasil di UI utama (melalui Handler)
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailAgenActivity.this, "Gambar berhasil diunduh", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDownloadErrorNotification() {
        // Menampilkan pemberitahuan unduhan gagal di UI utama (melalui Handler)
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(DetailAgenActivity.this, "Gagal mengunduh gambar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}