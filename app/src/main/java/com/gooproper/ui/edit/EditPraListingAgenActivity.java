package com.gooproper.ui.edit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.gooproper.ui.TambahListingActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.SendMessageToFCM;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class EditPraListingAgenActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    ImageView back;
    Button batal, submit;
    TextInputEditText Etjenisproperti, Etnamaproperti, Etalamatproperti, Etluas, Etland, Etdimensi, Etlantai, Etbed, Etbath, Etbedart, Etbathart, Etgarasi, Etcarpot, Etlistrik, Etair, Etperabot, Etketperabot, Etbanner, Etstatus, Etharga, Ethargasewa, Etketerangan, Ethadap, Etsize, EtFee;
    TextInputLayout LytSize, LytHargaJual, LytHargaSewa;
    RadioButton open, exclusive;
    RadioGroup rgpriority;
    String idpralisting, priority, HargaString, HargaSewaString, StringIdAgen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pra_listing_agen);

        pDialog = new ProgressDialog(EditPraListingAgenActivity.this);

        rgpriority = findViewById(R.id.rgstatus);

        open = findViewById(R.id.rbopen);
        exclusive = findViewById(R.id.rbexclusive);

        LytSize = findViewById(R.id.lytUkuranBanner);
        LytHargaJual = findViewById(R.id.lytharga);
        LytHargaSewa = findViewById(R.id.lythargasewa);

        back = findViewById(R.id.backFormBtn);

        batal = findViewById(R.id.btnbatal);
        submit = findViewById(R.id.btnsubmit);

        Etjenisproperti = findViewById(R.id.etjenisproperti);
        Etnamaproperti = findViewById(R.id.etnamaproperti);
        Etalamatproperti = findViewById(R.id.etalamatproperti);
        Etluas = findViewById(R.id.etluastanah);
        Etland = findViewById(R.id.etluasbangunan);
        Etdimensi = findViewById(R.id.etdimensi);
        Etlantai = findViewById(R.id.etjumlahlantai);
        Etbed = findViewById(R.id.etkamartidur);
        Etbath = findViewById(R.id.etkamarmandi);
        Etbedart = findViewById(R.id.etkamartidurart);
        Etbathart = findViewById(R.id.etkamarmandiart);
        Etgarasi = findViewById(R.id.etgarasi);
        Etcarpot = findViewById(R.id.etcarpot);
        Etlistrik = findViewById(R.id.etdayalistrik);
        Etair = findViewById(R.id.etsumberair);
        Etperabot = findViewById(R.id.etperabot);
        Etketperabot = findViewById(R.id.etketperabot);
        Etbanner = findViewById(R.id.etbanner);
        Etstatus = findViewById(R.id.etstatusproperti);
        Etharga = findViewById(R.id.etharga);
        Ethargasewa = findViewById(R.id.ethargasewa);
        Etketerangan = findViewById(R.id.etketerangan);
        Ethadap = findViewById(R.id.ethadap);
        Etsize = findViewById(R.id.etukuranbanner);
        EtFee = findViewById(R.id.etfee);

        back.setOnClickListener(view -> finish());
        batal.setOnClickListener(view -> finish());
        Etjenisproperti.setOnClickListener(view -> ShowJenisProperti(view));
        Etair.setOnClickListener(view -> ShowSumberAir(view));
        Etperabot.setOnClickListener(view -> ShowPerabot(view));
        Etbanner.setOnClickListener(view -> ShowBanner(view));
        Etsize.setOnClickListener(view -> ShowSize(view));
        Etstatus.setOnClickListener(view -> ShowStatus(view));
        Etbanner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Ya")) {
                    LytSize.setVisibility(View.VISIBLE);
                } else {
                    LytSize.setVisibility(View.GONE);
                }
            }
        });
        Etstatus.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().equalsIgnoreCase("Jual")) {
                    LytHargaJual.setVisibility(View.VISIBLE);
                    LytHargaSewa.setVisibility(View.GONE);
                } else if (editable.toString().equalsIgnoreCase("Sewa")) {
                    LytHargaSewa.setVisibility(View.VISIBLE);
                    LytHargaJual.setVisibility(View.GONE);
                } else if (editable.toString().equalsIgnoreCase("Jual/Sewa")) {
                    LytHargaJual.setVisibility(View.VISIBLE);
                    LytHargaSewa.setVisibility(View.VISIBLE);
                } else {
                    LytHargaJual.setVisibility(View.GONE);
                    LytHargaSewa.setVisibility(View.GONE);
                }
            }
        });
        Etharga.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    Etharga.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Rp,.\\s]", "");
                    /*
                    double parsed = Double.parseDouble(cleanString);
                    String formatted = String.format(Locale.US, "%,.0f", parsed);

                    HargaString = cleanString;

                    current = formatted;
                    harga.setText(formatted);
                    harga.setSelection(formatted.length());
                    harga.addTextChangedListener(this);
                     */
                    if (!cleanString.isEmpty()) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = String.format(Locale.US, "%,.0f", parsed);
                        HargaString = cleanString;
                        current = formatted;
                        Etharga.setText(formatted);
                        Etharga.setSelection(formatted.length());
                    } else {
                        Etharga.setText("");
                        HargaString = "";
                    }

                    Etharga.addTextChangedListener(this);
                }
            }
        });
        Ethargasewa.addTextChangedListener(new TextWatcher() {
            private String current = "";

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals(current)) {
                    Ethargasewa.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[Rp,.\\s]", "");
                    /*
                    double parsed = Double.parseDouble(cleanString);
                    String formatted = String.format(Locale.US, "%,.0f", parsed);

                    HargaString = cleanString;

                    current = formatted;
                    harga.setText(formatted);
                    harga.setSelection(formatted.length());
                    harga.addTextChangedListener(this);
                     */
                    if (!cleanString.isEmpty()) {
                        double parsed = Double.parseDouble(cleanString);
                        String formatted = String.format(Locale.US, "%,.0f", parsed);
                        HargaSewaString = cleanString;
                        current = formatted;
                        Ethargasewa.setText(formatted);
                        Ethargasewa.setSelection(formatted.length());
                    } else {
                        Ethargasewa.setText("");
                        HargaSewaString = "";
                    }

                    Ethargasewa.addTextChangedListener(this);
                }
            }
        });

        Intent data = getIntent();
        String intentIdPraListing = data.getStringExtra("IdPraListing");
        String intentIdListing = data.getStringExtra("IdListing");
        String intentIdAgen = data.getStringExtra("IdAgen");
        String intentIdAgenCo = data.getStringExtra("IdAgenCo");
        String intentIdInput = data.getStringExtra("IdInput");
        String intentNamaListing = data.getStringExtra("NamaListing");
        String intentAlamat = data.getStringExtra("Alamat");
        String intentLatitude = data.getStringExtra("Latitude");
        String intentLongitude = data.getStringExtra("Longitude");
        String intentLocation = data.getStringExtra("Location");
        String intentSelfie = data.getStringExtra("Selfie");
        String intentWide = data.getStringExtra("Wide");
        String intentLand = data.getStringExtra("Land");
        String intentDimensi = data.getStringExtra("Dimensi");
        String intentListrik = data.getStringExtra("Listrik");
        String intentLevel = data.getStringExtra("Level");
        String intentBed = data.getStringExtra("Bed");
        String intentBedArt = data.getStringExtra("BedArt");
        String intentBath = data.getStringExtra("Bath");
        String intentBathArt = data.getStringExtra("BathArt");
        String intentGarage = data.getStringExtra("Garage");
        String intentCarpot = data.getStringExtra("Carpot");
        String intentHadap = data.getStringExtra("Hadap");
        String intentSHM = data.getStringExtra("SHM");
        String intentHGB = data.getStringExtra("HGB");
        String intentHSHP = data.getStringExtra("HSHP");
        String intentPPJB = data.getStringExtra("PPJB");
        String intentStratatitle = data.getStringExtra("Stratatitle");
        String intentPjp = data.getStringExtra("Pjp");
        String intentImgSHM = data.getStringExtra("ImgSHM");
        String intentImgHGB = data.getStringExtra("ImgHGB");
        String intentImgHSHP = data.getStringExtra("ImgHSHP");
        String intentImgPPJB = data.getStringExtra("ImgPPJB");
        String intentImgStratatitle = data.getStringExtra("ImgStratatitle");
        String intentImgPjp = data.getStringExtra("ImgPjp");
        String intentImgPjp1 = data.getStringExtra("ImgPjp1");
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
        String intentSize = data.getStringExtra("Size");
        String intentHarga = data.getStringExtra("Harga");
        String intentHargaSewa = data.getStringExtra("HargaSewa");
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
        String intentMarketable = data.getStringExtra("Marketable");
        String intentStatusHarga = data.getStringExtra("StatusHarga");
        String intentNama = data.getStringExtra("Nama");
        String intentNoTelp = data.getStringExtra("NoTelp");
        String intentInstagram = data.getStringExtra("Instagram");
        String intentFee = data.getStringExtra("Fee");

        StringIdAgen = intentIdAgen;

        if (intentPriority.equals("open")){
            open.setChecked(true);
            priority = intentPriority;
        } else if (intentPriority.equals("exclusive")) {
            exclusive.setChecked(true);
            priority = intentPriority;
        } else {
            open.setChecked(false);
            exclusive.setChecked(false);
        }
        idpralisting = intentIdPraListing;
        Etjenisproperti.setText(intentJenisProperti);
        Etnamaproperti.setText(intentNamaListing);
        Etalamatproperti.setText(intentAlamat);
        Etluas.setText(intentWide);
        Etland.setText(intentLand);
        Etdimensi.setText(intentDimensi);
        Ethadap.setText(intentHadap);
        Etlantai.setText(intentLevel);
        Etbed.setText(intentBed);
        Etbedart.setText(intentBedArt);
        Etbath.setText(intentBath);
        Etbathart.setText(intentBathArt);
        Etgarasi.setText(intentGarage);
        Etcarpot.setText(intentCarpot);
        Etlistrik.setText(intentListrik);
        Etair.setText(intentSumberAir);
        Etperabot.setText(intentPrabot);
        Etketperabot.setText(intentKetPrabot);
        Etbanner.setText(intentBanner);
        Etsize.setText(intentSize);
        Etstatus.setText(intentKondisi);
        Etharga.setText(intentHarga);
        Ethargasewa.setText(intentHargaSewa);
        Etketerangan.setText(intentDeskripsi);
        EtFee.setText(intentFee);

        submit.setOnClickListener(v -> simpanData());
        rgpriority.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.rbopen) {
                priority = "open";
            } else if (checkedId == R.id.rbexclusive) {
                priority = "exclusive";
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void simpanData() {
        pDialog.setMessage("Menyimpan Pembaruan Data");
        pDialog.setCancelable(false);
        pDialog.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, ServerApi.URL_UPDATE_PRALISTING_AGEN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pDialog.cancel();
                        try {
                            JSONObject res = new JSONObject(response);
                            String status = res.getString("Status");
                            if (status.equals("Sukses")) {
                                Dialog customDialog = new Dialog(EditPraListingAgenActivity.this);
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

                                Glide.with(EditPraListingAgenActivity.this)
                                        .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                        .transition(DrawableTransitionOptions.withCrossFade())
                                        .into(gifimage);

                                customDialog.show();
                            } else if (status.equals("Error")) {
                                Dialog customDialog = new Dialog(EditPraListingAgenActivity.this);
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
                                        finish();
                                    }
                                });

                                Glide.with(EditPraListingAgenActivity.this)
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
                        Dialog customDialog = new Dialog(EditPraListingAgenActivity.this);
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

                        Glide.with(EditPraListingAgenActivity.this)
                                .load(R.mipmap.ic_eror_network_foreground) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                if (HargaString == null){
                    HargaString = Etharga.getText().toString();
                }
                if (HargaSewaString == null){
                    HargaSewaString = Ethargasewa.getText().toString();
                }

                map.put("IdPraListing", idpralisting);
                map.put("NamaListing", Etnamaproperti.getText().toString());
                map.put("Alamat", Etalamatproperti.getText().toString());
                map.put("Wide", Etluas.getText().toString());
                map.put("Land", Etland.getText().toString());
                map.put("Dimensi", Etdimensi.getText().toString());
                map.put("Listrik", Etlistrik.getText().toString());
                map.put("Level", Etlantai.getText().toString());
                map.put("Bed", Etbed.getText().toString());
                map.put("Bath", Etbath.getText().toString());
                map.put("BedArt", Etbedart.getText().toString());
                map.put("BathArt", Etbathart.getText().toString());
                map.put("Garage", Etgarasi.getText().toString());
                map.put("Carpot", Etcarpot.getText().toString());
                map.put("Hadap", Ethadap.getText().toString());
                map.put("JenisProperti", Etjenisproperti.getText().toString());
                map.put("SumberAir", Etair.getText().toString());
                map.put("Kondisi", Etstatus.getText().toString());
                map.put("Deskripsi", Etketerangan.getText().toString());
                map.put("Prabot", Etperabot.getText().toString());
                map.put("KetPrabot", Etketperabot.getText().toString());
                map.put("Priority", priority);
                map.put("Banner", Etbanner.getText().toString());
                map.put("Size", Etsize.getText().toString());
                map.put("Harga", HargaString);
                map.put("HargaSewa", HargaSewaString);
                map.put("Fee", EtFee.getText().toString());
                System.out.println(map);

                return map;
            }
        };

        requestQueue.add(stringRequest);
    }

    public void ShowJenisProperti(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Jenis Properti");

        final CharSequence[] JenisProperti = {"Rumah", "Ruko", "Tanah", "Gudang", "Ruang Usaha", "Villa", "Apartemen", "Pabrik", "Kantor", "Hotel", "Rukost"};
        final int[] SelectedJenisProperti = {0};

        builder.setSingleChoiceItems(JenisProperti, SelectedJenisProperti[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedJenisProperti[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etjenisproperti.setText(JenisProperti[SelectedJenisProperti[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowSumberAir(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Sumber Air");

        final CharSequence[] SumberAir = {"PAM atau PDAM", "Sumur Pompa", "Sumur Bor", "Sumur Resapan", "Sumur Galian"};
        final int[] SelectedSumberAir = {0};

        builder.setSingleChoiceItems(SumberAir, SelectedSumberAir[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedSumberAir[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etair.setText(SumberAir[SelectedSumberAir[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowPerabot(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ketersediaan Prabot");

        final CharSequence[] Perabot = {"Ada", "Tidak"};
        final int[] SelectedPerabot = {0};

        builder.setSingleChoiceItems(Perabot, SelectedPerabot[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedPerabot[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etperabot.setText(Perabot[SelectedPerabot[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowBanner(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Pemasangan Banner");

        final CharSequence[] Banner = {"Ya", "Tidak"};
        final int[] SelectedBanner = {0};

        builder.setSingleChoiceItems(Banner, SelectedBanner[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedBanner[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etbanner.setText(Banner[SelectedBanner[0]]);
                if (Banner[SelectedBanner[0]].equals("Ya")) {
                    Etsize.setVisibility(View.VISIBLE);
                }
                /*if (Banner[SelectedBanner[0]] == "Ya"){
                    size.setVisibility(View.VISIBLE);
                }*/
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowSize(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ukuran Banner");

        final CharSequence[] Banner = {"80 X 90", "100 X 125", "180 X 80", "Lainnya"};
        final int[] SelectedBanner = {0};

        builder.setSingleChoiceItems(Banner, SelectedBanner[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == Banner.length - 1) {
                    ShowCustomSize();
                } else {
                    Etsize.setText(Banner[which]);
                }
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void ShowCustomSize() {
        final EditText editTextCustomType = new EditText(this);
        editTextCustomType.setHint("Masukkan Ukuran Banner");
        editTextCustomType.setTextColor(getResources().getColor(android.R.color.black));
        editTextCustomType.setHintTextColor(getResources().getColor(android.R.color.black));
        editTextCustomType.setBackgroundColor(getResources().getColor(android.R.color.white));
        editTextCustomType.setPadding(50, 20, 50, 0);

        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Ukuran Banner")
                .setView(editTextCustomType)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String customType = editTextCustomType.getText().toString().trim();
                        if (!customType.isEmpty()) {
                            Etsize.setText(customType);
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Batal", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void ShowStatus(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomAlertDialogStyle);
        builder.setTitle("Silahkan Pilih Status Properti");

        final CharSequence[] Status = {"Jual", "Sewa", "Jual/Sewa"};
        final int[] SelectedStatus = {0};

        builder.setSingleChoiceItems(Status, SelectedStatus[0], new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SelectedStatus[0] = which;
            }
        });

        builder.setPositiveButton("Pilih", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Etstatus.setText(Status[SelectedStatus[0]]);
            }
        });

        builder.setNegativeButton("Batal", null);

        AlertDialog dialog = builder.create();

        dialog.show();
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