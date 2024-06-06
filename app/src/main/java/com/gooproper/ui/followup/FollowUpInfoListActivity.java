package com.gooproper.ui.followup;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

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
import com.gooproper.R;
import com.gooproper.adapter.followup.FlowUpInfoAdapter;
import com.gooproper.adapter.listing.ListingAdapter;
import com.gooproper.model.FlowUpInfoModel;
import com.gooproper.model.InfoModel;
import com.gooproper.model.ListingModel;
import com.gooproper.ui.listing.InfoPropertyActivity;
import com.gooproper.ui.listing.ListingFavoriteActivity;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FollowUpInfoListActivity extends AppCompatActivity {

    ProgressDialog PDialog;
    SwipeRefreshLayout SRFollowUpInfo;
    RecyclerView RVFollowUpInfo;
    FlowUpInfoAdapter adapter;
    List<FlowUpInfoModel> list;
    String StringStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_up_info_list);

        PDialog = new ProgressDialog(FollowUpInfoListActivity.this);
        RVFollowUpInfo = findViewById(R.id.RVFollowUpInfo);
        SRFollowUpInfo = findViewById(R.id.SRFollowUpInfo);

        list = new ArrayList<>();
        StringStatus = Preferences.getKeyStatus(FollowUpInfoListActivity.this);

        LoadFollowUp(true);

        RVFollowUpInfo.setLayoutManager(new LinearLayoutManager(FollowUpInfoListActivity.this,LinearLayoutManager.VERTICAL,false));
        adapter = new FlowUpInfoAdapter(FollowUpInfoListActivity.this, list);
        RVFollowUpInfo.setAdapter(adapter);

        SRFollowUpInfo.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadFollowUp(true);
            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }
    private void LoadFollowUp(boolean showProgressDialog) {
        PDialog.setMessage("Memuat Data...");
        PDialog.show();
        if (showProgressDialog) PDialog.show();
        else PDialog.cancel();

        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_FLOWUP_INFO,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDialog.cancel();
                        else SRFollowUpInfo.setRefreshing(false);
                        list.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                FlowUpInfoModel md = new FlowUpInfoModel();
                                md.setIdFlowup(data.getString("IdFlowup"));
                                md.setIdInfo(data.getString("IdInfo"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setTanggal(data.getString("Tanggal"));
                                md.setJam(data.getString("Jam"));
                                md.setKeteranganFollowUp(data.getString("KeteranganFollowUp"));
                                md.setKeterangan(data.getString("Keterangan"));
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
                                md.setIsAgen(data.getString("IsAgen"));
                                md.setLBangunan(data.getString("LBangunan"));
                                md.setLTanah(data.getString("LTanah"));
                                md.setHarga(data.getString("Harga"));
                                md.setHargaSewa(data.getString("HargaSewa"));
                                md.setIsSpek(data.getString("IsSpek"));
                                list.add(md);
                                PDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDialog.dismiss();

                                Dialog customDialog = new Dialog(FollowUpInfoListActivity.this);
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
                                        LoadFollowUp(true);
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

                        Dialog customDialog = new Dialog(FollowUpInfoListActivity.this);
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
                                LoadFollowUp(true);
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