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
import com.gooproper.adapter.followup.FlowUpAdapter;
import com.gooproper.adapter.followup.FlowUpInfoAdapter;
import com.gooproper.model.FlowUpInfoModel;
import com.gooproper.model.FlowUpModel;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryFollowUpActivity extends AppCompatActivity {

    ProgressDialog PDialog;
    SwipeRefreshLayout SRFollowUp;
    RecyclerView RVFollowUp;
    FlowUpAdapter adapter;
    List<FlowUpModel> list;
    String StringStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_history_follow_up);

        PDialog = new ProgressDialog(HistoryFollowUpActivity.this);
        RVFollowUp = findViewById(R.id.RVHistoryFollowUp);
        SRFollowUp = findViewById(R.id.SRHistoryFollowUp);

        list = new ArrayList<>();
        StringStatus = Preferences.getKeyStatus(HistoryFollowUpActivity.this);

        LoadFollowUp(true);

        RVFollowUp.setLayoutManager(new LinearLayoutManager(HistoryFollowUpActivity.this,LinearLayoutManager.VERTICAL,false));
        adapter = new FlowUpAdapter(HistoryFollowUpActivity.this, list);
        RVFollowUp.setAdapter(adapter);

        SRFollowUp.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_HISTORY_FOLLOW_UP,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (showProgressDialog) PDialog.cancel();
                        else SRFollowUp.setRefreshing(false);
                        list.clear();
                        for(int i = 0 ; i < response.length(); i++)
                        {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                FlowUpModel md = new FlowUpModel();
                                md.setIdFlowup(data.getString("IdFlowup"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setIdInput(data.getString("IdInput"));
                                md.setIdListing(data.getString("IdListing"));
                                md.setNamaBuyer(data.getString("NamaBuyer"));
                                md.setTelpBuyer(data.getString("TelpBuyer"));
                                md.setSumberBuyer(data.getString("SumberBuyer"));
                                md.setTanggal(data.getString("Tanggal"));
                                md.setJam(data.getString("Jam"));
                                md.setKeterangan(data.getString("Keterangan"));
                                md.setChat(data.getString("Chat"));
                                md.setSurvei(data.getString("Survei"));
                                md.setTawar(data.getString("Tawar"));
                                md.setLokasi(data.getString("Lokasi"));
                                md.setDeal(data.getString("Deal"));
                                md.setSelfie(data.getString("Selfie"));
                                md.setNamaListing(data.getString("NamaListing"));
                                md.setAlamat(data.getString("Alamat"));
                                md.setLatitude(data.getString("Latitude"));
                                md.setLongitude(data.getString("Longitude"));
                                md.setHarga(data.getString("Harga"));
                                list.add(md);
                                PDialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDialog.dismiss();

                                Dialog customDialog = new Dialog(HistoryFollowUpActivity.this);
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

                        Dialog customDialog = new Dialog(HistoryFollowUpActivity.this);
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