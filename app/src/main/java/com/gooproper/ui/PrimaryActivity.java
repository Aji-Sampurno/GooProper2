package com.gooproper.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.gooproper.adapter.ListingAdapter;
import com.gooproper.adapter.PrimaryAdapter;
import com.gooproper.model.ListingModel;
import com.gooproper.model.PrimaryModel;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrimaryActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    ImageView IVSortAsc, IVSortDesc, IVFilter;
    SwipeRefreshLayout srprimary;
    RecyclerView rvgrid;
    PrimaryAdapter adapter;
    List<PrimaryModel> list;
    private AlertDialog alertDialog;
    private EditText searchView;
    private boolean applyFilters = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        pDialog = new ProgressDialog(PrimaryActivity.this);

        IVSortAsc = findViewById(R.id.sortAscendingBtn);
        IVSortDesc = findViewById(R.id.sortDescendingBtn);
        IVFilter = findViewById(R.id.filterBtn);
        searchView  = findViewById(R.id.etsearchView);
        srprimary = findViewById(R.id.SRPrimary);
        rvgrid = findViewById(R.id.RVListingPrimary);

        list = new ArrayList<>();

        rvgrid.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new PrimaryAdapter(this,list);
        rvgrid.setAdapter(adapter);

        LoadListing(true);

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
        IVFilter.setVisibility(View.INVISIBLE);
        srprimary.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        List<PrimaryModel> filteredList = new ArrayList<>();
        for (PrimaryModel item : list) {
            if (item.getJudulListingPrimary().toLowerCase().contains(text.toLowerCase()) || item.getAlamatListingPrimary().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }
        adapter.setFilteredlist(filteredList);
    }
    private void LoadListing(boolean showProgressDialog) {
        pDialog.setMessage("Memuat Data...");
        pDialog.show();
        if (showProgressDialog) pDialog.show();
        else pDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_PRIMARY,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) pDialog.cancel();
                        else srprimary.setRefreshing(false);
                        list.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                PrimaryModel md = new PrimaryModel();
                                md.setIdListingPrimary(data.getString("IdListingPrimary"));
                                md.setJudulListingPrimary(data.getString("JudulListingPrimary"));
                                md.setHargaListingPrimary(data.getString("HargaListingPrimary"));
                                md.setDeskripsiListingPrimary(data.getString("DeskripsiListingPrimary"));
                                md.setAlamatListingPrimary(data.getString("AlamatListingPrimary"));
                                md.setLatitudeListingPrimary(data.getString("LatitudeListingPrimary"));
                                md.setLongitudeListingPrimary(data.getString("LongitudeListingPrimary"));
                                md.setLocationListingPrimary(data.getString("LocationListingPrimary"));
                                md.setKontakPerson1(data.getString("KontakPerson1"));
                                md.setKontakPerson2(data.getString("KontakPerson2"));
                                md.setImg1(data.getString("Img1"));
                                md.setImg2(data.getString("Img2"));
                                md.setImg3(data.getString("Img3"));
                                md.setImg4(data.getString("Img4"));
                                md.setImg5(data.getString("Img5"));
                                md.setImg6(data.getString("Img6"));
                                md.setImg7(data.getString("Img7"));
                                md.setImg8(data.getString("Img8"));
                                md.setImg9(data.getString("Img9"));
                                md.setImg10(data.getString("Img10"));
                                list.add(md);
                                pDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                pDialog.dismiss();

                                Dialog customDialog = new Dialog(PrimaryActivity.this);
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
                        pDialog.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(PrimaryActivity.this);
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