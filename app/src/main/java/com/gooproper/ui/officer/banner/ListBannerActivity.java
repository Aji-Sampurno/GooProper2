package com.gooproper.ui.officer.banner;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import com.gooproper.R;
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.adapter.officer.PasangBannerAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.model.PasangBannerModel;
import com.gooproper.ui.listing.NewActivity;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListBannerActivity extends AppCompatActivity {

    ProgressDialog PDDialog;
    ImageView IVSortAsc, IVSortDesc, IVFilter;
    SwipeRefreshLayout srpasangbanner;
    RecyclerView rvgrid;
    PasangBannerAdapter adapter;
    List<PasangBannerModel> list;
    String StringKondisi;
    private boolean iskondisisewa = false;
    private AlertDialog alertDialog;
    private EditText searchView;
    private boolean applyFilters = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_banner);

        PDDialog = new ProgressDialog(ListBannerActivity.this);

        IVSortAsc = findViewById(R.id.sortAscendingBtn);
        IVSortDesc = findViewById(R.id.sortDescendingBtn);
        IVFilter = findViewById(R.id.filterBtn);
        searchView = findViewById(R.id.etsearchView);
        srpasangbanner = findViewById(R.id.SRPasangBanner);
        rvgrid = findViewById(R.id.RVPasangBanner);

        srpasangbanner.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadListing(false);
            }
        });

        LoadListing(true);

        list = new ArrayList<>();

        rvgrid.setLayoutManager(new LinearLayoutManager(ListBannerActivity.this, LinearLayoutManager.VERTICAL, false));
        adapter = new PasangBannerAdapter(this, list);
        rvgrid.setAdapter(adapter);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    private void LoadListing(boolean showProgressDialog) {
        PDDialog.setMessage("Memuat Data...");
        PDDialog.show();
        if (showProgressDialog) PDDialog.show();
        else PDDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PASANG_BANNER, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDDialog.cancel();
                        else srpasangbanner.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                PasangBannerModel md = new PasangBannerModel();
                                md.setIdListing(data.getString("IdListing"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setKondisi(data.getString("Kondisi"));
                                md.setPriority(data.getString("Priority"));
                                md.setSize(data.getString("Size"));
                                md.setImg1(data.getString("Img1"));
                                list.add(md);
                                PDDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDDialog.dismiss();

                                Dialog customDialog = new Dialog(ListBannerActivity.this);
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
                        PDDialog.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(ListBannerActivity.this);
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