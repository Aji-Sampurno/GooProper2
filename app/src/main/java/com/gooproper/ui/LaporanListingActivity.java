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

    Button BtnUnduh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laporan_listing);

        BtnUnduh = findViewById(R.id.BtnUnduh);

        BtnUnduh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchDataAndCreateExcel();
            }
        });
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

        Map<String, String> params = new HashMap<>();
        params.put("TglAwal", "2023-11-11");
        params.put("TglAkhir", "2023-11-11");

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_LAPORAN,new JSONArray(new ArrayList<>(params.entrySet())),
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
                            sheet.addCell(new Label(1, 0, "Upload dan Template"));
                            sheet.addCell(new Label(2, 0, "KET"));
                            sheet.addCell(new Label(3, 0, "KODE"));
                            sheet.addCell(new Label(4, 0, "PJP"));
                            sheet.addCell(new Label(5, 0, "ALAMAT"));
                            sheet.addCell(new Label(6, 0, "WILAYAH"));
                            sheet.addCell(new Label(7, 0, "NOMOR"));
                            sheet.addCell(new Label(8, 0, "NAMA PEMILIK"));
                            sheet.addCell(new Label(9, 0, "E/O"));
                            sheet.addCell(new Label(10, 0, "J/S"));
                            sheet.addCell(new Label(11, 0, "LT"));
                            sheet.addCell(new Label(12, 0, "LB"));
                            sheet.addCell(new Label(13, 0, "LANTAI"));
                            sheet.addCell(new Label(14, 0, "HARGA JUAL"));
                            sheet.addCell(new Label(15, 0, "HARGA"));
                            sheet.addCell(new Label(16, 0, "STATUS"));
                            sheet.addCell(new Label(17, 0, "NOTE"));
                            sheet.addCell(new Label(18, 0, "HDP"));
                            sheet.addCell(new Label(19, 0, "LISTRIK"));
                            sheet.addCell(new Label(20, 0, "AIR"));
                            sheet.addCell(new Label(21, 0, "FEE"));
                            sheet.addCell(new Label(22, 0, "MA"));
                            sheet.addCell(new Label(23, 0, "TGL"));
                            sheet.addCell(new Label(24, 0, "Harga"));
                            sheet.addCell(new Label(25, 0, "Marketable"));
                            sheet.addCell(new Label(26, 0, "BANNER"));
                            sheet.addCell(new Label(27, 0, "BINTANG"));
                            sheet.addCell(new Label(28, 0, "Status Property"));
                            sheet.addCell(new Label(29, 0, "UPLOAD"));

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                String IdListing = jsonObject.getString("IdListing");
                                String IdAgen = jsonObject.getString("IdAgen");
                                String IdAgenCo = jsonObject.getString("IdAgenCo");
                                String NamaListing = jsonObject.getString("NamaListing");
                                String AlamatListing = jsonObject.getString("Alamat");
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
                                String Pjp = jsonObject.getString("Pjp");
                                String JenisProperti = jsonObject.getString("JenisProperti");
                                String SumberAir = jsonObject.getString("SumberAir");
                                String Kondisi = jsonObject.getString("Kondisi");
                                String Deskripsi = jsonObject.getString("Deskripsi");
                                String Prabot = jsonObject.getString("Prabot");
                                String Priority = jsonObject.getString("Priority");
                                String Banner = jsonObject.getString("Banner");
                                String Harga = jsonObject.getString("Harga");
                                String HargaSewa = jsonObject.getString("HargaSewa");
                                String TglInput = jsonObject.getString("TglInput");
                                String Sold = jsonObject.getString("Sold");
                                String Rented = jsonObject.getString("Rented");
                                String Marketable = jsonObject.getString("Marketable");
                                String StatusHarga = jsonObject.getString("StatusHarga");
                                String NamaAgen = jsonObject.getString("Nama");
                                String NoTelpAgen = jsonObject.getString("NoTelp");
                                String Instagram = jsonObject.getString("Instagram");
                                String Fee = jsonObject.getString("Fee");
                                String NamaVendor = jsonObject.getString("NamaVendor");
                                String NoTelpVendor = jsonObject.getString("NoTelpVendor");
                                String No = String.valueOf(i + 1);

                                sheet.addCell(new Label(0, i + 1, No));
                                sheet.addCell(new Label(1, i + 1, ""));
                                sheet.addCell(new Label(2, i + 1, ""));
                                sheet.addCell(new Label(3, i + 1, ""));
                                sheet.addCell(new Label(4, i + 1, Pjp));
                                sheet.addCell(new Label(5, i + 1, AlamatListing));
                                sheet.addCell(new Label(6, i + 1, ""));
                                sheet.addCell(new Label(7, i + 1, NoTelpVendor));
                                sheet.addCell(new Label(8, i + 1, NamaVendor));
                                sheet.addCell(new Label(9, i + 1, Priority));
                                sheet.addCell(new Label(10, i + 1, Kondisi));
                                sheet.addCell(new Label(11, i + 1, Wide));
                                sheet.addCell(new Label(12, i + 1, Land));
                                sheet.addCell(new Label(13, i + 1, Level));
                                sheet.addCell(new Label(14, i + 1, Harga));
                                sheet.addCell(new Label(15, i + 1, HargaSewa));
                                sheet.addCell(new Label(16, i + 1, ""));
                                sheet.addCell(new Label(17, i + 1, Deskripsi));
                                sheet.addCell(new Label(18, i + 1, Hadap));
                                sheet.addCell(new Label(19, i + 1, Listrik));
                                sheet.addCell(new Label(20, i + 1, SumberAir));
                                sheet.addCell(new Label(21, i + 1, Fee));
                                sheet.addCell(new Label(22, i + 1, ""));
                                sheet.addCell(new Label(23, i + 1, TglInput));
                                sheet.addCell(new Label(24, i + 1, StatusHarga));
                                sheet.addCell(new Label(25, i + 1, Marketable));
                                sheet.addCell(new Label(26, i + 1, Banner));
                                sheet.addCell(new Label(27, i + 1, ""));
                                if (Sold.equals("1")){
                                    sheet.addCell(new Label(28, i + 1, "Sold"));
                                } else if (Rented.equals("1")) {
                                    sheet.addCell(new Label(28, i + 1, "Rented"));
                                } else {
                                    sheet.addCell(new Label(28, i + 1, "Ready"));
                                }
                                sheet.addCell(new Label(29, i + 1, ""));
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

        // Menambahkan request ke dalam antrian Volley
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }
}