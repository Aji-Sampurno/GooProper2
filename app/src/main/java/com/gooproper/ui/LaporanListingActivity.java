package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
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
import com.gooproper.R;
import com.gooproper.generator.ExcelGenerator;
import com.gooproper.model.ListingModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class LaporanListingActivity extends AppCompatActivity {

    Button BtnUnduh, BtnUnduhAgen;
    TextView TVListingSold, TVListingRent, TVListingSoldRent, TVListingSolded, TVListingRented, TVListingReady, TVAllListing, TVListingTahun, TVListingBulan;
    int Poin, UpdatePoin, FinalPoin;
    String Grade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_listing);

        BtnUnduh = findViewById(R.id.BtnUnduh);
        BtnUnduhAgen = findViewById(R.id.BtnUnduhAgen);
        TVListingSold = findViewById(R.id.TVSold);
        TVListingSoldRent = findViewById(R.id.TVListing);
        TVListingRent = findViewById(R.id.TVRented);
        TVListingSolded = findViewById(R.id.TVListingTerJual);
        TVListingRented = findViewById(R.id.TVListingTersewa);
        TVListingReady = findViewById(R.id.TVListingTersedia);
        TVAllListing = findViewById(R.id.TVListingAll);
        TVListingTahun = findViewById(R.id.TVListingTahunIni);
        TVListingBulan = findViewById(R.id.TVListingBulanIni);

        BtnUnduh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataAndCreateExcel();
            }
        });
        BtnUnduhAgen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataAgenAndCreateExcel();
            }
        });

        CountTersedia();
        CountTerjual();
        CountTersewa();
        CountSold();
        CountSoldRent();
        CountRent();
        CountAll();
        CountTahun();
        CountBulan();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private List<ListingModel> fetchDataFromServerOrOtherSource() {
        final List<ListingModel> dataList = new ArrayList<>();

        RequestQueue queue = Volley.newRequestQueue(LaporanListingActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject data = response.getJSONObject(i);
                                ListingModel md = new ListingModel();
                                md.setIdListing(data.getString("IdListing"));
                                md.setNamaListing(data.getString("NamaListing"));
                                dataList.add(md);
                            }
                            ExcelGenerator.createExcel(LaporanListingActivity.this, dataList);

                        } catch (JSONException e) {
                            e.printStackTrace();
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

        return dataList;
    }
    private void fetchDataAndCreateExcel() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_LAPORAN,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            WritableWorkbook workbook = null;

                            File sdCard = Environment.getExternalStorageDirectory();
                            File directory = new File(sdCard.getAbsolutePath() + "/Download");
                            directory.mkdir();

                            String filename = String.format(Locale.getDefault(), "%d.xls", System.currentTimeMillis());
                            File outFile = new File(directory, filename);

                            WorkbookSettings wbSettings = new WorkbookSettings();
                            wbSettings.setLocale(new Locale("en", "EN"));
                            workbook = Workbook.createWorkbook(outFile, wbSettings);

                            WritableSheet sheet = workbook.createSheet("Data", 0);

                            sheet.addCell(new Label(0, 0, "No"));
                            sheet.addCell(new Label(1, 0, "JENIS PROPERTY"));
                            sheet.addCell(new Label(2, 0, "KODE"));
                            sheet.addCell(new Label(3, 0, "PJP"));
                            sheet.addCell(new Label(4, 0, "ALAMAT"));
                            sheet.addCell(new Label(5, 0, "WILAYAH"));
                            sheet.addCell(new Label(6, 0, "NOMOR"));
                            sheet.addCell(new Label(7, 0, "NAMA PEMILIK"));
                            sheet.addCell(new Label(8, 0, "E/O"));
                            sheet.addCell(new Label(9, 0, "J/S"));
                            sheet.addCell(new Label(10, 0, "LT"));
                            sheet.addCell(new Label(11, 0, "LB"));
                            sheet.addCell(new Label(12, 0, "Dimensi"));
                            sheet.addCell(new Label(13, 0, "LANTAI"));
                            sheet.addCell(new Label(14, 0, "HARGA JUAL"));
                            sheet.addCell(new Label(15, 0, "HARGA"));
                            sheet.addCell(new Label(16, 0, "NOTE"));
                            sheet.addCell(new Label(17, 0, "HDP"));
                            sheet.addCell(new Label(18, 0, "LISTRIK"));
                            sheet.addCell(new Label(19, 0, "AIR"));
                            sheet.addCell(new Label(20, 0, "FEE"));
                            sheet.addCell(new Label(21, 0, "MA"));
                            sheet.addCell(new Label(22, 0, "MA Co"));
                            sheet.addCell(new Label(23, 0, "TGL"));
                            sheet.addCell(new Label(24, 0, "Harga"));
                            sheet.addCell(new Label(25, 0, "Marketable"));
                            sheet.addCell(new Label(26, 0, "Selfie"));
                            sheet.addCell(new Label(27, 0, "Share Lokasi"));
                            sheet.addCell(new Label(28, 0, "BANNER"));
                            sheet.addCell(new Label(29, 0, "GRADE"));
                            sheet.addCell(new Label(30, 0, "POIN"));
                            sheet.addCell(new Label(31, 0, "Status Property"));
                            sheet.addCell(new Label(32, 0, "UPLOAD"));

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String IdListing = jsonObject.getString("IdListing");
                                String IdAgen = jsonObject.getString("IdAgen");
                                String IdAgenCo = jsonObject.getString("IdAgenCo");
                                String IdInput = jsonObject.getString("IdInput");
                                String NamaListing = jsonObject.getString("NamaListing");
                                String Alamat = jsonObject.getString("Alamat");
                                String Latitude = jsonObject.getString("Latitude");
                                String Longitude = jsonObject.getString("Longitude");
                                String Location = jsonObject.getString("Location");
                                String Selfie = jsonObject.getString("Selfie");
                                String Wide = jsonObject.getString("Wide");
                                String Land = jsonObject.getString("Land");
                                String Dimensi = jsonObject.getString("Dimensi");
                                String Listrik = jsonObject.getString("Listrik");
                                String Level = jsonObject.getString("Level");
                                String Bed = jsonObject.getString("Bed");
                                String Bath = jsonObject.getString("Bath");
                                String BedArt = jsonObject.getString("BedArt");
                                String BathArt = jsonObject.getString("BathArt");
                                String Garage = jsonObject.getString("Garage");
                                String Carpot = jsonObject.getString("Carpot");
                                String Hadap = jsonObject.getString("Hadap");
                                String SHM = jsonObject.getString("SHM");
                                String HGB = jsonObject.getString("HGB");
                                String HSHP = jsonObject.getString("HSHP");
                                String PPJB = jsonObject.getString("PPJB");
                                String Stratatitle = jsonObject.getString("Stratatitle");
                                String AJB = jsonObject.getString("AJB");
                                String PetokD = jsonObject.getString("PetokD");
                                String Pjp = jsonObject.getString("Pjp");
                                String ImgSHM = jsonObject.getString("ImgSHM");
                                String ImgHGB = jsonObject.getString("ImgHGB");
                                String ImgHSHP = jsonObject.getString("ImgHSHP");
                                String ImgPPJB = jsonObject.getString("ImgPPJB");
                                String ImgStratatitle = jsonObject.getString("ImgStratatitle");
                                String ImgAJB = jsonObject.getString("ImgAJB");
                                String ImgPetokD = jsonObject.getString("ImgPetokD");
                                String ImgPjp = jsonObject.getString("ImgPjp");
                                String ImgPjp1 = jsonObject.getString("ImgPjp1");
                                String NoCertificate = jsonObject.getString("NoCertificate");
                                String Pbb = jsonObject.getString("Pbb");
                                String JenisProperti = jsonObject.getString("JenisProperti");
                                String JenisCertificate = jsonObject.getString("JenisCertificate");
                                String SumberAir = jsonObject.getString("SumberAir");
                                String Kondisi = jsonObject.getString("Kondisi");
                                String Deskripsi = jsonObject.getString("Deskripsi");
                                String Prabot = jsonObject.getString("Prabot");
                                String KetPrabot = jsonObject.getString("KetPrabot");
                                String Priority = jsonObject.getString("Priority");
                                String Ttd = jsonObject.getString("Ttd");
                                String Banner = jsonObject.getString("Banner");
                                String Size = jsonObject.getString("Size");
                                String Harga = jsonObject.getString("Harga");
                                String HargaSewa = jsonObject.getString("HargaSewa");
                                String TglInput = jsonObject.getString("TglInput");
                                String Img1 = jsonObject.getString("Img1");
                                String Img2 = jsonObject.getString("Img2");
                                String Img3 = jsonObject.getString("Img3");
                                String Img4 = jsonObject.getString("Img4");
                                String Img5 = jsonObject.getString("Img5");
                                String Img6 = jsonObject.getString("Img6");
                                String Img7 = jsonObject.getString("Img7");
                                String Img8 = jsonObject.getString("Img8");
                                String Video = jsonObject.getString("Video");
                                String LinkFacebook = jsonObject.getString("LinkFacebook");
                                String LinkTiktok = jsonObject.getString("LinkTiktok");
                                String LinkInstagram = jsonObject.getString("LinkInstagram");
                                String LinkYoutube = jsonObject.getString("LinkYoutube");
                                String IsAdmin = jsonObject.getString("IsAdmin");
                                String IsManager = jsonObject.getString("IsManager");
                                String IsRejected = jsonObject.getString("IsRejected");
                                String Sold = jsonObject.getString("Sold");
                                String Rented = jsonObject.getString("Rented");
                                String SoldAgen = jsonObject.getString("SoldAgen");
                                String RentedAgen = jsonObject.getString("RentedAgen");
                                String View = jsonObject.getString("View");
                                String Marketable = jsonObject.getString("Marketable");
                                String StatusHarga = jsonObject.getString("StatusHarga");
                                String Nama = jsonObject.getString("Nama");
                                String NoTelp = jsonObject.getString("NoTelp");
                                String Instagram = jsonObject.getString("Instagram");
                                String NamaCo = jsonObject.getString("NamaCo");
                                String Fee = jsonObject.getString("Fee");
                                String NamaVendor = jsonObject.getString("NamaVendor");
                                String NoTelpVendor = jsonObject.getString("NoTelpVendor");
                                String IsSelfie = jsonObject.getString("IsSelfie");
                                String IsLokasi = jsonObject.getString("IsLokasi");
                                String No = String.valueOf(i + 1);

                                if (Priority.equals("exclusive") && !Pjp.isEmpty() && Banner.equals("Ya")){
                                    Poin = 50;
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = ( Poin * 2 ) + 20;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = ( Poin * 2 ) + 20;
                                            } else {
                                                UpdatePoin = (( Poin * 2 ) + 20) / 2;
                                            }
                                        } else {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = Poin * 2;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = Poin * 2;
                                            } else {
                                                UpdatePoin = (Poin * 2) / 2;
                                            }
                                        }
                                    } else {
                                        if (IdAgenCo.equals("0")){
                                            UpdatePoin = Poin;
                                        } else if (IdAgenCo.equals(IdAgen)) {
                                            UpdatePoin = Poin;
                                        } else {
                                            UpdatePoin = Poin / 2;
                                        }
                                    }
                                } else if (Priority.equals("exclusive") && !Pjp.isEmpty() && Banner.equals("Tidak")) {
                                    Poin = 40;
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = ( Poin * 2 ) + 20;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = ( Poin * 2 ) + 20;
                                            } else {
                                                UpdatePoin = (( Poin * 2 ) + 20) / 2;
                                            }
                                        } else {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = Poin * 2;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = Poin * 2;
                                            } else {
                                                UpdatePoin = (Poin * 2) / 2;
                                            }
                                        }
                                    } else {
                                        if (IdAgenCo.equals("0")){
                                            UpdatePoin = Poin;
                                        } else if (IdAgenCo.equals(IdAgen)) {
                                            UpdatePoin = Poin;
                                        } else {
                                            UpdatePoin = Poin / 2;
                                        }
                                    }
                                } else if (Priority.equals("open") && !Pjp.isEmpty() && Banner.equals("Ya")) {
                                    Poin = 30;
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = ( Poin * 2 ) + 10;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = ( Poin * 2 ) + 10;
                                            } else {
                                                UpdatePoin = (( Poin * 2 ) + 10) / 2;
                                            }
                                        } else {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = Poin * 2;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = Poin * 2;
                                            } else {
                                                UpdatePoin = (Poin * 2) / 2;
                                            }
                                        }
                                    } else {
                                        if (IdAgenCo.equals("0")){
                                            UpdatePoin = Poin;
                                        } else if (IdAgenCo.equals(IdAgen)) {
                                            UpdatePoin = Poin;
                                        } else {
                                            UpdatePoin = Poin / 2;
                                        }
                                    }
                                } else if (Priority.equals("open") && !Pjp.isEmpty() && Banner.equals("Tidak")) {
                                    Poin = 20;
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = ( Poin * 2 ) + 20;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = ( Poin * 2 ) + 20;
                                            } else {
                                                UpdatePoin = (( Poin * 2 ) + 20) / 2;
                                            }
                                        } else {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = Poin * 2;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = Poin * 2;
                                            } else {
                                                UpdatePoin = (Poin * 2) / 2;
                                            }
                                        }
                                    } else {
                                        if (IdAgenCo.equals("0")){
                                            UpdatePoin = Poin;
                                        } else if (IdAgenCo.equals(IdAgen)) {
                                            UpdatePoin = Poin;
                                        } else {
                                            UpdatePoin = Poin / 2;
                                        }
                                    }
                                } else if (Priority.equals("open") && Pjp.isEmpty() && Banner.equals("Ya")) {
                                    Poin = 10;
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = ( Poin * 2 ) + 30;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = ( Poin * 2 ) + 30;
                                            } else {
                                                UpdatePoin = (( Poin * 2 ) + 30) / 2;
                                            }
                                        } else {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = Poin * 2;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = Poin * 2;
                                            } else {
                                                UpdatePoin = (Poin * 2) / 2;
                                            }
                                        }
                                    } else {
                                        if (IdAgenCo.equals("0")){
                                            UpdatePoin = Poin;
                                        } else if (IdAgenCo.equals(IdAgen)) {
                                            UpdatePoin = Poin;
                                        } else {
                                            UpdatePoin = Poin / 2;
                                        }
                                    }
                                } else if (Priority.equals("open") && Pjp.isEmpty() && Banner.equals("Tidak")) {
                                    Poin = 10;
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = ( Poin * 2 ) + 10;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = ( Poin * 2 ) + 10;
                                            } else {
                                                UpdatePoin = (( Poin * 2 ) + 10) / 2;
                                            }
                                        } else {
                                            if (IdAgenCo.equals("0")){
                                                UpdatePoin = Poin * 2;
                                            } else if (IdAgenCo.equals(IdAgen)) {
                                                UpdatePoin = Poin * 2;
                                            } else {
                                                UpdatePoin = (Poin * 2) / 2;
                                            }
                                        }
                                    } else {
                                        if (IdAgenCo.equals("0")){
                                            UpdatePoin = Poin;
                                        } else if (IdAgenCo.equals(IdAgen)) {
                                            UpdatePoin = Poin;
                                        } else {
                                            UpdatePoin = Poin / 2;
                                        }
                                    }
                                }

                                sheet.addCell(new Label(0, i + 1, No));
                                sheet.addCell(new Label(1, i + 1, JenisProperti));
                                sheet.addCell(new Label(2, i + 1, ""));
                                sheet.addCell(new Label(3, i + 1, Pjp));
                                sheet.addCell(new Label(4, i + 1, Alamat));
                                sheet.addCell(new Label(5, i + 1, Location));
                                sheet.addCell(new Label(6, i + 1, NoTelpVendor));
                                sheet.addCell(new Label(7, i + 1, NamaVendor));
                                sheet.addCell(new Label(8, i + 1, Priority));
                                sheet.addCell(new Label(9, i + 1, Kondisi));
                                sheet.addCell(new Label(10, i + 1, Wide));
                                sheet.addCell(new Label(11, i + 1, Land));
                                sheet.addCell(new Label(12, i + 1, Dimensi));
                                sheet.addCell(new Label(13, i + 1, Level));
                                sheet.addCell(new Label(14, i + 1, Harga));
                                sheet.addCell(new Label(15, i + 1, HargaSewa));
                                sheet.addCell(new Label(16, i + 1, Deskripsi));
                                sheet.addCell(new Label(17, i + 1, Hadap));
                                sheet.addCell(new Label(18, i + 1, Listrik));
                                sheet.addCell(new Label(19, i + 1, SumberAir));
                                sheet.addCell(new Label(20, i + 1, Fee));
                                sheet.addCell(new Label(21, i + 1, Nama));

                                if (IdAgenCo.equals(IdAgen)){
                                    sheet.addCell(new Label(22, i + 1, "-"));
                                } else if (IdAgenCo.equals("0")) {
                                    sheet.addCell(new Label(22, i + 1, "-"));
                                } else {
                                    sheet.addCell(new Label(22, i + 1, NamaCo));
                                }

                                sheet.addCell(new Label(23, i + 1, TglInput));
                                sheet.addCell(new Label(24, i + 1, StatusHarga));
                                sheet.addCell(new Label(25, i + 1, Marketable));
                                sheet.addCell(new Label(26, i + 1, IsSelfie));
                                sheet.addCell(new Label(27, i + 1, IsLokasi));
                                sheet.addCell(new Label(28, i + 1, Banner));

                                if (Priority.equals("exclusive") && !Pjp.isEmpty() && Banner.equals("Ya")){
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            Grade = "Grade 6C";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        } else {
                                            Grade = "Grade 6B";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        }
                                    } else {
                                        Grade = "Grade 6A";
                                        sheet.addCell(new Label(29, i + 1, Grade));
                                    }
                                } else if (Priority.equals("exclusive") && !Pjp.isEmpty() && Banner.equals("Tidak")) {
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            Grade = "Grade 5C";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        } else {
                                            Grade = "Grade 5B";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        }
                                    } else {
                                        Grade = "Grade 5A";
                                        sheet.addCell(new Label(29, i + 1, Grade));
                                    }
                                } else if (Priority.equals("open") && !Pjp.isEmpty() && Banner.equals("Ya")) {
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            Grade = "Grade 4C";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        } else {
                                            Grade = "Grade 4B";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        }
                                    } else {
                                        Grade = "Grade 4A";
                                        sheet.addCell(new Label(29, i + 1, Grade));
                                    }
                                } else if (Priority.equals("open") && !Pjp.isEmpty() && Banner.equals("Tidak")) {
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            Grade = "Grade 3C";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        } else {
                                            Grade = "Grade 3B";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        }
                                    } else {
                                        Grade = "Grade 3A";
                                        sheet.addCell(new Label(29, i + 1, Grade));
                                    }
                                } else if (Priority.equals("open") && Pjp.isEmpty() && Banner.equals("Ya")) {
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            Grade = "Grade 2C";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        } else {
                                            Grade = "Grade 2B";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        }
                                    } else {
                                        Grade = "Grade 2A";
                                        sheet.addCell(new Label(29, i + 1, Grade));
                                    }
                                } else if (Priority.equals("open") && Pjp.isEmpty() && Banner.equals("Tidak")) {
                                    if (IsSelfie.equals("1") && IsLokasi.equals("1")) {
                                        if (Marketable.equals("1") && StatusHarga.equals("1")) {
                                            Grade = "Grade 1C";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        } else {
                                            Grade = "Grade 1B";
                                            sheet.addCell(new Label(29, i + 1, Grade));
                                        }
                                    } else {
                                        Grade = "Grade 1A";
                                        sheet.addCell(new Label(29, i + 1, Grade));
                                    }
                                }
                                if (SoldAgen.equals("1")){
                                    if (UpdatePoin == 120){
                                        FinalPoin = 120;
                                        sheet.addCell(new Label(30, i + 1, String.valueOf(FinalPoin)));
                                    } else {
                                        FinalPoin = 100;
                                        sheet.addCell(new Label(30, i + 1, String.valueOf(FinalPoin)));
                                    }
                                } else {
                                    FinalPoin = UpdatePoin;
                                    sheet.addCell(new Label(30, i + 1, String.valueOf(FinalPoin)));
                                }
                                if (Sold.equals("1") || SoldAgen.equals("1")){
                                    sheet.addCell(new Label(31, i + 1, "Sold"));
                                } else if (Rented.equals("1") || RentedAgen.equals("1")) {
                                    sheet.addCell(new Label(31, i + 1, "Rented"));
                                } else {
                                    sheet.addCell(new Label(31, i + 1, "Ready"));
                                }
                                sheet.addCell(new Label(32, i + 1, ""));
                            }

                            workbook.write();
                            workbook.close();

                            Toast.makeText(LaporanListingActivity.this, "File Excel berhasil disimpan", Toast.LENGTH_SHORT).show();

                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            mediaScanIntent.setData(Uri.fromFile(outFile));
                            sendBroadcast(mediaScanIntent);

                            Dialog customDialog = new Dialog(LaporanListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Berhasil Menyimpan Laporan di :"+ outFile.getAbsolutePath());
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(LaporanListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Dialog customDialog = new Dialog(LaporanListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Gagal Menyimpan Laporan" + e.getMessage());
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(LaporanListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Dialog customDialog = new Dialog(LaporanListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Gagal Menyimpan Laporan" + e.getMessage());
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(LaporanListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Dialog customDialog = new Dialog(LaporanListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Gagal Menyimpan Laporan" + e.getMessage());
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(LaporanListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(LaporanListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Gagal Menyimpan Laporan" + error.getMessage());
                        cobalagi.setVisibility(View.GONE);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                                finish();
                            }
                        });

                        Glide.with(LaporanListingActivity.this)
                                .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    private void fetchDataAgenAndCreateExcel() {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_REPORT_AGEN,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            WritableWorkbook workbook = null;

                            File sdCard = Environment.getExternalStorageDirectory();
                            File directory = new File(sdCard.getAbsolutePath() + "/Download");
                            directory.mkdir();

                            String filename = String.format(Locale.getDefault(), "%d.xls", System.currentTimeMillis());
                            File outFile = new File(directory, filename);

                            WorkbookSettings wbSettings = new WorkbookSettings();
                            wbSettings.setLocale(new Locale("en", "EN"));
                            workbook = Workbook.createWorkbook(outFile, wbSettings);

                            WritableSheet sheet = workbook.createSheet("Data", 0);

                            sheet.addCell(new Label(0, 0, "No"));
                            sheet.addCell(new Label(1, 0, "Nama Agen"));
                            sheet.addCell(new Label(2, 0, "No Karyawan"));

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String Nama = jsonObject.getString("Nama");
                                String NoKaryawan = jsonObject.getString("NoKaryawan");
                                String No = String.valueOf(i + 1);

                                sheet.addCell(new Label(0, i + 1, No));
                                sheet.addCell(new Label(1, i + 1, Nama));
                                sheet.addCell(new Label(2, i + 1, NoKaryawan));
                            }

                            workbook.write();
                            workbook.close();

                            Toast.makeText(LaporanListingActivity.this, "File Excel berhasil disimpan", Toast.LENGTH_SHORT).show();

                            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                            mediaScanIntent.setData(Uri.fromFile(outFile));
                            sendBroadcast(mediaScanIntent);

                            Dialog customDialog = new Dialog(LaporanListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Berhasil Menyimpan Laporan di :"+ outFile.getAbsolutePath());
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(LaporanListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Dialog customDialog = new Dialog(LaporanListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Gagal Menyimpan Laporan" + e.getMessage());
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(LaporanListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Dialog customDialog = new Dialog(LaporanListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Gagal Menyimpan Laporan" + e.getMessage());
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(LaporanListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Dialog customDialog = new Dialog(LaporanListingActivity.this);
                            customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            customDialog.setContentView(R.layout.custom_dialog_sukses);

                            if (customDialog.getWindow() != null) {
                                customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                            }

                            TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                            Button ok = customDialog.findViewById(R.id.btnya);
                            Button cobalagi = customDialog.findViewById(R.id.btntidak);
                            ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                            dialogTitle.setText("Gagal Menyimpan Laporan" + e.getMessage());
                            cobalagi.setVisibility(View.GONE);

                            ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    customDialog.dismiss();
                                    finish();
                                }
                            });

                            Glide.with(LaporanListingActivity.this)
                                    .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                    .transition(DrawableTransitionOptions.withCrossFade())
                                    .into(gifimage);

                            customDialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(LaporanListingActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.custom_dialog_sukses);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        TextView dialogTitle = customDialog.findViewById(R.id.dialog_title);
                        Button ok = customDialog.findViewById(R.id.btnya);
                        Button cobalagi = customDialog.findViewById(R.id.btntidak);
                        ImageView gifimage = customDialog.findViewById(R.id.ivdialog);

                        dialogTitle.setText("Gagal Menyimpan Laporan" + error.getMessage());
                        cobalagi.setVisibility(View.GONE);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                                finish();
                            }
                        });

                        Glide.with(LaporanListingActivity.this)
                                .load(R.mipmap.ic_yes) // You can also use a local resource like R.drawable.your_gif_resource
                                .transition(DrawableTransitionOptions.withCrossFade())
                                .into(gifimage);

                        customDialog.show();
                    }
                });

        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
    private void CountTerjual() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_TERJUAL,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVListingSolded.setText(countlike);

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
    private void CountTersewa() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_TERSEWA,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVListingRented.setText(countlike);

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
    private void CountTersedia() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_READY,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVListingReady.setText(countlike);

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
    private void CountSold() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_JUAL,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVListingSold.setText(countlike);

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
    private void CountSoldRent() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_JUALSEWA,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVListingSoldRent.setText(countlike);

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
    private void CountRent() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_SEWA,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVListingRent.setText(countlike);

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
    private void CountAll() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_ALL,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVAllListing.setText(countlike);

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
    private void CountTahun() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_TAHUN,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVListingTahun.setText(countlike);

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
    private void CountBulan() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_COUNT_LISTING_BULAN,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                String countlike = data.getString("sum");

                                TVListingBulan.setText(countlike);

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
}