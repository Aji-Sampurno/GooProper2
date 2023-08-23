package com.gooproper.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.gooproper.R;
import com.gooproper.adapter.ViewPagerAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.ui.registrasi.RegistrasiAgenActivity;
import com.gooproper.util.AgenManager;
import com.gooproper.util.FormatCurrency;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailListingActivity extends AppCompatActivity {

    TextView titletop, titleTxt, addressTxt, bedTxt, bathTxt, descriptionTxt, levelTxt, garageTxt, priceTxt, electricTxt, wideTxt, carpotTxt, bedArtTxt, bathArtTxt, certifTxt;
    TextView TVNamaDetailListing, TVAlamatDetailListing, TVHargaDetailListing, TVBedDetailListing, TVNamaAgen;
    ImageView IVFlowUp, IVWhatsapp, IVInstagram;
    Button admin, manager;
    TextInputEditText tambahagen;
    TextInputLayout lytambahagen;
    ScrollView scrollView;
    LinearLayout topbar;
    CardView agen;
    String status, idpralisting, idagen, idlisting, agenid;
    ProgressDialog pDialog;
    ListingModel lm;
    ViewPager viewPager;
    ViewPagerAdapter adapter;
    ArrayList<String> images = new ArrayList<>();
    private String[] dataOptions;
    private int selectedOption = -1;

    private AgenManager agenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_listing);

        tambahagen = findViewById(R.id.ETTambahAgenDetailListing);
        titleTxt = findViewById(R.id.TVNamaDetailListing);
        bedTxt = findViewById(R.id.TVBedDetailListing);
        bathTxt = findViewById(R.id.TVBathDetailListing);
        addressTxt = findViewById(R.id.TVAlamatDetailListing);
        levelTxt = findViewById(R.id.TVLevelDetailListing);
        garageTxt = findViewById(R.id.TVGasariDetailListing);
        electricTxt = findViewById(R.id.TVListrikDetailListing);
        priceTxt = findViewById(R.id.TVHargaDetailListing);
        wideTxt = findViewById(R.id.TVWideDetailListing);
        descriptionTxt = findViewById(R.id.TVDeskripsiDetailListing);
        carpotTxt = findViewById(R.id.TVCarportDetailListing);
        bedArtTxt = findViewById(R.id.TVBedArtDetailListing);
        bathArtTxt = findViewById(R.id.TVBathArtDetailListing);
        certifTxt = findViewById(R.id.TVSertifikatDetailListing);
        viewPager = findViewById(R.id.VPDetailListing);
        agen = findViewById(R.id.LytAgenDetailListing);
        lytambahagen = findViewById(R.id.LytTambahAgenDetailListing);
        admin = findViewById(R.id.BtnApproveAdminDetailListing);
        manager = findViewById(R.id.BtnApproveManagerDetailListing);
        scrollView = findViewById(R.id.SVDetailListing);

        TVNamaAgen = findViewById(R.id.TVNamaAgenDetailListing);
        IVFlowUp = findViewById(R.id.IVFlowUpAgenDetailListing);
        IVWhatsapp = findViewById(R.id.IVNoTelpAgenDetailListing);
        IVInstagram = findViewById(R.id.IVInstagramAgenDetailListing);

        agenManager = new AgenManager();

        FormatCurrency currency = new FormatCurrency();

        Intent data = getIntent();
        final int update = data.getIntExtra("update", 0);
        String intentIdPraListing = data.getStringExtra("IdPraListing");
        String intentIdListing = data.getStringExtra("IdListing");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentIdInput = data.getStringExtra("IdInput");
        String intentNamaListing = data.getStringExtra("NamaListing");
        String intentAlamat = data.getStringExtra("Alamat");
        String intentLocation = data.getStringExtra("Location");
        String intentWide = data.getStringExtra("Wide");
        String intentLevel = data.getStringExtra("Level");
        String intentBed = data.getStringExtra("Bed");
        String intentBedArt = data.getStringExtra("BedArt");
        String intentBath = data.getStringExtra("Bath");
        String intentBathArt = data.getStringExtra("BathArt");
        String intentGarage = data.getStringExtra("Garage");
        String intentCarpot = data.getStringExtra("Carpot");
        String intentNoCertificate = data.getStringExtra("NoCertificate");
        String intentPbb = data.getStringExtra("Pbb");
        String intentJenisProperti = data.getStringExtra("JenisProperti");
        String intentJenisCertificate = data.getStringExtra("JenisCertificate");
        String intentSumberAir = data.getStringExtra("SumberAir");
        String intentKondisi = data.getStringExtra("Kondisi");
        String intentDeskripsi = data.getStringExtra("Deskripsi");
        String intentPrabot = data.getStringExtra("Prabot");
        String intentKetPrabot = data.getStringExtra("KetPrabot");
        String intentPriority = data.getStringExtra("Priority");
        String intentTtd = data.getStringExtra("Ttd");
        String intentBanner = data.getStringExtra("Banner");
        String intentHarga = data.getStringExtra("Harga");
        String intentTglInput = data.getStringExtra("TglInput");
        String intentImg1 = data.getStringExtra("Img1");
        String intentImg2 = data.getStringExtra("Img2");
        String intentImg3 = data.getStringExtra("Img3");
        String intentImg4 = data.getStringExtra("Img4");
        String intentImg5 = data.getStringExtra("Img5");
        String intentImg6 = data.getStringExtra("Img6");
        String intentImg7 = data.getStringExtra("Img7");
        String intentImg8 = data.getStringExtra("Img8");
        String intentVideo = data.getStringExtra("Video");
        String intentLinkFacebook = data.getStringExtra("LinkFacebook");
        String intentLinkTiktok = data.getStringExtra("LinkTiktok");
        String intentLinkInstagram = data.getStringExtra("LinkInstagram");
        String intentLinkYoutube = data.getStringExtra("LinkYoutube");
        String intentIsAdmin = data.getStringExtra("IsAdmin");
        String intentIsManager = data.getStringExtra("IsManager");
        String intentSold = data.getStringExtra("Sold");
        String intentView = data.getStringExtra("View");
        String intentNama = data.getStringExtra("Nama");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentInstagram = data.getStringExtra("Instagram");

        adapter = new ViewPagerAdapter(this, images);
        pDialog = new ProgressDialog(this);
        status = Preferences.getKeyStatus(this);
        idpralisting = intentIdPraListing;
        idlisting = intentIdListing;
        idagen = intentIdAgen;

        tambahagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchDataFromApi();
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lytambahagen.getVisibility() == View.VISIBLE){
                    String input = tambahagen.getText().toString();
                    if (input.isEmpty()){
                        Dialog customDialog = new Dialog(DetailListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_eror_input);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BtnOkErorInput);
                        TextView message = customDialog.findViewById(R.id.TVDialogErorInput);
                        ImageView gifImageView = customDialog.findViewById(R.id.IVDialogErorInput);

                        message.setText("Harap tambahkan agen terlebih dahulu");

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });

                        Glide.with(DetailListingActivity.this)
                                .load(R.drawable.alert) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifImageView);

                        customDialog.show();
                    } else {
                        approveadmin();
                    }
                } else {
                    approveadmin();
                }
            }
        });
        manager.setOnClickListener(v -> approvemanager());

        if (update == 1) {
            titleTxt.setText(intentNamaListing);
            addressTxt.setText(intentAlamat);
            bedTxt.setText(intentBed);
            bathTxt.setText(intentBedArt);
            bedArtTxt.setText(intentBath);
            bathArtTxt.setText(intentBathArt);
            wideTxt.setText(intentWide);
            levelTxt.setText(intentLevel);
            garageTxt.setText(intentGarage);
            carpotTxt.setText(intentCarpot);
            descriptionTxt.setText(intentDeskripsi);
            priceTxt.setText(currency.formatRupiah(intentHarga));
            TVNamaAgen.setText(intentNama);

            IVWhatsapp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String message = "Halo! Saya berminat dengan listingan anda yang beralamat di "+intentAlamat;
                    String url = "https://api.whatsapp.com/send?phone=+62" + intentNoTelp + "&text=" + message;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            IVInstagram.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = "http://instagram.com/_u/" + intentInstagram;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                }
            });

            if (intentIdAgen.equals("null")) {
                agen.setVisibility(View.GONE);
                lytambahagen.setVisibility(View.VISIBLE);
                idagen = agenid;
            } else {
                agen.setVisibility(View.VISIBLE);
                lytambahagen.setVisibility(View.GONE);
                idagen = intentIdAgen;
            }

            if (status.equals("1")) {
                if (intentIsAdmin.equals("0")) {
                    admin.setVisibility(View.VISIBLE);
                    manager.setVisibility(View.GONE);
                } else if (intentIsManager.equals("0")) {
                    admin.setVisibility(View.GONE);
                    manager.setVisibility(View.VISIBLE);
                } else {
                    admin.setVisibility(View.GONE);
                    manager.setVisibility(View.GONE);
                }
            } else if (status.equals("2")) {
                if (intentIsAdmin.equals("0")) {
                    admin.setVisibility(View.VISIBLE);
                    manager.setVisibility(View.GONE);
                } else if (intentIsManager.equals("0")) {
                    admin.setVisibility(View.GONE);
                    manager.setVisibility(View.VISIBLE);
                } else {
                    admin.setVisibility(View.GONE);
                    manager.setVisibility(View.GONE);
                }
            } else if (status.equals("3")) {
                admin.setVisibility(View.GONE);
                manager.setVisibility(View.GONE);
            } else if (status.equals("4")) {
                admin.setVisibility(View.GONE);
                manager.setVisibility(View.GONE);
            }

            if (intentImg1.equals("0")) {
            } else {
                images.add(intentImg1);
            }

            if (intentImg2.equals("0")) {
            } else {
                images.add(intentImg2);
            }

            if (intentImg3.equals("0")) {
            } else {
                images.add(intentImg3);
            }

            if (intentImg4.equals("0")) {
            } else {
                images.add(intentImg4);
            }

            if (intentImg5.equals("0")) {
            } else {
                images.add(intentImg5);
            }

            if (intentImg6.equals("0")) {
            } else {
                images.add(intentImg6);
            }

            if (intentImg7.equals("0")) {
            } else {
                images.add(intentImg7);
            }

            if (intentImg8.equals("0")) {
            } else {
                images.add(intentImg8);
            }
            adapter = new ViewPagerAdapter(this, images);
            viewPager.setPadding(0, 0, 0, 0);
            viewPager.setAdapter(adapter);
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void LoadAgen() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_AGEN, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        handleResponseData(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley Error", error.toString());
                    }
                });

        Volley.newRequestQueue(this).add(request);
    }

    private void handleResponseData(JSONArray data) {
        if (data.length() > 0) {
            dataOptions = new String[data.length()];
            for (int i = 0; i < data.length(); i++) {
                try {
                    dataOptions[i] = data.getString(i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            ShowAgen();
        } else {
            // Tampilkan pesan jika data kosong
            showToast("Data dari API kosong");
        }
    }

    public void ShowAgen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pilih data:");
        builder.setSingleChoiceItems(dataOptions, selectedOption, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectedOption = which;
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (selectedOption != -1) {
                    tambahagen.setText(dataOptions[selectedOption]);
                    showToast("Anda memilih: " + dataOptions[selectedOption]);
                }
            }
        });
        builder.setNegativeButton("Batal", null);

        builder.create().show();
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void approveadmin() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_APPROVE_ADMIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Berhasil Approve Listing");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailListingActivity.this);
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

                Glide.with(DetailListingActivity.this)
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
                map.put("IdAgen", idagen);
                map.put("IdPraListing", idpralisting);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void approvemanager() {
        pDialog.setMessage("Sedang Diproses...");
        pDialog.setCancelable(false);
        pDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_APPROVE_MANAGER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailListingActivity.this);
                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                customDialog.setContentView(R.layout.custom_dialog_sukses);

                if (customDialog.getWindow() != null) {
                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                }

                TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                Button ok = customDialog.findViewById(R.id.btnya);
                Button cobalagi = customDialog.findViewById(R.id.btntidak);
                ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                dialogTitle.setText("Berhasil Approve Listing");
                cobalagi.setVisibility(View.GONE);

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finish();
                    }
                });

                Glide.with(DetailListingActivity.this)
                        .load(R.mipmap.ic_yes)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .into(gifimage);

                customDialog.show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.cancel();
                Dialog customDialog = new Dialog(DetailListingActivity.this);
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

                Glide.with(DetailListingActivity.this)
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
                map.put("IdPraListing", idpralisting);
                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void fetchDataFromApi() {
        agenManager.fetchDataFromApi(this, new AgenManager.ApiCallback() {
            @Override
            public void onSuccess(List<AgenManager.DataItem> dataList) {
                showAlertDialog(dataList);
            }

            @Override
            public void onError(String errorMessage) {
            }
        });
    }

    private void showAlertDialog(List<AgenManager.DataItem> dataList) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Daftar Agen");

        final String[] dataItems = new String[dataList.size()];
        for (int i = 0; i < dataList.size(); i++) {
            AgenManager.DataItem item = dataList.get(i);
            dataItems[i] = "Nama Agen : " + item.getName();
        }

        builder.setItems(dataItems, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AgenManager.DataItem selectedData = dataList.get(which);
                idagen = selectedData.getId();
                handleSelectedData(selectedData);
            }
        });

        builder.setPositiveButton("OK", null);
        builder.show();
    }

    private void handleSelectedData(AgenManager.DataItem selectedData) {
        String selectedText = "Selected ID: " + selectedData.getId();
        Toast.makeText(this, selectedText, Toast.LENGTH_SHORT).show();

        tambahagen.setText(selectedData.getName());
        agenid = selectedData.getId();
    }
}