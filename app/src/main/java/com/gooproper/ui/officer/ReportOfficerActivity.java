package com.gooproper.ui.officer;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.gooproper.R;
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.adapter.officer.ReportKinerjaAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.model.ReportKinerjaModel;
import com.gooproper.ui.LaporanListingActivity;
import com.gooproper.ui.listing.NewActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ReportOfficerActivity extends AppCompatActivity {

    ProgressDialog PDialog;
    Button BtnTambahReport, BtnRekapReport;
    SwipeRefreshLayout SRReport;
    RecyclerView RVReport;
    ReportKinerjaAdapter adapter;
    List<ReportKinerjaModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_officer);

        PDialog = new ProgressDialog(ReportOfficerActivity.this);

        BtnTambahReport = findViewById(R.id.BtnTambahReport);
        BtnRekapReport = findViewById(R.id.BtnRekapReport);
        RVReport = findViewById(R.id.RVReport);
        SRReport = findViewById(R.id.SRReport);

        BtnTambahReport.setOnClickListener(v -> startActivity(new Intent(ReportOfficerActivity.this, TambahReportOfficerActivity.class)));
        BtnRekapReport.setOnClickListener(v -> createPdf());

        LoadReport(true);

        list = new ArrayList<>();

        RVReport.setLayoutManager(new LinearLayoutManager(ReportOfficerActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new ReportKinerjaAdapter(this, list);
        RVReport.setAdapter(adapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void LoadReport(boolean showProgressDialog) {
        PDialog.setMessage("Memuat Data...");
        PDialog.show();
        if (showProgressDialog) PDialog.show();
        else PDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_REPORT_KINERJA_OFFICER+ Preferences.getKeyIdAgen(ReportOfficerActivity.this), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDialog.cancel();
                        else SRReport.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ReportKinerjaModel md = new ReportKinerjaModel();
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setLocation(data.getString("Location"));
                                md.setWilayah(data.getString("Wilayah"));
                                md.setJenisProperti(data.getString("JenisProperti"));
                                md.setKondisi(data.getString("Kondisi"));
                                md.setPriority(data.getString("Priority"));
                                md.setImg1(data.getString("Img1"));
                                md.setImg2(data.getString("Img2"));
                                md.setTemplate(data.getString("Template"));
                                md.setTemplateBlank(data.getString("TemplateBlank"));
                                md.setKeterangan(data.getString("Keterangan"));
                                list.add(md);
                                PDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDialog.dismiss();

                                Dialog customDialog = new Dialog(ReportOfficerActivity.this);
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
                                        LoadReport(true);
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
                        PDialog.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(ReportOfficerActivity.this);
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
                                LoadReport(true);
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
    private void createPdf() {
        String url1 = ServerApi.URL_GET_URAIAN_KERJA_OFFICER+Preferences.getKeyIdAgen(ReportOfficerActivity.this);
        String url2 = ServerApi.URL_GET_CALL_OFFICER+Preferences.getKeyIdAgen(ReportOfficerActivity.this);
        String url3 = ServerApi.URL_GET_FOLLOW_UP_INFO_OFFICER+Preferences.getKeyIdAgen(ReportOfficerActivity.this);
        String url4 = ServerApi.URL_GET_FOLLOW_UP_VENDOR_OFFICER+Preferences.getKeyIdAgen(ReportOfficerActivity.this);
        String url5 = ServerApi.URL_GET_FOLLOW_UP_BUYER_OFFICER+Preferences.getKeyIdAgen(ReportOfficerActivity.this);
        String url6 = ServerApi.URL_GET_TINJAU_LOKASI_OFFICER+Preferences.getKeyIdAgen(ReportOfficerActivity.this);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        PDialog.setMessage("Menyimpan Data");
        PDialog.setCancelable(false);
        PDialog.show();

        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray jsonArray1) {
                        PDialog.cancel();
                        try {
                            JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray jsonArray2) {
                                            PDialog.cancel();
                                            try {
                                                JsonArrayRequest jsonArrayRequest3 = new JsonArrayRequest(Request.Method.GET, url3, null,
                                                        new Response.Listener<JSONArray>() {
                                                            @Override
                                                            public void onResponse(JSONArray jsonArray3) {
                                                                PDialog.cancel();
                                                                try {
                                                                    JsonArrayRequest jsonArrayRequest4 = new JsonArrayRequest(Request.Method.GET, url4, null,
                                                                            new Response.Listener<JSONArray>() {
                                                                                @Override
                                                                                public void onResponse(JSONArray jsonArray4) {
                                                                                    PDialog.cancel();
                                                                                    try {
                                                                                        JsonArrayRequest jsonArrayRequest5 = new JsonArrayRequest(Request.Method.GET, url5, null,
                                                                                                new Response.Listener<JSONArray>() {
                                                                                                    @Override
                                                                                                    public void onResponse(JSONArray jsonArray5) {
                                                                                                        PDialog.cancel();
                                                                                                        try {
                                                                                                            JsonArrayRequest jsonArrayRequest6 = new JsonArrayRequest(Request.Method.GET, url6, null,
                                                                                                                    new Response.Listener<JSONArray>() {
                                                                                                                        @Override
                                                                                                                        public void onResponse(JSONArray jsonArray6) {
                                                                                                                            PDialog.cancel();
                                                                                                                            try {
                                                                                                                                String NamaReportAgen = Preferences.getKeyNama(ReportOfficerActivity.this);

                                                                                                                                Calendar calendar = Calendar.getInstance();
                                                                                                                                int day = calendar.get(Calendar.DAY_OF_WEEK);
                                                                                                                                int date = calendar.get(Calendar.DAY_OF_MONTH);

                                                                                                                                String[] days = new String[]{"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
                                                                                                                                String dayName = days[day - 1];

                                                                                                                                DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                                                                                                                                String formattedDate = dateFormat.format(calendar.getTime());

                                                                                                                                File sdCard = Environment.getExternalStorageDirectory();
                                                                                                                                File directory = new File(sdCard.getAbsolutePath() + "/Download");
                                                                                                                                directory.mkdirs();

                                                                                                                                File file = new File(directory, "Laporan_Kinerja_"+NamaReportAgen+"_"+formattedDate+".pdf");
                                                                                                                                Document document = new Document();

                                                                                                                                PdfWriter.getInstance(document, new FileOutputStream(file));
                                                                                                                                document.open();

                                                                                                                                document.add(new Paragraph("Laporan Kinerja Harian"));
                                                                                                                                document.add(new Paragraph("Nama  : " + Preferences.getKeyNama(ReportOfficerActivity.this)));
                                                                                                                                document.add(new Paragraph("Hari/Tanggal : " + dayName + " / " + formattedDate));
                                                                                                                                document.add(new Paragraph(" "));
                                                                                                                                document.add(new Paragraph("Uraian Kerja :"));

                                                                                                                                for (int i = 0; i < jsonArray1.length(); i++) {
                                                                                                                                    JSONObject jsonObject = jsonArray1.getJSONObject(i);

                                                                                                                                    String Keterangan = jsonObject.getString("Keterangan");
                                                                                                                                    String NamaListing = jsonObject.getString("NamaListing");
                                                                                                                                    String Alamat = jsonObject.getString("Alamat");
                                                                                                                                    String Location = jsonObject.getString("Location");
                                                                                                                                    String Wilayah = jsonObject.getString("Wilayah");
                                                                                                                                    String JenisProperti = jsonObject.getString("JenisProperti");
                                                                                                                                    String Kondisi = jsonObject.getString("Kondisi");
                                                                                                                                    String Priority = jsonObject.getString("Priority");
                                                                                                                                    String Img1 = jsonObject.getString("Img1");
                                                                                                                                    String Img2 = jsonObject.getString("Img2");
                                                                                                                                    String Template = jsonObject.getString("Template");
                                                                                                                                    String TemplateBlank = jsonObject.getString("TemplateBlank");
                                                                                                                                    String No = String.valueOf(i + 1);

                                                                                                                                    document.add(new Paragraph(No + ". " + Keterangan));
                                                                                                                                }

                                                                                                                                document.add(new Paragraph(" "));
                                                                                                                                document.add(new Paragraph("Call"));

                                                                                                                                for (int i = 0; i < jsonArray2.length(); i++) {
                                                                                                                                    JSONObject jsonObject = jsonArray2.getJSONObject(i);

                                                                                                                                    String Keterangan = jsonObject.getString("Keterangan");
                                                                                                                                    String NamaListing = jsonObject.getString("NamaListing");
                                                                                                                                    String Alamat = jsonObject.getString("Alamat");
                                                                                                                                    String Location = jsonObject.getString("Location");
                                                                                                                                    String Wilayah = jsonObject.getString("Wilayah");
                                                                                                                                    String JenisProperti = jsonObject.getString("JenisProperti");
                                                                                                                                    String Kondisi = jsonObject.getString("Kondisi");
                                                                                                                                    String Priority = jsonObject.getString("Priority");
                                                                                                                                    String Img1 = jsonObject.getString("Img1");
                                                                                                                                    String Img2 = jsonObject.getString("Img2");
                                                                                                                                    String Template = jsonObject.getString("Template");
                                                                                                                                    String TemplateBlank = jsonObject.getString("TemplateBlank");
                                                                                                                                    String No = String.valueOf(i + 1);

                                                                                                                                    document.add(new Paragraph(No + ". " + Keterangan));
                                                                                                                                }

                                                                                                                                document.add(new Paragraph(" "));
                                                                                                                                document.add(new Paragraph("Follow Up Info"));

                                                                                                                                for (int i = 0; i < jsonArray3.length(); i++) {
                                                                                                                                    JSONObject jsonObject = jsonArray3.getJSONObject(i);

                                                                                                                                    String Keterangan = jsonObject.getString("Keterangan");
                                                                                                                                    String NamaListing = jsonObject.getString("NamaListing");
                                                                                                                                    String Alamat = jsonObject.getString("Alamat");
                                                                                                                                    String Location = jsonObject.getString("Location");
                                                                                                                                    String Wilayah = jsonObject.getString("Wilayah");
                                                                                                                                    String JenisProperti = jsonObject.getString("JenisProperti");
                                                                                                                                    String Kondisi = jsonObject.getString("Kondisi");
                                                                                                                                    String Priority = jsonObject.getString("Priority");
                                                                                                                                    String Img1 = jsonObject.getString("Img1");
                                                                                                                                    String Img2 = jsonObject.getString("Img2");
                                                                                                                                    String Template = jsonObject.getString("Template");
                                                                                                                                    String TemplateBlank = jsonObject.getString("TemplateBlank");
                                                                                                                                    String No = String.valueOf(i + 1);

                                                                                                                                    document.add(new Paragraph(No + ". " + Keterangan));
                                                                                                                                }

                                                                                                                                document.add(new Paragraph(" "));
                                                                                                                                document.add(new Paragraph("Follow Up Vendor"));

                                                                                                                                for (int i = 0; i < jsonArray4.length(); i++) {
                                                                                                                                    JSONObject jsonObject = jsonArray4.getJSONObject(i);

                                                                                                                                    String Keterangan = jsonObject.getString("Keterangan");
                                                                                                                                    String NamaListing = jsonObject.getString("NamaListing");
                                                                                                                                    String Alamat = jsonObject.getString("Alamat");
                                                                                                                                    String Location = jsonObject.getString("Location");
                                                                                                                                    String Wilayah = jsonObject.getString("Wilayah");
                                                                                                                                    String JenisProperti = jsonObject.getString("JenisProperti");
                                                                                                                                    String Kondisi = jsonObject.getString("Kondisi");
                                                                                                                                    String Priority = jsonObject.getString("Priority");
                                                                                                                                    String Img1 = jsonObject.getString("Img1");
                                                                                                                                    String Img2 = jsonObject.getString("Img2");
                                                                                                                                    String Template = jsonObject.getString("Template");
                                                                                                                                    String TemplateBlank = jsonObject.getString("TemplateBlank");
                                                                                                                                    String No = String.valueOf(i + 1);

                                                                                                                                    document.add(new Paragraph(No + ". " + Keterangan));
                                                                                                                                }

                                                                                                                                document.add(new Paragraph(" "));
                                                                                                                                document.add(new Paragraph("Follow Up Buyer"));

                                                                                                                                for (int i = 0; i < jsonArray5.length(); i++) {
                                                                                                                                    JSONObject jsonObject = jsonArray5.getJSONObject(i);

                                                                                                                                    String Keterangan = jsonObject.getString("Keterangan");
                                                                                                                                    String NamaListing = jsonObject.getString("NamaListing");
                                                                                                                                    String Alamat = jsonObject.getString("Alamat");
                                                                                                                                    String Location = jsonObject.getString("Location");
                                                                                                                                    String Wilayah = jsonObject.getString("Wilayah");
                                                                                                                                    String JenisProperti = jsonObject.getString("JenisProperti");
                                                                                                                                    String Kondisi = jsonObject.getString("Kondisi");
                                                                                                                                    String Priority = jsonObject.getString("Priority");
                                                                                                                                    String Img1 = jsonObject.getString("Img1");
                                                                                                                                    String Img2 = jsonObject.getString("Img2");
                                                                                                                                    String Template = jsonObject.getString("Template");
                                                                                                                                    String TemplateBlank = jsonObject.getString("TemplateBlank");
                                                                                                                                    String No = String.valueOf(i + 1);

                                                                                                                                    document.add(new Paragraph(No + ". " + Keterangan));
                                                                                                                                }

                                                                                                                                document.add(new Paragraph(" "));
                                                                                                                                document.add(new Paragraph("Tinjau Lokasi"));

                                                                                                                                for (int i = 0; i < jsonArray6.length(); i++) {
                                                                                                                                    JSONObject jsonObject = jsonArray6.getJSONObject(i);

                                                                                                                                    String Keterangan = jsonObject.getString("Keterangan");
                                                                                                                                    String NamaListing = jsonObject.getString("NamaListing");
                                                                                                                                    String Alamat = jsonObject.getString("Alamat");
                                                                                                                                    String Location = jsonObject.getString("Location");
                                                                                                                                    String Wilayah = jsonObject.getString("Wilayah");
                                                                                                                                    String JenisProperti = jsonObject.getString("JenisProperti");
                                                                                                                                    String Kondisi = jsonObject.getString("Kondisi");
                                                                                                                                    String Priority = jsonObject.getString("Priority");
                                                                                                                                    String Img1 = jsonObject.getString("Img1");
                                                                                                                                    String Img2 = jsonObject.getString("Img2");
                                                                                                                                    String Template = jsonObject.getString("Template");
                                                                                                                                    String TemplateBlank = jsonObject.getString("TemplateBlank");
                                                                                                                                    String No = String.valueOf(i + 1);

                                                                                                                                    document.add(new Paragraph(No + ". " + JenisProperti + " " + Kondisi + " " + Alamat + " ( " + Keterangan + " )"));
                                                                                                                                }

                                                                                                                                document.close();

                                                                                                                                Toast.makeText(ReportOfficerActivity.this, "PDF saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
                                                                                                                            } catch (DocumentException | FileNotFoundException | JSONException e) {
                                                                                                                                PDialog.cancel();
                                                                                                                                e.printStackTrace();
                                                                                                                                Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                                            }
                                                                                                                        }
                                                                                                                    },
                                                                                                                    new Response.ErrorListener() {
                                                                                                                        @Override
                                                                                                                        public void onErrorResponse(VolleyError volleyError) {
                                                                                                                            PDialog.cancel();
                                                                                                                        }
                                                                                                                    });
                                                                                                            requestQueue.add(jsonArrayRequest6);

                                                                                                        } catch (Exception e) {
                                                                                                            PDialog.cancel();
                                                                                                            e.printStackTrace();
                                                                                                            Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                                        }
                                                                                                    }
                                                                                                },
                                                                                                new Response.ErrorListener() {
                                                                                                    @Override
                                                                                                    public void onErrorResponse(VolleyError volleyError) {
                                                                                                        PDialog.cancel();
                                                                                                    }
                                                                                                });
                                                                                        requestQueue.add(jsonArrayRequest5);

                                                                                    } catch (Exception e) {
                                                                                        PDialog.cancel();
                                                                                        e.printStackTrace();
                                                                                        Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            },
                                                                            new Response.ErrorListener() {
                                                                                @Override
                                                                                public void onErrorResponse(VolleyError volleyError) {
                                                                                    PDialog.cancel();
                                                                                }
                                                                            });
                                                                    requestQueue.add(jsonArrayRequest4);

                                                                } catch (Exception e) {
                                                                    PDialog.cancel();
                                                                    e.printStackTrace();
                                                                    Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        },
                                                        new Response.ErrorListener() {
                                                            @Override
                                                            public void onErrorResponse(VolleyError volleyError) {
                                                                PDialog.cancel();
                                                            }
                                                        });
                                                requestQueue.add(jsonArrayRequest3);

                                            } catch (Exception e) {
                                                PDialog.cancel();
                                                e.printStackTrace();
                                                Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            PDialog.cancel();
                                        }
                                    });
                            requestQueue.add(jsonArrayRequest2);

                        } catch (Exception e) {
                            PDialog.cancel();
                            e.printStackTrace();
                            Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        PDialog.cancel();
                    }
                });
        requestQueue.add(jsonArrayRequest1);

//        JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(Request.Method.GET, url1, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response1) {
//                        try {
//                            JsonArrayRequest jsonArrayRequest2 = new JsonArrayRequest(Request.Method.GET, url2, null,
//                                    new Response.Listener<JSONArray>() {
//                                        @Override
//                                        public void onResponse(JSONArray response2) {
//                                            try {
//                                                Calendar calendar = Calendar.getInstance();
//                                                int day = calendar.get(Calendar.DAY_OF_WEEK);
//                                                int date = calendar.get(Calendar.DAY_OF_MONTH);
//
//                                                String[] days = new String[]{"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
//                                                String dayName = days[day - 1];
//
//                                                DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//                                                String formattedDate = dateFormat.format(calendar.getTime());
//
//                                                File sdCard = Environment.getExternalStorageDirectory();
//                                                File directory = new File(sdCard.getAbsolutePath() + "/Download");
//                                                directory.mkdirs();
//
//                                                File file = new File(directory, "info_laporan.pdf");
//                                                Document document = new Document();
//
//                                                PdfWriter.getInstance(document, new FileOutputStream(file));
//                                                document.open();
//
//                                                document.add(new Paragraph("Laporan Kinerja Harian"));
//                                                document.add(new Paragraph("Nama  : " + Preferences.getKeyNama(ReportOfficerActivity.this)));
//                                                document.add(new Paragraph("Hari/Tanggal : " + dayName + " / " + formattedDate));
//                                                document.add(new Paragraph(" "));
//                                                document.add(new Paragraph("Uraian Kerja"));
//                                                document.add(new Paragraph(" "));
//
//                                                // Add data from first JSON array
//                                                for (int i = 0; i < response1.length(); i++) {
//                                                    JSONObject jsonObject = response1.getJSONObject(i);
//                                                    // Assuming your JSON structure matches your cell positions in the PDF
//                                                    String jenisProperty = jsonObject.getString("JenisProperty");
//                                                    String statusProperty = jsonObject.getString("StatusProperty");
//                                                    String narahubung = jsonObject.getString("Narahubung");
//                                                    // Add more fields as needed
//
//                                                    // Add the data to the PDF
//                                                    document.add(new Paragraph("Jenis Property: " + jenisProperty));
//                                                    document.add(new Paragraph("Status Property: " + statusProperty));
//                                                    document.add(new Paragraph("Narahubung: " + narahubung));
//                                                    document.add(new Paragraph("------------------------------------"));
//                                                }
//
//                                                document.add(new Paragraph("Call"));
//                                                document.add(new Paragraph(" "));
//                                                document.add(new Paragraph("Follow Up Info"));
//                                                document.add(new Paragraph(" "));
//                                                document.add(new Paragraph("Follow Up Vendor"));
//                                                document.add(new Paragraph(" "));
//                                                document.add(new Paragraph("Follow Up Buyer"));
//                                                document.add(new Paragraph(" "));
//                                                document.add(new Paragraph("Tinjau Lokasi"));
//                                                document.add(new Paragraph(" "));
//
//                                                // Add data from second JSON array
//                                                for (int i = 0; i < response2.length(); i++) {
//                                                    JSONObject jsonObject = response2.getJSONObject(i);
//                                                    // Assuming your JSON structure matches your cell positions in the PDF
//                                                    String jenisProperty = jsonObject.getString("JenisProperty");
//                                                    String statusProperty = jsonObject.getString("StatusProperty");
//                                                    String narahubung = jsonObject.getString("Narahubung");
//                                                    // Add more fields as needed
//
//                                                    // Add the data to the PDF
//                                                    document.add(new Paragraph("Jenis Property 2: " + jenisProperty));
//                                                    document.add(new Paragraph("Status Property 2: " + statusProperty));
//                                                    document.add(new Paragraph("Narahubung 2: " + narahubung));
//                                                    document.add(new Paragraph("------------------------------------"));
//                                                }
//
//                                                document.add(new Paragraph("Follow Up Info"));
//                                                document.add(new Paragraph(" "));
//                                                document.add(new Paragraph("Follow Up Vendor"));
//                                                document.add(new Paragraph(" "));
//                                                document.add(new Paragraph("Follow Up Buyer"));
//                                                document.add(new Paragraph(" "));
//                                                document.add(new Paragraph("Tinjau Lokasi"));
//                                                document.add(new Paragraph(" "));
//
//                                                document.close();
//
//                                                Toast.makeText(ReportOfficerActivity.this, "PDF saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                                            } catch (DocumentException | FileNotFoundException | JSONException e) {
//                                                e.printStackTrace();
//                                                Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    },
//                                    new Response.ErrorListener() {
//                                        @Override
//                                        public void onErrorResponse(VolleyError error) {
//                                            error.printStackTrace();
//                                            Toast.makeText(ReportOfficerActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
//
//                            requestQueue.add(jsonArrayRequest2);
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        Toast.makeText(ReportOfficerActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        requestQueue.add(jsonArrayRequest1);
    }
//    private void createPdf() {
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_INFO_LAPORAN, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        try {
//                            Calendar calendar = Calendar.getInstance();
//                            int day = calendar.get(Calendar.DAY_OF_WEEK);
//                            int date = calendar.get(Calendar.DAY_OF_MONTH);
//
//                            String[] days = new String[]{"Minggu", "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
//                            String dayName = days[day - 1];
//
//                            DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
//                            String formattedDate = dateFormat.format(calendar.getTime());
//
//                            File sdCard = Environment.getExternalStorageDirectory();
//                            File directory = new File(sdCard.getAbsolutePath() + "/Download");
//                            directory.mkdirs();
//
//                            File file = new File(directory, "info_laporan.pdf");
//                            Document document = new Document();
//
//                            PdfWriter.getInstance(document, new FileOutputStream(file));
//                            document.open();
//
//                            document.add(new Paragraph("Laporan Kinerja Harian"));
//                            document.add(new Paragraph("Nama  : " + Preferences.getKeyNama(ReportOfficerActivity.this)));
//                            document.add(new Paragraph("Hari/Tanggal : " + dayName + " / " + formattedDate));
//                            document.add(new Paragraph(" "));
//                            document.add(new Paragraph("Uraian Kerja"));
//                            document.add(new Paragraph(" "));
//                            document.add(new Paragraph("Call"));
//                            document.add(new Paragraph(" "));
//                            document.add(new Paragraph("Follow Up Info"));
//                            document.add(new Paragraph(" "));
//                            document.add(new Paragraph("Follow Up Vendor"));
//                            document.add(new Paragraph(" "));
//                            document.add(new Paragraph("Follow Up Buyer"));
//                            document.add(new Paragraph(" "));
//                            document.add(new Paragraph("Tinjau Lokasi"));
//                            document.add(new Paragraph(" "));
//
//                            for (int i = 0; i < response.length(); i++) {
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                // Assuming your JSON structure matches your cell positions in the PDF
//                                String jenisProperty = jsonObject.getString("JenisProperty");
//                                String statusProperty = jsonObject.getString("StatusProperty");
//                                String narahubung = jsonObject.getString("Narahubung");
//                                String img = jsonObject.getString("ImgProperty");
//                                // Add more fields as needed
//
//                                // Add the data to the PDF
//                                document.add(new Paragraph("Jenis Property: " + jenisProperty));
//                                document.add(new Paragraph("Status Property: " + statusProperty));
//                                document.add(new Paragraph("Narahubung: " + narahubung));
//
//                                Anchor anchor = new Anchor("Lihat Gambar");
//                                anchor.setReference(img);
//                                document.add(anchor);
//                                // Add more fields as needed
//                                document.add(new Paragraph("------------------------------------"));
//                            }
//
//                            document.close();
//
//                            Toast.makeText(ReportOfficerActivity.this, "PDF saved to " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
//                        } catch (DocumentException | FileNotFoundException | JSONException e) {
//                            e.printStackTrace();
//                            Toast.makeText(ReportOfficerActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        Toast.makeText(ReportOfficerActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//        Volley.newRequestQueue(this).add(jsonArrayRequest);
//    }
}