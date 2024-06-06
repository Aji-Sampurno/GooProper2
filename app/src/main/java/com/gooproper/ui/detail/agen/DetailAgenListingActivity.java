package com.gooproper.ui.detail.agen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.gooproper.R;
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.util.ServerApi;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailAgenListingActivity extends AppCompatActivity {

    ProgressDialog PDAgen;
    TextView nama, telp, instagram, listing, jual, sewa;
    NestedScrollView SV1;
    ImageView qrCodeImageView;
    CircleImageView cvagen;
    RecyclerView rvbadge;
    String imgurl, profile, agen, StringIdAgen, StringNamaAgen, StringApprove, StringStatus;
    RecyclerView rvgrid;
    RecyclerView.Adapter adapter;
    List<ListingModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_agen_listing);

        PDAgen = new ProgressDialog(DetailAgenListingActivity.this);
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

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdAgen = data.getStringExtra("IdAgen");

        agen = intentIdAgen;
        StringIdAgen = intentIdAgen;
        String deepLinkUrl = "https://gooproper.com/agen/" + agen;
        Bitmap qrCodeBitmap = generateQRCode(deepLinkUrl);
        qrCodeImageView.setImageBitmap(qrCodeBitmap);

        list = new ArrayList<>();

        rvgrid.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        adapter = new ListingAdapter(this, list);
        rvgrid.setAdapter(adapter);

        displayAgenDetails(agen);
        LoadListing(true);
        CountSewa(agen);
        CountJual(agen);
        CountListing(agen);

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
                                md.setIdAgenCo(data.getString("IdAgenCo"));
                                md.setIdInput(data.getString("IdInput"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setAlamatTemplate(data.getString("AlamatTemplate"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLocation(data.getString("Location"));
                                md.setWilayah(data.getString("Wilayah"));
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
                                md.setRangeHarga(data.getString("RangeHarga"));
                                md.setTglInput(data.getString("TglInput"));
                                md.setImg1(data.getString("Img1"));
                                md.setImg2(data.getString("Img2"));
                                md.setImg3(data.getString("Img3"));
                                md.setImg4(data.getString("Img4"));
                                md.setImg5(data.getString("Img5"));
                                md.setImg6(data.getString("Img6"));
                                md.setImg7(data.getString("Img7"));
                                md.setImg8(data.getString("Img8"));
                                md.setImg9(data.getString("Img9"));
                                md.setImg10(data.getString("Img10"));
                                md.setImg11(data.getString("Img11"));
                                md.setImg12(data.getString("Img12"));
                                md.setVideo(data.getString("Video"));
                                md.setLinkFacebook(data.getString("LinkFacebook"));
                                md.setLinkTiktok(data.getString("LinkTiktok"));
                                md.setLinkInstagram(data.getString("LinkInstagram"));
                                md.setLinkYoutube(data.getString("LinkYoutube"));
                                md.setIsAdmin(data.getString("IsAdmin"));
                                md.setIsManager(data.getString("IsManager"));
                                md.setIsRejected(data.getString("IsRejected"));
                                md.setSold(data.getString("Sold"));
                                md.setRented(data.getString("Rented"));
                                md.setSoldAgen(data.getString("SoldAgen"));
                                md.setRentedAgen(data.getString("RentedAgen"));
                                md.setView(data.getString("View"));
                                md.setMarketable(data.getString("Marketable"));
                                md.setStatusHarga(data.getString("StatusHarga"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setFee(data.getString("Fee"));
                                md.setNamaVendor(data.getString("NamaVendor"));
                                md.setNoTelpVendor(data.getString("NoTelpVendor"));
                                md.setIsSelfie(data.getString("IsSelfie"));
                                md.setIsLokasi(data.getString("IsLokasi"));
                                md.setIdTemplate(data.getString("IdTemplate"));
                                md.setTemplate(data.getString("Template"));
                                md.setTemplateBlank(data.getString("TemplateBlank"));
                                list.add(md);
                                PDAgen.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDAgen.dismiss();

                                Dialog customDialog = new Dialog(DetailAgenListingActivity.this);
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

                        Dialog customDialog = new Dialog(DetailAgenListingActivity.this);
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
    private void displayAgenDetails(String agenId) {
        PDAgen.setMessage("Memuat Data...");
        PDAgen.show();
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_AGEN_DEEP + agenId,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDAgen.cancel();
                        PDAgen.dismiss();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String intentIdAgen = data.getString("IdAgen");
                                String intentUsername = data.getString("Username");
                                String intentPassword = data.getString("Password");
                                String intentNama = data.getString("Nama");
                                String intentNoTelp = data.getString("NoTelp");
                                String intentEmail = data.getString("Email");
                                String intentTglLahir = data.getString("TglLahir");
                                String intentKotaKelahiran = data.getString("KotaKelahiran");
                                String intentPendidikan = data.getString("Pendidikan");
                                String intentNamaSekolah = data.getString("NamaSekolah");
                                String intentMasaKerja = data.getString("MasaKerja");
                                String intentJabatan = data.getString("Jabatan");
                                String intentStatus = data.getString("Status");
                                String intentAlamatDomisili = data.getString("AlamatDomisili");
                                String intentFacebook = data.getString("Facebook");
                                String intentInstagram  = data.getString("Instagram");
                                String intentNoKtp = data.getString("NoKtp");
                                String intentImgKtp = data.getString("ImgKtp");
                                String intentImgTtd = data.getString("ImgTtd");
                                String intentNpwp  = data.getString("Npwp");
                                String intentPhoto = data.getString("Photo");
                                String intentPoin = data.getString("Poin");
                                String intentIsAkses = data.getString("IsAkses");

                                profile = intentPhoto;

                                if (profile.isEmpty()){
                                    imgurl = "https://testingadnro.000webhostapp.com/gambar/profile/user%20default.png";
                                } else {
                                    imgurl = profile;
                                }

                                nama.setText(intentNama);
                                telp.setText(intentNoTelp);
                                instagram.setText(intentInstagram);
                                Picasso.get()
                                        .load(imgurl)
                                        .into(cvagen);

                            } catch (JSONException e) {
                                PDAgen.dismiss();
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(DetailAgenListingActivity.this);
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
                                        displayAgenDetails(agenId);
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
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        PDAgen.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(DetailAgenListingActivity.this);
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
                                displayAgenDetails(agenId);
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