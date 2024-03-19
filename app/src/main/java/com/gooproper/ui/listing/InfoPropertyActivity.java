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
import com.gooproper.adapter.listing.InfoAdapter;
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.model.InfoModel;
import com.gooproper.model.ListingModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoPropertyActivity extends AppCompatActivity {

    ProgressDialog PDInfo;
    ImageView IVSortAsc, IVSortDesc, IVFilter;
    SwipeRefreshLayout SRInfo;
    RecyclerView RVInfo;
    InfoAdapter adapter;
    List<InfoModel> list;
    private AlertDialog alertDialog;
    private EditText searchView;
    private boolean applyFilters = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_property);

        IVSortAsc = findViewById(R.id.sortAscendingBtn);
        IVSortDesc = findViewById(R.id.sortDescendingBtn);
        IVFilter = findViewById(R.id.filterBtn);
        searchView  = findViewById(R.id.etsearchView);

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

        PDInfo = new ProgressDialog(InfoPropertyActivity.this);

        SRInfo = findViewById(R.id.SRInfo);
        RVInfo = findViewById(R.id.RVInfo);
        list = new ArrayList<>();

        RVInfo.setLayoutManager(new LinearLayoutManager(InfoPropertyActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new InfoAdapter(this,list);
        RVInfo.setAdapter(adapter);

        LoadListing(true);

        SRInfo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
            if (item.getJenisProperty().toLowerCase().contains(text.toLowerCase())
                    || item.getAlamat().toLowerCase().contains(text.toLowerCase())
                    || item.getStatusProperty().toLowerCase().contains(text.toLowerCase())
                    || item.getLokasi().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }
        adapter.setFilteredlist(filteredList);
    }
    private void LoadListing(boolean showProgressDialog) {
        PDInfo.setMessage("Memuat Data...");
        PDInfo.show();
        if (showProgressDialog) PDInfo.show();
        else PDInfo.cancel();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_INFO,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDInfo.cancel();
                        else SRInfo.setRefreshing(false);
                        list.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                InfoModel md = new InfoModel();
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
                                PDInfo.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDInfo.dismiss();

                                Dialog customDialog = new Dialog(InfoPropertyActivity.this);
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
                        PDInfo.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(InfoPropertyActivity.this);
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