package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.AgenAdminAdapter;
import com.gooproper.model.AgenModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReportAgenActivity extends AppCompatActivity {

    private RecyclerView RVAktif, RVTidakAktif;
    private AgenAdminAdapter adapterAgenAktif;
    private AgenAdminAdapter adapterAgenTidakAktif;
    List<AgenModel> mItemsTidakAktif;
    List<AgenModel> mItemsAktif;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_agen);

        RVAktif = findViewById(R.id.AgenAktif);
        RVTidakAktif = findViewById(R.id.AgenTidakAktif);

        mItemsTidakAktif = new ArrayList<>();
        mItemsAktif = new ArrayList<>();

        RVTidakAktif.setLayoutManager(new LinearLayoutManager(ReportAgenActivity.this, LinearLayoutManager.VERTICAL, false));
        adapterAgenTidakAktif = new AgenAdminAdapter(ReportAgenActivity.this, mItemsTidakAktif);
        RVTidakAktif.setAdapter(adapterAgenTidakAktif);

        RVAktif.setLayoutManager(new LinearLayoutManager(ReportAgenActivity.this, LinearLayoutManager.VERTICAL, false));
        adapterAgenAktif = new AgenAdminAdapter(ReportAgenActivity.this, mItemsAktif);
        RVAktif.setAdapter(adapterAgenAktif);

        LoadAgenAktif();
        LoadAgenTidakAktif();

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void LoadAgenAktif() {
        RequestQueue queue = Volley.newRequestQueue(ReportAgenActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_AGEN_AKTIF, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsAktif.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                AgenModel agenModel = new AgenModel();
                                agenModel.setIdAgen(data.getString("IdAgen"));
                                agenModel.setUsername(data.getString("Username"));
                                agenModel.setPassword(data.getString("Password"));
                                agenModel.setNama(data.getString("Nama"));
                                agenModel.setNoTelp(data.getString("NoTelp"));
                                agenModel.setEmail(data.getString("Email"));
                                agenModel.setTglLahir(data.getString("TglLahir"));
                                agenModel.setKotaKelahiran(data.getString("KotaKelahiran"));
                                agenModel.setPendidikan(data.getString("Pendidikan"));
                                agenModel.setNamaSekolah(data.getString("NamaSekolah"));
                                agenModel.setMasaKerja(data.getString("MasaKerja"));
                                agenModel.setJabatan(data.getString("Jabatan"));
                                agenModel.setKonfirmasi(data.getString("Konfirmasi"));
                                agenModel.setStatus(data.getString("Status"));
                                agenModel.setAlamatDomisili(data.getString("AlamatDomisili"));
                                agenModel.setFacebook(data.getString("Facebook"));
                                agenModel.setInstagram (data.getString("Instagram"));
                                agenModel.setNoKtp(data.getString("NoKtp"));
                                agenModel.setImgKtp(data.getString("ImgKtp"));
                                agenModel.setImgTtd(data.getString("ImgTtd"));
                                agenModel.setPhoto(data.getString("Photo"));
                                agenModel.setIsAkses(data.getString("IsAkses"));
                                agenModel.setApprove(data.getString("Approve"));
                                agenModel.setNoKaryawan(data.getString("NoKaryawan"));
                                mItemsAktif.add(agenModel);
                            } catch (JSONException e) {
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(ReportAgenActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.alert_eror);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                Button ok = customDialog.findViewById(R.id.BTNOkEror);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                        LoadAgenAktif();
                                    }
                                });

                                customDialog.show();

                            }
                        }

                        adapterAgenAktif.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(ReportAgenActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.alert_eror);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BTNOkEror);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                                LoadAgenAktif();
                            }
                        });

                        customDialog.show();
                    }
                });

        queue.add(reqData);
    }
    private void LoadAgenTidakAktif() {
        RequestQueue queue = Volley.newRequestQueue(ReportAgenActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_AGEN_TIDAK_AKTIF, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        mItemsTidakAktif.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                AgenModel agenModel = new AgenModel();
                                agenModel.setIdAgen(data.getString("IdAgen"));
                                agenModel.setUsername(data.getString("Username"));
                                agenModel.setPassword(data.getString("Password"));
                                agenModel.setNama(data.getString("Nama"));
                                agenModel.setNoTelp(data.getString("NoTelp"));
                                agenModel.setEmail(data.getString("Email"));
                                agenModel.setTglLahir(data.getString("TglLahir"));
                                agenModel.setKotaKelahiran(data.getString("KotaKelahiran"));
                                agenModel.setPendidikan(data.getString("Pendidikan"));
                                agenModel.setNamaSekolah(data.getString("NamaSekolah"));
                                agenModel.setMasaKerja(data.getString("MasaKerja"));
                                agenModel.setJabatan(data.getString("Jabatan"));
                                agenModel.setKonfirmasi(data.getString("Konfirmasi"));
                                agenModel.setStatus(data.getString("Status"));
                                agenModel.setAlamatDomisili(data.getString("AlamatDomisili"));
                                agenModel.setFacebook(data.getString("Facebook"));
                                agenModel.setInstagram (data.getString("Instagram"));
                                agenModel.setNoKtp(data.getString("NoKtp"));
                                agenModel.setImgKtp(data.getString("ImgKtp"));
                                agenModel.setImgTtd(data.getString("ImgTtd"));
                                agenModel.setPhoto(data.getString("Photo"));
                                agenModel.setIsAkses(data.getString("IsAkses"));
                                agenModel.setApprove(data.getString("Approve"));
                                agenModel.setNoKaryawan(data.getString("NoKaryawan"));
                                mItemsTidakAktif.add(agenModel);
                            } catch (JSONException e) {
                                e.printStackTrace();

                                Dialog customDialog = new Dialog(ReportAgenActivity.this);
                                customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                customDialog.setContentView(R.layout.alert_eror);

                                if (customDialog.getWindow() != null) {
                                    customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                                }

                                Button ok = customDialog.findViewById(R.id.BTNOkEror);

                                ok.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                        LoadAgenTidakAktif();
                                    }
                                });

                                customDialog.show();

                            }
                        }

                        adapterAgenTidakAktif.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(ReportAgenActivity.this);
                        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        customDialog.setContentView(R.layout.alert_eror);

                        if (customDialog.getWindow() != null) {
                            customDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                        }

                        Button ok = customDialog.findViewById(R.id.BTNOkEror);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                                LoadAgenTidakAktif();
                            }
                        });

                        customDialog.show();
                    }
                });

        queue.add(reqData);
    }
}