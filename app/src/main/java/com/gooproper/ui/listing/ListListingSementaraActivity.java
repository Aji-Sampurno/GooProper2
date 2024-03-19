package com.gooproper.ui.listing;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.listing.ListingSementaraAdapter;
import com.gooproper.model.ListingSementaraModel;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListListingSementaraActivity extends AppCompatActivity {

    ProgressDialog PDListingSementara;
    SwipeRefreshLayout srlistingsementara;
    RecyclerView rvlist;
    ListingSementaraAdapter adapter;
    List<ListingSementaraModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_listing_sementara);

        PDListingSementara = new ProgressDialog(ListListingSementaraActivity.this);
        srlistingsementara = findViewById(R.id.SRListingSementara);
        rvlist = findViewById(R.id.RVListingSementara);

        list = new ArrayList<>();

        rvlist.setLayoutManager(new LinearLayoutManager(ListListingSementaraActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new ListingSementaraAdapter(this, list);
        rvlist.setAdapter(adapter);

        LoadListing(true);

        srlistingsementara.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadListing(false);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void LoadListing(boolean showProgressDialog) {
        PDListingSementara.setMessage("Memuat Data...");
        PDListingSementara.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_LISTING_SEMENTARA + Preferences.getKeyIdAgen(ListListingSementaraActivity.this), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDListingSementara.cancel();
                        srlistingsementara.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                ListingSementaraModel md = new ListingSementaraModel();
                                md.setIdShareLokasi(data.getString("IdShareLokasi"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setLokasi(data.getString("Lokasi"));
                                md.setSelfie(data.getString("Selfie"));
                                list.add(md);
                                PDListingSementara.cancel();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDListingSementara.cancel();
                                srlistingsementara.setRefreshing(false);

                                Dialog customDialog = new Dialog(ListListingSementaraActivity.this);
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
                        PDListingSementara.cancel();
                        srlistingsementara.setRefreshing(false);
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(ListListingSementaraActivity.this);
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