package com.gooproper.ui.listing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.InfoAdapter;
import com.gooproper.adapter.ListingAdapter;
import com.gooproper.model.InfoModel;
import com.gooproper.model.ListingModel;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class InfoPropertyKuActivity extends AppCompatActivity {
    ProgressDialog PDInfoKu;
    ImageView IVSortAsc, IVSortDesc, IVFilter;
    SwipeRefreshLayout srinfoku;
    RecyclerView rvlist;
    InfoAdapter adapter;
    List<InfoModel> list;
    private AlertDialog alertDialog;
    private EditText searchView;
    private boolean applyFilters = false;
    Button open, exclusive;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_property_ku);
        IVSortAsc = findViewById(R.id.sortAscendingBtn);
        IVSortDesc = findViewById(R.id.sortDescendingBtn);
        searchView = findViewById(R.id.etsearchView);
        searchView.clearFocus();
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String newText = s.toString();
                filterList(newText);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        IVSortAsc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.sortAscending();
                IVSortDesc.setVisibility(View.VISIBLE);
                IVSortAsc.setVisibility(View.GONE);
            }
        });
        IVSortDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.sortDescending();
                IVSortDesc.setVisibility(View.GONE);
                IVSortAsc.setVisibility(View.VISIBLE);
            }
        });

        PDInfoKu = new ProgressDialog(InfoPropertyKuActivity.this);
        srinfoku = findViewById(R.id.SRInfoKu);
        rvlist = findViewById(R.id.RVInfoKu);

        list = new ArrayList<>();

        rvlist.setLayoutManager(new LinearLayoutManager(InfoPropertyKuActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new InfoAdapter(this,list);
        rvlist.setAdapter(adapter);

        LoadListing(true);

        srinfoku.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadListing(false);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void filterList(String text) {
        List<InfoModel> filteredList = new ArrayList<>();
        for (InfoModel item : list) {
            if (item.getAlamat().toLowerCase().contains(text.toLowerCase())
                    || item.getJenisProperty().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }
        adapter.setFilteredlist(filteredList);
    }
    private void LoadListing(boolean showProgressDialog) {
        PDInfoKu.setMessage("Memuat Data...");
        PDInfoKu.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_INFO_AGEN+ Preferences.getKeyIdAgen(InfoPropertyKuActivity.this),null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDInfoKu.cancel();
                        srinfoku.setRefreshing(false);
                        list.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                InfoModel md = new InfoModel( );
                                md.setIdInfo(data.getString("IdInfo"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setJenisProperty(data.getString("JenisProperty"));
                                md.setStatusProperty(data.getString("StatusProperty"));
                                md.setNarahubung(data.getString("Narahubung"));
                                md.setImgSelfie(data.getString("ImgSelfie"));
                                md.setImgProperty(data.getString("ImgProperty"));
                                md.setLokasi(data.getString("Lokasi"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setNoTelp(data.getString("NoTelp"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setTglInput(data.getString("TglInput"));
                                md.setJamInput(data.getString("JamInput"));
                                md.setIsListing(data.getString("IsListing"));
                                md.setLBangunan(data.getString("LBangunan"));
                                md.setLTanah(data.getString("LTanah"));
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
                                md.setKeterangan(data.getString("Keterangan"));
                                md.setIsSpek(data.getString("IsSpek"));
                                list.add(md);
                                PDInfoKu.cancel();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDInfoKu.cancel();
                                srinfoku.setRefreshing(false);

                                Dialog customDialog = new Dialog(InfoPropertyKuActivity.this);
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
                        PDInfoKu.cancel();
                        srinfoku.setRefreshing(false);
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(InfoPropertyKuActivity.this);
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