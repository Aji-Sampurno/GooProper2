package com.gooproper.agen.fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gooproper.R;
import com.gooproper.adapter.followup.FlowUpAdapter;
import com.gooproper.adapter.followup.FlowUpPrimaryAdapter;
import com.gooproper.model.FlowUpModel;
import com.gooproper.model.FlowUpPrimaryModel;
import com.gooproper.util.Preferences;
import com.gooproper.util.ServerApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FlowUpAgenFragment extends Fragment {

    ProgressDialog PDFlowUpAgen;
    SwipeRefreshLayout srflowup;
    RecyclerView rvlist, rvprimary;
    RecyclerView.Adapter adapter;
    FlowUpPrimaryAdapter primaryAdapter;
    List<FlowUpModel> list;
    List<FlowUpPrimaryModel> primaryModelList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_flow_up_agen, container, false);

        PDFlowUpAgen = new ProgressDialog(getActivity());
        rvlist = root.findViewById(R.id.RVFlowUpListAgen);
        rvprimary = root.findViewById(R.id.RVFlowUpPrimaryListAgen);
//        srflowup = root.findViewById(R.id.SRFlowUpAgen);

        list = new ArrayList<>();
        primaryModelList = new ArrayList<>();

        LoadFlowup(true);

        rvlist.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        adapter = new FlowUpAdapter(getActivity(), list);
        rvlist.setAdapter(adapter);

        LoadFlowupPrimary(true);

        rvprimary.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL, false));
        primaryAdapter = new FlowUpPrimaryAdapter(getActivity(), primaryModelList);
        rvprimary.setAdapter(primaryAdapter);

//        srflowup.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                LoadFlowup(true);
//            }
//        });

        return root;
    }

    private void LoadFlowup(boolean showProgressDialog) {
        PDFlowUpAgen.setMessage("Memuat Data Follow Up...");
        PDFlowUpAgen.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_FLOWUP+Preferences.getKeyIdAgen(getActivity()), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDFlowUpAgen.cancel();
//                        srflowup.setRefreshing(false);
                        list.clear();
                        for (int i = 0; i < response.length(); i++) {
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
                                PDFlowUpAgen.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
//                                srflowup.setRefreshing(false);
                                PDFlowUpAgen.dismiss();

                                Dialog customDialog = new Dialog(getActivity());
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
                                        customDialog.dismiss();
                                        LoadFlowup(true);
                                    }
                                });

                                batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
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
//                        srflowup.setRefreshing(false);
                        PDFlowUpAgen.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(getActivity());
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
                                customDialog.dismiss();
                                LoadFlowup(true);
                            }
                        });

                        batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                    }
                });
        queue.add(reqData);
    }

    private void LoadFlowupPrimary(boolean showProgressDialog) {
        PDFlowUpAgen.setMessage("Memuat Data Follow Up...");
        PDFlowUpAgen.show();

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest reqData = new JsonArrayRequest(Request.Method.GET, ServerApi.URL_GET_FLOWUP_PRIMARY+Preferences.getKeyIdAgen(getActivity()), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        PDFlowUpAgen.cancel();
//                        srflowup.setRefreshing(false);
                        primaryModelList.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject data = response.getJSONObject(i);
                                FlowUpPrimaryModel md = new FlowUpPrimaryModel();
                                md.setIdFlowupPrimary(data.getString("IdFlowupPrimary"));
                                md.setIdAgen(data.getString("IdAgen"));
                                md.setIdInput(data.getString("IdInput"));
                                md.setIdListingPrimary(data.getString("IdListingPrimary"));
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
                                md.setJudulListingPrimary(data.getString("JudulListingPrimary"));
                                md.setAlamatListingPrimary(data.getString("AlamatListingPrimary"));
                                md.setHargaListingPrimary(data.getString("HargaListingPrimary"));
                                primaryModelList.add(md);
                                PDFlowUpAgen.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                PDFlowUpAgen.dismiss();
//                                srflowup.setRefreshing(false);

                                Dialog customDialog = new Dialog(getActivity());
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
                                        customDialog.dismiss();
                                        LoadFlowupPrimary(true);
                                    }
                                });
                                batal.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        customDialog.dismiss();
                                    }
                                });

                                customDialog.show();
                            }
                        }

                        primaryAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        srflowup.setRefreshing(false);
                        PDFlowUpAgen.dismiss();
                        error.printStackTrace();

                        Dialog customDialog = new Dialog(getActivity());
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
                                customDialog.dismiss();
                                LoadFlowup(true);
                            }
                        });
                        batal.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                customDialog.dismiss();
                            }
                        });
                        customDialog.show();
                    }
                });
        queue.add(reqData);
    }
}