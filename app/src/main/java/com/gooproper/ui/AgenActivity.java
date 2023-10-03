package com.gooproper.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.AgenAdapter;
import com.gooproper.adapter.AgenAdminAdapter;
import com.gooproper.adapter.ListingSoldAdapter;
import com.gooproper.adapter.list.ListPelamarAgenAdapter;
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
    private AgenAdminAdapter adapterAgen;
    //private SearchView searchView;
    private EditText searchView;
    List<AgenModel> mItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agen);

        searchView  = findViewById(R.id.etsearchView);
        searchView.clearFocus();searchView.addTextChangedListener(new TextWatcher() {
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
        /*searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });*/

        recyclerView = findViewById(R.id.RVAgenAdmin);
        refreshLayout = findViewById(R.id.SRLAgenAdmin);

        mItems = new ArrayList<>();

        recyclerView.setLayoutManager(new LinearLayoutManager(AgenActivity.this, LinearLayoutManager.VERTICAL, false));
        adapterAgen = new AgenAdminAdapter(AgenActivity.this, mItems);
        recyclerView.setAdapter(adapterAgen);

        LoadAgen(true);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    //searchView
    private void filterList(String text) {
        List<AgenModel> filteredList = new ArrayList<>();
        for (AgenModel item : mItems) {
            if (item.getNama().toLowerCase().contains(text.toLowerCase())
                    || item.getUsername().toLowerCase().contains(text.toLowerCase())
                    || item.getAlamatDomisili().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "Not Found", Toast.LENGTH_SHORT).show();
        }
        adapterAgen.setFilteredlist(filteredList);
    }

    private void LoadAgen(boolean showProgressDialog) {
        final ProgressDialog progressDialog = new ProgressDialog(AgenActivity.this);
        progressDialog.setMessage("Memuat Data Agen...");
        progressDialog.show();
        if (showProgressDialog) progressDialog.show();
        else progressDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(AgenActivity.this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_DAFTAR_AGEN, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) progressDialog.cancel();
                        else refreshLayout.setRefreshing(false);
                        mItems.clear();
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
                                mItems.add(agenModel);
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