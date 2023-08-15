package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.AgenAdapter;
import com.gooproper.adapter.ListingSoldAdapter;
import com.gooproper.model.AgenModel;
import com.gooproper.model.ListingModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AgenActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    private RecyclerView.Adapter adapterAgen;
    List<AgenModel> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agen);

        recyclerView = findViewById(R.id.RVAgenAdmin);
        refreshLayout = findViewById(R.id.SRLAgenAdmin);

        mItems = new ArrayList<>();

        recyclerView.setLayoutManager(new GridLayoutManager(AgenActivity.this, 1));
        adapterAgen = new AgenAdapter(AgenActivity.this, mItems);
        recyclerView.setAdapter(adapterAgen);

        LoadAgen(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void LoadAgen(boolean showProgressDialog) {
        final ProgressDialog progressDialog = new ProgressDialog(AgenActivity.this);
        progressDialog.setMessage("Memuat Data Agen...");
        progressDialog.show();
        if (showProgressDialog) progressDialog.show();
        else progressDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(AgenActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_AGEN, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) progressDialog.cancel();
                        else refreshLayout.setRefreshing(false);
                        mItems.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                AgenModel md = new AgenModel();
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setUsername(data.getString("Username"));
                                md.setPassword(data.getString("Password"));
                                md.setNama(data.getString("Nama"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setEmail(data.getString("Email"));
                                md.setTglLahir(data.getString("TglLahir"));
                                md.setKotaKelahiran(data.getString("KotaKelahiran"));
                                md.setPendidikan(data.getString("Pendidikan"));
                                md.setNamaSekolah(data.getString("NamaSekolah"));
                                md.setMasaKerja(data.getString("MasaKerja"));
                                md.setJabatan(data.getString("Jabatan"));
                                md.setStatus(data.getString("Status"));
                                md.setAlamatDomisili(data.getString("AlamatDomisili"));
                                md.setFacebook(data.getString("Facebook"));
                                md.setInstagram(data.getString("Instagram"));
                                md.setNoKtp(data.getString("NoKtp"));
                                md.setImgKtp(data.getString("ImgKtp"));
                                md.setImgTtd(data.getString("ImgTtd"));
                                md.setNpwp(data.getString("Npwp"));
                                md.setPhoto(data.getString("Photo"));
                                md.setPoin(data.getString("Poin"));
                                md.setIsAkses(data.getString("IsAkses"));
                                mItems.add(md);
                                progressDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();

                                Dialog customDialog = new Dialog(AgenActivity.this);
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
                                        LoadAgen(true);
                                    }
                                });

                                customDialog.show();

                            }
                        }

                        adapterAgen.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(AgenActivity.this);
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
                                LoadAgen(true);
                            }
                        });

                        customDialog.show();
                    }
                });

        queue.add(reqData);
    }
}